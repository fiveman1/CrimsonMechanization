package fiveman1.crimsonmechanization.blocks;

import com.google.common.collect.Maps;
import fiveman1.crimsonmechanization.enums.EnumMachineTier;
import fiveman1.crimsonmechanization.tile.TileMachine;
import fiveman1.crimsonmechanization.util.StringUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public abstract class BlockMachine extends BlockBase {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyEnum<EnumMachineTier> TIER = PropertyEnum.create("tier", EnumMachineTier.class);
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockMachine(String name) {
        super(name);
        setSoundType(SoundType.METAL);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false).withProperty(TIER, EnumMachineTier.CRIMSON));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileMachine) {
            NBTTagCompound nbtTag = stack.getTagCompound();
            if (nbtTag != null) {
                ((TileMachine) te).readRestorableToNBT(nbtTag);
            }
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileMachine) {
            ItemStack itemStack = new ItemStack(Item.getItemFromBlock(this));
            itemStack.setItemDamage(damageDropped(state));
            NBTTagCompound nbtTag = new NBTTagCompound();
            ((TileMachine) te).writeRestorableToNBT(nbtTag);
            itemStack.setTagCompound(nbtTag);
            drops.add(itemStack);
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        NBTTagCompound nbtTag = stack.getTagCompound();
        if (nbtTag != null) {
            tooltip.add(StringUtil.convertTooltip("message.crimsonmechanization.machine_tooltip", nbtTag.getInteger("energy")));
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 0b11)).withProperty(TIER, EnumMachineTier.byMetadata((meta >> 2) & 0b11));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        // meta: 4 bits
        // TTFF
        // T: tier, 0b00 - 0b11 represents one of 4 tiers of machine
        // F: facing, 0b00 - 0b11, represent a direction north, east, south, or west
        return state.getValue(FACING).getHorizontalIndex() | (state.getValue(TIER).getMetadata() << 2);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(TIER).getMetadata() << 2;
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
        return new BlockStateContainer(this, FACING, ACTIVE, TIER);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumMachineTier enumMachineTier : EnumMachineTier.values) {
            items.add(new ItemStack(this, 1, enumMachineTier.getMetadata() << 2));
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileMachine) {
            IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            int slots = inventory != null ? inventory.getSlots() : 0;
            for (int i = 0; i < slots; i++) {
                ItemStack itemstack = inventory.getStackInSlot(i);
                if (!itemstack.isEmpty()) {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemstack);
                }
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileMachine) {
            return ((TileMachine) te).getLightLevel();
        }
        return 0;
    }

    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + EnumMachineTier.byMetadata(stack.getMetadata() >> 2).getUnlocalizedName();
    }

    @Override
    public void initItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlockMachine(this).setRegistryName(getRegistryName()));
    }

    @Override
    public void initModel(ModelRegistryEvent event) {
        ModelLoader.setCustomStateMapper(this, new StateMapperBase() {
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
                map.remove(TIER);
                return new ModelResourceLocation(getRegistryName() + "_" + state.getValue(TIER).getName(), getPropertyString(map));
            }
        });
        for (EnumMachineTier enumMachineTier : EnumMachineTier.values) {
            String location = getRegistryName() + "_" + enumMachineTier.getName();
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), (enumMachineTier.getMetadata() << 2),
                    new ModelResourceLocation(location, "inventory"));
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public abstract TileEntity createTileEntity(World world, IBlockState state);

    @Override
    public abstract boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ);

    static class ItemBlockMachine extends ItemBlock {

        private final BlockMachine blockMachine;

        public ItemBlockMachine(BlockMachine block) {
            super(block);
            this.blockMachine = block;
            setHasSubtypes(true);
            setMaxDamage(0);
            setNoRepair();
        }

        @Override
        public String getUnlocalizedName(ItemStack stack) {
            return blockMachine.getUnlocalizedName(stack);
        }

        @Override
        public int getMetadata(int damage) {
            return damage;
        }
    }
}
