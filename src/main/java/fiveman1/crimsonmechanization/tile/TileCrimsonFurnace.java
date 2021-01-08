package fiveman1.crimsonmechanization.tile;


import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
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

    private int progress;

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

    @Override
    public void update() {

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
}
