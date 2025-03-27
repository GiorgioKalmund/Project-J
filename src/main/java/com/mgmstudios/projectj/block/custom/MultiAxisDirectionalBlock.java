package com.mgmstudios.projectj.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class MultiAxisDirectionalBlock extends DirectionalBlock {

    public static final MapCodec<MultiAxisDirectionalBlock> CODEC = simpleCodec(MultiAxisDirectionalBlock::new);
    public MultiAxisDirectionalBlock(Properties p_55926_) {
        super(p_55926_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        return this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected BlockState rotate(BlockState p_154354_, Rotation p_154355_) {
        return p_154354_.setValue(FACING, p_154355_.rotate(p_154354_.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState p_154351_, Mirror p_154352_) {
        return p_154351_.setValue(FACING, p_154352_.mirror(p_154351_.getValue(FACING)));
    }
}
