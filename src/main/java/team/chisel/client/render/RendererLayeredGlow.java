package team.chisel.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import team.chisel.Chisel;
import team.chisel.block.BlockCarvableGlow;
import team.chisel.config.Configurations;
import team.chisel.ctmlib.Drawing;
import team.chisel.utils.GeneralClient;

@ThreadSafeISBRH(perThread = false)
public class RendererLayeredGlow implements ISimpleBlockRenderingHandler {

    public RendererLayeredGlow() {
        Chisel.renderGlowId = RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GeneralClient.setGLColorFromInt(Configurations.configColors[metadata]);
        GL11.glDisable(GL11.GL_LIGHTING);
        Drawing.drawBlock(block, ((BlockCarvableGlow) block).getGlowTexture(), renderer);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glColor3f(1, 1, 1);
        Drawing.drawBlock(block, metadata, renderer);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        final Tessellator tessellator = Tessellator.instance;
        final boolean prevEnableAO = renderer.enableAO;
        final int tint = Configurations.configColors[world.getBlockMetadata(x, y, z)];
        tessellator.setColorOpaque_I(tint);
        tessellator.setBrightness(0xF000F0);
        setFullbrightColor(renderer, tint);
        Drawing.renderAllFaces(renderer, block, x, y, z, ((BlockCarvableGlow) block).getGlowTexture());
        renderer.enableAO = prevEnableAO;
        renderer.renderStandardBlock(block, x, y, z);
        return true;
    }

    private static void setFullbrightColor(RenderBlocks renderer, int color) {
        final float r = (color >> 16 & 255) / 255.0F;
        final float g = (color >> 8 & 255) / 255.0F;
        final float b = (color & 255) / 255.0F;
        renderer.enableAO = true;
        renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = r;
        renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = g;
        renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = b;
        renderer.brightnessTopLeft = renderer.brightnessBottomLeft = renderer.brightnessTopRight = renderer.brightnessBottomRight = 0xF000F0;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Chisel.renderGlowId;
    }
}
