package fiveman1.crimsonmechanization.recipe;

import net.minecraft.item.ItemStack;

import java.util.List;

public class EnergyRecipe extends BaseRecipe {

    protected int energyRequired;

    public EnergyRecipe(List<ItemStack> inputs, ItemStack output, int energyRequired) {
        super(inputs, output);
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
