package fiveman1.crimsonmechanization.jei.wrappers;

import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.util.ColorUtil;
import fiveman1.crimsonmechanization.util.StringUtil;
import net.minecraft.client.Minecraft;

public class CrusherRecipeWrapper extends BaseEnergyRecipeWrapper {
    public CrusherRecipeWrapper(BaseEnergyRecipe recipe) {
        super(recipe);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
        int[] chances = recipe.getOutputChances();
        int x = 87;
        for (int chance : chances) {
            if (chance < 100) {
                StringUtil.drawCenteredText(chance + "%", x, 36, ColorUtil.GREY, minecraft.fontRenderer);
            }
            x += 24;
        }
    }
}
