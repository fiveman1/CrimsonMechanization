package fiveman1.crimsonmechanization.inventory.gui;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.inventory.container.UpgradeContainer;
import fiveman1.crimsonmechanization.inventory.gui.buttons.SimpleImageButton;
import fiveman1.crimsonmechanization.util.PacketUtil;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class UpgradeScreen extends ContainerScreenBase<UpgradeContainer> {

    public UpgradeScreen(UpgradeContainer container, PlayerInventory inv, ITextComponent titleIn) {
        super(container, inv, titleIn, "upgrades", 176, 166);
    }

    @Override
    protected void init() {
        super.init();
        ITextComponent toolTip = new TranslationTextComponent("info." + CrimsonMechanization.MODID + ".button_back");
        SimpleImageButton buttonBack = new SimpleImageButton(guiLeft + 151, guiTop + 4, 16, 16, 0, 0,
                new ResourceLocation(CrimsonMechanization.MODID, "textures/gui/buttons.png"), this, toolTip, (button) -> {

            PacketUtil.openTileGui(container.getMachine());

        });
        addButton(buttonBack);
    }


}
