package fiveman1.crimsonmechanization.recipe.managers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ComparableIngredient {

    private final List<Item> items;
    private final int hashCode;

    public ComparableIngredient(Ingredient ingredient) {
        int hash = 0;
        ItemStack[] matchingStacks = ingredient.getMatchingStacks();
        items = new ArrayList<>(matchingStacks.length);
        for (ItemStack stack : matchingStacks) {
            items.add(stack.getItem());
            hash += Item.getIdFromItem(stack.getItem());
        }
        hashCode = hash;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ComparableIngredient && ((ComparableIngredient) obj).items.containsAll(items);
    }
}
