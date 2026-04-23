package team.chisel.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;

import com.cricketcraft.chisel.api.carving.CarvableHelper;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import team.chisel.config.Configurations;

public class BlockCarvableConcreteStairs extends BlockCarvableStairs {

    public BlockCarvableConcreteStairs(Block block, int meta, CarvableHelper helper) {
        super(block, meta, helper);

        FMLCommonHandler.instance()
            .bus()
            .register(new EventHandler());
    }

    // Override super's getSubBlocks to allow concrete's 11 variants
    // Stairs are added to the list in pairs, so odd-numbered variant counts create
    // a block that shouldn't exist. This modification makes the block unreachable by normal
    // means (NEI, creative menu), but still technically exists in-game
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item block, CreativeTabs tabs, List list) {
        list.add(new ItemStack(block, 1, 0));

        // If odd# of variations && meta for the current block == variation.size - 1, exit
        // Second check checks if the current block is at the end of the variation list
        if (carverHelper.infoList.size() % 2 == 1 && this.blockMeta == carverHelper.infoList.size() - 1) return;
        list.add(new ItemStack(block, 1, 8));
    }

    @SideOnly(Side.CLIENT)
    private static MovementInput manualInputCheck;

    public class EventHandler {

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public void speedupPlayer(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.START && event.side.isClient()
                && event.player.onGround
                && event.player instanceof EntityPlayerSP) {
                if (manualInputCheck == null) {
                    manualInputCheck = new MovementInputFromOptions(Minecraft.getMinecraft().gameSettings);
                }
                EntityPlayerSP player = (EntityPlayerSP) event.player;
                Block below = player.worldObj.getBlock(
                    MathHelper.floor_double(player.posX),
                    MathHelper.floor_double(player.posY) - 2,
                    MathHelper.floor_double(player.posZ));

                if (below == BlockCarvableConcreteStairs.this) {
                    manualInputCheck.updatePlayerMoveState();
                    if (manualInputCheck.moveForward != 0 || manualInputCheck.moveStrafe != 0) {
                        player.motionX *= Configurations.concreteVelocityMult + 0.05;
                        player.motionZ *= Configurations.concreteVelocityMult + 0.05;
                    }
                }
            }
        }
    }
}
