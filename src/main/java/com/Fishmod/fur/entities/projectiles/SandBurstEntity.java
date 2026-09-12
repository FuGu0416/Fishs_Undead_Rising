package com.Fishmod.fur.entities.projectiles;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.ForsakenEntity;
import com.Fishmod.fur.entities.SkeletonKingEntity;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

/**
 * Sand Burst — the Skeleton King's Sand Tomb spell. Behaves like an Evoker Fang made of sand:
 * after a warmup delay it erupts, damaging and launching every living thing standing on it.
 * Ported from the 1.16.5 module to 1.20.1 Mojmap.
 */
public class SandBurstEntity extends Entity {
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks = 22;
    private boolean clientSideAttackStarted;
    private LivingEntity caster;
    private UUID ownerUUID;

    public SandBurstEntity(EntityType<? extends SandBurstEntity> type, Level level) {
        super(type, level);
    }

    public SandBurstEntity(Level level, double x, double y, double z, float yRotIn, int warmupDelayIn, LivingEntity casterIn) {
        super(FUREntityRegistry.SANDBURST.get(), level);
        this.warmupDelayTicks = warmupDelayIn;
        this.setOwner(casterIn);
        this.setYRot(yRotIn * (180F / (float) Math.PI));
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData() {
    }

    public void setOwner(@Nullable LivingEntity ownerIn) {
        this.caster = ownerIn;
        this.ownerUUID = ownerIn == null ? null : ownerIn.getUUID();
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.caster == null && this.ownerUUID != null && this.level() instanceof ServerLevel server) {
            Entity entity = server.getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity) {
                this.caster = (LivingEntity) entity;
            }
        }

        return this.caster;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        this.warmupDelayTicks = nbt.getInt("Warmup");
        if (nbt.hasUUID("Owner")) {
            this.ownerUUID = nbt.getUUID("Owner");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.putInt("Warmup", this.warmupDelayTicks);
        if (this.ownerUUID != null) {
            nbt.putUUID("Owner", this.ownerUUID);
        }
    }

    @Override
    public void tick() {
        super.tick();
        BlockState state = this.level().getBlockState(this.blockPosition().below());

        if (this.level().isClientSide()) {
            if (this.clientSideAttackStarted) {
                --this.lifeTicks;
                if (this.lifeTicks == 14) {
                    for (int i = 0; i < 12; ++i) {
                        double d0 = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getBbWidth() * 0.5D;
                        double d1 = this.getY() + 0.05D + this.random.nextDouble();
                        double d2 = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getBbWidth() * 0.5D;
                        double d3 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
                        double d4 = 0.3D + this.random.nextDouble() * 0.3D;
                        double d5 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
                        this.level().addParticle(ParticleTypes.CRIT, d0, d1 + 1.0D, d2, d3, d4, d5);
                    }
                }
            }
        } else if (--this.warmupDelayTicks < 0) {
            if (this.warmupDelayTicks == -8) {
                for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2D, 0.0D, 0.2D))) {
                    if (!(livingentity instanceof SkeletonKingEntity) && !(livingentity instanceof ForsakenEntity))
                        this.dealDamageTo(livingentity);
                }
            }

            if (!this.sentSpikeEvent) {
                this.level().broadcastEntityEvent(this, (byte) 4);
                this.sentSpikeEvent = true;
            }

            if (--this.lifeTicks < 0) {
                // 30% chance for an expiring burst to raise a Sand Wraith (Forsaken) that inherits the
                // caster's target - restored now that FUREntityRegistry.FORSAKEN exists.
                if (this.random.nextFloat() < 0.3F && this.level() instanceof ServerLevel server) {
                    ForsakenEntity entity = SpawnUtil.trySpawnEntity(FUREntityRegistry.FORSAKEN.get(), server, this.blockPosition());

                    if (entity != null) {
                        if (this.getOwner() instanceof Mob mob && mob.getTarget() != null) {
                            entity.setTarget(mob.getTarget());
                        }

                        for (int j = 0; j < 4; ++j) {
                            server.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth(), this.getY() + this.random.nextDouble() * (double) this.getBbHeight() - 0.25D, this.getZ() + (this.random.nextDouble() - 0.5D) * (double) this.getBbWidth(), 15, 0.0D, this.random.nextDouble() * 0.5D, 0.0D, 0.0D);
                        }
                    }
                }

                this.discard();
            }
        }

        if (state.isSolid()) {
            if (this.level().isClientSide()) {
                for (int i = 0; i < 4; i++)
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state).setPos(this.blockPosition().below()), this.getX() + (this.random.nextDouble() * 4.0D - 2.0D), this.getY(), this.getZ() + (this.random.nextDouble() * 4.0D - 2.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            }
        }

        if (this.tickCount % 10 == 0 && !this.level().isClientSide()) {
            this.playSound(SoundEvents.SAND_BREAK, 1, 0.5F);
        }
    }

    private void dealDamageTo(LivingEntity target) {
        LivingEntity livingentity = this.getOwner();
        if (target.isAlive() && !target.isInvulnerable() && target != livingentity) {
            if (livingentity == null) {
                target.hurt(this.damageSources().magic(), 6.0F);
            } else {
                if (livingentity.isAlliedTo(target)) {
                    return;
                }

                target.hurt(this.damageSources().indirectMagic(this, livingentity), 6.0F);
                target.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.8D, 0.0D));
            }

        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 4) {
            this.clientSideAttackStarted = true;
            if (!this.isSilent()) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.EVOKER_FANGS_ATTACK, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.2F + 0.85F, false);
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getAnimationProgress(float partialTicks) {
        if (!this.clientSideAttackStarted) {
            return 0.0F;
        } else {
            int i = this.lifeTicks - 2;
            return i <= 0 ? 1.0F : 1.0F - ((float) i - partialTicks) / 20.0F;
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
