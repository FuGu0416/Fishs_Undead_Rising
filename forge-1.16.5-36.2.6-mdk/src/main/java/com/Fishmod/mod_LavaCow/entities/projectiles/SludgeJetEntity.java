package com.Fishmod.mod_LavaCow.entities.projectiles;

import net.minecraft.entity.LivingEntity;

import com.Fishmod.mod_LavaCow.init.FURParticleRegistry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.network.IPacket;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class SludgeJetEntity extends AbstractFireballEntity {	
	private float damage = 6.0F;
	
	public SludgeJetEntity(EntityType<? extends SludgeJetEntity> p_i48540_1_, World worldIn) {
		super(p_i48540_1_, worldIn);
	}

	public SludgeJetEntity(EntityType<? extends SludgeJetEntity> p_i48540_1_, LivingEntity shooter, double x, double y, double z, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i48540_1_, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	public SludgeJetEntity(EntityType<? extends SludgeJetEntity> p_i48540_1_, LivingEntity shooter, double accelX, double accelY, double accelZ, World worldIn) {
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
    protected void onHitEntity(EntityRayTraceResult result) {
    	if (!this.level.isClientSide() && this.getOwner() != null && result.getEntity() instanceof LivingEntity && result.getEntity() != this.getOwner()) {
    		float local_difficulty = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
    		this.setDamage( (float) ((LivingEntity) this.getOwner()).getAttributeValue(Attributes.ATTACK_DAMAGE));
    		result.getEntity().hurt(DamageSource.indirectMobAttack(this, (LivingEntity) this.getOwner()).setProjectile(), this.getDamage());           		            		            	            		            	
    		((LivingEntity)result.getEntity()).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 4 * 20 * (int)local_difficulty, 3));
    		((LivingEntity)result.getEntity()).addEffect(new EffectInstance(Effects.BLINDNESS, 4 * 20 * (int)local_difficulty, 1));
    		this.remove();
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
		return FURParticleRegistry.SLUDGE_JET;
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