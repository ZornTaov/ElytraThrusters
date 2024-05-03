package org.zornco.elytrathrusters;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

@Mod(Constants.MOD_ID)
public class ElytraThrusters {
    public static final Logger LOGGER = LogUtils.getLogger();
    @SuppressWarnings("unused")
    public ElytraThrusters(IEventBus modBus) {

        Registries.setup(modBus);

        modBus.addListener(ModEventHandlers::onRegisterCapabilities);
    }

    public static ResourceLocation rl(String id) {
        return new ResourceLocation(Constants.MOD_ID, id);
    }
}
