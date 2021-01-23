package fiveman1.crimsonmechanization.network;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketServerToClient implements IMessage {

    private static byte id = 0;
    private static byte nextID() {
        return id++;
    }

    public static byte ENERGY_ID = nextID();
    public static byte CAPACITY_ID = nextID();
    public static byte MAX_RECEIVE_ID = nextID();
    public static byte MAX_EXTRACT_ID = nextID();
    public static byte PROGRESS_ID = nextID();
    public static byte RECIPE_ENERGY_ID = nextID();
    public static byte TIER_ID = nextID();
    public static byte ENERGY_RATE_ID = nextID();

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
            if (message.packetID >= ENERGY_ID && message.packetID <= ENERGY_RATE_ID) {
                container.updateProgressBar(message.packetID, message.args[0]);
            }
        }
    }
}
