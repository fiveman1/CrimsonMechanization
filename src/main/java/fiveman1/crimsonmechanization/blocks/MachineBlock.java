package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.datagen.BlockStates;
import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.tile.AbstractMachineTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public abstract class MachineBlock extends Block {

    public static DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static BooleanProperty ACTIVE = BooleanProperty.create("active");

    protected final MachineTier tier;

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
    }

    public MachineTier getTier() {
        return tier;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof AbstractMachineTile) {
            IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
            int slots = inventory.getSlots();
            for (int i = 0; i < slots; i++) {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT compoundNBT = stack.getTag();
        if (compoundNBT != null && compoundNBT.contains("BlockEntityTag")) {
            CompoundNBT entityTag = compoundNBT.getCompound("BlockEntityTag");
            int energy = entityTag.getInt("energy");
            if (energy != 0) {
                IFormattableTextComponent text = new TranslationTextComponent("info.crimsonmechanization.machine_tooltip").mergeStyle(TextFormatting.BLUE)
                    .append(new StringTextComponent(" " + energy + " RF").mergeStyle(TextFormatting.GRAY));
                tooltip.add(text);
            }
        }
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
