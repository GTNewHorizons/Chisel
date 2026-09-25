package team.chisel.ctmlib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lombok.experimental.Delegate;

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

    public static final class StitchHandler {

        @SubscribeEvent
        public void onTextureStitchPre(TextureStitchEvent.Pre event) {
            if (event.map.getTextureType() != 0) return;
            TextureSubmapResource.beginTextureStitch();
            for (TextureSubmap submap : submaps) {
                submap.registerSubIcons(event.map);
            }
            submaps.clear();
        }

        @SubscribeEvent
        public void onTextureStitchPost(TextureStitchEvent.Post event) {
            if (event.map.getTextureType() != 0) return;
            TextureSubmapResource.endTextureStitch();
        }
    }

    /**
     * Register the real sprites backing this submap.
     */
    public void registerSubIcons(TextureMap textureMap) {
        if (baseIcon == null || width <= 0 || height <= 0) {
            return;
        }

        ResourceLocation sourceLocation = new ResourceLocation(baseIcon.getIconName());
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                String name = TextureSubmapResource.getSubIconName(sourceLocation, width, height, x, y);
                icons[x][y] = textureMap.registerIcon(name);
            }
        }
    }
}
