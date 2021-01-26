package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.recipe.managers.CompactorRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.IRecipeManager;

public class CompactorTile extends AbstractMachineTile {

    public CompactorTile() {
        super(TileRegistration.compactorTileType);
    }

    public CompactorTile(MachineTier machineTier) {
        super(TileRegistration.compactorTileType, machineTier);
    }

    @Override
    public int getInputSlots() {
        return 1;
    }

    @Override
    public int getOutputSlots() {
        return 1;
    }

    @Override
    protected IRecipeManager getRecipes() {
        return CompactorRecipeManager.instance();
    }
}
