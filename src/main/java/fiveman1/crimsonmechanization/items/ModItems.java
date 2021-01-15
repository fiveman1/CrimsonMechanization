package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.items.armor.ItemArmorBase;
import fiveman1.crimsonmechanization.items.materials.*;
import fiveman1.crimsonmechanization.items.tools.*;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {

    public static ItemCrimsonPickaxe itemCrimsonPickaxe;
    public static ItemCrimsonSpade itemCrimsonSpade;
    public static ItemCrimsonAxe itemCrimsonAxe;
    public static ItemCrimsonHoe itemCrimsonHoe;
    public static ItemCrimsonSword itemCrimsonSword;
    public static ItemDust itemDust;
    public static ItemGem itemGem;
    public static ItemIngot itemIngot;
    public static ItemNugget itemNugget;
    public static ItemPlate itemPlate;

    public static final ItemArmor.ArmorMaterial CRIMSON_MODEL_MATERIAL = EnumHelper.addArmorMaterial("crimson_model",
            CrimsonMechanization.MODID + ":crimson_model", 100, new int[] {7,8,8,9}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC
                , 2.0f);
    public static ItemArmorBase crimson_helmet;
    public static ItemArmorBase crimson_chestplate;
    public static ItemArmorBase crimson_leggings;
    public static ItemArmorBase crimson_boots;

    public static void init() {
        itemCrimsonPickaxe = new ItemCrimsonPickaxe("crimson_pickaxe");
        itemCrimsonSpade = new ItemCrimsonSpade("crimson_shovel");
        itemCrimsonAxe = new ItemCrimsonAxe("crimson_axe");
        itemCrimsonHoe = new ItemCrimsonHoe("crimson_hoe");
        itemCrimsonSword = new ItemCrimsonSword("crimson_sword");
        itemDust = new ItemDust("dust");
        itemGem = new ItemGem("gem");
        itemIngot = new ItemIngot("ingot");
        itemNugget = new ItemNugget("nugget");
        itemPlate = new ItemPlate("plate");

        crimson_helmet = new ItemArmorBase("crimson_helmet", EntityEquipmentSlot.HEAD);
        crimson_chestplate = new ItemArmorBase("crimson_chestplate", EntityEquipmentSlot.CHEST);
        crimson_leggings = new ItemArmorBase("crimson_leggings", EntityEquipmentSlot.LEGS);
        crimson_boots = new ItemArmorBase("crimson_boots", EntityEquipmentSlot.FEET);
    }
}
