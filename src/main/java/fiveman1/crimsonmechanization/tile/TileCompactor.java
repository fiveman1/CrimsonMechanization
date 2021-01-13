package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.inventory.container.ContainerCompactor;
import fiveman1.crimsonmechanization.recipe.CompactorRecipeRegistry;
import fiveman1.crimsonmechanization.recipe.EnergyRecipe;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileCompactor extends TileMachine {

    public TileCompactor(String name) {
        super(name);
    }

    public TileCompactor() {}

    private final ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return !CompactorRecipeRegistry.getOutput(stack).isEmpty();
        }

        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };
    private final ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }
    };
    private final CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);


    private ItemStack previousInput = inputHandler.getStackInSlot(0);
    private ItemStack currentInput;
    private EnergyRecipe currentRecipe = CompactorRecipeRegistry.getRecipe(previousInput);

    @Override
    protected boolean canProcess() {
        if (energyStorage.getEnergyStored() >= ENERGY_RATE && currentRecipe != null && currentRecipe.isValidInputCount(currentInput)) {
            ItemStack output = currentRecipe.getOutput();
            ItemStack currentOutput = outputHandler.getStackInSlot(0);
            return currentOutput.isEmpty() ||
                    (ItemStack.areItemsEqual(output, currentOutput) &&
                            currentOutput.getCount() + output.getCount() <= output.getMaxStackSize());
        }
        return false;
    }

    @Override
    protected void updateProgress() {
        if (!ItemStack.areItemsEqual(currentInput, previousInput)) {
            progress = 0;
        }
        progress += ENERGY_RATE;
        recipeEnergy = currentRecipe.getEnergyRequired();
    }

    @Override
    protected void processRecipe() {
        // recipe should never be null at this point but it doesn't hurt to check
        if (currentRecipe != null) {
            ItemStack output = currentRecipe.getOutput();
            outputHandler.insertItem(0, output.copy(), false);
            inputHandler.extractItem(0, currentRecipe.getInputCount(currentInput), false);
            progress = 0;
        }
    }

    @Override
    protected void startUpdate() {
        currentInput = inputHandler.getStackInSlot(0);
        if (!ItemStack.areItemsEqual(currentInput, previousInput)) {
            currentRecipe = CompactorRecipeRegistry.getRecipe(currentInput);
        }
    }

    @Override
    protected void endUpdate() {
        previousInput = currentInput;
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
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        return compound;
    }

    @Override
    public IItemHandler getItemStackHandler(@Nullable EnumFacing facing) {
        if (facing == null) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
        } else if (facing == EnumFacing.UP) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
        } else {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
        }
    }

    public ContainerCompactor createContainer(InventoryPlayer playerInventory) {
        return new ContainerCompactor(playerInventory, this, 8, 84);
    }
}
