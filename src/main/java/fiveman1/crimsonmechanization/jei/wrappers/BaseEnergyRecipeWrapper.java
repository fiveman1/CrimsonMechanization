package fiveman1.crimsonmechanization.jei.wrappers;

import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.util.ColorUtil;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public class BaseEnergyRecipeWrapper implements IRecipeWrapper {

    public final BaseEnergyRecipe recipe;

    public BaseEnergyRecipeWrapper(BaseEnergyRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getInputsList());
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.getOutputs());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(recipe.getEnergyRequired() + " RF", 0, 0, ColorUtil.GREY);
    }
}
