package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {

    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {

        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.MAIZE_SEEDS.getId(), new Compostable(0.25F), false)
                .add(ModItems.MAIZE.getId(), new Compostable(0.4F), false);
    }
}
