package com.Fishmod.mod_LavaCow.entities.floating;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityWraith extends EntityFloatingMob implements IAggressive {
    public EntityWraith(World worldIn) {
        super(worldIn);
        this.setSize(1.25F, 1.5F);
        this.daytimeBurning = true;
    }

    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityWraith.AIUseSpell());
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Wraith_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Wraith_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Wraith_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Wraith_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    protected void onDeathUpdate() {
        super.onDeathUpdate();

        if (this.isSpellcasting()) {
            if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))) {
                int i = this.getExperiencePoints(this.attackingPlayer);
                i = ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);

                while (i > 0) {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
                }
            }

            this.setDead();

            for (int k = 0; k < 20; ++k) {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                mod_LavaCow.PROXY.spawnCustomParticle("wither_flame", world, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d2, d0, d1, 0.0F, 0.0F, 0.0F);
            }
        }
    }

    public class AIUseSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;

        private boolean isAlly;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (EntityWraith.this.getAttackTarget() == null) {
                return false;
            } else if (EntityWraith.this.isSpellcasting()) {
                return false;
            } else {
                return EntityWraith.this.ticksExisted >= this.spellCooldown
                        && EntityWraith.this.getDistance(EntityWraith.this.getAttackTarget()) < 8.0F
                        && EntityWraith.this.getHealth() < EntityWraith.this.getMaxHealth() * 0.3F;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntityWraith.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntityWraith.this.spellTicks = this.getCastingTime();
            EntityWraith.this.world.setEntityState(EntityWraith.this, (byte) 10);
            this.spellCooldown = EntityWraith.this.ticksExisted + this.getCastingInterval();
            this.isAlly = false;
            SoundEvent soundevent = this.getSpellPrepareSound();

            for (EntityMob entitylivingbase : EntityWraith.this.world.getEntitiesWithinAABB(EntityMob.class, EntityWraith.this.getEntityBoundingBox().grow(Modconfig.Wraith_Ability_Radius))) {
                if (!EntityWraith.this.equals(entitylivingbase) && entitylivingbase.getAttackTarget() != null && entitylivingbase.getAttackTarget().equals(EntityWraith.this.getAttackTarget())) {
                    EntityWraith.this.setAttackTarget(entitylivingbase);
                    this.isAlly = true;
                    break;
                }
            }

            if (soundevent != null) {
                EntityWraith.this.playSound(soundevent, 4.0F, 1.2F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 5) {
                this.castSpell();
                EntityWraith.this.playSound(EntityWraith.this.getSpellSound(), 1.0F, 1.5F);

            }
        }

        protected void castSpell() {
            EntityWraith.this.world.setEntityState(EntityWraith.this, (byte) 11);

            if (EntityWraith.this.getAttackTarget() != null) {
                EntityLivingBase target = EntityWraith.this.getAttackTarget();
                float local_difficulty = EntityWraith.this.world.getDifficultyForLocation(new BlockPos(EntityWraith.this)).getAdditionalDifficulty();
                // 10 seconds * regional difficulty
                int difficulty_calculation = (int) (200 * local_difficulty);

                if (this.isAlly) {
                    target.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * 20 + difficulty_calculation, 2));
                    target.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 * 20 + difficulty_calculation, 1));
                    target.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * 20 + difficulty_calculation, 2));
                    target.setHealth(Math.min(target.getHealth() + EntityWraith.this.getHealth(), target.getMaxHealth()));
                } else {
                    EntityWraith.this.getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20 * 20 + difficulty_calculation, 0));
                    EntityWraith.this.getAttackTarget().addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE, 20 * 20 + difficulty_calculation, 4));
                    EntityWraith.this.getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 20 + difficulty_calculation, 0));
                }

                if (EntityWraith.this.world instanceof WorldServer) {
                    for (int j = 0; j < 16; ++j) {
                        double d0 = EntityWraith.this.getAttackTarget().posX + (double) (EntityWraith.this.getRNG().nextFloat() * EntityWraith.this.getAttackTarget().width * 2.0F) - (double) EntityWraith.this.getAttackTarget().width;
                        double d1 = EntityWraith.this.getAttackTarget().posY + (double) (EntityWraith.this.getRNG().nextFloat() * EntityWraith.this.getAttackTarget().height);
                        double d2 = EntityWraith.this.getAttackTarget().posZ + (double) (EntityWraith.this.getRNG().nextFloat() * EntityWraith.this.getAttackTarget().width * 2.0F) - (double) EntityWraith.this.getAttackTarget().width;
                        mod_LavaCow.PROXY.spawnCustomParticle("wither_flame", world, d0, d1, d2, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
                    }
                }

                EntityWraith.this.damageEntity(DamageSource.causeMobDamage(EntityWraith.this).setDamageIsAbsolute().setDamageBypassesArmor(), EntityWraith.this.getMaxHealth());
            }
        }

        protected int getCastWarmupTime() {
            return 30;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return 160;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return FishItems.ENTITY_WRAITH_ATTACK;
        }
    }

    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_WRAITH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_BANSHEE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_WRAITH_DEATH;
    }

    protected SoundEvent getSpellSound() {
        return FishItems.ENTITY_SKELETONKING_KNOCKBACK;
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
        return LootTableHandler.WRAITH;
    }
}
