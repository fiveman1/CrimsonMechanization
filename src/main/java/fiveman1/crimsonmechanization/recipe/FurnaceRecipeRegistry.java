package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.items.ModItems;
import fiveman1.crimsonmechanization.items.materials.EnumMaterial;
import fiveman1.crimsonmechanization.util.RecipeHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class FurnaceRecipeRegistry {

    public static void initRecipes() {
        FurnaceRecipes instance = FurnaceRecipes.instance();
        instance.addSmeltingRecipe(new ItemStack(ModItems.itemDust, 1, EnumMaterial.COAL.getMetadata()), new ItemStack(Items.COAL), 0f);
        String[] oreNames = OreDictionary.getOreNames();
        for (String name : oreNames) {
            ItemStack ore = RecipeHelper.getStrictlyModItemFromOreDict(OreDictionary.getOres(name), CrimsonMechanization.MODID);
            if (!ore.isEmpty() && name.startsWith("dust")) {
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
