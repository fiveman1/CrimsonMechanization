package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.tile.TileCompactor;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class GuiCompactor extends GuiMachine{

    private final TileCompactor te;

    public GuiCompactor(TileCompactor tileCompactor, Container container, InventoryPlayer playerInv, String name, int width, int height) {
        super(container, playerInv, name, width, height);
        te = tileCompactor;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        drawProgressBar(te.getField(0), te.MAX_PROGRESS);
        drawEnergyBar(te.getField(1), te.getField(2));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (isMouseOverEnergyBar(mouseX, mouseY)) {
            drawHoveringText(te.getField(1) + " RF", mouseX, mouseY);
        }
    }
}
