package team.chisel.ctmlib;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

/**
 * Chisel CTM stores four logical face quadrants in each complete texture.
 *
 * A 4x4 CTM sheet therefore contains a 2x2 grid of complete textures, while the normal 2x2 sheet contains one.
 * Those complete textures are the units that need independent atlas stitching; their four quadrants remain virtual.
 */
public final class TextureSubmapCTM extends TextureSubmap {

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
