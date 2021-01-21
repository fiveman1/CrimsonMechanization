package fiveman1.crimsonmechanization.network;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.tile.TileMachine;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketServerToClient implements IMessage {

    public static byte ENERGY_ID = 0;
    public static byte ENERGY_STORAGE_ID = 1;
    public static byte PROGRESS_ID = 2;
    public static byte RECIPE_ENERGY_ID = 3;
    public static byte TIER_ID = 4;
    public static byte ENERGY_RATE_ID = 5;

    private int[] args;
    private byte packetID;
    private byte size;

    @Override
    public void fromBytes(ByteBuf buf) {
        packetID = buf.readByte();
        size = buf.readByte();
        args = new int[size];
        for (int i = 0; i < size; i++) {
            args[i] = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(packetID);
        buf.writeByte(size);
        for (int i = 0; i < size; i++) {
            buf.writeInt(args[i]);
        }
    }

    // have to include default constructor for registry
    public PacketServerToClient() {}

    public PacketServerToClient(byte packetID, int... args) {
        this.packetID = packetID;
        this.size = (byte) args.length;
        this.args = args;
    }

    public static class Handler implements IMessageHandler<PacketServerToClient, IMessage> {

        @Override
        public IMessage onMessage(PacketServerToClient message, MessageContext ctx) {
            CrimsonMechanization.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketServerToClient message, MessageContext ctx) {
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
            } else if (message.packetID == TIER_ID) {
                container.updateProgressBar(TileMachine.TIER_ID, message.args[0]);
            } else if (message.packetID == ENERGY_RATE_ID) {
                container.updateProgressBar(TileMachine.ENERGY_RATE_ID, message.args[0]);
            }
        }
    }
}
