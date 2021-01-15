package fiveman1.crimsonmechanization.util;

import net.minecraft.item.ItemStack;

public class IngredientUtil {

    public static void setMatchingStacksCount(ItemStack[] itemStacks, int count) {
        for (ItemStack itemStack : itemStacks) {
            itemStack.setCount(count);
        }
    }
}
