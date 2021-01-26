package fiveman1.crimsonmechanization.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {

    public CustomEnergyStorage() {
        super(0);
    }

    public CustomEnergyStorage(int capacity) {
        super(capacity);
    }

    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setCapacity(int newCapacity) {
        if (energy > newCapacity) {
            energy = newCapacity;
        }
        this.capacity = newCapacity;
    }

    public void setMaxReceive (int maxReceive) {
        this.maxReceive = maxReceive;
    }
    public void setMaxExtract (int maxExtract) {
        this.maxExtract = maxExtract;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public void read(CompoundNBT compound) {
        this.setEnergy(compound.getInt("energy"));
        this.setCapacity(compound.getInt("capacity"));
        this.setMaxReceive(compound.getInt("maxReceive"));
        this.setMaxExtract(compound.getInt("maxExtract"));
    }

    public void write(CompoundNBT compound) {
        compound.putInt("energy", this.getEnergyStored());
        compound.putInt("capacity", this.getCapacity());
        compound.putInt("maxReceive", this.getMaxReceive());
        compound.putInt("maxExtract", this.getMaxExtract());
    }
}
