package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.recipe.AlloyPair;
import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.ComparableOreIngredient;
import fiveman1.crimsonmechanization.recipe.ComparableOreIngredientOutput;
import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.*;

public class AlloyerRecipeManager implements IRecipeManager {

    private static final Hashtable<Pair<ComparableOreIngredient, ComparableOreIngredient>, BaseEnergyRecipe> recipeLookup = new Hashtable<>();
    private static final ArrayList<BaseEnergyRecipe> recipes = new ArrayList<>();
    private static final HashSet<ComparableOreIngredient> recipeInputs = new HashSet<>();
    private static final int DEFAULT_ENERGY = 4000;

    @Nullable
    @Override
    public BaseEnergyRecipe getRecipe(ItemStack... inputs) {
        ItemStack input1 = inputs[0];
        ItemStack input2 = inputs[1];
        BaseEnergyRecipe result = recipeLookup.get(new Pair<>(new ComparableOreIngredient(input1), new ComparableOreIngredient(input2)));
        if (result == null) {
            result = recipeLookup.get(new Pair<>(new ComparableOreIngredient(input2), new ComparableOreIngredient(input1)));
        }
        return result;
    }

    @Override
    public boolean isValidInput(ItemStack input) {
        return recipeInputs.contains(new ComparableOreIngredient(input));
    }

    private static void addRecipe(List<ComparableOreIngredient> inputs, ComparableOreIngredientOutput output) {
        addRecipe(inputs, output, DEFAULT_ENERGY);
    }

    private static void addRecipe(List<ComparableOreIngredient> inputs, ComparableOreIngredientOutput output, int energy) {
        BaseEnergyRecipe recipe = new BaseEnergyRecipe(inputs, Collections.singletonList(output), energy);
        recipeLookup.put(new Pair<>(inputs.get(0), inputs.get(1)), recipe);
        recipes.add(recipe);
        AlloyerRecipeManager.recipeInputs.addAll(inputs);
    }

    public static Collection<BaseEnergyRecipe> getRecipeCollection() {
        return recipes;
    }

    public static void initRecipes() {
        String[] oreTitles = {"ingot", "dust", "gem"};
        for (AlloyPair alloyPair : AlloyPair.values) {
            String outputOreName = "ingot" + alloyPair.output.name;
            if (OreDictionary.doesOreNameExist(outputOreName)) {
                String firstName = alloyPair.first.name;
                String secondName = alloyPair.second.name;
                for (String oreTitleFirst: oreTitles) {
                    String firstOreName = oreTitleFirst + firstName;
                    if (OreDictionary.doesOreNameExist(firstOreName)) {
                        for (String oreTitleSecond: oreTitles) {
                            List<ComparableOreIngredient> inputs = new ArrayList<>();
                            String secondOreName = oreTitleSecond + secondName;
                            if (OreDictionary.doesOreNameExist(secondOreName)) {
                                Collections.addAll(inputs, new ComparableOreIngredient(firstOreName, alloyPair.first.count), new ComparableOreIngredient(secondOreName, alloyPair.second.count));
                                addRecipe(inputs, new ComparableOreIngredientOutput(outputOreName, alloyPair.output.count));
                            }
                        }
                    }
                }
            }
        }

        // sort recipe list alphabetically according to the localized name of the outputs
        Collections.sort(recipes);
    }
}
