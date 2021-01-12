package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.network.Messages;
import fiveman1.crimsonmechanization.network.PacketMachineInfo;
import fiveman1.crimsonmechanization.tile.TileMachine;
import fiveman1.crimsonmechanization.util.CustomEnergyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ContainerMachine extends ContainerBase {

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
        CustomEnergyStorage storage = machine.energyStorage;
        if (machine.getField(TileMachine.ENERGY_ID) != storage.getEnergyStored()) {
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    Messages.INSTANCE.sendTo(new PacketMachineInfo(storage.getEnergyStored()), (EntityPlayerMP) listener);
                }
            }
        }
        if (machine.getField(TileMachine.CAPACITY_ID) != storage.getCapacity() ||
                machine.getField(TileMachine.MAX_RECEIVE_ID) != storage.getMaxReceive() ||
                machine.getField(TileMachine.MAX_EXTRACT_ID) != storage.getMaxExtract()) {
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    Messages.INSTANCE.sendTo(new PacketMachineInfo(storage.getCapacity(), storage.getMaxReceive(), storage.getMaxExtract()), (EntityPlayerMP) listener);
                }
            }
        }
        if (machine.getField(TileMachine.PROGRESS_ID) != machine.progress ||
                machine.getField(TileMachine.RECIPE_ENERGY_ID) != machine.getRecipeEnergy()) {
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    Messages.INSTANCE.sendTo(new PacketMachineInfo(machine.progress, machine.getRecipeEnergy()), (EntityPlayerMP) listener);
                }
            }
        }
    }
}
