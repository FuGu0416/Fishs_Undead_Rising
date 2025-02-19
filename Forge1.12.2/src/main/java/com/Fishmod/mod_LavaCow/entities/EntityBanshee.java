package com.Fishmod.mod_LavaCow.entities;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.floating.EntityFloatingMob;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityBanshee extends EntityFloatingMob implements IAggressive {
    public EntityBanshee(World worldIn) {
        super(worldIn);
        this.setSize(0.75F, 2.25F);
        this.daytimeBurning = true;
    }

    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityBanshee.AIUseSpell());
        this.tasks.addTask(3, new EntityFloatingMob.AIChargeAttack());
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Banshee_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Banshee_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }

    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.getSpellTicks() > 8 && this.getSpellTicks() < 13) {
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY + this.height, this.posZ, 0.0D, 1.0D, 0.0D);
        }
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Banshee_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Banshee_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, livingdata);
    }

    public class AIUseSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (EntityBanshee.this.getAttackTarget() == null) {
                return false;
            } else if (EntityBanshee.this.isSpellcasting()) {
                return false;
            } else {
                return EntityBanshee.this.ticksExisted >= this.spellCooldown && EntityBanshee.this.getDistance(EntityBanshee.this.getAttackTarget()) < 3.0F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntityBanshee.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntityBanshee.this.spellTicks = this.getCastingTime();
            EntityBanshee.this.world.setEntityState(EntityBanshee.this, (byte) 10);
            this.spellCooldown = EntityBanshee.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();

            if (soundevent != null) {
                EntityBanshee.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 5) {
                this.castSpell();
                EntityBanshee.this.playSound(EntityBanshee.this.getSpellSound(), 4.0F, 1.2F);

            }
        }

        protected void castSpell() {
            List<Entity> list = EntityBanshee.this.world.getEntitiesWithinAABBExcludingEntity(EntityBanshee.this, EntityBanshee.this.getEntityBoundingBox().grow(Modconfig.Banshee_Ability_Radius));
            EntityBanshee.this.world.setEntityState(EntityBanshee.this, (byte) 11);

            for (Entity entity1 : list) {
                if (entity1 instanceof EntityLivingBase) {
                    if (((EntityLivingBase) entity1).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
                        if (((EntityLivingBase) entity1).attackEntityFrom(DamageSource.causeMobDamage(EntityBanshee.this).setMagicDamage(), (float) EntityBanshee.this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 1.0F)) {
                            float local_difficulty = EntityBanshee.this.world.getDifficultyForLocation(new BlockPos(EntityBanshee.this)).getAdditionalDifficulty();
                            ((EntityLivingBase) entity1).addPotionEffect(new PotionEffect(ModMobEffects.FEAR, 7 * 20 * (int) local_difficulty, 2));
                        }
                    }
                }
            }
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return 160;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return null;
        }
    }

    public float getEyeHeight() {
        return this.height * 0.8F;
    }

    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_BANSHEE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_BANSHEE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_BANSHEE_DEATH;
    }

    protected SoundEvent getSpellSound() {
        return FishItems.ENTITY_BANSHEE_ATTACK;
    }

    // Immune to Infested
    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return effect.getPotion() != ModMobEffects.INFESTED && super.isPotionApplicable(effect);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.BANSHEE;
    }
}
