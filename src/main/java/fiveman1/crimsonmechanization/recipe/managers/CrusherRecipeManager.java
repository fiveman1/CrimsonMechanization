package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.ComparableOreIngredient;
import fiveman1.crimsonmechanization.recipe.ComparableOreIngredientOutput;
import fiveman1.crimsonmechanization.util.RecipeUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.*;

public class CrusherRecipeManager implements IRecipeManager {

    private static final Hashtable<ComparableOreIngredient, BaseEnergyRecipe> recipeLookup = new Hashtable<>();
    private static final ArrayList<BaseEnergyRecipe> recipes = new ArrayList<>();
    private static final int DEFAULT_ENERGY = 4000;

    private static final ArrayList<OrePair> orePairs = new ArrayList<>();
    private static final Hashtable<String, String> orePairsLookup = new Hashtable<>();
    private static final HashSet<String> ignoreList = new HashSet<>();

    static class OrePair {
        public final String first;
        public final String second;
        public boolean reversible = true;
        OrePair(String first, String second) {
            this.first = first;
            this.second = second;
        }
        OrePair(String first, String second, boolean reversible) {
            this.first = first;
            this.second = second;
            this.reversible = reversible;
        }
    }

    static {
        orePairs.add(new OrePair("Iron", "Tin"));
        orePairs.add(new OrePair("Gold", "Copper"));
        orePairs.add(new OrePair("Lead", "Silver"));
        orePairs.add(new OrePair("Nickel", "Iron", false));

        for (OrePair orePair : orePairs) {
            orePairsLookup.put(orePair.first, orePair.second);
            if (orePair.reversible) {
                orePairsLookup.put(orePair.second, orePair.first);
            }
        }

        ignoreList.add("Coal");
        ignoreList.add("Redstone");
        ignoreList.add("Lapis");
        ignoreList.add("Quartz");
    }

    @Nullable
    @Override
    public BaseEnergyRecipe getRecipe(ItemStack... inputs) {
        return recipeLookup.get(new ComparableOreIngredient(inputs[0]));
    }

    @Override
    public boolean isValidInput(ItemStack input) {
        return getRecipe(input) != null;
    }

    private static void addRecipe(ComparableOreIngredient input, List<ComparableOreIngredientOutput> output) {
        addRecipe(input, output, DEFAULT_ENERGY);
    }

    private static void addRecipe(ComparableOreIngredient input, List<ComparableOreIngredientOutput> outputs, int energy) {
        BaseEnergyRecipe recipe = new BaseEnergyRecipe(Collections.singletonList(input), outputs, energy);
        recipeLookup.put(input, recipe);
        recipes.add(recipe);
    }

    public static Collection<BaseEnergyRecipe> getRecipeCollection() {
        return recipes;
    }

    public static void initRecipes() {
        // TODO: json recipe implementation
        for (String name : OreDictionary.getOreNames()) {
            if (name.startsWith("ingot") || name.startsWith("gem")) {
                String suffix = RecipeUtil.getSuffixFromOreName(name);
                String outputName = "dust" + suffix;
                if (OreDictionary.doesOreNameExist(outputName)) {
                    addRecipe(new ComparableOreIngredient(name), Collections.singletonList(new ComparableOreIngredientOutput(outputName)));
                }
            } else if (name.startsWith("ore") && !ignoreList.contains(name)) {
                String suffix = RecipeUtil.getSuffixFromOreName(name);
                String dustName = "dust" + suffix;
                if (OreDictionary.doesOreNameExist("gem" + suffix)) {
                    addRecipe(new ComparableOreIngredient(name), Collections.singletonList(new ComparableOreIngredientOutput("gem" + suffix, 2)));
                } else if (OreDictionary.doesOreNameExist(dustName)) {
                    ComparableOreIngredientOutput firstOutput = new ComparableOreIngredientOutput(dustName, 2);
                    String orePair = orePairsLookup.get(suffix);
                    if (orePair != null && OreDictionary.doesOreNameExist("dust" + orePair)) {
                        ComparableOreIngredientOutput secondOutput = new ComparableOreIngredientOutput("dust" + orePair);
                        secondOutput.chance = 15;
                        addRecipe(new ComparableOreIngredient(name), Arrays.asList(firstOutput, secondOutput));
                    } else {
                        addRecipe(new ComparableOreIngredient(name), Collections.singletonList(firstOutput));
                    }
                }
            }
        }
        // sort recipe list alphabetically according to the localized name of the outputs
        Collections.sort(recipes);
    }
}
