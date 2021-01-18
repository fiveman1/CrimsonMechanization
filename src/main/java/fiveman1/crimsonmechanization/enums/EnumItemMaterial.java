package fiveman1.crimsonmechanization.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumItemMaterial implements IStringSerializable {

    // Vanilla
    IRON(0, "iron", "Iron"),
    GOLD(1, "gold", "Gold"),
    DIAMOND(2, "diamond", "Diamond"),
    EMERALD(3, "emerald", "Emerald"),
    LAPIS(4, "lapis", "Lapis"),
    COAL(5, "coal", "Coal"),
    REDSTONE(6, "redstone", "Redstone"),

    // Modded
    CRIMSON(16, "crimson", "Crimson"),
    CRIMSON_IRON(17, "crimson_iron", "CrimsonIron"),
    CRIMSON_STEEL(18, "crimson_steel", "CrimsonSteel"),
    IRIDESCENT(19, "iridescent", "Iridescent"),

    COPPER(32, "copper", "Copper"),
    TIN(33, "tin", "Tin"),

    BRONZE(64, "bronze", "Bronze");

    private final int meta;
    private final String name;
    private final String unlocalizedName;

    public static final EnumItemMaterial[] values = values();
    public static final EnumItemMaterial[] vanillaMaterials = {EnumItemMaterial.IRON, EnumItemMaterial.GOLD, EnumItemMaterial.DIAMOND, EnumItemMaterial.EMERALD, EnumItemMaterial.LAPIS, EnumItemMaterial.COAL, EnumItemMaterial.REDSTONE};

    EnumItemMaterial(int metaIn, String nameIn, String unlocalizedNameIn) {
        this.meta = metaIn;
        this.name = nameIn;
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

    public static EnumItemMaterial byMetadata(int meta) {
        for (EnumItemMaterial enumItemMaterial : values) {
            if (enumItemMaterial.getMetadata() == meta) {
                return enumItemMaterial;
            }
        }
        // need a default, this typically shouldn't happen
        return IRON;
    }
}
