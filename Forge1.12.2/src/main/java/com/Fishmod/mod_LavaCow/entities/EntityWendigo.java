package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIPickupMeat;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWendigo extends EntityMob implements IAggressive {
    private static final DataParameter<Boolean> POUNCING = EntityDataManager.<Boolean>createKey(EntityWendigo.class, DataSerializers.BOOLEAN);
    private boolean isAggressive = false;
    private int attackTimer;
    /**
     * set the Cooldown to pounce attack
     */
    private int jumpTimer;
    /**
     * 4: Attack with both hands 5: right hand 6: left hand
     */
    public byte AttackStance;

    public EntityWendigo(World worldIn) {
        super(worldIn);
        this.setSize(1.6F, 2.6F);
        this.experienceValue = 20;
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        if (!Modconfig.SunScreen_Mode) this.tasks.addTask(1, new EntityAIFleeSun(this, 2.0D));
        this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(2, new AIWendigoLeapAtTarget(this, 0.7F));
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.25D, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAIPickupMeat<>(this, EntityItem.class, true));
        if (this.getAttackTarget() == null && Modconfig.Wendigo_KeepDistance)
            this.targetTasks.addTask(2, new EntityAIAvoidEntity<Entity>(this, Entity.class, new Predicate<Entity>() {
                @Override
                public boolean apply(Entity input) {
                    return input instanceof EntityPlayer && input.world.isDaytime();
                }
            }, 30.0F, 2.0D, 2.0D));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, new Predicate<Entity>() {
            public boolean apply(@Nullable Entity input) {
                return !input.world.isDaytime();
            }
        }));
        if (Modconfig.Wendigo_AnimalAttack)
            this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntityAgeable.class, 0, true, false, new Predicate<Entity>() {
                public boolean apply(@Nullable Entity input) {
                    return !(input instanceof EntityTameable) && !input.world.isDaytime();
                }
            }));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Wendigo_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Wendigo_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(POUNCING, Boolean.valueOf(false));
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    public double getMountedYOffset() {
        return -0.85D;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();
        EntityLivingBase target = this.getAttackTarget();

        if (this.attackTimer > 0) {
            --this.attackTimer;
        }

        if (this.jumpTimer > 0) {
            --this.jumpTimer;
        }

        if (this.isPouncing() && this.onGround) {
            this.setPouncing(false);
        }

        if (!this.world.isRemote) {
            if (target != null && this.getDistanceSq(target) < 4D && this.getAttackTimer() == 10 && this.deathTime <= 0 && this.canEntityBeSeen(target)) {
                boolean flag = false;

                if (this.AttackStance == (byte) 4) {
                    flag = this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 1.5F);
                    if (target instanceof EntityPlayer)
                        ((EntityPlayer) target).disableShield(true);
                } else
                    flag = this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());

                if (flag) {
                    float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
                    if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
                        this.getAttackTarget().setFire(2 * (int) f);
                    }

                    if (target instanceof EntityLivingBase) {
                        ((EntityLivingBase) this.getAttackTarget()).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 7 * 20 * (int) f, 4));
                    }
                }
            }
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTimer = 20;
        this.AttackStance = (byte) (4 + this.rand.nextInt(3));
        this.world.setEntityState(this, this.AttackStance);

        return true;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.isFireDamage())
            return super.attackEntityFrom(source, 2.0F * amount);
        return super.attackEntityFrom(source, amount);
    }

    protected void updateAITasks() {
        super.updateAITasks();
        if (this.getAttackTarget() != null) {
            isAggressive = true;
            this.world.setEntityState(this, (byte) 11);
        } else {
            isAggressive = false;
            this.world.setEntityState(this, (byte) 34);
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean isAggressive() {
        return isAggressive;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Wendigo_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Wendigo_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, livingdata);
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

    @Override
    public void setAttackTimer(int i) {
        this.attackTimer = i;
    }

    @SideOnly(Side.CLIENT)
    public void setAttackStance(byte byteIn) {
        this.AttackStance = byteIn;
    }

    @SideOnly(Side.CLIENT)
    public byte getAttackStance() {
        return this.AttackStance;
    }

    public void setPouncing(boolean pouncing) {
        this.dataManager.set(POUNCING, Boolean.valueOf(pouncing));
    }

    public boolean isPouncing() {
        return this.dataManager.get(POUNCING).booleanValue();
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        switch (id) {
            case 4:
            case 5:
            case 6:
                this.attackTimer = 20;
                this.AttackStance = id;
                break;
            case 11:
                this.isAggressive = true;
                break;
            case 34:
                this.isAggressive = false;
                break;
            default:
                super.handleStatusUpdate(id);
                break;
        }
    }

    static class AIWendigoLeapAtTarget extends EntityAIBase {
        /**
         * The entity that is leaping.
         */
        private final EntityLiving leaper;
        /**
         * The entity that the leaper is leaping towards.
         */
        private EntityLivingBase leapTarget;

        public AIWendigoLeapAtTarget(EntityLiving leapingEntity, float leapMotionYIn) {
            this.leaper = leapingEntity;
            this.setMutexBits(5);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            this.leapTarget = this.leaper.getAttackTarget();
            if (this.leapTarget == null || ((EntityWendigo) this.leaper).jumpTimer > 0) {
                return false;
            } else {
                float f = this.leaper.getDistance(this.leapTarget);
                if (!(f < 12.0D) && !(f > 20.0D)) {
                    return this.leaper.onGround;
                } else {
                    return false;
                }
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return this.leaper.onGround && ((EntityWendigo) this.leaper).jumpTimer >= 235;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            Vec3d vec3d1 = new Vec3d(this.leapTarget.posX - this.leaper.posX, 0.0D, this.leapTarget.posZ - this.leaper.posZ);
            float d0 = this.leaper.getDistance(this.leapTarget);

            if (((EntityWendigo) this.leaper).jumpTimer == 235) {
                if (vec3d1.lengthSquared() > 1.0E-7D) {
                    vec3d1 = vec3d1.normalize().scale(Math.min(d0, 15) * 0.2F);
                }

                this.leaper.motionX += vec3d1.x;
                this.leaper.motionY += vec3d1.y + 0.3F + 0.1F * MathHelper.clamp(this.leapTarget.getEyeHeight() - this.leaper.posY, 0, 2);
                this.leaper.motionZ += vec3d1.z;
                ((EntityWendigo) this.leaper).setPouncing(true);
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            Vec3d vec3d = new Vec3d(this.leapTarget.getPosition().subtract(this.leaper.getPosition()));
            this.leaper.getNavigator().clearPath();
            this.leaper.setRotationYawHead(-((float) Math.atan2(vec3d.x, vec3d.z)) * (180F / (float) Math.PI));
            this.leaper.rotationYaw = this.leaper.getRotationYawHead(); // ???
            this.leaper.playSound(FishItems.ENTITY_WENDIGO_ATTACK, 4.0F, 1.0F);
            ((EntityWendigo) this.leaper).jumpTimer = 240;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
        }
    }

    public float getEyeHeight() {
        return 2.4F;
    }

    @Override
    public int getTalkInterval() {
        return 200;
    }

    // Immune to Deathtouched, Corroded, Fear, Wither, and Slowness
    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return effect.getPotion() != ModMobEffects.FRAGILE && effect.getPotion() != ModMobEffects.CORRODED && effect.getPotion() != ModMobEffects.FEAR
                && effect.getPotion() != MobEffects.WITHER && effect.getPotion() != MobEffects.SLOWNESS && super.isPotionApplicable(effect);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_WENDIGO_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_WENDIGO_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_WENDIGO_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.WENDIGO;
    }
}
