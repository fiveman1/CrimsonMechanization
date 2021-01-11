package fiveman1.crimsonmechanization.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.inventory.GuiHandler;
import fiveman1.crimsonmechanization.network.Messages;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
        Messages.registerMessages(CrimsonMechanization.MODID);
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(CrimsonMechanization.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called on the client");
    }

    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called on the client");
    }
}