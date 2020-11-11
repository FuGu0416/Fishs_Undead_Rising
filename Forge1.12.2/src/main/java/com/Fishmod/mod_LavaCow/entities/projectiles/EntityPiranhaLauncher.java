package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.entities.aquatic.EntityZombiePiranha;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPiranhaLauncher extends EntityEnchantableFireBall {
	
	   public EntityPiranhaLauncher(World worldIn) {
		   super(worldIn);
		   this.setDamage(this.getDamage() + 3.0F);
	   }

	   public EntityPiranhaLauncher(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
		   super(worldIn, shooter, accelX, accelY, accelZ);
		   this.setDamage(this.getDamage() + 3.0F);
	   }

	   public EntityPiranhaLauncher(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		   super(worldIn, x, y, z, accelX, accelY, accelZ);
		   this.setDamage(this.getDamage() + 3.0F);
	   }
	   
	    public static void registerFixesSmallFireball(DataFixer fixer)
	    {
	        EntityFireball.registerFixesFireball(fixer, "PiranhaLauncher");
	    }
	   
	   @Override
	    public void onUpdate() {
	       super.onUpdate();
	       if(!this.collided)this.accelerationY -= 0.002f;
	    }
	   
	   /**
	    * Return the motion factor for this projectile. The factor is multiplied by the original motion.
	    */
	   protected float getMotionFactor() {
	      return 0.8F;
	   }

	   /**
	    * Called when this EntityFireball hits a block or entity.
	    */
	   protected void onImpact(RayTraceResult result) { 
	      if (!this.world.isRemote) {
	    	  EntityZombiePiranha entityzombie = new EntityZombiePiranha(this.world);
	    	  entityzombie.setIsAmmo(true);
	    	  if(result.entityHit != null && result.entityHit instanceof EntityLivingBase) {
	    		  entityzombie.setPosition(result.entityHit.posX, result.entityHit.posY + result.entityHit.height, result.entityHit.posZ);
	    		  entityzombie.setAttackTarget((EntityLivingBase) result.entityHit);
	    		  if(!result.entityHit.isBeingRidden())entityzombie.startRiding(result.entityHit);
	    		  this.world.spawnEntity(entityzombie);
	            if (this.shootingEntity != null) {
	            	result.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), this.getDamage());           		            		            	            	
	            	if (this.knockbackStrength > 0) {
	                    float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
	                    if (f1 > 0.0F) {
	                    	result.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * (double)0.6F / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * (double)0.6F / (double)f1);
	                    }
	                 }
	            	
	            	if(this.isBurning())
	            		result.entityHit.setFire(5);
	            }
	            
	    	  }
	    	  else {
	    		  entityzombie.setPosition(result.hitVec.x, result.hitVec.y + 1.5D, result.hitVec.z);
	    		  this.world.spawnEntity(entityzombie);	    		  
	    	  }
	      }
	      this.setDead();
	   }
	   
	    protected EnumParticleTypes getParticleType()
	    {
	        return EnumParticleTypes.WATER_SPLASH;
	    }
	}

