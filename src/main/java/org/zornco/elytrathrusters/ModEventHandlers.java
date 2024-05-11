package org.zornco.elytrathrusters;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventHandlers {

    @SubscribeEvent
    public static void onRegisterCapabilities(final RegisterCapabilitiesEvent e) {
        e.registerItem(
            Capabilities.EnergyStorage.ITEM,
            (stack, ctx) -> new ItemEnergyStorage(Registries.STORED_ENERGY, stack, 64_000),
            Registries.ROCKET_THRUSTER.get()
        );
    }
}
