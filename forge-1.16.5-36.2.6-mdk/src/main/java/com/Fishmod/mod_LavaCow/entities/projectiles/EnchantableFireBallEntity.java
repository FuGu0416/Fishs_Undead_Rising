package com.Fishmod.mod_LavaCow.entities.projectiles;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EnchantableFireBallEntity extends AbstractFireballEntity {
	private float damage = 5.0F;
	protected int knockbackStrength;
	protected int flame = 0;
	
	public EnchantableFireBallEntity(EntityType<? extends EnchantableFireBallEntity> p_i50163_1_, World worldIn) {
		super(p_i50163_1_, worldIn);
		this.xPower = 0.0D;
		this.yPower = 0.0D;
		this.zPower = 0.0D;
	}

	public EnchantableFireBallEntity(EntityType<? extends EnchantableFireBallEntity> p_i50163_1_, LivingEntity shooter, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i50163_1_, shooter, accelX, accelY, accelZ, worldIn);
	}

	public EnchantableFireBallEntity(EntityType<? extends EnchantableFireBallEntity> p_i50163_1_, double x, double y, double z, double accelX, double accelY, double accelZ, World worldIn) {
		super(p_i50163_1_, x, y, z, accelX, accelY, accelZ, worldIn);
	}
	   
	@Override
	public void tick() {
		super.tick();
		if(this.tickCount >= 10 * 20)
			this.remove();
	}
	   
	/**
	 * Return the motion factor for this projectile. The factor is multiplied by the original motion.
	 */
	@Override
	protected float getInertia() {
		return 1.0F;
	}
	
	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	@Override
	protected void onHit(RayTraceResult result) {
		super.onHit(result);
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
	   
	/**
	 * Sets the amount of knockback the arrow applies when it hits a mob.
	 */
	public void setKnockbackStrength(int knockbackStrengthIn) {
		this.knockbackStrength = knockbackStrengthIn;
	}
	   
	public void setFlame(boolean flameIn) {        
		this.flame = flameIn? 5 : 0;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean hurt(DamageSource source, float amount) {
		return false;
	}

	@Override
	protected boolean shouldBurn() {
		return this.flame > 0;
	}
}