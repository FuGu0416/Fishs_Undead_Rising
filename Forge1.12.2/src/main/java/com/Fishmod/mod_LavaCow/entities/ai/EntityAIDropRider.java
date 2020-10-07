package com.Fishmod.mod_LavaCow.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.PotionEffect;

public class EntityAIDropRider extends EntityAIBase {

    protected EntityCreature attacker;
    /** The PathEntity of our entity. */
    Path path;
	
	public EntityAIDropRider(EntityCreature creature) {
		super();
		this.attacker = creature;
	}
	
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
    	EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
    	
    	if(entitylivingbase == null)
    		return false;
    	else if(!attacker.getPassengers().isEmpty()) {
    		this.path = this.attacker.getNavigator().getPathToXYZ(entitylivingbase.posX, entitylivingbase.posY + 16.0D, entitylivingbase.posZ);
    		
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
    	return this.shouldExecute();
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.attacker.getNavigator().setPath(this.path, 1.0D);
    }
    
    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative()))
        {
            this.attacker.setAttackTarget((EntityLivingBase)null);
        }

        this.attacker.getNavigator().clearPath();
    }
    
    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
    	EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
    	
    	if(entitylivingbase != null && !attacker.getPassengers().isEmpty()) {
    		if(this.isOnTop(attacker, entitylivingbase)) {
	    		((EntityLiving) attacker.getPassengers().get(0)).setAttackTarget(entitylivingbase);
	    		((EntityLivingBase)attacker.getPassengers().get(0)).addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 3 * 20, 1));
	    		attacker.removePassengers();
    		}
    		else {
    			this.path = this.attacker.getNavigator().getPathToXYZ(entitylivingbase.posX, entitylivingbase.posY + 16.0D, entitylivingbase.posZ);
    			attacker.getNavigator().setPath(this.path, 1.0D);
    		}
    	}
    }
    
    private boolean isOnTop(EntityCreature flyerIn, EntityLivingBase targetIn) {
    	return targetIn.getDistance(flyerIn.posX, targetIn.posY, flyerIn.posZ) < 2.0D;
    }

}
