package com.Fishmod.mod_LavaCow.entities.ai;

import java.util.function.Predicate;

import com.Fishmod.mod_LavaCow.entities.IAggressive;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.Difficulty;

public class EntityFishAIBreakDoor extends BreakDoorGoal {

    private int knockknockTime;
	
	public EntityFishAIBreakDoor(MobEntity entityIn, Predicate<Difficulty> p_i50332_2_) {
		super(entityIn, p_i50332_2_);
	}
	
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start()
    {
        super.start();
        this.knockknockTime = 0;
    }
	
    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick()
    {
    	super.tick();
    	
    	if(this.knockknockTime % 40 == 0 && this.mob instanceof IAggressive) {
    		((IAggressive) this.mob).setAttackTimer(5);
    		this.mob.level.broadcastEntityEvent(this.mob, (byte)4);
    	}
    	this.knockknockTime++;
    }

}
