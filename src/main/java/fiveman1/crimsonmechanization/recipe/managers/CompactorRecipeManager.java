package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.ComparableItemOre;
import fiveman1.crimsonmechanization.util.RecipeUtil;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

public class CompactorRecipeManager implements IRecipeManager {

    private static final Hashtable<ComparableItemOre, BaseEnergyRecipe> recipesHash = new Hashtable<>();
    private static final ArrayList<BaseEnergyRecipe> recipes = new ArrayList<>();
    private static final int DEFAULT_ENERGY = 3200;

    @Nullable
    @Override
    public BaseEnergyRecipe getRecipe(ItemStack... inputs) {
        return recipesHash.get(new ComparableItemOre(inputs[0]));
    }
    @Override
    public boolean isValidInput(ItemStack input) {
        return getRecipe(input) != null;
    }

    private static void addRecipe(ItemStack input, ItemStack output) {
        addRecipe(ComparableItemOre.fromItemStack(input), ComparableItemOre.fromItemStack(output));
    }

    private static void addRecipe(ItemStack input, ItemStack output, int energy) {
        addRecipe(ComparableItemOre.fromItemStack(input), ComparableItemOre.fromItemStack(output), energy);
    }

    private static void addRecipe(ComparableItemOre input, ComparableItemOre output) {
        addRecipe(input, output, DEFAULT_ENERGY);
    }

    private static void addRecipe(ComparableItemOre input, ComparableItemOre output, int energy) {
        BaseEnergyRecipe recipe = new BaseEnergyRecipe(Collections.singletonList(input), Collections.singletonList(output), energy);
        recipesHash.put(input, recipe);
        recipes.add(recipe);
    }

    public static Collection<BaseEnergyRecipe> getRecipeCollection() {
        return recipes;
    }

    public static void initRecipes() {
        // TODO: json recipe implementation
        ComparableItemOre boneMeal = ComparableItemOre.fromItemStack(new ItemStack(Items.DYE, 16, EnumDyeColor.WHITE.getDyeDamage()));
        boneMeal.ignoreOreDict = true;
        ComparableItemOre bone = ComparableItemOre.fromItemStack(new ItemStack(Items.BONE));
        addRecipe(new ItemStack(Items.BLAZE_POWDER, 5), new ItemStack(Items.BLAZE_ROD), 2000);
        addRecipe(boneMeal, bone, 4000);

        // auto generate ingot/gem -> plate recipes using oredict
        String[] oreNames = OreDictionary.getOreNames();
        for (String name : oreNames) {
            if (name.startsWith("ingot") || name.startsWith("gem")) {
                String suffix = RecipeUtil.getSuffixFromOreName(name);
                String outputName = "plate" + suffix;
                if (OreDictionary.doesOreNameExist(outputName)) {
                    addRecipe(new ComparableItemOre(name), new ComparableItemOre(outputName));
                }
            }
            if (name.startsWith("block")) {
                String suffix = RecipeUtil.getSuffixFromOreName(name);
                String outputName = "plate" + suffix;
                if (OreDictionary.doesOreNameExist(outputName)) {
                    addRecipe(new ComparableItemOre(name), new ComparableItemOre(outputName, 9), DEFAULT_ENERGY * 8);
                }
            }
        }

        // sort recipe list alphabetically according to the localized name of the outputs
        Collections.sort(recipes);
    }
}
