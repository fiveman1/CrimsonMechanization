package fiveman1.crimsonmechanization.util;

import fiveman1.crimsonmechanization.network.PacketClientToServer;
import fiveman1.crimsonmechanization.network.PacketHandler;
import fiveman1.crimsonmechanization.network.PacketServerToClient;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.PacketDistributor;

public class PacketUtil {

    public static void openUpgradeGui(TileEntity te) {
        BlockPos pos = te.getPos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        PacketHandler.INSTANCE.sendToServer(new PacketClientToServer(PacketClientToServer.UPGRADE_GUI_ID, x, y, z));
    }

    public static void openTileGui(TileEntity te) {
        BlockPos pos = te.getPos();
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        PacketHandler.INSTANCE.sendToServer(new PacketClientToServer(PacketClientToServer.TILE_GUI_ID, x, y, z));
    }

    public static void updateMachineStat(byte packetID, int value, ServerPlayerEntity player) {
        PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new PacketServerToClient(packetID, value));
    }
}
