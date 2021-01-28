package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.blocks.materials.MaterialBlock;
import fiveman1.crimsonmechanization.blocks.materials.StorageBlock;
import fiveman1.crimsonmechanization.enums.BaseMaterial;
import fiveman1.crimsonmechanization.enums.MachineTier;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockRegistration {

    public static final List<Item> BLOCK_ITEMS = new ArrayList<>();
    public static final List<MaterialBlock> MATERIAL_BLOCKS = new ArrayList<>();
    public static final List<AbstractMachineBlock> MACHINES = new ArrayList<>();

    public static CompactorMachineBlock compactorCrimson;
    public static CompactorMachineBlock compactorRefined;
    public static CompactorMachineBlock compactorIridescent;
    public static CompactorMachineBlock compactorNight;

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
        compactorCrimson = new CompactorMachineBlock(MachineTier.CRIMSON);
        compactorRefined = new CompactorMachineBlock(MachineTier.REFINED);
        compactorIridescent = new CompactorMachineBlock(MachineTier.IRIDESCENT);
        compactorNight = new CompactorMachineBlock(MachineTier.NIGHT);
        registerMachines(registry, compactorCrimson, compactorRefined, compactorIridescent, compactorNight);

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

    private static void registerMachines(IForgeRegistry<Block> registry, AbstractMachineBlock... blocks) {
        registry.registerAll(blocks);
        MACHINES.addAll(Arrays.asList(blocks));
    }

    private static void registerMaterials(IForgeRegistry<Block> registry, MaterialBlock... blocks) {
        registry.registerAll(blocks);
        MATERIAL_BLOCKS.addAll(Arrays.asList(blocks));
    }

    public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegistryEvent) {
        IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
        Item.Properties defaultProperty = new Item.Properties().group(ItemGroup.MISC);

        for (AbstractMachineBlock machineBlock : MACHINES) {
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
        for (AbstractMachineBlock machineBlock : MACHINES) {
            RenderTypeLookup.setRenderLayer(machineBlock, RenderType.getSolid());
        }
    }

    private static Item getBlockItem(Block block, Item.Properties properties) {
        return new BlockItem(block, properties).setRegistryName(block.getRegistryName());
    }
}
