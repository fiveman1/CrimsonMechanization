package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.tile.TileMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineUpgrades extends ContainerBase {

    public ContainerMachineUpgrades(IInventory playerInventory, TileMachine tileEntity) {
        super(playerInventory, tileEntity, 8, 84);
    }

    public TileMachine getTileMachine() {
        return (TileMachine) te;
    }

    @Override
    protected void addSlots() {
        IItemHandler itemHandler = getTileMachine().getUpgradeHandler();
        for (int i = 0; i < 4; i++) {
            addSlotToContainer(new SlotItemHandler(itemHandler, i, 53 + i * 18, 36));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        TileMachine machine = getTileMachine();
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack currentItemStack = slot.getStack();
            itemstack = currentItemStack.copy();

            if (index < 4) {
                if (!this.mergeItemStack(currentItemStack, 4, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(currentItemStack, itemstack);
            } else if (!this.mergeItemStack(currentItemStack, 0, machine.getInputSlots(), false)) {
                return ItemStack.EMPTY;
            }
            if (currentItemStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            slot.onTake(playerIn, currentItemStack);
        }

        return itemstack;
    }


}
