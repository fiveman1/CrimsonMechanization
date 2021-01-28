package fiveman1.crimsonmechanization.enums;

public enum MachineTier {

    CRIMSON("crimson", 20, 100000, 2),
    REFINED("refined", 40, 200000, 4),
    NIGHT("night", 60, 300000, 8),
    IRIDESCENT("iridescent", 80, 400000, 16)
    ;

    private final String name;
    private final int energyUse;
    private final int capacity;
    private final int upgradeStackLimit;

    public static final MachineTier[] values = values();

    MachineTier(String nameIn, int energyUse, int capacity, int upgradeStackLimit) {
        this.name = nameIn;
        this.energyUse = energyUse;
        this.capacity = capacity;
        this.upgradeStackLimit = upgradeStackLimit;
    }

    public String getName() {
        return name;
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

}