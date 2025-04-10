package com.mgmstudios.projectj.item.custom;

import com.mgmstudios.projectj.item.ModToolMaterials;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class SacrificialDagger extends SwordItem {

    public SacrificialDagger(float attackDamage, float attackSpeed, Properties properties) {
        super(ModToolMaterials.SACRIFICIAL_DAGGER_MATERIAL, attackDamage, attackSpeed, properties);
    }

    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(level instanceof ServerLevel serverLevel) {
           player.hurtServer(serverLevel, serverLevel.damageSources().cactus(), 1);
           player.getItemInHand(hand).hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
           return InteractionResult.SUCCESS_SERVER;
        }else return InteractionResult.PASS;
    }
}
