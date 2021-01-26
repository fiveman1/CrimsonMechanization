package fiveman1.crimsonmechanization;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import fiveman1.crimsonmechanization.client.ClientSetup;
import fiveman1.crimsonmechanization.datagen.BlockStates;
import fiveman1.crimsonmechanization.datagen.Items;
import fiveman1.crimsonmechanization.inventory.container.ContainerRegistration;
import fiveman1.crimsonmechanization.items.ItemRegistration;
import fiveman1.crimsonmechanization.network.PacketHandler;
import fiveman1.crimsonmechanization.recipe.RecipeSerializerRegistration;
import fiveman1.crimsonmechanization.recipe.RecipeTypeRegistration;
import fiveman1.crimsonmechanization.recipe.managers.RecipeManagerHandler;
import fiveman1.crimsonmechanization.tile.TileRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.data.DataGenerator;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CrimsonMechanization.MODID)
public class CrimsonMechanization {

    public static final String MODID = "crimsonmechanization";

    public static IEventBus MOD_EVENT_BUS;
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public CrimsonMechanization() {
        MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        MOD_EVENT_BUS.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        MOD_EVENT_BUS.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        MOD_EVENT_BUS.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        MOD_EVENT_BUS.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        RecipeManagerHandler.init();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
    }

    @SubscribeEvent
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        if (event.getServer().isDedicatedServer()) {
            RecipeManagerHandler.onRefresh(event.getServer().getRecipeManager());
        }
    }

    @SubscribeEvent
    public void onRecipesUpdate(RecipesUpdatedEvent event) {
        RecipeManagerHandler.onRefresh(event.getRecipeManager());
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            BlockRegistration.onBlocksRegistry(blockRegistryEvent);
        }

        @SubscribeEvent
        public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegistryEvent) {
            BlockRegistration.onItemsRegistration(itemRegistryEvent);
            ItemRegistration.onItemsRegistration(itemRegistryEvent);
        }

        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            PacketHandler.onCommonSetupEvent(event);
        }

        @SubscribeEvent
        public static void registerTE(RegistryEvent.Register<TileEntityType<?>> event) {
            TileRegistration.registerTE(event);
        }

        @SubscribeEvent
        public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
            ContainerRegistration.registerContainers(event);
        }

        @SubscribeEvent
        public static void registerRecipeSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
            RecipeTypeRegistration.register();
            RecipeSerializerRegistration.register(event);
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            if (event.includeClient()) {
                generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
                generator.addProvider(new Items(generator, event.getExistingFileHelper()));
            }
        }
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            BlockRegistration.onClientSetup(event);
            ClientSetup.init(event);
        }
    }
}
