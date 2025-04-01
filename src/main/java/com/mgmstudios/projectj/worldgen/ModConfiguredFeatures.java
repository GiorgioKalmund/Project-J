package com.mgmstudios.projectj.worldgen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?,?>> MESQUITE_KEY = registerKey("mesquite");

    public static final ResourceKey<ConfiguredFeature<?,?>> ADOBE_PATCH_KEY = registerKey("adobe_patch");

    public static final ResourceKey<ConfiguredFeature<?,?>> PYRITE_ORE_KEY = registerKey("pyrite_ore");

    public static final ResourceKey<ConfiguredFeature<?,?>> JADE_ORE_KEY = registerKey("jade_ore");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest ruletest = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        RuleTest ruletest1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest ruletest2 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest ruletest3 = new BlockMatchTest(Blocks.NETHERRACK);
        RuleTest ruletest4 = new TagMatchTest(BlockTags.BASE_STONE_NETHER);

        List<OreConfiguration.TargetBlockState> list = List.of(
                OreConfiguration.target(ruletest1, ModBlocks.JADE_ORE.get().defaultBlockState()),
                OreConfiguration.target(ruletest2, ModBlocks.DEEPSLATE_JADE_ORE.get().defaultBlockState())
        );

       register(context, JADE_ORE_KEY, Feature.ORE, new OreConfiguration(list, 4));

        RuleTest sandReplaceables = new TagMatchTest(ModTags.Blocks.PYRITE_ORE_REPLACEABLES);

        register(context, PYRITE_ORE_KEY, Feature.ORE, new OreConfiguration(sandReplaceables, ModBlocks.PYRITE_ORE.get().defaultBlockState(),10));

        register(context, MESQUITE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MESQUITE_LOG.get()),
                new CherryTrunkPlacer(
                        4,
                        1,
                        0,
                        new WeightedListInt(
                                SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 1).add(ConstantInt.of(3), 1).build()
                        ),
                        UniformInt.of(2, 3),
                        UniformInt.of(-2, -1),
                        UniformInt.of(-1, 0)
                ),

                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(ModBlocks.MESQUITE_LEAVES.get().defaultBlockState(), 1)
                        ),
                        new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),

                new TwoLayersFeatureSize(1, 0, 2)).build()
        );

        register(context, ADOBE_PATCH_KEY, Feature.DISK, new DiskConfiguration(
                RuleBasedBlockStateProvider.simple(ModBlocks.RAW_ADOBE.get()),
                BlockPredicate.matchesBlocks(
                        Blocks.SAND
                ),
                UniformInt.of(2, 6),
                1
        ));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
