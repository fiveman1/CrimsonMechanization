package fiveman1.crimsonmechanization.jei.wrappers;

import fiveman1.crimsonmechanization.recipe.EnergyRecipe;
import fiveman1.crimsonmechanization.util.ColorHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public class EnergyRecipeWrapper implements IRecipeWrapper {

    private final EnergyRecipe recipe;

    public EnergyRecipeWrapper(EnergyRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, recipe.getInput());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(recipe.getEnergyRequired() + " RF", 0, 0, ColorHelper.GREY);
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
