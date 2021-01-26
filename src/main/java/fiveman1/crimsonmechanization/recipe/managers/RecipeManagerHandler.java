package fiveman1.crimsonmechanization.recipe.managers;

import net.minecraft.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

public class RecipeManagerHandler {

    private static final List<IRecipeManager> recipeManagers = new ArrayList<>();

    public static void init() {
        recipeManagers.add(CompactorRecipeManager.instance());
    }

    public static void onRefresh(RecipeManager recipeManager) {
        for (IRecipeManager manager : recipeManagers) {
            manager.refresh(recipeManager);
        }
    }
}
