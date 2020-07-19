package com.Fishmod.mod_LavaCow.entities.projectiles;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityEnchantableFireBall extends EntityFireball {
	
	private float damage = 5.0F;
	protected int knockbackStrength;
	protected int flame = 0;
	
	   public EntityEnchantableFireBall(World worldIn) {
		   super(worldIn);
		   this.setSize(0.3125F, 0.3125F);
		   this.accelerationX = 0.0D;
		   this.accelerationY = 0.0D;
		   this.accelerationZ = 0.0D;
	   }

	   public EntityEnchantableFireBall(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
		   super(worldIn, shooter, accelX, accelY, accelZ);
		   this.setSize(0.3125F, 0.3125F);
		   this.accelerationX = 0.0D;
		   this.accelerationY = 0.0D;
		   this.accelerationZ = 0.0D;
	   }

	   public EntityEnchantableFireBall(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		   super(worldIn, x, y, z, accelX, accelY, accelZ);
		   this.setSize(0.3125F, 0.3125F);
		   this.accelerationX = 0.0D;
		   this.accelerationY = 0.0D;
		   this.accelerationZ = 0.0D;
	   }
	   
	   @Override
	    public void onUpdate() {
	       super.onUpdate();
	       if(this.ticksExisted >= 10 * 20)
	    	   this.setDead();
	    }
	   
	   /**
	    * Return the motion factor for this projectile. The factor is multiplied by the original motion.
	    */
	   protected float getMotionFactor() {
	      return /*0.8F*/1.0F;
	   }

	   /**
	    * Called when this EntityFireball hits a block or entity.
	    */
	   protected void onImpact(RayTraceResult result) {
		   this.setDead();
	   }

	   /**
	    * Returns true if other Entities should be prevented from moving through this Entity.
	    */
	   public boolean canBeCollidedWith() {
	      return false;
	   }
	   
	   public void setDamage(float damageIn) {
		   this.damage = damageIn;
	   }

	   public float getDamage() {
		   return this.damage;
	   }
	   
	   /**
	    * Sets the amount of knockback the arrow applies when it hits a mob.
	    */
	   public void setKnockbackStrength(int knockbackStrengthIn) {
	      this.knockbackStrength = knockbackStrengthIn;
	   }
	   
	    public void setFlame(boolean flameIn)
	    {
	        
	    	this.flame = flameIn? 5 : 0;
	    }

	   /**
	    * Called when the entity is attacked.
	    */
	   public boolean attackEntityFrom(DamageSource source, float amount) {
	      return false;
	   }

	   protected boolean isFireballFiery() {
	      return false;
	   }
	}

