package com.mgmstudios.projectj.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TallStatueBlock extends TallBlock{

    public TallStatueBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape LOWER_SHAPE = Block.box(0,0,0, 16, 16, 16);
    private static final VoxelShape UPPER_SHAPE = Block.box(4,0,4, 12, 16, 12);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(HALF)){
            case UPPER -> UPPER_SHAPE;
            case LOWER -> LOWER_SHAPE;
        };
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return switch (state.getValue(HALF)){
            case UPPER -> UPPER_SHAPE;
            case LOWER -> LOWER_SHAPE;
        };
    }
}
