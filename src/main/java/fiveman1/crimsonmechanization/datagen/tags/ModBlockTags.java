package fiveman1.crimsonmechanization.datagen.tags;

import com.mojang.datafixers.util.Pair;
import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;

import java.util.ArrayList;
import java.util.List;

public class ModBlockTags extends ForgeBlockTagsProvider {

    private static final List<Pair<ITag.INamedTag<Block>, Block>> BLOCK_TO_TAG = new ArrayList<>();

    public static void createForgeTag(Block block, String path) {
        createTag(block, "forge", path);
    }

    public static void createModTag(Block block, String path) {
        createTag(block, CrimsonMechanization.MODID, path);
    }

    public static void createTag(Block block, String namespace, String path) {
        BLOCK_TO_TAG.add(Pair.of(BlockTags.makeWrapperTag(namespace + ":" + path), block));
    }

    public ModBlockTags(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen, existingFileHelper);
    }

    @Override
    public void registerTags() {
        for (Pair<ITag.INamedTag<Block>, Block> entry : BLOCK_TO_TAG) {
            getOrCreateBuilder(entry.getFirst()).add(entry.getSecond());
        }
    }

    @Override
    public String getName() {
        return "Crimson Mechanization Block Tags";
    }
}
