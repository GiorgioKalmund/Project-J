package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.entity.custom.TeleportationBlockEntity;
import net.minecraft.core.BlockPos;
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
            if (blockState.is(ModBlocks.TELEPORTATION_PAD.get()) && storedPosition == null) {
                storedPosition = clickedPos;
                //serverPlayer.sendSystemMessage(Component.literal("Teleportation block selected: " + clickedPos));
                return InteractionResult.SUCCESS_SERVER;
            } else if (blockState.is(ModBlocks.TELEPORTATION_PAD.get())) {
                if (storedPosition.equals(clickedPos)) {
                    serverPlayer.sendSystemMessage(Component.literal("Cannot connect teleportation block to itself"));
                    return InteractionResult.PASS;
                }
                if (level.getBlockEntity(storedPosition) instanceof TeleportationBlockEntity teleporterEntity) {
                    teleporterEntity.setConnectedPosition(clickedPos);
                    serverLevel.setBlockAndUpdate(storedPosition, serverLevel.getBlockState(storedPosition).setValue(LIT, true));
                    //serverPlayer.sendSystemMessage(Component.literal("Teleportation block connected to: " + clickedPos));
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
                serverPlayer.sendSystemMessage(Component.literal("Did not click on a teleportation block"));
                reset();
                return InteractionResult.FAIL;
            }
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        return super.use(level, player, hand);
    }

    private void reset(){
        storedPosition = null;
    }
}
