package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.enums.MachineTier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public abstract class MachineBlock extends Block {

    public static DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static BooleanProperty ACTIVE = BooleanProperty.create("active");

    protected final MachineTier tier;
    protected final String machineName;

    public MachineBlock(String machineName, MachineTier tier) {
        super(AbstractBlock.Properties.create(Material.ROCK)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(0)
            .hardnessAndResistance(3.0f, 5.0f)
            .setLightLevel((state) -> state.get(ACTIVE) ? 12 : 0)
        );
        setRegistryName(CrimsonMechanization.MODID, machineName + "_" + tier.getName());
        setDefaultState(
            getStateContainer().getBaseState()
            .with(FACING, Direction.NORTH)
            .with(ACTIVE, false)
        );
        this.tier = tier;
        this.machineName = machineName + "_" + tier.getName();
    }

    public String getMachineName() {
        return machineName;
    }

    public MachineTier getTier() {
        return tier;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, ACTIVE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);
}
