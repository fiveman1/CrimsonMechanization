package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.RecipeUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableItemOre {

    private String oreName;
    private int oreID = -1;
    private final Item item;
    private final int count;
    private final int meta;

    public ComparableItemOre() {
        this(ItemStack.EMPTY);
    }

    public ComparableItemOre(String oreName) {
        this(oreName, 1);
    }

    public ComparableItemOre(String oreName, int count) {
        this(RecipeUtil.getStackFromOreDict(OreDictionary.getOres(oreName), count));
    }

    public ComparableItemOre(ItemStack itemStack) {
        this(itemStack.getItem(), itemStack.getCount(), itemStack.getMetadata());
    }

    public ComparableItemOre(Item item, int count, int meta) {
        this.item = item;
        this.count = count;
        this.meta = meta;
        checkOres();
    }

    public ItemStack createStack() {
        return createStack(false);
    }

    public ItemStack createStack(boolean isOutput) {
        if (isOutput && isOre()) {
            return RecipeUtil.getModStackFromOreDict(OreDictionary.getOres(oreName), CrimsonMechanization.MODID, count);
        } else {
            return new ItemStack(item, count, meta);
        }
    }

    public List<ItemStack> createStackList() {
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
            return Collections.singletonList(createStack());
        }
    }

    public boolean apply(ItemStack itemStack) {
        return new ComparableItemOre(itemStack).equals(this);
    }

    public int getCount() {
        return count;
    }

    private void checkOres() {
        if (!createStack().isEmpty()) {
            int[] oreIDs = OreDictionary.getOreIDs(createStack());
            if (oreIDs.length > 0) {
                oreID = oreIDs[0];
                oreName = OreDictionary.getOreName(oreID);
            }
        }
    }

    private boolean isOre() {
        return oreID != -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparableItemOre) {
            ComparableItemOre other = (ComparableItemOre) obj;
            if (this.isOre() && other.isOre() && this.oreID == other.oreID) {
                return true;
            } else {
                return ItemStack.areItemsEqual(this.createStack(), other.createStack());
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
        return createStack().getUnlocalizedName();
    }
}
