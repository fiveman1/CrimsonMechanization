package fiveman1.crimsonmechanization.util;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.ModBlocks;
import fiveman1.crimsonmechanization.items.ModItems;
import fiveman1.crimsonmechanization.tile.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static final ArrayList<IInitializeable> INITIALIZEABLES = new ArrayList<>();

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        ModBlocks.init();
        for (IInitializeable initializeable : INITIALIZEABLES) {
            initializeable.initBlock(event);
        }
        ModTileEntities.init();
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        ModItems.init();
        for (IInitializeable initializeable : INITIALIZEABLES) {
            initializeable.initItem(event);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        for (IInitializeable initializeable : INITIALIZEABLES) {
            initializeable.initModel(event);
        }
    }

    public static void initItem(Item item, String name) {
        item.setRegistryName(CrimsonMechanization.MODID, name);
        item.setUnlocalizedName(CrimsonMechanization.MODID + "." + name);
    }

    public static void initBlock(Block block, String name) {
        block.setRegistryName(CrimsonMechanization.MODID, name);
        block.setUnlocalizedName(CrimsonMechanization.MODID + "." + name);
    }
}
