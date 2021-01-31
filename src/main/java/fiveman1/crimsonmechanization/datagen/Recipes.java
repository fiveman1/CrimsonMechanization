package fiveman1.crimsonmechanization.datagen;

import fiveman1.crimsonmechanization.items.ItemRegistration;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_IRON, Items.IRON_INGOT);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_GOLD, Items.GOLD_INGOT);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_DIAMOND, Items.DIAMOND);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_EMERALD, Items.EMERALD);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_LAPIS, Items.LAPIS_LAZULI);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_COAL, Items.COAL);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_CRIMSON, ItemRegistration.GEM_CRIMSON);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_CRIMSON_IRON, ItemRegistration.INGOT_CRIMSON_IRON);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_CRIMSON_STEEL, ItemRegistration.INGOT_CRIMSON_STEEL);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_IRIDESCENT, ItemRegistration.GEM_IRIDESCENT);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_COPPER, ItemRegistration.INGOT_COPPER);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_TIN, ItemRegistration.INGOT_TIN);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_BRONZE, ItemRegistration.INGOT_BRONZE);
        simpleSmeltingRecipe(consumer, ItemRegistration.DUST_NIGHT, ItemRegistration.GEM_NIGHT);
    }

    private void simpleSmeltingRecipe(Consumer<IFinishedRecipe> consumer, Item input, Item output) {
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(input), output, 0.1f, 200)
                .addCriterion("has_item", hasItem(input))
                .build(consumer);
    }
}
