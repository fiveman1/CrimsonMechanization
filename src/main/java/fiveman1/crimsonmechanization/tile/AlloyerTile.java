package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.recipe.managers.AlloyerRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.IRecipeManager;

public class AlloyerTile extends AbstractMachineTile {

    public static final int INPUT_SLOTS = 2;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    public AlloyerTile() {
        super(TileRegistration.ALLOYER_TILE);
    }

    public AlloyerTile(MachineTier machineTier) {
        super(TileRegistration.ALLOYER_TILE, machineTier);
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
        return AlloyerRecipeManager.instance();
    }
}
