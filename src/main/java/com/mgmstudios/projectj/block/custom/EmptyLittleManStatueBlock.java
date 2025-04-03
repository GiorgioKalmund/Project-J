package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.entity.custom.LittleManEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static com.mgmstudios.projectj.block.custom.LittleManStatueBlock.SHAPE_BASE;

public class EmptyLittleManStatueBlock extends HorizontalDirectionalBlock {

    protected static final MapCodec<EmptyLittleManStatueBlock> CODEC = simpleCodec(EmptyLittleManStatueBlock::new);
    public EmptyLittleManStatueBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BASE ;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LittleManEntity littleMan){
            littleMan.remove(Entity.RemovalReason.DISCARDED);
            level.playSound(null, littleMan.blockPosition(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS);
            level.playSound(null, pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS);
            level.setBlockAndUpdate(pos, ModBlocks.LITTLE_MAN_STATUE_BLOCK.get().defaultBlockState().setValue(FACING, Direction.fromYRot(littleMan.getYRot())));
        }
        super.stepOn(level, pos, state, entity);
    }

}
