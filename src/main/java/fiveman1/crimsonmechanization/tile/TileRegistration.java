package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class TileRegistration {

    public static TileEntityType<CompactorTile> COMPACTOR_TILE;
    public static TileEntityType<CrusherTile> CRUSHER_TILE;
    public static TileEntityType<FurnaceTile> FURNACE_TILE;
    private static IForgeRegistry<TileEntityType<?>> registry;

    public static void registerTE(RegistryEvent.Register<TileEntityType<?>> event) {
        registry = event.getRegistry();

        COMPACTOR_TILE = register(CompactorTile::new, BlockRegistration.COMPACTOR_CRIMSON, BlockRegistration.COMPACTOR_REFINED,
                BlockRegistration.COMPACTOR_NIGHT, BlockRegistration.COMPACTOR_IRIDESCENT);

        CRUSHER_TILE = register(CrusherTile::new, BlockRegistration.CRUSHER_CRIMSON, BlockRegistration.CRUSHER_REFINED,
                BlockRegistration.CRUSHER_NIGHT, BlockRegistration.CRUSHER_IRIDESCENT);

        FURNACE_TILE = register(FurnaceTile::new, BlockRegistration.FURNACE_CRIMSON, BlockRegistration.FURNACE_REFINED,
                BlockRegistration.FURNACE_NIGHT, BlockRegistration.FURNACE_IRIDESCENT);
    }

    private static <T extends TileEntity> TileEntityType<T> register(Supplier<T> factoryIn, Block... validBlocks) {
        TileEntityType<T> type = TileEntityType.Builder.create(factoryIn, validBlocks).build(null);
        type.setRegistryName(validBlocks[0].getRegistryName());
        registry.register(type);
        return type;
    }
}
