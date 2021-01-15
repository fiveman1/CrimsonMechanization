package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.recipe.ComparableItemMeta;
import fiveman1.crimsonmechanization.recipe.SimpleEnergyRecipe;
import fiveman1.crimsonmechanization.util.RecipeUtil;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

public class CompactorRecipeManager {

    // TODO replace ComparableItemMeta with ComparableItemOre and SimpleEnergyRecipe with BaseEnergyRecipe

    private static final Hashtable<ComparableItemMeta, SimpleEnergyRecipe> compactorRecipesHash = new Hashtable<>();
    private static final ArrayList<SimpleEnergyRecipe> compactorRecipes = new ArrayList<>();
    private static final int DEFAULT_ENERGY = 3200;

    @Nullable
    public static SimpleEnergyRecipe getRecipe(ItemStack input) {
        return compactorRecipesHash.get(new ComparableItemMeta(input));
    }

    public static boolean isValidInput(ItemStack input) {
        SimpleEnergyRecipe recipe = getRecipe(input);
        return recipe != null;
    }

    private static void addRecipe(Ingredient input, ItemStack output) {
        addRecipe(input, output, DEFAULT_ENERGY);
    }

    private static void addRecipe(Ingredient input, ItemStack output, int energy) {
        SimpleEnergyRecipe recipe = new SimpleEnergyRecipe(input, output, energy);
        compactorRecipes.add(recipe);
        for (ItemStack itemStack : input.getMatchingStacks()) {
            compactorRecipesHash.put(new ComparableItemMeta(itemStack), recipe);
        }
    }

    private static void addRecipe(ItemStack input, ItemStack output) {
        addRecipe(input, output, DEFAULT_ENERGY);
    }

    private static void addRecipe(ItemStack input, ItemStack output, int energy) {
        SimpleEnergyRecipe recipe = new SimpleEnergyRecipe(input, output, energy);
        compactorRecipes.add(recipe);
        compactorRecipesHash.put(new ComparableItemMeta(input), recipe);
    }

    public static Collection<SimpleEnergyRecipe> getRecipeCollection() {
        return compactorRecipes;
    }

    public static void initRecipes() {
        // TODO: json recipe implementation
        addRecipe(new ItemStack(Items.BLAZE_POWDER, 5), new ItemStack(Items.BLAZE_ROD));
        addRecipe(new ItemStack(Items.DYE, 16, EnumDyeColor.WHITE.getDyeDamage()), new ItemStack(Items.BONE), 4000);

        // auto generate ingot/gem -> plate recipes using oredict
        String[] oreNames = OreDictionary.getOreNames();
        for (String name : oreNames) {
            if (name.startsWith("ingot") || name.startsWith("gem")) {
                String suffix = RecipeUtil.getSuffixFromOreName(name);
                String outputName = "plate" + suffix;
                if (OreDictionary.doesOreNameExist(outputName)) {
                    ItemStack output = RecipeUtil.getModStackFromOreDict(OreDictionary.getOres(outputName), CrimsonMechanization.MODID);
                    addRecipe(new OreIngredient(name), output);
                }
            }
        }

        // sort recipe list alphabetically according to the localized name of the outputs
        Collections.sort(compactorRecipes);
    }
}
