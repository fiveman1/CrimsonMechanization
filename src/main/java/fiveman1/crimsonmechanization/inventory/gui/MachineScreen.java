package fiveman1.crimsonmechanization.inventory.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import fiveman1.crimsonmechanization.inventory.container.MachineContainer;
import fiveman1.crimsonmechanization.network.PacketServerToClient;
import fiveman1.crimsonmechanization.tile.AbstractMachineTile;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MachineScreen<T extends MachineContainer> extends ContainerScreenBase<T> {

    private final AbstractMachineTile te;

    public MachineScreen(MachineContainer screenContainer, PlayerInventory inv, ITextComponent titleIn, String guiName) {
        super(screenContainer, inv, titleIn, guiName, 176, 166);
        te = screenContainer.machineTile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, x, y);
        drawProgressBar(matrixStack, te.getField(PacketServerToClient.PROGRESS_ID), te.getField(PacketServerToClient.RECIPE_ENERGY_ID));
        drawEnergyBar(matrixStack, te.getField(PacketServerToClient.ENERGY_ID), te.getField(PacketServerToClient.CAPACITY_ID));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (isMouseOverEnergyBar(mouseX, mouseY)) {
            List<TranslationTextComponent> lines = new ArrayList<>();
            lines.add(new TranslationTextComponent("message.crimsonmechanization.machine_energy", te.getField(PacketServerToClient.ENERGY_ID), te.getField(PacketServerToClient.CAPACITY_ID)));
            lines.add(new TranslationTextComponent("message.crimsonmechanization.machine_energy_rate", te.getField(PacketServerToClient.ENERGY_RATE_ID)));
            GuiUtils.drawHoveringText(matrixStack, lines, mouseX, mouseY, width, height, -1, GuiUtils.DEFAULT_BACKGROUND_COLOR, Color.white.getRGB(), Color.cyan.getRGB(), font);

        }
    }

    protected void drawProgressBar(MatrixStack matrixStack, int progress, int maxProgress) {
        if (progress > 0 && maxProgress != 0) {
            GuiUtils.drawTexturedModalRect(matrixStack, guiLeft + 76, guiTop + 35, 176, 0, progress * 23 / maxProgress, 16, 1);
        }
    }

    protected void drawEnergyBar(MatrixStack matrixStack, int energy, int capacity) {
        if (capacity > 0) {
            // background
            GuiUtils.drawGradientRect(matrixStack.getLast().getMatrix(), 1, guiLeft + 79, guiTop + 71, guiLeft + 167, guiTop + 78, 0xff404040, 0xff404040);

            // energy bar
            int width = energy * (166 - 80) / capacity;
            GuiUtils.drawGradientRect(matrixStack.getLast().getMatrix(), 2, guiLeft + 80, guiTop + 72, guiLeft + 80 + width, guiTop + 77, Color.white.getRGB(), Color.cyan.getRGB());

            // empty energy bar
            GuiUtils.drawGradientRect(matrixStack.getLast().getMatrix(), 2, guiLeft + 80 + width, guiTop + 72, guiLeft + 166, guiTop + 77, Color.black.getRGB(), Color.black.getRGB());
        }
    }

    protected boolean isMouseOverEnergyBar(int mouseX, int mouseY) {
        return mouseX >= guiLeft + 79 && mouseY >= guiTop + 71 && mouseX < guiLeft + 167 && mouseY < guiTop + 78;
    }
}
