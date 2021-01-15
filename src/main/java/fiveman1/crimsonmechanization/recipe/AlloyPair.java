package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.items.materials.EnumMaterial;

public enum AlloyPair {
    BRONZE("Copper", 3, "Tin", 1, "Bronze", 4),
    CRIMSON_IRON(EnumMaterial.CRIMSON.getUnlocalizedName(), 1, "Iron", 1, EnumMaterial.CRIMSON_IRON.getUnlocalizedName(), 2),
    CONSTANTAN("Copper", 1, "Nickel", 1, "Constantan", 2)
    ;

    public final String firstMaterial;
    public final int firstMaterialCount;
    public final String secondMaterial;
    public final int secondMaterialCount;
    public final String outputMaterial;
    public final int outputMaterialCount;
    public static final AlloyPair[] values = values();

    AlloyPair(String firstMaterial, int firstMaterialCount, String secondMaterial, int secondMaterialCount, String outputMaterial, int outputMaterialCount) {
        this.firstMaterial = firstMaterial;
        this.firstMaterialCount = firstMaterialCount;
        this.secondMaterial = secondMaterial;
        this.secondMaterialCount = secondMaterialCount;
        this.outputMaterial = outputMaterial;
        this.outputMaterialCount = outputMaterialCount;
    }
}