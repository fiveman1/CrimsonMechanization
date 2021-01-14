package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class EnergyRecipe extends BaseRecipe {

    protected final int energyRequired;

    public EnergyRecipe(Ingredient input, ItemStack output, int energyRequired) {
        super(input, output);
        this.energyRequired = energyRequired;
    }

    public EnergyRecipe(ItemStack input, ItemStack output, int energyRequired) {
        super(input, output);
        this.energyRequired = energyRequired;
    }

    public int getEnergyRequired() {
        return energyRequired;
    }
}
