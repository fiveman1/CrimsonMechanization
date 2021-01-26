package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.blocks.MachineBlock;
import fiveman1.crimsonmechanization.enums.MachineTier;
import fiveman1.crimsonmechanization.network.PacketServerToClient;
import fiveman1.crimsonmechanization.recipe.internal.BaseMachineRecipe;
import fiveman1.crimsonmechanization.recipe.managers.IRecipeManager;
import fiveman1.crimsonmechanization.util.CustomEnergyStorage;
import fiveman1.crimsonmechanization.util.ItemStackHandlerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractMachineTile extends TileEntity implements ITickableTileEntity {

    protected int tierMeta = 0;

    // these MUST BE UPDATED WHEN CHANGED as they are used for the progress bar
    protected int recipeEnergy = 0;
    protected int progress = 0;

    protected int ENERGY_RATE = 20;
    protected int PROGRESS_RATE = 1;
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

    protected CustomEnergyStorage energyStorage = new CustomEnergyStorage(100000, 120, 0);
    private final LazyOptional<CustomEnergyStorage> energyStorageLazy = LazyOptional.of(() -> energyStorage);

    protected boolean isInputValid(ItemStack itemStack) {
        return recipes.isValid(itemStack);
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

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (isItemValid(slot, stack)) {
                return super.insertItem(slot, stack, simulate);
            }
            return stack;
        }
    };
    private final LazyOptional<ItemStackHandler> inputHandlerLazy = LazyOptional.of(() -> inputHandler);

    protected final ItemStackHandler outputHandler = new ItemStackHandler(getOutputSlots()) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };
    private final LazyOptional<ItemStackHandler> outputHandlerLazy = LazyOptional.of(() -> outputHandler);

    protected final CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);
    private final LazyOptional<CombinedInvWrapper> combinedHandlerLazy = LazyOptional.of(() -> combinedHandler);

    public AbstractMachineTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public AbstractMachineTile(TileEntityType<?> tileEntityTypeIn, MachineTier machineTier) {
        super(tileEntityTypeIn);
        energyStorage = new CustomEnergyStorage(machineTier.getCapacity(), machineTier.getMaxReceive(), 0);
        ENERGY_RATE = machineTier.getEnergyUse();
        tierMeta = machineTier.getMetadata();
    }

    protected ItemStack[] previousInput = ItemStackHandlerUtil.getStacks(inputHandler);
    protected ItemStack[] currentInput;
    protected BaseMachineRecipe currentRecipe = recipes.getRecipe(previousInput);
    protected int previousEnergyStored = energyStorage.getEnergyStored();

    @Override
    public void tick() {
        if (!world.isRemote) {
            startUpdate();
            int currentEnergyStored = energyStorage.getEnergyStored();
            if (canProcess()) {
                if (!active) {
                    setActive(true);
                    active = true;
                    counter = 0;
                }
                updateProgress();
                energyStorage.consumeEnergy(ENERGY_RATE);
                if (progress >= recipeEnergy) {
                    processRecipe();
                    progress -= recipeEnergy;
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
                    setActive(false);
                }
            }
            previousEnergyStored = currentEnergyStored;
            endUpdate();
        }
    }

    public void setActive(boolean isActive) {
        if (blockStateActive != isActive) {
            blockStateActive = isActive;
            markDirty();
            world.setBlockState(pos, world.getBlockState(pos).with(MachineBlock.ACTIVE, blockStateActive),
                    Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    // update any variables at the start of tile update
    protected void startUpdate() {
        currentInput = ItemStackHandlerUtil.getStacks(inputHandler);
        int slots = currentInput.length;
        for (int i = 0; i < slots; i++) {
            if (!ItemStack.areItemsEqual(currentInput[i], previousInput[i])) {
                currentRecipe = recipes.getRecipe(currentInput);
                break;
            }
        }
        int baseEnergyUse = getTier(tierMeta).getEnergyUse();
        ENERGY_RATE = baseEnergyUse;
        /*int speed = upgradeHandler.getStackInSlot(1).getCount();
        int efficiency = upgradeHandler.getStackInSlot(2).getCount();
        double powerMultiplier = Math.pow(1.3, speed) * Math.pow(0.95, efficiency);
        ENERGY_RATE = (int) Math.ceil(baseEnergyUse * powerMultiplier);
        PROGRESS_RATE = baseEnergyUse + (baseEnergyUse * speed) / 2;
        energyStorage.setMaxReceive(ENERGY_RATE * 5);*/
        PROGRESS_RATE = ENERGY_RATE;
    }

    // returns true if: inputs are a valid recipe, output has space in output slot,
    // and there is enough energy to complete the recipe
    protected boolean canProcess() {
        return energyStorage.getEnergyStored() >= ENERGY_RATE && currentRecipe != null && inputsAreValid(currentInput) &&
                ItemStackHandlerUtil.canProcessOutputs(outputHandler, currentRecipe.getOutputItems());
    }

    private boolean inputsAreValid(ItemStack... inputs) {
        for (ItemStack stack : inputs) {
            if (currentRecipe.getInputCount(stack) > stack.getCount()) {
                return false;
            }
        }
        return true;
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
        progress += PROGRESS_RATE;
        recipeEnergy = currentRecipe.getEnergy();
    }

    // subtracts inputs from input slots and puts output into output slot
    protected void processRecipe() {
        // recipe should never be null at this point but it doesn't hurt to check
        if (currentRecipe != null) {
            ItemStackHandlerUtil.processOutputs(outputHandler, currentRecipe.getOutputItems(), currentRecipe.getOutputChances());
            for (int j = 0; j < currentInput.length; j++) {
                inputHandler.extractItem(j, currentRecipe.getInputCount(currentInput[j]), false);
            }
        }
    }

    // update any variables at the end of tile update
    protected void endUpdate() {
        previousInput = currentInput;
    }

    protected MachineTier getTier(int meta) {
        return MachineTier.byMetadata(meta);
    }

    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        if (compound.contains("itemsIn")) {
            inputHandler.deserializeNBT((CompoundNBT) compound.get("itemsIn"));
        }
        if (compound.contains("itemsOut")) {
            outputHandler.deserializeNBT((CompoundNBT) compound.get("itemsOut"));
        }
        blockStateActive = compound.getBoolean("active");
        progress = compound.getInt("progress");
        counter = compound.getInt("counter");
        tierMeta = compound.getInt("tier");
        energyStorage.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("itemsIn", inputHandler.serializeNBT());
        compound.put("itemsOut", outputHandler.serializeNBT());
        compound.putBoolean("active", blockStateActive);
        compound.putInt("progress", progress);
        compound.putInt("counter", counter);
        compound.putInt("tier", tierMeta);
        energyStorage.write(compound);
        return compound;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) {
                return combinedHandlerLazy.cast();
            }
            else {
                return inputHandlerLazy.cast();
            }
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energyStorageLazy.cast();
        }
        return super.getCapability(cap, side);
    }

    public int getField(int id) {
        if (id == PacketServerToClient.PROGRESS_ID) {
            return progress;
        } else if (id == PacketServerToClient.ENERGY_ID) {
            return energyStorage.getEnergyStored();
        } else if (id == PacketServerToClient.CAPACITY_ID) {
            return energyStorage.getCapacity();
        } else if (id == PacketServerToClient.MAX_RECEIVE_ID) {
            return energyStorage.getMaxReceive();
        } else if (id == PacketServerToClient.MAX_EXTRACT_ID) {
            return energyStorage.getMaxExtract();
        } else if (id == PacketServerToClient.RECIPE_ENERGY_ID) {
            return recipeEnergy;
        } else if (id == PacketServerToClient.ENERGY_RATE_ID) {
            return ENERGY_RATE;
        }
        return 0;
    }

    public void setField(int id, int value) {
        if (id == PacketServerToClient.PROGRESS_ID) {
            progress = value;
        } else if (id == PacketServerToClient.ENERGY_ID) {
            energyStorage.setEnergy(value);
        } else if (id == PacketServerToClient.CAPACITY_ID) {
            energyStorage.setCapacity(value);
        } else if (id == PacketServerToClient.MAX_RECEIVE_ID) {
            energyStorage.setMaxReceive(value);
        } else if (id == PacketServerToClient.MAX_EXTRACT_ID) {
            energyStorage.setMaxExtract(value);
        } else if (id == PacketServerToClient.RECIPE_ENERGY_ID) {
            recipeEnergy = value;
        } else if (id == PacketServerToClient.ENERGY_RATE_ID) {
            ENERGY_RATE = value;
        }
    }
}
