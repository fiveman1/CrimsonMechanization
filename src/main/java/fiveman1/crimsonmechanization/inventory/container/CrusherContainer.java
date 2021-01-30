package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.inventory.slot.SlotOutput;
import fiveman1.crimsonmechanization.tile.AbstractMachineTile;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CrusherContainer extends MachineContainer {
    public CrusherContainer(int windowID, PlayerInventory playerInventory, AbstractMachineTile machine) {
        super(windowID, playerInventory, machine, ContainerRegistration.CRUSHER_CONTAINER);
    }

    @Override
    protected void addSlots() {
        IItemHandler itemHandler = machineTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
        addSlot(new SlotItemHandler(itemHandler, 0, 46, 34));
        addSlot(new SlotOutput(itemHandler, 1, 116, 34));
        addSlot(new SlotOutput(itemHandler, 2, 140, 34));
    }
}
