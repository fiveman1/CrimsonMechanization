package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.datagen.BlockStates;
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

    public static final List<MachineBlock> MACHINES = new ArrayList<>();
    public static final List<Item> BLOCK_ITEMS = new ArrayList<>();

    public static CompactorMachineBlock compactorMachineBlockCrimson;
    public static CompactorMachineBlock compactorMachineBlockRefined;
    public static CompactorMachineBlock compactorMachineBlockIridescent;

    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        IForgeRegistry<Block> registry = blockRegistryEvent.getRegistry();

        compactorMachineBlockCrimson = new CompactorMachineBlock(MachineTier.CRIMSON);
        compactorMachineBlockRefined = new CompactorMachineBlock(MachineTier.REFINED);
        compactorMachineBlockIridescent = new CompactorMachineBlock(MachineTier.IRIDESCENT);
        registerMachines(registry, compactorMachineBlockCrimson, compactorMachineBlockRefined, compactorMachineBlockIridescent);

    }

    private static void registerMachines(IForgeRegistry<Block> registry, MachineBlock... blocks) {
        registry.registerAll(blocks);
        List<MachineBlock> blockList = Arrays.asList(blocks);
        MACHINES.addAll(blockList);
        BlockStates.machineBlocks.addAll(blockList);
    }

    public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegistryEvent) {
        IForgeRegistry<Item> registry = itemRegistryEvent.getRegistry();
        Item.Properties defaultProperty = new Item.Properties().group(ItemGroup.MISC);

        for (MachineBlock machineBlock : MACHINES) {
            Item blockItem = getBlockItem(machineBlock, defaultProperty);
            BLOCK_ITEMS.add(blockItem);
            registry.register(blockItem);
        }
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        for (MachineBlock machineBlock : MACHINES) {
            RenderTypeLookup.setRenderLayer(machineBlock, RenderType.getSolid());
        }
    }

    private static Item getBlockItem(Block block, Item.Properties properties) {
        return new BlockItem(block, properties).setRegistryName(block.getRegistryName());
    }
}
