package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.enums.BaseMaterial;
import fiveman1.crimsonmechanization.items.materials.DustItem;
import fiveman1.crimsonmechanization.items.materials.GemItem;
import fiveman1.crimsonmechanization.items.materials.IngotItem;
import fiveman1.crimsonmechanization.items.materials.PlateItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class ItemRegistration {

    public static DustItem dustIron;
    public static DustItem dustGold;
    public static DustItem dustDiamond;
    public static DustItem dustEmerald;
    public static DustItem dustLapis;
    public static DustItem dustCoal;
    public static DustItem dustCrimson;
    public static DustItem dustCrimsonIron;
    public static DustItem dustCrimsonSteel;
    public static DustItem dustIridescent;
    public static DustItem dustCopper;
    public static DustItem dustTin;
    public static DustItem dustBronze;

    public static GemItem gemCrimson;
    public static GemItem gemIridescent;

    public static IngotItem ingotCrimsonIron;
    public static IngotItem ingotCrimsonSteel;
    public static IngotItem ingotCopper;
    public static IngotItem ingotTin;
    public static IngotItem ingotBronze;

    public static PlateItem plateIron;
    public static PlateItem plateGold;
    public static PlateItem plateDiamond;
    public static PlateItem plateEmerald;
    public static PlateItem plateLapis;
    public static PlateItem plateCrimson;
    public static PlateItem plateCrimsonIron;
    public static PlateItem plateCrimsonSteel;
    public static PlateItem plateIridescent;
    public static PlateItem plateCopper;
    public static PlateItem plateTin;
    public static PlateItem plateBronze;

    public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegisterEvent) {
        IForgeRegistry<Item> registry = itemRegisterEvent.getRegistry();

        // DUSTS
        dustIron = new DustItem(BaseMaterial.IRON);
        dustGold = new DustItem(BaseMaterial.GOLD);
        dustDiamond = new DustItem(BaseMaterial.DIAMOND);
        dustEmerald = new DustItem(BaseMaterial.EMERALD);
        dustLapis = new DustItem(BaseMaterial.LAPIS);
        dustCoal = new DustItem(BaseMaterial.COAL);
        dustCrimson = new DustItem(BaseMaterial.CRIMSON);
        dustCrimsonIron = new DustItem(BaseMaterial.CRIMSON_IRON);
        dustCrimsonSteel = new DustItem(BaseMaterial.CRIMSON_STEEL);
        dustIridescent = new DustItem(BaseMaterial.IRIDESCENT);
        dustCopper = new DustItem(BaseMaterial.COPPER);
        dustTin = new DustItem(BaseMaterial.TIN);
        dustBronze = new DustItem(BaseMaterial.BRONZE);

        registry.registerAll(dustIron, dustGold, dustDiamond, dustEmerald, dustLapis, dustCoal, dustCrimson,
                dustCrimsonIron, dustCrimsonSteel, dustIridescent, dustCopper, dustTin, dustBronze);

        // GEMS
        gemCrimson = new GemItem(BaseMaterial.CRIMSON);
        gemIridescent = new GemItem(BaseMaterial.IRIDESCENT);

        registry.registerAll(gemCrimson, gemIridescent);

        // INGOTS
        ingotCrimsonIron = new IngotItem(BaseMaterial.CRIMSON_IRON);
        ingotCrimsonSteel = new IngotItem(BaseMaterial.CRIMSON_STEEL);
        ingotCopper = new IngotItem(BaseMaterial.COPPER);
        ingotTin = new IngotItem(BaseMaterial.TIN);
        ingotBronze = new IngotItem(BaseMaterial.BRONZE);

        registry.registerAll(ingotCrimsonIron, ingotCrimsonSteel, ingotCopper, ingotTin, ingotBronze);

        // PLATES
        plateIron = new PlateItem(BaseMaterial.IRON);
        plateGold = new PlateItem(BaseMaterial.GOLD);
        plateDiamond = new PlateItem(BaseMaterial.DIAMOND);
        plateEmerald = new PlateItem(BaseMaterial.EMERALD);
        plateLapis = new PlateItem(BaseMaterial.LAPIS);
        plateCrimson = new PlateItem(BaseMaterial.CRIMSON);
        plateCrimsonIron = new PlateItem(BaseMaterial.CRIMSON_IRON);
        plateCrimsonSteel = new PlateItem(BaseMaterial.CRIMSON_STEEL);
        plateIridescent = new PlateItem(BaseMaterial.IRIDESCENT);
        plateCopper = new PlateItem(BaseMaterial.COPPER);
        plateTin = new PlateItem(BaseMaterial.TIN);
        plateBronze = new PlateItem(BaseMaterial.BRONZE);

        registry.registerAll(plateIron, plateGold, plateDiamond, plateEmerald, plateLapis, plateCrimson,
                plateCrimsonIron, plateCrimsonSteel, plateIridescent, plateCopper, plateTin, plateBronze);

    }
}
