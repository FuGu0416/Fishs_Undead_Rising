package com.Fishmod.mod_LavaCow.entities.flying;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityParasite;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowOwnerFlying;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityVespa extends EntityRideableFlyingMob {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityVespa.class, DataSerializers.VARINT);

    public EntityVespa(World worldIn) {
        super(worldIn, Modconfig.Vespa_FlyingHeight_limit);
        this.setSize(1.6F, 1.0F);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityVespa.class}));
        this.targetTasks.addTask(2, new EntityAITargetNonTamed<>(this, EntityPlayer.class, false, new Predicate<Entity>() {
            public boolean apply(@Nullable Entity p_apply_1_) {
                return !(p_apply_1_.isRiding() && p_apply_1_.getRidingEntity() instanceof EntityVespa);
            }
        }).setUnseenMemoryTicks(160));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Vespa_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Vespa_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.1D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);

        if (tamed) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Vespa_Health * 2.0D);
            this.setHealth(this.getHealth() * 2.0F);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Vespa_Health);
            this.setHealth(this.getHealth());
        }
    }

    @Override
    protected EntityAIBase wanderGoal() {
        return new EntityFlyingMob.AIRandomFly(this);
    }

    @Override
    protected EntityAIBase followGoal() {
        return new EntityAIFollowOwnerFlying(this, 1.0D, 10.0F, 4.0F);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.45F;
    }

    @Override
    public int abilityCooldown() {
        return 30;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.onGround && this.ticksExisted % 20 == 0) {
            this.playSound(this.getFlyingSound(), 1.0F, 1.0F);
        }

        if (this.getAttackTimer() == 6 && this.abilityCooldown > 0 && this.deathTime <= 0) {
            double d0 = this.posX + 1.75D * this.getLookVec().normalize().x;
            double d1 = this.posY;
            double d2 = this.posZ + 1.75D * this.getLookVec().normalize().z;

            for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(d0, d1, d2, d0, d1, d2).grow(1.0D))) {
                if (!this.equals(entitylivingbase) && !this.isOnSameTeam(entitylivingbase)) {
                    this.attackEntityAsMob(entitylivingbase);
                }
            }
        }

        if (this.getAttackTimer() == 6 && this.deathTime <= 0) {
            this.playSound(FishItems.RANDOM_THORN_SHOOT, 0.6F, 2.0F);
        }
    }

    @Override
    protected void onGrowingAdult() {
        if (this.isChild()) {
            if (this.canBeSteered()) {
                this.setSaddled(false);

                if (!this.world.isRemote) {
                    this.dropItem(Items.SADDLE, 1);
                }
            }

            if (!this.world.isRemote) {
                EntityParasite larva = new EntityParasite(this.world);
                larva.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                larva.setSkin(2);

                if (this.isTamed() && this.getOwner() instanceof EntityPlayer) {
                    larva.setTamedBy((EntityPlayer) this.getOwner());
                    larva.setCustomNameTag(this.getCustomNameTag());
                }

                this.world.spawnEntity(larva);
            }

            this.setDead();
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
            if (par1Entity instanceof EntityLivingBase) {
                float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

                ((EntityLivingBase) par1Entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 6 * 20 * (int) local_difficulty, 0));

                if (rand.nextInt(5) == 0 && Modconfig.Vespa_Spread_Parasites && !this.isTamed()) {
                    ((EntityLivingBase) par1Entity).addPotionEffect(new PotionEffect(ModMobEffects.INFESTED, 6 * 20 * (int) local_difficulty, 0));
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
        if (this.world.rand.nextDouble() <= Modconfig.Vespa_Hornet_Variant) {
            this.setSkin(1);
        }

        return super.onInitialSpawn(difficulty, entityLivingData);
    }

    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }

    @Override
    protected double VehicleSpeedMod() {
        return (this.isInLava() || this.isInWater()) ? 0.2D : 2.0D;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", getSkin());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setSkin(compound.getInteger("Variant"));
    }

    // Immune to Poison, Infested, and Corroded
    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return effect.getPotion() != MobEffects.POISON && effect.getPotion() != ModMobEffects.INFESTED && effect.getPotion() != ModMobEffects.CORRODED && super.isPotionApplicable(effect);
    }

    @Override
    public int getTalkInterval() {
        return 1000;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_VESPA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_VESPA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_VESPA_DEATH;
    }

    protected SoundEvent getFlyingSound() {
        return FishItems.ENTITY_VESPA_FLYING;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block block) {
        if (this.getLandTimer() > 10) {
            this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
        }
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.VESPA;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    @Override
    protected float getSoundVolume() {
        return 0.7F;
    }

    public EntityParasite createChild(EntityAgeable ageable) {
        EntityParasite entity = new EntityParasite(this.world);
        UUID uuid = this.getOwnerId();
        if (uuid != null) {
            entity.setOwnerId(uuid);
            entity.setTamed(true);
            entity.setSkin(2);
            entity.setHealth(entity.getMaxHealth());
        }

        return entity;
    }
}
