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

public abstract class ContainerMachine extends ContainerBase {

    protected int progress;
    protected final TileMachine machine;

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
        int field = machine.getField(0);
        if (field != progress) {
            for (IContainerListener listener : listeners) {
                listener.sendWindowProperty(this, 0, field);
            }
        }

        CustomEnergyStorage storage = machine.energyStorage;
        if (machine.getField(1) != storage.getEnergyStored()) {
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    Messages.INSTANCE.sendTo(new PacketGetEnergy(storage.getEnergyStored()), (EntityPlayerMP) listener);
                }
            }
        }
        if (machine.getField(2) != storage.getCapacity() ||
                machine.getField(3) != storage.getMaxReceive() ||
                machine.getField(4) != storage.getMaxExtract()) {
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    Messages.INSTANCE.sendTo(new PacketGetEnergy(storage.getCapacity(), storage.getMaxReceive(), storage.getMaxExtract()), (EntityPlayerMP) listener);
                }
            }
        }
        progress = machine.getField(0);
    }
}
