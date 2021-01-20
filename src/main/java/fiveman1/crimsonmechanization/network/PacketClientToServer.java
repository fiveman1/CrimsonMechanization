package fiveman1.crimsonmechanization.network;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.IOException;

/*
https://github.com/Ellpeck/ActuallyAdditions/blob/12206a03a383a035758d6615c5da3ae32adaac81/src/main/java/de/ellpeck/actuallyadditions/mod/network/PacketClientToServer.java
derived from this implementation
 */

public class PacketClientToServer implements IMessage {

    public static final byte BUTTON_TO_TILE_ID = 0;

    private NBTTagCompound data;
    private byte packetID;

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        try {
            packetID = buffer.readByte();
            data = buffer.readCompoundTag();

        } catch (IOException e) {
            CrimsonMechanization.logger.error("Error handling client to server packet");
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeByte(packetID);
        buffer.writeCompoundTag(data);
    }

    public PacketClientToServer() {}

    public PacketClientToServer(byte packetID, NBTTagCompound data) {
        this.data = data;
        this.packetID = packetID;
    }

    public static class Handler implements IMessageHandler<PacketClientToServer, IMessage> {

        @Override
        public IMessage onMessage(PacketClientToServer message, MessageContext ctx) {
            CrimsonMechanization.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketClientToServer message, MessageContext ctx) {
            if (message.packetID == BUTTON_TO_TILE_ID) {
                World world = DimensionManager.getWorld(message.data.getInteger("world"));
                Entity entity = world.getEntityByID(message.data.getInteger("player"));
                if (entity instanceof EntityPlayer) {
                    ((EntityPlayer) entity).openGui(CrimsonMechanization.instance, message.data.getInteger("button"), world,
                            message.data.getInteger("x"), message.data.getInteger("y"), message.data.getInteger("z"));
                }
            }
        }
    }
}
