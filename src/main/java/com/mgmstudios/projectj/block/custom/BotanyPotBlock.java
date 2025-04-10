package com.mgmstudios.projectj.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BotanyPotBlock extends Block {

    public static final VoxelShape SHAPE_BOTTOM = Block.box(2, 0, 2, 14, 10, 14);
    public static final VoxelShape SHAPE_TOP = Block.box(0, 10, 0, 16, 12, 16);
    public static final VoxelShape SHAPE = Shapes.or(SHAPE_BOTTOM, SHAPE_TOP);

    public BotanyPotBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.or(SHAPE);
    }
}
