package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.ProjectJ;
import com.mgmstudios.projectj.datagen.ModRecipeProvider;
import com.mgmstudios.projectj.sound.ModSounds;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.LightTexture;
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
import net.minecraft.world.entity.Interaction;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.HashMap;
import java.util.List;

@EventBusSubscriber(modid = ProjectJ.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MagnifyingGlassItem extends SpyglassItem {


    public static HashMap<BlockState, BlockState> MAGNIFYING_CONVERTABLES;

    public static final int CONVERSION_DURATION = 80;
    public final int minLight;
    public final int maxLight;

    public MagnifyingGlassItem(Properties properties, int minLight, int maxLight) {
        super(properties);
        this.minLight = minLight;
        this.maxLight = maxLight;
    }

    public MagnifyingGlassItem(Properties properties, int minLight) {
        this(properties, minLight, 15);
    }

    public MagnifyingGlassItem(Properties properties) {
        this(properties, 0, 15);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return CONVERSION_DURATION;
    }

    public boolean isValidConversion(ServerLevel serverLevel, BlockPos clickedPos, boolean needsToBeDay){
        int lightLevel = serverLevel.getBrightness(LightLayer.SKY, clickedPos.relative(Direction.UP));
        boolean day = !needsToBeDay || serverLevel.isDay();
        if (MAGNIFYING_CONVERTABLES == null){
            setupRecipes();
        }
        boolean contains = MAGNIFYING_CONVERTABLES.containsKey(serverLevel.getBlockState(clickedPos));
        return lightLevel >= minLight && lightLevel <= maxLight && day && contains;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide())
            return;

        if (livingEntity instanceof Player player){
            BlockHitResult povHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
            BlockPos selectedPos = povHitResult.getBlockPos();
            if (level.getBlockState(selectedPos).is(Blocks.AIR))
                return;

            if (level instanceof ServerLevel serverLevel){
                boolean validConversion = isValidConversion(serverLevel, selectedPos, true);
                if (validConversion){
                    float probability = 1F - (float) remainingUseDuration / CONVERSION_DURATION;
                    showBurningParticles(level, selectedPos, probability);
                    if (remainingUseDuration <= 1){
                        BlockState clickedBlockState = serverLevel.getBlockState(selectedPos);
                        serverLevel.setBlockAndUpdate(selectedPos, MAGNIFYING_CONVERTABLES.get(clickedBlockState));
                        level.playSound(null, selectedPos, SoundEvents.GENERIC_BURN, SoundSource.BLOCKS);
                    }
                }
            }
        }

        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    public static void showBurningParticles(Level level, BlockPos pos, float probability){
        RandomSource randomsource = level.getRandom();
        if (randomsource.nextDouble() < probability){
            if (level instanceof ServerLevel serverLevel){
                for (int k1 = 0; k1 < 2; k1++) {
                    serverLevel
                            .sendParticles(
                                    ParticleTypes.FLAME,
                                    (double)pos.getX() + randomsource.nextDouble(),
                                    (double)pos.getY()+ 1,
                                    (double)pos.getZ() + randomsource.nextDouble(),
                                    1,
                                    0.01,
                                    0.05,
                                    0.0,
                                    0.01F
                            );
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.BLOCK_TYPE)){
            setupRecipes();
        }
    }

    private static void setupRecipes(){
        MAGNIFYING_CONVERTABLES = new HashMap<>();
        ModRecipeProvider.buildMagnifyingGlassRecipes();
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
