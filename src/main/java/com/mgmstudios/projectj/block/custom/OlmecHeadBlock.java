package com.mgmstudios.projectj.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class OlmecHeadBlock extends RedstoneLampBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 15, 15);
    public OlmecHeadBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (state.getValue(LIT) && level instanceof ServerLevel serverLevel){
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10 * 20));
            serverLevel.playSound(null, pos, SoundEvents.WANDERING_TRADER_DRINK_POTION, SoundSource.BLOCKS, 1f, 1f);
            return InteractionResult.SUCCESS_SERVER;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public void onBlockStateChange(LevelReader levelReader, BlockPos pos, BlockState oldState, BlockState newState) {
        if (oldState.is(Blocks.AIR) && !newState.getValue(LIT))
            return;

        if (newState.getValue(LIT) && levelReader instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS);
            spawnActivationParticles(serverLevel, pos);
        } else if (!newState.getValue(LIT) && levelReader instanceof ServerLevel serverLevel){
            serverLevel.playSound(null, pos, SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS);
        }

        super.onBlockStateChange(levelReader, pos, oldState, newState);
    }

    private void spawnActivationParticles(ServerLevel serverLevel, BlockPos pos) {
        RandomSource random = serverLevel.getRandom();
        for (int i = 0; i < 12; i++) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + random.nextDouble();

            ParticleOptions particle = i % 3 == 0
                    ? ParticleTypes.CAMPFIRE_COSY_SMOKE
                    : ParticleTypes.HAPPY_VILLAGER;

            serverLevel.sendParticles(particle, x, y, z, 1, 0, 0, 0, 0.05);
        }
    }


    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
}
