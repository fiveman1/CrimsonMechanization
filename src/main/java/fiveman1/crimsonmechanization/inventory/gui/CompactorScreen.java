package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import fiveman1.crimsonmechanization.blocks.CompactorMachineBlock;
import fiveman1.crimsonmechanization.inventory.container.CompactorContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class CompactorScreen extends MachineScreen<CompactorContainer> {

    public CompactorScreen(CompactorContainer screenContainer, PlayerInventory inv, ITextComponent title) {
        super(screenContainer, inv, title, CompactorMachineBlock.NAME);
    }
}
