package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.inventory.GuiHandler;
import fiveman1.crimsonmechanization.inventory.container.ContainerMachine;
import fiveman1.crimsonmechanization.tile.TileMachine;
import fiveman1.crimsonmechanization.util.PacketUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiMachine extends GuiBase {

    protected final ContainerMachine machineContainer;
    protected final TileMachine te;

    public GuiMachine(ContainerMachine container, InventoryPlayer playerInv, String name) {
        super(container, playerInv, name, 176, 166);
        machineContainer = container;
        te = machineContainer.getTileMachine();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (isMouseOverEnergyBar(mouseX, mouseY)) {
            drawHoveringText(te.getField(TileMachine.ENERGY_ID) + " / " + te.getField(TileMachine.CAPACITY_ID) + " RF", mouseX, mouseY);
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        GuiButtonImage buttonUpgrade = new GuiButtonImage(0, guiLeft + 151, guiTop + 4, 16, 16, 0, 0, 0,
                new ResourceLocation(CrimsonMechanization.MODID, "textures/gui/buttons/button_upgrade.png"));
        //GuiButton buttonUpgrade = new GuiButton(0, guiLeft + 151, guiTop + 4, 20, 20, "U");
        buttonList.add(buttonUpgrade);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                PacketUtil.sendButtonPacket(te, GuiHandler.UPGRADE_GUI_ID);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        drawProgressBar(te.getField(TileMachine.PROGRESS_ID), te.getField(TileMachine.RECIPE_ENERGY_ID));
        drawEnergyBar(te.getField(TileMachine.ENERGY_ID), te.getField(TileMachine.CAPACITY_ID));
    }

    protected void drawProgressBar(int progress, int maxProgress) {
        if (progress > 0 && maxProgress != 0) {
            drawTexturedModalRect(guiLeft + 76, guiTop + 35, 176, 0, progress * 23 / maxProgress, 16);
        }
    }

    protected void drawEnergyBar(int energy, int capacity) {
        if (capacity > 0) {
            // background
            drawRect(guiLeft + 79, guiTop + 71, guiLeft + 167, guiTop + 78, 0xff404040);

            // energy bar
            int width = energy * (166 - 80) / capacity;
            drawGradientRect(guiLeft + 80, guiTop + 72, guiLeft + 80 + width, guiTop + 77, Color.white.getRGB(), Color.cyan.getRGB());

            // empty energy bar
            drawRect(guiLeft + 80 + width, guiTop + 72, guiLeft + 166, guiTop + 77, Color.black.getRGB());
        }
    }

    protected boolean isMouseOverEnergyBar(int mouseX, int mouseY) {
        return mouseX >= guiLeft + 79 && mouseY >= guiTop + 71 && mouseX < guiLeft + 167 && mouseY < guiTop + 78;
    }
}
