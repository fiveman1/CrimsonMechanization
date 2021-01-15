package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface IRecipeManager {

    @Nullable
    BaseEnergyRecipe getRecipe(ItemStack... itemStacks);

    boolean isValidInput(ItemStack input);
}
