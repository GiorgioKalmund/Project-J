package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.ProjectJ;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import com.google.common.collect.ImmutableMap.Builder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;

@EventBusSubscriber(modid = ProjectJ.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MagnifyingGlassItem extends SpyglassItem {


    protected static Map<BlockState, BlockState> CONVERTABLES;

    private int conversion;
    private boolean validConversion;
    private UseOnContext lastContext;

    public MagnifyingGlassItem(Properties properties) {
        super(properties);
        this.conversion = 0;
        this.validConversion = false;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }

    private final int CONVERSION_DURATION = 100;

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if (level instanceof ServerLevel serverLevel){
            lastContext = context;
            validConversion = CONVERTABLES.containsKey(serverLevel.getBlockState(lastContext.getClickedPos())) && level.isDay();
        }
        return super.useOn(context);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        if (lastContext == null || !validConversion)
            return;

        showBurningParticles(level, lastContext.getClickedPos());
        conversion++;
        if (level instanceof ServerLevel serverLevel){
            if (conversion >= CONVERSION_DURATION){
                BlockPos clickedPos = lastContext.getClickedPos();
                BlockState clickedBlockState = serverLevel.getBlockState(clickedPos);
                serverLevel.setBlockAndUpdate(clickedPos, CONVERTABLES.get(clickedBlockState));
                level.playSound(null, clickedPos, SoundEvents.GENERIC_BURN, SoundSource.BLOCKS);
                reset();
            }
        }
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        super.onStopUsing(stack, entity, count);
        reset();
    }

    private void reset(){
        this.conversion = 0;
        this.lastContext = null;
    }

    private static void showBurningParticles(Level level, BlockPos pos){
        RandomSource randomsource = level.getRandom();
        for (int k1 = 0; k1 < 2; k1++) {
            level
                    .addParticle(
                            ParticleTypes.FLAME,
                            (double)pos.getX() + randomsource.nextDouble(),
                            (double)pos.getY()+ 1,
                            (double)pos.getZ() + randomsource.nextDouble(),
                            0.0,
                            0.01,
                            0.0
                    );
        }
    }

    @SubscribeEvent
    public static void onRegisterBlocks(RegisterEvent event) {
         CONVERTABLES = new Builder<BlockState, BlockState>()
                 .put(Blocks.SAND.defaultBlockState(), Blocks.GLASS.defaultBlockState())
                 .build();
    }
}
