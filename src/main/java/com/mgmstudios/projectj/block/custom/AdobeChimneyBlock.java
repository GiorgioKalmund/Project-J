package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.AdobeFurnaceBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AdobeChimneyBlock extends Block {

    public static final MapCodec<AdobeChimneyBlock> CODEC = simpleCodec(AdobeChimneyBlock::new);

    private static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 14, 12);

    public static BooleanProperty SMOKING = BooleanProperty.create("chimney_smoking");

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public AdobeChimneyBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SMOKING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SMOKING);
    }

    @Override
    protected void onPlace(BlockState state, Level levelReader, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(Blocks.AIR))
            return;

        BlockPos belowPos = pos.below();
        BlockState belowBlockState = levelReader.getBlockState(belowPos);

        if (levelReader instanceof ServerLevel level && belowBlockState.is(ModBlocks.ADOBE_FURNACE.get())){
            level.playSound(null, belowPos, SoundEvents.DECORATED_POT_PLACE, SoundSource.BLOCKS, 1f, 1f);
        }

        super.onPlace(state, levelReader , pos, oldState, movedByPiston);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.getBlockState(pos).getValue(SMOKING)){
            makeParticles(level, pos, false, true);
        }
        super.animateTick(state, level, pos, random);
    }

    public static void makeParticles(Level level, BlockPos pos, boolean isSignalFire, boolean spawnExtraSmoke) {
        RandomSource randomsource = level.getRandom();
        SimpleParticleType simpleparticletype = isSignalFire ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        level.addAlwaysVisibleParticle(
                simpleparticletype,
                true,
                (double)pos.getX() + 0.5 + randomsource.nextDouble() / 3.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                (double)pos.getY() + randomsource.nextDouble() + randomsource.nextDouble(),
                (double)pos.getZ() + 0.5 + randomsource.nextDouble() / 3.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                0.0,
                0.07,
                0.0
        );
        if (spawnExtraSmoke) {
            level.addParticle(
                    ParticleTypes.SMOKE,
                    (double)pos.getX() + 0.5 + randomsource.nextDouble() / 4.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                    (double)pos.getY() + 0.4,
                    (double)pos.getZ() + 0.5 + randomsource.nextDouble() / 4.0 * (double)(randomsource.nextBoolean() ? 1 : -1),
                    0.0,
                    0.005,
                    0.0
            );
        }
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }
}
