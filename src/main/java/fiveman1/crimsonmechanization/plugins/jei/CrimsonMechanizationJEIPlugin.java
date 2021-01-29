package fiveman1.crimsonmechanization.plugins.jei;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import fiveman1.crimsonmechanization.inventory.container.CompactorContainer;
import fiveman1.crimsonmechanization.inventory.container.FurnaceContainer;
import fiveman1.crimsonmechanization.inventory.gui.CompactorScreen;
import fiveman1.crimsonmechanization.inventory.gui.FurnaceScreen;
import fiveman1.crimsonmechanization.plugins.jei.categories.CompactorRecipeCategory;
import fiveman1.crimsonmechanization.plugins.jei.categories.FurnaceRecipeCategory;
import fiveman1.crimsonmechanization.recipe.RecipeTypeRegistration;
import fiveman1.crimsonmechanization.recipe.managers.CompactorRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.FurnaceRecipeManager;
import fiveman1.crimsonmechanization.tile.CompactorTile;
import fiveman1.crimsonmechanization.tile.FurnaceTile;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class CrimsonMechanizationJEIPlugin implements IModPlugin {

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(CompactorContainer.class, RecipeTypeRegistration.COMPACTOR_RECIPE_ID, 0, CompactorTile.INPUT_SLOTS,
                CompactorTile.SIZE, 36);
        registration.addRecipeTransferHandler(FurnaceContainer.class, RecipeTypeRegistration.FURNACE_RECIPE_ID, 0, FurnaceTile.INPUT_SLOTS,
                FurnaceTile.SIZE, 36);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new CompactorRecipeCategory(guiHelper));
        registration.addRecipeCategories(new FurnaceRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(CompactorRecipeManager.instance().getRecipeCollection(), RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registration.addRecipes(FurnaceRecipeManager.instance().getRecipeCollection(), RecipeTypeRegistration.FURNACE_RECIPE_ID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CompactorScreen.class, 76, 35, 21, 16, RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registration.addRecipeClickArea(FurnaceScreen.class, 76, 35, 21, 16, RecipeTypeRegistration.FURNACE_RECIPE_ID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.compactorCrimson), RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.compactorRefined), RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.compactorNight), RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.compactorIridescent), RecipeTypeRegistration.COMPACTOR_RECIPE_ID);

        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.furnaceCrimson), RecipeTypeRegistration.FURNACE_RECIPE_ID, VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.furnaceRefined), RecipeTypeRegistration.FURNACE_RECIPE_ID, VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.furnaceNight), RecipeTypeRegistration.FURNACE_RECIPE_ID, VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.furnaceIridescent), RecipeTypeRegistration.FURNACE_RECIPE_ID, VanillaRecipeCategoryUid.FURNACE);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CrimsonMechanization.MODID, "jei");
    }
}
