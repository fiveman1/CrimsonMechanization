package fiveman1.crimsonmechanization.items.materials;

import fiveman1.crimsonmechanization.enums.EnumItemMaterial;

public class ItemDust extends ItemMaterial {

    public ItemDust(String name) {
        super(name);
        removeMaterials(EnumItemMaterial.REDSTONE);
    }
}
