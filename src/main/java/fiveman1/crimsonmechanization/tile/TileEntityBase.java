package fiveman1.crimsonmechanization.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends TileEntity {

    public String name;

    public TileEntityBase(String name) {
        this.name = name;
    }

    // YOU MUST ADD THE EMPTY CONSTRUCTOR TO REGISTER TILE ENTITIES
    public TileEntityBase() {
        super();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        if(oldState != newState) {
            return true;
        }
        return false;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64;
    }


    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return getCapability(capability, facing) != null;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) getItemStackHandler(facing);
        }
        return super.getCapability(capability, facing);
    }

    public abstract IItemHandler getItemStackHandler(@Nullable EnumFacing facing);
}
