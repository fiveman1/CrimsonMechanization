package fiveman1.crimsonmechanization.recipe.comparables;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ComparableStackList {

    private final List<ComparableStack> stacks;
    private final int hashCode;

    public ComparableStackList(List<ComparableStack> stackList) {
        stacks = stackList;
        int hash = 0;
        for (ComparableStack stack : stackList) {
            hash += stack.hashCode();
        }
        hashCode = hash;
    }

    public static ComparableStackList fromStacks(List<ItemStack> itemStacks) {
        List<ComparableStack> comparableStacks = new ArrayList<>(itemStacks.size());
        for (ItemStack stack : itemStacks) {
            comparableStacks.add(new ComparableStack(stack));
        }
        return new ComparableStackList(comparableStacks);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ComparableStackList && ((ComparableStackList) obj).stacks.containsAll(this.stacks);
    }
}
