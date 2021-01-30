package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.materials.MaterialBlock;
import fiveman1.crimsonmechanization.blocks.materials.StorageBlock;
import fiveman1.crimsonmechanization.enums.BaseMaterial;
import fiveman1.crimsonmechanization.enums.MachineTier;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockRegistration {

    public static final List<Item> BLOCK_ITEMS = new ArrayList<>();
    public static final List<MaterialBlock> MATERIAL_BLOCKS = new ArrayList<>();
    public static final List<AbstractMachineBlock<?, ?>> MACHINES = new ArrayList<>();

    public static AlloyerBlock ALLOYER_CRIMSON;
    public static AlloyerBlock ALLOYER_REFINED;
    public static AlloyerBlock ALLOYER_NIGHT;
    public static AlloyerBlock ALLOYER_IRIDESCENT;

    public static CompactorBlock COMPACTOR_CRIMSON;
    public static CompactorBlock COMPACTOR_REFINED;
    public static CompactorBlock COMPACTOR_NIGHT;
    public static CompactorBlock COMPACTOR_IRIDESCENT;

    public static CrusherBlock CRUSHER_CRIMSON;
    public static CrusherBlock CRUSHER_REFINED;
    public static CrusherBlock CRUSHER_NIGHT;
    public static CrusherBlock CRUSHER_IRIDESCENT;

    public static FurnaceBlock FURNACE_CRIMSON;
    public static FurnaceBlock FURNACE_REFINED;
    public static FurnaceBlock FURNACE_NIGHT;
    public static FurnaceBlock FURNACE_IRIDESCENT;

    public static StorageBlock BLOCK_CRIMSON;
    public static StorageBlock BLOCK_CRIMSON_IRON;
    public static StorageBlock BLOCK_CRIMSON_STEEL;
    public static StorageBlock BLOCK_BRONZE;
    public static StorageBlock BLOCK_COPPER;
    public static StorageBlock BLOCK_TIN;
    public static StorageBlock BLOCK_IRIDESCENT;
    public static StorageBlock BLOCK_NIGHT;

    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        IForgeRegistry<Block> registry = blockRegistryEvent.getRegistry();

        // Machines
        ALLOYER_CRIMSON = new AlloyerBlock(MachineTier.CRIMSON);
        ALLOYER_REFINED = new AlloyerBlock(MachineTier.REFINED);
        ALLOYER_NIGHT = new AlloyerBlock(MachineTier.NIGHT);
        ALLOYER_IRIDESCENT = new AlloyerBlock(MachineTier.IRIDESCENT);
        registerMachines(registry, ALLOYER_CRIMSON, ALLOYER_REFINED, ALLOYER_NIGHT, ALLOYER_IRIDESCENT);

        COMPACTOR_CRIMSON = new CompactorBlock(MachineTier.CRIMSON);
        COMPACTOR_REFINED = new CompactorBlock(MachineTier.REFINED);
        COMPACTOR_NIGHT = new CompactorBlock(MachineTier.NIGHT);
        COMPACTOR_IRIDESCENT = new CompactorBlock(MachineTier.IRIDESCENT);
        registerMachines(registry, COMPACTOR_CRIMSON, COMPACTOR_REFINED, COMPACTOR_IRIDESCENT, COMPACTOR_NIGHT);

        CRUSHER_CRIMSON = new CrusherBlock(MachineTier.CRIMSON);
        CRUSHER_REFINED = new CrusherBlock(MachineTier.REFINED);
        CRUSHER_NIGHT = new CrusherBlock(MachineTier.NIGHT);
        CRUSHER_IRIDESCENT = new CrusherBlock(MachineTier.IRIDESCENT);
        registerMachines(registry, CRUSHER_CRIMSON, CRUSHER_REFINED, CRUSHER_NIGHT, CRUSHER_IRIDESCENT);

        FURNACE_CRIMSON = new FurnaceBlock(MachineTier.CRIMSON);
        FURNACE_REFINED = new FurnaceBlock(MachineTier.REFINED);
        FURNACE_NIGHT = new FurnaceBlock(MachineTier.NIGHT);
        FURNACE_IRIDESCENT = new FurnaceBlock(MachineTier.IRIDESCENT);
        registerMachines(registry, FURNACE_CRIMSON, FURNACE_REFINED, FURNACE_NIGHT, FURNACE_IRIDESCENT);

        // Materials
        BLOCK_CRIMSON = new StorageBlock(BaseMaterial.CRIMSON);
        BLOCK_CRIMSON_IRON = new StorageBlock(BaseMaterial.CRIMSON_IRON);
        BLOCK_CRIMSON_STEEL = new StorageBlock(BaseMaterial.CRIMSON_STEEL);
        BLOCK_BRONZE = new StorageBlock(BaseMaterial.BRONZE);
        BLOCK_COPPER = new StorageBlock(BaseMaterial.COPPER);
        BLOCK_TIN = new StorageBlock(BaseMaterial.TIN);
        BLOCK_IRIDESCENT = new StorageBlock(BaseMaterial.IRIDESCENT);
        BLOCK_NIGHT = new StorageBlock(BaseMaterial.NIGHT);
        registerMaterials(registry, BLOCK_CRIMSON, BLOCK_CRIMSON_IRON, BLOCK_CRIMSON_STEEL, BLOCK_BRONZE, BLOCK_COPPER, BLOCK_TIN, BLOCK_IRIDESCENT, BLOCK_NIGHT);

    }

    private static void registerMachines(IForgeRegistry<Block> registry, AbstractMachineBlock<?, ?>... blocks) {
        registry.registerAll(blocks);
        MACHINES.addAll(Arrays.asList(blocks));
    }

    private static void registerMaterials(IForgeRegistry<Block> registry, MaterialBlock... blocks) {
        registry.registerAll(blocks);
        MATERIAL_BLOCKS.addAll(Arrays.asList(blocks));
    }

    public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegistryEvent) {
        IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
        Item.Properties defaultProperty = new Item.Properties().group(CrimsonMechanization.MOD_GROUP);

        for (AbstractMachineBlock<?, ?> machineBlock : MACHINES) {
            Item blockItem = getBlockItem(machineBlock, defaultProperty);
            BLOCK_ITEMS.add(blockItem);
            registry.register(blockItem);
        }

        for (MaterialBlock materialBlock : MATERIAL_BLOCKS) {
            Item blockItem = materialBlock.getBlockItem(defaultProperty);
            BLOCK_ITEMS.add(blockItem);
            registry.register(blockItem);
        }
    }

    public static void onClientSetup(FMLClientSetupEvent event) {

    }

    private static Item getBlockItem(Block block, Item.Properties properties) {
        return new BlockItem(block, properties).setRegistryName(block.getRegistryName());
    }
}
