package com.mgmstudios.projectj.worldgen;

import com.mgmstudios.projectj.ProjectJ;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes
{
    public static final ResourceKey<Biome> ADOBE_DESERT = register("adobe_desert");

    private static ResourceKey<Biome> register(String name)
    {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
    }
}