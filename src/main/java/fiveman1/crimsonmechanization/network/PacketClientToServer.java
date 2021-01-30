package fiveman1.crimsonmechanization.network;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.CompactorBlock;
import fiveman1.crimsonmechanization.blocks.CrusherBlock;
import fiveman1.crimsonmechanization.blocks.FurnaceBlock;
import fiveman1.crimsonmechanization.inventory.container.CompactorContainer;
import fiveman1.crimsonmechanization.inventory.container.CrusherContainer;
import fiveman1.crimsonmechanization.inventory.container.FurnaceContainer;
import fiveman1.crimsonmechanization.inventory.container.providers.CustomContainerProvider;
import fiveman1.crimsonmechanization.inventory.container.UpgradeContainer;
import fiveman1.crimsonmechanization.tile.AbstractMachineTile;
import fiveman1.crimsonmechanization.tile.CompactorTile;
import fiveman1.crimsonmechanization.tile.CrusherTile;
import fiveman1.crimsonmechanization.tile.FurnaceTile;
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
                    containerProvider = new CustomContainerProvider<>(CompactorBlock.NAME,
                            (id, inv) -> new CompactorContainer(id, inv, (CompactorTile) te));
                } else if (te instanceof FurnaceTile) {
                    containerProvider = new CustomContainerProvider<>(FurnaceBlock.NAME,
                            (id, inv) -> new FurnaceContainer(id, inv, (FurnaceTile) te));
                } else if (te instanceof CrusherTile) {
                    containerProvider = new CustomContainerProvider<>(CrusherBlock.NAME,
                            (id, inv) -> new CrusherContainer(id, inv, (CrusherTile) te));
                }
                if (containerProvider != null) {
                    NetworkHooks.openGui(player, containerProvider, pos);
                } else {
                    CrimsonMechanization.LOGGER.warn("Tile Entity at (X: " + pos.getX() + ", Y: " + pos.getY() + ", Z: " + pos.getZ() + ") does not have a valid container provider!");
                }
            }

        });
        ctx.get().setPacketHandled(true);
    }
}
