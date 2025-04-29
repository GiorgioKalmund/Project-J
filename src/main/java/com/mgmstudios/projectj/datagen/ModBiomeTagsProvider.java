package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {

    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, ProjectJ.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256485_) {
    }
}
