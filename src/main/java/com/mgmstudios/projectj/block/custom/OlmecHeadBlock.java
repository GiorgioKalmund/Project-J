package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.particle.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class OlmecHeadBlock extends RedstoneLampBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(0, 0, 1, 16, 15, 15);
    private static final VoxelShape SHAPE_EAST_WEST = Block.box(1, 0, 0, 15, 15, 16);
    public final ParticleOptions effectParticle;
    public final Holder<MobEffect> effect;
    public final int effectTime;
    public OlmecHeadBlock(Properties properties, ParticleOptions effectParticle, Holder<MobEffect> effect, int effectTime) {
        super(properties);
        this.effect = effect;
        this.effectTime = effectTime;
        this.effectParticle = effectParticle;
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH, SOUTH -> SHAPE_NORTH_SOUTH;
            default -> SHAPE_EAST_WEST;
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (state.getValue(LIT) && level instanceof ServerLevel serverLevel && player.getMainHandItem().isEmpty()){
            player.addEffect(new MobEffectInstance(effect, effectTime * 20, 2, true, false,true));
            serverLevel.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1f, 1f);
            this.spawnActivationParticles(serverLevel, pos, false);
            return InteractionResult.SUCCESS_SERVER;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public void onBlockStateChange(LevelReader levelReader, BlockPos pos, BlockState oldState, BlockState newState) {
        if (!(oldState.getBlock() instanceof OlmecHeadBlock) && !newState.getValue(LIT))
            return;

        if (newState.getValue(LIT) && levelReader instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS);
            spawnActivationParticles(serverLevel, pos, true);
        } else if (!newState.getValue(LIT) && levelReader instanceof ServerLevel serverLevel){
            serverLevel.playSound(null, pos, SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS);
        }

        super.onBlockStateChange(levelReader, pos, oldState, newState);
    }

    public void spawnActivationParticles(ServerLevel serverLevel, BlockPos pos, boolean smoke) {
        RandomSource random = serverLevel.getRandom();
        for (int i = 0; i < 25; i++) {
            double x = pos.getX() + random.nextDouble() + 0.1;
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble() + 0.1;

            ParticleOptions particle = i % 3 == 0 && smoke
                    ? ParticleTypes.CAMPFIRE_COSY_SMOKE
                    : effectParticle;

            serverLevel.sendParticles(particle,true,true,  x, y, z, 1, 0, 0, 0, 0.05);
        }
    }

    public static void spawnBeaconBeam(Level level, BlockPos headPosition, BlockPos targetPosition, ParticleOptions particleOptions, int particleCount, boolean clientOnly) {
        Vec3 headVec = headPosition.getCenter();
        Vec3 targetVec = targetPosition.getCenter();

        Vec3 directionVec = targetVec.subtract(headVec);

        var dist = directionVec.length();
        var stepSize = dist / (particleCount - 1);

        directionVec = directionVec.normalize();
        for (int i = 0; i < particleCount ; i ++){
            var position = headVec.add(directionVec.scale(stepSize * i));
            if (clientOnly){
                level.addParticle(ModParticles.TELEPORTATION_PARTICLES.get(), position.x, position.y, position.z, 0, 0,0);
            } else if (level instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(particleOptions,true,true,  position.x, position.y, position.z, 1, 0, 0, 0, 0.05);
            }
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
