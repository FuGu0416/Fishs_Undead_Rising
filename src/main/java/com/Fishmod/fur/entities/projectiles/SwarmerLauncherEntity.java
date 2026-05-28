package com.Fishmod.fur.entities.projectiles;

import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.aquatic.SwarmerEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SwarmerLauncherEntity extends EnchantableFireBallEntity implements GeoEntity {
    private static final RawAnimation SWIM = RawAnimation.begin().thenPlay("swarmer.model.swimming");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @SuppressWarnings("unchecked")
    public SwarmerLauncherEntity(EntityType<?> type, Level level) {
        super((EntityType<? extends SwarmerLauncherEntity>) type, level);
        this.setDamage(this.getDamage() + 3.0F);
    }

    public SwarmerLauncherEntity(EntityType<? extends SwarmerLauncherEntity> type, LivingEntity shooter, double accelX, double accelY, double accelZ, Level level) {
        super(type, shooter, accelX, accelY, accelZ, level);
        this.setDamage(this.getDamage() + 3.0F);
    }

    public SwarmerLauncherEntity(EntityType<? extends SwarmerLauncherEntity> type, double x, double y, double z, double accelX, double accelY, double accelZ, Level level) {
        super(type, x, y, z, accelX, accelY, accelZ, level);
        this.setDamage(this.getDamage() + 3.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (!(this.horizontalCollision || this.verticalCollision))
            this.yPower -= 0.004D;
    }

    @Override
    protected float getInertia() {
        return 0.8F;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (this.level() instanceof ServerLevel serverLevel) {
            boolean isInfinite = false;
            if (this.getOwner() instanceof LivingEntity owner) {
                isInfinite = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, owner.getMainHandItem()) > 0
                          || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, owner.getOffhandItem()) > 0;
            }

            Entity entity = result.getEntity();
            SwarmerEntity swarmer = SpawnUtil.trySpawnEntity(FUREntityRegistry.SWARMER.get(), serverLevel, BlockPos.containing(result.getLocation()));

            if (swarmer != null) {
                swarmer.setIsAmmo(true);
                swarmer.setIsInfinite(isInfinite);

                if (entity instanceof LivingEntity targetEntity) {
                    swarmer.moveTo(entity.getX(), entity.getY() + entity.getBbHeight(), entity.getZ());
                    swarmer.startRiding(entity);
                    swarmer.setTarget(targetEntity);

                    if (this.getOwner() instanceof LivingEntity owner) {
                        if (entity.hurt(this.damageSources().thrown(this, owner), this.getDamage())) {
                            if (this.knockbackStrength > 0) {
                                Vec3 vec3d = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double) this.knockbackStrength * 0.6D);
                                if (vec3d.lengthSqr() > 0.0D) {
                                    entity.push(vec3d.x, 0.1D, vec3d.z);
                                }
                            }

                            if (this.isOnFire())
                                entity.setSecondsOnFire(5 + flame);

                            this.doEnchantDamageEffects(owner, entity);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if (this.level() instanceof ServerLevel serverLevel) {
            boolean isInfinite = false;
            if (this.getOwner() instanceof LivingEntity owner) {
                isInfinite = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, owner.getMainHandItem()) > 0
                          || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, owner.getOffhandItem()) > 0;
            }

            SwarmerEntity swarmer = SpawnUtil.trySpawnEntity(FUREntityRegistry.SWARMER.get(), serverLevel, BlockPos.containing(result.getLocation()));

            if (swarmer != null) {
                swarmer.setIsAmmo(true);
                swarmer.setIsInfinite(isInfinite);
            }
        }
    }

    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SPLASH;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    protected ItemStack getItemRaw() {
        return new ItemStack(FURItemRegistry.SWARMER_RAW.get());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
        state.getController().setAnimation(SWIM);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
