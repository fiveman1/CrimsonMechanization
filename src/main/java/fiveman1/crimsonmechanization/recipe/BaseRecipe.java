package fiveman1.crimsonmechanization.recipe;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseRecipe implements Comparable<BaseRecipe> {

    protected final List<ItemStack> inputs = new ArrayList<>();
    protected final ItemStack output;

    /**
     * @param inputs: a list of valid inputs for the given output
     * @param output: a single ItemStack output
     */
    public BaseRecipe(List<ItemStack> inputs, @Nonnull ItemStack output) {
        this.output = output;
        addInputs(inputs);
    }

    public BaseRecipe(ItemStack input, @Nonnull ItemStack output) {
        this(Lists.newArrayList(input), output);
    }

    public void addInputs(ItemStack... itemStacks) {
        inputs.addAll(Arrays.asList(itemStacks));
    }

    public void addInputs(List<ItemStack> inputs) {
        this.inputs.addAll(inputs);
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public boolean isValidInputCount(ItemStack itemStack) {
        for (ItemStack input : inputs) {
            if (ItemStack.areItemsEqual(itemStack, input) && itemStack.getCount() >= input.getCount()) {
                return true;
            }
        }
        return false;
    }

    public int getInputCount(ItemStack itemStack) {
        for (ItemStack input : inputs) {
            if (ItemStack.areItemsEqual(itemStack, input)) {
                return input.getCount();
            }
        }
        return 0;
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
