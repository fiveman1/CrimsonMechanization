package fiveman1.crimsonmechanization.items;

import fiveman1.crimsonmechanization.enums.BaseMaterial;
import fiveman1.crimsonmechanization.items.materials.*;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemRegistration {

    public static List<Item> ITEMS = new ArrayList<>();

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
    public static GemItem gemNight;

    public static IngotItem ingotCrimsonIron;
    public static IngotItem ingotCrimsonSteel;
    public static IngotItem ingotCopper;
    public static IngotItem ingotTin;
    public static IngotItem ingotBronze;

    public static NuggetItem nuggetCrimsonIron;
    public static NuggetItem nuggetCrimsonSteel;
    public static NuggetItem nuggetCopper;
    public static NuggetItem nuggetTin;
    public static NuggetItem nuggetBronze;

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

    public static UpgradeItem upgradeSpeed;
    public static UpgradeItem upgradeEfficiency;
    public static UpgradeItem upgradeLuck;

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

        registerItems(registry, dustIron, dustGold, dustDiamond, dustEmerald, dustLapis, dustCoal, dustCrimson,
                dustCrimsonIron, dustCrimsonSteel, dustIridescent, dustCopper, dustTin, dustBronze);

        // GEMS
        gemCrimson = new GemItem(BaseMaterial.CRIMSON);
        gemNight = new GemItem(BaseMaterial.NIGHT);
        gemIridescent = new GemItem(BaseMaterial.IRIDESCENT);

        registerItems(registry, gemCrimson, gemIridescent, gemNight);

        // INGOTS
        ingotCrimsonIron = new IngotItem(BaseMaterial.CRIMSON_IRON);
        ingotCrimsonSteel = new IngotItem(BaseMaterial.CRIMSON_STEEL);
        ingotCopper = new IngotItem(BaseMaterial.COPPER);
        ingotTin = new IngotItem(BaseMaterial.TIN);
        ingotBronze = new IngotItem(BaseMaterial.BRONZE);

        registerItems(registry, ingotCrimsonIron, ingotCrimsonSteel, ingotCopper, ingotTin, ingotBronze);

        // NUGGETS
        nuggetBronze = new NuggetItem(BaseMaterial.BRONZE);
        nuggetCopper = new NuggetItem(BaseMaterial.COPPER);
        nuggetCrimsonIron = new NuggetItem(BaseMaterial.CRIMSON_IRON);
        nuggetCrimsonSteel = new NuggetItem(BaseMaterial.CRIMSON_STEEL);
        nuggetTin = new NuggetItem(BaseMaterial.TIN);

        registerItems(registry, nuggetBronze, nuggetCopper, nuggetCrimsonIron, nuggetCrimsonSteel, nuggetTin);

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

        registerItems(registry, plateIron, plateGold, plateDiamond, plateEmerald, plateLapis, plateCrimson,
                plateCrimsonIron, plateCrimsonSteel, plateIridescent, plateCopper, plateTin, plateBronze);

        upgradeSpeed = new UpgradeItem(UpgradeItem.Type.SPEED);
        upgradeEfficiency = new UpgradeItem(UpgradeItem.Type.EFFICIENCY);
        upgradeLuck = new UpgradeItem(UpgradeItem.Type.LUCK);

        registerItems(registry, upgradeSpeed, upgradeEfficiency, upgradeLuck);

    }

    private static void registerItems(IForgeRegistry<Item> registry, Item... items) {
        ITEMS.addAll(Arrays.asList(items));
        registry.registerAll(items);
    }
}
