package com.Fishmod.fur.entities.ai;

import java.util.EnumSet;

import com.Fishmod.fur.entities.projectiles.EnchantableFireBallEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.Fireball;

public class FURRangeAttackGoal<T extends Fireball> extends Goal {
    private final PathfinderMob shooter;
    private LivingEntity target;
    // remember, all shot must extend from FireballEntity
    private final EntityType<T> shot;
    private SoundEvent sound;
    private int attackStep;
    private int attackTime;
    private double Xoffset, Yoffset, Zoffset;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;
    
    // how many projectiles
    private int shot_times;
    // wait X seconds to launch another attack
    private int attackCD;
    // whether to throw things with a curve;
    private double curve;
    // attack range
    private double range;
    // launch speed multiplier (default 0.5)
    private double speedMultiplier;
    // ticks between broadcastEntityEvent and actual projectile launch (default 4)
    private int windupTicks = 4;

	public FURRangeAttackGoal(PathfinderMob shooterIn, EntityType<T> shotIn, int timesIn, int attackCDIn) {
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
    	this.speedMultiplier = 0.5D;
    	this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public FURRangeAttackGoal(PathfinderMob shooterIn, EntityType<T> shotIn, SoundEvent soundIn, int timesIn, int attackCDIn, double curveIn, double rangeIn) {
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
        this.speedMultiplier = 0.5D;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

    public FURRangeAttackGoal(PathfinderMob shooterIn, EntityType<T> shotIn, SoundEvent soundIn, int timesIn, int attackCDIn, double curveIn, double rangeIn, double XIn, double YIn, double ZIn) {
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
        this.speedMultiplier = 0.5D;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

    public FURRangeAttackGoal(PathfinderMob shooterIn, EntityType<T> shotIn, SoundEvent soundIn, int timesIn, int attackCDIn, double curveIn, double rangeIn, double XIn, double YIn, double ZIn, double speedIn) {
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
        this.speedMultiplier = speedIn;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

    public FURRangeAttackGoal(PathfinderMob shooterIn, EntityType<T> shotIn, int timesIn, int attackCDIn, double XIn, double YIn, double ZIn) {
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
        this.speedMultiplier = 0.5D;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

    public FURRangeAttackGoal<T> withWindup(int ticks) {
        this.windupTicks = ticks;
        return this;
    }

    /** Upward launch bias: aims higher with distance so a gravity-affected shot arcs onto the target. */
    public FURRangeAttackGoal<T> withCurve(double curveIn) {
        this.curve = curveIn;
        return this;
    }

	/**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse() {
    	LivingEntity LivingEntity = this.shooter.getTarget();
    	boolean flag = true;    	
    	
    	if (this.shooter instanceof TamableAnimal tamable && tamable.isInSittingPose()) {
			return false;
    	} else if (LivingEntity != null && LivingEntity.isAlive()) {
    		double d0 = this.shooter.distanceToSqr(LivingEntity);
    		if (d0 < (this.shooter.getBbWidth() + LivingEntity.getBbWidth()) * (this.shooter.getBbWidth() + LivingEntity.getBbWidth())) {
    			flag = false;
    		}
    	} else {
    		flag = false;
    	}

    	return flag;
	}
    
    public boolean canContinueToUse() {
    	boolean flag = true;

    	if (this.shooter instanceof TamableAnimal tamable && tamable.isInSittingPose()) {
    		flag = false;
    	} else if (this.target != null && this.target.isAlive()) {
    		double d0 = this.shooter.distanceToSqr(this.target);
    		if (d0 < (this.shooter.getBbWidth() + this.target.getBbWidth()) * (this.shooter.getBbWidth() + this.target.getBbWidth())) {
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
    	this.target = this.shooter.getTarget();
    	this.shooter.setAggressive(true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
    	this.target = null;
    	this.shooter.setAggressive(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
    	--this.attackTime;

       	if (this.target != null) {
           	double d0 = this.shooter.distanceToSqr(this.target);
           	if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
           		double d1 = this.target.getX() - this.shooter.getX();
           		double d2 = (this.target.getBoundingBox().minY + this.target.getBbHeight() * 0.35D) - this.shooter.getY() - Yoffset - 0.25D;
           		double d3 = this.target.getZ() - this.shooter.getZ();
           		if (this.attackTime <= 0) {
           			++this.attackStep;
           			if (this.attackStep == 1) {
           				this.attackTime = this.windupTicks;
           				this.shooter.level().broadcastEntityEvent(this.shooter, (byte)70);
           			} else if (this.attackStep <= (this.shot_times + 1)) {
           				this.attackTime = 6;
           			} else {
           				this.attackTime = this.attackCD * 20;
           				this.attackStep = 0;
           			}

           			if (this.attackStep > 1) {
           				float f = (float) (Math.sqrt(Math.sqrt(d0)) * 0.1F);

           				this.shooter.playSound(this.sound, 1.0F, 1.0F);
           				double t1 = d1 + this.shooter.getRandom().nextGaussian() * (double)f;
           				double t2 = d2 + (this.curve * d0 / 64.0D);
           				double t3 = d3 + this.shooter.getRandom().nextGaussian() * (double)f;
           				double t4 = (double)Math.sqrt(t1 * t1 + t2 * t2 + t3 * t3);                
           				Fireball shotentity = this.shot.create(this.shooter.level());
           				shotentity.setOwner(this.shooter);
           				shotentity.moveTo(this.shooter.getX() + (d1 / t4 * Xoffset), this.shooter.getY() + (double)(this.shooter.getBbHeight() / 2.0F) + Yoffset, this.shooter.getZ() + (d3 / t4 * Zoffset), this.shooter.getYRot(), this.shooter.getXRot());
           				shotentity.setDeltaMovement((t1 / t4) * this.speedMultiplier, (t2 / t4) * this.speedMultiplier, (t3 / t4) * this.speedMultiplier);
                    
           				if (shotentity instanceof EnchantableFireBallEntity) {
           					((EnchantableFireBallEntity) shotentity).setFlame(true);
           				}
                    
           				this.shooter.level().addFreshEntity(shotentity);               
           			}
           		}	

                boolean flag = this.shooter.getSensing().hasLineOfSight(this.target);
                boolean flag1 = this.seeTime > 0;
                double attackRadiusSqr = this.range * this.range;
                
                if (flag != flag1) {
                   this.seeTime = 0;
                }

                if (flag) {
                   ++this.seeTime;
                } else {
                   --this.seeTime;
                }

                if (!(d0 > attackRadiusSqr) && this.seeTime >= 20) {
                   this.shooter.getNavigation().stop();
                   ++this.strafingTime;
                } else {
                   this.shooter.getNavigation().moveTo(this.target, 1.0D);
                   this.strafingTime = -1;
                }

                if (this.strafingTime >= 20) {
                   if (this.shooter.getRandom().nextDouble() < 0.3D) {
                      this.strafingClockwise = !this.strafingClockwise;
                   }

                   if (this.shooter.getRandom().nextDouble() < 0.3D) {
                      this.strafingBackwards = !this.strafingBackwards;
                   }

                   this.strafingTime = 0;
                }

                if (this.strafingTime > -1) {
                   if (d0 > (attackRadiusSqr * 0.75D)) {
                      this.strafingBackwards = false;
                   } else if (d0 < (attackRadiusSqr * 0.25D)) {
                      this.strafingBackwards = true;
                   }

                   this.shooter.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                   Entity entity = this.shooter.getControlledVehicle();
                   if (entity instanceof Mob) {
                      Mob mob = (Mob)entity;
                      mob.lookAt(this.target, 30.0F, 30.0F);
                   }

                   this.shooter.lookAt(this.target, 30.0F, 30.0F);
                } else {
                   this.shooter.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
                }
           	} else if (!this.shooter.getMoveControl().hasWanted()) {
           		this.strafingTime = -1;
           		this.shooter.getMoveControl().setWantedPosition(this.target.getX(), this.target.getY(), this.target.getZ(), 1.0D);
           	}
       	}

       	super.tick();
    }
    
    private double getFollowDistance() {
    	return this.range;
	}
}
