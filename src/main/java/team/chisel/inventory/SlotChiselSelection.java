package team.chisel.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

import com.cricketcraft.chisel.api.IChiselItem;
import com.cricketcraft.chisel.api.Statistics;
import com.cricketcraft.chisel.api.carving.CarvingUtils;

import team.chisel.carving.Carving;
import team.chisel.utils.General;
import team.chisel.utils.GeneralClient;

public class SlotChiselSelection extends Slot {

    private final ContainerChisel container;
    private final InventoryChiselSelection selInventory;

    public SlotChiselSelection(ContainerChisel container, InventoryChiselSelection inv, IInventory iinventory, int i,
        int j, int k) {
        super(iinventory, i, j, k);

        this.container = container;
        selInventory = inv;
    }

    @Override
    public boolean isItemValid(ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        if (container.finished) return false;

        return par1EntityPlayer.inventory.getItemStack() == null;
    }

    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack itemstack) {
        ItemStack chiselInputStack = selInventory.inventory[InventoryChiselSelection.normalSlots];

        if (itemstack == null) {
            selInventory.decrStackSize(InventoryChiselSelection.normalSlots, 1);
        } else {
            if (chiselInputStack == null) {
                player.inventory.setItemStack(null);
                return;
            }

            // Perform chisel crafting operation
            ItemStack chiselingOutput = CarvingUtils.tryChiselingInto(chiselInputStack, itemstack);
            player.inventory.setItemStack(chiselingOutput);

            // Clear the input stack
            selInventory.setInventorySlotContents(InventoryChiselSelection.normalSlots, null);
        }

        selInventory.updateItems();

        // Damage and possibly break the chisel
        if (((IChiselItem) container.chisel.getItem())
            .onChisel(player.worldObj, container.chisel, General.getVariation(chiselInputStack))) {
            container.chisel.damageItem(1, player);
            if (container.chisel.stackSize <= 0) {
                player.setCurrentItemOrArmor(0, null);
            }
        }

        if (player.worldObj.isRemote) {
            String sound = Carving.chisel
                .getVariationSound(chiselInputStack.getItem(), chiselInputStack.getItemDamage());
            GeneralClient.playChiselSound(
                player.worldObj,
                MathHelper.floor_double(player.posX),
                MathHelper.floor_double(player.posY),
                MathHelper.floor_double(player.posZ),
                sound);
        } else {
            container.playerInventory.player.addStat(Statistics.blocksChiseled, chiselInputStack.stackSize);
        }
    }
}
