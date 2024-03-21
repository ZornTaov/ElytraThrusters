package org.zornco.elytrathrusters;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.zornco.elytrathrusters.Items.RocketThrusterItem;

import static org.zornco.elytrathrusters.Constants.MOD_ID;

public interface Registries {
    //DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);

    public static final DeferredItem<RocketThrusterItem> PERSONAL_THRUSTER = Registries.ITEMS.register("personal_thruster",
        () -> new RocketThrusterItem(new Item.Properties().stacksTo(1), false, 64_000));

    DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID);
    static void setup(IEventBus modBus) {

//        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TABS.register(modBus);

        ATTACHMENT_TYPES.register(modBus);


        modBus.addListener((BuildCreativeModeTabContentsEvent addToTabs) -> {
            if(addToTabs.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                addToTabs.accept(PERSONAL_THRUSTER.get());
            }
        });
    }
}
