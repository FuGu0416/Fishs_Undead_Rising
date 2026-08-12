package com.Fishmod.fur.entities.projectiles;

import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

/**
 * Acid Jet — the Boneworm's default ranged glob (skin 0): on a direct hit, deals the shooter's
 * attack damage and inflicts Poison + Corroded. Ported from 1.16.5; built on the mod's
 * {@link EnchantableFireBallEntity} like {@link SludgeJetEntity}, rather than the original's
 * now-unused {@code ProjectileItemEntity} base.
 */
public class AcidJetEntity extends EnchantableFireBallEntity {

    @SuppressWarnings("unchecked")
    public AcidJetEntity(EntityType<?> entityType, Level worldIn) {
        super((EntityType<? extends AcidJetEntity>) entityType, worldIn);
    }

    public AcidJetEntity(EntityType<? extends AcidJetEntity> entityType, LivingEntity shooter, double accelX, double accelY, double accelZ, Level worldIn) {
        super(entityType, shooter, accelX, accelY, accelZ, worldIn);
    }

    public AcidJetEntity(EntityType<? extends AcidJetEntity> entityType, double x, double y, double z, double accelX, double accelY, double accelZ, Level worldIn) {
        super(entityType, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide())
            for (int i = 0; i < 8 + this.random.nextInt(8); i++) {
                this.level().addParticle(FURParticleRegistry.GASTRO_ACID.get(), this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);
            }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity owner = this.getOwner();
        if (!this.level().isClientSide() && owner instanceof LivingEntity shooter && result.getEntity() instanceof LivingEntity victim && victim != owner) {
            this.setDamage((float) shooter.getAttributeValue(Attributes.ATTACK_DAMAGE));

            if (victim.hurt(this.damageSources().indirectMagic(this, shooter), this.getDamage())) {
                int local_difficulty = (int) this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                victim.addEffect(new MobEffectInstance(MobEffects.POISON, 2 * 20 * local_difficulty, 0));
                victim.addEffect(new MobEffectInstance(FUREffectRegistry.CORRODED.get(), 2 * 20 * local_difficulty, 3));
                this.doEnchantDamageEffects(shooter, victim);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }
}
