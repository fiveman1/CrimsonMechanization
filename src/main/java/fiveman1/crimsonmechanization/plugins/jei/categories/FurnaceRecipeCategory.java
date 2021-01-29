package fiveman1.crimsonmechanization.plugins.jei.categories;

import com.mojang.blaze3d.matrix.MatrixStack;
import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import fiveman1.crimsonmechanization.blocks.CompactorBlock;
import fiveman1.crimsonmechanization.blocks.FurnaceBlock;
import fiveman1.crimsonmechanization.recipe.RecipeTypeRegistration;
import fiveman1.crimsonmechanization.recipe.internal.BaseMachineRecipe;
import fiveman1.crimsonmechanization.util.ColorUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class FurnaceRecipeCategory implements IRecipeCategory<BaseMachineRecipe> {

    private final IDrawable background;
    private final IDrawable progressBar;
    private final IDrawable icon;

    public FurnaceRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(CrimsonMechanization.MODID, "textures/gui/" + FurnaceBlock.NAME + ".png");

        background = guiHelper.createDrawable(location, 38 ,22, 106, 39);
        IDrawableStatic arrow = guiHelper.createDrawable(location, 176, 0, 23, 16);
        progressBar = guiHelper.createAnimatedDrawable(arrow, 80, IDrawableAnimated.StartDirection.LEFT, false);
        icon = guiHelper.createDrawableIngredient(new ItemStack(BlockRegistration.furnaceCrimson));
    }

    @Override
    public ResourceLocation getUid() {
        return RecipeTypeRegistration.FURNACE_RECIPE_ID;
    }

    @Override
    public Class<? extends BaseMachineRecipe> getRecipeClass() {
        return BaseMachineRecipe.class;
    }

    @Override
    public String getTitle() {
        return I18n.format("container." + CrimsonMechanization.MODID + "." + FurnaceBlock.NAME);
    }

    @Override
    public void draw(BaseMachineRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        progressBar.draw(matrixStack, 37, 13);
        Minecraft.getInstance().fontRenderer.drawString(matrixStack, I18n.format("info.crimsonmechanization.recipe_energy", recipe.getEnergy()), 0, 0, ColorUtil.GREY);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(BaseMachineRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getInputItems());
        ingredients.setOutputs(VanillaTypes.ITEM, recipe.getOutputItems());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BaseMachineRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 7, 11);
        guiItemStacks.init(1, false, 77, 11);
        guiItemStacks.set(ingredients);
    }
}
