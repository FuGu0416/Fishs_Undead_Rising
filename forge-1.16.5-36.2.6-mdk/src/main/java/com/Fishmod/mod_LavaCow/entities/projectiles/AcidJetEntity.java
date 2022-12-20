package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AcidJetEntity extends ProjectileItemEntity {
	
	private float damage = 6.0F;
	
    public AcidJetEntity(EntityType<? extends AcidJetEntity> p_i48540_1_, World worldIn) {
        super(p_i48540_1_, worldIn);
    }

    public AcidJetEntity(EntityType<? extends AcidJetEntity> p_i48540_1_, LivingEntity throwerIn, World worldIn) {
        super(p_i48540_1_, throwerIn, worldIn);
    }

    public AcidJetEntity(EntityType<? extends AcidJetEntity> p_i48540_1_, double x, double y, double z, World worldIn) {
        super(p_i48540_1_, x, y, z, worldIn);
    }

    @Override
    public void tick() {
       super.tick();
       if(this.level.isClientSide())
    	   for(int i = 0 ; i < 8 + this.random.nextInt(8) ; i++) {
    		   this.level.addParticle(this.getParticleType(), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);	    	   
    	   }
    }
	   
	/**
	* Called when this EntityFireball hits a block or entity.
	*/
    @Override
    protected void onHitEntity(EntityRayTraceResult result) {
    	super.onHitEntity(result);
	    if (!this.level.isClientSide() && this.getOwner() != null && result.getEntity() instanceof LivingEntity && this.getOwner() instanceof LivingEntity && result.getEntity() != this.getOwner()) {    	
	    	this.setDamage( (float) ((LivingEntity) this.getOwner()).getAttributeValue(Attributes.ATTACK_DAMAGE));
	    	
	    	if (result.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity) this.getOwner()).setProjectile(), this.getDamage())) {
	    		float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();	    		
		    	((LivingEntity)result.getEntity()).addEffect(new EffectInstance(Effects.POISON, 2 * 20 * (int)local_difficulty, 0));
		    	((LivingEntity)result.getEntity()).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 2 * 20 * (int)local_difficulty, 3));			 
	            this.doEnchantDamageEffects((LivingEntity)this.getOwner(), result.getEntity());
	    	}
	    	
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
   
    public void setDamage(float damageIn) {
	    this.damage = damageIn;
    }

    public float getDamage() {
	    return this.damage;
    }	   

	protected BasicParticleType getParticleType() {
	    return FURParticleRegistry.GASTRO_ACID;
	}
	
	@Override
	protected Item getDefaultItem() {
		return FURItemRegistry.POISONSPORE;
	}
	
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}

