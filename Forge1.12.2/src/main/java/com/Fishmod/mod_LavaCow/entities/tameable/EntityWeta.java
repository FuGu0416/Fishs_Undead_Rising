package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIDestroyCrops;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.message.PacketParticle;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWeta extends EntityFishTameable implements IAggressive {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityWeta.class, DataSerializers.VARINT);
    private boolean isAggressive = false;
    private int attackTimer = 0;
    private int limitedLifeTicks;
    private EntityAIDestroyCrops DestroyCrops;
    private EntityAITempt tempt;

    public EntityWeta(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 0.5F);
        this.experienceValue = 5;
        this.limitedLifeTicks = -1;
    }

    @Override
    protected void initEntityAI() {
        this.DestroyCrops = new EntityAIDestroyCrops(this, 1.1D, this.isTamed());
        this.tempt = new EntityAITempt(this, 1.25D, false, Sets.newHashSet(FishItems.CANEPORK, FishItems.PLAGUED_PORKCHOP, FishItems.GREEN_BACON_AND_EGGS));

        super.initEntityAI();
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(3, this.tempt);
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, this.DestroyCrops);
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new AICopyOwnerTarget(this));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 0, true, true, (p_210136_0_) -> {
            return this.isTamed() && !(this.getOwner() instanceof EntityPlayer);
        }));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Weta_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Weta_Attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(this.rand.nextFloat() < 0.05F ? 1 : 0));
    }

    public void setLimitedLife(int limitedLifeTicksIn) {
        if (limitedLifeTicksIn != 0) {
            this.limitedLifeTicks = limitedLifeTicksIn;
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        if (this.attackTimer > 0) {
            --this.attackTimer;
        }

        if ((this.isTamed() && this.limitedLifeTicks >= 0 && this.ticksExisted >= this.limitedLifeTicks) || (this.limitedLifeTicks >= 0 && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.isTamed() && !(this.getOwner() instanceof EntityPlayer || this.isTamed() && this.getOwner() == null && Modconfig.Suicidal_Minion))) {
            if (!this.world.isRemote && this.world.getGameRules().getBoolean("showDeathMessages") && this.getOwner() instanceof EntityPlayerMP) {
                this.getOwner().sendMessage(SpawnUtil.TimeupDeathMessage(this));
            }

            if (this.world instanceof World) {
                for (int j = 0; j < 24; ++j) {
                    double d0 = this.posX + (double) (this.world.rand.nextFloat() * this.width * 2.0F) - (double) this.width;
                    double d1 = this.posY + (double) (this.world.rand.nextFloat() * this.height);
                    double d2 = this.posZ + (double) (this.world.rand.nextFloat() * this.width * 2.0F) - (double) this.width;
                    mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2));
                }
            }

            this.setDead();
        }

        super.onLivingUpdate();
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.CACTUS) return false;

        return super.attackEntityFrom(source, amount);
    }

    @Override
    protected float getJumpUpwardsMotion() {
        return 1.5F * super.getJumpUpwardsMotion();
    }

    @Override
    protected void doSitCommand(EntityPlayer playerIn) {
        this.tasks.removeTask(this.DestroyCrops);
        super.doSitCommand(playerIn);
    }

    @Override
    protected void doWanderCommand(EntityPlayer playerIn) {
        this.DestroyCrops = new EntityAIDestroyCrops(this, 1.1D, this.isTamed());
        this.tasks.addTask(5, this.DestroyCrops);
        super.doWanderCommand(playerIn);
    }

    @Override
    protected void setupTamedAI() {
        if (this.isTamed() && !(this.getOwner() instanceof EntityPlayer))
            this.tasks.removeTask(this.tempt);

        if (this.isTamed() && !this.isWandering() && this.DestroyCrops != null && this.getOwner() instanceof EntityPlayer) {
            this.tasks.removeTask(this.DestroyCrops);
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = this.isBreedingItem(itemstack);

        if (!flag && this.isOwner(player) && this.isTamed() && itemstack.getItem() == FishItems.DISEASED_BREAD && this.isEntityAlive() && this.getSkin() != 1) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
            this.setSkin(1);
            this.playSound(SoundEvents.ENTITY_ZOMBIE_INFECT, 1.0F, 1.0F);

            for (int i = 0; i < 16; ++i) {
                double d0 = new Random().nextGaussian() * 0.02D;
                double d1 = new Random().nextGaussian() * 0.02D;
                double d2 = new Random().nextGaussian() * 0.02D;
                this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + 0.5D + (double) (this.rand.nextFloat() * this.height), this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2);
            }
            return true;
        }

        boolean actionResultType = itemstack.interactWithEntity(player, this, hand);

        if (actionResultType) {
            return actionResultType;
        }

        return super.processInteract(player, hand);
    }

    public boolean attackEntityAsMob(Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            this.attackTimer = 5;
            this.world.setEntityState(this, (byte) 4);

            if (entity instanceof EntityLivingBase && this.getSkin() == 1) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ModMobEffects.SOILED, 8 * 20, 1));
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean canTameCondition() {
        return !this.isTamed() && !this.isChild() && this.getSkin() != 1 || (this.isTamed() && this.getOwner() == null);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack) {
        if (!this.isTamed() || (this.isTamed() && this.getOwner() instanceof EntityPlayer)) {
            return stack.getItem().equals(FishItems.CANEPORK) || stack.getItem().equals(FishItems.PLAGUED_PORKCHOP) || stack.getItem().equals(FishItems.GREEN_BACON_AND_EGGS);
        }

        return false;
    }

    @Override
    protected int TameRate(ItemStack stack) {
        if (stack.getItem().equals(FishItems.PLAGUED_PORKCHOP) || stack.getItem().equals(FishItems.CANEPORK)) {
            return 3;
        } else if (stack.getItem().equals(FishItems.GREEN_BACON_AND_EGGS)) {
            return 1;
        } else {
            return super.TameRate(stack);
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Weta_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Weta_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, entityLivingData);
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTamed() || (this.isTamed() && !(this.getOwner() instanceof EntityPlayer))) {
            return false;
        } else if (!(otherAnimal instanceof EntityWeta)) {
            return false;
        } else {
            EntityWeta entitywolf = (EntityWeta) otherAnimal;
            if (!entitywolf.isTamed()) {
                return false;
            } else if (entitywolf.isSitting()) {
                return false;
            } else {
                return this.isInLove() && entitywolf.isInLove();
            }
        }
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

    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
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

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        switch (id) {
            case 4:
                this.attackTimer = 5;
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
            return EntityWeta.this.getOwner() != null && EntityWeta.this.getOwner() instanceof EntityLiving && ((EntityLiving) EntityWeta.this.getOwner()).getAttackTarget() != null && this.isSuitableTarget(((EntityLiving) EntityWeta.this.getOwner()).getAttackTarget(), false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            EntityWeta.this.setAttackTarget(((EntityLiving) EntityWeta.this.getOwner()).getAttackTarget());
            super.startExecuting();
        }
    }

    @Override
    public float getEyeHeight() {
        return 0.1F;
    }

    @Override
    public boolean isMovementBlocked() {
        if (this.isSitting()) {
            return true;
        }

        return super.isMovementBlocked();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_WETA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_WETA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_WETA_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SPIDER_STEP;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.WETA;
    }

    public EntityWeta createChild(EntityAgeable ageable) {
        EntityWeta entity = new EntityWeta(this.world);
        UUID uuid = this.getOwnerId();
        if (uuid != null) {
            entity.setOwnerId(uuid);
            entity.setTamed(true);
            entity.setHealth(this.getMaxHealth());
        }

        return entity;
    }

    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
        return !this.isTamed();
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        if (this.getSkin() == 1) {
            return EnumCreatureAttribute.UNDEAD;
        } else {
            return EnumCreatureAttribute.ARTHROPOD;
        }
    }

    @Override
    public boolean isPreventingPlayerRest(EntityPlayer playerIn) {
        return !this.isTamed() && !(this.getOwner() instanceof EntityPlayer);
    }
}
