package com.mgmstudios.projectj.worldgen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.worldgen.structure.BigOlmecHeadStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
import net.neoforged.neoforge.common.world.StructureSettingsBuilder;

import java.util.function.Function;

public class ModStructures {

    public static final ResourceKey<Structure> BIG_OLMEC_HEAD = createKey("big_olmec_head");

    public static void bootstrap(BootstrapContext<Structure> context) {
        HolderGetter<Biome> holdergetter = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templatePools = context.lookup(Registries.TEMPLATE_POOL);

        context.register(BIG_OLMEC_HEAD, new BigOlmecHeadStructure(new Structure.StructureSettings.Builder(
                holdergetter.getOrThrow(BiomeTags.HAS_SWAMP_HUT))
                .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                .build()
        ));

    }

    private static ResourceKey<Structure> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
    }
}
