package com.mgmstudios.projectj.worldgen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> MESQUITE_PLACED_KEY =  registerKey("mesquite_placed");

    public static final ResourceKey<PlacedFeature> ADOBE_PATCH_KEY =  registerKey("adobe_patch");

    public static final ResourceKey<PlacedFeature> PYRITE_ORE_PLACED_KEY =  registerKey("pyrite_ore_placed");

    public static final ResourceKey<PlacedFeature> JADE_ORE_PLACED_KEY = registerKey("jade_ore_placed");

    public static final ResourceKey<PlacedFeature> JADE_ORE_SERPENTINITE_PLACED_KEY = registerKey("jade_ore_serpentinite_placed");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, JADE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.JADE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(80,
                        HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))
                ));

        register(context, JADE_ORE_SERPENTINITE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.JADE_ORE_SERPENTINITE_KEY),
                ModOrePlacement.commonOrePlacement( 20, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
        );

        register(context, PYRITE_ORE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.PYRITE_ORE_KEY),
                    ModOrePlacement.commonOrePlacement( 10, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
               );

        register(context, MESQUITE_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.MESQUITE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1,0.1f,1),
                        ModBlocks.MESQUITE_SAPLING.get()));

        register(context, ADOBE_PATCH_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.ADOBE_PATCH_KEY),
                List.of(RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
