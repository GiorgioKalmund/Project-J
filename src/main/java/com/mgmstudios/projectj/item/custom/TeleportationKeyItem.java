package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.custom.TeleportationBlockEntity;
import net.minecraft.client.gui.font.providers.BitmapProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static com.mgmstudios.projectj.block.custom.TeleportationBlock.LIT;
import static net.minecraft.world.level.block.Block.getDrops;
import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

public class TeleportationKeyItem extends Item {

    protected BlockPos storedPosition;

    public TeleportationKeyItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (level instanceof ServerLevel serverLevel && player instanceof ServerPlayer serverPlayer) {
            BlockPos clickedPos = context.getClickedPos();
            BlockState blockState = serverLevel.getBlockState(clickedPos);
            ItemStack stack = player.getItemInHand(context.getHand());
            if (blockState.is(ModBlocks.TELEPORTATION_PAD.get()) && storedPosition == null) {
                storedPosition = clickedPos;
                //serverPlayer.sendSystemMessage(Component.literal("Teleportation block selected: " + clickedPos));
                serverPlayer.sendSystemMessage(Component.literal("§a§lDestination set. Please select your origin.§r"));
                stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);

                return InteractionResult.SUCCESS_SERVER;
            } else if (blockState.is(ModBlocks.TELEPORTATION_PAD.get())) {
                if (storedPosition.equals(clickedPos)) {
                    serverPlayer.sendSystemMessage(Component.literal("Cannot connect teleportation block to itself"));
                    reset(stack);
                    return InteractionResult.PASS;
                }
                if (level.getBlockEntity(clickedPos) instanceof TeleportationBlockEntity teleporterEntity) {
                    teleporterEntity.setConnectedPosition(storedPosition);
                    serverLevel.setBlockAndUpdate(clickedPos, serverLevel.getBlockState(clickedPos).setValue(LIT, true));
                    //serverPlayer.sendSystemMessage(Component.literal("Teleportation block connected to: " + clickedPos));
                    serverPlayer.sendSystemMessage(Component.literal("§a§oConnection established!§r"));
                    stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false);
                    serverLevel.playSound(null, clickedPos, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS);
                } else {
                    serverPlayer.sendSystemMessage(Component.literal("No valid teleporter block entity found at the stored position"));
                }
                /* Two-way
                if (level.getBlockEntity(clickedPos) instanceof TeleportationBlockEntity teleporterEntity) {
                    teleporterEntity.setConnectedPosition(storedPosition);
                    serverPlayer.sendSystemMessage(Component.literal("Teleportation block connected to: " + storedPosition));
                } else {
                    serverPlayer.sendSystemMessage(Component.literal("No valid teleporter found at the stored position"));
                } */

                storedPosition = null;
                return InteractionResult.SUCCESS_SERVER;
            } else {
                serverPlayer.sendSystemMessage(Component.literal("Reset key."));
                reset(stack);
                return InteractionResult.FAIL;
            }
        }
        return super.useOn(context);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return super.use(level, player, hand);
    }

    private void reset(ItemStack stack){
        storedPosition = null;
        stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false);
    }
}
