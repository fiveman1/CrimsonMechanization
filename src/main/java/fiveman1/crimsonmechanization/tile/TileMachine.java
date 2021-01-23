package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.enums.EnumMachineTier;
import fiveman1.crimsonmechanization.items.ModItems;
import fiveman1.crimsonmechanization.network.PacketServerToClient;
import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.managers.IRecipeManager;
import fiveman1.crimsonmechanization.util.CustomEnergyStorage;
import fiveman1.crimsonmechanization.util.ItemStackHandlerUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileMachine extends TileEntityBase implements ITickable {

    public static final int PROGRESS_ID = PacketServerToClient.PROGRESS_ID;
    public static final int ENERGY_ID = PacketServerToClient.ENERGY_ID;
    public static final int CAPACITY_ID = PacketServerToClient.CAPACITY_ID;
    public static final int MAX_RECEIVE_ID = PacketServerToClient.MAX_RECEIVE_ID;
    public static final int MAX_EXTRACT_ID = PacketServerToClient.MAX_EXTRACT_ID;
    public static final int RECIPE_ENERGY_ID = PacketServerToClient.RECIPE_ENERGY_ID;
    public static final int TIER_ID = PacketServerToClient.TIER_ID;
    public static final int ENERGY_RATE_ID = PacketServerToClient.ENERGY_RATE_ID;

    protected int tierMeta = 0;

    // these MUST BE UPDATED WHEN CHANGED as they are used for the progress bar
    protected int recipeEnergy = 0;
    protected int progress = 0;

    protected int ENERGY_RATE = 20;
    protected int PROGRESS_RATE = 1;
    protected CustomEnergyStorage energyStorage = new CustomEnergyStorage(100000, 120, 0);
    protected int previousEnergyStored = energyStorage.getEnergyStored();
    protected boolean active = false;
    protected boolean blockStateActive = false;

    private int counter = 0;
    private boolean init = true;

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
    protected final ItemStackHandler upgradeHandler = new ItemStackHandler(4) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot) {
                case 1:
                    return ItemStack.areItemsEqual(stack, new ItemStack(ModItems.itemUpgradeSpeed));
                case 2:
                    return ItemStack.areItemsEqual(stack, new ItemStack(ModItems.itemUpgradeEfficiency));
                case 3:
                    return ItemStack.areItemsEqual(stack, new ItemStack(ModItems.itemUpgradeLuck));
            }
            return false;
        }

        @Override
        public int getSlotLimit(int slot) {
            return getTier(tierMeta).getUpgradeStackLimit();
        }

        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };
    protected final CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    public ItemStackHandler getUpgradeHandler() {
        return upgradeHandler;
    }

    protected ItemStack[] previousInput = ItemStackHandlerUtil.getStacks(inputHandler);
    protected ItemStack[] currentInput;
    protected BaseEnergyRecipe currentRecipe = recipes.getRecipe(previousInput);

    public TileMachine() {}

    public TileMachine(EnumMachineTier enumMachineTier) {
        energyStorage = new CustomEnergyStorage(enumMachineTier.getCapacity(), enumMachineTier.getMaxReceive(), 0);
        ENERGY_RATE = enumMachineTier.getEnergyUse();
        tierMeta = enumMachineTier.getMetadata();
    }

    @Override
    public void update() {
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
        if (init) {
            world.checkLightFor(EnumSkyBlock.BLOCK, pos);
            init = false;
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
        int speed = upgradeHandler.getStackInSlot(1).getCount();
        int efficiency = upgradeHandler.getStackInSlot(2).getCount();
        double powerMultiplier = Math.pow(1.3, speed) * Math.pow(0.95, efficiency);
        ENERGY_RATE = (int) Math.ceil(baseEnergyUse * powerMultiplier);
        PROGRESS_RATE = baseEnergyUse + (baseEnergyUse * speed) / 2;
        energyStorage.setMaxReceive(ENERGY_RATE * 5);
    }

    // returns true if: inputs are a valid recipe, output has space in output slot,
    // and there is enough energy to complete the recipe
    protected boolean canProcess() {
        return energyStorage.getEnergyStored() >= ENERGY_RATE && currentRecipe != null && currentRecipe.isValidInput(currentInput) &&
                ItemStackHandlerUtil.canProcessOutputs(outputHandler, currentRecipe.getOutputs());
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
        recipeEnergy = currentRecipe.getEnergyRequired();
    }

    // subtracts inputs from input slots and puts output into output slot
    protected void processRecipe() {
        // recipe should never be null at this point but it doesn't hurt to check
        if (currentRecipe != null) {
            ItemStackHandlerUtil.processOutputs(outputHandler, currentRecipe.getOutputs(), currentRecipe.getOutputChances());
            for (int j = 0; j < currentInput.length; j++) {
                inputHandler.extractItem(j, currentRecipe.getInputCount(currentInput[j]), false);
            }
        }
    }

    // update any variables at the end of tile update
    protected void endUpdate() {
        previousInput = currentInput;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        writeRestorableToNBT(compound);
        compound.setBoolean("active", blockStateActive);
        compound.setInteger("progress", progress);
        compound.setInteger("counter", counter);
        compound.setInteger("tier", tierMeta);
        energyStorage.writeToNBT(compound);
        return compound;
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

        readRestorableToNBT(compound);
        blockStateActive = compound.getBoolean("active");
        progress = compound.getInteger("progress");
        counter = compound.getInteger("counter");
        energyStorage.readFromNBT(compound);
    }

    public NBTTagCompound writeRestorableToNBT(NBTTagCompound compound) {
        compound.setInteger("energy", energyStorage.getEnergyStored());
        compound.setInteger("tier", tierMeta);
        compound.setTag("upgrades", upgradeHandler.serializeNBT());
        return compound;
    }

    public void readRestorableToNBT(NBTTagCompound compound) {
        energyStorage.setEnergy(compound.getInteger("energy"));
        tierMeta = compound.getInteger("tier");
        energyStorage.setCapacity(getTier(tierMeta).getCapacity());
        if (compound.hasKey("upgrades")) {
            upgradeHandler.deserializeNBT((NBTTagCompound) compound.getTag("upgrades"));
        }
    }

    public EnumMachineTier getTier(int meta) {
        return EnumMachineTier.byMetadata(meta);
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

    protected IItemHandler getItemStackHandler(@Nullable EnumFacing facing) {
        if (facing == null) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
        } else if (facing == EnumFacing.UP) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
        } else {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
        }
    }

    protected CustomEnergyStorage getEnergyStorage(EnumFacing facing) {
        return energyStorage;
    }

    public void setActive(boolean isActive) {
        if (blockStateActive != isActive) {
            blockStateActive = isActive;
            markDirty();
            IBlockState blockState = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, blockState, blockState, 3);
        }
    }

    public boolean isActive() {
        return blockStateActive;
    }

    public int getLightLevel() {
        return blockStateActive ? 12 : 0;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setBoolean("active", blockStateActive);
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, -1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        boolean isActive = pkt.getNbtCompound().getBoolean("active");
        if (isActive != blockStateActive) {
            if (world.isRemote) {
                blockStateActive = isActive;
                world.markBlockRangeForRenderUpdate(pos, pos);
            }
            world.checkLightFor(EnumSkyBlock.BLOCK, pos);
        }
    }

    public int getField(int id) {
        if (id == PROGRESS_ID) {
            return progress;
        } else if (id == ENERGY_ID) {
            return energyStorage.getEnergyStored();
        } else if (id == CAPACITY_ID) {
            return energyStorage.getCapacity();
        } else if (id == MAX_RECEIVE_ID) {
            return energyStorage.getMaxReceive();
        } else if (id == MAX_EXTRACT_ID) {
            return energyStorage.getMaxExtract();
        } else if (id == RECIPE_ENERGY_ID) {
            return recipeEnergy;
        } else if (id == ENERGY_RATE_ID) {
            return ENERGY_RATE;
        } else if (id == TIER_ID) {
            return tierMeta;
        }
        return 0;
    }

    public void setField(int id, int value) {
        if (id == PROGRESS_ID) {
            progress = value;
        } else if (id == ENERGY_ID) {
            energyStorage.setEnergy(value);
        } else if (id == CAPACITY_ID) {
            energyStorage.setCapacity(value);
        } else if (id == MAX_RECEIVE_ID) {
            energyStorage.setMaxReceive(value);
        } else if (id == MAX_EXTRACT_ID) {
            energyStorage.setMaxExtract(value);
        } else if (id == RECIPE_ENERGY_ID) {
            recipeEnergy = value;
        } else if (id == ENERGY_RATE_ID) {
            ENERGY_RATE = value;
        } else if (id == TIER_ID) {
            tierMeta = value;
        }
    }
}
