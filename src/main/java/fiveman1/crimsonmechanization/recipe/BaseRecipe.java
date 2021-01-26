package fiveman1.crimsonmechanization.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/*
https://github.com/KingLemming/1.15/blob/a67152e26d4213cb4f055f631b9db3abe698a308/CoFHCore/src/main/java/cofh/core/util/recipes/SerializableRecipe.java#L15
https://github.com/KingLemming/1.15/blob/a67152e26d4213cb4f055f631b9db3abe698a308/ThermalCore/src/main/java/cofh/thermal/core/util/recipes/ThermalRecipe.java

Derived from ThermalCore
 */

public abstract class BaseRecipe implements IRecipe<IInventory> {

    protected final ResourceLocation recipeID;

    protected final List<Ingredient> inputItems = new ArrayList<>();
    protected final List<ItemStack> outputItems = new ArrayList<>();
    protected final List<Integer> outputChances = new ArrayList<>();

    protected final int energy;

    protected BaseRecipe(ResourceLocation recipeID, List<Ingredient> inputItems, List<ItemStack> outputItems, List<Integer> outputChances, int energy) {
        this.recipeID = recipeID;
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

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return recipeID;
    }

    @Override
    public abstract IRecipeSerializer<?> getSerializer();

    @Override
    public abstract IRecipeType<?> getType();
}
