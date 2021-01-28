package fiveman1.crimsonmechanization.network;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.CompactorMachineBlock;
import fiveman1.crimsonmechanization.inventory.container.CompactorContainer;
import fiveman1.crimsonmechanization.inventory.container.providers.CustomContainerProvider;
import fiveman1.crimsonmechanization.inventory.container.UpgradeContainer;
import fiveman1.crimsonmechanization.tile.AbstractMachineTile;
import fiveman1.crimsonmechanization.tile.CompactorTile;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

public class PacketClientToServer {

    public static final byte UPGRADE_GUI_ID = 0;
    public static final byte TILE_GUI_ID = 1;

    private final int[] args;
    private final byte length;
    private final byte id;

    public PacketClientToServer(byte packetID, int... args) {
        this.id = packetID;
        this.args = args;
        this.length = (byte) args.length;
        if (args.length > 127) {
            throw new IllegalArgumentException("Too many args given");
        }
    }

    public static PacketClientToServer decode(PacketBuffer buf) {
        byte packetID = buf.readByte();
        byte argLength = buf.readByte();
        int[] packetArgs = buf.readVarIntArray(argLength);
        return new PacketClientToServer(packetID, packetArgs);
    }

    public void encode(PacketBuffer buf) {
        buf.writeByte(id);
        buf.writeByte(length);
        buf.writeVarIntArray(args);
    }

    public static void handle(PacketClientToServer message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player == null) return;
            if (message.id == UPGRADE_GUI_ID) {
                BlockPos pos = new BlockPos(message.args[0], message.args[1], message.args[2]);
                TileEntity te = player.world.getTileEntity(pos);
                if (te instanceof AbstractMachineTile) {
                    INamedContainerProvider containerProvider = new CustomContainerProvider<>("upgrade",
                            (id, inv) -> new UpgradeContainer(id, inv, (AbstractMachineTile) te));
                    NetworkHooks.openGui(player, containerProvider, pos);
                }
            } else if (message.id == TILE_GUI_ID) {
                BlockPos pos = new BlockPos(message.args[0], message.args[1], message.args[2]);
                TileEntity te = player.world.getTileEntity(pos);
                INamedContainerProvider containerProvider = null;
                if (te instanceof CompactorTile) {
                    containerProvider = new CustomContainerProvider<>(CompactorMachineBlock.NAME,
                            (id, inv) -> new CompactorContainer(id, inv, (CompactorTile) te));
                }
                if (containerProvider != null) {
                    NetworkHooks.openGui(player, containerProvider, pos);
                }
            }

        });
        ctx.get().setPacketHandled(true);
    }
}
