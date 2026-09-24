package team.chisel.ctmlib;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.experimental.Delegate;
import team.chisel.Chisel;

/**
 * Splits a texture sheet into independently stitched sub-icons used for CTM and other texture manipulation.
 */
public class TextureSubmap implements IIcon, ISubmap {

    private static List<TextureSubmap> submaps = Lists.newArrayList();
    private static TextureSubmap dummy = new TextureSubmap(null, 0, 0);
    static {
        MinecraftForge.EVENT_BUS.register(dummy);
    }

    private int width, height;
    @Delegate
    private IIcon baseIcon;

    protected IIcon[][] icons;

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

    /**
     * Registers each submap cell as an independent atlas sprite so filtered sampling cannot bleed into adjacent
     * cells of the source texture sheet.
     */
    @SubscribeEvent
    public final void onTextureStitchPre(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() != 0) return;
        TextureSubmapSprite.clearSourceCache();
        for (TextureSubmap ts : submaps) {
            ts.registerSubIcons(event.map);
        }
        submaps.clear();
    }

    @SubscribeEvent
    public final void onTextureStitchPost(TextureStitchEvent.Post event) {
        TextureSubmapSprite.clearSourceCache();
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

                if (!(registered instanceof TextureSubmapSprite)) {
                    TextureSubmapSprite sprite = new TextureSubmapSprite(name, sourceIcon, width, height, x, y);
                    if (textureMap.setTextureEntry(name, sprite)) {
                        registered = sprite;
                    } else {
                        registered = textureMap.getTextureExtry(name);
                    }
                }

                if (registered instanceof TextureSubmapSprite) {
                    icons[x][y] = registered;
                }
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
    private static class TextureSubmapSprite extends TextureAtlasSprite {

        private static final Map<ResourceLocation, SourceData> sourceCache = new ConcurrentHashMap<>();

        private final ResourceLocation sourceIcon;
        private final int columns;
        private final int rows;
        private final int cellX;
        private final int cellY;

        private TextureSubmapSprite(String name, ResourceLocation sourceIcon, int columns, int rows, int cellX,
            int cellY) {
            super(name);
            this.sourceIcon = sourceIcon;
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
            ResourceLocation source = getSourceResource();

            try {
                SourceData sourceData = getSourceData(manager, source);
                BufferedImage subImage = extractSubImage(sourceData.image, sourceData.animation != null);

                // The source animation metadata can be reused: frame indices and timings stay the same,
                // only the pixels of each frame are cropped to this submap cell.
                BufferedImage[] images = new BufferedImage[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1];
                images[0] = subImage;
                loadSprite(images, sourceData.animation, false);
                return false;
            } catch (IOException | RuntimeException e) {
                Chisel.logger.warn("Unable to create submap sprite {} from {}", getIconName(), source, e);
                return true;
            }
        }

        private static SourceData getSourceData(IResourceManager manager, ResourceLocation source) throws IOException {
            SourceData sourceData = sourceCache.get(source);
            if (sourceData != null) {
                return sourceData;
            }

            IResource resource = manager.getResource(source);
            BufferedImage image;
            try (InputStream stream = resource.getInputStream()) {
                image = ImageIO.read(stream);
            }

            if (image == null) {
                throw new IOException("ImageIO could not decode " + source);
            }

            AnimationMetadataSection animation = (AnimationMetadataSection) resource.getMetadata("animation");
            sourceData = new SourceData(image, animation);

            SourceData cached = sourceCache.putIfAbsent(source, sourceData);
            return cached == null ? sourceData : cached;
        }

        private static void clearSourceCache() {
            sourceCache.clear();
        }

        private ResourceLocation getSourceResource() {
            return new ResourceLocation(
                sourceIcon.getResourceDomain(),
                "textures/blocks/" + sourceIcon.getResourcePath() + ".png");
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
            if (cellWidth != cellHeight) {
                throw new IllegalArgumentException(
                    "Submap cell is not square: " + cellWidth + "x" + cellHeight + " (" + columns + "x" + rows + ")");
            }

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

        private static class SourceData {

            private final BufferedImage image;
            private final AnimationMetadataSection animation;

            private SourceData(BufferedImage image, AnimationMetadataSection animation) {
                this.image = image;
                this.animation = animation;
            }
        }
    }
}
