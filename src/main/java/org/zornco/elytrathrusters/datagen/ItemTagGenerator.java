package org.zornco.elytrathrusters.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.zornco.elytrathrusters.Registries;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends ItemTagsProvider {
    public ItemTagGenerator(PackOutput packOut, BlockTagGenerator blocks, CompletableFuture<HolderLookup.Provider> lookups) {
        super(packOut, lookups, blocks.contentsGetter());
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

    }
}
