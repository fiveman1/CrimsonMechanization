package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CompactorRecipe extends BaseRecipe {

    public CompactorRecipe(ResourceLocation recipeID, List<Ingredient> inputItems, List<ItemStack> outputItems, List<Integer> outputChances, int energy) {
        super(recipeID, inputItems, outputItems, outputChances, energy);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistration.compactorRecipeSerializer;
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeTypeRegistration.COMPACTOR_RECIPE_TYPE;
    }
}
