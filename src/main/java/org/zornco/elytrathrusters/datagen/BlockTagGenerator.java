package org.zornco.elytrathrusters.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.zornco.elytrathrusters.Constants;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends BlockTagsProvider {

    public BlockTagGenerator(PackOutput packOut, ExistingFileHelper files, CompletableFuture<HolderLookup.Provider> lookup) {
        super(packOut, lookup, Constants.MOD_ID, files);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

    }
}
