package fiveman1.crimsonmechanization.recipe.managers;

import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ComparableIngredientList {

    private final List<ComparableIngredient> ingredientList;
    private final int hashCode;

    public ComparableIngredientList(List<Ingredient> ingredients) {
        ingredientList = new ArrayList<>(ingredients.size());
        int hash = 0;
        for (Ingredient ingredient : ingredients) {
            ComparableIngredient converted = new ComparableIngredient(ingredient);
            ingredientList.add(converted);
            hash += converted.hashCode();
        }
        hashCode = hash;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ComparableIngredientList && ingredientList.containsAll(((ComparableIngredientList) obj).ingredientList);
    }
}
