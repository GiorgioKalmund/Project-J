package com.mgmstudios.projectj.entity.goals;

import com.mgmstudios.projectj.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

/*
public class AvoidBlockGoal extends RandomStrollGoal {
    public static final float PROBABILITY = 0.001F;
    protected final float probability;

    public AvoidBlockGoal(PathfinderMob p_25987_, double p_25988_) {
        this(p_25987_, p_25988_, 0.001F);
    }

    public AvoidBlockGoal(PathfinderMob mob, double speedModifier, float probability) {
        super(mob, speedModifier);
        this.probability = probability;
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        Level level = this.mob.level();
        if (level.getBlockState(this.mob.blockPosition()).is(ModBlocks.LITTLE_MAN_STATUE_BLOCK.get())) {
            Vec3 vec3 = LandRandomPos.getPos(this.mob, 30, 7);
            return vec3 == null ? super.getPosition() : vec3;
        } else {
            return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
        }
    }
}

 */




//package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public abstract class AvoidBlockGoal extends Goal {
    protected final PathfinderMob mob;
    protected Path path;
    public final double speedModifier;
    protected int nextStartTick;
    protected int tryTicks;
    private int maxStayTicks;
    protected BlockPos blockPos;
    private boolean reachedTarget;
    private final int searchRange;
    private final int verticalSearchRange;
    protected int verticalSearchStart;
    protected BlockPos mostDistantBlock;


    public AvoidBlockGoal(PathfinderMob mob, double speedModifier, int searchRange, int verticalSearchRange) {
        this.blockPos = BlockPos.ZERO;
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.searchRange = searchRange;
        this.verticalSearchStart = 0;
        this.verticalSearchRange = verticalSearchRange;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    public boolean canUse() {
        if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.mob);
            return this.findNearestBlock();
        }
    }

    protected boolean findNearestBlock() {
        Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, blockPos.getCenter());
        if (vec3 == null) {
            return false;
        } else if (this.blockPos.getCenter().distanceToSqr(vec3.x, vec3.y, vec3.z) < this.blockPos.getCenter().distanceToSqr(this.mob.position())) {
            return false;
        } else {
            this.path = this.mob.getNavigation().createPath(vec3.x, vec3.y, vec3.z, 0);
            return this.path != null;
        }


    }

    protected abstract boolean isValidTarget(LevelReader var1, BlockPos var2);

    public void tick() {
        BlockPos blockpos = this.getMoveToTarget();
        if (!blockpos.closerToCenterThan(this.mob.position(), this.acceptedDistance())) {
            this.reachedTarget = false;
            ++this.tryTicks;
            if (this.shouldRecalculatePath()) {
                this.mob.getNavigation().moveTo(path, this.speedModifier);
            }
        } else {
            this.reachedTarget = true;
            --this.tryTicks;
        }
    }

    public boolean shouldRecalculatePath() {
        return this.tryTicks % 40 == 0;
    }

    public double acceptedDistance() {
        return (double)1.0F;
    }

    protected BlockPos getMoveToTarget() {
        return this.blockPos.above();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.speedModifier);
    }

    protected void moveMobAwayFromBlock() {
        this.mob.getNavigation().moveTo((double)this.blockPos.getX() + (double)0.5F, (double)(this.blockPos.getY() + 1), (double)this.blockPos.getZ() + (double)0.5F, this.speedModifier);
    }

    protected int nextStartTick(PathfinderMob creature) {
        return reducedTickDelay(200 + creature.getRandom().nextInt(200));
    }
}
