package com.mgmstudios.projectj.worldgen;

import com.google.common.collect.ImmutableList;
import com.mgmstudios.projectj.block.ModBlocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModSurfaceRules
{
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource ADOBE_SAND = makeStateRule(ModBlocks.ADOBE_SAND.get());
    private static final SurfaceRules.RuleSource PACKED_ADOBE = makeStateRule(ModBlocks.PACKED_ADOBE.get());
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);
    private static final SurfaceRules.RuleSource SERPENTINITE = makeStateRule(ModBlocks.SERPENTINITE_ROCK.get());
    private static final SurfaceRules.RuleSource COBBLED_SERPENTINITE = makeStateRule(ModBlocks.COBBLED_SERPENTINITE.get());
    private static final SurfaceRules.RuleSource TUFF = makeStateRule(Blocks.TUFF);


    public static SurfaceRules.RuleSource makeRules() {
        // Biome condition sources
        SurfaceRules.ConditionSource isAdobeDesert = SurfaceRules.isBiome(ModBiomes.ADOBE_DESERT);
        SurfaceRules.ConditionSource isSerpentiniteHills = SurfaceRules.isBiome(ModBiomes.SERPENTINITE_HILLS);

        // Adobe Desert top and under layers
        SurfaceRules.RuleSource adobeTop = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(noiseAboveSurface(0.05), PACKED_ADOBE)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(isAdobeDesert, ADOBE_SAND)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.ifTrue(isAdobeDesert, ADOBE_SAND)
                )
        );
        SurfaceRules.RuleSource adobeUnder = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.DEEP_UNDER_FLOOR,
                        SurfaceRules.ifTrue(isAdobeDesert, PACKED_ADOBE)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.DEEP_UNDER_FLOOR,
                        SurfaceRules.ifTrue(
                                noiseAboveSurface(-0.1),
                                GRAVEL
                        )
                )
        );

        // Serpentinite Plains top and under layers (mirroring Adobe Desert)
        SurfaceRules.RuleSource serpTop = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(noiseAboveSurface(3), TUFF)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(isSerpentiniteHills, SERPENTINITE)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.ifTrue(isSerpentiniteHills, SERPENTINITE)
                )
        );
        SurfaceRules.RuleSource serpUnder = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.DEEP_UNDER_FLOOR,
                        SurfaceRules.ifTrue(isSerpentiniteHills, COBBLED_SERPENTINITE)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.DEEP_UNDER_FLOOR,
                        SurfaceRules.ifTrue(
                                isSerpentiniteHills, SurfaceRules.ifTrue(
                                        noiseCaveLayer(0.075),
                                       TUFF
                                )
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.DEEP_UNDER_FLOOR,
                        SurfaceRules.ifTrue(
                                isSerpentiniteHills, SurfaceRules.ifTrue(
                                        noiseCaveLayer(0.01),
                                        COBBLED_SERPENTINITE
                                )
                        )
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.DEEP_UNDER_FLOOR,
                        SurfaceRules.ifTrue(
                                noiseAboveSurface(-0.1),
                                GRAVEL
                        )
                )
        );

        // Assemble all rules
        ImmutableList.Builder<SurfaceRules.RuleSource> rules = ImmutableList.builder();

        // Bedrock layer
        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                        BEDROCK
                )
        );

        // Above preliminary surface: Adobe and Serpentinite layers
        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(isAdobeDesert, adobeTop)
                )
        );
        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(isAdobeDesert, adobeUnder)
                )
        );
        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(isSerpentiniteHills, serpTop)
                )
        );
        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        SurfaceRules.ifTrue(isSerpentiniteHills, serpUnder)
                )
        );

        // Deepslate layer
        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)),
                        DEEPSLATE
                )
        );

        // Fallback for any area in these biomes: rock
        rules.add(
                SurfaceRules.ifTrue(isAdobeDesert, SERPENTINITE)
        );
        rules.add(
                SurfaceRules.ifTrue(isSerpentiniteHills, SERPENTINITE)
        );

        // Water, gravel, and stone as global defaults
        rules.add(WATER);
        rules.add(GRAVEL);
        rules.add(STONE);

        return SurfaceRules.sequence(rules.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    private static SurfaceRules.ConditionSource noiseAboveSurface(double threshold) {
        // scale = 8.25 to match Minecraft's surface noise
        return SurfaceRules.noiseCondition(Noises.SURFACE, threshold / 8.25, Double.MAX_VALUE);
    }

    private static SurfaceRules.ConditionSource noiseCaveLayer(double thresh) {
        // match vanilla cave‐layer Y‐scale of ~8 (per noise_settings)
        return SurfaceRules.noiseCondition(Noises.CAVE_LAYER, thresh / 8.0, Double.MAX_VALUE);
    }
    private static SurfaceRules.ConditionSource noiseEntrance(double thresh) {
        return SurfaceRules.noiseCondition(Noises.CAVE_ENTRANCE, thresh / 4.0, Double.MAX_VALUE);
    }
    private static SurfaceRules.ConditionSource noiseCheese(double thresh) {
        return SurfaceRules.noiseCondition(Noises.CAVE_CHEESE, thresh / 0.6667, Double.MAX_VALUE);
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
