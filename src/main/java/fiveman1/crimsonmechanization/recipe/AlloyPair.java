package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.items.materials.EnumMaterial;

public enum AlloyPair {
    BRONZE("Copper", 3, "Tin", 1, "Bronze", 4),
    CRIMSON_IRON(EnumMaterial.CRIMSON.getUnlocalizedName(), 1, "Iron", 1, EnumMaterial.CRIMSON_IRON.getUnlocalizedName(), 2),
    CONSTANTAN("Copper", 1, "Nickel", 1, "Constantan", 2),
    INVAR("Iron", 2, "Nickel", 1, "Invar", 3),
    ELECTRUM("Gold", 1, "Silver", 1, "Electrum", 2)
    ;

    public final MaterialPair first;
    public final MaterialPair second;
    public final MaterialPair output;
    public static final AlloyPair[] values = values();

    AlloyPair(String firstMaterial, int firstMaterialCount, String secondMaterial, int secondMaterialCount, String outputMaterial, int outputMaterialCount) {
        first = new MaterialPair(firstMaterial, firstMaterialCount);
        second = new MaterialPair(secondMaterial, secondMaterialCount);
        output = new MaterialPair(outputMaterial, outputMaterialCount);
    }

    public static class MaterialPair {
        public final String name;
        public final int count;

        MaterialPair(String materialName, int materialCount) {
            this.name = materialName;
            this.count = materialCount;
        }
    }
}