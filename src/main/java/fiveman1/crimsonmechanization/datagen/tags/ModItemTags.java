package fiveman1.crimsonmechanization.datagen.tags;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import java.util.ArrayList;
import java.util.List;

public class ModItemTags extends ForgeItemTagsProvider {

    private static final List<Pair<ITag.INamedTag<Item>, Item>> ITEM_TO_TAG = new ArrayList<>();

    public static void createForgeTag(Item item, String path) {
        createTag(item, "forge", path);
    }

    public static void createModTag(Item item, String path) {
        createTag(item, CrimsonMechanization.MODID, path);
    }

    public static void createTag(Item item, String namespace, String path) {
        ITEM_TO_TAG.add(Pair.of(ItemTags.makeWrapperTag(namespace + ":" + path), item));
    }

    public ModItemTags(DataGenerator gen, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(gen, blockTagProvider, existingFileHelper);
    }

    @Override
    public void registerTags() {
        for (Pair<ITag.INamedTag<Item>, Item> entry : ITEM_TO_TAG) {
            getOrCreateBuilder(entry.getFirst()).add(entry.getSecond());
        }
    }

    @Override
    public String getName() {
        return "Crimson Mechanization Item Tags";
    }
}
