package com.mgmstudios.projectj.worldgen;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class ModStructureSets {

    public static final ResourceKey<StructureSet> BIG_OLMEC_HEADS = createKey("big_olmec_heads");

    private static ResourceKey<StructureSet> createKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(ProjectJ.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

        context.register(BIG_OLMEC_HEADS, new StructureSet(structures.getOrThrow(ModStructures.BIG_OLMEC_HEAD),
                new RandomSpreadStructurePlacement(10, 5, RandomSpreadType.LINEAR, 2738116))
        );
    }


    }
