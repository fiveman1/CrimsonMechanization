package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.inventory.container.FurnaceContainer;
import fiveman1.crimsonmechanization.tile.FurnaceTile;

public class FurnaceBlock extends AbstractMachineBlock<FurnaceTile, FurnaceContainer> {

    public static String NAME = "furnace";

    public FurnaceBlock(MachineTier tier) {
        super(NAME, tier, FurnaceTile.class, FurnaceTile::new, FurnaceContainer::new);
    }
}
