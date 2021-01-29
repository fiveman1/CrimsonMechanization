package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.blocks.FurnaceBlock;
import fiveman1.crimsonmechanization.inventory.container.FurnaceContainer;
import fiveman1.crimsonmechanization.inventory.container.MachineContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class FurnaceScreen extends MachineScreen<FurnaceContainer> {

    public FurnaceScreen(MachineContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn, FurnaceBlock.NAME);
    }
}
