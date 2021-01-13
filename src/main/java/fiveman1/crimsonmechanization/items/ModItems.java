package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.items.materials.*;
import fiveman1.crimsonmechanization.items.tools.*;

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
    }
}
