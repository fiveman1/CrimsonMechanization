package fiveman1.crimsonmechanization.items;

import net.minecraft.item.Item;

public class ModItems {

    public static Item itemCrimsonCrystal;

    public static void init() {
        itemCrimsonCrystal = new CrimsonCrystal("crimson_crystal");
    }
}
