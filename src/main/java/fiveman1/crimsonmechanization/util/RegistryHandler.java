package fiveman1.crimsonmechanization.util;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockBase;
import fiveman1.crimsonmechanization.blocks.ModBlocks;
import fiveman1.crimsonmechanization.items.ItemBase;
import fiveman1.crimsonmechanization.items.ModItems;
import fiveman1.crimsonmechanization.tile.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class RegistryHandler {

    public static ArrayList<BlockBase> BLOCKS = new ArrayList<>();
    public static ArrayList<ItemBase> ITEMS = new ArrayList<>();
    public static ArrayList<TileEntityBase> TILES = new ArrayList<>();

    @SubscribeEvent
    public static void onBlockRegistry(RegistryEvent.Register<Block> event) {
        ModBlocks.init();

        for (BlockBase block : BLOCKS) {
            event.getRegistry().register(block);
        }
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

    public static void initTileEntities() {
        for (TileEntityBase tileEntity : TILES) {
            // the segment below was adapted from the Actually Additions source
            // https://github.com/Ellpeck/ActuallyAdditions/blob/1.12.2/src/main/java/de/ellpeck/actuallyadditions/mod/tile/TileEntityBase.java
            try {
                Class<? extends TileEntityBase> tileClass = tileEntity.getClass();
                GameRegistry.registerTileEntity(tileClass, new ResourceLocation(CrimsonMechanization.MODID, tileClass.newInstance().name));
            } catch (Exception e) {
                CrimsonMechanization.logger.fatal("Failed to register a TileEntity");
            }
        }
    }

    public static void addBlockToRegistry(BlockBase block) {
        BLOCKS.add(block);
    }

    public static void addItemToRegistry(ItemBase item) {
        ITEMS.add(item);
    }

    public static void addTileToRegistry(TileEntityBase tileEntity) {
        TILES.add(tileEntity);
    }
}
