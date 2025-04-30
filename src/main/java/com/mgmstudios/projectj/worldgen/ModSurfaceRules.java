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

    private static SurfaceRules.ConditionSource noiseAboveSurface(double threshold) {
        // scale = 8.25 to match Minecraft's surface noise
        return SurfaceRules.noiseCondition(Noises.SURFACE, threshold / 8.25, Double.MAX_VALUE);
    }

    public static SurfaceRules.RuleSource makeRules() {
        // WE SHALL THANKETH LE CHAT

        // only apply within ADOBE_DESERT
        SurfaceRules.ConditionSource isAdobeDesert = SurfaceRules.isBiome(ModBiomes.ADOBE_DESERT);

        // water-level check (above sea level)
        SurfaceRules.ConditionSource atOrAboveWater = SurfaceRules.waterBlockCheck(-1, 0);

        // Define what goes on the very top of the desert
        SurfaceRules.RuleSource topLayer = SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        // …and that block is in ADOBE_DESERT, place Adobe Sand
                        SurfaceRules.ifTrue(noiseAboveSurface(0.05), PACKED_ADOBE)
                ),
                // if we're at the floor and in our biome, use Adobe Sand
                SurfaceRules.ifTrue(
                        SurfaceRules.ON_FLOOR,
                        // …and that block is in ADOBE_DESERT, place Adobe Sand
                        SurfaceRules.ifTrue(isAdobeDesert, ADOBE_SAND)
                ),
                SurfaceRules.ifTrue(
                        SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.ifTrue(isAdobeDesert, ADOBE_SAND)
                )
        );

        SurfaceRules.RuleSource underLayer = SurfaceRules.sequence(
                // 2a) Slightly deeper: another layer of Packed Adobe
                SurfaceRules.ifTrue(
                        SurfaceRules.DEEP_UNDER_FLOOR,
                        SurfaceRules.ifTrue(isAdobeDesert, PACKED_ADOBE)
                ),

                // 2b) Optionally carve in some pockets of gravel at depth
                SurfaceRules.ifTrue(
                        SurfaceRules.DEEP_UNDER_FLOOR,
                        SurfaceRules.ifTrue(
                                noiseAboveSurface(-0.1),
                                GRAVEL
                        )
                )
        );
        // Now compose the overall rule
        ImmutableList.Builder<SurfaceRules.RuleSource> rules = ImmutableList.builder();

        //  ─ Bedrock at the bottom ───────────────────────────────
        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                        BEDROCK
                )
        );

//  ─ Above the preliminary surface: our Adobe Desert logic ─
        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        topLayer
                )
        );
        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.abovePreliminarySurface(),
                        underLayer
                )
        );

//  ─ Deepslate layer ──────────────────────────────────────
        rules.add(
                SurfaceRules.ifTrue(
                        SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)),
                        DEEPSLATE
                )
        );

        rules.add(
                SurfaceRules.ifTrue(isAdobeDesert, SERPENTINITE)
        );

        // finally, fallback to vanilla stone, gravel, water, etc.
        rules.add(WATER);
        rules.add(GRAVEL);
        rules.add(STONE);

        return SurfaceRules.sequence(rules.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double value) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, value / 8.25, Double.MAX_VALUE);
    }
}
