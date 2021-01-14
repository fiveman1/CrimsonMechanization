package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.network.Messages;
import fiveman1.crimsonmechanization.network.PacketMachineInfo;
import fiveman1.crimsonmechanization.tile.TileMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ContainerMachine extends ContainerBase {

    protected final TileMachine machine;

    // these are client side (!!!)
    protected int ENERGY_STORED = 0;
    protected int CAPACITY = 0;
    protected int MAX_RECEIVE = 0;
    protected int MAX_EXTRACT = 0;
    protected int RECIPE_ENERGY = 0;
    protected int PROGRESS = 0;

    public ContainerMachine(IInventory playerInventory, TileMachine tileEntity, int xOffsetInventory, int yOffsetInventory) {
        super(playerInventory, tileEntity, xOffsetInventory, yOffsetInventory);
        machine = tileEntity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        machine.setField(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int energyStored = machine.getField(TileMachine.ENERGY_ID);
        int capacity = machine.getField(TileMachine.CAPACITY_ID);
        int maxReceieve = machine.getField(TileMachine.MAX_RECEIVE_ID);
        int maxExtract = machine.getField(TileMachine.MAX_EXTRACT_ID);
        int progress = machine.getField(TileMachine.PROGRESS_ID);
        int recipeEnergy = machine.getField(TileMachine.RECIPE_ENERGY_ID);
        for (IContainerListener listener : listeners) {
            if (listener instanceof EntityPlayerMP) {
                EntityPlayerMP playerMP = (EntityPlayerMP) listener;
                if (energyStored != ENERGY_STORED) {
                    Messages.INSTANCE.sendTo(new PacketMachineInfo(energyStored), playerMP);
                }

                if (capacity != CAPACITY || maxReceieve != MAX_RECEIVE || maxExtract != MAX_EXTRACT) {
                    Messages.INSTANCE.sendTo(new PacketMachineInfo(capacity, maxReceieve, maxExtract), playerMP);
                }
                // TODO: progress should be its own packet separate from recipe energy
                if (progress != PROGRESS || recipeEnergy != RECIPE_ENERGY) {
                    Messages.INSTANCE.sendTo(new PacketMachineInfo(progress, recipeEnergy), playerMP);
                }
            }
        }
        ENERGY_STORED = energyStored;
        CAPACITY = capacity;
        MAX_RECEIVE = maxReceieve;
        MAX_EXTRACT = maxExtract;
        PROGRESS = progress;
        RECIPE_ENERGY = recipeEnergy;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack currentItemStack = slot.getStack();
            itemstack = currentItemStack.copy();

            if (index < machine.getSize()) {
                if (!this.mergeItemStack(currentItemStack, machine.getSize(), inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(currentItemStack, itemstack);
            } else if (!this.mergeItemStack(currentItemStack, 0, machine.getInputSlots(), false)) {
                return ItemStack.EMPTY;
            }
            if (currentItemStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            slot.onTake(playerIn, currentItemStack);
        }

        return itemstack;
    }

    public TileMachine getTileMachine() {
        return machine;
    }
}
