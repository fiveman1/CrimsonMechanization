package fiveman1.crimsonmechanization.network;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.inventory.container.IMachineContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetEnergy implements IMessage {
    private int energy;
    private int capacity;
    private int maxReceive;
    private int maxExtract;

    @Override
    public void fromBytes(ByteBuf buf) {
        energy = buf.readInt();
        capacity = buf.readInt();
        maxReceive = buf.readInt();
        maxExtract = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(energy);
        buf.writeInt(capacity);
        buf.writeInt(maxReceive);
        buf.writeInt(maxExtract);
    }

    // have to include default constructor for registry
    public PacketGetEnergy() {}

    public PacketGetEnergy(int energy, int capacity, int maxReceive, int maxExtract) {
        this.energy = energy;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxReceive;
    }

    public static class Handler implements IMessageHandler<PacketGetEnergy, IMessage> {

        @Override
        public IMessage onMessage(PacketGetEnergy message, MessageContext ctx) {
            CrimsonMechanization.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketGetEnergy message, MessageContext ctx) {
            EntityPlayer player = CrimsonMechanization.proxy.getClientPlayer();
            if (player.openContainer instanceof IMachineContainer) {
                ((IMachineContainer) player.openContainer).syncEnergy(message.energy, message.capacity, message.maxReceive, message.maxExtract);
            }
        }
    }
}
