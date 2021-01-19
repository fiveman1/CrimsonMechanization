package fiveman1.crimsonmechanization.jei;

import fiveman1.crimsonmechanization.blocks.BlockMachine;
import fiveman1.crimsonmechanization.blocks.ModBlocks;
import fiveman1.crimsonmechanization.enums.EnumMachineTier;
import fiveman1.crimsonmechanization.inventory.container.ContainerAlloyer;
import fiveman1.crimsonmechanization.inventory.container.ContainerCompactor;
import fiveman1.crimsonmechanization.inventory.container.ContainerCrimsonFurnace;
import fiveman1.crimsonmechanization.inventory.container.ContainerCrusher;
import fiveman1.crimsonmechanization.inventory.gui.GuiAlloyer;
import fiveman1.crimsonmechanization.inventory.gui.GuiCompactor;
import fiveman1.crimsonmechanization.inventory.gui.GuiCrimsonFurnace;
import fiveman1.crimsonmechanization.inventory.gui.GuiCrusher;
import fiveman1.crimsonmechanization.jei.categories.AlloyerRecipeCategory;
import fiveman1.crimsonmechanization.jei.categories.CompactorRecipeCategory;
import fiveman1.crimsonmechanization.jei.categories.CrusherRecipeCategory;
import fiveman1.crimsonmechanization.jei.wrappers.BaseEnergyRecipeWrapper;
import fiveman1.crimsonmechanization.jei.wrappers.CrusherRecipeWrapper;
import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.managers.AlloyerRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.CompactorRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.CrusherRecipeManager;
import fiveman1.crimsonmechanization.tile.TileAlloyer;
import fiveman1.crimsonmechanization.tile.TileCompactor;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import fiveman1.crimsonmechanization.tile.TileCrusher;
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
    public static String ALLOYER_ID;
    public static String CRUSHER_ID;

    @Override
    public void register(IModRegistry registry) {
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();

        // Furnace
        addMachineRecipeCatalyst(registry, ModBlocks.blockCrimsonFurnace, VanillaRecipeCategoryUid.SMELTING);
        registry.addRecipeClickArea(GuiCrimsonFurnace.class, 76, 35, 21, 16, VanillaRecipeCategoryUid.SMELTING);
        transferRegistry.addRecipeTransferHandler(ContainerCrimsonFurnace.class, VanillaRecipeCategoryUid.SMELTING, 0, TileCrimsonFurnace.INPUT_SLOTS,
                TileCrimsonFurnace.SIZE, 36);

        // Compactor
        addMachineRecipeCatalyst(registry, ModBlocks.blockCompactor, COMPACTOR_ID);
        registry.addRecipes(CompactorRecipeManager.getRecipeCollection(), COMPACTOR_ID);
        registry.handleRecipes(BaseEnergyRecipe.class, BaseEnergyRecipeWrapper::new, COMPACTOR_ID);
        registry.addRecipeClickArea(GuiCompactor.class, 76, 35, 21, 16, COMPACTOR_ID);
        transferRegistry.addRecipeTransferHandler(ContainerCompactor.class, COMPACTOR_ID, 0, TileCompactor.INPUT_SLOTS,
                TileCompactor.SIZE, 36);

        // Alloyer
        addMachineRecipeCatalyst(registry, ModBlocks.blockAlloyer, ALLOYER_ID);
        registry.addRecipes(AlloyerRecipeManager.getRecipeCollection(), ALLOYER_ID);
        registry.handleRecipes(BaseEnergyRecipe.class, BaseEnergyRecipeWrapper::new, ALLOYER_ID);
        registry.addRecipeClickArea(GuiAlloyer.class, 76, 35, 21, 16, ALLOYER_ID);
        transferRegistry.addRecipeTransferHandler(ContainerAlloyer.class, ALLOYER_ID, 0, TileAlloyer.INPUT_SLOTS,
                TileAlloyer.SIZE, 36);

        // Crusher
        addMachineRecipeCatalyst(registry, ModBlocks.blockCrusher, CRUSHER_ID);
        registry.addRecipes(CrusherRecipeManager.getRecipeCollection(), CRUSHER_ID);
        registry.handleRecipes(BaseEnergyRecipe.class, CrusherRecipeWrapper::new, CRUSHER_ID);
        registry.addRecipeClickArea(GuiCrusher.class, 76, 35, 21, 16, CRUSHER_ID);
        transferRegistry.addRecipeTransferHandler(ContainerCrusher.class, CRUSHER_ID, 0, TileCrusher.INPUT_SLOTS,
                TileCrusher.SIZE, 36);
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

        ALLOYER_ID = ModBlocks.blockAlloyer.getRegistryName().toString();
        registry.addRecipeCategories(new AlloyerRecipeCategory(guiHelper));

        CRUSHER_ID = ModBlocks.blockCrusher.getRegistryName().toString();
        registry.addRecipeCategories(new CrusherRecipeCategory(guiHelper));
    }

    private void addMachineRecipeCatalyst(IModRegistry registry, BlockMachine blockMachine, String id) {
        for (EnumMachineTier enumMachineTier : EnumMachineTier.values) {
            registry.addRecipeCatalyst(new ItemStack(blockMachine, 1, enumMachineTier.getMetadata() << 2), id);
        }
    }
}
