package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.items.ModItems;
import fiveman1.crimsonmechanization.items.materials.EnumMaterial;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class CompactorRecipeRegistry {

    // TODO: automatic ore dict registration

    private static final ArrayList<BaseRecipe> compactorRecipes = new ArrayList<>();

    @Nullable
    public static BaseRecipe getRecipe(ItemStack input) {
        for (BaseRecipe recipe : compactorRecipes) {
            if (ItemStack.areItemsEqual(input, recipe.getInput())) {
                return recipe;
            }
        }
        return null;
    }

    public static ItemStack getOutput(ItemStack input) {
        for (BaseRecipe recipe : compactorRecipes) {
            if (ItemStack.areItemsEqual(input, recipe.getInput())) {
                return recipe.getOutput();
            }
        }
        return ItemStack.EMPTY;
    }

    private static void addRecipe(Item input, Item output) {
        compactorRecipes.add(new BaseRecipe(new ItemStack(input), new ItemStack(output)));
    }

    private static void addRecipe(Item input, int inputAmt, Item output, int outputAmt) {
        compactorRecipes.add(new BaseRecipe(new ItemStack(input, inputAmt), new ItemStack(output, outputAmt)));
    }

    private static void addRecipe(ItemStack input, ItemStack output) {
        compactorRecipes.add(new BaseRecipe(input, output));
    }

    public static void initRecipes() {
        ItemStack ironPlate = new ItemStack(ModItems.itemPlate);
        ironPlate.setItemDamage(EnumMaterial.IRON.getMetadata());
        addRecipe(new ItemStack(Items.IRON_INGOT), ironPlate.copy());
    }
}
