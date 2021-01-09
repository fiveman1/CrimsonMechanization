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

        // Server side

        // Client side
        INSTANCE.registerMessage(PacketGetEnergy.Handler.class, PacketGetEnergy.class, nextID(), Side.CLIENT);
    }
}
