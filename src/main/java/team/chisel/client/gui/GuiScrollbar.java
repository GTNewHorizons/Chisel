package team.chisel.client.gui;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import team.chisel.inventory.ContainerChisel;
import team.chisel.network.PacketHandler;
import team.chisel.network.message.MessageScrollUpdate;

public class GuiScrollbar {

    public static final ResourceLocation TEXTURE_RESOURCE_LOCATION = new ResourceLocation(
        "minecraft",
        "textures/gui/container/creative_inventory/tabs.png");

    private int displayX = 0;
    private int displayY = 0;
    private int width = 12;
    private int height = 15;

    private int rowSize = 10;
    private int rowsOnPage = 6;

    private int scrollHeight = height;
    private ContainerChisel container;

    public void draw(GuiChisel g) {
        // chisel2GuiScroll scroll button texture coordinates work strange (maybe because of alignment) so taking
        // vanilla one
        g.mc.getTextureManager()
            .bindTexture(TEXTURE_RESOURCE_LOCATION);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        if (getMaxScroll() == 0) {
            g.drawTexturedModalRect(displayX, displayY, 232 + width, 0, width, height);
        } else {
            int offset = getCurrentScroll() * (scrollHeight - height) / getMaxScroll();
            g.drawTexturedModalRect(displayX, offset + displayY, 232, 0, width, height);
        }
    }

    public GuiScrollbar setScrollHeight(int scrollHeight) {
        this.scrollHeight = scrollHeight;
        return this;
    }

    public GuiScrollbar setLeft(int displayX) {
        this.displayX = displayX;
        return this;
    }

    public GuiScrollbar setTop(int displayY) {
        this.displayY = displayY;
        return this;
    }

    private int getMaxScroll() {
        int maxScroll = (container.inventory.getMaxScroll() + rowSize - 1) / rowSize - rowsOnPage;

        if (maxScroll <= 0) {
            maxScroll = 0;
        }

        return maxScroll;
    }

    public int getCurrentScroll() {
        return container.inventory.getCurrentScroll() / rowSize;
    }

    private void setCurrentScroll(int scroll) {
        container.inventory.setCurrentScroll(scroll * rowSize);
        PacketHandler.INSTANCE.sendToServer(new MessageScrollUpdate(scroll * rowSize));
    }

    private int applyRange(int scroll) {
        return Math.max(Math.min(scroll, getMaxScroll()), 0);
    }

    void click(int x, int y) {
        if (getMaxScroll() == 0) {
            return;
        }

        if (x > displayX && x <= displayX + width) {
            if (y > displayY && y <= displayY + scrollHeight) {
                int currentScroll = (y - displayY - height / 2);
                currentScroll = Math.round(1.0f * currentScroll * getMaxScroll() / (scrollHeight - height));
                setCurrentScroll(applyRange(currentScroll));
            }
        }
    }

    void wheel(int delta) {
        setCurrentScroll(applyRange(getCurrentScroll() + Math.max(Math.min(-delta, 1), -1)));
    }

    public void setContainerChisel(ContainerChisel container) {
        this.container = container;
    }
}
