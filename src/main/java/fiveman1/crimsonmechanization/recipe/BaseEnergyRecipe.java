package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class BaseEnergyRecipe implements Comparable<BaseEnergyRecipe>, IEnergyRecipe {

    // RULES
    // there should be at least 1 input and 1 output
    // that's pretty much it
    // make sure the machine associated with the recipe has the appropriate number of inputs/outputs to support it

    private final int energyRequired;
    // Each index is a slot
    // If a slot has no output/input it should contain a ComparableItemOre() (default constuctor)
    private final List<ComparableItemOre> inputs;
    private final List<ComparableItemOre> outputs;

    // This is used to look up the input count of a given ComparableItemOre
    private final Hashtable<ComparableItemOre, Integer> inputCount = new Hashtable<>();

    public BaseEnergyRecipe(List<ComparableItemOre> inputs, List<ComparableItemOre> outputs, int energyRequired) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.energyRequired = energyRequired;
        for (ComparableItemOre input : inputs) {
            inputCount.put(input, input.getCount());
        }
    }

    public ItemStack getInputSlot(int slot) {
        if (slot < inputs.size()) {
            return inputs.get(slot).getStack();
        }
        return ItemStack.EMPTY;
    }

    public int getInputCount(ItemStack itemStack) {
        return getInputCount(new ComparableItemOre(itemStack));
    }

    public int getInputCount(ComparableItemOre comparableItemOre) {
        return inputCount.get(comparableItemOre);
    }

    public ItemStack getOutputSlot(int slot) {
        if (slot < outputs.size()) {
            return outputs.get(slot).getStack(true);
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
        List<ComparableItemOre> converted = new ArrayList<>();
        for (ItemStack itemStack : inputsToCheck) {
            converted.add(new ComparableItemOre(itemStack));
        }
        if (!converted.containsAll(inputs)) {
            return false;
        }
        if (strict) {
            for (ComparableItemOre inputToCheck : converted) {
                if (inputCount.get(inputToCheck) > inputToCheck.getCount()) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<List<ItemStack>> getInputsList() {
        List<List<ItemStack>> inputsList = new ArrayList<>();
        for (ComparableItemOre input : inputs) {
            inputsList.add(input.getStackList());
        }
        return inputsList;
    }

    public List<ItemStack> getOutputs() {
        List<ItemStack> outputList = new ArrayList<>();
        for (ComparableItemOre output : outputs) {
            outputList.add(output.getStack(true));
        }
        return outputList;
    }

    @Override
    public int getEnergyRequired() {
        return energyRequired;
    }

    @Override
    public int compareTo(BaseEnergyRecipe otherRecipe) {
        if (otherRecipe == null) {
            throw new NullPointerException();
        }
        String thisOutputName = getOutputSlot(0).getDisplayName();
        String otherOutputName = otherRecipe.getOutputSlot(0).getDisplayName();
        return thisOutputName.compareTo(otherOutputName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ComparableItemOre input : inputs) {
            stringBuilder.append(input);
            stringBuilder.append(" + ");
        }
        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
        stringBuilder.append(" = ");
        for (ComparableItemOre output : outputs) {
            stringBuilder.append(output);
            stringBuilder.append(" + ");
        }
        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
        return stringBuilder.toString();
    }
}
