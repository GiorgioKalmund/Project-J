package com.mgmstudios.projectj.fluid;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.fluid.custom.PyriteFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids
{
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, ProjectJ.MOD_ID);

    public static final Supplier<FlowingFluid> FLOWING_PYRITE =
            FLUIDS.register("flowing_pyrite", PyriteFluid.Flowing::new);

    public static final DeferredHolder<Fluid, FlowingFluid> PYRITE =
            FLUIDS.register("pyrite", PyriteFluid.Source::new);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
