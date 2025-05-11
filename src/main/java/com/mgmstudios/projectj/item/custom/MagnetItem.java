package com.mgmstudios.projectj.item.custom;

import ca.weblite.objc.Client;
import com.mgmstudios.projectj.component.ModDataComponents;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientActivePlayersTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.ServerboundPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.function.ToLongBiFunction;

public class MagnetItem extends Item {

    private final int radius;
    private final float strength;
    public MagnetItem(Properties properties, int radius, float strength) {
        super(properties);
        this.radius = radius;
        this.strength = strength;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

        if (level.isClientSide) return;

        boolean active = stack.getComponents().getOrDefault(ModDataComponents.MAGNET_ACTIVE.get(), false);
        if (!active || !(entity instanceof Player)) return;


        Vec3 playerPos = entity.position();
        AABB box = new AABB(playerPos.x - radius, playerPos.y - radius, playerPos.z - radius,
                playerPos.x + radius, playerPos.y + radius, playerPos.z + radius);

        for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, box)) {
            Vec3 toPlayer = playerPos.subtract(itemEntity.position());
            itemEntity.setDeltaMovement(toPlayer.normalize().scale(strength));
        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.projectj.magnet_item.radius.tooltip", radius));
        tooltipComponents.add(Component.translatable("tooltip.projectj.magnet_item.strength.tooltip", strength));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        ItemStack stack = player.getItemInHand(hand);
        boolean active = stack.getComponents().getOrDefault(ModDataComponents.MAGNET_ACTIVE.get(), false);
        stack.set(ModDataComponents.MAGNET_ACTIVE.get(), !active);
        stack.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, !active);
        player.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1f, !active ? 1f : 0.8f);
        return InteractionResult.SUCCESS_SERVER;
    }
}

