package fiveman1.crimsonmechanization.network;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetEnergy implements IMessage {
    private int energy;
    private int capacity;
    private int maxReceive;
    private int maxExtract;

    private PacketType packetType;

    @Override
    public void fromBytes(ByteBuf buf) {
        int id = buf.readByte();
        if (id == PacketType.ENERGY.getId()) {
            packetType = PacketType.ENERGY;
            energy = buf.readInt();
        } else if (id == PacketType.ENERGY_STORAGE.getId()) {
            packetType = PacketType.ENERGY_STORAGE;
            capacity = buf.readInt();
            maxReceive = buf.readInt();
            maxExtract = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if (packetType == PacketType.ENERGY) {
            buf.writeByte(PacketType.ENERGY.getId());
            buf.writeInt(energy);
        } else if (packetType == PacketType.ENERGY_STORAGE) {
            buf.writeByte(PacketType.ENERGY_STORAGE.getId());
            buf.writeInt(capacity);
            buf.writeInt(maxReceive);
            buf.writeInt(maxExtract);
        }
    }

    // have to include default constructor for registry
    public PacketGetEnergy() {}

    public PacketGetEnergy(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxReceive;
        this.packetType = PacketType.ENERGY_STORAGE;
    }

    public PacketGetEnergy(int energy) {
        this.energy = energy;
        this.packetType = PacketType.ENERGY;
    }

    public static class Handler implements IMessageHandler<PacketGetEnergy, IMessage> {

        @Override
        public IMessage onMessage(PacketGetEnergy message, MessageContext ctx) {
            CrimsonMechanization.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketGetEnergy message, MessageContext ctx) {
            EntityPlayer player = CrimsonMechanization.proxy.getClientPlayer();
            Container container = player.openContainer;
            if (message.packetType == PacketType.ENERGY) {
                container.updateProgressBar(1, message.energy);
            } else if (message.packetType == PacketType.ENERGY_STORAGE) {
                container.updateProgressBar(2, message.capacity);
                container.updateProgressBar(3, message.maxReceive);
                container.updateProgressBar(4, message.maxExtract);
            }
        }
    }

    enum PacketType  {
        ENERGY(0),
        ENERGY_STORAGE(1);

        private int id;

        PacketType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
