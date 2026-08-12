package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityFlameJet;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntitySoulWorm extends EntityBoneWorm {
    // Note: this class used to re-declare its own LocationFix/attackTimer/diggingTimer fields
    // here, shadowing EntityBoneWorm's - since every method that actually reads/writes them
    // (onUpdate/onLivingUpdate/attackEntityFrom/handleStatusUpdate/etc.) is only ever defined in
    // EntityBoneWorm and never overridden here, Java's static field resolution meant those methods
    // always used EntityBoneWorm's own copies regardless of the runtime type, making the shadow
    // fields dead - confirmed nothing in this file ever read/wrote them either. Removed 2026-08-12
    // rather than carried forward, since EntityBoneWorm's LocationFix is now synced data rather
    // than a plain field and keeping a same-named dead shadow around here would only mislead.

    public EntitySoulWorm(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 2.0F);
        this.isImmuneToFire = true;
    }

    @Override
    protected void initEntityAI() {
        // BoneWormRangedAttackGoal (not the bare vanilla EntityAIAttackRanged) so this still
        // refuses to telegraph attacks while isHidden() - see EntityBoneWorm's fix note.
        this.range_atk = new BoneWormRangedAttackGoal(1.0D, 40, 60, 12.0F);
        this.avoid_player = new EntityAIAvoidEntity<EntityPlayer>(this, EntityPlayer.class, 10.0F, 1.0D, 1.2D);

        this.tasks.addTask(0, this.range_atk);
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    @Override
    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.SoulWorm_Health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.SoulWorm_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.SoulWorm_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.SoulWorm_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void spit(EntityLivingBase target) {
        EntityThrowable throwable = new EntityFlameJet(this.world, this);
        SoundEvent sound = SoundEvents.ENTITY_BLAZE_SHOOT;

        double d0 = target.posY + (double) target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX - this.posX;
        double d2 = d0 - throwable.posY;
        double d3 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        throwable.shoot(d1, d2 + (double) f, d3, 1.6F, 1.0F);
        this.playSound((SoundEvent) sound, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(throwable);

        if (target instanceof EntityPlayer)
            this.setRunning(100);
    }

    // Immune to Infested
    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return effect.getPotion() != ModMobEffects.INFESTED && super.isPotionApplicable(effect);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.SOULWORM;
    }
}
