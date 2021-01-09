package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;

@SideOnly(Side.CLIENT)
public class GuiBase extends GuiContainer {

    protected final ResourceLocation background;
    protected final InventoryPlayer playerInventory;
    private final String NAME;

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

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = getLocalizedName();
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 0x404040);
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

    public static int getRGB(int red, int green, int blue, int alpha) {
        return new Color(red, green, blue, alpha).getRGB();
    }
    public static int getRGB(int red, int green, int blue) {
        return getRGB(red, green, blue, 0);
    }
}
