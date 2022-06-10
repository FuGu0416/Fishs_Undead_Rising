package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class DeathCoilEntity extends DamagingProjectileEntity {
	
	private float damage = 6.0F;
	
    public DeathCoilEntity(EntityType<? extends DeathCoilEntity> p_i50147_1_, World worldIn) {
        super(p_i50147_1_, worldIn);
    }

    public DeathCoilEntity(World worldIn, LivingEntity throwerIn, double x, double y, double z) {
        super(FUREntityRegistry.DEATHCOIL, throwerIn, x, y, z, worldIn);
    }

    @OnlyIn(Dist.CLIENT)
    public DeathCoilEntity(World worldIn, double x, double y, double z, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
        super(FUREntityRegistry.DEATHCOIL, p_i1795_12_, p_i1795_12_, p_i1795_12_, p_i1795_12_, p_i1795_12_, p_i1795_12_, worldIn);
    }

	   
    @Override
    public void tick() {
       super.tick();
       if(this.level.isClientSide())
    	   for(int i = 0 ; i < 4 + this.random.nextInt(16) ; i++) {
    		   this.level.addParticle(this.getTrailParticle(), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);	    	   
    	   }
       if(this.tickCount >= 5 * 20)
    	   this.remove();
    }
	   
	/**
	* Called when this EntityFireball hits a block or entity.
	*/
    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
    	super.onHitEntity(result);
	    if (!this.level.isClientSide() && result.getEntity() != null && this.getOwner() != null && result.getEntity() instanceof LivingEntity && result.getEntity() != this.getOwner()) {
	    	float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
	    	this.setDamage((float) ((LivingEntity) this.getOwner()).getAttributeValue(Attributes.ATTACK_DAMAGE));
	    	result.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity) this.getOwner()).setProjectile(), this.getDamage());
	    	((LivingEntity)result.getEntity()).addEffect(new EffectInstance(Effects.WITHER, 10 * 20 * (int)local_difficulty, 1));
	    	((LivingEntity)result.getEntity()).addEffect(new EffectInstance(FUREffectRegistry.FRAGILE, 10 * 20 * (int)local_difficulty, 2));
	    	this.remove();
	    }
    }
    
	@Override
	protected void onHit(RayTraceResult p_70227_1_) {
		super.onHit(p_70227_1_);
		if (!this.level.isClientSide) {
			this.remove();
		}
	}

    @Override
    public boolean isPickable() {
        return false;
    }
    
    @Override
    protected boolean shouldBurn() {
        return false;
	}
   
    public void setDamage(float damageIn) {
	    this.damage = damageIn;
    }

    public float getDamage() {
	    return this.damage;
    }	   

    @Override
    protected IParticleData getTrailParticle() {
        return ParticleTypes.SMOKE;
	}
    
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

