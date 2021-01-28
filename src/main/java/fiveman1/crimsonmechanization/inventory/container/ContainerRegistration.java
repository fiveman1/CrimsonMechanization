package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import fiveman1.crimsonmechanization.tile.AbstractMachineTile;
import fiveman1.crimsonmechanization.tile.CompactorTile;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ContainerRegistration {

    public static ContainerType<CompactorContainer> compactorContainer;
    public static ContainerType<UpgradeContainer> upgradeContainer;

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

        compactorContainer.setRegistryName(BlockRegistration.compactorCrimson.getRegistryName());
        registry.register(compactorContainer);

        upgradeContainer = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            World world = inv.player.world;
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof AbstractMachineTile) {
                return new UpgradeContainer(windowId, inv, (AbstractMachineTile) te);
            }
            return null;
        });

        upgradeContainer.setRegistryName(new ResourceLocation(CrimsonMechanization.MODID, "upgrades"));
        registry.register(upgradeContainer);
    }
}
