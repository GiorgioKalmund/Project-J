package com.mgmstudios.projectj.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BrazierBlock extends Block {

    public BrazierBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        double d0 = (double)blockPos.getX() + 0.5;
        double d1 = (double)blockPos.getY() + 0.7;
        double d2 = (double)blockPos.getZ() + 0.5;
        level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0);
        level.addParticle(ParticleTypes.LAVA, d0, d1, d2, 0.0, 0.0, 0.0);
    }
}
