package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class TileRegistration {

    public static TileEntityType<CompactorTile> compactorTileType;
    private static IForgeRegistry<TileEntityType<?>> registry;

    public static void registerTE(RegistryEvent.Register<TileEntityType<?>> event) {
        registry = event.getRegistry();

        compactorTileType = register(CompactorTile::new, BlockRegistration.compactorCrimson, BlockRegistration.compactorRefined,
                BlockRegistration.compactorIridescent);
    }

    private static <T extends TileEntity> TileEntityType<T> register(Supplier<T> factoryIn, Block... validBlocks) {
        TileEntityType<T> type = TileEntityType.Builder.create(factoryIn, validBlocks).build(null);
        type.setRegistryName(validBlocks[0].getRegistryName());
        registry.register(type);
        return type;
    }
}
