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

    public static CompactorBlock compactorCrimson;
    public static CompactorBlock compactorRefined;
    public static CompactorBlock compactorNight;
    public static CompactorBlock compactorIridescent;

    public static FurnaceBlock furnaceCrimson;
    public static FurnaceBlock furnaceRefined;
    public static FurnaceBlock furnaceNight;
    public static FurnaceBlock furnaceIridescent;

    public static StorageBlock blockCrimson;
    public static StorageBlock blockCrimsonIron;
    public static StorageBlock blockCrimsonSteel;
    public static StorageBlock blockBronze;
    public static StorageBlock blockCopper;
    public static StorageBlock blockTin;
    public static StorageBlock blockIridescent;
    public static StorageBlock blockNight;

    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        IForgeRegistry<Block> registry = blockRegistryEvent.getRegistry();

        // Machines
        compactorCrimson = new CompactorBlock(MachineTier.CRIMSON);
        compactorRefined = new CompactorBlock(MachineTier.REFINED);
        compactorNight = new CompactorBlock(MachineTier.NIGHT);
        compactorIridescent = new CompactorBlock(MachineTier.IRIDESCENT);
        registerMachines(registry, compactorCrimson, compactorRefined, compactorIridescent, compactorNight);

        furnaceCrimson = new FurnaceBlock(MachineTier.CRIMSON);
        furnaceRefined = new FurnaceBlock(MachineTier.REFINED);
        furnaceNight = new FurnaceBlock(MachineTier.NIGHT);
        furnaceIridescent = new FurnaceBlock(MachineTier.IRIDESCENT);
        registerMachines(registry, furnaceCrimson, furnaceRefined, furnaceNight, furnaceIridescent);

        // Materials
        blockCrimson = new StorageBlock(BaseMaterial.CRIMSON);
        blockCrimsonIron = new StorageBlock(BaseMaterial.CRIMSON_IRON);
        blockCrimsonSteel = new StorageBlock(BaseMaterial.CRIMSON_STEEL);
        blockBronze = new StorageBlock(BaseMaterial.BRONZE);
        blockCopper = new StorageBlock(BaseMaterial.COPPER);
        blockTin = new StorageBlock(BaseMaterial.TIN);
        blockIridescent = new StorageBlock(BaseMaterial.IRIDESCENT);
        blockNight = new StorageBlock(BaseMaterial.NIGHT);
        registerMaterials(registry, blockCrimson, blockCrimsonIron, blockCrimsonSteel, blockBronze, blockCopper, blockTin, blockIridescent, blockNight);

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
