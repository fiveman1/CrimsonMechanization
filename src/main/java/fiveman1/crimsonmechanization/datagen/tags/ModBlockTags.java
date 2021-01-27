package fiveman1.crimsonmechanization.datagen.tags;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;

public class ModBlockTags extends ForgeBlockTagsProvider {

    public ModBlockTags(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen, existingFileHelper);
    }

    @Override
    public void registerTags() {

    }

    @Override
    public String getName() {
        return "Crimson Mechanization Block Tags";
    }
}
