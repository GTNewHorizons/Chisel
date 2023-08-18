package team.chisel.client.gui.widgets;

import static team.chisel.client.gui.GuiChisel.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import team.chisel.client.gui.GuiChisel;
import team.chisel.item.chisel.ChiselMode;

@SideOnly(Side.CLIENT)
public class GuiButtonChiselMode extends GuiButton {

    public static final int BUTTON_WIDTH = 48;
    public static final int BUTTON_HEIGHT = 48;
    private static final float BUTTON_BACKGROUND_U = 48;
    private static final float BUTTON_BACKGROUND_V = 202;
    private static final float MODE_VIGNETTE_U = BUTTON_BACKGROUND_U + 2 * BUTTON_WIDTH + 3;
    private static final float MODE_VIGNETTE_V = BUTTON_BACKGROUND_V + 3;
    private static final int MODE_VIGNETTE_WIDTH = 14;
    private static final int MODE_VIGNETTE_ROW_WIDTH = 3 * MODE_VIGNETTE_WIDTH;
    private static final int MODE_VIGNETTE_HEIGHT = 14;
    private static final int MODE_VIGNETTE_COL_HEIGHT = 3 * MODE_VIGNETTE_HEIGHT;
    private final GuiChisel guiChisel;

    public GuiButtonChiselMode(int id, int x, int y, GuiChisel guiChisel) {
        super(id, x, y, BUTTON_WIDTH, BUTTON_HEIGHT, "");
        this.guiChisel = guiChisel;
    }

    /**
     * Draws this button to the screen.
     *
     * @param mc     The Minecraft instance
     * @param mouseX The horizontal location of the mouse within the GuiScreen
     * @param mouseY The vertical location of the mouse relative to the GuiScreen
     */
    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            mc.getTextureManager()
                .bindTexture(TEXTURE_RESOURCE_LOCATION);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            // Mouse Hover State
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition
                && mouseX < this.xPosition + this.width
                && mouseY < this.yPosition + this.height;
            drawButtonBackgroundLayer();
            drawButtonForegroundLayer();
        }
    }

    private void drawButtonBackgroundLayer() {
        final float u = BUTTON_BACKGROUND_U + (this.field_146123_n ? BUTTON_WIDTH : 0);
        // drawModalRectWithCustomSizedTexture: x, y, u, v, width, height, textureWidth, textureHeight
        func_146110_a(
            this.xPosition,
            this.yPosition,
            u,
            BUTTON_BACKGROUND_V,
            this.width,
            this.height,
            TEXTURE_WIDTH,
            TEXTURE_HEIGHT);
    }

    private void drawButtonForegroundLayer() {
        final int xBase = this.xPosition + 3;
        final int yBase = this.yPosition + 3;
        final float uBase = MODE_VIGNETTE_U + (this.field_146123_n ? BUTTON_WIDTH : 0);
        final float u;
        final float v;
        final int w;
        final int h;
        final int x;
        final int y;
        switch ((ChiselMode) guiChisel.getCurrentMode()) {
            case SINGLE:
                x = xBase + MODE_VIGNETTE_WIDTH;
                y = yBase + MODE_VIGNETTE_HEIGHT;
                u = uBase + MODE_VIGNETTE_WIDTH;
                v = MODE_VIGNETTE_V + MODE_VIGNETTE_HEIGHT;
                w = MODE_VIGNETTE_WIDTH;
                h = MODE_VIGNETTE_HEIGHT;
                break;
            case ROW:
                x = xBase;
                y = yBase + MODE_VIGNETTE_HEIGHT;
                u = uBase;
                v = MODE_VIGNETTE_V + MODE_VIGNETTE_HEIGHT;
                w = MODE_VIGNETTE_ROW_WIDTH;
                h = MODE_VIGNETTE_HEIGHT;
                break;
            case COLUMN:
                x = xBase + MODE_VIGNETTE_WIDTH;
                y = yBase;
                u = uBase + MODE_VIGNETTE_WIDTH;
                v = MODE_VIGNETTE_V;
                w = MODE_VIGNETTE_WIDTH;
                h = MODE_VIGNETTE_COL_HEIGHT;
                break;
            case PANEL:
                x = xBase;
                y = yBase;
                u = uBase;
                v = MODE_VIGNETTE_V;
                w = MODE_VIGNETTE_ROW_WIDTH;
                h = MODE_VIGNETTE_COL_HEIGHT;
                break;
            default:
                return;
        }
        // drawModalRectWithCustomSizedTexture: x, y, u, v, width, height, textureWidth, textureHeight
        func_146110_a(x, y, u, v, w, h, TEXTURE_WIDTH, TEXTURE_HEIGHT);
    }
}
