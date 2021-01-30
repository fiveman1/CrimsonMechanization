package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.blocks.CrusherBlock;
import fiveman1.crimsonmechanization.inventory.container.CrusherContainer;
import fiveman1.crimsonmechanization.inventory.container.MachineContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class CrusherScreen extends MachineScreen<CrusherContainer> {

    public CrusherScreen(MachineContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn, CrusherBlock.NAME);
    }
}
