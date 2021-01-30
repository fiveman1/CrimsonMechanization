package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.inventory.container.CrusherContainer;
import fiveman1.crimsonmechanization.tile.CrusherTile;

public class CrusherBlock extends AbstractMachineBlock<CrusherTile, CrusherContainer> {

    public static String NAME = "crusher";

    public CrusherBlock(MachineTier tier) {
        super(NAME, tier, CrusherTile.class, CrusherTile::new, CrusherContainer::new);
    }
}
