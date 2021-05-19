package com.Fishmod.mod_LavaCow.entities.ai;

import java.util.UUID;

import com.Fishmod.mod_LavaCow.core.SpawnUtil;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIGuardingEntity extends EntityAITarget
{
	EntityCreature tameable;
    EntityLivingBase attacker;
    private int timestamp;
    private UUID ownerID;
    
    public EntityAIGuardingEntity(EntityCreature theDefendingTameableIn, UUID uniqueIDIn)
    {
        super(theDefendingTameableIn, false);
        this.tameable = theDefendingTameableIn;
        this.ownerID = uniqueIDIn;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = SpawnUtil.getEntityByUniqueId(this.ownerID, this.tameable.world);

        if (entitylivingbase == null)
        {
            return false;
        }
        else
        {
            this.attacker = entitylivingbase.getLastAttackedEntity();
            int i = entitylivingbase.getLastAttackedEntityTime();
            return i != this.timestamp && this.isSuitableTarget(this.attacker, false);
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.attacker);
        EntityLivingBase entitylivingbase = SpawnUtil.getEntityByUniqueId(this.ownerID, this.tameable.world);

        if (entitylivingbase != null)
        {
            this.timestamp = entitylivingbase.getLastAttackedEntityTime();
        }

        super.startExecuting();
    }
}