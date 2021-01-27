package fiveman1.crimsonmechanization.recipe.managers;

import com.google.common.collect.Lists;
import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.recipe.comparables.ComparableStack;
import fiveman1.crimsonmechanization.recipe.comparables.ComparableStackList;
import fiveman1.crimsonmechanization.recipe.internal.BaseMachineRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractRecipeManager implements IRecipeManager {

    protected static final Object2ObjectOpenHashMap<ComparableStackList, BaseMachineRecipe> recipeHash = new Object2ObjectOpenHashMap<>();
    protected static List<BaseMachineRecipe> recipes = new ArrayList<>();
    protected static final HashSet<ComparableStack> validInputs = new HashSet<>();

    protected void clear() {
        recipeHash.clear();
        recipes.clear();
        validInputs.clear();
    }

    @Override
    public void refresh(RecipeManager recipeManager) {
        clear();
        onRefresh(recipeManager);
        recipes = new ArrayList<>(new HashSet<>(recipeHash.values()));
        Collections.sort(recipes);

        CrimsonMechanization.LOGGER.debug(getName());
        for (BaseMachineRecipe recipe : recipes) {
            CrimsonMechanization.LOGGER.debug(recipe);
        }
    }

    protected abstract void onRefresh(RecipeManager recipeManager);

    public abstract String getName();

    @Override
    public boolean isValid(ItemStack stack) {
        return validInputs.contains(new ComparableStack(stack));
    }

    @Nullable
    @Override
    public BaseMachineRecipe getRecipe(ItemStack... stacks) {
        return recipeHash.get(ComparableStackList.fromStacks(Arrays.asList(stacks)));
    }

    @Override
    public Collection<BaseMachineRecipe> getRecipeCollection() {
        return recipes;
    }


    protected static void addRecipe(List<Ingredient> inputs, List<ItemStack> outputs, List<Integer> outputChances, int energy) {
        List<List<ComparableStack>> stacksList = new ArrayList<>();
        for (Ingredient ingredient : inputs) {
            List<ComparableStack> stacks = new ArrayList<>();
            for (ItemStack itemStack : ingredient.getMatchingStacks()) {
                ComparableStack comparableStack = new ComparableStack(itemStack);
                validInputs.add(comparableStack);
                stacks.add(comparableStack);
            }
            stacksList.add(stacks);
        }
        BaseMachineRecipe recipe = new BaseMachineRecipe(inputs, outputs, outputChances, energy);
        for (List<ComparableStack> comparableStacks : Lists.cartesianProduct(stacksList)) {
            recipeHash.put(new ComparableStackList(comparableStacks), recipe);
        }
    }

    protected static void setIngredientCount(List<Ingredient> ingredients, int count) {
        for (Ingredient ingredient : ingredients) {
            for (ItemStack itemStack : ingredient.getMatchingStacks()) {
                itemStack.setCount(count);
            }
        }
    }
}