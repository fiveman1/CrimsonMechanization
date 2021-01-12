package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.blocks.BlockMachine;
import fiveman1.crimsonmechanization.inventory.container.ContainerCrimsonFurnace;
import net.minecraft.block.state.IBlockState;
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
            ItemStack result = instance.getSmeltingResult(stack);
            return !result.isEmpty();
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileCrimsonFurnace.this.markDirty();
        }
    };
    private final ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            TileCrimsonFurnace.this.markDirty();
        }
    };
    private final CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    private boolean active = false;
    private int counter = 0;
    private ItemStack previousInput = inputHandler.getStackInSlot(0);
    private int previousEnergyStored = energyStorage.getEnergyStored();
    private FurnaceRecipes instance = FurnaceRecipes.instance();

    @Override
    public void updateTile() {
        if (!inputHandler.getStackInSlot(0).isEmpty() && hasAvailableOutput()) {
            if (energyStorage.getEnergyStored() >= ENERGY_RATE) {
                if (!active) {
                    IBlockState state = world.getBlockState(pos);
                    world.setBlockState(pos, state.withProperty(BlockMachine.ACTIVE, true), 3);
                }
                active = true;
                progress += ENERGY_RATE;
                energyStorage.consumeEnergy(ENERGY_RATE);
                if (progress >= getRecipeEnergy()) {
                    attemptSmelt();
                }
                if (inputHandler.getStackInSlot(0).getItem() != previousInput.getItem()) {
                    progress = ENERGY_RATE;
                }
                markDirty();
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

    @Override
    public int getRecipeEnergy() {
        return MAX_PROGRESS;
    }

    private boolean insertOutput(ItemStack output, boolean simulate) {
        return outputHandler.insertItem(0, output, simulate).isEmpty();
    }

    private boolean hasAvailableOutput() {
        return insertOutput(instance.getSmeltingResult(inputHandler.getStackInSlot(0)).copy(), true);
    }

    private void attemptSmelt() {
        ItemStack result = instance.getSmeltingResult(inputHandler.getStackInSlot(0));
        if (insertOutput(result.copy(), false)) {
            inputHandler.extractItem(0, 1, false);
            progress = 0;
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
