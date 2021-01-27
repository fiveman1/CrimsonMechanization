package fiveman1.crimsonmechanization.recipe.comparables;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ComparableStack {

    private final Item item;
    private final int hash;

    public ComparableStack(ItemStack stack) {
        item = stack.getItem();
        hash = Item.getIdFromItem(item) * 31;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ComparableStack && ((ComparableStack) obj).item == this.item;
    }
}
