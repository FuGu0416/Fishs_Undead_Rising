package com.Fishmod.fur.entities.ai;

import java.util.EnumSet;

import com.Fishmod.fur.entities.ICharging;
import com.Fishmod.fur.entities.floating.FloatingMobEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class EntityChargeAttackGoal extends Goal {
	protected PathfinderMob mob;
	protected LivingEntity target;
	
    public EntityChargeAttackGoal(PathfinderMob creature) {
    	this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    	this.mob = creature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
    	LivingEntity target = this.mob.getTarget();
    	if (target != null && !this.mob.getMoveControl().hasWanted() && target.isAlive()) {
            return this.mob.distanceToSqr(target) > 4.0D && this.mob.distanceToSqr(target) < 64.0D;
        } else {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
    	boolean flag = false;
    	
    	if ((this.mob instanceof ICharging chargeentity && chargeentity.isCharging()) || !(this.mob instanceof ICharging)) {
    		flag = true;
    	} 
    			
    	return this.mob.getMoveControl().hasWanted() && flag && target != null && target.isAlive();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
    	this.target = this.mob.getTarget();
        
        if (this.mob instanceof ICharging chargeentity) {
        	chargeentity.setIsCharging(true);
        	this.mob.level().broadcastEntityEvent(this.mob, (byte) 6);
        }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        if (this.mob instanceof ICharging chargeentity) {
        	chargeentity.setIsCharging(false);
        }    	
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
    	if (this.target == null) return;

    	Vec3 aim = this.target.position().add(0.0D, target.getBbHeight() * 0.5D, 0.0D);
    	this.mob.getMoveControl().setWantedPosition(aim.x(), aim.y(), aim.z(), 1.0D);
    	
        if (this.mob.distanceTo(this.target) < this.target.getBbWidth()) {
        	this.mob.doHurtTarget(this.target);
        	this.mob.setDeltaMovement(this.mob.getDeltaMovement().scale(0.2D));
        } else if (this.mob.distanceTo(this.target) < 9.0D && this.mob instanceof FloatingMobEntity floater) {
        	floater.level().broadcastEntityEvent(floater, (byte)4);
        }
    }
}