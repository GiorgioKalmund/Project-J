package com.mgmstudios.projectj.block.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.custom.AdobeFurnaceBlockEntity;
import com.mgmstudios.projectj.block.entity.ModBlockEntities;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.mgmstudios.projectj.block.custom.AdobeChimneyBlock.SMOKING;

public class AdobeFurnaceBlock extends AbstractFurnaceBlock  {
    public static BooleanProperty TIER1 = BooleanProperty.create("tier1");
    public AdobeFurnaceBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(TIER1, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TIER1);
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
            toggleChimney(serverLevel, pos, newState.getValue(LIT));
        }
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (level instanceof ServerLevel serverLevel){
            checkIfTier1(serverLevel, pos);
        }
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (level instanceof ServerLevel serverLevel){
            BlockPos abovePos = pos.below();
            BlockState abovePosState = level.getBlockState(abovePos);
            if (abovePosState.is(ModBlocks.CHIMNEY.get())){
                serverLevel.setBlockAndUpdate(abovePos, abovePosState.setValue(SMOKING, false));
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    private void checkIfTier1(Level level, BlockPos pos){
        BlockPos abovePos = pos.above();
        BlockState aboveBlock = level.getBlockState(abovePos);
        boolean chimneyAbove = aboveBlock.is(ModBlocks.CHIMNEY.get());
        BlockState furnaceState = level.getBlockState(pos);
        level.setBlockAndUpdate(pos, furnaceState.setValue(TIER1, chimneyAbove));
    }

    private void toggleChimney(ServerLevel level, BlockPos pos, boolean activate){
        BlockPos abovePos = pos.above();
        BlockState aboveBlock = level.getBlockState(abovePos);
        if (aboveBlock.is(ModBlocks.CHIMNEY.get())){
            level.setBlockAndUpdate(abovePos, aboveBlock.setValue(SMOKING, activate));
        }
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
