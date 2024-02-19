package team.chisel.ctmlib;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * A convenience base class for CTM blocks. Any block using this must implement {@link ICTMBlock}.
 */
public class CTMRenderer implements ISimpleBlockRenderingHandler {

    private int renderId;

    public CTMRenderer(int renderId) {
        this.renderId = renderId;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        RenderBlocks rb = getContext(
            renderer,
            block,
            Minecraft.getMinecraft().theWorld,
            ((ICTMBlock<?>) block).getManager(metadata),
            metadata);
        Drawing.drawBlock(block, metadata, rb);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        rb.unlockBlockBounds();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks rendererOld) {
        try {
            int meta = world.getBlockMetadata(x, y, z);
            RenderBlocks rb = getContext(
                rendererOld,
                block,
                world,
                ((ICTMBlock<?>) block).getManager(world, x, y, z, meta),
                meta);
            boolean ret = rb.renderStandardBlock(block, x, y, z);
            rb.unlockBlockBounds();
            return ret;
        } catch (Throwable t) {
            CrashReport crashreport = CrashReport.makeCrashReport(t, "Rendering CTM Block");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being rendered");
            crashreportcategory.addCrashSection("Block name", GameRegistry.findUniqueIdentifierFor(block));
            crashreportcategory.addCrashSection("Block metadata", world.getBlockMetadata(x, y, z));
            throw new ReportedException(crashreport);
        }
    }

    protected RenderBlocks getContext(RenderBlocks rendererOld, Block block, IBlockAccess world, ISubmapManager manager,
        int meta) {
        if (!rendererOld.hasOverrideBlockTexture() && manager != null) {
            RenderBlocks rb = manager.createRenderContext(rendererOld, block, world);
            if (rb != null && rb != rendererOld) {
                rb.blockAccess = world;
                if (rendererOld.lockBlockBounds) {
                    rb.overrideBlockBounds(
                        rendererOld.renderMinX,
                        rendererOld.renderMinY,
                        rendererOld.renderMinZ,
                        rendererOld.renderMaxX,
                        rendererOld.renderMaxY,
                        rendererOld.renderMaxZ);
                }
                if (rb instanceof RenderBlocksCTM) {
                    RenderBlocksCTM rbctm = (RenderBlocksCTM) rb;
                    rbctm.manager = rbctm.manager == null ? manager : rbctm.manager;
                    rbctm.rendererOld = rbctm.rendererOld == null ? rendererOld : rbctm.rendererOld;
                }
                return rb;
            }
        }
        return rendererOld;
    }

    @Override
    public boolean shouldRender3DInInventory(int renderId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return renderId;
    }
}
