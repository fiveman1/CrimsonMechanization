package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.tile.TileAlloyer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAlloyer extends BlockMachine {

    public BlockAlloyer(String name) { super(name); }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileAlloyer(getName());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote){
            return true;
        }
        if (!(worldIn.getTileEntity(pos) instanceof TileAlloyer)) {
            return false;
        }
        playerIn.openGui(CrimsonMechanization.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileAlloyer) {
            removeItems(worldIn, pos, tileentity);
        }
        super.breakBlock(worldIn, pos, state);
    }
}
