package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.inventory.container.CompactorContainer;
import fiveman1.crimsonmechanization.inventory.container.MachineContainer;
import fiveman1.crimsonmechanization.inventory.container.providers.CustomContainerProvider;
import fiveman1.crimsonmechanization.tile.AbstractMachineTile;
import fiveman1.crimsonmechanization.tile.CompactorTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.*;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public abstract class AbstractMachineBlock<T extends AbstractMachineTile, C extends MachineContainer> extends Block {

    public static DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static BooleanProperty ACTIVE = BooleanProperty.create("active");

    protected final String name;
    protected final MachineTier tier;
    protected final ITileFactory<T> tileFactory;
    protected final Class<T> tileClass;
    protected final IContainerFactory<C, T> containerFactory;

    public AbstractMachineBlock(String machineName, MachineTier tier, Class<T> tileClazz, ITileFactory<T> tileFactory, IContainerFactory<C, T> containerFactory) {
        super(AbstractBlock.Properties.create(Material.ROCK)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(0)
            .hardnessAndResistance(5.0f, 6.0f)
            .setLightLevel((state) -> state.get(ACTIVE) ? 12 : 0)
        );
        setRegistryName(CrimsonMechanization.MODID, machineName + "_" + tier.getName());
        setDefaultState(
            getStateContainer().getBaseState()
            .with(FACING, Direction.NORTH)
            .with(ACTIVE, false)
        );
        this.name = machineName;
        this.tier = tier;
        this.tileFactory = tileFactory;
        this.tileClass = tileClazz;
        this.containerFactory = containerFactory;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return tileFactory.create(tier);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public MachineTier getTier() {
        return tier;
    }


    @SuppressWarnings({"deprecation", "unchecked"})
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileClass.isInstance(tileEntity)) {
                INamedContainerProvider containerProvider = new CustomContainerProvider<>(name,
                        (id, inv) -> containerFactory.create(id, inv, (T) tileEntity));
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
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

    public interface ITileFactory<T extends AbstractMachineTile> {
        T create(MachineTier tier);
    }

    public interface IContainerFactory<C extends MachineContainer, T extends AbstractMachineTile> {
        C create(int id, PlayerInventory inv, T tile);
    }
}
