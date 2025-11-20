package team.chisel.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.cricketcraft.chisel.api.carving.CarvingUtils;
import com.cricketcraft.chisel.api.rendering.TextureType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import team.chisel.ctmlib.ISubmapManager;
import team.chisel.ctmlib.RenderBlocksCTM;
import team.chisel.ctmlib.TextureSubmap;

public class SubmapManagerVoidstone extends SubmapManagerBase {

    // TODO there must be a better more generic way to do this...
    @SideOnly(Side.CLIENT)
    private static class RenderBlocksVoidstone extends RenderBlocksCTM {

        @Override
        public void renderFaceXNeg(Block block, double x, double y, double z, IIcon icon) {
            shiftBoundary(ForgeDirection.WEST);
            setOverrideBlockTexture(getBase(x, y, z, ForgeDirection.WEST.ordinal()));
            super.renderFaceXNeg(block, x, y, z, null);
            clearOverrideBlockTexture();
            resetBoundary(ForgeDirection.WEST);

            super.renderFaceXNeg(block, x, y, z, icon);
        }

        @Override
        public void renderFaceXPos(Block block, double x, double y, double z, IIcon icon) {
            shiftBoundary(ForgeDirection.EAST);
            setOverrideBlockTexture(getBase(x, y, z, ForgeDirection.EAST.ordinal()));
            super.renderFaceXPos(block, x, y, z, null);
            clearOverrideBlockTexture();
            resetBoundary(ForgeDirection.EAST);

            super.renderFaceXPos(block, x, y, z, icon);
        }

        @Override
        public void renderFaceYNeg(Block block, double x, double y, double z, IIcon icon) {
            shiftBoundary(ForgeDirection.DOWN);
            setOverrideBlockTexture(getBase(x, y, z, ForgeDirection.DOWN.ordinal()));
            super.renderFaceYNeg(block, x, y, z, null);
            clearOverrideBlockTexture();
            resetBoundary(ForgeDirection.DOWN);

            super.renderFaceYNeg(block, x, y, z, icon);
        }

        @Override
        public void renderFaceYPos(Block block, double x, double y, double z, IIcon icon) {
            shiftBoundary(ForgeDirection.UP);
            setOverrideBlockTexture(getBase(x, y, z, ForgeDirection.UP.ordinal()));
            super.renderFaceYPos(block, x, y, z, null);
            clearOverrideBlockTexture();
            resetBoundary(ForgeDirection.UP);

            super.renderFaceYPos(block, x, y, z, icon);
        }

        @Override
        public void renderFaceZNeg(Block block, double x, double y, double z, IIcon icon) {
            shiftBoundary(ForgeDirection.NORTH);
            setOverrideBlockTexture(getBase(x, y, z, ForgeDirection.NORTH.ordinal()));
            super.renderFaceZNeg(block, x, y, z, null);
            clearOverrideBlockTexture();
            resetBoundary(ForgeDirection.NORTH);

            super.renderFaceZNeg(block, x, y, z, icon);
        }

        @Override
        public void renderFaceZPos(Block block, double x, double y, double z, IIcon icon) {
            shiftBoundary(ForgeDirection.SOUTH);
            setOverrideBlockTexture(getBase(x, y, z, ForgeDirection.SOUTH.ordinal()));
            super.renderFaceZPos(block, x, y, z, null);
            clearOverrideBlockTexture();
            resetBoundary(ForgeDirection.SOUTH);

            super.renderFaceZPos(block, x, y, z, icon);
        }

        private IIcon getBase(double x, double y, double z, int side) {
            return TextureType.getVIcon(
                TextureType.V4,
                base,
                MathHelper.floor_double(x),
                MathHelper.floor_double(y),
                MathHelper.floor_double(z),
                side);
        }

        private void shiftBoundary(ForgeDirection direction) {
            // When rendering a CTM overlay texture over a base block texture the base texture starts to z-fight.
            // This method makes the base texture render slightly below the overlay.
            // This submap check makes sure that we only apply this hack for blocks with CTM overlays
            if (submap == null) {
                return;
            }

            float epsilon = 0.0005F;

            switch (direction) {
                case WEST -> {
                    renderMinX += epsilon;
                    renderMinX += epsilon;
                }
                case EAST -> {
                    renderMinX -= epsilon;
                    renderMaxX -= epsilon;
                }
                case DOWN -> {
                    renderMinY += epsilon;
                    renderMaxY += epsilon;
                }
                case UP -> {
                    renderMinY -= epsilon;
                    renderMaxY -= epsilon;
                }
                case NORTH -> {
                    renderMinZ += epsilon;
                    renderMaxZ += epsilon;
                }
                case SOUTH -> {
                    renderMinZ -= epsilon;
                    renderMaxZ -= epsilon;
                }
            }
        }

        private void resetBoundary(ForgeDirection direction) {
            if (submap == null) {
                return;
            }

            float epsilon = 0.0005F;

            switch (direction) {
                case WEST -> {
                    renderMinX -= epsilon;
                    renderMaxX -= epsilon;
                }
                case EAST -> {
                    renderMinX += epsilon;
                    renderMaxX += epsilon;
                }
                case DOWN -> {
                    renderMinY -= epsilon;
                    renderMaxY -= epsilon;
                }
                case UP -> {
                    renderMinY += epsilon;
                    renderMaxY += epsilon;
                }
                case NORTH -> {
                    renderMinZ -= epsilon;
                    renderMaxZ -= epsilon;
                }
                case SOUTH -> {
                    renderMinZ += epsilon;
                    renderMaxZ += epsilon;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static ThreadLocal<RenderBlocksVoidstone> renderBlocksThreadLocal;

    private ISubmapManager overlay;
    private static TextureSubmap base;

    private final String texture;
    private final int meta;

    public SubmapManagerVoidstone(String texture, int meta) {
        this.texture = texture;
        this.meta = meta;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return overlay.getIcon(side, meta);
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return overlay.getIcon(world, x, y, z, side);
    }

    @Override
    public void registerIcons(String modName, Block block, IIconRegister register) {
        overlay = TextureType.getTypeFor(null, modName, texture)
            .createManagerFor(CarvingUtils.getDefaultVariationFor(block, meta, 0), texture);
        overlay.registerIcons(modName, block, register);
        base = new TextureSubmap(register.registerIcon(modName + ":" + "animations/hadesX32"), 2, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public RenderBlocks createRenderContext(RenderBlocks rendererOld, Block block, IBlockAccess world) {
        if (renderBlocksThreadLocal == null) {
            renderBlocksThreadLocal = ThreadLocal.withInitial(RenderBlocksVoidstone::new);
        }

        RenderBlocksVoidstone rb = renderBlocksThreadLocal.get();
        RenderBlocks ctx = overlay.createRenderContext(rendererOld, block, world);
        rb.setRenderBoundsFromBlock(block);

        if (ctx instanceof RenderBlocksCTM) {
            rb.submap = ((RenderBlocksCTM) ctx).submap;
            rb.submapSmall = ((RenderBlocksCTM) ctx).submapSmall;
        } else {
            rb.submap = null;
            rb.submapSmall = null;
        }

        return rb;
    }
}
