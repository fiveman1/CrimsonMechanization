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
    private int energy;
    private int capacity;
    private int maxReceive;
    private int maxExtract;
    private int progress;
    private int recipeEnergy;

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
        } else if (id == PacketType.PROGRESS.getId()) {
            packetType = PacketType.PROGRESS;
            progress = buf.readInt();
            recipeEnergy = buf.readInt();
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
        } else if (packetType == PacketType.PROGRESS) {
            buf.writeByte(PacketType.PROGRESS.getId());
            buf.writeInt(progress);
            buf.writeInt(recipeEnergy);
        }
    }

    // have to include default constructor for registry
    public PacketMachineInfo() {}

    public PacketMachineInfo(int energy) {
        this.energy = energy;
        this.packetType = PacketType.ENERGY;
    }

    public PacketMachineInfo(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxReceive;
        this.packetType = PacketType.ENERGY_STORAGE;
    }

    public PacketMachineInfo(int progress, int recipeEnergy) {
        this.progress = progress;
        this.recipeEnergy = recipeEnergy;
        this.packetType = PacketType.PROGRESS;
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
            if (message.packetType == PacketType.ENERGY) {
                container.updateProgressBar(TileMachine.ENERGY_ID, message.energy);
            } else if (message.packetType == PacketType.ENERGY_STORAGE) {
                container.updateProgressBar(TileMachine.CAPACITY_ID, message.capacity);
                container.updateProgressBar(TileMachine.MAX_RECEIVE_ID, message.maxReceive);
                container.updateProgressBar(TileMachine.MAX_EXTRACT_ID, message.maxExtract);
            } else if (message.packetType == PacketType.PROGRESS) {
                container.updateProgressBar(TileMachine.PROGRESS_ID, message.progress);
                container.updateProgressBar(TileMachine.RECIPE_ENERGY_ID, message.recipeEnergy);
            }
        }
    }

    enum PacketType  {
        ENERGY(0),
        ENERGY_STORAGE(1),
        PROGRESS(2);

        private int id;

        PacketType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
