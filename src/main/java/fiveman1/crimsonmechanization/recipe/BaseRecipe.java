package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.ItemStack;

public class BaseRecipe {

    protected final ItemStack input;
    protected final ItemStack output;

    public BaseRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

}
