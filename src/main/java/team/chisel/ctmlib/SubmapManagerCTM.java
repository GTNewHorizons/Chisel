package team.chisel.ctmlib;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.Getter;

/**
 * A convenience implementation of {@link ISubmapManager} which does the standard CTM behavior.
 */
public class SubmapManagerCTM implements ISubmapManager {

    @SideOnly(Side.CLIENT)
    private static RenderBlocksCTM rb;

    @Getter
    private TextureSubmap submap, submapSmall;
    @Getter
    private final String textureName;

    /**
     * @param textureName The path to the texture, excluding the mod ID (this is passed into
     *                    {@link #registerIcons(String, Block, IIconRegister)}).
     *                    <p>
     *                    The CTM texture will be this path, plus {@code "-ctm"}.
     */
    public SubmapManagerCTM(String textureName) {
        this.textureName = textureName;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return submapSmall.getBaseIcon();
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(String modName, Block block, IIconRegister register) {
        submap = new TextureSubmap(register.registerIcon(modName + ":" + textureName + "-ctm"), 4, 4);
        submapSmall = new TextureSubmap(register.registerIcon(modName + ":" + textureName), 2, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public RenderBlocks createRenderContext(RenderBlocks rendererOld, Block block, IBlockAccess world) {
        if (rb == null) {
            rb = new RenderBlocksCTM();
        }
        rb.setRenderBoundsFromBlock(block);
        rb.submap = submap;
        rb.submapSmall = submapSmall;
        return rb;
    }

    @Override
    public void preRenderSide(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, ForgeDirection side) {}

    @Override
    public void postRenderSide(RenderBlocks renderer, IBlockAccess world, int x, int y, int z, ForgeDirection side) {}
}
