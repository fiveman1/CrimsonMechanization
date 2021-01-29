package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.recipe.FurnaceRecipe;
import fiveman1.crimsonmechanization.recipe.RecipeTypeRegistration;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;

import java.util.Collections;

public class FurnaceRecipeManager extends AbstractRecipeManager {

    public static final int DEFAULT_ENERGY = 2000;

    private static final FurnaceRecipeManager INSTANCE = new FurnaceRecipeManager();
    public static FurnaceRecipeManager instance() {
        return INSTANCE;
    }

    private FurnaceRecipeManager() {}

    @Override
    protected void onRefresh(RecipeManager recipeManager) {
        for (FurnaceRecipe recipe : recipeManager.getRecipesForType(RecipeTypeRegistration.FURNACE_RECIPE_TYPE)) {
            addRecipe(recipe.getInputItems(), recipe.getOutputItems(), recipe.getOutputChances(), recipe.getEnergy());
        }
        for (net.minecraft.item.crafting.FurnaceRecipe recipe : recipeManager.getRecipesForType(IRecipeType.SMELTING)) {
            addRecipe(recipe.getIngredients(), Collections.singletonList(recipe.getRecipeOutput()), Collections.singletonList(100), DEFAULT_ENERGY);
        }
    }

    @Override
    public String getName() {
        return "Furnace Recipes";
    }
}
