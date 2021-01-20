package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.inventory.container.ContainerMachineUpgrades;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMachineUpgrades extends GuiBase {

    public GuiMachineUpgrades(ContainerMachineUpgrades container, InventoryPlayer playerInv) {
        super(container, playerInv, "upgrades", 176, 166);
    }
}
