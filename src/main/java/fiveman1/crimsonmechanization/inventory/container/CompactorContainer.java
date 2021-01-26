package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.inventory.slot.SlotOutput;
import fiveman1.crimsonmechanization.tile.CompactorTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.concurrent.atomic.AtomicReference;

public class CompactorContainer extends MachineContainer {
    public CompactorContainer(int windowID, PlayerInventory playerInventory, CompactorTile machine) {
        super(windowID, playerInventory, machine, ContainerRegistration.compactorContainer);
    }

    @Override
    protected void addSlots() {
        AtomicReference<IItemHandler> itemHandler = new AtomicReference<>();
        machineTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(itemHandler::set);
        addSlot(new SlotItemHandler(itemHandler.get(), 0, 46, 34));
        addSlot(new SlotOutput(itemHandler.get(), 1, 116, 34));
    }
}
