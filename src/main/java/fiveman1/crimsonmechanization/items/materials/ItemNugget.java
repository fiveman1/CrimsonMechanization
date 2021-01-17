package fiveman1.crimsonmechanization.items.materials;

import fiveman1.crimsonmechanization.enums.EnumItemMaterial;

public class ItemNugget extends ItemMaterial {

    public ItemNugget(String name) {
        super(name);
        clearMaterials();
        addMaterials(EnumItemMaterial.BRONZE, EnumItemMaterial.COPPER, EnumItemMaterial.TIN, EnumItemMaterial.CRIMSON_IRON, EnumItemMaterial.CRIMSON_STEEL);
    }
}
