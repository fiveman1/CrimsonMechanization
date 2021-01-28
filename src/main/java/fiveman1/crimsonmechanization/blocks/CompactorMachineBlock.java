package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.inventory.container.CompactorContainer;
import fiveman1.crimsonmechanization.inventory.container.providers.CustomContainerProvider;
import fiveman1.crimsonmechanization.tile.CompactorTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class CompactorMachineBlock extends AbstractMachineBlock<CompactorTile, CompactorContainer> {

    public static String NAME = "compactor";

    public CompactorMachineBlock(MachineTier tier) {
        super(NAME, tier, CompactorTile.class, CompactorTile::new, CompactorContainer::new);
    }
}
