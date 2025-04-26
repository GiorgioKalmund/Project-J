package com.mgmstudios.projectj.item.custom;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LittleManVoodoo extends Item {

    public LittleManVoodoo(Properties properties) {
        super(properties
                .stacksTo(1));
    }

    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(level.isClientSide()) return;

        if(entity instanceof Player player){

            if(!(player.getItemInHand(InteractionHand.MAIN_HAND).is(this) || player.getItemInHand(InteractionHand.OFF_HAND).is(this))){
                return;
            }

            List<Zombie> allEntities = level.getEntitiesOfClass(Zombie.class, new AABB(player.blockPosition()).inflate(35));
            for (Zombie zombie : allEntities) {

                NearestAttackableTargetGoal<Player> goal = new NearestAttackableTargetGoal<>(zombie, Player.class, true);

                if(player.getItemInHand(InteractionHand.MAIN_HAND).is(this) || player.getItemInHand(InteractionHand.OFF_HAND).is(this)){
                    goal.stop();
                }
            }
        }
    }
}
