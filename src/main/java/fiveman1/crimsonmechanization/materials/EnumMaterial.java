package fiveman1.crimsonmechanization.materials;

import net.minecraft.util.IStringSerializable;

public enum EnumMaterial implements IStringSerializable {

    IRON(0, "iron", "Iron"),
    CRIMSON(1, "crimson", "Crimson");

    private static final EnumMaterial[] META_LOOKUP = new EnumMaterial[values().length];
    private final int meta;
    private final String name;
    private final String unlocalizedName;

    EnumMaterial(int metaIn, String nameIn, String unlocalizedNameIn)
    {
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

    public static EnumMaterial byMetadata(int meta)
    {
        if (meta < 0 || meta >= META_LOOKUP.length)
        {
            meta = 0;
        }
        return META_LOOKUP[meta];
    }

    public static int getLength() {
        return values().length;
    }

    static
    {
        for (EnumMaterial enumMaterial : values())
        {
            META_LOOKUP[enumMaterial.getMetadata()] = enumMaterial;
        }
    }
}
