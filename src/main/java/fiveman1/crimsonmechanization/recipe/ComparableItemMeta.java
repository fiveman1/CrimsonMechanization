package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ComparableItemMeta {

    // TODO: remove or integrate with ComparableItemOre

    private final Item item;
    private final int meta;

    public ComparableItemMeta(ItemStack itemStack) {
        item = itemStack.getItem();
        meta = itemStack.getItemDamage();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComparableItemMeta) {
            ComparableItemMeta other = (ComparableItemMeta) obj;
            return this.item == other.item && this.meta == other.meta;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = meta;
        for (char c : item.getRegistryName().toString().toCharArray()) {
            hash = hash * 31 + c;
        }
        return hash;
    }
}
