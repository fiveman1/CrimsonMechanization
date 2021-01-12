package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.RecipeHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.Hashtable;

public class CompactorRecipeRegistry {

    private static final Hashtable<ComparableItemMeta, EnergyRecipe> compactorRecipesHash = new Hashtable<>();
    private static final int DEFAULT_ENERGY = 6400;

    @Nullable
    public static EnergyRecipe getRecipe(ItemStack input) {
        return compactorRecipesHash.get(new ComparableItemMeta(input));
    }

    public static ItemStack getOutput(ItemStack input) {
        EnergyRecipe recipe = compactorRecipesHash.get(new ComparableItemMeta(input));
        CrimsonMechanization.logger.info(input.getMetadata());
        return recipe != null && input.getCount() >= recipe.getInput().getCount() ? recipe.getOutput() : ItemStack.EMPTY;
    }

    private static void addRecipe(ItemStack input, ItemStack output) {
        EnergyRecipe recipe = new EnergyRecipe(input, output, DEFAULT_ENERGY);
        compactorRecipesHash.put(new ComparableItemMeta(input), recipe);
    }

    private static void addRecipe(ItemStack input, ItemStack output, int energy) {
        EnergyRecipe recipe = new EnergyRecipe(input, output, energy);
        compactorRecipesHash.put(new ComparableItemMeta(input), recipe);
    }

    public static void initRecipes() {
        String[] oreNames = OreDictionary.getOreNames();
        for (String name : oreNames) {
            if (name.startsWith("ingot") || name.startsWith("gem")) {
                String suffix = RecipeHelper.getSuffixFromOreName(name);
                String outputName = "plate" + suffix;
                if (OreDictionary.doesOreNameExist(outputName)) {
                    ItemStack output = RecipeHelper.getModItemFromOreDict(OreDictionary.getOres(outputName), CrimsonMechanization.MODID);
                    for (ItemStack input : OreDictionary.getOres(name)) {
                        addRecipe(input, output);
                        CrimsonMechanization.logger.info("Recipe registered, input: " + input + ", output: " + output + ", input meta: " + input.getMetadata());
                    }
                }
            }
        }
    }
}
