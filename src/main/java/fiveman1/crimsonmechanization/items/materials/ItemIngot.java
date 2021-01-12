package fiveman1.crimsonmechanization.items.materials;

public class ItemIngot extends ItemMaterial{
    public ItemIngot(String name) {
        super(name);
        removeMaterials(EnumMaterial.vanillaMaterials);
        removeMaterials(EnumMaterial.CRIMSON);
    }
}
