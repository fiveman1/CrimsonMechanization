package fiveman1.crimsonmechanization.util;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.ModBlocks;
import fiveman1.crimsonmechanization.items.ModItems;
import fiveman1.crimsonmechanization.tile.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = CrimsonMechanization.MODID)
public class RegistryHandler {

    public static final ArrayList<Block> BLOCKS = new ArrayList<>();
    public static final ArrayList<Item> ITEMS = new ArrayList<>();

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        ModBlocks.init();

        for (Block block : BLOCKS) {
            event.getRegistry().register(block);
        }

        ModTileEntities.init();
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        ModItems.init();
        for (Item item : ITEMS) {
            event.getRegistry().register(item);
        }
        for (Block block : BLOCKS) {
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
        event.getRegistry().register(ModItems.itemPlate);
        ModItems.itemPlate.registerOreDictEntries();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0 , new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void initBlockModel(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        for (Item item : RegistryHandler.ITEMS) {
            RegistryHandler.initItemModel(item);
        }
        for (Block block : RegistryHandler.BLOCKS) {
            RegistryHandler.initBlockModel(block);
        }
        ModItems.itemPlate.initModels();
    }

    public static void initItem(Item item, String name) {
        item.setRegistryName(CrimsonMechanization.MODID, name);
        item.setUnlocalizedName(CrimsonMechanization.MODID + "." + name);
        ITEMS.add(item);
    }

    public static void initItem(Item item, String registryName, String unlocalizedName) {
        item.setRegistryName(CrimsonMechanization.MODID, registryName);
        item.setUnlocalizedName(CrimsonMechanization.MODID + "." + unlocalizedName);
        ITEMS.add(item);
    }

    public static void initBlock(Block block, String name) {
        block.setRegistryName(CrimsonMechanization.MODID, name);
        block.setUnlocalizedName(CrimsonMechanization.MODID + "." + name);
        BLOCKS.add(block);
    }
}
