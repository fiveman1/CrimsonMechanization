package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.blocks.AlloyerBlock;
import fiveman1.crimsonmechanization.inventory.container.AlloyerContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class AlloyerScreen extends MachineScreen<AlloyerContainer> {

    public AlloyerScreen(AlloyerContainer screenContainer, PlayerInventory inv, ITextComponent title) {
        super(screenContainer, inv, title, AlloyerBlock.NAME);
    }
}
