package com.mgmstudios.projectj.entity.goals;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NearestAttackableTargetGoalWithExceptions<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    private Set<T> avoidEntities = new HashSet<>();

    public NearestAttackableTargetGoalWithExceptions(Mob mob, Class targetType, boolean mustSee) {
        super(mob, targetType, mustSee);
    }

    public NearestAttackableTargetGoalWithExceptions(Mob mob, Class targetType, boolean mustSee, TargetingConditions.Selector selector) {
        super(mob, targetType, mustSee, selector);
    }

    public NearestAttackableTargetGoalWithExceptions(Mob mob, Class targetType, boolean mustSee, boolean mustReach) {
        super(mob, targetType, mustSee, mustReach);
    }

    public NearestAttackableTargetGoalWithExceptions(Mob mob, Class targetType, int interval, boolean mustSee, boolean mustReach, @Nullable TargetingConditions.Selector selector) {
        super(mob, targetType, interval, mustSee, mustReach, selector);
    }

    private TargetingConditions getTargetConditions() {
        return this.targetConditions.range(this.getFollowDistance());
    }

    @Override
    protected void findTarget() {
        ServerLevel serverlevel = getServerLevel(this.mob);
//        if (this.targetType != Player .class && this.targetType != ServerPlayer .class) {
//            this.target = serverlevel.getNearestEntity(this.mob.level().getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (p_148152_) -> {
//                return true;
//            }), this.getTargetConditions(), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
//        } else {
            List<T> allEntities = serverlevel.getEntitiesOfClass(targetType, new AABB(mob.blockPosition()).inflate(this.getFollowDistance()));

            double shortestDistance = Double.MAX_VALUE;

            for(T entity : allEntities){
                if(!avoidEntities.contains(entity) && mob.distanceTo(entity) < shortestDistance){
                    this.target = entity;
                }
            }


            this.target = serverlevel.getNearestPlayer(this.getTargetConditions(), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
    }

    public void avoidEntity(T t){
        avoidEntities.add(t);
        update();
    }

    public void acceptEntity(T t){
        avoidEntities.remove(t);
        update();
    }

    public void resetAvoidEntities(){
        avoidEntities = new HashSet<>();
    }

    public void update(){
        stop();
        start();
    }



}
