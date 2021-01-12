package fiveman1.crimsonmechanization.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class RecipeHelper {

    public static ItemStack getModItemFromOreDict(NonNullList<ItemStack> ores, String modID) {
        for (ItemStack ore : ores) {
            if (ore.getItem().getRegistryName().getResourceDomain().equals(modID)) {
                return ore;
            }
        }
        return ores.get(0);
    }

    public static String getSuffixFromOreName(String oreName) {
        for (int i = 0; i < oreName.length(); i++) {
            if(Character.isUpperCase(oreName.charAt(i))) {
                return oreName.substring(i);
            }
        }
        return "";
    }
}
