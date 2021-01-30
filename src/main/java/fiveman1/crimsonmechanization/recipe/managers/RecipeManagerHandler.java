package fiveman1.crimsonmechanization.recipe.managers;

import net.minecraft.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecipeManagerHandler {

    private static final List<IRecipeManager> RECIPE_MANAGERS = new ArrayList<>();

    public static void init() {
        Collections.addAll(RECIPE_MANAGERS, CompactorRecipeManager.instance(), FurnaceRecipeManager.instance(), CrusherRecipeManager.instance(),
                AlloyerRecipeManager.instance());
    }

    public static void onRefresh(RecipeManager recipeManager) {
        for (IRecipeManager manager : RECIPE_MANAGERS) {
            manager.refresh(recipeManager);
        }
    }
}
