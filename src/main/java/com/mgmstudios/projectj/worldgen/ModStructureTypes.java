package com.mgmstudios.projectj.worldgen;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.worldgen.structure.BigOlmecHeadStructure;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE, ProjectJ.MOD_ID);

    public static final DeferredHolder<StructureType<?>, StructureType<BigOlmecHeadStructure>> BIG_OLMEC_HEAD = STRUCTURE_TYPES.register("big_olmec_head", () -> () -> BigOlmecHeadStructure.CODEC);

    public static void register(IEventBus bus){
        STRUCTURE_TYPES.register(bus);
    }

}
