package com.Fishmod.mod_LavaCow.entities.ai;

import com.Fishmod.mod_LavaCow.entities.IAggressive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBreakDoor;

public class EntityFishAIBreakDoor extends EntityAIBreakDoor{

    private int knockknockTime;
	
	public EntityFishAIBreakDoor(EntityLiving entityIn) {
		super(entityIn);
	}
	
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        super.startExecuting();
        this.knockknockTime = 0;
    }
	
    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
    	super.updateTask();
    	
    	if(this.knockknockTime % 40 == 0 && this.entity instanceof IAggressive) {
    		((IAggressive) this.entity).setAttackTimer(5);
    		this.entity.world.setEntityState(this.entity, (byte)4);
    	}
    	this.knockknockTime++;
    }

}
