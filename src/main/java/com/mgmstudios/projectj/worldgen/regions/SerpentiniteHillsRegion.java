package com.mgmstudios.projectj.worldgen.regions;

import com.mgmstudios.projectj.worldgen.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class SerpentiniteHillsRegion extends Region
{
    public SerpentiniteHillsRegion(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        this.addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(Biomes.FROZEN_PEAKS, ModBiomes.SERPENTINITE_HILLS);
            builder.replaceBiome(Biomes.JAGGED_PEAKS, ModBiomes.SERPENTINITE_HILLS);
            builder.replaceBiome(Biomes.SNOWY_SLOPES, ModBiomes.SERPENTINITE_HILLS);
        });
    }
}