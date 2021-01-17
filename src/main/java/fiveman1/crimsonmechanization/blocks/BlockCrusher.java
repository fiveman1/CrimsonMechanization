package fiveman1.crimsonmechanization.blocks;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.tile.TileCrusher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCrusher extends BlockMachine {

    public BlockCrusher(String name) {
        super(name);
    }


    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCrusher();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote){
            return true;
        }
        if (!(worldIn.getTileEntity(pos) instanceof TileCrusher)) {
            return false;
        }
        playerIn.openGui(CrimsonMechanization.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
