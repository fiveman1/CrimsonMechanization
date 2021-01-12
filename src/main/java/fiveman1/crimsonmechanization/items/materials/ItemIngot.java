package fiveman1.crimsonmechanization.items.materials;

public class ItemIngot extends ItemMaterial{
    public ItemIngot(String name) {
        super(name);
        clearMaterials();
        addMaterials(EnumMaterial.BRONZE, EnumMaterial.COPPER, EnumMaterial.TIN, EnumMaterial.CRIMSON_IRON, EnumMaterial.CRIMSON_STEEL);
    }
}
