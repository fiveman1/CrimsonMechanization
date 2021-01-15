package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.blocks.BlockMachine;
import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.managers.IRecipeManager;
import fiveman1.crimsonmechanization.util.CustomEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class TileMachine extends TileEntityBase implements ITickable {

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
    protected boolean blockStateActive = false;
    private int counter = 0;

    public abstract int getInputSlots();
    public abstract int getOutputSlots();
    public int getSize() {
        return getInputSlots() + getOutputSlots();
    }
    protected abstract IRecipeManager getRecipes();
    protected IRecipeManager recipes = getRecipes();

    protected boolean isInputValid(ItemStack itemStack) {
        return recipes.isValidInput(itemStack);
    }

    protected final ItemStackHandler inputHandler = new ItemStackHandler(getInputSlots()) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return isInputValid(stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };
    protected final ItemStackHandler outputHandler = new ItemStackHandler(getOutputSlots()) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };
    protected final CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    protected ItemStack[] getStacks(ItemStackHandler itemStackHandler) {
        int slots = itemStackHandler.getSlots();
        ItemStack[] stacks = new ItemStack[slots];
        for (int i = 0; i < slots; i++) {
            stacks[i] = itemStackHandler.getStackInSlot(i);
        }
        return stacks;
    }

    protected ItemStack[] previousInput = getStacks(inputHandler);
    protected ItemStack[] currentInput;
    protected BaseEnergyRecipe currentRecipe = recipes.getRecipe(previousInput);

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
            // after the machine goes from active to inactive, wait 40 ticks
            // then update the block state (this will get reset if the machine goes
            // active again
            if (!active && blockStateActive) {
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

    // update any variables at the start of tile update
    protected void startUpdate() {
        currentInput = getStacks(inputHandler);
        int slots = currentInput.length;
        for (int i = 0; i < slots; i++) {
            if (!ItemStack.areItemsEqual(currentInput[i], previousInput[i])) {
                currentRecipe = recipes.getRecipe(currentInput);
                break;
            }
        }
    }

    // returns true if: inputs are a valid recipe, output has space in output slot,
    // and there is enough energy to complete the recipe
    protected boolean canProcess() {
        if (energyStorage.getEnergyStored() >= ENERGY_RATE && currentRecipe != null && currentRecipe.isValidInput(currentInput)) {
            // TODO: i think this needs to be changed for machines with more than 1 output to work properly
            int slots = outputHandler.getSlots();
            for (int i = 0; i < slots; i++) {
                ItemStack output = currentRecipe.getOutputSlot(i);
                ItemStack currentOutput = outputHandler.getStackInSlot(i);
                if (!(currentOutput.isEmpty() || (ItemStack.areItemsEqual(output, currentOutput) &&
                                currentOutput.getCount() + output.getCount() <= output.getMaxStackSize()))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // updates progress and recipeEnergy, this should reset progress and
    // recipeEnergy if the input has changed
    protected void updateProgress() {
        for (int i = 0; i < currentInput.length; i++) {
            if (!ItemStack.areItemsEqual(currentInput[i], previousInput[i])) {
                progress = 0;
                break;
            }
        }
        progress += ENERGY_RATE;
        recipeEnergy = currentRecipe.getEnergyRequired();
    }

    // subtracts inputs from input slots and puts output into output slot
    protected void processRecipe() {
        // recipe should never be null at this point but it doesn't hurt to check
        if (currentRecipe != null) {
            List<ItemStack> outputs = currentRecipe.getOutputs();
            int slots = outputHandler.getSlots();
            for (int i = 0; i < slots; i++) {
                outputHandler.insertItem(0, outputs.get(i), false);
            }
            for (int j = 0; j < currentInput.length; j++) {
                inputHandler.extractItem(j, currentRecipe.getInputCount(currentInput[j]), false);
            }
            progress = 0;
        }
    }

    // update any variables at the end of tile update
    protected void endUpdate() {
        previousInput = currentInput;
    }

    // TODO: updating blockstate should be done via getActualState and networking between server/client
    protected void updateActive(boolean isActive) {
        blockStateActive = isActive;
        IBlockState state = world.getBlockState(pos);
        world.setBlockState(pos, state.withProperty(BlockMachine.ACTIVE, isActive), 3);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if (compound.hasKey("itemsOut")) {
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }
        energyStorage.readFromNBT(compound);
        progress = compound.getInteger("progress");
        counter = compound.getInteger("counter");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        energyStorage.writeToNBT(compound);
        compound.setInteger("progress", progress);
        compound.setInteger("counter", counter);
        return compound;
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

    public IItemHandler getItemStackHandler(@Nullable EnumFacing facing) {
        if (facing == null) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
        } else if (facing == EnumFacing.UP) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
        } else {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
        }
    }

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
