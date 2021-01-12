package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.ItemStack;

public class EnergyRecipe extends BaseRecipe {

    protected int energyRequired;

    public EnergyRecipe(ItemStack input, ItemStack output, int energyRequired) {
        super(input, output);
        this.energyRequired = energyRequired;
    }

    public int getEnergyRequired() {
        return energyRequired;
    }
}
