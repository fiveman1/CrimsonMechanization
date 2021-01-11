package fiveman1.crimsonmechanization.items.materials;

public class ItemGem extends ItemMaterial {
    public ItemGem(String name) {
        super(name);
        clearMaterials();
        addMaterials(new EnumMaterial[]{EnumMaterial.CRIMSON});
    }
}
