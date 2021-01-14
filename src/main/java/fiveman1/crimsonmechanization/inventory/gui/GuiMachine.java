package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.inventory.container.ContainerMachine;
import fiveman1.crimsonmechanization.tile.TileMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class GuiMachine extends GuiBase {

    protected final ContainerMachine machineContainer;
    protected final TileMachine te;

    public GuiMachine(ContainerMachine container, InventoryPlayer playerInv, String name, int width, int height) {
        super(container, playerInv, name, width, height);
        machineContainer = container;
        te = machineContainer.getTileMachine();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (isMouseOverEnergyBar(mouseX, mouseY)) {
            drawHoveringText(te.getField(TileMachine.ENERGY_ID) + " / " + te.getField(TileMachine.CAPACITY_ID) + " RF", mouseX, mouseY);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        drawProgressBar(te.getField(TileMachine.PROGRESS_ID), te.getField(TileMachine.RECIPE_ENERGY_ID));
        drawEnergyBar(te.getField(TileMachine.ENERGY_ID), te.getField(TileMachine.CAPACITY_ID));
    }

    protected void drawProgressBar(int progress, int maxProgress) {
        if (progress > 0 && maxProgress != 0) {
            drawTexturedModalRect(guiLeft + 76, guiTop + 35, 176, 0, progress * 23 / maxProgress, 16);
        }
    }

    protected void drawEnergyBar(int energy, int capacity) {
        if (capacity > 0) {
            // background
            drawRect(guiLeft + 79, guiTop + 71, guiLeft + 167, guiTop + 78, 0xff404040);

            // energy bar
            int width = energy * (166 - 80) / capacity;
            drawGradientRect(guiLeft + 80, guiTop + 72, guiLeft + 80 + width, guiTop + 77, Color.white.getRGB(), Color.cyan.getRGB());

            // empty energy bar
            drawRect(guiLeft + 80 + width, guiTop + 72, guiLeft + 166, guiTop + 77, Color.black.getRGB());
        }
    }

    protected boolean isMouseOverEnergyBar(int mouseX, int mouseY) {
        return mouseX >= guiLeft + 79 && mouseY >= guiTop + 71 && mouseX < guiLeft + 167 && mouseY < guiTop + 78;
    }
}
