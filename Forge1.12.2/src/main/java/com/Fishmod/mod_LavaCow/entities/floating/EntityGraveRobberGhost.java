package com.Fishmod.mod_LavaCow.entities.floating;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityGraveRobberGhost extends EntityFloatingMob implements IAggressive {
    public EntityGraveRobberGhost(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
        this.daytimeBurning = false;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityFloatingMob.AIChargeAttack());
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Grave_Robber_Ghost_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Grave_Robber_Ghost_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
    public float getBrightness() {
        return 1.0F;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Grave_Robber_Ghost_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Grave_Robber_Ghost_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, livingdata);
    }

    /**
     * Returns whether this Entity is on the same team as the given Entity.
     */
    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    // Immune Poison
    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return effect.getPotion() != MobEffects.POISON && super.isPotionApplicable(effect);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_GRAVE_ROBBER_GHOST_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_GRAVE_ROBBER_GHOST_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_GRAVE_ROBBER_GHOST_DEATH;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        // Always an illager, even at death...
        return EnumCreatureAttribute.ILLAGER;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.GRAVE_ROBBER_GHOST;
    }
}
