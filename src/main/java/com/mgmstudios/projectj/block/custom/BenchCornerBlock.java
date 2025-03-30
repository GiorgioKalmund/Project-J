package com.mgmstudios.projectj.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BenchCornerBlock extends SittableBlock{

    protected static final VoxelShape SHAPE_NORTH = Block.box(0,3,0, 12, 6, 12);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(4,3,4, 16, 6, 16);
    protected static final VoxelShape SHAPE_WEST = Block.box(0,3,4, 12, 6, 16);
    protected static final VoxelShape SHAPE_EAST = Block.box(4,3,0, 16, 6, 12);
    public BenchCornerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH_SOUTH;
        };
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getCounterClockWise());
    }
}
