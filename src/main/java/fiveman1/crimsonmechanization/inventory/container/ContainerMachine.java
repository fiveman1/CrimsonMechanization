package fiveman1.crimsonmechanization.inventory.container;

import fiveman1.crimsonmechanization.network.Messages;
import fiveman1.crimsonmechanization.network.PacketMachineInfo;
import fiveman1.crimsonmechanization.tile.TileMachine;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
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
        //CrimsonMechanization.logger.info("id: " + id + ", data : " + data);
        machine.setField(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        // TODO: only getField once for each ID
        if (machine.getField(TileMachine.ENERGY_ID) != ENERGY_STORED) {
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    Messages.INSTANCE.sendTo(new PacketMachineInfo(machine.getField(TileMachine.ENERGY_ID)), (EntityPlayerMP) listener);
                }
            }
        }
        if (machine.getField(TileMachine.CAPACITY_ID) != CAPACITY ||
                machine.getField(TileMachine.MAX_RECEIVE_ID) != MAX_RECEIVE ||
                machine.getField(TileMachine.MAX_EXTRACT_ID) != MAX_EXTRACT) {
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    Messages.INSTANCE.sendTo(new PacketMachineInfo(machine.getField(TileMachine.CAPACITY_ID),
                            machine.getField(TileMachine.MAX_RECEIVE_ID), machine.getField(TileMachine.MAX_EXTRACT_ID)), (EntityPlayerMP) listener);
                }
            }
        }
        // TODO: sync recipe energy separately because it doesn't change as often as progress
        if (machine.getField(TileMachine.PROGRESS_ID) != PROGRESS ||
                machine.getField(TileMachine.RECIPE_ENERGY_ID) != RECIPE_ENERGY) {
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    Messages.INSTANCE.sendTo(new PacketMachineInfo(machine.getField(TileMachine.PROGRESS_ID),
                            machine.getField(TileMachine.RECIPE_ENERGY_ID)), (EntityPlayerMP) listener);
                }
            }
        }
        ENERGY_STORED = machine.getField(TileMachine.ENERGY_ID);
        CAPACITY = machine.getField(TileMachine.CAPACITY_ID);
        MAX_RECEIVE = machine.getField(TileMachine.MAX_RECEIVE_ID);
        MAX_EXTRACT = machine.getField(TileMachine.MAX_EXTRACT_ID);
        PROGRESS = machine.getField(TileMachine.PROGRESS_ID);
        RECIPE_ENERGY = machine.getField(TileMachine.RECIPE_ENERGY_ID);
    }
}
