package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiBase extends GuiContainer {

    protected final ResourceLocation background;
    protected final InventoryPlayer playerInventory;
    private String NAME;

    public GuiBase(Container container, InventoryPlayer playerInv, String name, int width, int height) {
        super(container);
        background = new ResourceLocation(CrimsonMechanization.MODID, "textures/gui/" + name + ".png");
        playerInventory = playerInv;
        NAME = "container." + CrimsonMechanization.MODID + "." + name + ".name";
        xSize = width;
        ySize = height;
    }

    public String getLocalizedName() {
        return I18n.format(NAME);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
