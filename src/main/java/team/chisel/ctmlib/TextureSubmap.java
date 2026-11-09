package team.chisel.ctmlib;

import java.util.ArrayList;
import java.util.Collections;
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
 * Represents a grid of individually accessible texture regions.
 */
public class TextureSubmap implements IIcon, ISubmap {

    private static final List<TextureSubmap> submaps = Collections.synchronizedList(new ArrayList<>(128));

    @Delegate
    private final IIcon baseIcon;
    private final int width;
    private final int height;
    protected final IIcon[][] icons;

    /**
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

    public void registerSubIcons(TextureMap textureMap) {
        registerSpriteGrid(textureMap, width, height);
    }

    /**
     * Splits the texture into a grid of atlas sprites and assigns sub-icons to them.
     * <p>
     * The number of sub-icons is determined by the submap's width and height,
     * while spriteColumns and spriteRows determine how many separate atlas sprites
     * are used. Each sprite can contain one or more sub-icons.
     * <p>
     * For example, a 4x4 submap backed by a 2x2 sprite grid uses four atlas sprites,
     * each containing a 2x2 group of sub-icons.
     */
    protected final void registerSpriteGrid(TextureMap textureMap, int spriteColumns, int spriteRows) {
        if (baseIcon == null || width <= 0 || height <= 0) {
            return;
        }
        if (spriteColumns <= 0 || spriteRows <= 0 || width % spriteColumns != 0 || height % spriteRows != 0) {
            throw new IllegalArgumentException(
                "Logical submap " + width
                    + "x"
                    + height
                    + " cannot be backed by sprite grid "
                    + spriteColumns
                    + "x"
                    + spriteRows);
        }

        ResourceLocation sourceLocation = new ResourceLocation(baseIcon.getIconName());
        IIcon[][] sprites = new IIcon[spriteColumns][spriteRows];

        for (int spriteX = 0; spriteX < spriteColumns; spriteX++) {
            for (int spriteY = 0; spriteY < spriteRows; spriteY++) {
                sprites[spriteX][spriteY] = getBackingSprite(
                    textureMap,
                    sourceLocation,
                    spriteColumns,
                    spriteRows,
                    spriteX,
                    spriteY);
            }
        }

        int cellsPerSpriteX = width / spriteColumns;
        int cellsPerSpriteY = height / spriteRows;
        boolean wholeSprite = cellsPerSpriteX == 1 && cellsPerSpriteY == 1;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                IIcon sprite = sprites[x / cellsPerSpriteX][y / cellsPerSpriteY];
                icons[x][y] = wholeSprite ? sprite
                    : new TextureRegion(
                        sprite,
                        cellsPerSpriteX,
                        cellsPerSpriteY,
                        x % cellsPerSpriteX,
                        y % cellsPerSpriteY);
            }
        }
    }

    private IIcon getBackingSprite(TextureMap textureMap, ResourceLocation sourceLocation, int columns, int rows, int x,
        int y) {

        if (columns == 1 && rows == 1) {
            return baseIcon;
        }

        String name = TextureSubmapResource.getSubIconName(sourceLocation, columns, rows, x, y);
        return textureMap.registerIcon(name);
    }

    private static final class TextureRegion implements IIcon {

        private final IIcon parent;
        private final int columns;
        private final int rows;
        private final int x;
        private final int y;

        private TextureRegion(IIcon parent, int columns, int rows, int x, int y) {
            this.parent = parent;
            this.columns = columns;
            this.rows = rows;
            this.x = x;
            this.y = y;
        }

        @Override
        public float getMinU() {
            return getInterpolatedU(0);
        }

        @Override
        public float getMaxU() {
            return getInterpolatedU(16);
        }

        @Override
        public float getInterpolatedU(double u) {
            return parent.getInterpolatedU((16.0 * x + u) / columns);
        }

        @Override
        public float getMinV() {
            return getInterpolatedV(0);
        }

        @Override
        public float getMaxV() {
            return getInterpolatedV(16);
        }

        @Override
        public float getInterpolatedV(double v) {
            return parent.getInterpolatedV((16.0 * y + v) / rows);
        }

        @Override
        public String getIconName() {
            return parent.getIconName();
        }

        @Override
        public int getIconWidth() {
            return parent.getIconWidth() / columns;
        }

        @Override
        public int getIconHeight() {
            return parent.getIconHeight() / rows;
        }
    }
}
