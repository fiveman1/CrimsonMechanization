package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.tile.TileCompactor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCompactor extends BlockMachine {
    public BlockCompactor(String name) {
        super(name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileCompactor(getName());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote){
            return true;
        }
        if (!(worldIn.getTileEntity(pos) instanceof TileCompactor)) {
            return false;
        }
        playerIn.openGui(CrimsonMechanization.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileCompactor) {
            removeItems(worldIn, pos, tileentity);
        }
        super.breakBlock(worldIn, pos, state);
    }
}
