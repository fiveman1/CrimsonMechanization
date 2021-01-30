package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.AlloyerBlock;
import fiveman1.crimsonmechanization.blocks.CompactorBlock;
import fiveman1.crimsonmechanization.blocks.CrusherBlock;
import fiveman1.crimsonmechanization.blocks.FurnaceBlock;
import fiveman1.crimsonmechanization.tile.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ContainerRegistration {

    public static ContainerType<AlloyerContainer> ALLOYER_CONTAINER;
    public static ContainerType<CompactorContainer> COMPACTOR_CONTAINER;
    public static ContainerType<CrusherContainer> CRUSHER_CONTAINER;
    public static ContainerType<FurnaceContainer> FURNACE_CONTAINER;
    public static ContainerType<UpgradeContainer> UPGRADE_CONTAINER;

    public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
        IForgeRegistry<ContainerType<?>> registry = event.getRegistry();

        // TODO: abstract container types

        ALLOYER_CONTAINER = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            World world = inv.player.world;
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof AlloyerTile) {
                return new AlloyerContainer(windowId, inv, (AlloyerTile) te);
            }
            return null;
        });
        ALLOYER_CONTAINER.setRegistryName(new ResourceLocation(CrimsonMechanization.MODID, AlloyerBlock.NAME));
        registry.register(ALLOYER_CONTAINER);

        COMPACTOR_CONTAINER = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            World world = inv.player.world;
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof CompactorTile) {
                return new CompactorContainer(windowId, inv, (CompactorTile) te);
            }
            return null;
        });

        COMPACTOR_CONTAINER.setRegistryName(new ResourceLocation(CrimsonMechanization.MODID, CompactorBlock.NAME));
        registry.register(COMPACTOR_CONTAINER);

        CRUSHER_CONTAINER = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            World world = inv.player.world;
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof CrusherTile) {
                return new CrusherContainer(windowId, inv, (CrusherTile) te);
            }
            return null;
        });
        CRUSHER_CONTAINER.setRegistryName(new ResourceLocation(CrimsonMechanization.MODID, CrusherBlock.NAME));
        registry.register(CRUSHER_CONTAINER);

        FURNACE_CONTAINER = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            World world = inv.player.world;
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof FurnaceTile) {
                return new FurnaceContainer(windowId, inv, (FurnaceTile) te);
            }
            return null;
        });
        FURNACE_CONTAINER.setRegistryName(new ResourceLocation(CrimsonMechanization.MODID, FurnaceBlock.NAME));
        registry.register(FURNACE_CONTAINER);

        UPGRADE_CONTAINER = IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            World world = inv.player.world;
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof AbstractMachineTile) {
                return new UpgradeContainer(windowId, inv, (AbstractMachineTile) te);
            }
            return null;
        });

        UPGRADE_CONTAINER.setRegistryName(new ResourceLocation(CrimsonMechanization.MODID, "upgrades"));
        registry.register(UPGRADE_CONTAINER);
    }
}
