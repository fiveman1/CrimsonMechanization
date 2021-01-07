package fiveman1.crimsonmechanization.inventory.container;


import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerCrimsonFurnace extends ContainerBase {

    public TileCrimsonFurnace te;

    public ContainerCrimsonFurnace(IInventory playerInventory, TileCrimsonFurnace tileCrimsonFurnace, int xOffsetInventory, int yOffsetInventory) {
        super(playerInventory, xOffsetInventory, yOffsetInventory);
        te = tileCrimsonFurnace;
    }

    @Override
    protected void addSlots() {

    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }
}
