package fiveman1.crimsonmechanization.client;

import fiveman1.crimsonmechanization.inventory.container.ContainerRegistration;
import fiveman1.crimsonmechanization.inventory.gui.CompactorScreen;
import fiveman1.crimsonmechanization.inventory.gui.CrusherScreen;
import fiveman1.crimsonmechanization.inventory.gui.FurnaceScreen;
import fiveman1.crimsonmechanization.inventory.gui.UpgradeScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {

    public static void init(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ContainerRegistration.COMPACTOR_CONTAINER, CompactorScreen::new);
        ScreenManager.registerFactory(ContainerRegistration.CRUSHER_CONTAINER, CrusherScreen::new);
        ScreenManager.registerFactory(ContainerRegistration.FURNACE_CONTAINER, FurnaceScreen::new);
        ScreenManager.registerFactory(ContainerRegistration.UPGRADE_CONTAINER, UpgradeScreen::new);
    }

}
