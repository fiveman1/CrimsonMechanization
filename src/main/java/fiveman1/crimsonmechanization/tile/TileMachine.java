package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.util.CustomEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public abstract class TileMachine extends TileEntityBase implements ITickable {

    public final CustomEnergyStorage energyStorage = new CustomEnergyStorage(100000, 120, 0);
    public static final int ENERGY_RATE = 20;

    public TileMachine(String name) {
        super(name);
    }

    public TileMachine() { super(); }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return getCapability(capability, facing) != null;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) getItemStackHandler(facing);
        } else if (capability == CapabilityEnergy.ENERGY) {
            return (T) getEnergyStorage(facing);
        }
        return super.getCapability(capability, facing);
    }

    public abstract IItemHandler getItemStackHandler(@Nullable EnumFacing facing);

    // feel free to override this to prevent access to the energy storage from certain sides
    public CustomEnergyStorage getEnergyStorage(EnumFacing facing) {
        return energyStorage;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            updateTile();
        }
    }

    public abstract void updateTile();

    public abstract int getField(int id);

    public abstract void setField(int id, int value);
}
