package fiveman1.crimsonmechanization.util;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockBase;
import fiveman1.crimsonmechanization.blocks.ModBlocks;
import fiveman1.crimsonmechanization.items.ItemBase;
import fiveman1.crimsonmechanization.items.ModItems;
import fiveman1.crimsonmechanization.tile.ModTileEntities;
import fiveman1.crimsonmechanization.tile.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static ArrayList<BlockBase> BLOCKS = new ArrayList<>();
    public static ArrayList<ItemBase> ITEMS = new ArrayList<>();

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        ModBlocks.init();

        for (BlockBase block : BLOCKS) {
            event.getRegistry().register(block);
        }

        ModTileEntities.init();
    }

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        ModItems.init();

        for (ItemBase item : ITEMS) {
            event.getRegistry().register(item);
        }
        for (BlockBase block : BLOCKS) {
            event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    public static void addBlockToRegistry(BlockBase block) {
        BLOCKS.add(block);
    }

    public static void addItemToRegistry(ItemBase item) {
        ITEMS.add(item);
    }
}
