package fiveman1.crimsonmechanization.items.materials;

public class ItemPlate extends ItemMaterial {

    public ItemPlate(String name) {
        super(name);
        removeMaterials(EnumMaterial.COAL, EnumMaterial.REDSTONE);
    }
}
