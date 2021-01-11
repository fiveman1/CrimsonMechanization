package fiveman1.crimsonmechanization.inventory.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import java.awt.*;

public class GuiMachine extends GuiBase {

    public GuiMachine(Container container, InventoryPlayer playerInv, String name, int width, int height) {
        super(container, playerInv, name, width, height);
    }

    protected void drawProgressBar(int progress, int maxProgress) {
        if (progress > 0) {
            drawTexturedModalRect(guiLeft + 76, guiTop + 35, 176, 0, progress * 23 / maxProgress, 16);
        }
    }

    protected void drawEnergyBar(int energy, int capacity) {
        if (capacity > 0) {
            drawRect(guiLeft + 79, guiTop + 71, guiLeft + 167, guiTop + 78, 0xff404040);
            int width = energy * (166 - 80) / capacity;
            //drawGradientRect(guiLeft + 80, guiTop + 72, guiLeft + 80 + width, guiTop + 77, Color.red.getRGB(), Color.yellow.getRGB());
            drawGradientRect(guiLeft + 80, guiTop + 72, guiLeft + 80 + width, guiTop + 77, Color.white.getRGB(), Color.cyan.getRGB());
            drawRect(guiLeft + 80 + width, guiTop + 72, guiLeft + 166, guiTop + 77, Color.black.getRGB());
        }
    }

    protected boolean isMouseOverEnergyBar(int mouseX, int mouseY) {
        return mouseX >= guiLeft + 79 && mouseY >= guiTop + 71 && mouseX < guiLeft + 167 && mouseY < guiTop + 78;
    }
}
