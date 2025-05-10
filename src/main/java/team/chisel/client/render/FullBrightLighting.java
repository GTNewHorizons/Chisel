package team.chisel.client.render;

import codechicken.lib.render.CCRenderState;

public class FullBrightLighting implements CCRenderState.IVertexOperation {

    public static FullBrightLighting instance = new FullBrightLighting();
    public int id;

    public boolean load() {
        final CCRenderState state = CCRenderState.instance();

        if (!state.computeLighting) {
            return false;
        }
        state.pipeline.addDependency(CCRenderState.instance().colourAttrib);
        state.pipeline.addDependency(CCRenderState.instance().lightCoordAttrib);
        return true;
    }

    public void operate() {
        CCRenderState.instance()
            .setBrightness(FULL_BRIGHT_BLOCK_BRIGHTNESS);
    }

    public int operationID() {
        return id;
    }

    {
        id = CCRenderState.registerOperation();
    }

    // magic number from 'RenderBlocksCTMFullbright'
    // this is the brightness of the neonite, anti, glotek blocks
    // so we use it for custom microblock lighting
    private final static int FULL_BRIGHT_BLOCK_BRIGHTNESS = 0xF000F0;
}
