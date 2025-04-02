package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.entity.custom.TeleportationBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class TeleportationBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
    public static final BooleanProperty UNLOCKED = BooleanProperty.create("teleportation_unlocked");

    public TeleportationBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false).setValue(UNLOCKED, true));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection()).setValue(LIT, false).setValue(UNLOCKED, true);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(LIT);
        builder.add(UNLOCKED);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (level instanceof ServerLevel serverLevel && entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.isCrouching()) {
                TeleportationBlockEntity blockEntity = (TeleportationBlockEntity) level.getBlockEntity(pos);
                if (blockEntity != null && blockEntity.getConnectedPosition() != null) {
                    if (state.getValue(UNLOCKED)){
                        BlockPos connectedPos = blockEntity.getConnectedPosition();
                        serverPlayer.sendSystemMessage(Component.literal("Teleporting to " + blockEntity.getConnectedPosition()));
                        level.setBlockAndUpdate(pos, state.setValue(UNLOCKED, false));
                        serverLevel.scheduleTick(pos, this, 60);
                        level.setBlockAndUpdate(connectedPos, state.setValue(UNLOCKED, false));
                        serverLevel.scheduleTick(connectedPos, level.getBlockState(connectedPos).getBlock(), 60);
                        teleportPlayer(serverPlayer, connectedPos.above());
                        level.playSound(null, connectedPos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS);
                    } else {
                        serverPlayer.sendSystemMessage(Component.literal("Teleportation Block is on cooldown!"));
                    }
                } else {
                    serverPlayer.sendSystemMessage(Component.literal("Teleportation Block is not bound"));
                }
            }
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlockAndUpdate(pos, state.setValue(UNLOCKED, true));
        //level.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS);
        super.tick(state, level, pos, random);
    }

    private void teleportPlayer(ServerPlayer player, BlockPos targetPosition) {
        player.teleportTo(targetPosition.getX(), targetPosition.getY(), targetPosition.getZ());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TeleportationBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null; // No ticking required for this block entity
    }
}
