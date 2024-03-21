package org.zornco.elytrathrusters.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.zornco.elytrathrusters.Constants;

public class ETItemModelGenerator extends ItemModelProvider {

    public ETItemModelGenerator(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        basic(modLoc("personal_thruster"))
            .texture("layer0", modLoc("item/personal_thruster"));
    }

    private ItemModelBuilder basic(ResourceLocation name) {
        return withExistingParent(name.getPath(), mcLoc("item/generated"));
    }
}
