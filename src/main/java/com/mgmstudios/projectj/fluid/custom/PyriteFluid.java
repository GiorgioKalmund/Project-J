package com.mgmstudios.projectj.fluid.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.fluid.ModFluidTypes;
import com.mgmstudios.projectj.fluid.ModFluids;
import com.mgmstudios.projectj.item.ModItems;
import com.mgmstudios.projectj.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.Optional;

public class PyriteFluid extends FlowingFluid{

    @Override
    public Fluid getSource() {
        return ModFluids.PYRITE.get();
    }


    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_PYRITE.get();
    }

    @Override
    public Item getBucket() {
        return ModItems.LIQUID_PYRITE_BUCKET.get();
    }

    @Override
    public FluidType getFluidType() {
        return ModFluidTypes.PYRITE_TYPE.get();
    }

    @Nullable
    @Override
    public ParticleOptions getDripParticle() {
        return ParticleTypes.DRIPPING_LAVA;
    }

    @Override
    public BlockState createLegacyBlock(FluidState state) {
        return ModBlocks.LIQUID_PYRITE.get().defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(state)));
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == ModFluids.PYRITE.get() || fluid == ModFluids.FLOWING_PYRITE.get();
    }


    @Override
    public boolean isSource(FluidState state) {
        return false;
    }

    @Override
    protected boolean canConvertToSource(ServerLevel serverLevel) {
        return serverLevel.getGameRules().getBoolean(GameRules.RULE_LAVA_SOURCE_CONVERSION);
    }


    @Override
    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
        return true;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockEntity blockentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockentity);
    }

    @Override
    public int getSlopeFindDistance(LevelReader level) {
        return 4;
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return fluidState.getValue(LEVEL);
    }

    @Override
    public int getDropOff(LevelReader level) {
        return 1;
    }

    @Override
    public int getTickDelay(LevelReader level) {
        return 30;
    }

    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockReader, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.is(ModTags.Fluids.LIQUID_PYRITE);
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    public static class Flowing extends PyriteFluid{
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return fluidState.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends PyriteFluid{
        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
