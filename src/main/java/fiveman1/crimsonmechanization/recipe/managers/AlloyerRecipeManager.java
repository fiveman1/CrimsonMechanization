package fiveman1.crimsonmechanization.recipe.managers;

import com.google.common.collect.Lists;
import fiveman1.crimsonmechanization.enums.AlloyPair;
import fiveman1.crimsonmechanization.recipe.AlloyerRecipe;
import fiveman1.crimsonmechanization.recipe.RecipeTypeRegistration;
import fiveman1.crimsonmechanization.util.TagUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AlloyerRecipeManager extends AbstractRecipeManager {

    public static final int DEFAULT_ENERGY = 3000;

    private static final AlloyerRecipeManager INSTANCE = new AlloyerRecipeManager();
    public static AlloyerRecipeManager instance() {
        return INSTANCE;
    }

    private AlloyerRecipeManager() {}


    @Override
    protected void onRefresh(RecipeManager recipeManager) {
        List<AlloyerRecipe> recipes = recipeManager.getRecipesForType(RecipeTypeRegistration.ALLOYER_RECIPE_TYPE);
        for (AlloyerRecipe recipe : recipes) {
            addRecipe(recipe.getInputItems(), recipe.getOutputItems(), recipe.getOutputChances(), recipe.getEnergy());
        }
        generateRecipes();
    }

    @Override
    public String getName() {
        return "Alloyer Recipes";
    }

    private void generateRecipes() {
        ITagCollection<Item> tagCollection = ItemTags.getCollection();
        Collection<ResourceLocation> registeredTags = tagCollection.getRegisteredTags();

        String[] oreTitles = {"ingots/", "dusts/", "gems/"};
        for (AlloyPair alloyPair : AlloyPair.values) {
            ResourceLocation outputLocation = new ResourceLocation("forge", "ingots/" + alloyPair.output.name);
            if (registeredTags.contains(outputLocation)) {
                for (String firstTitle : oreTitles) {
                    ResourceLocation firstLocation = new ResourceLocation("forge", firstTitle + alloyPair.first.name);
                    if (registeredTags.contains(firstLocation)) {
                        for (String secondTitle: oreTitles) {
                            ResourceLocation secondLocation = new ResourceLocation("forge", secondTitle + alloyPair.second.name);
                            if (registeredTags.contains(secondLocation)) {
                                Ingredient firstInput = Ingredient.fromTag(tagCollection.getTagByID(firstLocation));
                                Ingredient secondInput = Ingredient.fromTag(tagCollection.getTagByID(secondLocation));
                                ItemStack output = new ItemStack(TagUtil.getModItemFromTag(tagCollection.getTagByID(outputLocation), false));
                                setIngredientCount(firstInput.getMatchingStacks(), alloyPair.first.count);
                                setIngredientCount(secondInput.getMatchingStacks(), alloyPair.second.count);
                                output.setCount(alloyPair.output.count);
                                List<Ingredient> intputList = Lists.newArrayList(firstInput, secondInput);
                                List<ItemStack> outputList = Collections.singletonList(output);
                                List<Integer> chances = Collections.singletonList(100);
                                addRecipe(intputList, outputList, chances, DEFAULT_ENERGY);
                            }
                        }
                    }
                }
            }
        }
    }
}
