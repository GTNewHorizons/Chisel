package team.chisel.item.chisel;

import java.util.List;
import java.util.Queue;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.oredict.OreDictionary;

import com.cricketcraft.chisel.api.IChiselItem;
import com.cricketcraft.chisel.api.carving.ICarvingGroup;
import com.cricketcraft.chisel.api.carving.ICarvingVariation;
import com.cricketcraft.chisel.api.carving.IChiselMode;
import com.google.common.collect.Queues;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import team.chisel.Chisel;
import team.chisel.carving.Carving;
import team.chisel.client.render.SubmapManagerSpecialMaterial;
import team.chisel.utils.General;

public final class ChiselController {

    protected static class GuiOpen implements Runnable {

        private EntityPlayer player;
        private ItemStack stack;

        public GuiOpen(EntityPlayer player, ItemStack stack) {
            super();
            this.player = player;
            this.stack = stack;
        }

        @Override
        public void run() {
            ItemStack current = player.getCurrentEquippedItem();
            if (current != null) {
                if (ItemStack.areItemStacksEqual(stack, current) && ItemStack.areItemStackTagsEqual(stack, current)) {
                    player.openGui(Chisel.instance, 0, player.worldObj, 0, 0, 0);
                }
            }
        }
    }

    public static final ChiselController INSTANCE = new ChiselController();

    Queue<GuiOpen> openQueue = Queues.newArrayDeque();

    private ChiselController() {}

    public void preInit() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance()
            .bus()
            .register(this);
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        ItemStack held = event.entityPlayer.getCurrentEquippedItem();
        int slot = event.entityPlayer.inventory.currentItem;

        // Prevents a crash when activated from a Battlegear slot.
        if (slot > 8 || slot < 0) {
            return;
        }

        if (held == null || !(held.getItem() instanceof IChiselItem)) {
            return;
        }

        IChiselItem chisel = (IChiselItem) held.getItem();

        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) {
            boolean shifting = event.entityPlayer.isSneaking();
            int x = event.x, y = event.y, z = event.z;
            Block block = event.world.getBlock(x, y, z);
            int metadata = event.world.getBlockMetadata(x, y, z);
            ICarvingGroup group = Carving.chisel.getGroup(block, metadata);

            if (group == null) {
                return;
            }

            TileEntity te = event.world.getTileEntity(x, y, z);
            if (te != null) {
                // Don't support chiseling tile entities
                return;
            }

            List<ICarvingVariation> list = group.getVariations();

            main: for (ItemStack stack : OreDictionary.getOres(group.getOreName())) {
                ICarvingVariation v = General.getVariation(stack);
                for (ICarvingVariation check : list) {
                    if (check.getBlock() == v.getBlock() && check.getBlockMeta() == v.getBlockMeta()) {
                        continue main;
                    }
                }
                list.add(General.getVariation(stack));
            }

            ICarvingVariation[] variations = list.toArray(new ICarvingVariation[] {});

            if (chisel.canChiselBlock(event.world, event.entityPlayer, x, y, z, block, metadata)) {
                ItemStack target = General.getChiselTarget(held);
                IChiselMode mode = General.getChiselMode(held);
                ForgeDirection sideHit = ForgeDirection.VALID_DIRECTIONS[event.face];

                if (target != null) {
                    for (ICarvingVariation v : variations) {
                        if (v.getBlock() == Block.getBlockFromItem(target.getItem())
                            && v.getBlockMeta() == target.getItemDamage()) {
                            mode.chiselAll(event.entityPlayer, event.world, x, y, z, sideHit, v);
                        }
                    }
                } else {
                    int idx = 0;
                    for (int i = 0; i < variations.length; i++) {
                        ICarvingVariation v = variations[i];
                        if (v.getBlock() == block && v.getBlockMeta() == metadata) {
                            idx = i + variations.length + (shifting ? -1 : 1);
                            idx %= variations.length;
                        }
                    }

                    ICarvingVariation newVar = variations[idx];
                    mode.chiselAll(event.entityPlayer, event.world, x, y, z, sideHit, newVar);
                    event.entityPlayer.inventory.currentItem = slot;
                }
            }
        }
    }

    // This still shows the block being broken, but it's better than nothing
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        ItemStack stack = event.getPlayer()
            .getCurrentEquippedItem();
        if (event.getPlayer().capabilities.isCreativeMode && stack != null && stack.getItem() instanceof IChiselItem) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void openQueuedGui(ServerTickEvent event) {
        if (event.phase == Phase.END) {
            GuiOpen open = openQueue.poll();
            if (open != null) {
                open.run();
            }
        }
    }

}
