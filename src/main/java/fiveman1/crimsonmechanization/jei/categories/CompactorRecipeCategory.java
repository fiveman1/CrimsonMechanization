package fiveman1.crimsonmechanization.jei.categories;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.jei.JEIPluginCrimsonMechanization;
import fiveman1.crimsonmechanization.jei.wrappers.EnergyRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class CompactorRecipeCategory implements IRecipeCategory<EnergyRecipeWrapper> {

    private final IDrawable background;
    private final IDrawable progress;

    public CompactorRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(CrimsonMechanization.MODID, "textures/gui/crimson_compactor.png");

        background = guiHelper.createDrawable(location, 44 - 6, 28 - 6, 137 - 44 + 12, 55 - 28 + 12);
        IDrawableStatic arrow = guiHelper.createDrawable(location, 176, 0, 23, 16);
        progress = guiHelper.createAnimatedDrawable(arrow, 80, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public String getUid() {
        return JEIPluginCrimsonMechanization.COMPACTOR_ID;
    }

    @Override
    public String getTitle() {
        return I18n.format("category." + CrimsonMechanization.MODID + ".compactor");
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        progress.draw(minecraft, 37, 13);
    }

    @Override
    public String getModName() {
        return CrimsonMechanization.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, EnergyRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        guiItemStackGroup.init(0, true, 7, 11);
        guiItemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));

        guiItemStackGroup.init(1, false, 77, 11);
        guiItemStackGroup.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
