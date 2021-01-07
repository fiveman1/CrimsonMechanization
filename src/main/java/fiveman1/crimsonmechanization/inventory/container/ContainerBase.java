package fiveman1.crimsonmechanization.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public abstract class ContainerBase extends Container {

    public ContainerBase(IInventory playerInventory, int xOffsetInventory, int yOffsetInventory) {
        //size: amount of slots (not including inventory)
        //xOffsetInventory: x position of top left pixel of inventory slots
        //yOffsetInventory: y position of top left pixel of inventory slots
        addSlots();
        addInventorySlots(playerInventory, xOffsetInventory, yOffsetInventory);
    }

    protected abstract void addSlots();

    protected void addInventorySlots(IInventory playerInventory, int xOffset, int yOffset) {
        int x;
        int y;
        //hotbar
        y = yOffset + 58;
        for (int col = 0; col < 9; col++) {
            x = xOffset + (col * 18);
            this.addSlotToContainer(new Slot(playerInventory, col, x, y));
        }
        //inventory
        for(int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                x = xOffset + (col * 18);
                y = yOffset + (row * 18);
                this.addSlotToContainer(new Slot(playerInventory, ((row + 1) * 9) + col + 9, x, y));
            }
        }
    }

}
