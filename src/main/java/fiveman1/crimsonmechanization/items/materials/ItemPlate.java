package fiveman1.crimsonmechanization.items.materials;

public class ItemPlate extends ItemMaterial {

    public ItemPlate(String name) {
        super(name);
        /*EnumMaterial[] ignore = new EnumMaterial[]{EnumMaterial.LAPIS, EnumMaterial.DIAMOND, EnumMaterial.EMERALD,
                EnumMaterial.GOLD, EnumMaterial.COAL, EnumMaterial.REDSTONE};
        removeMaterials(ignore);*/
        clearMaterials();
        addMaterials(new EnumMaterial[]{EnumMaterial.IRON, EnumMaterial.CRIMSON});
    }
}
