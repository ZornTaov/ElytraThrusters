package org.zornco.elytrathrusters.datagen;

import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.zornco.elytrathrusters.Constants;
import org.zornco.elytrathrusters.Registries;

public class EnglishLangGenerator extends LanguageProvider {
    public EnglishLangGenerator(DataGenerator gen) {
        super(gen.getPackOutput(), Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        add(Registries.ROCKET_THRUSTER.get(), "Rocket Thruster");
        add("misc.elytrathrusters.energy_stored", "%1$s/%2$s FE");
    }
}
