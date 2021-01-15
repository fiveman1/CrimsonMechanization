package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.util.IngredientUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;

// TODO: remove this and replace with BaseEnergyRecipe

public class SimpleEnergyRecipe implements Comparable<SimpleEnergyRecipe>, IEnergyRecipe {

    protected final Ingredient input;
    protected final int inputCount;
    protected final ItemStack output;
    protected final int energyRequired;

    protected ItemStack[] ingredientMatchingStacks;

    public SimpleEnergyRecipe(Ingredient input, ItemStack output, int energy) {
        this(input, output, energy, 1);
    }

    public SimpleEnergyRecipe(Ingredient input, ItemStack output, int energy, int count) {
        this.input = input;
        this.inputCount = count;
        this.output = output;
        this.energyRequired = energy;

        ingredientMatchingStacks = input.getMatchingStacks();
        IngredientUtil.setMatchingStacksCount(ingredientMatchingStacks, count);
    }

    public SimpleEnergyRecipe(ItemStack input, ItemStack output, int energy) {
        this(Ingredient.fromStacks(input), output, energy, input.getCount());
    }

    public List<ItemStack> getInputs() {
        return Arrays.asList(ingredientMatchingStacks);
    }

    public boolean isValidInputAndCount(ItemStack itemStack) {
        return input.apply(itemStack) && itemStack.getCount() >= inputCount;
    }

    public int getInputCount() {
        return inputCount;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public int getEnergyRequired() {
        return energyRequired;
    }

    @Override
    public int compareTo(SimpleEnergyRecipe otherRecipe) {
        if (otherRecipe == null) {
            throw new NullPointerException();
        }
        String thisOutputName = output.getDisplayName();
        String otherOutputName = otherRecipe.getOutput().getDisplayName();
        return thisOutputName.compareTo(otherOutputName);
    }
}