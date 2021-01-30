package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.inventory.container.AlloyerContainer;
import fiveman1.crimsonmechanization.tile.AlloyerTile;

public class AlloyerBlock extends AbstractMachineBlock<AlloyerTile, AlloyerContainer> {

    public static String NAME = "alloyer";

    public AlloyerBlock(MachineTier tier) {
        super(NAME, tier, AlloyerTile.class, AlloyerTile::new, AlloyerContainer::new);
    }
}
