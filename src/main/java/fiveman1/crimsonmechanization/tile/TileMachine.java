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
    protected int ENERGY_RATE = 20;
    protected int progress = 0;
    protected int energyStored = 0;
    protected int capacity = 0;
    protected int maxReceive = 0;
    protected int maxExtract = 0;

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
        progress = compound.getInteger("progress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        compound.setInteger("progress", progress);
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

    public int getField(int id) {
        switch (id) {
            case 0:
                return progress;
            case 1:
                return energyStored;
            case 2:
                return capacity;
            case 3:
                return maxReceive;
            case 4:
                return maxExtract;
            default:
                return 0;

        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                progress = value;
            case 1:
                energyStored = value;
            case 2:
                capacity = value;
            case 3:
                maxReceive = value;
            case 4:
                maxExtract = value;
        }
    }
}
