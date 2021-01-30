package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.recipe.internal.BaseMachineRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;

import java.util.Collection;

public interface IRecipeManager {

    void refresh(RecipeManager recipeManager);

    boolean isValid(ItemStack stack);

    BaseMachineRecipe getRecipe(ItemStack... stacks);

    Collection<BaseMachineRecipe> getRecipeCollection();

    boolean isLoaded();

}
