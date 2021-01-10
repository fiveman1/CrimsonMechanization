package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.items.materials.ItemPlate;
import fiveman1.crimsonmechanization.items.tools.ItemCrimsonPickaxe;

public class ModItems {

    public static ItemCrimsonCrystal itemCrimsonCrystal;
    public static ItemCrimsonPickaxe itemCrimsonPickaxe;
    public static ItemCrimsonDust itemCrimsonDust;
    public static ItemPlate itemPlate;

    public static void init() {
        itemCrimsonDust = new ItemCrimsonDust("crimson_dust");
        itemCrimsonCrystal = new ItemCrimsonCrystal("crimson_crystal");
        itemCrimsonPickaxe = new ItemCrimsonPickaxe("crimson_pickaxe");
        itemPlate = new ItemPlate("plate");
    }
}
