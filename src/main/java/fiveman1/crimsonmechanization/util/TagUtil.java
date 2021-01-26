package fiveman1.crimsonmechanization.util;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;

import java.util.List;

public class TagUtil {

    public static String getPathMaterial(String path) {
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == '/') {
                return path.substring(i + 1);
            }
        }
        return "";
    }

    public static Item getModItemFromTag(ITag<Item> tag, boolean strict) {
        List<Item> items = tag.getAllElements();
        if (items.size() == 0) return Items.AIR;
        for (Item item : tag.getAllElements()) {
            if (item.getRegistryName().getNamespace().equals(CrimsonMechanization.MODID)) {
                return item;
            }
        }
        return strict ? Items.AIR : items.get(0);
    }
}
