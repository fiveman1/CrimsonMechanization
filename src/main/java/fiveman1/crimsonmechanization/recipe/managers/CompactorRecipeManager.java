package fiveman1.crimsonmechanization.recipe.managers;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.recipe.*;
import fiveman1.crimsonmechanization.recipe.internal.BaseMachineRecipe;
import fiveman1.crimsonmechanization.util.TagUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.*;

public class CompactorRecipeManager extends AbstractRecipeManager {

    public static final int DEFAULT_ENERGY = 2000;

    private static final CompactorRecipeManager INSTANCE = new CompactorRecipeManager();
    public static CompactorRecipeManager instance() {
        return INSTANCE;
    }

    @Override
    protected void onRefresh(RecipeManager recipeManager) {
        List<CompactorRecipe> recipes = recipeManager.getRecipesForType(RecipeTypeRegistration.COMPACTOR_RECIPE_TYPE);
        for (CompactorRecipe recipe : recipes) {
            addRecipe(recipe.getInputItems(), recipe.getOutputItems(), recipe.getOutputChances(), recipe.getEnergy());
        }
        generateRecipes();
    }

    private static void generateRecipes() {
        ITagCollection<Item> tagCollection = ItemTags.getCollection();
        Collection<ResourceLocation> registeredTags = tagCollection.getRegisteredTags();

        for (ResourceLocation name : registeredTags) {
            if (name.getNamespace().equals("forge") && name.getPath().startsWith("ingots") || name.getPath().startsWith("gems")) {
                String suffix = TagUtil.getPathMaterial(name.getPath());
                if (suffix.equals("")) continue;
                ResourceLocation outputName = new ResourceLocation("forge", "plates/" + suffix);
                if (registeredTags.contains(outputName)) {
                    ITag<Item> inputTag = tagCollection.getTagByID(name);
                    List<Ingredient> input = Collections.singletonList(Ingredient.fromTag(inputTag));
                    List<ItemStack> output = Collections.singletonList(new ItemStack(TagUtil.getModItemFromTag(tagCollection.getTagByID(outputName), false)));
                    List<Integer> chances = Collections.singletonList(100);
                    addRecipe(input, output, chances, DEFAULT_ENERGY);
                }
            }
        }
    }
}
