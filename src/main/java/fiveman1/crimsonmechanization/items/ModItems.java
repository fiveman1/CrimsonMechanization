package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.items.materials.*;
import fiveman1.crimsonmechanization.items.tools.ItemCrimsonPickaxe;

public class ModItems {

    public static ItemCrimsonPickaxe itemCrimsonPickaxe;
    public static ItemDust itemDust;
    public static ItemGem itemGem;
    public static ItemIngot itemIngot;
    public static ItemNugget itemNugget;
    public static ItemPlate itemPlate;

    public static void init() {
        itemCrimsonPickaxe = new ItemCrimsonPickaxe("crimson_pickaxe");
        itemDust = new ItemDust("dust");
        itemGem = new ItemGem("gem");
        itemIngot = new ItemIngot("ingot");
        itemNugget = new ItemNugget("nugget");
        itemPlate = new ItemPlate("plate");
    }
}
