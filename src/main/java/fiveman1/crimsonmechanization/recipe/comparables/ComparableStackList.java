package fiveman1.crimsonmechanization.recipe.comparables;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComparableStackList {

    private final List<ComparableStack> stacks;
    private final int hashCode;

    public ComparableStackList(List<ComparableStack> stackList) {
        stacks = new ArrayList<>(stackList);
        int hash = 0;
        for (ComparableStack stack : stackList) {
            hash += stack.hashCode();
        }
        hashCode = hash;
        stacks.sort(Comparator.comparingInt(ComparableStack::hashCode));
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
            for (int i = 0; i < this.stacks.size(); i++) {
                if (!this.stacks.get(i).equals(other.stacks.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
