package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.enums.EnumItemMaterial;
import fiveman1.crimsonmechanization.items.ModItems;
import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.ComparableOreIngredient;
import fiveman1.crimsonmechanization.recipe.ComparableOreIngredientOutput;
import fiveman1.crimsonmechanization.util.RecipeUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.Collections;

public class FurnaceRecipeManager implements IRecipeManager {

    private static final FurnaceRecipes instance = FurnaceRecipes.instance();
    public static final int DEFAULT_ENERGY = 1600;

    @Nullable
    @Override
    public BaseEnergyRecipe getRecipe(ItemStack... inputs) {
        ItemStack output = instance.getSmeltingResult(inputs[0]).copy();
        if (!output.isEmpty()) {
            ItemStack inputCopy = inputs[0].copy();
            inputCopy.setCount(1);
            return new BaseEnergyRecipe(Collections.singletonList(ComparableOreIngredient.fromItemStack(inputCopy)),
                    Collections.singletonList(ComparableOreIngredientOutput.fromItemStack(output)), DEFAULT_ENERGY);
        }
        return null;
    }

    @Override
    public boolean isValidInput(ItemStack input) {
        return !instance.getSmeltingResult(input).isEmpty();
    }

    public static void initRecipes() {
        FurnaceRecipes instance = FurnaceRecipes.instance();
        instance.addSmeltingRecipe(new ItemStack(ModItems.itemDust, 1, EnumItemMaterial.COAL.getMetadata()), new ItemStack(Items.COAL), 0f);
        String[] oreNames = OreDictionary.getOreNames();
        for (String name : oreNames) {
            if (name.startsWith("dust")) {
                ItemStack ore = RecipeUtil.getStrictlyModStackFromOreDict(OreDictionary.getOres(name), CrimsonMechanization.MODID);
                if (!ore.isEmpty()) {
                    String suffix = RecipeUtil.getSuffixFromOreName(name);
                    String outputIngot = "ingot" + suffix;
                    String outputGem = "gem" + suffix;
                    if (OreDictionary.doesOreNameExist(outputIngot)) {
                        ItemStack output = RecipeUtil.getModStackFromOreDict(OreDictionary.getOres(outputIngot), CrimsonMechanization.MODID);
                        instance.addSmeltingRecipe(ore, output, 0f);
                    } else if (OreDictionary.doesOreNameExist(outputGem)) {
                        ItemStack output = RecipeUtil.getModStackFromOreDict(OreDictionary.getOres(outputGem), CrimsonMechanization.MODID);
                        instance.addSmeltingRecipe(ore, output, 0f);
                    }
                }
            }
        }
    }
}
