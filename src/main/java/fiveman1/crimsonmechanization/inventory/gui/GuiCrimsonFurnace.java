package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.inventory.container.ContainerCrimsonFurnace;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class GuiCrimsonFurnace extends GuiBase {

    private final TileCrimsonFurnace te;

    public GuiCrimsonFurnace(TileCrimsonFurnace te, ContainerCrimsonFurnace container, InventoryPlayer playerInv, String name, int width, int height) {
        super(container, playerInv, name, width, height);
        this.te = te;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int progress = te.getField(0);
        if (progress > 0) {
            drawTexturedModalRect(guiLeft + 76, guiTop + 35, 176, 0, progress * 23 / te.MAX_PROGRESS, 16);
        }
        drawEnergyBar(te.getField(1), te.getField(2));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (isMouseOverEnergyBar(mouseX, mouseY)) {
            drawHoveringText("Energy: " + te.getField(1), mouseX, mouseY);
        }
    }

    private void drawEnergyBar(int energy, int capacity) {
        if (capacity > 0) {
            drawRect(guiLeft + 79, guiTop + 71, guiLeft + 167, guiTop + 78, 0xff404040);
            int width = energy * (166 - 80) / capacity;
            //drawGradientRect(guiLeft + 80, guiTop + 72, guiLeft + 80 + width, guiTop + 77, Color.red.getRGB(), Color.yellow.getRGB());
            drawGradientRect(guiLeft + 80, guiTop + 72, guiLeft + 80 + width, guiTop + 77, Color.white.getRGB(), Color.cyan.getRGB());
            drawRect(guiLeft + 80 + width, guiTop + 72, guiLeft + 166, guiTop + 77, Color.black.getRGB());
        }
    }

    private boolean isMouseOverEnergyBar(int mouseX, int mouseY) {
        return mouseX >= guiLeft + 79 && mouseY >= guiTop + 71 && mouseX < guiLeft + 167 && mouseY < guiTop + 78;
    }
}
