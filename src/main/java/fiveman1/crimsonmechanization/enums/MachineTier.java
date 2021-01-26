package fiveman1.crimsonmechanization.enums;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public enum MachineTier {

    CRIMSON(0, "crimson", 20, 100000, 2),
    REFINED(1, "refined", 40, 200000, 4),
    IRIDESCENT(3, "iridescent", 80, 400000, 16)
    ;

    private final int meta;
    private final String name;
    private final int energyUse;
    private final int capacity;
    private final int upgradeStackLimit;

    public static final MachineTier[] values = values();
    private static final Int2ObjectOpenHashMap<MachineTier> metaToEnum = new Int2ObjectOpenHashMap<>();

    static {
        for (MachineTier machineTier : values) {
            metaToEnum.put(machineTier.getMetadata(), machineTier);
        }
    }

    MachineTier(int metaIn, String nameIn, int energyUse, int capacity, int upgradeStackLimit) {
        this.meta = metaIn;
        this.name = nameIn;
        this.energyUse = energyUse;
        this.capacity = capacity;
        this.upgradeStackLimit = upgradeStackLimit;
    }

    public String getName() {
        return name;
    }

    public int getMetadata()
    {
        return meta;
    }

    public int getEnergyUse() {
        return energyUse;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxReceive() {
        return energyUse * 6;
    }

    public int getUpgradeStackLimit() {
        return upgradeStackLimit;
    }

    public static MachineTier byMetadata(int meta) {
        MachineTier machineTier = metaToEnum.get(meta);
        return machineTier != null ? machineTier : CRIMSON;
    }
}