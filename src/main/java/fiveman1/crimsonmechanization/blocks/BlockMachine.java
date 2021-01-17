package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.tile.TileMachine;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class BlockMachine extends BlockBase implements ITileEntityProvider {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockMachine(String name) {
        super(name);
        setSoundType(SoundType.METAL);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        // meta: 4 bits
        // XXFF
        // X: unused
        // F: PropertyDirection FACING (0 - 3, represent a direction north, east, south, or west)
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        // rquired safety check taken from BlockFlowerPot.getActualState()
        TileEntity te = worldIn instanceof ChunkCache ? ((ChunkCache)worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : worldIn.getTileEntity(pos);
        if (te instanceof TileMachine) {
            return state.withProperty(ACTIVE, ((TileMachine) te).isActive());
        }
        return super.getActualState(state, worldIn, pos);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, ACTIVE);
    }

    protected static void removeItems(World worldIn, BlockPos pos, TileEntity tileentity) {
        IItemHandler inventory = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int slots = inventory != null ? inventory.getSlots() : 0;
        for (int i = 0; i < slots; i++) {
            ItemStack itemstack = inventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemstack);
            }
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileMachine) {
            return ((TileMachine) te).getLightLevel();
        }
        return 0;
    }


    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    public abstract boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);
}
