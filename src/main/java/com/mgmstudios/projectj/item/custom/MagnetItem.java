package com.mgmstudios.projectj.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MagnetItem extends Item {

    private int radius;
    private float strength;
    private boolean active;
    public MagnetItem(Properties properties, int radius, float strength) {
        super(properties);
        this.radius = radius;
        this.strength = strength;
        active = false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        Vec3 pos = entity.position();
        BlockPos playerPos = BlockPos.containing(pos);
        if (entity instanceof Player player){
            // Performance heavy a bit, maybe a better solution
            boolean itemGlint = player.getInventory().getItem(slotId).getOrDefault(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false);
            if (active != itemGlint){
                active = itemGlint;
            }
            if (active){
                List<ItemEntity> allEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(playerPos).inflate(radius));
                Vec3 targetPos = new Vec3(playerPos.getX(), playerPos.getY(), playerPos.getZ());
                for (ItemEntity itemEntity : allEntities){
                    Vec3 direction = targetPos.subtract(itemEntity.position());
                    itemEntity.setDeltaMovement(direction.normalize().scale(strength));
                }
            }
        }

        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        if (level instanceof ServerLevel){
            active = !active;
            player.getItemInHand(hand).set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, active);
            player.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1f, active ? 1f : 0.8f);
            return InteractionResult.SUCCESS_SERVER;
        }
        return InteractionResult.PASS;
    }
}

