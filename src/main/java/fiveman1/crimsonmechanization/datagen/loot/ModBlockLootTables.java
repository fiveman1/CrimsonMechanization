package fiveman1.crimsonmechanization.datagen.loot;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.stream.Collectors;

/*
Adapted from here
https://github.com/SilentChaos512/Silent-Gear/blob/1.16.x/src/main/java/net/silentchaos512/gear/data/loot/ModBlockLootTables.java
 */

public class ModBlockLootTables extends BlockLootTables {

    @Override
    protected void addTables() {
        for (Block block : BlockRegistration.MACHINES) {
            registerLootTable(block, this::machineLootTable);
        }
    }

    private LootTable.Builder machineLootTable(Block block) {
        LootPool.Builder builder = LootPool.builder()
                .name(block.getRegistryName().toString())
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(block))
                    .acceptFunction(CopyNbt.builder(CopyNbt.Source.BLOCK_ENTITY)
                        .addOperation("energy", "BlockEntityTag.energy", CopyNbt.Action.REPLACE));
        return LootTable.builder().addLootPool(builder);
    }

    // Won't work without this, makes our loot table only check for blocks from our mod
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> CrimsonMechanization.MODID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }
}
