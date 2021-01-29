package fiveman1.crimsonmechanization.items.materials;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.datagen.tags.ModItemTags;
import fiveman1.crimsonmechanization.enums.BaseMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tags.ItemTags;

public class MaterialItem extends Item {

    public MaterialItem(String itemName, BaseMaterial material) {
        super(new Item.Properties().group(CrimsonMechanization.MOD_GROUP));
        setRegistryName(CrimsonMechanization.MODID, itemName + "_" + material.name);
        ModItemTags.createForgeTag(this, itemName + "s/" + material.name);
        ModItemTags.createForgeTag(this, itemName + "s");
    }
}
