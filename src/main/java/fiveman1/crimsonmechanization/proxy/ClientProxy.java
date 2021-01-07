package fiveman1.crimsonmechanization.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import fiveman1.crimsonmechanization.blocks.BlockBase;
import fiveman1.crimsonmechanization.items.ItemBase;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (ItemBase item : RegistryHandler.ITEMS) {
            item.initModel();
        }
        for (BlockBase block : RegistryHandler.BLOCKS) {
            block.initModel();
        }
    }

    @Override
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }
}