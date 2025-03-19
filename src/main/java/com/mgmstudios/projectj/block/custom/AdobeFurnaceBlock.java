package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.entity.AdobeFurnaceBlockEntity;
import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AdobeFurnaceBlock extends BaseEntityBlock {

    public static final MapCodec<AdobeFurnaceBlock> CODEC = simpleCodec(AdobeFurnaceBlock::new);

    public AdobeFurnaceBlock(Properties p_53627_) {
        super(p_53627_);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AdobeFurnaceBlockEntity(blockPos, blockState);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    // Drop contents when destroyed
    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof AdobeFurnaceBlockEntity adobeFurnaceBlockEntity) {
                adobeFurnaceBlockEntity.dropContents();
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    // Open the Menu on right click
    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof AdobeFurnaceBlockEntity adobeFurnaceBlockEntity) {
                ((ServerPlayer) player).openMenu(
                        new SimpleMenuProvider(adobeFurnaceBlockEntity, Component.literal("Adobe Furnace")), pos);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
            BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        
        return createTickerHelper(blockEntityType, ModBlockEntities.ADOBE_FURNACE_BE.get(),
                (level1, blockPos, blockState, blockEntity) -> blockEntity.tick(level, blockPos, blockState));
    }
}
