package com.Fishmod.mod_LavaCow.entities.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.math.MathHelper;

public class EntityAIChargeAttack extends EntityAIBase {
    protected final EntityMob charger;
    protected int attackStep;
    private int attackTime;
    
    public EntityAIChargeAttack(EntityMob entityIn) {
       this.charger = entityIn;
       this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
    	EntityLivingBase LivingEntity = this.charger.getAttackTarget();
    	return LivingEntity != null && LivingEntity.isEntityAlive() && LivingEntity.posY <= this.charger.posY + 3.0D && this.charger.getDistanceSq(LivingEntity) > 6.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
       this.attackStep = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void resetTask() {
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void updateTask() {
       --this.attackTime;
       EntityLivingBase LivingEntity = this.charger.getAttackTarget();

       if (LivingEntity != null) {
           double d0 = this.charger.getDistance(LivingEntity);

           if (this.charger.getEntityBoundingBox().intersects(LivingEntity.getEntityBoundingBox())) {
              if (this.attackTime <= 0) {
                 this.attackTime = 30;
                 this.charger.attackEntityAsMob(LivingEntity);
                 LivingEntity.knockBack(LivingEntity, 2.0F * 0.5F, (double)MathHelper.sin(this.charger.rotationYaw * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.charger.rotationYaw * ((float)Math.PI / 180F))));
              }
           } else if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
              double v = 4.0D;
        	  double d1 = v * (LivingEntity.posX - this.charger.posX)/d0;
              double d2 = v * (LivingEntity.posY - this.charger.posY)/d0;
              double d3 = v * (LivingEntity.posZ - this.charger.posZ)/d0;
              if (this.attackTime <= 0) {
                 ++this.attackStep;
                 if (this.attackStep > 20) {
                	 this.charger.getMoveHelper().setMoveTo(this.charger.posX + d1, this.charger.posY + d2, this.charger.posZ + d3, 2.0D);
                 } else if(this.attackStep > 50) {
                	 this.attackTime = 30;
                	 this.attackStep = 0;
                 }
              }
              this.charger.getLookHelper().setLookPositionWithEntity(LivingEntity, 100.0F, 100.0F);
           } else {
              this.charger.getMoveHelper().setMoveTo(LivingEntity.posX, LivingEntity.posY, LivingEntity.posZ, 1.0D);
           }
       }

       super.updateTask();
    }
    
    private double getFollowDistance() {
    	ModifiableAttributeInstance iattributeinstance = (ModifiableAttributeInstance) this.charger.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getBaseValue();
    }
    
    public boolean isCharging() {
    	return this.attackStep > 20;
    }         
}
