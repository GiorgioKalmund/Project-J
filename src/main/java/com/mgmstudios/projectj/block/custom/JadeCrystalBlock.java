package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.entity.custom.JadeCrystalBlockEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;


public class JadeCrystalBlock extends BaseEntityBlock{
    public JadeCrystalBlock(Properties properties) {
        super(properties);
    }

    private static final MapCodec<BaseEntityBlock> CODEC = simpleCodec(JadeCrystalBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new JadeCrystalBlockEntity(pos, state);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (level.getBlockEntity(pos) instanceof JadeCrystalBlockEntity jadeCrystalBlockEntity){
            if (willHarvest || player.isCreative()){
                jadeCrystalBlockEntity.drops(level, pos);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}