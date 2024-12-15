package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityFlameJet extends EntityThrowable {
    private float damage = 8.0F;

    public EntityFlameJet(World worldIn) {
        super(worldIn);
    }

    public EntityFlameJet(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityFlameJet(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public static void registerFixesAcidJet(DataFixer fixer) {
        EntityThrowable.registerFixesThrowable(fixer, "FlameJet");
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.getEntityWorld().isRemote) {
            for (int i = 0; i < 8 + this.rand.nextInt(8); i++) {
                mod_LavaCow.PROXY.spawnCustomParticle("ectoplasm", world, this.posX + this.rand.nextDouble() * 0.5D, this.posY + 0.5D + this.rand.nextDouble() * 0.5D, this.posZ + this.rand.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    protected void onImpact(RayTraceResult result) {
        Entity target = result.entityHit;

        if (!this.world.isRemote && result != null && result.typeOfHit != RayTraceResult.Type.MISS) {
            if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
                if (target != null && this.getThrower() != null && target instanceof EntityLivingBase && target != this.getThrower() && !target.isOnSameTeam(this.getThrower())) {
                    boolean flag = target.attackEntityFrom(DamageSource.ON_FIRE, 6.0F);

                    if (flag) {
                        float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
                        // 5 seconds * regional difficulty
                        int difficulty_calculation = (int) (100 * local_difficulty);

                        ((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.WITHER, 5 * 20 + difficulty_calculation, 1));
                        ((EntityLivingBase) target).addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 5 * 20 + difficulty_calculation, 3));

                        this.applyEnchantments(this.thrower, target);
                    }
                }
            } else if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                boolean flag1 = true;

                if (this.thrower != null && this.thrower instanceof EntityLiving) {
                    ForgeEventFactory.getMobGriefingEvent(this.world, this.thrower);
                }

                if (flag1) {
                    BlockPos blockpos = result.getBlockPos().offset(result.sideHit);

                    if (this.world.isAirBlock(blockpos)) {
                        this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
                    }
                }
            }

            this.setDead();
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return false;
    }

    public void setDamage(float damageIn) {
        this.damage = damageIn;
    }

    public float getDamage() {
        return this.damage;
    }

    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SMOKE_NORMAL;
    }
}