package com.Fishmod.mod_LavaCow.entities.ai;

import java.util.UUID;

import com.Fishmod.mod_LavaCow.core.SpawnUtil;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.world.server.ServerWorld;

public class EntityAIGuardingEntity extends TargetGoal {
	CreatureEntity tameable;
    LivingEntity attacker;
    private int timestamp;
    private UUID ownerID;
    
    public EntityAIGuardingEntity(CreatureEntity theDefendingTameableIn, UUID uniqueIDIn)
    {
        super(theDefendingTameableIn, false);
        this.tameable = theDefendingTameableIn;
        this.ownerID = uniqueIDIn;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse()
    {
    	if(this.tameable.level instanceof ServerWorld) {
	    	LivingEntity LivingEntity = SpawnUtil.getEntityByUniqueId(this.ownerID, (ServerWorld) this.tameable.level);
	
	        if (LivingEntity == null)
	        {
	        	return false;
	        }
	        else
	        {
	            this.attacker = LivingEntity.getLastHurtByMob();
	            int i = LivingEntity.getLastHurtByMobTimestamp();
	            return i != this.timestamp && this.canAttack(this.attacker, EntityPredicate.DEFAULT);
	        }
    	} else return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start()
    {
        this.mob.setTarget(this.attacker);
        
        if(this.tameable.level instanceof ServerWorld) {
	        LivingEntity LivingEntity = SpawnUtil.getEntityByUniqueId(this.ownerID, (ServerWorld) this.tameable.level);
	
	        if (LivingEntity != null)
	        {
	            this.timestamp = LivingEntity.getLastHurtByMobTimestamp();
	        }
        }

        super.start();
    }
}