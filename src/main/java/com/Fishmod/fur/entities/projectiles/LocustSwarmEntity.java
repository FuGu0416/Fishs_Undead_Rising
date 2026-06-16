package com.Fishmod.fur.entities.projectiles;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.tameable.ScarabEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class LocustSwarmEntity extends Fireball {
    /** Fraction of the heading error corrected each tick while homing (limited steering). */
    private static final double HOMING_CORRECTION = 0.05D;

    private float damage = 6.0F;
    /** Optional chase target assigned at spawn; transient (not serialized). */
    private LivingEntity homingTarget;
    /** Cruise speed captured on the first homing tick so the swarm keeps a steady pace. */
    private double homingSpeed = -1.0D;

    @SuppressWarnings("unchecked")
    public LocustSwarmEntity(EntityType<?> entityType, Level worldIn) {
        super((EntityType<? extends LocustSwarmEntity>) entityType, worldIn);
    }

    public LocustSwarmEntity(EntityType<? extends LocustSwarmEntity> type, LivingEntity shooter, double x, double y, double z, double accelX, double accelY, double accelZ, Level worldIn) {
        super(type, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    public LocustSwarmEntity(EntityType<? extends LocustSwarmEntity> type, LivingEntity shooter, double accelX, double accelY, double accelZ, Level worldIn) {
        super(type, shooter, accelX, accelY, accelZ, worldIn);
    }

    @Override
    public void tick() {
        // No gravity: the swarm is weightless. Instead, steer toward an assigned target if any.
        if (!this.level().isClientSide()) {
            if (this.homingTarget != null) {
                if (this.homingTarget.isAlive())
                    this.steerTowardsTarget();
                else
                    this.homingTarget = null;
            }
        }

        super.tick();

        if (this.level().isClientSide()) {
            // Inner layer: dense, tight core.
            for (int i = 0; i < 6 + this.random.nextInt(4); i++)
                this.spawnSwarmParticle(0.2D);
            // Outer layer: sparse, wider halo.
            for (int i = 0; i < 2 + this.random.nextInt(2); i++)
                this.spawnSwarmParticle(0.55D);
        }
    }

    /** Spawns one locust_swarm particle centered on the entity, scattered within +/- {@code spread}. */
    private void spawnSwarmParticle(double spread) {
        this.level().addParticle(FURParticleRegistry.LOCUST_SWARM.get(),
                this.getX() + (this.random.nextDouble() - 0.5D) * 2.0D * spread,
                this.getY() + 0.5D + (this.random.nextDouble() - 0.5D) * 2.0D * spread,
                this.getZ() + (this.random.nextDouble() - 0.5D) * 2.0D * spread,
                0.0D, 0.0D, 0.0D);
    }

    /**
     * Nudge the flight direction toward {@link #homingTarget} by {@link #HOMING_CORRECTION} of the
     * heading error this tick, keeping a constant cruise speed. The small per-tick fraction makes
     * the swarm bank gradually onto the target rather than snapping to it.
     */
    private void steerTowardsTarget() {
        if (this.horizontalCollision || this.verticalCollision)
            return;

        Vec3 velocity = this.getDeltaMovement();
        double speed = velocity.length();
        if (this.homingSpeed < 0.0D)
            this.homingSpeed = speed > 1.0E-4D ? speed : 0.5D;

        Vec3 desired = new Vec3(
                this.homingTarget.getX() - this.getX(),
                this.homingTarget.getY() + this.homingTarget.getBbHeight() * 0.5D - this.getY(),
                this.homingTarget.getZ() - this.getZ());
        if (desired.lengthSqr() < 1.0E-7D)
            return;

        Vec3 heading = speed > 1.0E-4D ? velocity.scale(1.0D / speed) : desired.normalize();
        Vec3 steered = heading.add(desired.normalize().subtract(heading).scale(HOMING_CORRECTION));
        if (steered.lengthSqr() < 1.0E-7D)
            return;

        this.setDeltaMovement(steered.normalize().scale(this.homingSpeed));
    }

    /** Assign a target the swarm will gradually chase in flight. Pass {@code null} to fly straight. */
    public void setHomingTarget(LivingEntity target) {
        this.homingTarget = target;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide && this.getOwner() instanceof LivingEntity owner && result.getEntity() instanceof LivingEntity target && result.getEntity() != owner) {
            this.setDamage((float) owner.getAttributeValue(Attributes.ATTACK_DAMAGE));

            if (target.hurt(this.damageSources().indirectMagic(this, owner), this.getDamage())) {
                float local_difficulty = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 4 * 20 * (int) local_difficulty, 4));
                this.doEnchantDamageEffects(owner, target);
            }

            int count = 2 + this.random.nextInt(2);
            for (int i = 0; i < count; i++)
                this.spawnScarab(BlockPos.containing(result.getLocation()), owner);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide && this.getOwner() instanceof LivingEntity owner) {
            int count = 2 + this.random.nextInt(2);
            for (int i = 0; i < count; i++)
                this.spawnScarab(result.getBlockPos(), owner);
        }
    }

    private void spawnScarab(BlockPos pos, LivingEntity owner) {
        ScarabEntity scarab = SpawnUtil.trySpawnEntity(FUREntityRegistry.SCARAB.get(), (ServerLevel) this.level(), pos);
        if (scarab != null) {
            scarab.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
            scarab.setHealth(scarab.getMaxHealth());
            scarab.setLimitedLife(FURConfig.Scarab_Lifespan.get() * 20);
            scarab.setOwnerUUID(owner.getUUID());

            if (owner instanceof Mob mob && mob.getTarget() != null)
                scarab.setTarget(mob.getTarget());
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide)
            this.discard();
    }

    @Override
    protected float getInertia() {
        return 0.9F;
    }

    public void setDamage(float damageIn) {
        this.damage = damageIn;
    }

    public float getDamage() {
        return this.damage;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return FURParticleRegistry.LOCUST_SWARM.get();
    }
}
