package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class BaseEnergyRecipe implements Comparable<BaseEnergyRecipe>, IEnergyRecipe {

    // RULES
    // there should be at least 1 input and 1 output
    // if there are inputs of the same type they should have the same count
    // that's pretty much it
    // make sure the machine associated with the recipe has the appropriate number of inputs/outputs to support it

    private final int energyRequired;
    // Each index is a slot
    // If a slot has no output/input it should contain a ComparableItemOre() (default constuctor)
    private final List<ComparableOreIngredient> inputs;
    private final List<ComparableOreIngredientOutput> outputs;

    // cache this for returning
    private final List<ItemStack> outputStacks;

    // This is used to look up the input count of a given ComparableItemOre
    private final Hashtable<ComparableOreIngredient, Integer> inputCount = new Hashtable<>();

    public BaseEnergyRecipe(List<ComparableOreIngredient> inputs, List<ComparableOreIngredientOutput> outputs, int energyRequired) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.energyRequired = energyRequired;
        for (ComparableOreIngredient input : inputs) {
            inputCount.put(input, input.getCount());
        }
        outputStacks = setOutputs(this.outputs);
    }

    protected List<ItemStack> setOutputs(List<ComparableOreIngredientOutput> outputList) {
        List<ItemStack> outputStacksList = new ArrayList<>();
        for (ComparableOreIngredientOutput output : outputList) {
            outputStacksList.add(output.getStack());
        }
        return outputStacksList;
    }

    public ItemStack getInputSlot(int slot) {
        if (slot < inputs.size()) {
            return inputs.get(slot).getStack();
        }
        return ItemStack.EMPTY;
    }

    public int getInputCount(ItemStack itemStack) {
        return getInputCount(new ComparableOreIngredient(itemStack));
    }

    public int getInputCount(ComparableOreIngredient comparableOreIngredient) {
        return inputCount.get(comparableOreIngredient);
    }

    public ItemStack getOutputSlot(int slot) {
        if (slot < outputs.size()) {
            return outputs.get(slot).getStack();
        }
        return ItemStack.EMPTY;
    }

    public boolean isValidInput(ItemStack... itemStacks) {
        return isValidInput(Arrays.asList(itemStacks));
    }

    public boolean isValidInput(List<ItemStack> inputsToCheck) {
        return isValidInput(inputsToCheck, true);
    }

    public boolean isValidInput(List<ItemStack> inputsToCheck, boolean strict) {
        List<ComparableOreIngredient> converted = new ArrayList<>();
        for (ItemStack itemStack : inputsToCheck) {
            converted.add(new ComparableOreIngredient(itemStack));
        }
        if (!converted.containsAll(inputs)) {
            return false;
        }
        if (strict) {
            for (ComparableOreIngredient inputToCheck : converted) {
                if (inputCount.get(inputToCheck) > inputToCheck.getCount()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<List<ItemStack>> getInputsList() {
        List<List<ItemStack>> inputsList = new ArrayList<>();
        for (ComparableOreIngredient input : inputs) {
            inputsList.add(input.getStackList());
        }
        return inputsList;
    }

    public List<ItemStack> getOutputs() {
        return outputStacks;
    }

    public int[] getOutputChances() {
        int[] chances = new int[outputs.size()];
        int i = 0;
        for (ComparableOreIngredientOutput output : outputs) {
            chances[i] = output.chance;
            i++;
        }
        return chances;
    }

    @Override
    public int getEnergyRequired() {
        return energyRequired;
    }

    @Override
    public int compareTo(BaseEnergyRecipe otherRecipe) {
        if (otherRecipe == null) {
            return -1;
        }
        String thisOutputName = getOutputSlot(0).getDisplayName();
        String otherOutputName = otherRecipe.getOutputSlot(0).getDisplayName();
        return thisOutputName.compareTo(otherOutputName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ComparableOreIngredient input : inputs) {
            stringBuilder.append(input);
            stringBuilder.append(" + ");
        }
        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
        stringBuilder.append(" = ");
        for (ComparableOreIngredient output : outputs) {
            stringBuilder.append(output);
            stringBuilder.append(" + ");
        }
        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
        return stringBuilder.toString();
    }
}
