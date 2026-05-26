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
import net.minecraftforge.network.NetworkHooks;

public class LocustSwarmEntity extends Fireball {
    private float damage = 6.0F;

    @SuppressWarnings("unchecked")
    public LocustSwarmEntity(EntityType<?> p_i48540_1_, Level worldIn) {
        super((EntityType<? extends LocustSwarmEntity>) p_i48540_1_, worldIn);
    }

    public LocustSwarmEntity(EntityType<? extends LocustSwarmEntity> type, LivingEntity shooter, double x, double y, double z, double accelX, double accelY, double accelZ, Level worldIn) {
        super(type, x, y, z, accelX, accelY, accelZ, worldIn);
    }

    public LocustSwarmEntity(EntityType<? extends LocustSwarmEntity> type, LivingEntity shooter, double accelX, double accelY, double accelZ, Level worldIn) {
        super(type, shooter, accelX, accelY, accelZ, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.horizontalCollision && !this.verticalCollision)
            this.yPower -= 0.002D;

        if (this.level().isClientSide())
            for (int i = 0; i < 4 + this.random.nextInt(4); i++)
                this.level().addParticle(FURParticleRegistry.LOCUST_SWARM.get(),
                        this.getX() + this.random.nextDouble() * 0.5D,
                        this.getY() + 0.5D + this.random.nextDouble() * 0.5D,
                        this.getZ() + this.random.nextDouble() * 0.5D,
                        0.0D, 0.0D, 0.0D);
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
