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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import static com.mgmstudios.projectj.block.custom.TeleportationBlock.LIT;

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
                serverPlayer.displayClientMessage(Component.literal("§a§lDestination set. Please select your origin.§r"), true);
                stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);

                return InteractionResult.SUCCESS_SERVER;
            } else if (blockState.is(ModBlocks.TELEPORTATION_PAD.get())) {
                if (storedPosition.equals(clickedPos)) {
                    serverPlayer.sendSystemMessage(Component.literal("§7§oCannot connect teleportation pad to itself.§o"));
                    reset(stack);
                    return InteractionResult.PASS;
                }
                if (level.getBlockEntity(clickedPos) instanceof TeleportationBlockEntity teleporterEntity) {
                    teleporterEntity.setConnectedPosition(storedPosition);
                    serverLevel.setBlockAndUpdate(clickedPos, serverLevel.getBlockState(clickedPos).setValue(LIT, true));
                    serverPlayer.displayClientMessage(Component.literal("§a§oConnection established!§r"), true);
                    stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false);
                    serverLevel.playSound(null, clickedPos, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS);
                } else {
                    serverPlayer.sendSystemMessage(Component.literal("§c§oNo valid block entity found at the stored position.§r"));
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
            } else if (storedPosition != null) {
                serverPlayer.displayClientMessage(Component.literal("Reset key."), true);
                reset(stack);
                return InteractionResult.FAIL;
            }
        }
        return super.useOn(context);
    }

    private void reset(ItemStack stack){
        storedPosition = null;
        stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false);
    }
}
