package fiveman1.crimsonmechanization.enums;

public enum AlloyPair {
    BRONZE("copper", 3, "tin", 1, "bronze", 4),
    CRIMSON_IRON(BaseMaterial.CRIMSON.name, 1, "iron", 1, BaseMaterial.CRIMSON_IRON.name, 2),
    CONSTANTAN("copper", 1, "nickel", 1, "constantan", 2),
    INVAR("iron", 2, "nickel", 1, "invar", 3),
    ELECTRUM("gold", 1, "silver", 1, "electrum", 2)
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