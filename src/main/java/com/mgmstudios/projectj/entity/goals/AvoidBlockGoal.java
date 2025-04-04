package com.mgmstudios.projectj.entity.goals;

import com.mgmstudios.projectj.block.ModBlocks;
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
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

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
