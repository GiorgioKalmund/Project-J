package com.mgmstudios.projectj.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;

public class RunnerEntity extends Zombie {
    public RunnerEntity(EntityType<? extends Zombie> entityType, Level level) {
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

    protected boolean isSunSensitive() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.HUSK_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.HUSK_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.HUSK_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.HUSK_STEP;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.FOLLOW_RANGE, 35.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 3.0F)
                .add(Attributes.ARMOR, 2.0F)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
    }

    protected boolean convertsInWater() {
        return true;
    }

    protected void doUnderWaterConversion() {
        if (EventHooks.canLivingConvert(this, EntityType.ZOMBIE, (timer) -> this.conversionTime = timer)) {
            this.convertToZombieType(EntityType.ZOMBIE);
            if (!this.isSilent()) {
                this.level().levelEvent((Player)null, 1041, this.blockPosition(), 0);
            }

        }
    }

    protected ItemStack getSkull() {
        return ItemStack.EMPTY;
    }

}
