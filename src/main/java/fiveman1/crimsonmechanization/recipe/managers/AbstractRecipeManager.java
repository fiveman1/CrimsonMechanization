package fiveman1.crimsonmechanization.recipe.managers;

import com.google.common.collect.Lists;
import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.recipe.CompactorRecipe;
import fiveman1.crimsonmechanization.recipe.RecipeTypeRegistration;
import fiveman1.crimsonmechanization.recipe.internal.BaseMachineRecipe;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractRecipeManager implements IRecipeManager {

    protected static final Object2ObjectOpenHashMap<ComparableIngredientList, BaseMachineRecipe> recipeHash = new Object2ObjectOpenHashMap<>();
    protected static List<BaseMachineRecipe> recipes = new ArrayList<>();
    protected static final HashSet<Item> validInputs = new HashSet<>();
    protected static final HashSet<ComparableIngredient> validIngredients = new HashSet<>();

    protected void clear() {
        recipeHash.clear();
        recipes.clear();
        validInputs.clear();
        validIngredients.clear();
    }

    @Override
    public void refresh(RecipeManager recipeManager) {
        clear();
        onRefresh(recipeManager);
        recipes = new ArrayList<>(new HashSet<>(recipeHash.values()));
        Collections.sort(recipes);

        for (BaseMachineRecipe recipe : recipes) {
            CrimsonMechanization.LOGGER.debug(recipe);
        }
    }

    protected abstract void onRefresh(RecipeManager recipeManager);

    @Override
    public boolean isValid(ItemStack stack) {
        return validInputs.contains(stack.getItem());
    }

    @Nullable
    @Override
    public BaseMachineRecipe getRecipe(ItemStack... stacks) {
        List<List<Ingredient>> ingredientListsToCheck = new ArrayList<>();
        for (ItemStack stack : stacks) {
            List<Ingredient> validIngredients = generateValidIngredientList(stack);
            if (validIngredients.size() == 0) return null;
            ingredientListsToCheck.add(validIngredients);
        }

        for (List<Ingredient> ingredients : Lists.cartesianProduct(ingredientListsToCheck)) {
            BaseMachineRecipe result = recipeHash.get(new ComparableIngredientList(ingredients));
            if (result != null) return result;
        }

        return null;
    }

    @Override
    public Collection<BaseMachineRecipe> getRecipeCollection() {
        return recipes;
    }

    protected static List<Ingredient> generateValidIngredientList(ItemStack stack) {
        List<Ingredient> ingredients = new ArrayList<>();

        Ingredient baseIngredient = Ingredient.fromStacks(stack);
        if (validIngredients.contains(new ComparableIngredient(baseIngredient))) {
            ingredients.add(Ingredient.fromStacks(stack));
        }

        ITagCollection<Item> tagCollection = ItemTags.getCollection();
        for (ResourceLocation location : stack.getItem().getTags()) {
            ITag<Item> tag = tagCollection.getTagByID(location);
            Ingredient tagIngredient = Ingredient.fromTag(tag);
            if (validIngredients.contains(new ComparableIngredient(tagIngredient))) {
                ingredients.add(Ingredient.fromTag(tag));
            }
        }

        return ingredients;
    }

    protected static void addRecipe(List<Ingredient> inputs, List<ItemStack> outputs, List<Integer> outputChances, int energy) {
        for (Ingredient ingredient : inputs) {
            validIngredients.add(new ComparableIngredient(ingredient));
            for (ItemStack itemStack : ingredient.getMatchingStacks()) {
                validInputs.add(itemStack.getItem());
            }
        }
        recipeHash.put(new ComparableIngredientList(inputs), new BaseMachineRecipe(inputs, outputs, outputChances, energy));
    }

    protected static void setIngredientCount(List<Ingredient> ingredients, int count) {
        for (Ingredient ingredient : ingredients) {
            for (ItemStack itemStack : ingredient.getMatchingStacks()) {
                itemStack.setCount(count);
            }
        }
    }
}
