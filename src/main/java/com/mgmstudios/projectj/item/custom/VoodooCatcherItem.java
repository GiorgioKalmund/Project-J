package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.entity.VoodooEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class VoodooCatcherItem extends Item {

    public VoodooCatcherItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget instanceof VoodooEntity voodooEntity){
            interactionTarget.remove(Entity.RemovalReason.DISCARDED);
            Level level = player.level();
            ItemStack droppedVoodoo = new ItemStack(voodooEntity.getVoodoo());
            Vec3 pos = interactionTarget.position();
            ItemEntity dropppedEntity = new ItemEntity(
                    level,
                    pos.x(),
                    pos.y() + 0.5,
                    pos.z(),
                    droppedVoodoo
            );
            level.levelEvent(2009, interactionTarget.blockPosition(), 0);
            level.addFreshEntity(dropppedEntity);
            player.playNotifySound(SoundEvents.IRON_GOLEM_STEP, SoundSource.BLOCKS, 1f, 1f);
            return InteractionResult.SUCCESS_SERVER;
        }
        return super.interactLivingEntity(stack, player, interactionTarget, usedHand);
    }
}
