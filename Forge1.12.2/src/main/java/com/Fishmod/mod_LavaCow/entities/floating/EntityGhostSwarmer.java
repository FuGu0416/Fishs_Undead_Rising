package com.Fishmod.mod_LavaCow.entities.floating;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.message.PacketParticle;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityGhostSwarmer extends EntityTameableFloatingMob implements IAggressive {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityGhostSwarmer.class, DataSerializers.VARINT);
    private int limitedLifeTicks;

    public EntityGhostSwarmer(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 0.8F);
        this.limitedLifeTicks = -1;
    }

    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityTameableFloatingMob.AIChargeAttack());
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
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

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Ghost_Swarmer_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Ghost_Swarmer_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
    }

    public void setLimitedLife(int limitedLifeTicksIn) {
        if (limitedLifeTicksIn != 0) {
            this.limitedLifeTicks = limitedLifeTicksIn;
        }
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Ghost_Swarmer_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Ghost_Swarmer_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, livingdata);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        if (this.isTamed() && this.limitedLifeTicks >= 0 && this.ticksExisted >= this.limitedLifeTicks || this.limitedLifeTicks >= 0 && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.isTamed() && !(this.getOwner() instanceof EntityPlayer) || this.isTamed() && this.getOwner() == null) {
            if (Modconfig.Suicidal_Minion) {
                if (!this.world.isRemote && this.world.getGameRules().getBoolean("showDeathMessages") && this.getOwner() instanceof EntityPlayerMP) {
                    this.getOwner().sendMessage(SpawnUtil.TimeupDeathMessage(this));
                }

                if (this.world instanceof World) {
                    for (int j = 0; j < 24; ++j) {
                        double d0 = this.posX + (double) (this.world.rand.nextFloat() * this.width * 2.0F) - (double) this.width;
                        double d1 = this.posY + (double) (this.world.rand.nextFloat() * this.height);
                        double d2 = this.posZ + (double) (this.world.rand.nextFloat() * this.width * 2.0F) - (double) this.width;
                        mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.WATER_SPLASH, d0, d1, d2));
                    }
                }

                this.setDead();
            }
        }

        if (this.ticksExisted % 2 == 0 && this.getEntityWorld().isRemote) {
            this.world.spawnParticle(EnumParticleTypes.TOWN_AURA, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
        }

        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        this.playSound(FishItems.ENTITY_ZOMBIEPIRANHA_GHOST_ATTACK, 1.0F, 1.0F);
        return super.attackEntityAsMob(entity);
    }

    class AICopyOwnerTarget extends EntityAITarget {
        public AICopyOwnerTarget(EntityCreature creature) {
            super(creature, false);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntityGhostSwarmer.this.getOwner() != null && EntityGhostSwarmer.this.getOwner() instanceof EntityLiving && ((EntityLiving) EntityGhostSwarmer.this.getOwner()).getAttackTarget() != null && this.isSuitableTarget(((EntityLiving) EntityGhostSwarmer.this.getOwner()).getAttackTarget(), false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            EntityGhostSwarmer.this.setAttackTarget(((EntityLiving) EntityGhostSwarmer.this.getOwner()).getAttackTarget());
            super.startExecuting();
        }
    }

    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_ZOMBIEPIRANHA_GHOST_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ELDER_GUARDIAN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_ZOMBIEPIRANHA_GHOST_DEATH;
    }

    @Override
    public int getTalkInterval() {
        return 150;
    }

    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("Variant", getSkin());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        setSkin(nbt.getInteger("Variant"));
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.5F;
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
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.GHOST_SWARMER;
    }

    @Override
    public boolean isPreventingPlayerRest(EntityPlayer playerIn) {
        return !this.isTamed() && !(this.getOwner() instanceof EntityPlayer);
    }
}
