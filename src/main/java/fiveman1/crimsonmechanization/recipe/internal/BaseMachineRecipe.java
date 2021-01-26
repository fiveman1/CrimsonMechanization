package fiveman1.crimsonmechanization.recipe.internal;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class BaseMachineRecipe implements Comparable<BaseMachineRecipe> {

    protected final List<Ingredient> inputItems = new ArrayList<>();
    protected final List<ItemStack> outputItems = new ArrayList<>();
    protected final List<Integer> outputChances = new ArrayList<>();

    protected final int energy;

    public BaseMachineRecipe(List<Ingredient> inputItems, List<ItemStack> outputItems, List<Integer> outputChances, int energy) {
        this.inputItems.addAll(inputItems);
        this.outputItems.addAll(outputItems);
        this.outputChances.addAll(outputChances);
        this.energy = energy;
    }

    public List<Ingredient> getInputItems() {
        return inputItems;
    }

    public List<ItemStack> getOutputItems() {
        return outputItems;
    }

    public List<Integer> getOutputChances() {
        return outputChances;
    }

    public int getEnergy() {
        return energy;
    }

    public int getInputCount(ItemStack itemStack) {
        for (Ingredient ingredient : inputItems) {
            if (ingredient.test(itemStack)) {
                return ingredient.getMatchingStacks()[0].getCount();
            }
        }
        return -1;
    }

    @Override
    public int compareTo(BaseMachineRecipe otherRecipe) {
        if (otherRecipe == null) {
            return -1;
        }
        String thisOutputName = outputItems.get(0).getDisplayName().toString();
        String otherOutputName = otherRecipe.outputItems.get(0).getDisplayName().toString();
        return thisOutputName.compareTo(otherOutputName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredient input : inputItems) {
            stringBuilder.append(input.getMatchingStacks()[0].getCount())
                    .append("x")
                    .append(input.getMatchingStacks()[0].getDisplayName().getString())
                    .append(" + ");
        }
        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
        stringBuilder.append(" = ");
        for (ItemStack output : outputItems) {
            stringBuilder.append(output.getCount())
                    .append("x")
                    .append(output.getDisplayName().getString())
                    .append(" + ");
        }
        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length());
        return stringBuilder.toString();
    }
}
