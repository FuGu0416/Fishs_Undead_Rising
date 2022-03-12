package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.entities.aquatic.SwarmerEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PiranhaLauncherEntity extends EnchantableFireBallEntity {
	
	public PiranhaLauncherEntity(EntityType<? extends PiranhaLauncherEntity> p_i50163_1_, World worldIn) {
		super(p_i50163_1_, worldIn);
		this.setDamage(this.getDamage() + 3.0F);
	}

	public PiranhaLauncherEntity(EntityType<? extends PiranhaLauncherEntity> p_i50163_1_, LivingEntity shooter, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i50163_1_, shooter, accelX, accelY, accelZ, worldIn);
		this.setDamage(this.getDamage() + 3.0F);
	}

	public PiranhaLauncherEntity(EntityType<? extends PiranhaLauncherEntity> p_i50163_1_, double x, double y, double z, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i50163_1_, x, y, z, accelX, accelY, accelZ, worldIn);
		this.setDamage(this.getDamage() + 3.0F);
	}
	   
	@Override
	public void tick() {
		super.tick();
		if(!(this.horizontalCollision || this.verticalCollision))
			this.yPower -= 0.004f;
	}
	   
	/**
	 * Return the motion factor for this projectile. The factor is multiplied by the original motion.
	 */
	protected float getInertia() {
		return 0.8F;
	}
	
	protected void onHitEntity(EntityRayTraceResult result) {
		super.onHitEntity(result);
		if (!this.level.isClientSide()) {
			Entity entity = result.getEntity();
			SwarmerEntity entityzombie = FUREntityRegistry.SWARMER.create(this.level);
			entityzombie.setIsAmmo(true);
			if(entity != null && entity instanceof LivingEntity) {
				entityzombie.moveTo(entity.getX(), entity.getY() + entity.getBbHeight(), entity.getZ());	    		  
				entityzombie.startRiding(entity);
				this.level.addFreshEntity(entityzombie);
				entityzombie.setTarget((LivingEntity) entity);
	            if (this.getOwner() != null && this.getOwner() instanceof LivingEntity) {
	            	entity.hurt(DamageSource.indirectMobAttack(this, (LivingEntity) this.getOwner()).setProjectile(), this.getDamage());           		            		            	            	
	                if (this.knockbackStrength > 0) {
	                	Vector3d vector3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockbackStrength * 0.6D);
	                    if (vector3d.lengthSqr() > 0.0D) {
	                    	entity.push(vector3d.x, 0.1D, vector3d.z);
	                    }
	                }   
	            	
	            	if(this.isOnFire())
	            		entity.setSecondsOnFire(5 + flame);
	            }	            
			}
		}
	}

	protected void onHitBlock(BlockRayTraceResult result) {
		super.onHitBlock(result);

		SwarmerEntity entityzombie = FUREntityRegistry.SWARMER.create(this.level);
		entityzombie.moveTo(result.getLocation().x, result.getLocation().y + 1.5D, result.getLocation().z);
		this.level.addFreshEntity(entityzombie);	    		  
	}
	   
	@Override
	protected IParticleData getTrailParticle() {
		return ParticleTypes.SPLASH;
	}
	   
	@Override
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}