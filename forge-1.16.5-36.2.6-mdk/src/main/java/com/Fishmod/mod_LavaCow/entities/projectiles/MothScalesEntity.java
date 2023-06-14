package com.Fishmod.mod_LavaCow.entities.projectiles;

import net.minecraft.entity.LivingEntity;

import java.util.List;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class MothScalesEntity extends AbstractFireballEntity {	
	private float damage = 6.0F;
	public int scaleType = 0;
	private float[][] scaleColor = {{0.42F, 0.4F, 1.00F}, {1.00F, 0.59F, 0.11F}, {0.99F, 0.0F, 0.02F}};
	
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
				this.level.addParticle(this.getParticleType(this.scaleType), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);
			}
	}
	
	@Override
	protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
		super.onHitEntity(p_213868_1_);
		
		if (!this.level.isClientSide && this.scaleType == 0) {
			
		}
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	*/
    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        Entity entity = this.getOwner();
        
		if (!this.level.isClientSide && (p_70227_1_.getType() != RayTraceResult.Type.MISS || !((EntityRayTraceResult)p_70227_1_).getEntity().is(entity))) {
			switch (this.scaleType) {    		   
    		    case 1 :
    	            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this.getOwner());
    	            this.level.explode((Entity)null, this.getX(), this.getY(), this.getZ(), 1.0F, flag, Explosion.Mode.NONE);
    	            this.remove();
    		    	break;
    		    case 2 :
	        		List<LivingEntity> list = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
	             	AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.level, this.getX(), this.getY(), this.getZ());
	             	if (entity instanceof LivingEntity) {
	             		areaeffectcloudentity.setOwner((LivingEntity)entity);
	             	}
	                
	             	areaeffectcloudentity.setRadius(2.0F);
	             	areaeffectcloudentity.setRadiusOnUse(-0.5F);
	             	areaeffectcloudentity.setWaitTime(10);
	             	areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
	             	areaeffectcloudentity.setPotion((this.scaleType == 0) ? Potions.POISON : Potions.STRENGTH);
	             	areaeffectcloudentity.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 20 * 20, 0));	   
	             	
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
    		    	break;  
    		    case 0 :
    		    default :
    		    	break;
			}               
		}
		
     	this.remove();
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
	   
	protected RedstoneParticleData getParticleType(int Skin) {		
		return new RedstoneParticleData(this.scaleColor[Skin][0] + this.random.nextFloat() * 0.3F, this.scaleColor[Skin][1] + this.random.nextFloat() * 0.3F, this.scaleColor[Skin][2] + this.random.nextFloat() * 0.3F, 0.66F + this.random.nextFloat() * 0.33F);
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
		return ParticleTypes.ASH;
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
	@OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 6) {
            this.scaleType = 0;
        } else if (id == 7) {
            this.scaleType = 1;
        } else if (id == 8) {
            this.scaleType = 2;
        } else {
            super.handleEntityEvent(id);
        }
    }
    
    public void addAdditionalSaveData(CompoundNBT p_213281_1_) {
    	super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putInt("ScaleType", this.scaleType);
	}

	public void readAdditionalSaveData(CompoundNBT p_70037_1_) {
		super.readAdditionalSaveData(p_70037_1_);
		if (p_70037_1_.contains("ScaleType", 99)) {
			this.scaleType = p_70037_1_.getInt("ScaleType");
		}
	}
}