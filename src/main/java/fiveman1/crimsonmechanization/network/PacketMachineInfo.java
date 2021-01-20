package fiveman1.crimsonmechanization.network;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.tile.TileMachine;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMachineInfo implements IMessage {

    public static byte ENERGY_ID = 0;
    public static byte ENERGY_STORAGE_ID = 1;
    public static byte PROGRESS_ID = 2;
    public static byte RECIPE_ENERGY_ID = 3;

    // if you implement a packet type with more than 4 arguments
    // then you better increase the args size
    private int[] args = new int[4];
    private byte packetID;

    @Override
    public void fromBytes(ByteBuf buf) {
        packetID = buf.readByte();
        if (packetID == ENERGY_ID || packetID == PROGRESS_ID || packetID == RECIPE_ENERGY_ID) {
            args[0] = buf.readInt();
        } else if (packetID == ENERGY_STORAGE_ID) {
            args[0] = buf.readInt();
            args[1] = buf.readInt();
            args[2] = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(packetID );
        if (packetID == ENERGY_ID || packetID == PROGRESS_ID || packetID == RECIPE_ENERGY_ID) {
            buf.writeInt(args[0]);
        } else if (packetID == ENERGY_STORAGE_ID) {
            buf.writeInt(args[0]);
            buf.writeInt(args[1]);
            buf.writeInt(args[2]);
        }
    }

    // have to include default constructor for registry
    public PacketMachineInfo() {}

    public PacketMachineInfo (byte packetID, int... args) {
        this.packetID = packetID;
        this.args = args;
    }

    public static class Handler implements IMessageHandler<PacketMachineInfo, IMessage> {

        @Override
        public IMessage onMessage(PacketMachineInfo message, MessageContext ctx) {
            CrimsonMechanization.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketMachineInfo message, MessageContext ctx) {
            EntityPlayer player = CrimsonMechanization.proxy.getClientPlayer();
            Container container = player.openContainer;
            if (message.packetID == ENERGY_ID) {
                container.updateProgressBar(TileMachine.ENERGY_ID, message.args[0]);
            } else if (message.packetID == ENERGY_STORAGE_ID) {
                container.updateProgressBar(TileMachine.CAPACITY_ID, message.args[0]);
                container.updateProgressBar(TileMachine.MAX_RECEIVE_ID, message.args[1]);
                container.updateProgressBar(TileMachine.MAX_EXTRACT_ID, message.args[2]);
            } else if (message.packetID == PROGRESS_ID) {
                container.updateProgressBar(TileMachine.PROGRESS_ID, message.args[0]);
            } else if (message.packetID == RECIPE_ENERGY_ID) {
                container.updateProgressBar(TileMachine.RECIPE_ENERGY_ID, message.args[0]);
            }
        }
    }
}
