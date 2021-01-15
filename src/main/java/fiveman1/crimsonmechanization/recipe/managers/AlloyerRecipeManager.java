package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.recipe.AlloyPair;
import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.ComparableItemOre;
import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.*;

public class AlloyerRecipeManager implements IRecipeManager {

    private static final Hashtable<Pair<ComparableItemOre, ComparableItemOre>, BaseEnergyRecipe> recipesHash = new Hashtable<>();
    private static final ArrayList<BaseEnergyRecipe> recipes = new ArrayList<>();
    private static final HashSet<ComparableItemOre> recipeInputs = new HashSet<>();
    private static final int DEFAULT_ENERGY = 4000;

    @Nullable
    @Override
    public BaseEnergyRecipe getRecipe(ItemStack... inputs) {
        ItemStack input1 = inputs[0];
        ItemStack input2 = inputs[1];
        BaseEnergyRecipe result = recipesHash.get(new Pair<>(new ComparableItemOre(input1), new ComparableItemOre(input2)));
        if (result == null) {
            result = recipesHash.get(new Pair<>(new ComparableItemOre(input2), new ComparableItemOre(input1)));
        }
        return result;
    }

    @Override
    public boolean isValidInput(ItemStack input) {
        return recipeInputs.contains(new ComparableItemOre(input));
    }

    private static void addRecipe(List<ComparableItemOre> inputs, ComparableItemOre output) {
        addRecipe(inputs, output, DEFAULT_ENERGY);
    }

    private static void addRecipe(List<ComparableItemOre> inputs, ComparableItemOre output, int energy) {
        BaseEnergyRecipe recipe = new BaseEnergyRecipe(inputs, Collections.singletonList(output), energy);
        recipesHash.put(new Pair<>(inputs.get(0), inputs.get(1)), recipe);
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
                            List<ComparableItemOre> inputs = new ArrayList<>();
                            String secondOreName = oreTitleSecond + secondName;
                            if (OreDictionary.doesOreNameExist(secondOreName)) {
                                Collections.addAll(inputs, new ComparableItemOre(firstOreName, alloyPair.first.count), new ComparableItemOre(secondOreName, alloyPair.second.count));
                                addRecipe(inputs, new ComparableItemOre(outputOreName, alloyPair.output.count));
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
