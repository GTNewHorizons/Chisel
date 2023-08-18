package team.chisel.network.message;

import net.minecraft.inventory.Container;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import team.chisel.inventory.ContainerChisel;

public class MessageScrollUpdate implements IMessage {

    private int currentScroll;

    public MessageScrollUpdate() {}

    public MessageScrollUpdate(int scroll) {
        currentScroll = scroll;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        currentScroll = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(currentScroll);
    }

    public static class Handler implements IMessageHandler<MessageScrollUpdate, IMessage> {

        @Override
        public IMessage onMessage(MessageScrollUpdate message, MessageContext ctx) {
            Container container = ctx.getServerHandler().playerEntity.openContainer;
            if (container instanceof ContainerChisel) {
                ((ContainerChisel) container).inventory.setCurrentScroll(message.currentScroll);
            }
            return null;
        }
    }
}
