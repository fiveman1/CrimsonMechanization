package fiveman1.crimsonmechanization.items.materials;

import fiveman1.crimsonmechanization.enums.EnumItemMaterial;

public class ItemGem extends ItemMaterial {

    public ItemGem(String name) {
        super(name);
        clearMaterials();
        addMaterials(EnumItemMaterial.CRIMSON);
    }
}
