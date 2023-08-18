package team.chisel.client.gui;

import static team.chisel.Chisel.MOD_ID;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.cricketcraft.chisel.api.IAdvancedChisel;
import com.cricketcraft.chisel.api.IChiselItem;
import com.cricketcraft.chisel.api.carving.IChiselMode;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import team.chisel.client.gui.widgets.GuiButtonChiselMode;
import team.chisel.inventory.ContainerChisel;
import team.chisel.inventory.InventoryChiselSelection;
import team.chisel.inventory.SlotChiselInput;
import team.chisel.item.chisel.ChiselMode;
import team.chisel.network.PacketHandler;
import team.chisel.network.message.MessageChiselMode;
import team.chisel.utils.General;

@SideOnly(Side.CLIENT)
public class GuiChisel extends GuiContainer {

    public static final ResourceLocation TEXTURE_RESOURCE_LOCATION = new ResourceLocation(
        MOD_ID,
        "textures/chisel2GuiScroll.png");
    public static final int TEXTURE_WIDTH = 266;
    public static final int TEXTURE_HEIGHT = 250;
    public static final int GUI_WIDTH = 266;
    public static final int GUI_HEIGHT = 202;
    public static final int CHISEL_INPUT_SIZE = 48;
    private EntityPlayer player;
    private ContainerChisel container;
    private IChiselMode currentMode;
    private GuiScrollbar scrollBar;
    private InventoryChiselSelection menu;

    public GuiChisel(InventoryPlayer iinventory, InventoryChiselSelection menu) {
        super(new ContainerChisel(iinventory, menu));
        scrollBar = new GuiScrollbar();
        this.menu = menu;
        player = iinventory.player;
        xSize = GUI_WIDTH;
        ySize = GUI_HEIGHT;

        container = (ContainerChisel) inventorySlots;
        scrollBar.setContainerChisel(container);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        inventorySlots.onContainerClosed(player);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {
        super.initGui();
        currentMode = General.getChiselMode(container.chisel);

        if (showMode()) {
            buttonList.add(new GuiButtonChiselMode(0, guiLeft + 12, guiTop + 132, this));
        }

        scrollBar.setLeft(246);
        scrollBar.setTop(8);
        scrollBar.setScrollHeight(106);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        final ItemStack held = player.getCurrentEquippedItem();
        if (held == null || !(held.getItem() instanceof IChiselItem)) {
            mc.displayGuiScreen(null);
        }
    }

    private boolean showMode() {
        return container.chisel != null && container.chisel.getItem() instanceof IChiselItem
            && ((IChiselItem) container.chisel.getItem()).hasModes(container.chisel);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(final int mouseX, final int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        final String titleText = I18n.format(container.inventory.getInventoryName() + ".title");
        fontRendererObj
            .drawSplitString(titleText, 50 - fontRendererObj.getStringWidth(titleText) / 2, 60, 40, 0x404040);

        if (showMode()) {
            final String modeText = I18n.format(container.inventory.getInventoryName() + ".mode");
            fontRendererObj.drawString(
                modeText,
                12 + (GuiButtonChiselMode.BUTTON_WIDTH - fontRendererObj.getStringWidth(modeText)) / 2,
                122,
                0x404040);
        }

        scrollBar.draw(this);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(final float partialTicks, final int mouseX, final int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);

        mc.getTextureManager()
            .bindTexture(TEXTURE_RESOURCE_LOCATION);
        // drawModalRectWithCustomSizedTexture: x, y, u, v, width, height, textureWidth, textureHeight
        func_146110_a(guiLeft, guiTop, 0, 0, xSize, ySize, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        Slot main = (Slot) container.inventorySlots.get(InventoryChiselSelection.normalSlots);
        if (main.getStack() == null) {
            func_146110_a(
                guiLeft + 8,
                guiTop + 8,
                0,
                GUI_HEIGHT,
                CHISEL_INPUT_SIZE,
                CHISEL_INPUT_SIZE,
                TEXTURE_WIDTH,
                TEXTURE_HEIGHT);
        }
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 0) {
            if (container.chisel != null && container.chisel.getItem() instanceof IAdvancedChisel) {
                final IAdvancedChisel item = (IAdvancedChisel) container.chisel.getItem();
                currentMode = item.getNextMode(container.chisel, currentMode);
            } else {
                currentMode = ChiselMode.next(currentMode);
            }
            PacketHandler.INSTANCE.sendToServer(new MessageChiselMode(currentMode));
        }
        super.actionPerformed(button);
    }

    @Override
    protected void func_146977_a(final Slot slot) {
        if (slot instanceof SlotChiselInput) {
            GL11.glPushMatrix();
            GL11.glScalef(2, 2, 2);
            slot.xDisplayPosition -= 16;
            slot.yDisplayPosition -= 16;
            super.func_146977_a(slot);
            slot.xDisplayPosition += 16;
            slot.yDisplayPosition += 16;
            GL11.glPopMatrix();
        } else {
            super.func_146977_a(slot);
        }
    }

    @Override
    protected void mouseClickMove(int x, int y, int c, long d) {
        scrollBar.click(x - guiLeft, y - guiTop);
        super.mouseClickMove(x, y, c, d);
    }

    @Override
    protected void mouseClicked(int xCoord, int yCoord, int btn) {
        scrollBar.click(xCoord - guiLeft, yCoord - guiTop);
        super.mouseClicked(xCoord, yCoord, btn);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();

        int i = Mouse.getEventDWheel();
        if (i != 0 && scrollBar != null) {
            scrollBar.wheel(i);
        }
    }

    public IChiselMode getCurrentMode() {
        return currentMode;
    }
}
