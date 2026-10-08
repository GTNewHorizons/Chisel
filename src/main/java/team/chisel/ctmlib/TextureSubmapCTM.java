package team.chisel.ctmlib;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

/**
 * A CTM submap that keeps each tile in one atlas sprite while exposing its four quadrants as sub-icons.
 */
public final class TextureSubmapCTM extends TextureSubmap {

    /**
     * @param baseIcon The registered texture sheet.
     * @param size     The number of logical quadrants per axis: 4 for the CTM sheet, 2 for the normal texture.
     */
    public TextureSubmapCTM(IIcon baseIcon, int size) {
        super(baseIcon, size, size);

        if (size <= 0 || (size & 1) != 0) {
            throw new IllegalArgumentException("CTM submap size must be a positive even number: " + size);
        }
    }

    @Override
    public void registerSubIcons(TextureMap textureMap) {
        registerSpriteGrid(textureMap, getWidth() / 2, getHeight() / 2);
    }
}
