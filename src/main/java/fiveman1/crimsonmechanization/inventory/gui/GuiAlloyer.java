package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.tile.TileAlloyer;
import fiveman1.crimsonmechanization.tile.TileMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiAlloyer extends GuiMachine{

    private final TileAlloyer te;

    public GuiAlloyer(TileAlloyer tileAlloyer, Container container, InventoryPlayer playerInv, String name, int width, int height) {
        super(container, playerInv, name, width, height);
        te = tileAlloyer;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        drawProgressBar(te.getField(TileMachine.PROGRESS_ID), te.getField(TileMachine.RECIPE_ENERGY_ID));
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
