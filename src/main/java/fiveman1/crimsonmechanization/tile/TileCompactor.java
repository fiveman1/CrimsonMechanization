package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.blocks.BlockMachine;
import fiveman1.crimsonmechanization.inventory.container.ContainerCompactor;
import fiveman1.crimsonmechanization.recipe.CompactorRecipeRegistry;
import fiveman1.crimsonmechanization.recipe.EnergyRecipe;
import net.minecraft.block.state.IBlockState;
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

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;

    public TileCompactor(String name) {
        super(name);
    }

    public TileCompactor() {}

    private final ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            ItemStack result = getRecipeResult(stack);
            return !result.isEmpty();
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

    private boolean active = false;
    private int counter = 0;
    public ItemStack previousInput = inputHandler.getStackInSlot(0);
    private int previousEnergyStored = energyStorage.getEnergyStored();



    // TODO: cache everything (recipe energy, output, current input, etc)

    @Override
    public void updateTile() {
        if (!inputHandler.getStackInSlot(0).isEmpty() && hasAvailableOutput()) {
            if (energyStorage.getEnergyStored() >= ENERGY_RATE) {
                if (!active) {
                    IBlockState state = world.getBlockState(pos);
                    world.setBlockState(pos, state.withProperty(BlockMachine.ACTIVE, true), 3);
                    active = true;
                }
                if (!ItemStack.areItemsEqual(inputHandler.getStackInSlot(0), previousInput)) {
                    progress = 0;
                }
                progress += ENERGY_RATE;
                energyStorage.consumeEnergy(ENERGY_RATE);
                getRecipeEnergy();
                if (progress >= recipeEnergy) {
                    attemptSmelt();
                }
                markDirty();
                energyStorage.consumeEnergy(ENERGY_RATE);
            } else if (energyStorage.getEnergyStored() == previousEnergyStored) {
                active = false;
            }
        } else {
            active = false;
            progress = 0;
        }
        counter++;
        if (counter > 40) {
            IBlockState state = world.getBlockState(pos);
            if (state.getValue(BlockMachine.ACTIVE) != active) {
                world.setBlockState(pos, state.withProperty(BlockMachine.ACTIVE, active), 3);
            }
            counter = 0;
        }
        previousInput = inputHandler.getStackInSlot(0);
        previousEnergyStored = energyStorage.getEnergyStored();
    }

    public void getRecipeEnergy() {
        EnergyRecipe result = CompactorRecipeRegistry.getRecipe(inputHandler.getStackInSlot(0));
        recipeEnergy = result != null ? result.getEnergyRequired() : 0;
    }

    private ItemStack getRecipeResult(ItemStack itemStack) {
        // TODO: cache results to improve performance
        return CompactorRecipeRegistry.getOutput(itemStack);
    }

    private boolean insertOutput(ItemStack output, boolean simulate) {
        return outputHandler.insertItem(0, output, simulate).isEmpty();
    }

    private boolean hasAvailableOutput() {
        ItemStack input = inputHandler.getStackInSlot(0);
        EnergyRecipe recipe = CompactorRecipeRegistry.getRecipe(input);
        return recipe != null &&
                recipe.getInput().getCount() <= input.getCount() &&
                insertOutput(getRecipeResult(input).copy(), true);
    }

    private void attemptSmelt() {
        EnergyRecipe recipe = CompactorRecipeRegistry.getRecipe(inputHandler.getStackInSlot(0));
        if (recipe != null) {
            ItemStack input = recipe.getInput();
            ItemStack output = recipe.getOutput();
            if (insertOutput(output.copy(), false)) {
                inputHandler.extractItem(0, input.getCount(), false);
                progress = 0;
            }
        }
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
        counter = compound.getInteger("counter");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setInteger("counter", counter);
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
