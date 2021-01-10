package fiveman1.crimsonmechanization.items.materials;

public class ItemPlate extends ItemMaterial {

    public ItemPlate(String name) {
        super(name);
        addMaterialsToIgnoreList(new EnumMaterial[]{EnumMaterial.LAPIS, EnumMaterial.DIAMOND, EnumMaterial.EMERALD, EnumMaterial.GOLD, EnumMaterial.COAL, EnumMaterial.REDSTONE});
        rebuildValues();
    }
}
