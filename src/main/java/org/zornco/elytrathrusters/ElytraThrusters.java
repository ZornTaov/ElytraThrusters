package org.zornco.elytrathrusters;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;


@Mod(Constants.MOD_ID)
public class ElytraThrusters {

    @SuppressWarnings("unused")
    public ElytraThrusters(IEventBus modBus) {

        Registries.setup(modBus);
        DataAttachments.prepare();

        modBus.addListener(CommonSetup::onRegisterCapabilities);
    }

    public static ResourceLocation rl(String id) {
        return new ResourceLocation(Constants.MOD_ID, id);
    }
}
