package team.chisel.client.render;

import com.cricketcraft.chisel.api.rendering.ClientUtils;

import cpw.mods.fml.client.registry.RenderingRegistry;
import team.chisel.ctmlib.CTMRenderer;

public class RendererCTM extends CTMRenderer {

    public RendererCTM() {
        super(RenderingRegistry.getNextAvailableRenderId());
        ClientUtils.renderCTMId = getRenderId();
    }
}
