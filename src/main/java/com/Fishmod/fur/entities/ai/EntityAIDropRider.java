package com.Fishmod.fur.entities.ai;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class EntityAIDropRider extends Goal {

    protected PathfinderMob attacker;
    /** The PathEntity of our entity. */
    Path path;
	
	public EntityAIDropRider(PathfinderMob creature) {
		super();
		this.attacker = creature;
	}
	
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
    	LivingEntity LivingEntity = this.attacker.getTarget();
    	
    	if(LivingEntity == null)
    		return false;
    	else if(!attacker.getPassengers().isEmpty()) {
    		this.path = this.attacker.getNavigation().createPath(LivingEntity, 0);
    		
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start()
    {
        this.attacker.getNavigation().moveTo(this.path, 1.0D);
    }
    
    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop()
    {
        LivingEntity LivingEntity = this.attacker.getTarget();

        if (LivingEntity instanceof Player && (((Player)LivingEntity).isSpectator() || ((Player)LivingEntity).isCreative()))
        {
            this.attacker.setTarget((LivingEntity)null);
        }

        this.attacker.getNavigation().stop();
    }
    
    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick()
    {
    	LivingEntity LivingEntity = this.attacker.getTarget();
    	
    	if(LivingEntity != null && !attacker.getPassengers().isEmpty()) {
    		if(this.isOnTop(attacker, LivingEntity)) {
	    		((Monster) attacker.getPassengers().get(0)).setTarget(LivingEntity);
	    		((LivingEntity)attacker.getPassengers().get(0)).addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 3 * 20, 1));
	    		attacker.ejectPassengers();
    		}
    		else {
    			this.path = this.attacker.getNavigation().createPath(LivingEntity.getX(), LivingEntity.getY() + 16.0D, LivingEntity.getZ(), 0);
    			attacker.getNavigation().moveTo(this.path, 1.0D);
    		}
    	}
    }
    
    private boolean isOnTop(PathfinderMob flyerIn, LivingEntity targetIn) {
    	return targetIn.distanceToSqr(flyerIn.getX(), targetIn.getY(), flyerIn.getZ()) < 2.0D;
    }

}
