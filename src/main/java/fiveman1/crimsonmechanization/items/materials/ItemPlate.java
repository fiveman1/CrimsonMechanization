package fiveman1.crimsonmechanization.items.materials;

import fiveman1.crimsonmechanization.enums.EnumItemMaterial;

public class ItemPlate extends ItemMaterial {

    public ItemPlate(String name) {
        super(name);
        removeMaterials(EnumItemMaterial.COAL, EnumItemMaterial.REDSTONE);
    }
}
