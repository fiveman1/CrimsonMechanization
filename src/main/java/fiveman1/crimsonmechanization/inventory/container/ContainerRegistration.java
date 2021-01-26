package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import fiveman1.crimsonmechanization.tile.CompactorTile;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ContainerRegistration {

    public static ContainerType<CompactorContainer> compactorContainer;

    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        IForgeRegistry<ContainerType<?>> registry = event.getRegistry();

        compactorContainer = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            World world = inv.player.world;
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof CompactorTile) {
                return new CompactorContainer(windowId, inv, (CompactorTile) te);
            }
            return null;
        });

        compactorContainer.setRegistryName(BlockRegistration.compactorMachineBlockCrimson.getRegistryName());
        registry.register(compactorContainer);
    }
}
