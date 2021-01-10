package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.materials.EnumMaterial;

public class ItemPlate extends ItemMaterial {

    public ItemPlate(String name) {
        super(name);
        addMaterialsToIgnoreList(new EnumMaterial[]{EnumMaterial.LAPIS, EnumMaterial.DIAMOND, EnumMaterial.EMERALD, EnumMaterial.GOLD});
        rebuildValues();
    }
}
