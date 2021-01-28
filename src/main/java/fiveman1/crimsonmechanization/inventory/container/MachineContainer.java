package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.network.PacketHandler;
import fiveman1.crimsonmechanization.network.PacketServerToClient;
import fiveman1.crimsonmechanization.tile.AbstractMachineTile;
import fiveman1.crimsonmechanization.util.PacketUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.PacketDistributor;

public abstract class MachineContainer extends AbstractBaseContainer {

    protected int ENERGY_STORED = -1;
    protected int CAPACITY = -1;
    protected int MAX_RECEIVE = -1;
    protected int MAX_EXTRACT = -1;
    protected int RECIPE_ENERGY = -1;
    protected int PROGRESS = -1;
    protected int ENERGY_RATE = -1;
    protected int TIER = -1;

    public final AbstractMachineTile machineTile;

    public MachineContainer(int windowID, PlayerInventory playerInventory, AbstractMachineTile machine, ContainerType<?> containerType) {
        super(windowID, playerInventory, containerType);
        this.machineTile = machine;
        addSlots();
        addInventorySlots(8, 84);
    }

    protected abstract void addSlots();

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return machineTile.isUsableByPlayer(playerIn);
    }

    public void updateClientData(int id, int data) {
        machineTile.setField(id, data);
    }

    public AbstractMachineTile getMachine() {
        return machineTile;
    }

    @Override
    public void detectAndSendChanges() {

        // TODO: make our own version of trackable ints so we don't have this ugly mess

        super.detectAndSendChanges();
        if (listeners != null) {
            int energyStored = machineTile.getField(PacketServerToClient.ENERGY_ID);
            int capacity = machineTile.getField(PacketServerToClient.CAPACITY_ID);
            int maxReceieve = machineTile.getField(PacketServerToClient.MAX_RECEIVE_ID);
            int maxExtract = machineTile.getField(PacketServerToClient.MAX_EXTRACT_ID);
            int progress = machineTile.getField(PacketServerToClient.PROGRESS_ID);
            int recipeEnergy = machineTile.getField(PacketServerToClient.RECIPE_ENERGY_ID);
            int energyRate = machineTile.getField(PacketServerToClient.ENERGY_RATE_ID);
            int tier = machineTile.getField(PacketServerToClient.TIER_ID);
            for (IContainerListener listener : listeners) {
                if (listener instanceof ServerPlayerEntity) {
                    ServerPlayerEntity playerMP = (ServerPlayerEntity) listener;
                    if (energyStored != ENERGY_STORED) {
                        PacketUtil.updateMachineStat(PacketServerToClient.ENERGY_ID, energyStored, playerMP);
                    }
                    if (capacity != CAPACITY) {
                        PacketUtil.updateMachineStat(PacketServerToClient.CAPACITY_ID, capacity, playerMP);
                    }
                    if (maxReceieve != MAX_RECEIVE) {
                        PacketUtil.updateMachineStat(PacketServerToClient.MAX_RECEIVE_ID, maxReceieve, playerMP);
                    }
                    if (maxExtract != MAX_EXTRACT) {
                        PacketUtil.updateMachineStat(PacketServerToClient.MAX_EXTRACT_ID, maxExtract, playerMP);
                    }
                    if (progress != PROGRESS) {
                        PacketUtil.updateMachineStat(PacketServerToClient.PROGRESS_ID, progress, playerMP);
                    }
                    if (recipeEnergy != RECIPE_ENERGY) {
                        PacketUtil.updateMachineStat(PacketServerToClient.RECIPE_ENERGY_ID, recipeEnergy, playerMP);
                    }
                    if (energyRate != ENERGY_RATE) {
                        PacketUtil.updateMachineStat(PacketServerToClient.ENERGY_RATE_ID, energyRate, playerMP);
                    }
                    if (tier != TIER) {
                        PacketUtil.updateMachineStat(PacketServerToClient.TIER_ID, tier, playerMP);
                    }

                }
            }
            ENERGY_STORED = energyStored;
            CAPACITY = capacity;
            MAX_RECEIVE = maxReceieve;
            MAX_EXTRACT = maxExtract;
            PROGRESS = progress;
            RECIPE_ENERGY = recipeEnergy;
            ENERGY_RATE = energyRate;
            TIER = tier;
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack currentItemStack = slot.getStack();
            itemstack = currentItemStack.copy();

            if (index < machineTile.getSize()) {
                if (!this.mergeItemStack(currentItemStack, machineTile.getSize(), inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(currentItemStack, itemstack);
            } else if (!this.mergeItemStack(currentItemStack, 0, machineTile.getInputSlots(), false)) {
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
}
