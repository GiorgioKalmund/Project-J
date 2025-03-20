package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.AdobeFurnaceBlockEntity;
import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.mgmstudios.projectj.block.custom.AdobeChimneyBlock.SMOKING;

public class AdobeFurnaceBlock extends AbstractFurnaceBlock  {

    public AdobeFurnaceBlock(Properties p_48687_) {
        super(p_48687_);
    }

    @Override
    protected MapCodec<? extends AbstractFurnaceBlock> codec() {
        return null;
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof AdobeFurnaceBlockEntity) {
            player.openMenu((MenuProvider)blockentity);
            //player.awardStat(Stats.INTERACT_WITH_SMOKER);
        }
    }

    @Override
    public void onBlockStateChange(LevelReader level, BlockPos pos, BlockState oldState, BlockState newState) {
        if (level instanceof ServerLevel serverLevel){
            BlockPos abovePos = pos.above();
            BlockState aboveBlock = level.getBlockState(abovePos);
            BlockState newBlock = level.getBlockState(pos.above());
            if (newBlock.is(ModBlocks.CHIMNEY.get())) {
                serverLevel.setBlock(abovePos, aboveBlock.setValue(SMOKING, newState.getValue(LIT)), 3);
            }
        }
        super.onBlockStateChange(level, pos, oldState, newState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_154640_, BlockState p_154641_, BlockEntityType<T> p_154642_) {
        return createFurnaceTicker(p_154640_, p_154642_, ModBlockEntities.ADOBE_FURNACE_BE.get());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AdobeFurnaceBlockEntity(pos, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()){
            tooltipComponents.add(Component.translatable("tooltip.projectj.adobe_furnace.tooltip.shift_down"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.projectj.adobe_furnace.tooltip"));
        }
    }
}
