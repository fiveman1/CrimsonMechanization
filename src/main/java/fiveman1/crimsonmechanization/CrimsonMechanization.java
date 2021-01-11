package fiveman1.crimsonmechanization;

import fiveman1.crimsonmechanization.proxy.CommonProxy;
import fiveman1.crimsonmechanization.recipe.CompactorRecipeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CrimsonMechanization.MODID, name = CrimsonMechanization.NAME, version = CrimsonMechanization.VERSION, useMetadata = true)
public class CrimsonMechanization {

    public static final String MODID = "crimsonmechanization";
    public static final String NAME = "Crimson Mechanization";
    public static final String VERSION = "0.0.1";

    @SidedProxy(clientSide = "fiveman1." + MODID + ".proxy.ClientProxy", serverSide = "fiveman1." + MODID + ".proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static CrimsonMechanization instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
        CompactorRecipeRegistry.initRecipes();
    }
}
