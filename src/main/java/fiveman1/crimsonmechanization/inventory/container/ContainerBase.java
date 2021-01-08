package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.tile.TileEntityBase;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public abstract class ContainerBase extends Container {

    public TileEntityBase te;

    public ContainerBase(IInventory playerInventory, TileEntityBase tileEntity, int xOffsetInventory, int yOffsetInventory) {
        te = tileEntity;
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
            addSlotToContainer(new Slot(playerInventory, col, x, y));
        }
        //inventory
        for(int row = 0; row < 3; row++) {
            y = yOffset + (row * 18);
            for (int col = 0; col < 9; col++) {
                x = xOffset + (col * 18);
                addSlotToContainer(new Slot(playerInventory, (row * 9) + col + 9, x, y));
            }
        }
    }

}
