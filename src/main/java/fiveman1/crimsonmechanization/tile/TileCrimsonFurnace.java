package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.inventory.container.ContainerCrimsonFurnace;
import fiveman1.crimsonmechanization.recipe.FurnaceRecipeManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class TileCrimsonFurnace extends TileMachine {

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int DEFAULT_ENERGY = 1600;

    @Override
    protected boolean isInputValid(ItemStack itemStack) {
        return !FurnaceRecipeManager.getOutput(itemStack).isEmpty();
    }

    @Override
    public int getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int getOutputSlots() {
        return OUTPUT_SLOTS;
    }


    public TileCrimsonFurnace(String name) {
        super(name);
    }

    public TileCrimsonFurnace() {
        super();
    }

    private ItemStack previousInput = inputHandler.getStackInSlot(0);
    private ItemStack currentInput;
    private ItemStack currentRecipeOutput = FurnaceRecipeManager.getOutput(previousInput);

    @Override
    protected void startUpdate() {
        currentInput = inputHandler.getStackInSlot(0);
        if (!ItemStack.areItemsEqual(currentInput, previousInput)) {
            currentRecipeOutput = FurnaceRecipeManager.getOutput(currentInput);
        }
    }

    @Override
    protected boolean canProcess() {
        if (energyStorage.getEnergyStored() >= ENERGY_RATE && !currentRecipeOutput.isEmpty()) {
            ItemStack currentOutput = outputHandler.getStackInSlot(0);
            return currentOutput.isEmpty() ||
                    (ItemStack.areItemsEqual(currentRecipeOutput, currentOutput) &&
                            currentOutput.getCount() + currentRecipeOutput.getCount() <= currentRecipeOutput.getMaxStackSize());
        }
        return false;
    }

    @Override
    protected void updateProgress() {
        if (!ItemStack.areItemsEqual(currentInput, previousInput)) {
            progress = 0;
        }
        progress += ENERGY_RATE;
        recipeEnergy = DEFAULT_ENERGY;
    }

    @Override
    protected void processRecipe() {
        // recipe should never be null at this point but it doesn't hurt to check
        if (currentRecipeOutput != null) {
            ItemStack output = currentRecipeOutput;
            outputHandler.insertItem(0, output.copy(), false);
            inputHandler.extractItem(0, 1, false);
            progress = 0;
        }
    }

    @Override
    protected void endUpdate() {
        previousInput = currentInput;
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

    public ContainerCrimsonFurnace createContainer(InventoryPlayer playerInventory) {
        return new ContainerCrimsonFurnace(playerInventory, this, 8, 84);
    }
}
