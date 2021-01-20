package fiveman1.crimsonmechanization.inventory.container;


import fiveman1.crimsonmechanization.inventory.slot.SlotOutput;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCrimsonFurnace extends ContainerMachine {

    public ContainerCrimsonFurnace(IInventory playerInventory, TileCrimsonFurnace tileCrimsonFurnace) {
        super(playerInventory, tileCrimsonFurnace);
    }

    @Override
    protected void addSlots() {
        IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        addSlotToContainer(new SlotItemHandler(itemHandler, 0, 46, 34));
        addSlotToContainer(new SlotOutput(itemHandler, 1, 116, 34));
    }
}
