package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockBase;
import fiveman1.crimsonmechanization.blocks.ModBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

    public static final String title = CrimsonMechanization.MODID + ":";

    public static void init() {
        register(TileCompactor.class, ModBlocks.blockCompactor);
        register(TileCrimsonFurnace.class, ModBlocks.blockCrimsonFurnace);
        register(TileAlloyer.class, ModBlocks.blockAlloyer);
        register(TileCrusher.class, ModBlocks.blockCrusher);
    }

    private static void register(Class<? extends TileEntity> teClass, BlockBase block) {
        GameRegistry.registerTileEntity(teClass, title + block.getName());
    }
}
