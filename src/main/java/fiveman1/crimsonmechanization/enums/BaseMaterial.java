package fiveman1.crimsonmechanization.enums;

public enum BaseMaterial {
    // Vanilla
    IRON("iron"),
    GOLD("gold"),
    DIAMOND("diamond"),
    EMERALD("emerald"),
    LAPIS("lapis"),
    COAL("coal"),
    REDSTONE("redstone"),

    // Modded
    CRIMSON("crimson"),
    CRIMSON_IRON("crimson_iron"),
    CRIMSON_STEEL("crimson_steel"),
    IRIDESCENT("iridescent"),

    COPPER("copper"),
    TIN("tin"),
    BRONZE("bronze")
    ;

    public final String name;

    BaseMaterial(String name) {
        this.name = name;
    }
}
