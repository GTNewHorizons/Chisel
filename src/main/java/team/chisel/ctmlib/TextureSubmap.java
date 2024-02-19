package team.chisel.ctmlib;

import java.util.List;

import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.experimental.Delegate;

/**
 * This class is used to split up a large IIcon into smaller "submapped" icons used for CTM and other texture
 * manipulation.
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
     * Construct a new submap. A submap is not required to be square, but it is required to be rectangular.
     *
     * @param baseIcon The IIcon to submap.
     * @param width    The width of the map, in icons.
     * @param height   The height of the map, in icons.
     */
    public TextureSubmap(IIcon baseIcon, int width, int height) {
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
     * For internal use only, this is used to create the virtual "subicons" used in the map.
     */
    @SubscribeEvent
    public final void TexturesStitched(TextureStitchEvent.Post event) {
        for (TextureSubmap ts : submaps) {
            ts.texturesStitched();
        }
    }

    public void texturesStitched() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                icons[x][y] = new TextureVirtual(getBaseIcon(), width, height, x, y);
            }
        }
    }

    private class TextureVirtual implements IIcon {

        private int width, height;
        private float umin, umax, vmin, vmax;
        private IIcon parentIcon;

        private TextureVirtual(IIcon parent, int w, int h, int x, int y) {
            parentIcon = parent;

            umin = parentIcon.getInterpolatedU(16.0 * (x) / w);
            umax = parentIcon.getInterpolatedU(16.0 * (x + 1) / w);
            vmin = parentIcon.getInterpolatedV(16.0 * (y) / h);
            vmax = parentIcon.getInterpolatedV(16.0 * (y + 1) / h);

            width = parentIcon.getIconWidth();
            height = parentIcon.getIconHeight();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMinU() {
            return umin;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMaxU() {
            return umax;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getInterpolatedU(double d0) {
            return (float) (umin + (umax - umin) * d0 / 16.0);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMinV() {
            return vmin;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getMaxV() {
            return vmax;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public float getInterpolatedV(double d0) {
            return (float) (vmin + (vmax - vmin) * d0 / 16.0);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getIconName() {
            return parentIcon.getIconName();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public int getIconWidth() {
            return width;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public int getIconHeight() {
            return height;
        }
    }
}
