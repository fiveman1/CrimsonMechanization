package fiveman1.crimsonmechanization.jei;

import fiveman1.crimsonmechanization.blocks.ModBlocks;
import fiveman1.crimsonmechanization.inventory.gui.GuiCrimsonFurnace;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIPluginCrimsonMechanization implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.blockCrimsonFurnace), VanillaRecipeCategoryUid.SMELTING);

        registry.addRecipeClickArea(GuiCrimsonFurnace.class, 76, 35, 21, 16, VanillaRecipeCategoryUid.SMELTING);
    }
}
