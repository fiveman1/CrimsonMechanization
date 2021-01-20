package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.blocks.ModBlocks;
import fiveman1.crimsonmechanization.inventory.container.ContainerMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCompactor extends GuiMachine {

    public GuiCompactor(ContainerMachine container, InventoryPlayer playerInv) {
        super(container, playerInv, ModBlocks.blockCompactor.getName());
    }
}
