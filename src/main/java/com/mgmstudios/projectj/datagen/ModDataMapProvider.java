package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
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

        int standardBurnTime = AbstractFurnaceBlockEntity.BURN_TIME_STANDARD;
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModBlocks.MESQUITE_SLAB.getId(), new FurnaceFuel(standardBurnTime), false)
                .add(ModBlocks.MESQUITE_BENCH.getId(), new FurnaceFuel(standardBurnTime), false)
                .add(ModBlocks.MESQUITE_BENCH_CORNER.getId(), new FurnaceFuel(standardBurnTime), false)
                .add(ModBlocks.CHARCOAL_BLOCK.getId(), new FurnaceFuel(standardBurnTime * 8 * 5), false);
    }
}
