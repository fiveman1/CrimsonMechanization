package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.RecipeHelper;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.*;

public class CompactorRecipeRegistry {

    private static final Hashtable<ComparableItemMeta, EnergyRecipe> compactorRecipesHash = new Hashtable<>();
    private static final ArrayList<EnergyRecipe> compactorRecipes = new ArrayList<>();
    private static final int DEFAULT_ENERGY = 3200;

    @Nullable
    public static EnergyRecipe getRecipe(ItemStack input) {
        return compactorRecipesHash.get(new ComparableItemMeta(input));
    }

    public static ItemStack getOutput(ItemStack input) {
        EnergyRecipe recipe = getRecipe(input);
        return recipe != null && recipe.isValidInputCount(input) ? recipe.getOutput() : ItemStack.EMPTY;
    }

    public static int getRecipeEnergy(ItemStack input) {
        EnergyRecipe recipe = getRecipe(input);
        return recipe != null ? recipe.getEnergyRequired() : 0;
    }

    private static void addRecipe(List<ItemStack> inputs, ItemStack output) {
        addRecipe(inputs, output, DEFAULT_ENERGY);
    }

    private static void addRecipe(List<ItemStack> inputs, ItemStack output, int energy) {
        EnergyRecipe recipe = new EnergyRecipe(inputs, output, energy);
        compactorRecipes.add(recipe);
        for (ItemStack input : inputs) {
            compactorRecipesHash.put(new ComparableItemMeta(input), recipe);
        }
    }

    private static void addRecipe(ItemStack input, ItemStack output) {
        addRecipe(input, output, DEFAULT_ENERGY);
    }

    private static void addRecipe(ItemStack input, ItemStack output, int energy) {
        EnergyRecipe recipe = new EnergyRecipe(input, output, energy);
        compactorRecipes.add(recipe);
        compactorRecipesHash.put(new ComparableItemMeta(input), recipe);
    }

    public static Collection<EnergyRecipe> getRecipeCollection() {
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
                String suffix = RecipeHelper.getSuffixFromOreName(name);
                String outputName = "plate" + suffix;
                if (OreDictionary.doesOreNameExist(outputName)) {
                    ItemStack output = RecipeHelper.getModItemFromOreDict(OreDictionary.getOres(outputName), CrimsonMechanization.MODID);
                    addRecipe(OreDictionary.getOres(name), output);
                }
            }
        }

        // sort recipe list alphabetically according to the localized name of the outputs
        Collections.sort(compactorRecipes);
    }
}
