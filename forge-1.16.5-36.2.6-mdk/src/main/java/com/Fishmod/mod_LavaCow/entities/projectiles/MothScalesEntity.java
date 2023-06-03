package com.Fishmod.mod_LavaCow.entities.projectiles;

import net.minecraft.entity.LivingEntity;

import java.util.List;

import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MothScalesEntity extends AbstractFireballEntity {	
	private float damage = 6.0F;
	
	public MothScalesEntity(EntityType<? extends MothScalesEntity> p_i48540_1_, World worldIn) {
		super(p_i48540_1_, worldIn);
	}

	public MothScalesEntity(EntityType<? extends MothScalesEntity> p_i48540_1_, LivingEntity shooter, double x, double y, double z, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i48540_1_, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public MothScalesEntity(EntityType<? extends MothScalesEntity> p_i48540_1_, LivingEntity shooter, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i48540_1_, shooter, accelX, accelY, accelZ, worldIn);
	}
	   
	@Override
	public void tick() {
		super.tick();
		if(!this.horizontalCollision && !this.verticalCollision)
			this.yPower -= 0.006D;

		if(this.level.isClientSide())
			for(int i = 0 ; i < 4 + this.random.nextInt(4) ; i++) {
				this.level.addParticle(this.getParticleType(), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);
			}
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	*/
    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        Entity entity = this.getOwner();
        if (p_70227_1_.getType() != RayTraceResult.Type.ENTITY || !((EntityRayTraceResult)p_70227_1_).getEntity().is(entity)) {
        	if (!this.level.isClientSide) {
        		List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
             	AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(), this.getZ());
             	if (entity instanceof LivingEntity) {
             		areaeffectcloudentity.setOwner((LivingEntity)entity);
             	}
                
             	areaeffectcloudentity.setRadius(2.0F);
             	areaeffectcloudentity.setRadiusOnUse(-0.5F);
             	areaeffectcloudentity.setWaitTime(10);
             	areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
             	areaeffectcloudentity.setPotion(Potions.POISON);
             	areaeffectcloudentity.addEffect(new EffectInstance(Effects.POISON, 2 * 20, 0));
                
             	if (!list.isEmpty()) {
             		for(LivingEntity livingentity : list) {
             			double d0 = this.distanceToSqr(livingentity);
             			if (d0 < 16.0D) {
             				areaeffectcloudentity.setPos(livingentity.getX(), livingentity.getY(), livingentity.getZ());
             				break;
             			}
             		}
             	}

             	this.level.addFreshEntity(areaeffectcloudentity);
             	this.remove();
           }

        }
	}
    
	/**
	 * Return the motion factor for this projectile. The factor is multiplied by the original motion.
	 */
	@Override
	protected float getInertia() {
		return 0.33F;
	}
	   
	public void setDamage(float damageIn) {
		this.damage = damageIn;
	}

	public float getDamage() {
		return this.damage;
	}	   
	   
	protected BasicParticleType getParticleType() {
		return FURParticleRegistry.MOTH_SCALES;
	}	

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    
    @Override
    protected boolean shouldBurn() {
        return false;
	}

    @Override
	protected IParticleData getTrailParticle() {
		return ParticleTypes.SQUID_INK;
	}
}