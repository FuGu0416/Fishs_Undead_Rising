package com.Fishmod.mod_LavaCow.entities.ai;

import java.util.EnumSet;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

public class EntityChargeAttackGoal extends Goal {
    protected final MobEntity charger;
    protected int attackStep;
    private int attackTime;
    
    public EntityChargeAttackGoal(MobEntity entityIn) {
       this.charger = entityIn;
       this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canUse() {
    	LivingEntity LivingEntity = this.charger.getTarget();
    	return LivingEntity != null && LivingEntity.isAlive() && LivingEntity.getY() <= this.charger.getY() && this.charger.distanceToSqr(LivingEntity) > 6.0D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
       this.attackStep = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void stop() {
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
       --this.attackTime;
       LivingEntity LivingEntity = this.charger.getTarget();

       if (LivingEntity != null) {
           double d0 = this.charger.distanceTo(LivingEntity);

           if (this.charger.getBoundingBox().intersects(LivingEntity.getBoundingBox())) {
              if (this.attackTime <= 0) {
                 this.attackTime = 30;
                 this.charger.doHurtTarget(LivingEntity);
                 LivingEntity.knockback(2.0F * 0.5F, (double)MathHelper.sin(this.charger.yRot * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.charger.yRot * ((float)Math.PI / 180F))));
              }
           } else if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
              double v = 4.0D;
        	  double d1 = v * (LivingEntity.getX() - this.charger.getX())/d0;
              double d2 = v * (LivingEntity.getY() - this.charger.getY())/d0;
              double d3 = v * (LivingEntity.getZ() - this.charger.getZ())/d0;
              if (this.attackTime <= 0) {
                 ++this.attackStep;
                 if (this.attackStep > 20) {
                	 this.charger.getMoveControl().setWantedPosition(this.charger.getX() + d1, this.charger.getY() + d2, this.charger.getZ() + d3, 2.0D);
                 } else if(this.attackStep > 100) {
                	 this.attackTime = 60;
                	 this.attackStep = 0;
                 }
              }
              this.charger.getLookControl().setLookAt(LivingEntity, 100.0F, 100.0F);
           } else {
              this.charger.getMoveControl().setWantedPosition(LivingEntity.getX(), LivingEntity.getY(), LivingEntity.getZ(), 1.0D);
           }
       }

       super.tick();
    }
    
    private double getFollowDistance() {
    	ModifiableAttributeInstance iattributeinstance = this.charger.getAttribute(Attributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getBaseValue();
    }
    
    public boolean isCharging() {
    	return this.attackStep > 20;
    }         
}
