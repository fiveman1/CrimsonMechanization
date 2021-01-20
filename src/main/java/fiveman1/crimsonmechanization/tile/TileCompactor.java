package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.enums.EnumMachineTier;
import fiveman1.crimsonmechanization.inventory.container.ContainerCompactor;
import fiveman1.crimsonmechanization.recipe.managers.CompactorRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.IRecipeManager;
import net.minecraft.entity.player.InventoryPlayer;

public class TileCompactor extends TileMachine {
    public TileCompactor() {
    }

    public TileCompactor(EnumMachineTier enumMachineTier) {
        super(enumMachineTier);
    }

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    @Override
    public int getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    protected IRecipeManager getRecipes() {
        return new CompactorRecipeManager();
    }

    public ContainerCompactor createContainer(InventoryPlayer playerInventory) {
        return new ContainerCompactor(playerInventory, this);
    }
}
