package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.datagen.ModRecipeProvider;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.List;

@EventBusSubscriber(modid = ProjectJ.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MagnifyingGlassItem extends SpyglassItem {


    public static HashMap<BlockState, BlockState> MAGNIFYING_CONVERTABLES;

    protected int conversion;
    protected  boolean validConversion;
    protected  UseOnContext lastContext;

    public MagnifyingGlassItem(Properties properties) {
        super(properties);
        this.conversion = 0;
        this.validConversion = false;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }

    protected final int CONVERSION_DURATION = 100;

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level instanceof ServerLevel serverLevel){
            lastContext = context;
            this.validConversion = isValidConversion(context.getPlayer(), serverLevel, context.getClickedPos(), context.getClickedFace(), 5, true);
        }
        return super.useOn(context);
    }

    protected int getConversionDuration(){
        return CONVERSION_DURATION;
    }

    public boolean isValidConversion(Player player, ServerLevel serverLevel, BlockPos clickedPos, Direction clickedFace, int minimumLightLevel, boolean needsToBeDay){
        int skyLightLevel = serverLevel.getBrightness(LightLayer.SKY, clickedPos.relative(clickedFace));
        boolean day = !needsToBeDay || serverLevel.isDay();
        boolean contains = MAGNIFYING_CONVERTABLES.containsKey(serverLevel.getBlockState(clickedPos));
        if (player != null){
            player.displayClientMessage(Component.literal("L: " +skyLightLevel + " / D:" + day + " / V:" + contains + ": " + serverLevel.getBlockState(clickedPos)), true);
        }
        return skyLightLevel > minimumLightLevel && day && contains && serverLevel.isDay();
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        if (lastContext == null || !validConversion)
            return;

        if (livingEntity instanceof Player player){
            BlockHitResult povHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
            BlockPos selectedPos = povHitResult.getBlockPos();
            if (!lastContext.getClickedPos().equals(selectedPos)){
                reset();
                return;
            }

            showBurningParticles(level, selectedPos);
            conversion++;
            if (level instanceof ServerLevel serverLevel){
                if (conversion >= getConversionDuration()){
                    BlockState clickedBlockState = serverLevel.getBlockState(selectedPos);
                    serverLevel.setBlockAndUpdate(selectedPos, MAGNIFYING_CONVERTABLES.get(clickedBlockState));
                    level.playSound(null, selectedPos, SoundEvents.GENERIC_BURN, SoundSource.BLOCKS);
                    reset();
                }
            }
        }
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        super.onStopUsing(stack, entity, count);
        reset();
    }

    void reset(){
        this.conversion = 0;
        this.lastContext = null;
    }

    public static void showBurningParticles(Level level, BlockPos pos){
        RandomSource randomsource = level.getRandom();
        if (level.isClientSide())
            return;
        else if (level instanceof ServerLevel serverLevel){
            for (int k1 = 0; k1 < 2; k1++) {
               serverLevel
                        .sendParticles(
                                ParticleTypes.FLAME,
                                (double)pos.getX() + randomsource.nextDouble(),
                                (double)pos.getY()+ 1,
                                (double)pos.getZ() + randomsource.nextDouble(),
                                1,
                                0.01,
                                0.0,
                                0.0,
                                0.01F
                        );
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.BLOCK_TYPE)){
            MAGNIFYING_CONVERTABLES = new HashMap<>();
            ModRecipeProvider.buildMagnifyingGlassRecipes();
        }
    }

    public static class MagnifyingRecipeBuilder {
        public static void magnify(Block blockToSmelt, Block resultBlock){
            MAGNIFYING_CONVERTABLES.put(blockToSmelt.defaultBlockState(), resultBlock.defaultBlockState());
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.projectj.magnifying_glass.tooltip.shift_down"));
        } else  {
            tooltipComponents.add(Component.translatable("tooltip.projectj.magnifying_glass.tooltip"));
        }

    }
}
