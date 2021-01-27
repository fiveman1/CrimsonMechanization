package fiveman1.crimsonmechanization.recipe.managers.old;

import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ComparableIngredientList {

    private final List<ComparableIngredient> ingredientList;
    private final int hashCode;

    public static ComparableIngredientList fromIngredients(List<Ingredient> ingredients) {
        List<ComparableIngredient> convertedList = new ArrayList<>(ingredients.size());
        for (Ingredient ingredient : ingredients) {
            ComparableIngredient converted = new ComparableIngredient(ingredient);
            convertedList.add(converted);
        }
        return new ComparableIngredientList(convertedList);
    }

    public ComparableIngredientList(List<ComparableIngredient> ingredients) {
        ingredientList = ingredients;
        int hash = 0;
        for (ComparableIngredient ingredient : ingredients) {
            hash += ingredient.hashCode();
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
