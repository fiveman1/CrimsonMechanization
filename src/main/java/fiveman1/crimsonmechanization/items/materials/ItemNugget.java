package fiveman1.crimsonmechanization.items.materials;

public class ItemNugget extends ItemMaterial {

    public ItemNugget(String name) {
        super(name);
        clearMaterials();
        addMaterials(EnumMaterial.BRONZE, EnumMaterial.COPPER, EnumMaterial.TIN, EnumMaterial.CRIMSON_IRON, EnumMaterial.CRIMSON_STEEL);
    }
}
