package com.mgmstudios.projectj.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import com.mgmstudios.projectj.ProjectJ;

public class ModBiomes
{
    public static DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(Registries.BIOME, ProjectJ.MOD_ID);

    public static void bootstrap(BootstrapContext<Biome> context)
    {
        HolderGetter<PlacedFeature> holdergetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holdergetter1 = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(ProjectJBiomes.HOT_RED, TestOverworldBiomes.hotRed(holdergetter, holdergetter1));
        context.register(ProjectJBiomes.COLD_BLUE, TestOverworldBiomes.coldBlue(holdergetter, holdergetter1));
    }

    public static void register(IEventBus eventBus){
        BIOME_REGISTER.register(eventBus);
    }
}