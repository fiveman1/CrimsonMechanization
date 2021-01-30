package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.tile.AbstractMachineTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class UpgradeContainer extends AbstractBaseContainer {

    private final AbstractMachineTile machine;

    public UpgradeContainer(int windowID, PlayerInventory playerInventory, AbstractMachineTile te) {
        super(windowID, playerInventory, ContainerRegistration.UPGRADE_CONTAINER);
        this.machine = te;
        addSlots();
        addInventorySlots(8, 84);
    }

    private void addSlots() {
        IItemHandler itemHandler = machine.getUpgradeHandler();
        for (int i = 0; i < 4; i++) {
            addSlot(new SlotItemHandler(itemHandler, i, 53 + i * 18, 36));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return machine.isUsableByPlayer(playerIn);
    }

    public AbstractMachineTile getMachine() {
        return machine;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
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
            } else if (!this.mergeItemStack(currentItemStack, 0, 4, false)) {
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
