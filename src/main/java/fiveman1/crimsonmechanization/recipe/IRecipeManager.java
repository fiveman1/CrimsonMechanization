package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.ItemStack;

public interface IRecipeManager {

    public BaseRecipe getRecipe(ItemStack input);

    public ItemStack getOutput(ItemStack input);}
