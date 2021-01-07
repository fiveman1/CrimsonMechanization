package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiBase extends GuiContainer {

    private final ResourceLocation background;

    public GuiBase(Container container, String name, int width, int height) {
        super(container);
        background = new ResourceLocation(CrimsonMechanization.MODID, "textures/gui/" + name + ".png");
        xSize = width;
        ySize = height;
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
