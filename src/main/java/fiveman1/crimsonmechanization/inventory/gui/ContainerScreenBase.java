package fiveman1.crimsonmechanization.inventory.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.ColorUtil;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ContainerScreenBase<T extends Container> extends ContainerScreen<T> {

    protected final ResourceLocation background;
    protected final String name;

    @SuppressWarnings("unchecked")
    public ContainerScreenBase(Container screenContainer, PlayerInventory inv, ITextComponent titleIn, String guiName, int width, int height) {
        super((T) screenContainer, inv, titleIn);
        this.background = new ResourceLocation(CrimsonMechanization.MODID, "textures/gui/" + guiName + ".png");
        this.name = I18n.format("container." + CrimsonMechanization.MODID + "." + guiName);
        this.xSize = width;
        this.ySize = height;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(background);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        blit(matrixStack, relX, relY, 0, 0, this.xSize, this.ySize);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        String s = this.name;
        font.drawString(matrixStack, s, xSize / 2.0f - font.getStringWidth(s) / 2.0f, 6, ColorUtil.GREY);
        font.drawString(matrixStack, playerInventory.getDisplayName().getString(), 8, ySize - 96 + 2, ColorUtil.GREY);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
        /*for (GuiButton button : buttonList) {
            if (button.isMouseOver()) {
                String tooltip = tooltipHash.get(button.id);
                if (tooltip != null) {
                    drawHoveringText(I18n.format(tooltip), mouseX, mouseY);
                }
            }
        }*/
    }
}
