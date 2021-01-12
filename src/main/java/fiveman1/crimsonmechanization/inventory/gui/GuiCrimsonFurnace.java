package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.inventory.container.ContainerCrimsonFurnace;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import fiveman1.crimsonmechanization.tile.TileMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCrimsonFurnace extends GuiMachine {

    private final TileCrimsonFurnace te;

    public GuiCrimsonFurnace(TileCrimsonFurnace te, ContainerCrimsonFurnace container, InventoryPlayer playerInv, String name, int width, int height) {
        super(container, playerInv, name, width, height);
        this.te = te;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        drawProgressBar(te.getField(TileMachine.PROGRESS_ID), te.getRecipeEnergy());
        drawEnergyBar(te.getField(TileMachine.ENERGY_ID), te.getField(TileMachine.CAPACITY_ID));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (isMouseOverEnergyBar(mouseX, mouseY)) {
            drawHoveringText(te.getField(TileMachine.ENERGY_ID) + " RF", mouseX, mouseY);
        }
    }
}
