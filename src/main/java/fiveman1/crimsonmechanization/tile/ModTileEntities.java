package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.ModBlocks;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static void init() {
        GameRegistry.registerTileEntity(TileCrimsonFurnace.class, CrimsonMechanization.MODID + ":" + ModBlocks.blockCrimsonFurnace.getName());
    }
}
