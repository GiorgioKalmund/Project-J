package com.mgmstudios.projectj.datagen;

import com.mgmstudios.projectj.worldgen.ModBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.mgmstudios.projectj.ProjectJ;

public class ModBiomesProvider
{
    public static DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(Registries.BIOME, ProjectJ.MOD_ID);

    public static void bootstrap(BootstrapContext<Biome> context)
    {
        HolderGetter<PlacedFeature> holdergetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holdergetter1 = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(ModBiomes.ADOBE_DESERT, ModBiomeBuilder.adobeDesert(holdergetter, holdergetter1));
    }

    public static void register(IEventBus eventBus){
        BIOME_REGISTER.register(eventBus);
    }
}