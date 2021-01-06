package com.Fishmod.mod_LavaCow.entities.ai;

import com.Fishmod.mod_LavaCow.entities.EntityBoneWorm;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class EntityFishAIAttackRange extends EntityAIBase {
    private final EntityCreature shooter;
    // remember, all shot must extend from EntityFireBall
    private final Class <? extends Entity > shot;
    private SoundEvent sound;
    private int attackStep;
    private int attackTime;
    private double Xoffset, Yoffset, Zoffset;
    
    // how many projectiles
    private int shot_times;
    // wait X seconds to launch another attack
    private int attackCD;
    // whether to throw things with a curve;
    private double curve;
    // attack range
    private double range;

    public EntityFishAIAttackRange(EntityCreature shooterIn, Class <? extends Entity > shotIn, int timesIn, int attackCDIn) {
       this.shooter = shooterIn;
       this.shot = shotIn;
       this.sound = SoundEvents.ENTITY_BLAZE_SHOOT;
       this.shot_times = timesIn;
       this.attackCD = attackCDIn;
       this.curve = 0.0D;
       this.range = this.shooter.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue();
       this.Xoffset = 0.0D;
       this.Yoffset = 0.0D;
       this.Zoffset = 0.0D;
       this.setMutexBits(3);
    }
    
    public EntityFishAIAttackRange(EntityCreature shooterIn, Class <? extends Entity > shotIn, SoundEvent soundIn, int timesIn, int attackCDIn, double curveIn, double rangeIn) {
        this.shooter = shooterIn;
        this.shot = shotIn;
        this.sound = soundIn;
        this.shot_times = timesIn;
        this.attackCD = attackCDIn;
        this.curve = curveIn;
        this.range = rangeIn;
        this.Xoffset = 0.0D;
        this.Yoffset = 0.0D;
        this.Zoffset = 0.0D;
        this.setMutexBits(3);
     }
    
    public EntityFishAIAttackRange(EntityCreature shooterIn, Class <? extends Entity > shotIn, SoundEvent soundIn, int timesIn, int attackCDIn, double curveIn, double rangeIn, double XIn, double YIn, double ZIn) {
        this.shooter = shooterIn;
        this.shot = shotIn;
        this.sound = soundIn;
        this.shot_times = timesIn;
        this.attackCD = attackCDIn;
        this.curve = curveIn;
        this.range = rangeIn;
        this.Xoffset = XIn;
        this.Yoffset = YIn;
        this.Zoffset = ZIn;
        this.setMutexBits(3);
     }
    
    public EntityFishAIAttackRange(EntityCreature shooterIn, Class <? extends Entity > shotIn, int timesIn, int attackCDIn, double XIn, double YIn, double ZIn) {
        this.shooter = shooterIn;
        this.shot = shotIn;
        this.sound = SoundEvents.ENTITY_BLAZE_SHOOT;
        this.shot_times = timesIn;
        this.attackCD = attackCDIn;
        this.curve = 0.0D;
        this.range = this.shooter.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue();
        this.Xoffset = XIn;
        this.Yoffset = YIn;
        this.Zoffset = ZIn;
        this.setMutexBits(3);
     }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
       EntityLivingBase entitylivingbase = this.shooter.getAttackTarget();
       if(this.shooter instanceof EntityBoneWorm)
    	   return ((EntityBoneWorm)this.shooter).LocationFix == 0.0D && entitylivingbase != null && entitylivingbase.isEntityAlive();
       else
    	   return entitylivingbase != null && entitylivingbase.isEntityAlive();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
       this.attackStep = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() {
       --this.attackTime;
       EntityLivingBase entitylivingbase = this.shooter.getAttackTarget();

       if (entitylivingbase != null) {
           double d0 = this.shooter.getDistanceSq(entitylivingbase);
           if (d0 < (this.shooter.width + entitylivingbase.width) * (this.shooter.width + entitylivingbase.width)) {
              if (this.attackTime <= 0) {
                 this.attackTime = 20;
                 this.shooter.attackEntityAsMob(entitylivingbase);
              }

              this.shooter.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
           } else if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
              double d1 = entitylivingbase.posX - this.shooter.posX;
              double d2 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (this.shooter.posY + (double)(this.shooter.height / 2.0F));
              double d3 = entitylivingbase.posZ - this.shooter.posZ;
              if (this.attackTime <= 0) {
                 ++this.attackStep;
                 if (this.attackStep == 1) {
                    this.attackTime = 30;
                    if(this.shooter instanceof IAggressive && !this.shooter.isChild() && ((IAggressive) this.shooter).getAttackTimer() == 0.0F/* && !((EntitySalamander) this.shooter).isTamed()*/) {
                 	   ((IAggressive) this.shooter).setAttackTimer(80);
                    }
                 } else if (this.attackStep <= (this.shot_times + 1)) {
                    this.attackTime = 6;
                 } else {
                    this.attackTime = this.attackCD * 20;
                    this.attackStep = 0;
                 }

                 if (this.attackStep > 1) {
                    float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.1F;

                    this.shooter.playSound(this.sound, 1.0F, 1.0F);
                    double t1 = d1 + this.shooter.getRNG().nextGaussian() * (double)f;
                    double t2 = d2 + (this.curve * d0 / 64.0D);
                    double t3 = d3 + this.shooter.getRNG().nextGaussian() * (double)f;
                    double t4 = (double)MathHelper.sqrt(t1 * t1 + t2 * t2 + t3 * t3);                
                    Entity shotentity = EntityList.newEntity(this.shot, this.shooter.getEntityWorld());
                    ((EntityFireball)shotentity).shootingEntity = this.shooter;
                    shotentity.setLocationAndAngles(this.shooter.posX + (d1 / t4 * Xoffset), this.shooter.posY + (double)(this.shooter.height / 2.0F) + Yoffset, this.shooter.posZ + (d3 / t4 * Zoffset), this.shooter.rotationYaw, this.shooter.rotationPitch);
                    ((EntityFireball)shotentity).motionX = (t1 / t4) * 0.75D;
                    ((EntityFireball)shotentity).motionY = (t2 / t4) * 0.75D;
                    ((EntityFireball)shotentity).motionZ = (t3 / t4) * 0.75D;
                    this.shooter.world.spawnEntity(shotentity);
                    
                    if(this.shooter instanceof EntityBoneWorm && ((EntityBoneWorm)this.shooter).attackTimer[0] == 0) {
                    	((EntityBoneWorm)this.shooter).attackTimer[0] = 15;
                    	this.shooter.world.setEntityState(this.shooter, (byte)4);
                    	((EntityBoneWorm)this.shooter).setRunning(200);
                    }
                 }
              }

              this.shooter.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
           } else {
              this.shooter.getNavigator().clearPath();
              this.shooter.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, this.shooter.getMoveHelper().getSpeed());
           }
       }

       super.updateTask();
    }
    
    private double getFollowDistance() {
    	return this.range;
     }
}
