package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.entity.custom.JadeCrystalBlockEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;



public class JadeCrystalBlock extends BaseEntityBlock{
    public JadeCrystalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new JadeCrystalBlockEntity(pos, state);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()){
            if (level.getBlockEntity(pos) instanceof JadeCrystalBlockEntity jadeCrystalBlockEntity){
                jadeCrystalBlockEntity.drops(level, pos);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}