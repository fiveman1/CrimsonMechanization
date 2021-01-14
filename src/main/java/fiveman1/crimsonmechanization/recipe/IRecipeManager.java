package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Implements methods used by machines that support recipes
 */
public interface IRecipeManager {

    /**
     * @param input A list of inputs
     * @return Returns the recipe associated with the inputs, or null
     */
    @Nullable
    BaseRecipe getRecipe(ItemStack... input);

    /**
     * @param input A list of inputs
     * @return Returns the output of the recipe associated with the inputs, or ItemStack.EMPTY
     */
    ItemStack getOutput(ItemStack... input);

}
