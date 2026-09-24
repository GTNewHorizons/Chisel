package team.chisel.ctmlib;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import org.apache.commons.lang3.ArrayUtils;

import com.github.bsideup.jabel.Desugar;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.experimental.Delegate;
import team.chisel.Chisel;

/**
 * Splits a texture sheet into independently stitched sub-icons used for CTM and other texture manipulation.
 */
public class TextureSubmap implements IIcon, ISubmap {

    private static final List<TextureSubmap> submaps = new ArrayList<>();

    @Delegate
    private final IIcon baseIcon;
    private final int width;
    private final int height;
    protected final IIcon[][] icons;

    /**
     * Construct a new square submap.
     *
     * @param baseIcon The IIcon to submap.
     * @param width    The width of the map, in icons.
     * @param height   The height of the map, in icons. Must equal {@code width}.
     */
    public TextureSubmap(IIcon baseIcon, int width, int height) {
        if (width != height) {
            throw new IllegalArgumentException("TextureSubmap must be square: " + width + "x" + height);
        }

        this.baseIcon = baseIcon;
        this.width = width;
        this.height = height;
        this.icons = new IIcon[width][height];
        submaps.add(this);
    }

    /**
     * The large "base" icon used for the submap.
     */
    public IIcon getBaseIcon() {
        return baseIcon;
    }

    /**
     * Gets all the icons in this submap. This is an expensive operation as it first clones the entire 2D array.
     *
     * @return All icons in this submap.
     */
    public IIcon[][] getAllIcons() {
        IIcon[][] ret = ArrayUtils.clone(icons);
        for (int i = 0; i < ret.length; i++) {
            ret[i] = ArrayUtils.clone(ret[i]);
        }
        return ret;
    }

    /* ==== ISubmap ==== */

    /**
     * Finds and returns the sub-icon at the given coordinates. For example, if you have a 3 by 3 submap, calling
     * {@code getSubIcon(1, 1)} will return the center subicon.
     */
    @Override
    public IIcon getSubIcon(int x, int y) {
        x = x % icons.length;
        return icons[x][y % icons[x].length];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    /* ==== Internal Stitching Logic ==== */

    static {
        MinecraftForge.EVENT_BUS.register(new StitchHandler());
    }

    private static final class StitchHandler {

        @SubscribeEvent
        public void onTextureStitchPre(TextureStitchEvent.Pre event) {
            if (event.map.getTextureType() != 0) return;
            TextureSubmapSprite.clearSourceCache();
            for (TextureSubmap submap : submaps) {
                submap.registerSubIcons(event.map);
            }
            submaps.clear();
        }

        @SubscribeEvent
        public void onTextureStitchPost(TextureStitchEvent.Post event) {
            TextureSubmapSprite.clearSourceCache();
        }
    }

    /**
     * Register the real sprites backing this submap.
     */
    public void registerSubIcons(TextureMap textureMap) {
        if (baseIcon == null || width <= 0 || height <= 0) {
            return;
        }

        ResourceLocation sourceIcon = new ResourceLocation(baseIcon.getIconName());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String name = getSubIconName(sourceIcon, width, height, x, y);
                TextureAtlasSprite registered = textureMap.getTextureExtry(name);
                if (registered == null) {
                    registered = new TextureSubmapSprite(name, sourceIcon, width, height, x, y);
                    textureMap.setTextureEntry(name, registered);
                }
                icons[x][y] = registered;
            }
        }
    }

    private static String getSubIconName(ResourceLocation source, int width, int height, int x, int y) {
        return source.getResourceDomain() + ":__chisel_submap/"
            + width
            + "x"
            + height
            + "/"
            + x
            + "_"
            + y
            + "/"
            + source.getResourcePath();
    }

    @SideOnly(Side.CLIENT)
    private static final class TextureSubmapSprite extends TextureAtlasSprite {

        @Desugar
        private record SourceTexture(BufferedImage[] images, AnimationMetadataSection animation) {}

        private static final Map<ResourceLocation, SourceTexture> sourceCache = new ConcurrentHashMap<>();

        private final ResourceLocation sourceLocation;
        private final int columns;
        private final int rows;
        private final int cellX;
        private final int cellY;

        private TextureSubmapSprite(String name, ResourceLocation sourceLocation, int columns, int rows, int cellX,
            int cellY) {

            super(name);
            this.sourceLocation = sourceLocation;
            this.columns = columns;
            this.rows = rows;
            this.cellX = cellX;
            this.cellY = cellY;
        }

        @Override
        public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
            return true;
        }

        @Override
        public boolean load(IResourceManager manager, ResourceLocation location) {
            try {
                int mipmapLevels = Minecraft.getMinecraft().gameSettings.mipmapLevels;
                boolean useAnisotropicFiltering = Minecraft.getMinecraft().gameSettings.anisotropicFiltering > 1;

                SourceTexture sourceTexture = getSourceTexture(manager, sourceLocation, mipmapLevels);
                BufferedImage[] images = new BufferedImage[sourceTexture.images().length];
                boolean animated = sourceTexture.animation() != null;

                for (int level = 0; level < images.length; level++) {
                    BufferedImage sourceImage = sourceTexture.images()[level];
                    if (sourceImage == null) continue;
                    // A mip level can be too small to split into the submap grid
                    if (sourceImage.getWidth() < columns) continue;
                    images[level] = extractSubImage(sourceImage, animated);
                }

                loadSprite(images, sourceTexture.animation(), useAnisotropicFiltering);
                return false;
            } catch (IOException | RuntimeException e) {
                Chisel.logger.warn("Unable to create submap sprite {} from {}", getIconName(), sourceLocation, e);
                return true;
            }
        }

        private static SourceTexture getSourceTexture(IResourceManager manager, ResourceLocation sourceLocation,
            int mipmapLevels) throws IOException {

            SourceTexture cached = sourceCache.get(sourceLocation);
            if (cached != null) {
                return cached;
            }

            ResourceLocation location = getSourceResource(sourceLocation, 0);
            IResource resource = manager.getResource(location);

            BufferedImage[] images = new BufferedImage[mipmapLevels + 1];
            images[0] = readImage(resource, location);

            TextureMetadataSection textureMetadata = (TextureMetadataSection) resource.getMetadata("texture");
            if (textureMetadata != null && !textureMetadata.getListMipmaps()
                .isEmpty()) {

                int width = images[0].getWidth();
                int height = images[0].getHeight();

                if (MathHelper.roundUpToPowerOfTwo(width) != width
                    || MathHelper.roundUpToPowerOfTwo(height) != height) {
                    throw new RuntimeException("Unable to load extra miplevels, source texture is not power of two");
                }

                for (int mipLevel : textureMetadata.getListMipmaps()) {
                    if (mipLevel <= 0 || mipLevel >= images.length || images[mipLevel] != null) {
                        continue;
                    }

                    ResourceLocation mipLocation = getSourceResource(sourceLocation, mipLevel);
                    try {
                        images[mipLevel] = readImage(manager.getResource(mipLocation), mipLocation);
                    } catch (IOException e) {
                        Chisel.logger.warn("Unable to load mip level {} from {}", mipLevel, mipLocation, e);
                    }
                }
            }

            AnimationMetadataSection animation = (AnimationMetadataSection) resource.getMetadata("animation");
            SourceTexture sourceTexture = new SourceTexture(images, animation);

            SourceTexture existing = sourceCache.putIfAbsent(sourceLocation, sourceTexture);
            return existing != null ? existing : sourceTexture;
        }

        private static BufferedImage readImage(IResource resource, ResourceLocation location) throws IOException {
            BufferedImage image;
            try (InputStream stream = resource.getInputStream()) {
                image = ImageIO.read(stream);
            }
            if (image == null) {
                throw new IOException("ImageIO could not decode " + location);
            }
            return image;
        }

        private static void clearSourceCache() {
            sourceCache.clear();
        }

        private static ResourceLocation getSourceResource(ResourceLocation sourceLocation, int mipLevel) {
            String domain = sourceLocation.getResourceDomain();
            String path = sourceLocation.getResourcePath();
            if (mipLevel == 0) return new ResourceLocation(domain, "textures/blocks/" + path + ".png");
            return new ResourceLocation(domain, "textures/blocks/mipmaps/" + path + "." + mipLevel + ".png");
        }

        private BufferedImage extractSubImage(BufferedImage source, boolean animated) {
            int frameSize = source.getWidth();
            int sourceHeight = source.getHeight();

            if (frameSize % columns != 0 || frameSize % rows != 0) {
                throw new IllegalArgumentException(
                    "Texture size " + frameSize + " is not divisible by submap " + columns + "x" + rows);
            }

            if (animated) {
                if (sourceHeight % frameSize != 0) {
                    throw new IllegalArgumentException(
                        "Animated texture height " + sourceHeight + " is not divisible by frame size " + frameSize);
                }
            } else if (sourceHeight != frameSize) {
                throw new IllegalArgumentException(
                    "Non-animated texture is not square: " + frameSize + "x" + sourceHeight);
            }

            int cellWidth = frameSize / columns;
            int cellHeight = frameSize / rows;
            int frameCount = animated ? sourceHeight / frameSize : 1;

            BufferedImage result = new BufferedImage(cellWidth, cellHeight * frameCount, BufferedImage.TYPE_INT_ARGB);
            int[] pixels = new int[cellWidth * cellHeight];

            for (int frame = 0; frame < frameCount; frame++) {
                int sourceX = cellX * cellWidth;
                int sourceY = frame * frameSize + cellY * cellHeight;
                source.getRGB(sourceX, sourceY, cellWidth, cellHeight, pixels, 0, cellWidth);
                result.setRGB(0, frame * cellHeight, cellWidth, cellHeight, pixels, 0, cellWidth);
            }

            return result;
        }
    }
}
