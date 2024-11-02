package com.Fishmod.mod_LavaCow.entities.tameable;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.message.PacketParticle;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityScarab extends EntityFishTameable implements IAggressive {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityScarab.class, DataSerializers.VARINT);
    private boolean isAggressive = false;
    private int attackTimer = 10;
    private int limitedLifeTicks;
    private int fire_aspect;
    private int sharpness;
    private int knockback;
    private int bane_of_arthropods;
    private int smite;
    private int lifesteal;
    private int poisonous;
    private int corrosive;
    private int unbreaking;
    private boolean isSmoking = false;

    public EntityScarab(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 0.6F);
        this.limitedLifeTicks = -1;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SKIN_TYPE, Integer.valueOf(0));
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(4, new AICopyOwnerTarget(this));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 0, true, true, (p_210136_0_) -> {
            return !(this.getOwner() instanceof EntityLivingBase);
        }));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Amber_Scarab_Health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2363D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Amber_Scarab_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }

    public void setLimitedLife(int limitedLifeTicksIn) {
        if (limitedLifeTicksIn != 0) {
            this.limitedLifeTicks = limitedLifeTicksIn;
        }
    }

    public float getBonusDamage(EntityLivingBase entityLivingBaseIn) {
        return (0.5f * this.sharpness + 0.5f)
                + (entityLivingBaseIn.getCreatureAttribute().equals(EnumCreatureAttribute.ARTHROPOD) ? (float) bane_of_arthropods * 2.5f : 0)
                + (entityLivingBaseIn.getCreatureAttribute().equals(EnumCreatureAttribute.UNDEAD) ? (float) smite * 2.5f : 0);
    }

    public int getLifestealLevel() {
        return this.lifesteal;
    }

    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }

    @Override
    protected boolean isCommandable() {
        return false;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        if (this.isSmoking) {
            for (int j = 0; j < 8; ++j) {
                float f = this.rand.nextFloat() * ((float) Math.PI * 2F);
                float f1 = this.height * 0.4F + this.rand.nextFloat() * 0.5F;
                float f2 = MathHelper.sin(f) * f1;
                float f3 = MathHelper.cos(f) * f1;
                World world = this.world;
                EnumParticleTypes enumparticletypes = EnumParticleTypes.SMOKE_LARGE;
                double d0 = this.posX + (double) f2;
                double d1 = this.posZ + (double) f3;
                world.spawnParticle(enumparticletypes, d0, this.getCollisionBoundingBox().minY + (double) f1, d1, 0.0D, 0.05D, 0.0D);
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.attackTimer > 0)
            this.attackTimer--;

        if (this.isTamed() && this.limitedLifeTicks >= 0 && this.ticksExisted >= this.limitedLifeTicks || this.limitedLifeTicks >= 0 && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.isTamed() && !(this.getOwner() instanceof EntityPlayer) || this.isTamed() && this.getOwner() == null && Modconfig.Suicidal_Minion) {
            if (!this.world.isRemote && this.world.getGameRules().getBoolean("showDeathMessages") && this.getOwner() instanceof EntityPlayerMP) {
                this.getOwner().sendMessage(SpawnUtil.TimeupDeathMessage(this));
            }

            if (this.world instanceof World) {
                for (int j = 0; j < 24; ++j) {
                    double d0 = this.posX + (double) (this.world.rand.nextFloat() * this.width * 2.0F) - (double) this.width;
                    double d1 = this.posY + (double) (this.world.rand.nextFloat() * this.height);
                    double d2 = this.posZ + (double) (this.world.rand.nextFloat() * this.width * 2.0F) - (double) this.width;
                    mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(fire_aspect > 0 ? EnumParticleTypes.FLAME : EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2));
                }
            }

            this.world.setEntityState(this, (byte) 11);
            this.setDead();
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag) {
            this.attackTimer = 10;
            this.world.setEntityState(this, (byte) 40);

            if (entityIn instanceof EntityLivingBase) {
                if (this.fire_aspect > 0)
                    entityIn.setFire((this.fire_aspect * 4) - 1);

                if (this.knockback > 0)
                    ((EntityLivingBase) entityIn).knockBack(this, (float) this.knockback * 0.5F, (this.posX - entityIn.posX) / this.getDistance(entityIn), (this.posZ - entityIn.posZ) / this.getDistance(entityIn));

                if (this.bane_of_arthropods > 0 && (((EntityLivingBase) entityIn).getCreatureAttribute().equals(EnumCreatureAttribute.ARTHROPOD))) {
                    int i = 20 + this.rand.nextInt(10 * bane_of_arthropods);
                    ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, i, 3));
                }

                if (this.poisonous > 0)
                    ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 8 * 20, this.poisonous - 1));

                if (this.corrosive > 0)
                    ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 4 * 20, this.corrosive - 1));

                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModMobEffects.SOILED, 8 * 20, 1));
            }
        }

        return flag;
    }

    /**
     * If Animal, checks if the age timer is negative
     */
    @Override
    public boolean isChild() {
        return true;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Amber_Scarab_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Amber_Scarab_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, livingdata);
    }

    protected void updateAITasks() {
        super.updateAITasks();

        // Causes issues with attack animations
        /*if(this.getAttackTarget() != null) {       		
        		isAggressive = true;
        		this.world.setEntityState(this, (byte)11);
        	}
        else {
        		isAggressive = false;
        		this.world.setEntityState(this, (byte)34);
        	}*/
    }

    @Override
    public float getEyeHeight() {
        return height * 0.6F;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return source == DamageSource.FALL ? false : super.attackEntityFrom(source, amount);
    }

    @Override
    protected SoundEvent getFallSound(int heightIn) {
        return null;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public boolean isAggressive() {
        return isAggressive;
    }

    @Override
    public int getAttackTimer() {
        return this.attackTimer;
    }

    @Override
    public void setAttackTimer(int i) {
        this.attackTimer = i;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        switch (id) {
            case 11:
                this.attackTimer = 5;
                break;
            case 40:
                this.attackTimer = 10;
                break;
            default:
                super.handleStatusUpdate(id);
                break;
        }
    }

    class AICopyOwnerTarget extends EntityAITarget {
        public AICopyOwnerTarget(EntityCreature creature) {
            super(creature, false);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntityScarab.this.getOwner() != null && EntityScarab.this.getOwner() instanceof EntityLiving && ((EntityLiving) EntityScarab.this.getOwner()).getAttackTarget() != null && this.isSuitableTarget(((EntityLiving) EntityScarab.this.getOwner()).getAttackTarget(), false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            EntityScarab.this.setAttackTarget(((EntityLiving) EntityScarab.this.getOwner()).getAttackTarget());
            super.startExecuting();
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_SCARAB_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_SCARAB_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_SCARAB_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_CHICKEN_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setLimitedLife(compound.getInteger("LifeTicks"));
        setSkin(compound.getInteger("Variant"));
        this.fire_aspect = compound.getInteger("fire_aspect");
        this.sharpness = compound.getInteger("sharpness");
        this.knockback = compound.getInteger("knockback");
        this.bane_of_arthropods = compound.getInteger("bane_of_arthropods");
        this.smite = compound.getInteger("fire_aspect");
        this.lifesteal = compound.getInteger("lifesteal");
        this.poisonous = compound.getInteger("poisonous");
        this.corrosive = compound.getInteger("corrosive");
        this.unbreaking = compound.getInteger("unbreaking");
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Amber_Scarab_Health + ((float) this.unbreaking * 2.0F));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("LifeTicks", this.limitedLifeTicks - this.ticksExisted);
        compound.setInteger("Variant", getSkin());
        compound.setInteger("fire_aspect", this.fire_aspect);
        compound.setInteger("sharpness", this.sharpness);
        compound.setInteger("knockback", this.knockback);
        compound.setInteger("bane_of_arthropods", this.bane_of_arthropods);
        compound.setInteger("smite", this.smite);
        compound.setInteger("lifesteal", this.lifesteal);
        compound.setInteger("poisonous", this.poisonous);
        compound.setInteger("corrosive", this.corrosive);
        compound.setInteger("unbreaking", this.unbreaking);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.AMBER_SCARAB;
    }

    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
        return !this.isTamed();
    }

    @Override
    public boolean isPreventingPlayerRest(EntityPlayer playerIn) {
        return !this.isTamed() && !(this.getOwner() instanceof EntityPlayer);
    }
}
