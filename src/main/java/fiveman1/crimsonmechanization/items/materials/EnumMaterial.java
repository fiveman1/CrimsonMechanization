package fiveman1.crimsonmechanization.items.materials;

import net.minecraft.util.IStringSerializable;

public enum EnumMaterial implements IStringSerializable {

    // Vanilla
    IRON(0, "iron", "Iron"),
    GOLD(1, "gold", "Gold"),
    DIAMOND(2, "diamond", "Diamond"),
    EMERALD(3, "emerald", "Emerald"),
    LAPIS(4, "lapis", "Lapis"),
    COAL(5, "coal", "Coal"),
    REDSTONE(6, "redstone", "Redstone"),
    // Modded
    CRIMSON(16, "crimson", "Crimson");

    private final int meta;
    private final String name;
    private final String unlocalizedName;

    public static final EnumMaterial[] values = values();
    public static final EnumMaterial[] vanillaMaterials = {EnumMaterial.IRON, EnumMaterial.GOLD, EnumMaterial.DIAMOND, EnumMaterial.EMERALD, EnumMaterial.LAPIS};

    EnumMaterial(int metaIn, String nameIn, String unlocalizedNameIn) {
        this.meta = metaIn;
        this.name = "_" + nameIn;
        this.unlocalizedName = unlocalizedNameIn;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getUnlocalizedName()
    {
        return unlocalizedName;
    }

    public int getMetadata()
    {
        return meta;
    }

    public static EnumMaterial byMetadata(int meta) {
        for (EnumMaterial enumMaterial : values) {
            if (enumMaterial.getMetadata() == meta) {
                return enumMaterial;
            }
        }
        // default, this typically shouldn't happen
        return IRON;
    }

    public static int getLength() {
        return values().length;
    }
}
