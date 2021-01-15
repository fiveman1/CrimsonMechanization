package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.inventory.container.ContainerAlloyer;
import fiveman1.crimsonmechanization.recipe.BaseEnergyRecipe;
import fiveman1.crimsonmechanization.recipe.managers.AlloySmelterRecipeManager;
import fiveman1.crimsonmechanization.recipe.managers.IRecipeManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Arrays;

public class TileAlloyer extends TileMachine {

    public static final int INPUT_SLOTS = 2;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    protected IRecipeManager recipes = new AlloySmelterRecipeManager();

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
        return recipes.isValidInput(itemStack);
    }

    public TileAlloyer(String name) {
        super(name);
    }

    public TileAlloyer() {}

    private ItemStack[] getStacks() {
        int slots = inputHandler.getSlots();
        ItemStack[] stacks = new ItemStack[slots];
        for (int i = 0; i < slots; i++) {
            stacks[i] = inputHandler.getStackInSlot(i);
        }
        return stacks;
    }

    private ItemStack[] previousInput = getStacks();
    private ItemStack[] currentInput;
    private BaseEnergyRecipe currentRecipe = recipes.getRecipe(previousInput);

    @Override
    protected void startUpdate() {
        currentInput = getStacks();
        int slots = inputHandler.getSlots();
        for (int i = 0; i < slots; i++) {
            if (!ItemStack.areItemsEqual(currentInput[i], previousInput[i])) {
                currentRecipe = recipes.getRecipe(currentInput);
                break;
            }
        }
    }

    @Override
    protected boolean canProcess() {
        if (energyStorage.getEnergyStored() >= ENERGY_RATE && currentRecipe != null && currentRecipe.isValidInput(Arrays.asList(currentInput), true)) {
            ItemStack output = currentRecipe.getOutputSlot(0);
            ItemStack currentOutput = outputHandler.getStackInSlot(0);
            return currentOutput.isEmpty() ||
                    (ItemStack.areItemsEqual(output, currentOutput) &&
                            currentOutput.getCount() + output.getCount() <= output.getMaxStackSize());
        }
        return false;
    }

    @Override
    protected void updateProgress() {
        int slots = inputHandler.getSlots();
        for (int i = 0; i < slots; i++) {
            if (!ItemStack.areItemsEqual(currentInput[i], previousInput[i])) {
                progress = 0;
                break;
            }
        }
        progress += ENERGY_RATE;
        recipeEnergy = currentRecipe.getEnergyRequired();
    }

    @Override
    protected void processRecipe() {
        // recipe should never be null at this point but it doesn't hurt to check
        if (currentRecipe != null) {
            ItemStack output = currentRecipe.getOutputSlot(0);
            outputHandler.insertItem(0, output, false);
            int slots = inputHandler.getSlots();
            for (int i = 0; i < slots; i++) {
                inputHandler.extractItem(i, currentRecipe.getInputCount(currentInput[i]), false);
            }
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

    public ContainerAlloyer createContainer(InventoryPlayer playerInventory) {
        return new ContainerAlloyer(playerInventory, this, 8, 84);
    }
}

