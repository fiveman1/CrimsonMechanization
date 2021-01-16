package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.util.RecipeUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableOreIngredient {

    public boolean ignoreOreDict = false;
    protected String oreName;
    protected int oreID = -1;
    protected final Item item;
    protected final int count;
    protected final int meta;

    public ComparableOreIngredient() {
        this(ItemStack.EMPTY);
    }

    public ComparableOreIngredient(String oreName) {
        this(oreName, 1);
    }

    public ComparableOreIngredient(String oreName, int count) {
        this(RecipeUtil.getStackFromOreDict(OreDictionary.getOres(oreName), count));
    }

    public ComparableOreIngredient(ItemStack itemStack) {
        this(itemStack.getItem(), itemStack.getCount(), itemStack.getMetadata());
    }

    public ComparableOreIngredient(Item item) {
        this(item, 0, 0);
    }

    public ComparableOreIngredient(Item item, int count) {
        this(item, count, 0);
    }

    public ComparableOreIngredient(Item item, int count, int meta) {
        this.item = item;
        this.count = count;
        this.meta = meta;
        checkOres();
    }

    protected void checkOres() {
        if (!getStack().isEmpty()) {
            int[] oreIDs = OreDictionary.getOreIDs(getStack());
            if (oreIDs.length > 0) {
                oreID = oreIDs[0];
                oreName = OreDictionary.getOreName(oreID);
            }
        }
    }

    public static ComparableOreIngredient fromOreName(String oreName) {
        return new ComparableOreIngredient(oreName);
    }

    public static ComparableOreIngredient fromOreName(String oreName, int count) {
        return new ComparableOreIngredient(oreName, count);
    }

    public static ComparableOreIngredient fromItemStack(ItemStack itemStack) {
        return new ComparableOreIngredient(itemStack);
    }

    public ItemStack getStack() {
        return new ItemStack(item, count, meta);
    }

    public List<ItemStack> getStackList() {
        if (isOre()) {
            List<ItemStack> oreList = OreDictionary.getOres(oreName);
            List<ItemStack> fixedList = new ArrayList<>();
            for (ItemStack stack : oreList) {
                ItemStack copy = stack.copy();
                copy.setCount(count);
                fixedList.add(copy);
            }
            return fixedList;
        } else {
            return Collections.singletonList(getStack());
        }
    }

    public boolean apply(ItemStack itemStack) {
        return new ComparableOreIngredient(itemStack).equals(this);
    }

    public int getCount() {
        return count;
    }

    protected boolean isOre() {
        return oreID != -1 && !ignoreOreDict;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparableOreIngredient) {
            ComparableOreIngredient other = (ComparableOreIngredient) obj;
            if (this.isOre() && other.isOre() && this.oreID == other.oreID) {
                return true;
            } else {
                return ItemStack.areItemsEqual(this.getStack(), other.getStack());
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return isOre() ? oreName.hashCode() : Item.getIdFromItem(item) * 31 + meta;
    }

    @Override
    public String toString() {
        ItemStack stack = getStack();
        return stack.getCount() + "x" + stack.getUnlocalizedName();
    }
}
