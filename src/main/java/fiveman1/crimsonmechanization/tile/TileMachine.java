package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.blocks.BlockMachine;
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

    // TODO: abstract more stuff
    public static int INPUT_SLOTS = 1;
    public static int OUTPUT_SLOTS = 1;
    public static int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    public TileMachine(String name) {
        super(name);
    }

    public TileMachine() { super(); }


    public static final int PROGRESS_ID = 0;
    public static final int ENERGY_ID = 1;
    public static final int CAPACITY_ID = 2;
    public static final int MAX_RECEIVE_ID = 3;
    public static final int MAX_EXTRACT_ID = 4;
    public static final int RECIPE_ENERGY_ID = 5;

    // these MUST BE UPDATED WHEN CHANGED as they are used for the progress bar
    protected int recipeEnergy = 0;
    protected int progress = 0;

    protected int ENERGY_RATE = 20;
    protected final CustomEnergyStorage energyStorage = new CustomEnergyStorage(100000, 120, 0);
    protected int previousEnergyStored = energyStorage.getEnergyStored();
    protected boolean active = false;
    private int counter = 0;

    @Override
    public void update() {
        if (!world.isRemote) {
            startUpdate();
            int currentEnergyStored = energyStorage.getEnergyStored();
            if (canProcess()) {
                if (!active) {
                    updateActive(true);
                    active = true;
                    counter = 0;
                }
                updateProgress();
                energyStorage.consumeEnergy(ENERGY_RATE);
                if (progress >= recipeEnergy) {
                    processRecipe();
                    progress = 0;
                }
                markDirty();


            } else {
                progress = 0;
                active = false;
            }
            if (!active) {
                counter++;
                counter %= 40;
                if (counter == 0) {
                    updateActive(false);
                }
            }
            previousEnergyStored = currentEnergyStored;
            endUpdate();
        }
    }

    protected void updateActive(boolean isActive) {
        IBlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.withProperty(BlockMachine.ACTIVE, isActive), 3);
    }

    // returns true if: inputs are a valid recipe, output has space in output slot,
    // and there is enough energy to complete the recipe
    protected abstract boolean canProcess();

    // updates progress and recipeEnergy, this should reset progress and
    // recipeEnergy if the input has changed
    protected abstract void updateProgress();

    // subtracts inputs from input slots and puts output into output slot
    protected abstract void processRecipe();

    // update any variables at the start of tile update
    protected void startUpdate() {}

    // update any variables at the end of tile update
    protected void endUpdate() {}



    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        energyStorage.readFromNBT(compound);
        progress = compound.getInteger("progress");
        counter = compound.getInteger("counter");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        energyStorage.writeToNBT(compound);
        compound.setInteger("progress", progress);
        compound.setInteger("counter", counter);
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

    public int getField(int id) {
        switch (id) {
            case PROGRESS_ID:
                return progress;
            case ENERGY_ID:
                return energyStorage.getEnergyStored();
            case CAPACITY_ID:
                return energyStorage.getCapacity();
            case MAX_RECEIVE_ID:
                return energyStorage.getMaxReceive();
            case MAX_EXTRACT_ID:
                return energyStorage.getMaxExtract();
            case RECIPE_ENERGY_ID:
                return recipeEnergy;
            default:
                return 0;
        }
    }

    public void setField(int id, int value) {
        switch (id) {
            case PROGRESS_ID:
                progress = value;
                return;
            case ENERGY_ID:
                energyStorage.setEnergy(value);
                return;
            case CAPACITY_ID:
                energyStorage.setCapacity(value);
                return;
            case MAX_RECEIVE_ID:
                energyStorage.setMaxReceive(value);
                return;
            case MAX_EXTRACT_ID:
                energyStorage.setMaxExtract(value);
                return;
            case RECIPE_ENERGY_ID:
                recipeEnergy = value;
        }
    }
}
