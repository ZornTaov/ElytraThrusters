package org.zornco.elytrathrusters.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.zornco.elytrathrusters.Constants;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGeneration {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        final var fileHelper = event.getExistingFileHelper();
        final var generator = event.getGenerator();

        final var packOut = generator.getPackOutput();
        final var holderLookup = event.getLookupProvider();

        // Server
        boolean server = event.includeServer();
        final var blocks = new BlockTagGenerator(packOut, fileHelper, holderLookup);
//        generator.addProvider(server, blocks);
//        generator.addProvider(server, new ItemTagGenerator(packOut, blocks, holderLookup));
        // CURIOS Integration
//        generator.addProvider(server, new CurioSlotGenerator(packOut, holderLookup, fileHelper));
//        generator.addProvider(server, new CurioEntityGenerator(packOut, holderLookup, fileHelper));


        // Client
        boolean client = event.includeClient();
        generator.addProvider(client, new ETItemModelGenerator(packOut, fileHelper));
        generator.addProvider(client, new EnglishLangGenerator(generator));
    }
}
