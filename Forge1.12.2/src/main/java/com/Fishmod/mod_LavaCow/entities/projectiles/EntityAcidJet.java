package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.ModMobEffects;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityAcidJet extends EntityFireball {
	
	private float damage = 6.0F;
	
	   public EntityAcidJet(World worldIn) {
	      super(worldIn);
	      this.setSize(0.3125F, 0.3125F);
	   }

	   public EntityAcidJet(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
	      super(worldIn, shooter, accelX, accelY, accelZ);
	      this.setSize(0.3125F, 0.3125F);
	   }

	   public EntityAcidJet(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
	      super(worldIn, x, y, z, accelX, accelY, accelZ);
	      this.setSize(0.3125F, 0.3125F);
	   }
	   
	   @Override
	    public void onUpdate() {
	       super.onUpdate();
	       //if(!this.collided)this.accelerationY -= 0.006f;
	       //new ParticleSquidInk.Factory().makeParticle(Particles.SQUID_INK, this.world, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D).setColor(0.0F, 0.5F, 0.8F);
	       /*Particle fx = null;
	       fx = new ParticleSquidInk.Factory().makeParticle(Particles.SQUID_INK, this.world, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
	       fx.setColor(0.0F, 0.5F, 0.8F);*/
	       if(this.getEntityWorld().isRemote)
	    	   for(int i = 0 ; i < 4 + this.rand.nextInt(16) ; i++) {
	    		   //mod_LavaCow.PROXY.spawnCustomParticle("sludgejet", world, this.posX - 3.5D * this.rand.nextDouble() * this.motionX, this.posY - 3.5D * this.rand.nextDouble() * this.motionY, this.posZ - 3.5D * this.rand.nextDouble() * this.motionZ, this.motionX, this.motionY, this.motionZ, 0.5F, 0.8F, 0.5F);
	    		   world.spawnParticle(EnumParticleTypes.SLIME, this.posX + this.rand.nextDouble() * 0.5D, this.posY + 0.5D + this.rand.nextDouble() * 0.5D, this.posZ + this.rand.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);	    	   
	    	   }
	    }
	   
	   /**
	    * Return the motion factor for this projectile. The factor is multiplied by the original motion.
	    */
	   protected float getMotionFactor() {
	      return 1.0F;
	   }

	   /**
	    * Called when this EntityFireball hits a block or entity.
	    */
	   protected void onImpact(RayTraceResult result) {
	      if (!this.world.isRemote && result.entityHit != null && this.shootingEntity != null && result.entityHit instanceof EntityLivingBase) {
	    	  float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
	    	  this.setDamage( (float) this.shootingEntity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
	    	  result.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), this.getDamage());
	    	  ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.POISON, 2 * 20 * (int)local_difficulty, 0));
	    	  ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 2 * 20 * (int)local_difficulty, 3));
	    	  this.setDead();
	      }

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
	    * Called when the entity is attacked.
	    */
	   public boolean attackEntityFrom(DamageSource source, float amount) {
	      return false;
	   }

	   protected boolean isFireballFiery() {
	      return false;
	   }
	   
	    protected EnumParticleTypes getParticleType()
	    {
	        return EnumParticleTypes.SLIME;
	    }
	}

