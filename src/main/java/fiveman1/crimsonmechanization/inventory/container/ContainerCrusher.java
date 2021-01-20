package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.inventory.slot.SlotOutput;
import fiveman1.crimsonmechanization.tile.TileMachine;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCrusher extends ContainerMachine {
    public ContainerCrusher(IInventory playerInventory, TileMachine tileEntity) {
        super(playerInventory, tileEntity);
    }

    @Override
    protected void addSlots() {
        IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        addSlotToContainer(new SlotItemHandler(itemHandler, 0, 46, 34));
        addSlotToContainer(new SlotOutput(itemHandler, 1, 116, 34));
        addSlotToContainer(new SlotOutput(itemHandler, 2, 140, 34));
    }
}
