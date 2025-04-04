package com.mgmstudios.projectj.entity.custom;

import com.mgmstudios.projectj.block.ModBlocks;
import com.mgmstudios.projectj.block.custom.EmptyLittleManStatueBlock;
import com.mgmstudios.projectj.entity.VoodooEntity;
import com.mgmstudios.projectj.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;

public class LittleManEntity extends AbstractGolem implements VoodooEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public LittleManEntity(EntityType<? extends LittleManEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TemptGoal(this, 1.1, stack -> stack.is(ModItems.JADE.get()), false));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new GolemRandomStrollInVillageGoal(this, 0.6));
        this.goalSelector.addGoal(6, new MoveToBlockGoal(this, 1.15, 30) {
            @Override
            protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
                return levelReader.getBlockState(blockPos).is(ModBlocks.EMPTY_LITTLE_MAN_STATUE_BLOCK.get());
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.TEMPT_RANGE, 20.0)
                .add(Attributes.MAX_HEALTH, 100.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6)
                .add(Attributes.ATTACK_DAMAGE, 15.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    private void setupAnimationStates(){
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 40;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()){
            this.setupAnimationStates();
        }
    }

    @Override
    public Item getVoodoo() {
        return ModItems.LITTLE_MAN_VOODOO.get();
    }
}
