package fiveman1.crimsonmechanization.inventory.container;


import fiveman1.crimsonmechanization.inventory.slot.SlotOutput;
import fiveman1.crimsonmechanization.network.Messages;
import fiveman1.crimsonmechanization.network.PacketGetEnergy;
import fiveman1.crimsonmechanization.tile.TileCrimsonFurnace;
import fiveman1.crimsonmechanization.util.CustomEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCrimsonFurnace extends ContainerBase implements IMachineContainer {

    private int progress;
    private final TileCrimsonFurnace furnace;

    public ContainerCrimsonFurnace(IInventory playerInventory, TileCrimsonFurnace tileCrimsonFurnace, int xOffsetInventory, int yOffsetInventory) {
        super(playerInventory, tileCrimsonFurnace, xOffsetInventory, yOffsetInventory);
        furnace = tileCrimsonFurnace;
    }

    protected void addSlots() {
        IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        addSlotToContainer(new SlotItemHandler(itemHandler, 0, 46, 34));
        addSlotToContainer(new SlotOutput(itemHandler, 1, 116, 34));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack currentItemStack = slot.getStack();
            itemstack = currentItemStack.copy();

            if (index < furnace.SIZE) {
                if (!this.mergeItemStack(currentItemStack, furnace.SIZE, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(currentItemStack, itemstack);
            } else if (!this.mergeItemStack(currentItemStack, 0, 1, false)) {
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

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        furnace.setField(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) {
            int field = furnace.getField(0);
            if (field != progress) {
                listener.sendWindowProperty(this, 0, field);
            }
            CustomEnergyStorage storage = furnace.energyStorage;
            if (furnace.getField(1) != storage.getEnergyStored() ||
            furnace.getField(2) != storage.getCapacity() ||
            furnace.getField(3) != storage.getMaxReceive() ||
            furnace.getField(4) != storage.getMaxExtract()) {
                if (listener instanceof EntityPlayerMP) {
                    Messages.INSTANCE.sendTo(new PacketGetEnergy(storage.getEnergyStored(), storage.getCapacity(), storage.getMaxReceive(), storage.getMaxExtract()), (EntityPlayerMP) listener);
                }
            }
        }
        progress = furnace.getField(0);
    }

    @Override
    public void syncEnergy(int energy, int capacity, int maxReceive, int maxExtract) {
        furnace.setField(1, energy);
        furnace.setField(2, capacity);
        furnace.setField(3, maxReceive);
        furnace.setField(4, maxExtract);
    }
}
