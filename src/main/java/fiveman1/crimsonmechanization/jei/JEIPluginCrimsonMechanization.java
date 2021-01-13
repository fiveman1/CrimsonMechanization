package fiveman1.crimsonmechanization.jei;

import fiveman1.crimsonmechanization.blocks.ModBlocks;
import fiveman1.crimsonmechanization.inventory.container.ContainerCompactor;
import fiveman1.crimsonmechanization.inventory.container.ContainerCrimsonFurnace;
import fiveman1.crimsonmechanization.inventory.gui.GuiCompactor;
import fiveman1.crimsonmechanization.inventory.gui.GuiCrimsonFurnace;
import fiveman1.crimsonmechanization.jei.categories.CompactorRecipeCategory;
import fiveman1.crimsonmechanization.jei.wrappers.EnergyRecipeWrapper;
import fiveman1.crimsonmechanization.recipe.CompactorRecipeRegistry;
import fiveman1.crimsonmechanization.recipe.EnergyRecipe;
import fiveman1.crimsonmechanization.tile.TileCompactor;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIPluginCrimsonMechanization implements IModPlugin {
    // credit to McJty's tutorial for JEI integration, very useful/instructive
    // https://www.youtube.com/watch?v=Rl0yuOwHN9I

    // cannot instantiate UID's yet because the blocks haven't been instantiated yet
    // so we can't use getRegistryName(), consider adding a static class with registry names
    // for blocks/items to make things easier?
    public static String COMPACTOR_ID;

    @Override
    public void register(IModRegistry registry) {
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();

        // Furnace
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.blockCrimsonFurnace), VanillaRecipeCategoryUid.SMELTING);
        registry.addRecipeClickArea(GuiCrimsonFurnace.class, 76, 35, 21, 16, VanillaRecipeCategoryUid.SMELTING);
        transferRegistry.addRecipeTransferHandler(ContainerCrimsonFurnace.class, VanillaRecipeCategoryUid.SMELTING, 0, TileCrimsonFurnace.INPUT_SLOTS,
                TileCrimsonFurnace.SIZE, 36);

        // Compactor
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.blockCompactor), COMPACTOR_ID);
        registry.addRecipes(CompactorRecipeRegistry.getRecipeCollection(), COMPACTOR_ID);
        registry.handleRecipes(EnergyRecipe.class, EnergyRecipeWrapper::new, COMPACTOR_ID);
        registry.addRecipeClickArea(GuiCompactor.class, 76, 35, 21, 16, COMPACTOR_ID);
        transferRegistry.addRecipeTransferHandler(ContainerCompactor.class, COMPACTOR_ID, 0, TileCompactor.INPUT_SLOTS,
                TileCompactor.SIZE, 36);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = helpers.getGuiHelper();

        /*
            our mod's blocks are registered by this point, categories are registered
            before the normal registries and the recipe categories use the UID's
            so they have to get registered in here, this could cause problems if
            something is registered later than expected but it works so /shrug
         */

        COMPACTOR_ID = ModBlocks.blockCompactor.getRegistryName().toString();
        registry.addRecipeCategories(new CompactorRecipeCategory(guiHelper));
    }
}
