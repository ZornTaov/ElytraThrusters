package org.zornco.elytrathrusters;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.zornco.elytrathrusters.Items.RocketThrusterItem;

import java.util.function.Consumer;

import static org.zornco.elytrathrusters.Constants.MOD_ID;

public interface Registries {
    //DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID);
    DeferredRegister<DataComponentType<?>> COMPONENT_TYPES = DeferredRegister.create(net.minecraft.core.registries.Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);
    DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);

    DeferredItem<RocketThrusterItem> ROCKET_THRUSTER = Registries.ITEMS.register("personal_thruster",
        () -> new RocketThrusterItem(new Item.Properties().stacksTo(1), false, 64_000));

    DataComponentType<Integer> STORED_ENERGY = register("stored_energy",
        builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    DataComponentType<Integer> ENERGY_CAPACITY = register("energy_capacity",
        builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    private static <T> DataComponentType<T> register(String name, Consumer<DataComponentType.Builder<T>> customizer) {
        var builder = DataComponentType.<T>builder();
        customizer.accept(builder);
        var componentType = builder.build();
        COMPONENT_TYPES.register(name, () -> componentType);
        return componentType;
    }

    static void setup(IEventBus modBus) {
//        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TABS.register(modBus);
        COMPONENT_TYPES.register(modBus);
        ATTACHMENT_TYPES.register(modBus);


        modBus.addListener((BuildCreativeModeTabContentsEvent addToTabs) -> {
            if(addToTabs.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                RocketThrusterItem thrusterItem = ROCKET_THRUSTER.get();
                addToTabs.accept(thrusterItem);

                ItemStack stack = new ItemStack(thrusterItem);
                stack.set(STORED_ENERGY, 40000);
                addToTabs.accept(stack);
            }
        });
    }
}
