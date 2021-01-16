package fiveman1.crimsonmechanization.recipe;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.RecipeUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ComparableOreIngredientOutput extends ComparableOreIngredient {

    // chance is a percentage, chance should never be <= 0

    public int chance = 100;

    public ComparableOreIngredientOutput() {
        super();
    }

    public ComparableOreIngredientOutput(String oreName) {
        super(oreName);
    }

    public ComparableOreIngredientOutput(String oreName, int count) {
        super(oreName, count);
    }

    public ComparableOreIngredientOutput(ItemStack itemStack) {
        super(itemStack);
    }

    public ComparableOreIngredientOutput(Item item) {
        super(item);
    }

    public ComparableOreIngredientOutput(Item item, int count) {
        super(item, count);
    }

    public ComparableOreIngredientOutput(Item item, int count, int meta) {
        super(item, count, meta);
    }

    public static ComparableOreIngredientOutput fromOreName(String oreName) {
        return new ComparableOreIngredientOutput(oreName);
    }

    public static ComparableOreIngredientOutput fromOreName(String oreName, int count) {
        return new ComparableOreIngredientOutput(oreName, count);
    }

    public static ComparableOreIngredientOutput fromItemStack(ItemStack itemStack) {
        return new ComparableOreIngredientOutput(itemStack);
    }

    @Override
    public ItemStack getStack() {
        if (isOre()) {
            return RecipeUtil.getModStackFromOreDict(OreDictionary.getOres(oreName), CrimsonMechanization.MODID, count);
        } else {
            return super.getStack();
        }
    }

    public ComparableOreIngredient setChance(int chance) {
        this.chance = chance;
        return this;
    }
}
