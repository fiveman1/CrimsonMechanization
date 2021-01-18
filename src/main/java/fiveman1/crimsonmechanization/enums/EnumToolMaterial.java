package fiveman1.crimsonmechanization.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumToolMaterial implements IStringSerializable {
    CRIMSON("crimson", "Crimson", 2, 400, 6.0f, 2.0f, 12),
    CRIMSON_IRON("crimson_iron", "CrimsonIron", 2, 500, 7.0f, 2.5f, 12),
    CRIMSON_STEEL("crimson_steel", "CrimsonSteel", 3, 700, 8.0f, 3.5f, 12),
    IRIDESCENT("iridescent", "Iridescent", 3, 1750, 9.5f, 5.0f, 15)
    ;

    private final String name;
    private final String unlocalizedName;
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float damageVsEntity;
    private final int enchantability;

    EnumToolMaterial(String name, String unlocalizedName, int harvestLevel, int maxUses, float efficiency, float damageVsEntity, int enchantability){
        this.name = name;
        this.unlocalizedName = unlocalizedName;
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.damageVsEntity = damageVsEntity;
        this.enchantability = enchantability;
    }

    @Override
    public String getName() {
        return name;
    }
    public String getUnlocalizedName(){
        return unlocalizedName;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public int getMaxUses() {
        return maxUses;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public float getDamageVsEntity() {
        return damageVsEntity;
    }

    public int getEnchantability() {
        return enchantability;
    }
}
