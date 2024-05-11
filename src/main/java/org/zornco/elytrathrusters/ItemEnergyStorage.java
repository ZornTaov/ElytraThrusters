package org.zornco.elytrathrusters;


import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

public class ItemEnergyStorage implements IEnergyStorage, INBTSerializable<Tag> {
    private final DataComponentType<Integer> componentType;
    private final ItemStack stack;
    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public ItemEnergyStorage(DataComponentType<Integer> componentType, ItemStack stack, int capacity) {
        this(componentType, stack, capacity, capacity, capacity);
    }

    public ItemEnergyStorage(DataComponentType<Integer> componentType, ItemStack stack, int capacity, int maxTransfer) {
        this(componentType, stack, capacity, maxTransfer, maxTransfer);
    }

    public ItemEnergyStorage(DataComponentType<Integer> componentType, ItemStack stack, int capacity, int maxReceive, int maxExtract) {
        this.componentType = componentType;
        this.stack = stack;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        int energyClamped = Math.clamp( stack.getOrDefault(componentType, 0), 0, capacity);
        stack.set(componentType, energyClamped);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energy = stack.getOrDefault(componentType, 0);
        if (!canReceive())
            return 0;

        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            energy += energyReceived;
            stack.set(componentType, energy);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energy = stack.getOrDefault(componentType, 0);
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
            stack.set(componentType, energy);
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return stack.getOrDefault(componentType, 0);
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    @Override
    public Tag serializeNBT(HolderLookup.@NotNull Provider provider) {
        return IntTag.valueOf(this.getEnergyStored());
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull Tag nbt) {
        if (!(nbt instanceof IntTag intNbt))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");

        stack.set(componentType, intNbt.getAsInt());
    }
}

