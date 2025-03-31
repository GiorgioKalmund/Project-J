package com.mgmstudios.projectj.fluid;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, ProjectJ.MOD_ID);

    public static final DeferredHolder<FluidType, FluidType> PYRITE_TYPE = FLUID_TYPES.register("pyrite", () -> new FluidType(FluidType.Properties.create()
                    .descriptionId("block.projectj.liquid_pyrite")
                .canSwim(false)
                .canDrown(false)
                .pathType(PathType.LAVA)
                .adjacentPathType(null)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .lightLevel(15)
                .density(3000)
                .viscosity(6000)
                .temperature(1300)
                .addDripstoneDripping(PointedDripstoneBlock.LAVA_TRANSFER_PROBABILITY_PER_RANDOM_TICK, ParticleTypes.DRIPPING_DRIPSTONE_LAVA, Blocks.LAVA_CAULDRON, SoundEvents.POINTED_DRIPSTONE_DRIP_LAVA_INTO_CAULDRON)){

        @Override
            public double motionScale(Entity entity) {
                return entity.level().dimensionType().ultraWarm() ? 0.007D : 0.0023333333333333335D;
            }

            @Override
            public void setItemMovement(ItemEntity entity) {
                Vec3 vec3 = entity.getDeltaMovement();
                entity.setDeltaMovement(vec3.x * (double) 0.95F, vec3.y + (double) (vec3.y < (double) 0.06F ? 5.0E-4F : 0.0F), vec3.z * (double) 0.95F);
            }
        }
    );

    public static void register(IEventBus eventBus){
        FLUID_TYPES.register(eventBus);
    }
}
