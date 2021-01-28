package fiveman1.crimsonmechanization.network;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class PacketHandler {

    private static int ID = 0;
    private static int nextID() {
        return ID++;
    }

    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;

    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(CrimsonMechanization.MODID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );

        INSTANCE.registerMessage(nextID(), PacketServerToClient.class,
                PacketServerToClient::encode,
                PacketServerToClient::decode,
                PacketServerToClient::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        INSTANCE.registerMessage(nextID(), PacketClientToServer.class,
                PacketClientToServer::encode,
                PacketClientToServer::decode,
                PacketClientToServer::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}
