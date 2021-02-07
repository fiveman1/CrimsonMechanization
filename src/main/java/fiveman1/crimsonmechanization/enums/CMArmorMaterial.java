package fiveman1.crimsonmechanization.enums;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;


// Reference: https://championash5357.github.io/ChampionAsh5357/tutorial/minecraft/1.16.1/items/armor


public enum CMArmorMaterial implements IArmorMaterial {

    // Fix repair item and stats later
    CRIMSON("crimson", 30, new int[] {2,6,7,3}, 15,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 0.0f, null),
    CRIMSON_IRON("crimsoniron", 30, new int[] {2,6,7,3}, 15,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 0.0f, null),
    CRIMSON_STEEL("crimsonsteel", 30, new int[] {2,6,7,3}, 15,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 0.0f, null),
    NIGHT("night", 30, new int[] {2,6,7,3}, 15,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 0.0f, null),
    IRIDESCENT("iridescent", 30, new int[] {2,6,7,3}, 15,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 0.0f, null)
    ;

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final Lazy<Ingredient> repairMaterialLazy;

    private CMArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountArrayIn, int enchantabilityIn,
                            SoundEvent soundEventIn, float toughnessIn, float knockbackResistanceIn, Supplier<Ingredient> repairMaterialSupplier){
        this.name = CrimsonMechanization.MODID + ":" + nameIn;
        this.maxDamageFactor = maxDamageFactorIn;
        this.damageReductionAmountArray = damageReductionAmountArrayIn;
        this.enchantability = enchantabilityIn;
        this.soundEvent = soundEventIn;
        this.toughness = toughnessIn;
        this.knockbackResistance = knockbackResistanceIn;
        this.repairMaterialLazy = Lazy.concurrentOf(repairMaterialSupplier);

    }

    @Override
    public int getDurability(EquipmentSlotType equipmentSlotType) {
        return MAX_DAMAGE_ARRAY[equipmentSlotType.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType equipmentSlotType) {
        return this.damageReductionAmountArray[equipmentSlotType.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterialLazy.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
