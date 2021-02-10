package fiveman1.crimsonmechanization.recipe.comparables;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComparableStackList {

    private final ComparableStack[] stacks;
    private final int hashCode;

    public ComparableStackList(List<ComparableStack> stackList) {
        stacks = new ComparableStack[stackList.size()];
        int hash = 0;
        int i = 0;
        for (ComparableStack stack : stackList) {
            hash += stack.hashCode();
            stacks[i++] = stack;
        }
        hashCode = hash;
        Arrays.sort(stacks);
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
        if (obj instanceof ComparableStackList) {
            ComparableStackList other = (ComparableStackList) obj;
            for (int i = 0; i < this.stacks.length; i++) {
                if (!this.stacks[i].equals(other.stacks[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
