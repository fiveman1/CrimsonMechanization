package fiveman1.crimsonmechanization.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class CustomGuiButtonImage extends GuiButton {

    protected final ResourceLocation location;
    protected final int xTexStart;
    protected final int yTexStart;

    public CustomGuiButtonImage(int buttonId, int x, int y, int widthIn, int heightIn, int xTexStart, int yTexStart, ResourceLocation location) {
        super(buttonId, x, y, widthIn, heightIn, "");
        this.xTexStart = xTexStart;
        this.yTexStart = yTexStart;
        this.location = location;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(this.location);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            if (this.hovered) {
                this.drawTexturedModalRect(this.x, this.y, this.xTexStart, this.yTexStart + this.height, this.width, this.height);
            } else {
                this.drawTexturedModalRect(this.x, this.y, this.xTexStart, this.yTexStart, this.width, this.height);
            }
        }
    }
}
