package org.zornco.elytrathrusters;

import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.energy.EnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zornco.elytrathrusters.Items.RocketThrusterItem;

import java.util.function.Supplier;

public class DataAttachments {
    public static final Supplier<AttachmentType<EnergyStorage>> ITEM_ENERGY_ATTACHMENT = Registries.ATTACHMENT_TYPES.register("energy",
        () -> AttachmentType.builder((holder) ->
                holder instanceof ItemStack stack &&
                    stack.getItem() instanceof RocketThrusterItem rti ?
                    new EnergyStorage(rti.getEnergyCapacity()) : null
                )
            .serialize(new Serializer())
            .build());
    public static void prepare() {
    }

    static class Serializer implements IAttachmentSerializer<Tag, EnergyStorage>{

        Serializer(){}
        @Override
        public EnergyStorage read(@NotNull IAttachmentHolder holder, @NotNull Tag tag) {
            if(holder instanceof ItemStack stack &&
                stack.getItem() instanceof RocketThrusterItem rti)
            {
                EnergyStorage storage = new EnergyStorage(rti.getEnergyCapacity());
                storage.deserializeNBT(tag);
                return storage;
            }
            return null;
        }

        @Override
        public @Nullable Tag write(EnergyStorage energyStorage) {
            return energyStorage.serializeNBT();

        }
    }
}
