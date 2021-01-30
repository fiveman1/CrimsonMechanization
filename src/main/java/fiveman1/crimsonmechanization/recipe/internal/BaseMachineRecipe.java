package fiveman1.crimsonmechanization.recipe.internal;

import fiveman1.crimsonmechanization.recipe.comparables.ComparableStack;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.List;

public class BaseMachineRecipe implements Comparable<BaseMachineRecipe> {

    protected final List<Ingredient> inputItems = new ArrayList<>();
    protected final List<ItemStack> outputItems = new ArrayList<>();
    protected final List<Integer> outputChances = new ArrayList<>();
    protected final Object2IntOpenHashMap<ComparableStack> inputToCount = new Object2IntOpenHashMap<>();

    protected final int energy;

    public BaseMachineRecipe(List<Ingredient> inputItems, List<ItemStack> outputItems, List<Integer> outputChances, int energy) {
        this.inputItems.addAll(inputItems);
        this.outputItems.addAll(outputItems);
        this.outputChances.addAll(outputChances);
        this.energy = energy;
        for (Ingredient ingredient : inputItems) {
            for (ItemStack stack : ingredient.getMatchingStacks()) {
                inputToCount.put(new ComparableStack(stack), stack.getCount());
            }
        }
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
        return inputToCount.getInt(new ComparableStack(itemStack));
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
