package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.recipe.managers.CompactorRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.IRecipeManager;

public class CompactorTile extends AbstractMachineTile {

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    public CompactorTile() {
        super(TileRegistration.compactorTileType);
    }

    public CompactorTile(MachineTier machineTier) {
        super(TileRegistration.compactorTileType, machineTier);
    }

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
        return CompactorRecipeManager.instance();
    }
}
