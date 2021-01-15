package fiveman1.crimsonmechanization.jei.wrappers;

import fiveman1.crimsonmechanization.recipe.SimpleEnergyRecipe;
import fiveman1.crimsonmechanization.util.ColorUtil;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public class SimpleEnergyRecipeWrapper implements IRecipeWrapper {

    public final SimpleEnergyRecipe recipe;

    public SimpleEnergyRecipeWrapper(SimpleEnergyRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipe.getInputs());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(recipe.getEnergyRequired() + " RF", 0, 0, ColorUtil.GREY);
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
