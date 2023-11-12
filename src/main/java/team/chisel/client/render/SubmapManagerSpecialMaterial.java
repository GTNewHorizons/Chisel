package team.chisel.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import team.chisel.ctmlib.RenderBlocksCTM;
import team.chisel.ctmlib.TextureSubmap;

public class SubmapManagerSpecialMaterial extends SubmapManagerBase {

    public enum MaterialType {
        NEONITE,
        GLOTEK
    }

    private static class RenderBlocksCTMFullbright extends RenderBlocksCTM {

        @Override
        protected void fillLightmap(int bottomLeft, int bottomRight, int topRight, int topLeft) {
            enableAmbientOcclusionIfInWorld();
            int maxLight = 0xF000F0;
            super.fillLightmap(maxLight, maxLight, maxLight, maxLight);
        }

        @Override
        protected void fillColormap(float bottomLeft, float bottomRight, float topRight, float topLeft, float[][] map) {
            enableAmbientOcclusionIfInWorld();
            int color = 0xFFFFFF;
            super.fillColormap(color, color, color, color, map);
        }

        @Override
        public boolean renderStandardBlock(Block block, int x, int y, int z) {
            boolean ret = super.renderStandardBlock(block, x, y, z);
            this.enableAO = false;
            return ret;
        }

        private void enableAmbientOcclusionIfInWorld() {
            if (this.inWorld) {
                this.enableAO = true;
            }
        }
    };

    private RenderBlocksCTMFullbright renderBlocksFullbright;

    private String color;
    private MaterialType materialType;
    private TextureSubmap submap, submapSmall;

    public SubmapManagerSpecialMaterial(String color, MaterialType materialType) {
        this.color = color;
        this.materialType = materialType;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return submapSmall.getBaseIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(String modName, Block block, IIconRegister register) {
        String materialName = materialType.name()
            .toLowerCase();
        submap = new TextureSubmap(register.registerIcon(modName + ":" + materialName + "/" + color + "-ctm"), 4, 4);
        submapSmall = new TextureSubmap(register.registerIcon(modName + ":" + materialName + "/" + color), 2, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public RenderBlocks createRenderContext(RenderBlocks rendererOld, Block block, IBlockAccess world) {
        if (renderBlocksFullbright == null) {
            renderBlocksFullbright = new RenderBlocksCTMFullbright();
        }
        renderBlocksFullbright.setRenderBoundsFromBlock(block);
        renderBlocksFullbright.submap = submap;
        renderBlocksFullbright.submapSmall = submapSmall;
        return renderBlocksFullbright;
    }

}
