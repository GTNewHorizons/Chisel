package team.chisel.ctmlib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.Getter;

/**
 * Convenience implementation of ICMBlock. This does everything you should need for basic CTM.
 * <p>
 * This class does <i>not</i> handle metadata-dependent textures.
 */
public class CTMBlock extends Block implements ICTMBlock<ISubmapManager> {

    @SideOnly(Side.CLIENT)
    private SubmapManagerCTM manager;

    @Getter
    private final String modid;
    @Getter
    private final String texturePath;

    private final int renderId;

    public CTMBlock(String modid, String texturePath, int renderId) {
        super(Material.rock);
        this.modid = modid;
        this.texturePath = texturePath;
        this.renderId = renderId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon) {
        manager = new SubmapManagerCTM(texturePath);
        manager.registerIcons(modid, this, icon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return manager.getIcon(world, x, y, z, side);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return manager.getIcon(side, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return renderId;
    }

    @Override
    public ISubmapManager getManager(IBlockAccess world, int x, int y, int z, int meta) {
        return manager;
    }

    @Override
    public ISubmapManager getManager(int meta) {
        return manager;
    }
}
