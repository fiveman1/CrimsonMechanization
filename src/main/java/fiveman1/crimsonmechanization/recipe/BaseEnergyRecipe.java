package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class BaseEnergyRecipe implements Comparable<BaseEnergyRecipe>, IEnergyRecipe {

    private final int energyRequired;
    // Each index is a slot
    // If a slot has no output/input it should contain a ComparableItemOre() (default constuctor)
    private final List<ComparableItemOre> inputs;
    private final List<ComparableItemOre> outputs;

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
            return inputs.get(slot).createStack();
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
            return outputs.get(slot).createStack(true);
        }
        return ItemStack.EMPTY;
    }

    public boolean isValidInput(List<ItemStack> inputsToCheck) {
        return isValidInput(inputsToCheck, false);
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
            inputsList.add(input.createStackList());
        }
        return inputsList;
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
        ItemStack input1 = inputs.get(0).createStack();
        ItemStack input2 = inputs.get(1).createStack();
        ItemStack output1 = outputs.get(0).createStack();
        return input1.getCount() + " " + input1.getUnlocalizedName() + " + " + input2.getCount() + " " + input2.getUnlocalizedName() + " = " + output1.getCount() + " " + output1.getUnlocalizedName();
    }
}
