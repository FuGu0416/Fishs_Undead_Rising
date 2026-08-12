package com.Fishmod.fur.entities.projectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

/**
 * Flame Jet — the Boneworm's Soul Sand Valley-variant ranged glob (skin 1): on a direct hit,
 * deals the shooter's attack damage and sets the victim ablaze, and ignites the block it lands
 * on (respecting mobGriefing), matching vanilla SmallFireball's block-hit behaviour. Ported from
 * 1.16.5; built on the mod's {@link EnchantableFireBallEntity} like {@link SludgeJetEntity}/
 * {@link AcidJetEntity}.
 */
public class FlameJetEntity extends EnchantableFireBallEntity {

    @SuppressWarnings("unchecked")
    public FlameJetEntity(EntityType<?> entityType, Level worldIn) {
        super((EntityType<? extends FlameJetEntity>) entityType, worldIn);
    }

    public FlameJetEntity(EntityType<? extends FlameJetEntity> entityType, LivingEntity shooter, double accelX, double accelY, double accelZ, Level worldIn) {
        super(entityType, shooter, accelX, accelY, accelZ, worldIn);
    }

    public FlameJetEntity(EntityType<? extends FlameJetEntity> entityType, double x, double y, double z, double accelX, double accelY, double accelZ, Level worldIn) {
        super(entityType, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide())
            for (int i = 0; i < 8 + this.random.nextInt(8); i++) {
                this.level().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + this.random.nextDouble() * 0.5D, this.getY() + 0.5D + this.random.nextDouble() * 0.5D, this.getZ() + this.random.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D);
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
                victim.setRemainingFireTicks(2 * 20 * local_difficulty);
                this.doEnchantDamageEffects(shooter, victim);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide()) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), entity)) {
                BlockPos blockpos = result.getBlockPos().relative(result.getDirection());
                if (this.level().isEmptyBlock(blockpos)) {
                    this.level().setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level(), blockpos));
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }
}
