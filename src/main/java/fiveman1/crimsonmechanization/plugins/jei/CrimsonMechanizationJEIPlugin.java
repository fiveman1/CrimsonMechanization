package fiveman1.crimsonmechanization.plugins.jei;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import fiveman1.crimsonmechanization.inventory.container.AlloyerContainer;
import fiveman1.crimsonmechanization.inventory.container.CompactorContainer;
import fiveman1.crimsonmechanization.inventory.container.CrusherContainer;
import fiveman1.crimsonmechanization.inventory.container.FurnaceContainer;
import fiveman1.crimsonmechanization.inventory.gui.AlloyerScreen;
import fiveman1.crimsonmechanization.inventory.gui.CompactorScreen;
import fiveman1.crimsonmechanization.inventory.gui.CrusherScreen;
import fiveman1.crimsonmechanization.inventory.gui.FurnaceScreen;
import fiveman1.crimsonmechanization.plugins.jei.categories.AlloyerRecipeCategory;
import fiveman1.crimsonmechanization.plugins.jei.categories.CompactorRecipeCategory;
import fiveman1.crimsonmechanization.plugins.jei.categories.CrusherRecipeCategory;
import fiveman1.crimsonmechanization.plugins.jei.categories.FurnaceRecipeCategory;
import fiveman1.crimsonmechanization.recipe.RecipeTypeRegistration;
import fiveman1.crimsonmechanization.recipe.managers.AlloyerRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.CompactorRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.CrusherRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.FurnaceRecipeManager;
import fiveman1.crimsonmechanization.tile.AlloyerTile;
import fiveman1.crimsonmechanization.tile.CompactorTile;
import fiveman1.crimsonmechanization.tile.CrusherTile;
import fiveman1.crimsonmechanization.tile.FurnaceTile;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class CrimsonMechanizationJEIPlugin implements IModPlugin {

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(AlloyerContainer.class, RecipeTypeRegistration.ALLOYER_RECIPE_ID, 0, AlloyerTile.INPUT_SLOTS,
                AlloyerTile.SIZE, 36);
        registration.addRecipeTransferHandler(CompactorContainer.class, RecipeTypeRegistration.COMPACTOR_RECIPE_ID, 0, CompactorTile.INPUT_SLOTS,
                CompactorTile.SIZE, 36);
        registration.addRecipeTransferHandler(FurnaceContainer.class, RecipeTypeRegistration.FURNACE_RECIPE_ID, 0, FurnaceTile.INPUT_SLOTS,
                FurnaceTile.SIZE, 36);
        registration.addRecipeTransferHandler(CrusherContainer.class, RecipeTypeRegistration.CRUSHER_RECIPE_ID, 0, CrusherTile.INPUT_SLOTS,
                CrusherTile.SIZE, 36);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new AlloyerRecipeCategory(guiHelper));
        registration.addRecipeCategories(new CompactorRecipeCategory(guiHelper));
        registration.addRecipeCategories(new FurnaceRecipeCategory(guiHelper));
        registration.addRecipeCategories(new CrusherRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(AlloyerRecipeManager.instance().getRecipeCollection(), RecipeTypeRegistration.ALLOYER_RECIPE_ID);
        registration.addRecipes(CompactorRecipeManager.instance().getRecipeCollection(), RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registration.addRecipes(FurnaceRecipeManager.instance().getRecipeCollection(), RecipeTypeRegistration.FURNACE_RECIPE_ID);
        registration.addRecipes(CrusherRecipeManager.instance().getRecipeCollection(), RecipeTypeRegistration.CRUSHER_RECIPE_ID);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(AlloyerScreen.class, 76, 35, 21, 16, RecipeTypeRegistration.ALLOYER_RECIPE_ID);
        registration.addRecipeClickArea(CompactorScreen.class, 76, 35, 21, 16, RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registration.addRecipeClickArea(FurnaceScreen.class, 76, 35, 21, 16, RecipeTypeRegistration.FURNACE_RECIPE_ID);
        registration.addRecipeClickArea(CrusherScreen.class, 76, 35, 21, 16, RecipeTypeRegistration.CRUSHER_RECIPE_ID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.ALLOYER_CRIMSON), RecipeTypeRegistration.ALLOYER_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.ALLOYER_REFINED), RecipeTypeRegistration.ALLOYER_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.ALLOYER_NIGHT), RecipeTypeRegistration.ALLOYER_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.ALLOYER_IRIDESCENT), RecipeTypeRegistration.ALLOYER_RECIPE_ID);

        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.COMPACTOR_CRIMSON), RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.COMPACTOR_REFINED), RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.COMPACTOR_NIGHT), RecipeTypeRegistration.COMPACTOR_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.COMPACTOR_IRIDESCENT), RecipeTypeRegistration.COMPACTOR_RECIPE_ID);

        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.FURNACE_CRIMSON), RecipeTypeRegistration.FURNACE_RECIPE_ID, VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.FURNACE_REFINED), RecipeTypeRegistration.FURNACE_RECIPE_ID, VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.FURNACE_NIGHT), RecipeTypeRegistration.FURNACE_RECIPE_ID, VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.FURNACE_IRIDESCENT), RecipeTypeRegistration.FURNACE_RECIPE_ID, VanillaRecipeCategoryUid.FURNACE);

        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.CRUSHER_CRIMSON), RecipeTypeRegistration.CRUSHER_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.CRUSHER_REFINED), RecipeTypeRegistration.CRUSHER_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.CRUSHER_NIGHT), RecipeTypeRegistration.CRUSHER_RECIPE_ID);
        registration.addRecipeCatalyst(new ItemStack(BlockRegistration.CRUSHER_IRIDESCENT), RecipeTypeRegistration.CRUSHER_RECIPE_ID);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CrimsonMechanization.MODID, "jei");
    }
}
