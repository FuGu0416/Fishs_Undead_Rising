package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIAttackMelee;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIAttackRange;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySludgeJet;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityLilSludge;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.message.PacketParticle;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySludgeLord extends EntityMob implements IAggressive {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntitySludgeLord.class, DataSerializers.VARINT);
    public static final int ATTACK_TIMER = 30;
    private int attackTimer;
    private int RattackTimer;
    private boolean isAggressive = false;
    protected int spellTicks;

    public EntitySludgeLord(World worldIn) {
        super(worldIn);
        this.setSize(2.2F, 3.7F);
        this.experienceValue = 20;
    }

    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new AICastingApell());
        this.tasks.addTask(2, new EntityFishAIAttackRange(this, EntitySludgeJet.class, FishItems.ENTITY_SLUDGELORD_ATTACK, 1, 2, 0.0D, 8.0D, 1.2D, 0.6D, 1.2D));
        this.tasks.addTask(3, new EntitySludgeLord.AIUseSpell());
        this.tasks.addTask(4, new EntitySludgeLord.AISludgeLordAttack(this));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntitySludgeLord.AISludgeLordTarget<>(this, EntityPlayer.class, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.19D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.SludgeLord_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.SludgeLord_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
    }

    public boolean isSpellcasting() {
        return this.spellTicks > 0;
    }

    public int getSpellTicks() {
        return this.spellTicks;
    }

    @Override
    public double getMountedYOffset() {
        return -0.65D;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.attackTimer > 0) {
            --this.attackTimer;

            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
        }

        if (this.RattackTimer > 0) {
            --this.RattackTimer;
        }

        if (this.spellTicks > 0) {
            --this.spellTicks;

            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
        }
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.CACTUS) return false;

        if (source.isFireDamage())
            return super.attackEntityFrom(source, 2.0F * amount);
        return super.attackEntityFrom(source, amount);
    }

    public boolean attackEntityAsMob(Entity entity) {
        boolean flag = super.attackEntityAsMob(entity);

        if (flag) {
            float f = this.world.getDifficultyForLocation(this.getPosition()).getAdditionalDifficulty();

            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.getRNG().nextFloat() < f * 0.3F) {
                entity.setFire(2 * (int) f);
            }
        }

        return flag;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.SludgeLord_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.SludgeLord_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, livingdata);
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

    public float getEyeHeight() {
        return height * 0.8F;
    }

    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }

    public int getAttackTimer() {
        return this.attackTimer;
    }

    @Override
    public void setAttackTimer(int i) {
        this.attackTimer = i;
    }

    public int getRAttackTimer() {
        return this.RattackTimer;
    }

    public void setRAttackTimer(int i) {
        this.RattackTimer = i;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        switch (id) {
            case 4:
                this.attackTimer = ATTACK_TIMER;
                break;
            case 5:
                this.RattackTimer = 40;
                break;
            case 10:
                this.spellTicks = 100;
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

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.spellTicks = compound.getInteger("SpellTicks");
        setSkin(compound.getInteger("Variant"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("SpellTicks", this.spellTicks);
        compound.setInteger("Variant", getSkin());
    }

    // Immune to Poison
    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return effect.getPotion() != MobEffects.POISON && super.isPotionApplicable(effect);
    }

    public class AICastingApell extends EntityAIBase {
        public AICastingApell() {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntitySludgeLord.this.getSpellTicks() > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            EntitySludgeLord.this.navigator.clearPath();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            super.resetTask();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            if (EntitySludgeLord.this.getAttackTarget() != null) {
                EntitySludgeLord.this.getLookHelper().setLookPositionWithEntity(EntitySludgeLord.this.getAttackTarget(), (float) EntitySludgeLord.this.getHorizontalFaceSpeed(), (float) EntitySludgeLord.this.getVerticalFaceSpeed());
            }
        }
    }

    public class AIUseSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (EntitySludgeLord.this.getAttackTarget() == null) {
                return false;
            } else if (EntitySludgeLord.this.isSpellcasting() || EntitySludgeLord.this.getAttackTimer() > 0 || EntitySludgeLord.this.getRAttackTimer() > 0) {
                return false;
            } else {
                int i = EntitySludgeLord.this.world.getEntitiesWithinAABB(EntityLilSludge.class, EntitySludgeLord.this.getEntityBoundingBox().grow(16.0D)).size();
                return EntitySludgeLord.this.ticksExisted >= this.spellCooldown && i < Modconfig.SludgeLord_Ability_Max;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntitySludgeLord.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntitySludgeLord.this.spellTicks = this.getCastingTime();
            this.spellCooldown = EntitySludgeLord.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EntitySludgeLord.this.world.setEntityState(EntitySludgeLord.this, (byte) 10);

            if (soundevent != null) {
                EntitySludgeLord.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                EntitySludgeLord.this.playSound(EntitySludgeLord.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell() {
            for (int i = 0; i < Modconfig.SludgeLord_Ability_Num; ++i) {
                BlockPos blockpos = (new BlockPos(EntitySludgeLord.this)).add(-2 + EntitySludgeLord.this.rand.nextInt(5), 0, -2 + EntitySludgeLord.this.rand.nextInt(5));
                EntityLilSludge entity = new EntityLilSludge(EntitySludgeLord.this.world);
                entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
                entity.setHealth(entity.getMaxHealth());
                entity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                entity.onInitialSpawn(EntitySludgeLord.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData) null);
                entity.setOwnerId(EntitySludgeLord.this.getUniqueID());
                entity.setTamed(true);
                entity.setLimitedLife(20 * (30 + EntitySludgeLord.this.rand.nextInt(90)));

                if (!EntitySludgeLord.this.world.isRemote) {
                    EntitySludgeLord.this.world.spawnEntity(entity);
                }

                entity.setAttackTarget(EntitySludgeLord.this.getAttackTarget());

                if (EntitySludgeLord.this.world instanceof World) {
                    for (int j = 0; j < 24; ++j) {
                        double d0 = entity.posX + (double) (entity.world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width;
                        double d1 = entity.posY + (double) (entity.world.rand.nextFloat() * entity.height);
                        double d2 = entity.posZ + (double) (entity.world.rand.nextFloat() * entity.width * 2.0F) - (double) entity.width;
                        mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.WATER_SPLASH, d0, d1, d2));
                    }
                }
            }
        }

        protected int getCastWarmupTime() {
            return 80;
        }

        protected int getCastingTime() {
            return 100;
        }

        protected int getCastingInterval() {
            return Modconfig.SludgeLord_Ability_Cooldown * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
        }
    }

    static class AISludgeLordAttack extends EntityFishAIAttackMelee {
        float f = this.attacker.getBrightness();

        public AISludgeLordAttack(EntitySludgeLord entity) {
            super(entity, 1.0D, false);
        }

        @Override
        protected int atkTimerMax() {
            return ATTACK_TIMER;
        }

        @Override
        protected int atkTimerHit() {
            return 18;
        }

        @Override
        protected byte atkTimerEvent() {
            return (byte) 4;
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }

        @Override
        protected double getAttackReachSqr(EntityLivingBase attackTarget) {
            return 4.0F + attackTarget.width;
        }

        @Override
        protected void dmgEvent(EntityLivingBase target) {
            double d0 = this.attacker.posX + 2.5D * this.attacker.getLookVec().normalize().x;
            double d1 = this.attacker.posY;
            double d2 = this.attacker.posZ + 2.5D * this.attacker.getLookVec().normalize().z;
            IBlockState state = this.attacker.world.getBlockState(new BlockPos(d0, d1, d2).down());
            int blockId = Block.getStateId(state);

            if (state.getMaterial().isSolid()) {
                this.attacker.playSound(state.getBlock().getSoundType(state, this.attacker.world, new BlockPos(d0, d1, d2).down(), this.attacker).getBreakSound(), 1, 0.5F);

                if (this.attacker.world.isRemote) {
                    for (int i = 0; i < 64; i++) {
                        this.attacker.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d0 + (double) (this.attacker.getRNG().nextFloat() * this.attacker.width * 2.0F) - (double) this.attacker.width, d1 + (double) (this.attacker.getRNG().nextFloat() * this.attacker.width * 2.0F) - (double) this.attacker.width, d2 + (double) (this.attacker.getRNG().nextFloat() * this.attacker.width * 2.0F) - (double) this.attacker.width, this.attacker.getRNG().nextGaussian() * 0.02D, this.attacker.getRNG().nextGaussian() * 0.02D, this.attacker.getRNG().nextGaussian() * 0.02D, blockId);
                    }
                }
            }

            for (EntityLivingBase entitylivingbase : this.attacker.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(d0, d1, d2, d0, d1, d2).grow(1.5D))) {
                if (!this.attacker.equals(entitylivingbase) && !this.attacker.isOnSameTeam(entitylivingbase)) {
                    if (entitylivingbase instanceof EntityPlayer) ((EntityPlayer) entitylivingbase).disableShield(true);
                    if (!(entitylivingbase instanceof EntityTameable && ((EntityTameable) entitylivingbase).isOwner(this.attacker))) {
                        entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this.attacker), (float) this.attacker.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                        super.dmgEvent(entitylivingbase);
                    }
                }
            }
        }
    }

    static class AISludgeLordTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
        public AISludgeLordTarget(EntitySludgeLord entity, Class<T> classTarget, boolean checkSight) {
            super(entity, classTarget, checkSight);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }

    @Override
    public int getTalkInterval() {
        return 320;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_SLUDGELORD_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_SLUDGELORD_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_SLUDGELORD_DEATH;
    }

    protected SoundEvent getSpellSound() {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_IRONGOLEM_STEP;
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
        return LootTableHandler.SLUDGELORD;
    }
}
