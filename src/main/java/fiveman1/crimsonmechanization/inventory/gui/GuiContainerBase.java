package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.ColorUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Hashtable;

@SideOnly(Side.CLIENT)
public class GuiContainerBase extends GuiContainer {

    protected final ResourceLocation background;
    protected final InventoryPlayer playerInventory;
    protected final String NAME;
    protected final Hashtable<Integer, String> tooltipHash = new Hashtable<>();

    public GuiContainerBase(Container container, InventoryPlayer playerInv, String name, int width, int height) {
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
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, ColorUtil.GREY);
        fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, ColorUtil.GREY);
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
        for (GuiButton button : buttonList) {
            if (button.isMouseOver()) {
                String tooltip = tooltipHash.get(button.id);
                if (tooltip != null) {
                    drawHoveringText(I18n.format(tooltip), mouseX, mouseY);
                }
            }
        }
    }
}
