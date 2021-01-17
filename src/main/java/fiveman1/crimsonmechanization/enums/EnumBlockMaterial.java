package fiveman1.crimsonmechanization.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumBlockMaterial implements IStringSerializable {

    CRIMSON(0, "crimson", "Crimson"),
    CRIMSON_IRON(1, "crimson_iron", "CrimsonIron"),
    CRIMSON_STEEL(2, "crimson_steel", "CrimsonSteel"),
    COPPER(3, "copper", "Copper"),
    TIN(4, "tin", "Tin"),
    BRONZE(5, "bronze", "Bronze");

    private final int meta;
    private final String name;
    private final String unlocalizedName;

    public static final EnumBlockMaterial[] values = values();

    EnumBlockMaterial(int metaIn, String nameIn, String unlocalizedNameIn) {
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

    public static EnumBlockMaterial byMetadata(int meta) {
        for (EnumBlockMaterial enumBlockMaterial : values) {
            if (enumBlockMaterial.getMetadata() == meta) {
                return enumBlockMaterial;
            }
        }
        // need a default, this typically shouldn't happen
        return CRIMSON;
    }
}
