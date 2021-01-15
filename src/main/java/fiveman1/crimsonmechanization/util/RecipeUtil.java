package fiveman1.crimsonmechanization.util;

import fiveman1.crimsonmechanization.recipe.managers.AlloySmelterRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.CompactorRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.FurnaceRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class RecipeUtil {

    public static ItemStack getStackFromOreDict(NonNullList<ItemStack> ores) {
        return getStackFromOreDict(ores, 1);
    }

    public static ItemStack getStackFromOreDict(NonNullList<ItemStack> ores, int count) {
        ItemStack oreCopy = ores.get(0).copy();
        oreCopy.setCount(count);
        return oreCopy;
    }

    public static ItemStack getModStackFromOreDict(NonNullList<ItemStack> ores, String modID) {
        return getModStackFromOreDict(ores, modID, 1);
    }

    public static ItemStack getModStackFromOreDict(NonNullList<ItemStack> ores, String modID, int count) {
        for (ItemStack ore : ores) {
            if (ore.getItem().getRegistryName().getResourceDomain().equals(modID)) {
                ItemStack oreCopy = ore.copy();
                oreCopy.setCount(count);
                return oreCopy;
            }
        }
        ItemStack oreCopy = ores.get(0).copy();
        oreCopy.setCount(count);
        return oreCopy;
    }

    public static ItemStack getStrictlyModStackFromOreDict(NonNullList<ItemStack> ores, String modID) {
        return getStrictlyModStackFromOreDict(ores, modID, 1);
    }

    public static ItemStack getStrictlyModStackFromOreDict(NonNullList<ItemStack> ores, String modID, int count) {
        for (ItemStack ore : ores) {
            if (ore.getItem().getRegistryName().getResourceDomain().equals(modID)) {
                ItemStack oreCopy = ore.copy();
                oreCopy.setCount(count);
                return oreCopy;
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
        AlloySmelterRecipeManager.initRecipes();
        CompactorRecipeManager.initRecipes();
        FurnaceRecipeManager.initRecipes();
    }
}
