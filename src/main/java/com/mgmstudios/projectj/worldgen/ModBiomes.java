package com.mgmstudios.projectj.worldgen;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import com.mgmstudios.projectj.ProjectJ;

public class ModBiomes
{
    public static DeferredRegister<Biome> BIOME_REGISTER = DeferredRegister.create(Registries.BIOME, ProjectJ.MOD_ID);

    public static void registerBiomes()
    {
        register(ProjectJBiomes.HOT_RED, TestOverworldBiomes::hotRed);
        register(ProjectJBiomes.COLD_BLUE, TestOverworldBiomes::coldBlue);
    }

    public static DeferredHolder<Biome, Biome> register(ResourceKey<Biome> key, Supplier<Biome> biomeSupplier)
    {
        return BIOME_REGISTER.register(key.location().getPath(), biomeSupplier);
    }
}