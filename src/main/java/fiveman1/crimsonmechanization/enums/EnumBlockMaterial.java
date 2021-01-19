package fiveman1.crimsonmechanization.enums;

import net.minecraft.util.IStringSerializable;

import java.util.Hashtable;

public enum EnumBlockMaterial implements IStringSerializable {

    CRIMSON(0, "crimson", "Crimson"),
    CRIMSON_IRON(1, "crimson_iron", "CrimsonIron"),
    CRIMSON_STEEL(2, "crimson_steel", "CrimsonSteel"),
    COPPER(3, "copper", "Copper"),
    TIN(4, "tin", "Tin"),
    BRONZE(5, "bronze", "Bronze"),
    IRIDESCENT(6, "iridescent", "Iridescent");

    private final int meta;
    private final String name;
    private final String unlocalizedName;

    public static final EnumBlockMaterial[] values = values();
    private static final Hashtable<Integer, EnumBlockMaterial> metaToEnum = new Hashtable<>();
    static {
        for (EnumBlockMaterial enumBlockMaterial : values) {
            metaToEnum.put(enumBlockMaterial.getMetadata(), enumBlockMaterial);
        }
    }

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
        EnumBlockMaterial enumBlockMaterial = metaToEnum.get(meta);
        return enumBlockMaterial != null ? enumBlockMaterial : CRIMSON;
    }
}
