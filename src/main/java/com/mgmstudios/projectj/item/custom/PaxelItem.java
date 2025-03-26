package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.item.ModToolMaterials;
import com.mgmstudios.projectj.util.ModTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class PaxelItem extends DiggerItem {

    public PaxelItem(float attackDamage, float attackSpeed, Properties properties) {
        super(ModToolMaterials.PAXEL_MATERIAL, ModTags.Blocks.MINEABLE_WITH_PAXEL, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.neoforged.neoforge.common.ItemAbility itemAbility) {
        return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(itemAbility) || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(itemAbility);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(context, ItemAbilities.SHOVEL_FLATTEN, false);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState));
        boolean axeActive = false;
        if (pair == null){
            toolModifiedState = level.getBlockState(blockpos).getToolModifiedState(context, ItemAbilities.AXE_STRIP, false);
            pair = toolModifiedState == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState));
            axeActive = true;
        }
        if (pair == null) {
            return InteractionResult.PASS;
        } else {
            Predicate<UseOnContext> predicate = pair.getFirst();
            Consumer<UseOnContext> consumer = pair.getSecond();
            if (predicate.test(context)) {
                Player player = context.getPlayer();
                level.playSound(player, blockpos, axeActive ? SoundEvents.AXE_STRIP : SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide) {
                    consumer.accept(context);
                    if (player != null) {
                        context.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
                    }
                }
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public static Consumer<UseOnContext> changeIntoState(BlockState state) {
        return p_316061_ -> {
            p_316061_.getLevel().setBlock(p_316061_.getClickedPos(), state, 11);
            p_316061_.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, p_316061_.getClickedPos(), GameEvent.Context.of(p_316061_.getPlayer(), state));
        };
    }
}
