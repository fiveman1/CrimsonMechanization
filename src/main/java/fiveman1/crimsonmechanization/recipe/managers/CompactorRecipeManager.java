package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.ComparableOreIngredient;
import fiveman1.crimsonmechanization.recipe.ComparableOreIngredientOutput;
import fiveman1.crimsonmechanization.util.RecipeUtil;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

public class CompactorRecipeManager implements IRecipeManager {

    private static final Hashtable<ComparableOreIngredient, BaseEnergyRecipe> recipeLookup = new Hashtable<>();
    private static final ArrayList<BaseEnergyRecipe> recipes = new ArrayList<>();
    private static final int DEFAULT_ENERGY = 3200;

    @Nullable
    @Override
    public BaseEnergyRecipe getRecipe(ItemStack... inputs) {
        return recipeLookup.get(new ComparableOreIngredient(inputs[0]));
    }

    @Override
    public boolean isValidInput(ItemStack input) {
        return getRecipe(input) != null;
    }

    private static void addRecipe(ItemStack input, ItemStack output) {
        addRecipe(ComparableOreIngredient.fromItemStack(input), ComparableOreIngredientOutput.fromItemStack(output));
    }

    private static void addRecipe(ItemStack input, ItemStack output, int energy) {
        addRecipe(ComparableOreIngredient.fromItemStack(input), ComparableOreIngredientOutput.fromItemStack(output), energy);
    }

    private static void addRecipe(ComparableOreIngredient input, ComparableOreIngredientOutput output) {
        addRecipe(input, output, DEFAULT_ENERGY);
    }

    private static void addRecipe(ComparableOreIngredient input, ComparableOreIngredientOutput output, int energy) {
        BaseEnergyRecipe recipe = new BaseEnergyRecipe(Collections.singletonList(input), Collections.singletonList(output), energy);
        recipeLookup.put(input, recipe);
        recipes.add(recipe);
    }

    public static Collection<BaseEnergyRecipe> getRecipeCollection() {
        return recipes;
    }

    public static void initRecipes() {
        // TODO: json recipe implementation
        ComparableOreIngredient boneMeal = ComparableOreIngredient.fromItemStack(new ItemStack(Items.DYE, 16, EnumDyeColor.WHITE.getDyeDamage()));
        boneMeal.ignoreOreDict = true;
        ComparableOreIngredientOutput bone = ComparableOreIngredientOutput.fromItemStack(new ItemStack(Items.BONE));
        addRecipe(new ItemStack(Items.BLAZE_POWDER, 5), new ItemStack(Items.BLAZE_ROD), 2000);
        addRecipe(boneMeal, bone, 4000);

        // auto generate ingot/gem -> plate recipes using oredict
        for (String name : OreDictionary.getOreNames()) {
            if (name.startsWith("ingot") || name.startsWith("gem")) {
                String suffix = RecipeUtil.getSuffixFromOreName(name);
                String outputName = "plate" + suffix;
                if (OreDictionary.doesOreNameExist(outputName)) {
                    addRecipe(new ComparableOreIngredient(name), new ComparableOreIngredientOutput(outputName));
                }
            } else if (name.startsWith("block")) {
                String suffix = RecipeUtil.getSuffixFromOreName(name);
                String outputName = "plate" + suffix;
                if (OreDictionary.doesOreNameExist(outputName)) {
                    addRecipe(new ComparableOreIngredient(name), new ComparableOreIngredientOutput(outputName, 9), DEFAULT_ENERGY * 8);
                }
            }
        }

        // sort recipe list alphabetically according to the localized name of the outputs
        Collections.sort(recipes);
    }
}
