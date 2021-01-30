package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.inventory.slot.SlotOutput;
import fiveman1.crimsonmechanization.tile.AlloyerTile;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AlloyerContainer extends MachineContainer {
    public AlloyerContainer(int windowID, PlayerInventory playerInventory, AlloyerTile machine) {
        super(windowID, playerInventory, machine, ContainerRegistration.ALLOYER_CONTAINER);
    }

    @Override
    protected void addSlots() {
        IItemHandler itemHandler = machineTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
        addSlot(new SlotItemHandler(itemHandler, 0, 22, 34));
        addSlot(new SlotItemHandler(itemHandler, 1, 46, 34));
        addSlot(new SlotOutput(itemHandler, 2, 116, 34));
    }
}
