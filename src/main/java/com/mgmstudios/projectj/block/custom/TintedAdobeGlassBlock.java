package com.mgmstudios.projectj.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TintedAdobeGlassBlock extends AdobeGlassBlock{
    public static final MapCodec<TintedAdobeGlassBlock> CODEC = simpleCodec(TintedAdobeGlassBlock::new);
    public TintedAdobeGlassBlock(Properties properties) {
        super(properties);
    }

    protected boolean propagatesSkylightDown(BlockState p_154824_) {
        return false;
    }

    protected int getLightBlock(BlockState p_154828_) {
        return 15;
    }

    @Override
    protected MapCodec<? extends TransparentBlock> codec() {
        return CODEC;
    }
}
