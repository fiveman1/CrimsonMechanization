package fiveman1.crimsonmechanization.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class Messages {

    // credit to McJty for providing this implementation seen here:
    // https://www.youtube.com/watch?v=sDoVD7A3ETw

    public static SimpleNetworkWrapper INSTANCE;

    private static int ID = 0;
    private static int nextID() {
        return ID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);

        INSTANCE.registerMessage(PacketClientToServer.Handler.class, PacketClientToServer.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketMachineInfo.Handler.class, PacketMachineInfo.class, nextID(), Side.CLIENT);
    }
}
