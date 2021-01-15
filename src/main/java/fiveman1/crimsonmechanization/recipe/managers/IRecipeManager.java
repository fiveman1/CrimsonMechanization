package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface IRecipeManager {

    /**
     * @param inputs an array of inputs
     * @return Returns the recipe associated with the inputs, or null, this should return a valid recipe even
     * if the machine does not have enough of each individual itemstack to satisfy the recipe (but it does have every stack)
     */
    @Nullable
    BaseEnergyRecipe getRecipe(ItemStack... inputs);

    /**
     * @param input a single input
     * @return returns true if this item is used as an input in ANY associated recipe
     */
    boolean isValidInput(ItemStack input);
}
