package com.Fishmod.mod_LavaCow.entities.ai;

import java.util.EnumSet;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class EntityFishAIAttackRange<T extends AbstractFireballEntity> extends Goal {
    private final CreatureEntity shooter;
    // remember, all shot must extend from FireballEntity
    private final EntityType<T> shot;
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

    public EntityFishAIAttackRange(CreatureEntity shooterIn, EntityType<T> shotIn, int timesIn, int attackCDIn) {
       this.shooter = shooterIn;
       this.shot = shotIn;
       this.sound = SoundEvents.BLAZE_SHOOT;
       this.shot_times = timesIn;
       this.attackCD = attackCDIn;
       this.curve = 0.0D;
       this.range = this.shooter.getAttribute(Attributes.FOLLOW_RANGE).getValue();
       this.Xoffset = 0.0D;
       this.Yoffset = 0.0D;
       this.Zoffset = 0.0D;
       this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }
    
    public EntityFishAIAttackRange(CreatureEntity shooterIn, EntityType<T> shotIn, SoundEvent soundIn, int timesIn, int attackCDIn, double curveIn, double rangeIn) {
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
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
     }
    
    public EntityFishAIAttackRange(CreatureEntity shooterIn, EntityType<T> shotIn, SoundEvent soundIn, int timesIn, int attackCDIn, double curveIn, double rangeIn, double XIn, double YIn, double ZIn) {
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
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
     }
    
    public EntityFishAIAttackRange(CreatureEntity shooterIn, EntityType<T> shotIn, int timesIn, int attackCDIn, double XIn, double YIn, double ZIn) {
        this.shooter = shooterIn;
        this.shot = shotIn;
        this.sound = SoundEvents.BLAZE_SHOOT;
        this.shot_times = timesIn;
        this.attackCD = attackCDIn;
        this.curve = 0.0D;
        this.range = this.shooter.getAttribute(Attributes.FOLLOW_RANGE).getValue();
        this.Xoffset = XIn;
        this.Yoffset = YIn;
        this.Zoffset = ZIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
     }

	/**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
       LivingEntity LivingEntity = this.shooter.getTarget();
       boolean flag = true;
 
       if (LivingEntity != null && LivingEntity.isAlive()) {
    	   double d0 = this.shooter.distanceToSqr(LivingEntity);
    	   if (d0 < (this.shooter.getBbWidth() + LivingEntity.getBbWidth()) * (this.shooter.getBbWidth() + LivingEntity.getBbWidth())) {
    		   flag = false;
    	   }
       } else {
    	   flag = false;
       }
       
       return flag;
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
       this.attackStep = 0;
       this.shooter.setAggressive(true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
    	this.shooter.setAggressive(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
       --this.attackTime;
       LivingEntity LivingEntity = this.shooter.getTarget();

       if (LivingEntity != null) {
           double d0 = this.shooter.distanceToSqr(LivingEntity);
           if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
              double d1 = LivingEntity.getX() - this.shooter.getX();
              double d2 = LivingEntity.getBoundingBox().minY + (double)(LivingEntity.getBbHeight() / 2.0F) - (this.shooter.getY() + (double)(this.shooter.getBbHeight() / 2.0F));
              double d3 = LivingEntity.getZ() - this.shooter.getZ();
              if (this.attackTime <= 0) {
                 ++this.attackStep;
                 if (this.attackStep == 1) {
                    this.attackTime = 30;
                    if(!this.shooter.isBaby()) {
                    	this.shooter.level.broadcastEntityEvent(this.shooter, (byte)5);
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
                    double t1 = d1 + this.shooter.getRandom().nextGaussian() * (double)f;
                    double t2 = d2 + (this.curve * d0 / 64.0D);
                    double t3 = d3 + this.shooter.getRandom().nextGaussian() * (double)f;
                    double t4 = (double)MathHelper.sqrt(t1 * t1 + t2 * t2 + t3 * t3);                
                    AbstractFireballEntity shotentity = this.shot.create(this.shooter.level);
                    shotentity.setOwner(this.shooter);
                    shotentity.moveTo(this.shooter.getX() + (d1 / t4 * Xoffset), this.shooter.getY() + (double)(this.shooter.getBbHeight() / 2.0F) + Yoffset, this.shooter.getZ() + (d3 / t4 * Zoffset), this.shooter.yRot, this.shooter.xRot);
                    shotentity.xPower = (t1 / t4) * 0.75D;
                    shotentity.yPower = (t2 / t4) * 0.75D;
                    shotentity.zPower = (t3 / t4) * 0.75D;
                    this.shooter.level.addFreshEntity(shotentity);               
                 }
              }

              this.shooter.getLookControl().setLookAt(LivingEntity, 30.0F, 30.0F);
           } else {
              this.shooter.getMoveControl().setWantedPosition(LivingEntity.getX(), LivingEntity.getY(), LivingEntity.getZ(), 1.0D);
           }
       }

       super.tick();
    }
    
    private double getFollowDistance() {
    	return this.range;
     }
}
