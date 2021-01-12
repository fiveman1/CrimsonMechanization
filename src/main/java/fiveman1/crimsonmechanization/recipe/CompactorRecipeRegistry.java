package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.RecipeHelper;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.Hashtable;

public class CompactorRecipeRegistry {

    private static final Hashtable<ComparableItemMeta, EnergyRecipe> compactorRecipes = new Hashtable<>();
    private static final int DEFAULT_ENERGY = 3200;

    @Nullable
    public static EnergyRecipe getRecipe(ItemStack input) {
        return compactorRecipes.get(new ComparableItemMeta(input));
    }

    public static ItemStack getOutput(ItemStack input) {
        EnergyRecipe recipe = compactorRecipes.get(new ComparableItemMeta(input));
        return recipe != null && input.getCount() >= recipe.getInput().getCount() ? recipe.getOutput() : ItemStack.EMPTY;
    }

    private static void addRecipe(ItemStack input, ItemStack output) {
        EnergyRecipe recipe = new EnergyRecipe(input, output, DEFAULT_ENERGY);
        compactorRecipes.put(new ComparableItemMeta(input), recipe);
    }

    private static void addRecipe(ItemStack input, ItemStack output, int energy) {
        EnergyRecipe recipe = new EnergyRecipe(input, output, energy);
        compactorRecipes.put(new ComparableItemMeta(input), recipe);
    }

    public static void initRecipes() {
        addRecipe(new ItemStack(Items.BLAZE_POWDER, 5), new ItemStack(Items.BLAZE_ROD), 2000);
        addRecipe(new ItemStack(Items.DYE, 16, EnumDyeColor.WHITE.getDyeDamage()), new ItemStack(Items.BONE), 4000);

        // auto generate ingot/gem -> plate recipes using oredict
        String[] oreNames = OreDictionary.getOreNames();
        for (String name : oreNames) {
            if (name.startsWith("ingot") || name.startsWith("gem")) {
                String suffix = RecipeHelper.getSuffixFromOreName(name);
                String outputName = "plate" + suffix;
                if (OreDictionary.doesOreNameExist(outputName)) {
                    ItemStack output = RecipeHelper.getModItemFromOreDict(OreDictionary.getOres(outputName), CrimsonMechanization.MODID);
                    for (ItemStack input : OreDictionary.getOres(name)) {
                        addRecipe(input, output);
                    }
                }
            }
        }
    }
}
