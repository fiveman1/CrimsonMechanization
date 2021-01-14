package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.items.ModItems;
import fiveman1.crimsonmechanization.items.materials.EnumMaterial;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import fiveman1.crimsonmechanization.util.RecipeHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

public class FurnaceRecipeManager implements IRecipeManager {

    private static final FurnaceRecipes instance = FurnaceRecipes.instance();

    @Override
    @Nullable
    public EnergyRecipe getRecipe(ItemStack... input) {
        ItemStack output = getOutput(input);
        return !output.isEmpty() ? new EnergyRecipe(input[0], output, TileCrimsonFurnace.DEFAULT_ENERGY) : null;
    }

    @Override
    public ItemStack getOutput(ItemStack... input) {
        return instance.getSmeltingResult(input[0]);
    }

    public static void initRecipes() {
        FurnaceRecipes instance = FurnaceRecipes.instance();
        instance.addSmeltingRecipe(new ItemStack(ModItems.itemDust, 1, EnumMaterial.COAL.getMetadata()), new ItemStack(Items.COAL), 0f);
        String[] oreNames = OreDictionary.getOreNames();
        for (String name : oreNames) {
            if (name.startsWith("dust")) {
                ItemStack ore = RecipeHelper.getStrictlyModItemFromOreDict(OreDictionary.getOres(name), CrimsonMechanization.MODID);
                if (!ore.isEmpty()) {
                    String suffix = RecipeHelper.getSuffixFromOreName(name);
                    String outputIngot = "ingot" + suffix;
                    String outputGem = "gem" + suffix;
                    if (OreDictionary.doesOreNameExist(outputIngot)) {
                        ItemStack output = RecipeHelper.getModItemFromOreDict(OreDictionary.getOres(outputIngot), CrimsonMechanization.MODID);
                        instance.addSmeltingRecipe(ore, output, 0f);
                    } else if (OreDictionary.doesOreNameExist(outputGem)) {
                        ItemStack output = RecipeHelper.getModItemFromOreDict(OreDictionary.getOres(outputGem), CrimsonMechanization.MODID);
                        instance.addSmeltingRecipe(ore, output, 0f);
                    }
                }
            }
        }
    }
}
