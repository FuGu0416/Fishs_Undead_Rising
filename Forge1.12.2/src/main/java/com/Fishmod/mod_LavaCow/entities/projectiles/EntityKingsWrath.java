package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityBoneWorm;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityKingsWrath extends EntityThrowable {
    public EntityKingsWrath(World worldIn) {
        super(worldIn);
    }

    public EntityKingsWrath(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityKingsWrath(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public static void registerFixesAcidJet(DataFixer fixer) {
        EntityThrowable.registerFixesThrowable(fixer, "KingsWrath");
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.world.isRemote || (this.thrower == null || !this.thrower.isDead) && this.world.isBlockLoaded(new BlockPos(this))) {
            mod_LavaCow.PROXY.spawnCustomParticle("wither_flame", world, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        }

        if (this.ticksExisted >= 2 * 20) {
            this.setDead();
        }
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        Entity shooter = this.thrower;
        Entity target = result.entityHit;

        if (!this.world.isRemote && result != null && result.typeOfHit == RayTraceResult.Type.ENTITY) {
            if (target != null && shooter != null && target instanceof EntityLivingBase && target != shooter && !target.isOnSameTeam(shooter)) {
                if (target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, (EntityLivingBase) shooter).setProjectile(), this.getDamage())) {
                    float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
                    ((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.WITHER, 10 * 20 * (int) local_difficulty, 1));
                    ((EntityLivingBase) target).addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE_KING, 10 * 20 * (int) local_difficulty, 2));

                    if (shooter instanceof EntityLivingBase) {
                        this.applyEnchantments((EntityLivingBase) shooter, target);
                    }
                }
            }
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return false;
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity() {
        if (this.thrower instanceof EntityBoneWorm) {
            return 0.03F;
        } else {
            return 0.0F;
        }
    }

    public void setDamage(float damageIn) {
        this.damage = damageIn;
    }

    public float getDamage() {
        return (float) Modconfig.SkeletonKing_Kings_Wrath_Attack;
    }

    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SMOKE_NORMAL;
    }
}

