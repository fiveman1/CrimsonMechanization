package fiveman1.crimsonmechanization.client;

import fiveman1.crimsonmechanization.inventory.container.ContainerRegistration;
import fiveman1.crimsonmechanization.inventory.gui.CompactorScreen;
import fiveman1.crimsonmechanization.inventory.gui.FurnaceScreen;
import fiveman1.crimsonmechanization.inventory.gui.UpgradeScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ContainerRegistration.compactorContainer, CompactorScreen::new);
        ScreenManager.registerFactory(ContainerRegistration.furnaceContainer, FurnaceScreen::new);
        ScreenManager.registerFactory(ContainerRegistration.upgradeContainer, UpgradeScreen::new);
    }

}
