package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.items.materials.ItemDust;
import fiveman1.crimsonmechanization.items.materials.ItemGem;
import fiveman1.crimsonmechanization.items.materials.ItemPlate;
import fiveman1.crimsonmechanization.items.tools.ItemCrimsonPickaxe;

public class ModItems {

    public static ItemCrimsonPickaxe itemCrimsonPickaxe;
    public static ItemDust itemDust;
    public static ItemGem itemGem;
    public static ItemPlate itemPlate;

    public static void init() {
        itemCrimsonPickaxe = new ItemCrimsonPickaxe("crimson_pickaxe");
        itemDust = new ItemDust("dust");
        itemGem = new ItemGem("gem");
        itemPlate = new ItemPlate("plate");
    }
}
