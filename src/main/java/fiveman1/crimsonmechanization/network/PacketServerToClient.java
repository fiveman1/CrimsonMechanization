package fiveman1.crimsonmechanization.network;

import fiveman1.crimsonmechanization.inventory.container.MachineContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketServerToClient {

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
    public static byte ENERGY_RATE_ID = nextID();

    private final int[] args;
    private final byte packetID;
    private final byte size;

    public PacketServerToClient(byte packetID, int... args) {
        this.packetID = packetID;
        this.size = (byte) args.length;
        this.args = args;
    }

    public static PacketServerToClient decode(PacketBuffer buf) {
        byte packetID = buf.readByte();
        byte size = buf.readByte();
        int[] args = new int[size];
        for (int i = 0; i < size; i++) {
            args[i] = buf.readInt();
        }
        return new PacketServerToClient(packetID, args);
    }

    public void encode(PacketBuffer buf) {
        buf.writeByte(packetID);
        buf.writeByte(size);
        for (int i = 0; i < size; i++) {
            buf.writeInt(args[i]);
        }
    }

    public static void handle(PacketServerToClient message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            PlayerEntity player = Minecraft.getInstance().player;
            Container container = player.openContainer;
            if (message.packetID >= ENERGY_ID && message.packetID <= ENERGY_RATE_ID && container instanceof MachineContainer) {
                ((MachineContainer) container).updateClientData(message.packetID, message.args[0]);
            }

        });
        ctx.get().setPacketHandled(true);
    }
}
