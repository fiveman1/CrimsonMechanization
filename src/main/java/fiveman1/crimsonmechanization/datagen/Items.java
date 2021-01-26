package fiveman1.crimsonmechanization.datagen;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class Items extends ItemModelProvider {
    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CrimsonMechanization.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Item blockItem : BlockRegistration.blockItems) {
            withExistingParent(blockItem.getRegistryName().getPath(), new ResourceLocation(CrimsonMechanization.MODID, "block/" + blockItem.getRegistryName().getPath()));
        }
    }
}
