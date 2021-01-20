package fiveman1.crimsonmechanization.util;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.network.Messages;
import fiveman1.crimsonmechanization.network.PacketClientToServer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketUtil {

    /*
    https://github.com/Ellpeck/ActuallyAdditions/blob/12206a03a383a035758d6615c5da3ae32adaac81/src/main/java/de/ellpeck/actuallyadditions/mod/network/PacketHandlerHelper.java#L30
    pretty much this
     */
    @SideOnly(Side.CLIENT)
    public static void sendButtonPacket(TileEntity te, int id) {
        NBTTagCompound compound = new NBTTagCompound();
        BlockPos pos = te.getPos();
        compound.setInteger("x", pos.getX());
        compound.setInteger("y", pos.getY());
        compound.setInteger("z", pos.getZ());
        compound.setInteger("world", te.getWorld().provider.getDimension());
        compound.setInteger("player", CrimsonMechanization.proxy.getClientPlayer().getEntityId());
        compound.setInteger("button", id);
        Messages.INSTANCE.sendToServer(new PacketClientToServer(PacketClientToServer.BUTTON_TO_TILE_ID, compound));
    }
}
