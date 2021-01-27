package fiveman1.crimsonmechanization.enums;

public enum BaseMaterial {
    // Vanilla
    IRON("iron", 1),
    GOLD("gold", 2, 3.0f, 6.0f),
    DIAMOND("diamond", 2),
    EMERALD("emerald", 2),
    LAPIS("lapis", 1),
    COAL("coal", 1),
    REDSTONE("redstone", 2),

    // Modded
    CRIMSON("crimson", 1),
    CRIMSON_IRON("crimson_iron", 1),
    CRIMSON_STEEL("crimson_steel", 2),
    IRIDESCENT("iridescent", 2),

    COPPER("copper", 1),
    TIN("tin", 1),
    BRONZE("bronze", 1)
    ;

    public final String name;
    public final int harvestLevel;
    public final float hardness;
    public final float resistance;

    BaseMaterial(String name, int harvestLevel, float hardness, float resistance) {
        this.name = name;
        this.harvestLevel = harvestLevel;
        this.hardness = hardness;
        this.resistance = resistance;
    }

    BaseMaterial(String name, int harvestLevel) {
        this(name, harvestLevel, 5.0f, 6.0f);
    }
}
