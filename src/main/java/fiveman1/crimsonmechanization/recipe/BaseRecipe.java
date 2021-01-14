package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;

public class BaseRecipe implements Comparable<BaseRecipe> {

    protected final Ingredient input;
    protected final int inputCount;
    protected final ItemStack output;

    public BaseRecipe(Ingredient input, ItemStack output) {
        this(input, output, 1);
    }

    public BaseRecipe(Ingredient input, ItemStack output, int count) {
        this.input = input;
        this.inputCount = count;
        this.output = output;
    }

    public BaseRecipe(ItemStack input, ItemStack output) {
        this(Ingredient.fromStacks(input), output, input.getCount());
    }

    public List<ItemStack> getInputs() {
        return Arrays.asList(input.getMatchingStacks());
    }

    public boolean isValidInputAndCount(ItemStack itemStack) {
        return input.apply(itemStack) && itemStack.getCount() >= inputCount;
    }

    public int getInputCount(ItemStack itemStack) {
        return input.apply(itemStack) ? inputCount : 0;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public int compareTo(BaseRecipe otherRecipe) {
        if (otherRecipe == null) {
            throw new NullPointerException();
        }
        String thisOutputName = output.getDisplayName();
        String otherOutputName = otherRecipe.getOutput().getDisplayName();
        return thisOutputName.compareTo(otherOutputName);
    }
}