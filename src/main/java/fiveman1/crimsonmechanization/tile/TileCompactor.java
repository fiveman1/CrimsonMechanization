package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.inventory.container.ContainerCompactor;
import fiveman1.crimsonmechanization.recipe.SimpleEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.managers.CompactorRecipeManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class TileCompactor extends TileMachine {

    // TODO replace SimpleEnergyRecipe with BaseEnergyRecipe

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    @Override
    public int getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    public int getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Override
    protected boolean isInputValid(ItemStack itemStack) {
        return CompactorRecipeManager.isValidInput(itemStack);
    }

    public TileCompactor(String name) {
        super(name);
    }

    public TileCompactor() {}

    private ItemStack previousInput = inputHandler.getStackInSlot(0);
    private ItemStack currentInput;
    private SimpleEnergyRecipe currentRecipe = CompactorRecipeManager.getRecipe(previousInput);

    @Override
    protected void startUpdate() {
        currentInput = inputHandler.getStackInSlot(0);
        if (!ItemStack.areItemsEqual(currentInput, previousInput)) {
            currentRecipe = CompactorRecipeManager.getRecipe(currentInput);
        }
    }

    @Override
    protected boolean canProcess() {
        if (energyStorage.getEnergyStored() >= ENERGY_RATE && currentRecipe != null && currentRecipe.isValidInputAndCount(currentInput)) {
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
            inputHandler.extractItem(0, currentRecipe.getInputCount(), false);
            progress = 0;
        }
    }

    @Override
    protected void endUpdate() {
        previousInput = currentInput;
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
