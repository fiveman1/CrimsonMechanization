package fiveman1.crimsonmechanization.items.materials;

public class ItemDust extends ItemMaterial {
    public ItemDust(String name) {
        super(name);
        clearMaterials();
        addMaterials(new EnumMaterial[]{EnumMaterial.CRIMSON});
    }
}
