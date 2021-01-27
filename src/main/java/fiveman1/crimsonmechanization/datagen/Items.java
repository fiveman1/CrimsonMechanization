package fiveman1.crimsonmechanization.datagen;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import fiveman1.crimsonmechanization.items.ItemRegistration;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class Items extends ItemModelProvider {
    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, CrimsonMechanization.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Item blockItem : BlockRegistration.BLOCK_ITEMS) {
            withExistingParent(blockItem.getRegistryName().getPath(), new ResourceLocation(CrimsonMechanization.MODID, "block/" + blockItem.getRegistryName().getPath()));
        }
        for (Item item : ItemRegistration.ITEMS) {
            singleTexture(item.getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0",
                    new ResourceLocation(CrimsonMechanization.MODID, "item/" + item.getRegistryName().getPath()));
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Crimson Mechanization Items";
    }
}
