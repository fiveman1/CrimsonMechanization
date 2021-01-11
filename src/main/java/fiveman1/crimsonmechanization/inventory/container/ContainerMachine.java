package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.network.Messages;
import fiveman1.crimsonmechanization.network.PacketGetEnergy;
import fiveman1.crimsonmechanization.tile.TileMachine;
import fiveman1.crimsonmechanization.util.CustomEnergyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ContainerMachine extends ContainerBase implements IMachineContainer {

    protected int progress;
    protected final TileMachine machine;

    public ContainerMachine(IInventory playerInventory, TileMachine tileEntity, int xOffsetInventory, int yOffsetInventory) {
        super(playerInventory, tileEntity, xOffsetInventory, yOffsetInventory);
        machine = tileEntity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        machine.setField(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) {
            int field = machine.getField(0);
            if (field != progress) {
                listener.sendWindowProperty(this, 0, field);
            }
            CustomEnergyStorage storage = machine.energyStorage;
            if (machine.getField(1) != storage.getEnergyStored() ||
                    machine.getField(2) != storage.getCapacity() ||
                    machine.getField(3) != storage.getMaxReceive() ||
                    machine.getField(4) != storage.getMaxExtract()) {
                if (listener instanceof EntityPlayerMP) {
                    Messages.INSTANCE.sendTo(new PacketGetEnergy(storage.getEnergyStored(), storage.getCapacity(), storage.getMaxReceive(), storage.getMaxExtract()), (EntityPlayerMP) listener);
                }
            }
        }
        progress = machine.getField(0);
    }

    @Override
    public void syncEnergy(int energy, int capacity, int maxReceive, int maxExtract) {
        machine.setField(1, energy);
        machine.setField(2, capacity);
        machine.setField(3, maxReceive);
        machine.setField(4, maxExtract);
    }
}
