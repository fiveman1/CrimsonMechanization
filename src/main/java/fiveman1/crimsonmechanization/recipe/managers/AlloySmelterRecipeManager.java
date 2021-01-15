package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.recipe.AlloyPair;
import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.ComparableItemOre;
import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.*;

public class AlloySmelterRecipeManager implements IRecipeManager {

    private static final Hashtable<Pair<ComparableItemOre, ComparableItemOre>, BaseEnergyRecipe> alloySmelterRecipesHash = new Hashtable<>();
    private static final ArrayList<BaseEnergyRecipe> alloySmelterRecipes = new ArrayList<>();
    private static final HashSet<ComparableItemOre> alloySmelterInputs = new HashSet<>();
    private static final int DEFAULT_ENERGY = 4000;

    @Nullable
    public BaseEnergyRecipe getRecipe(ItemStack... itemStacks) {
        ItemStack input1 = itemStacks[0];
        ItemStack input2 = itemStacks[1];
        BaseEnergyRecipe result = alloySmelterRecipesHash.get(new Pair<>(new ComparableItemOre(input1), new ComparableItemOre(input2)));
        if (result == null) {
            result = alloySmelterRecipesHash.get(new Pair<>(new ComparableItemOre(input2), new ComparableItemOre(input1)));
        }
        return result;
    }

    public boolean isValidInput(ItemStack input) {
        return alloySmelterInputs.contains(new ComparableItemOre(input));
    }

    private static void addRecipe(List<ComparableItemOre> inputs, ComparableItemOre output) {
        addRecipe(inputs, output, DEFAULT_ENERGY);
    }

    private static void addRecipe(List<ComparableItemOre> inputs, ComparableItemOre output, int energy) {
        List<ComparableItemOre> outputList = Collections.singletonList(output);
        BaseEnergyRecipe recipe = new BaseEnergyRecipe(inputs, outputList, energy);
        alloySmelterRecipesHash.put(new Pair<>(inputs.get(0), inputs.get(1)), recipe);
        alloySmelterRecipes.add(recipe);
        alloySmelterInputs.addAll(inputs);
    }

    public static Collection<BaseEnergyRecipe> getRecipeCollection() {
        return alloySmelterRecipes;
    }

    public static void initRecipes() {
        String[] oreTitles = {"ingot", "dust", "gem"};
        for (AlloyPair alloyPair : AlloyPair.values) {
            String outputName = "ingot" + alloyPair.outputMaterial;
            if (OreDictionary.doesOreNameExist(outputName)) {
                String firstName = alloyPair.firstMaterial;
                String secondName = alloyPair.secondMaterial;
                for (String oreTitleFirst: oreTitles) {
                    String firstOreName = oreTitleFirst + firstName;
                    if (OreDictionary.doesOreNameExist(firstOreName)) {
                        for (String oreTitleSecond: oreTitles) {
                            List<ComparableItemOre> inputs = new ArrayList<>();
                            String secondOreName = oreTitleSecond + secondName;
                            if (OreDictionary.doesOreNameExist(secondOreName)) {
                                Collections.addAll(inputs, new ComparableItemOre(firstOreName, alloyPair.firstMaterialCount), new ComparableItemOre(secondOreName, alloyPair.secondMaterialCount));
                                addRecipe(inputs, new ComparableItemOre(outputName, alloyPair.outputMaterialCount));
                            }
                        }
                    }
                }
            }
        }
        /*for (BaseEnergyRecipe recipe : alloySmelterRecipes) {
            CrimsonMechanization.logger.info(recipe);
        }*/
    }
}
