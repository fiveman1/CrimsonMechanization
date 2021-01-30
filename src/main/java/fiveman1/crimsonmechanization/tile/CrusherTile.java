package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.recipe.managers.CrusherRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.IRecipeManager;

public class CrusherTile extends AbstractMachineTile {

    public static int INPUT_SLOTS = 1;
    public static int OUTPUT_SLOTS = 2;
    public static int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    public CrusherTile() {
        super(TileRegistration.CRUSHER_TILE);
    }

    public CrusherTile(MachineTier machineTier) {
        super(TileRegistration.CRUSHER_TILE, machineTier);
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
        return CrusherRecipeManager.instance();
    }
}
