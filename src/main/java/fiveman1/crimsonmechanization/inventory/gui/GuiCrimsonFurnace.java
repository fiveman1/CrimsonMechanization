package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.inventory.container.ContainerCrimsonFurnace;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiCrimsonFurnace extends GuiBase {

    private TileCrimsonFurnace te;

    public GuiCrimsonFurnace(TileCrimsonFurnace te, ContainerCrimsonFurnace container, InventoryPlayer playerInv, String name, int width, int height) {
        super(container, playerInv, name, width, height);
        this.te = te;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = getLocalizedName();
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int progress = te.getField(0);
        if (progress > 0) {
            drawTexturedModalRect(guiLeft + 76, guiTop + 35, 176, 0, progress * 23 / te.MAX_PROGRESS, 16);
        }
    }
}
