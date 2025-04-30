package com.mgmstudios.projectj.particle;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, ProjectJ.MOD_ID);

    public static final Supplier<SimpleParticleType> TELEPORTATION_PARTICLES =
            PARTICLE_TYPES.register("teleportation_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
