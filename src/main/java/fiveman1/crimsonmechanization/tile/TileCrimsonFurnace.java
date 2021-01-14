package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.inventory.container.ContainerCrimsonFurnace;
import fiveman1.crimsonmechanization.recipe.CompactorRecipeRegistry;
import fiveman1.crimsonmechanization.recipe.FurnaceRecipeRegistry;
import fiveman1.crimsonmechanization.recipe.IRecipeManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileCrimsonFurnace extends TileMachine {

    @Override
    protected IRecipeManager getRecipes() {
        return new CompactorRecipeRegistry();
    }

    @Override
    public int getInputSlots() {
        return 1;
    }

    @Override
    public int getOutputSlots() {
        return 1;
    }

    private final FurnaceRecipeRegistry furnaceRecipes = new FurnaceRecipeRegistry();

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_PROGRESS = 1600;

    public TileCrimsonFurnace(String name) {
        super(name);
    }

    public TileCrimsonFurnace() {
        super();
    }

    private final ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return !furnaceRecipes.getOutput(stack).isEmpty();
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

    private final FurnaceRecipes instance = FurnaceRecipes.instance();

    private ItemStack previousInput = inputHandler.getStackInSlot(0);
    private ItemStack currentInput;
    private ItemStack currentRecipeOutput = furnaceRecipes.getOutput(previousInput);

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
        recipeEnergy = MAX_PROGRESS;
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
    protected void startUpdate() {
        currentInput = inputHandler.getStackInSlot(0);
        if (!ItemStack.areItemsEqual(currentInput, previousInput)) {
            currentRecipeOutput = furnaceRecipes.getOutput(currentInput);
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
