package team.chisel.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

import com.cricketcraft.chisel.api.IChiselItem;
import com.cricketcraft.chisel.api.Statistics;

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
        ItemStack chiselInput = selInventory.inventory[InventoryChiselSelection.normalSlots];
        ItemStack heldStack = player.inventory.getItemStack();

        // Was chiseled by clicking 1-9 or Q, chisel only one item from the stack
        if (heldStack == null) {
            selInventory.decrStackSize(InventoryChiselSelection.normalSlots, 1);
            if (selInventory.inventory[InventoryChiselSelection.normalSlots] != null) {
                NBTTagCompound stackNBT = itemstack.getTagCompound();
                if (stackNBT != null) {
                    // Deep copy the resulting stack's NBT to prevent it from sharing
                    // the same NBT object reference with the unchiseled ItemStack
                    itemstack.setTagCompound((NBTTagCompound) stackNBT.copy());
                }
            }
        }

        // Was clicked normally (or shift-clicked), chisel the entire stack
        else {
            heldStack.stackSize = chiselInput.stackSize;
            selInventory.setInventorySlotContents(InventoryChiselSelection.normalSlots, null);
        }

        selInventory.updateItems();

        if (((IChiselItem) container.chisel.getItem())
            .onChisel(player.worldObj, container.chisel, General.getVariation(chiselInput))) {
            container.chisel.damageItem(1, player);
            if (container.chisel.stackSize <= 0) {
                player.setCurrentItemOrArmor(0, null);
            }
        }

        if (player.worldObj.isRemote) {
            String sound = Carving.chisel.getVariationSound(chiselInput.getItem(), chiselInput.getItemDamage());
            GeneralClient.playChiselSound(
                player.worldObj,
                MathHelper.floor_double(player.posX),
                MathHelper.floor_double(player.posY),
                MathHelper.floor_double(player.posZ),
                sound);
        } else {
            container.playerInventory.player.addStat(Statistics.blocksChiseled, itemstack.stackSize);
        }
    }
}
