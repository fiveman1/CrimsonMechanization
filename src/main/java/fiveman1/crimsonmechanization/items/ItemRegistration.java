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

    public static DustItem DUST_IRON;
    public static DustItem DUST_GOLD;
    public static DustItem DUST_DIAMOND;
    public static DustItem DUST_EMERALD;
    public static DustItem DUST_LAPIS;
    public static DustItem DUST_COAL;
    public static DustItem DUST_CRIMSON;
    public static DustItem DUST_CRIMSON_IRON;
    public static DustItem DUST_CRIMSON_STEEL;
    public static DustItem DUST_IRIDESCENT;
    public static DustItem DUST_COPPER;
    public static DustItem DUST_TIN;
    public static DustItem DUST_BRONZE;

    public static GemItem GEM_CRIMSON;
    public static GemItem GEM_IRIDESCENT;
    public static GemItem GEM_NIGHT;

    public static IngotItem INGOT_CRIMSON_IRON;
    public static IngotItem INGOT_CRIMSON_STEEL;
    public static IngotItem INGOT_COPPER;
    public static IngotItem INGOT_TIN;
    public static IngotItem INGOT_BRONZE;

    public static NuggetItem NUGGET_CRIMSON_IRON;
    public static NuggetItem NUGGET_CRIMSON_STEEL;
    public static NuggetItem NUGGET_COPPER;
    public static NuggetItem NUGGET_TIN;
    public static NuggetItem NUGGET_BRONZE;

    public static PlateItem PLATE_IRON;
    public static PlateItem PLATE_GOLD;
    public static PlateItem PLATE_DIAMOND;
    public static PlateItem PLATE_EMERALD;
    public static PlateItem PLATE_LAPIS;
    public static PlateItem PLATE_CRIMSON;
    public static PlateItem PLATE_CRIMSON_IRON;
    public static PlateItem PlATE_CRIMSON_STEEL;
    public static PlateItem PLATE_IRIDESCENT;
    public static PlateItem PLATE_COPPER;
    public static PlateItem PLATE_TIN;
    public static PlateItem PLATE_BRONZE;

    public static UpgradeItem UPGRADE_SPEED;
    public static UpgradeItem UPGRADE_EFFICIENCY;
    public static UpgradeItem UPGRADE_LUCK;

    private static IForgeRegistry<Item> registry;

    public static void onItemsRegistration(final RegistryEvent.Register<Item> itemRegisterEvent) {
        registry = itemRegisterEvent.getRegistry();

        // DUSTS
        DUST_IRON = new DustItem(BaseMaterial.IRON);
        DUST_GOLD = new DustItem(BaseMaterial.GOLD);
        DUST_DIAMOND = new DustItem(BaseMaterial.DIAMOND);
        DUST_EMERALD = new DustItem(BaseMaterial.EMERALD);
        DUST_LAPIS = new DustItem(BaseMaterial.LAPIS);
        DUST_COAL = new DustItem(BaseMaterial.COAL);
        DUST_CRIMSON = new DustItem(BaseMaterial.CRIMSON);
        DUST_CRIMSON_IRON = new DustItem(BaseMaterial.CRIMSON_IRON);
        DUST_CRIMSON_STEEL = new DustItem(BaseMaterial.CRIMSON_STEEL);
        DUST_IRIDESCENT = new DustItem(BaseMaterial.IRIDESCENT);
        DUST_COPPER = new DustItem(BaseMaterial.COPPER);
        DUST_TIN = new DustItem(BaseMaterial.TIN);
        DUST_BRONZE = new DustItem(BaseMaterial.BRONZE);

        registerItems(DUST_IRON, DUST_GOLD, DUST_DIAMOND, DUST_EMERALD, DUST_LAPIS, DUST_COAL, DUST_CRIMSON,
                DUST_CRIMSON_IRON, DUST_CRIMSON_STEEL, DUST_IRIDESCENT, DUST_COPPER, DUST_TIN, DUST_BRONZE);

        // GEMS
        GEM_CRIMSON = new GemItem(BaseMaterial.CRIMSON);
        GEM_NIGHT = new GemItem(BaseMaterial.NIGHT);
        GEM_IRIDESCENT = new GemItem(BaseMaterial.IRIDESCENT);

        registerItems(GEM_CRIMSON, GEM_IRIDESCENT, GEM_NIGHT);

        // INGOTS
        INGOT_CRIMSON_IRON = new IngotItem(BaseMaterial.CRIMSON_IRON);
        INGOT_CRIMSON_STEEL = new IngotItem(BaseMaterial.CRIMSON_STEEL);
        INGOT_COPPER = new IngotItem(BaseMaterial.COPPER);
        INGOT_TIN = new IngotItem(BaseMaterial.TIN);
        INGOT_BRONZE = new IngotItem(BaseMaterial.BRONZE);

        registerItems(INGOT_CRIMSON_IRON, INGOT_CRIMSON_STEEL, INGOT_COPPER, INGOT_TIN, INGOT_BRONZE);

        // NUGGETS
        NUGGET_BRONZE = new NuggetItem(BaseMaterial.BRONZE);
        NUGGET_COPPER = new NuggetItem(BaseMaterial.COPPER);
        NUGGET_CRIMSON_IRON = new NuggetItem(BaseMaterial.CRIMSON_IRON);
        NUGGET_CRIMSON_STEEL = new NuggetItem(BaseMaterial.CRIMSON_STEEL);
        NUGGET_TIN = new NuggetItem(BaseMaterial.TIN);

        registerItems(NUGGET_BRONZE, NUGGET_COPPER, NUGGET_CRIMSON_IRON, NUGGET_CRIMSON_STEEL, NUGGET_TIN);

        // PLATES
        PLATE_IRON = new PlateItem(BaseMaterial.IRON);
        PLATE_GOLD = new PlateItem(BaseMaterial.GOLD);
        PLATE_DIAMOND = new PlateItem(BaseMaterial.DIAMOND);
        PLATE_EMERALD = new PlateItem(BaseMaterial.EMERALD);
        PLATE_LAPIS = new PlateItem(BaseMaterial.LAPIS);
        PLATE_CRIMSON = new PlateItem(BaseMaterial.CRIMSON);
        PLATE_CRIMSON_IRON = new PlateItem(BaseMaterial.CRIMSON_IRON);
        PlATE_CRIMSON_STEEL = new PlateItem(BaseMaterial.CRIMSON_STEEL);
        PLATE_IRIDESCENT = new PlateItem(BaseMaterial.IRIDESCENT);
        PLATE_COPPER = new PlateItem(BaseMaterial.COPPER);
        PLATE_TIN = new PlateItem(BaseMaterial.TIN);
        PLATE_BRONZE = new PlateItem(BaseMaterial.BRONZE);

        registerItems(PLATE_IRON, PLATE_GOLD, PLATE_DIAMOND, PLATE_EMERALD, PLATE_LAPIS, PLATE_CRIMSON,
                PLATE_CRIMSON_IRON, PlATE_CRIMSON_STEEL, PLATE_IRIDESCENT, PLATE_COPPER, PLATE_TIN, PLATE_BRONZE);

        // UPGRADES
        UPGRADE_SPEED = new UpgradeItem(UpgradeItem.Type.SPEED);
        UPGRADE_EFFICIENCY = new UpgradeItem(UpgradeItem.Type.EFFICIENCY);
        UPGRADE_LUCK = new UpgradeItem(UpgradeItem.Type.LUCK);

        registerItems(UPGRADE_SPEED, UPGRADE_EFFICIENCY, UPGRADE_LUCK);

    }

    private static void registerItems(Item... items) {
        ITEMS.addAll(Arrays.asList(items));
        registry.registerAll(items);
    }
}
