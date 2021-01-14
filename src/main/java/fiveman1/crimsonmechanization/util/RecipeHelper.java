package fiveman1.crimsonmechanization.util;

import fiveman1.crimsonmechanization.recipe.CompactorRecipeManager;
import fiveman1.crimsonmechanization.recipe.FurnaceRecipeManager;
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

    public static ItemStack getStrictlyModItemFromOreDict(NonNullList<ItemStack> ores, String modID) {
        for (ItemStack ore : ores) {
            if (ore.getItem().getRegistryName().getResourceDomain().equals(modID)) {
                return ore;
            }
        }
        return ItemStack.EMPTY;
    }

    public static String getSuffixFromOreName(String oreName) {
        for (int i = 0; i < oreName.length(); i++) {
            if(Character.isUpperCase(oreName.charAt(i))) {
                return oreName.substring(i);
            }
        }
        return "";
    }

    public static void initRecipes() {
        CompactorRecipeManager.initRecipes();
        FurnaceRecipeManager.initRecipes();
    }
}
