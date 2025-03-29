package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.entity.ModEntities;
import com.mgmstudios.projectj.entity.custom.SittableEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerPacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class SittableBlock extends HorizontalDirectionalBlock {

    public SittableBlock(Properties properties) {
        super(properties);
    }

    public static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(0,0,4, 16, 6, 12);
    public static final VoxelShape SHAPE_EAST_WEST = Block.box(4,0,0, 12, 6, 16);

    public static final MapCodec<SittableBlock> CODEC = simpleCodec(SittableBlock::new);

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
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
        if(!level.isClientSide()) {
            System.out.println("Used without item");
            Entity entity = null;
            List<SittableEntity> entities = level.getEntities(ModEntities.SITTABLE_ENTITY.get(), new AABB(pos), chair -> true);
            if(entities.isEmpty()) {
                System.out.println("Spawned new Entity");
                entity = ModEntities.SITTABLE_ENTITY.get().spawn(((ServerLevel) level), pos, EntitySpawnReason.SPAWN_ITEM_USE);
            } else {
                System.out.println("Got existing entity");
                entity = entities.getFirst();
            }
            System.out.println("Entity: " + entity);

            assert entity != null;
            player.startRiding(entity);
            System.out.println("Player started riding: " + entity);
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
