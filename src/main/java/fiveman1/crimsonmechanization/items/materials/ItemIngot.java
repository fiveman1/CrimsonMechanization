package fiveman1.crimsonmechanization.items.materials;

import fiveman1.crimsonmechanization.enums.EnumItemMaterial;

public class ItemIngot extends ItemMaterial {

    public ItemIngot(String name) {
        super(name);
        removeMaterials(EnumItemMaterial.vanillaMaterials);
        removeMaterials(EnumItemMaterial.CRIMSON);
    }
}
