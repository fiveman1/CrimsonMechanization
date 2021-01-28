package fiveman1.crimsonmechanization.inventory.gui.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SimpleImageButton extends Button {

    private final ResourceLocation resourceLocation;
    private final int xTexStart;
    private final int yTexStart;

    public SimpleImageButton(int xIn, int yIn, int widthIn, int heightIn, int xTexStartIn, int yTexStartIn, ResourceLocation resourceLocationIn, Screen screen, ITextComponent tooltipText, IPressable pressedAction) {
        super(xIn, yIn, widthIn, heightIn, tooltipText, pressedAction, (button, matrixStack, mouseX, mouseY) -> {
            screen.renderTooltip(matrixStack, tooltipText, mouseX, mouseY);
        });
        this.xTexStart = xTexStartIn;
        this.yTexStart = yTexStartIn;
        this.resourceLocation = resourceLocationIn;
    }

    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.resourceLocation);
        int textureHeight = 256;
        int textureWidth = 256;

        int i = this.yTexStart;
        if (this.isHovered()) {
            i += this.height;
        }

        RenderSystem.enableDepthTest();
        blit(matrixStack, this.x, this.y, (float)this.xTexStart, (float)i, this.width, this.height, textureWidth, textureHeight);
        if (this.isHovered()) {
            this.renderToolTip(matrixStack, mouseX, mouseY);
        }
    }
}
