package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.inventory.container.ContainerMachineUpgrades;
import fiveman1.crimsonmechanization.util.PacketUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiMachineUpgrades extends GuiContainerBase {

    // TODO: display the current machine speed increase and power mulitiplier

    private final ContainerMachineUpgrades container;

    public GuiMachineUpgrades(ContainerMachineUpgrades container, InventoryPlayer playerInv) {
        super(container, playerInv, "upgrades", 176, 166);
        this.container = container;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        super.initGui();
        CustomGuiButtonImage buttonBack = new CustomGuiButtonImage(0, guiLeft + 151, guiTop + 4, 16, 16, 0, 0,
                new ResourceLocation(CrimsonMechanization.MODID, "textures/gui/buttons.png"));
        buttonList.add(buttonBack);
        tooltipHash.put(buttonBack.id, "info.crimsonmechanization.button_back");
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            PacketUtil.sendButtonPacket(container.getTileMachine(), 0);
        }
    }
}
