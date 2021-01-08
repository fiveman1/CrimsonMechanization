package fiveman1.crimsonmechanization.tile;

import fiveman1.crimsonmechanization.blocks.BlockMachine;
import fiveman1.crimsonmechanization.inventory.container.ContainerCrimsonFurnace;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileCrimsonFurnace extends TileEntityBase implements ITickable {

    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_PROGRESS = 40;

    public TileCrimsonFurnace(String name) {
        super(name);
    }

    public TileCrimsonFurnace() {
        super();
    }

    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);
            return !result.isEmpty();
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileCrimsonFurnace.this.markDirty();
        }
    };
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            TileCrimsonFurnace.this.markDirty();
        }
    };
    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    private boolean active = false;
    private int progress = 0;
    private int counter = 0;
    private ItemStack previousInput = inputHandler.getStackInSlot(0);

    @Override
    public void update() {
        if (!world.isRemote) {
            if (progress < MAX_PROGRESS) {
                if (!inputHandler.getStackInSlot(0).isEmpty() && hasAvailableOutput()) {
                    if (!active) {
                        IBlockState state = world.getBlockState(pos);
                        world.setBlockState(pos, state.withProperty(BlockMachine.ACTIVE, true), 3);
                    }
                    active = true;
                    progress++;
                    if (progress >= MAX_PROGRESS) {
                        attemptSmelt();
                    }
                    if (inputHandler.getStackInSlot(0).getItem() != previousInput.getItem()) {
                        progress = 1;
                    }
                    markDirty();
                } else {
                    active = false;
                    progress = 0;
                }
            } else {
                startSmelt();
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
        }
    }

    private boolean insertOutput(ItemStack output, boolean simulate) {
        return outputHandler.insertItem(0, output, simulate).isEmpty();
    }

    private boolean hasAvailableOutput() {
        return insertOutput(FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(0)).copy(), true);
    }

    private void startSmelt() {
        progress = 0;
        if (!inputHandler.getStackInSlot(0).isEmpty() && hasAvailableOutput()) {
            markDirty();
        }
    }

    private void attemptSmelt() {
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(0));
        if (insertOutput(result.copy(), false)) {
            inputHandler.extractItem(0, 1, false);
        }
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
        progress = compound.getInteger("progress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setInteger("progress", progress);
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

    public int getField(int id) {
        switch (id) {
            case 0:
                return progress;
            default:
                return 0;

        }
    }

    public void setField(int id, int value)
    {
        switch (id) {
            case 0:
                progress = value;
        }
    }

    public boolean isActive() {
        return active;
    }
}
