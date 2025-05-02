package com.mgmstudios.projectj.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class RunnerEntity extends Husk {
    public RunnerEntity(EntityType<? extends Husk> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean doHurtTarget(ServerLevel p_376715_, Entity p_32892_) {
        boolean flag = super.doHurtTarget(p_376715_, p_32892_);
        if (flag && this.getMainHandItem().isEmpty() && p_32892_ instanceof LivingEntity) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            ((LivingEntity)p_32892_).addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140 * (int)f), this);
        }

        return flag;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 3.0F)
                .add(Attributes.ARMOR, 2.0F)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

}
