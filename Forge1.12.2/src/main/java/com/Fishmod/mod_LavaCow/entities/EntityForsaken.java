package com.Fishmod.mod_LavaCow.entities;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIChargeAttack;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIAttackRange;
import com.Fishmod.mod_LavaCow.entities.ai.EntityForsakenAIFollowOwner;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityDeathCoil;
import com.Fishmod.mod_LavaCow.init.AddRecipes;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.message.PacketParticle;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.base.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityForsaken extends AbstractSkeleton implements IEntityOwnable {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityForsaken.class, DataSerializers.VARINT);
    protected static final DataParameter<Byte> TAMED = EntityDataManager.<Byte>createKey(EntityForsaken.class, DataSerializers.BYTE);
    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.<Optional<UUID>>createKey(EntityForsaken.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    private final EntityAIChargeAttack entityAICharge = new EntityAIChargeAttack(this);
    private final EntityFishAIAttackRange entityAIStaff = new EntityFishAIAttackRange(this, EntityDeathCoil.class, FishItems.RANDOM_DEATH_COIL_SHOOT, 1, 1, 1.0D, 0.5D, 1.0D);
    protected int limitedLifeTicks;
    protected int risingTicks;

    public EntityForsaken(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
        this.limitedLifeTicks = -1;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(6, new EntityForsakenAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.applyEntityAI();
        this.setupTamedAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(4, new AICopyOwnerTarget(this));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(this.rand.nextInt(4)));
        this.dataManager.register(TAMED, Byte.valueOf((byte) 0));
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
    }

    @Override
    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Forsaken_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Forsaken_Attack);
    }

    public void setLimitedLife(int limitedLifeTicksIn) {
        if (limitedLifeTicksIn != 0) {
            this.limitedLifeTicks = limitedLifeTicksIn;
        }
    }

    public boolean isRising() {
        return this.risingTicks > 0;
    }

    @SideOnly(Side.CLIENT)
    public int getRisingTicks() {
        return this.risingTicks;
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
        super.applyEntityCollision(entityIn);

        if (this.entityAICharge != null && isCharging() && !isOnSameTeam(entityIn)) {
            attackEntityAsMob(entityIn);
            entityIn.motionX *= 0.8D;
            entityIn.motionY *= 1.6D;
            entityIn.motionZ *= 0.8D;
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onLivingUpdate() {
        IBlockState state = world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ).down());
        int blockId = Block.getStateId(state);

        if (this.isRising()) {
            if (state.isOpaqueCube()) {
                if (world.isRemote) {
                    for (int i = 0; i < 4; i++)
                        this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, blockId);
                }
            }

            if (this.ticksExisted % 10 == 0) {
                this.playSound(SoundEvents.BLOCK_SAND_BREAK, 1, 0.5F);
            }

            this.motionX = 0;
            this.motionY = 0;
            this.motionZ = 0;
        }

        super.onLivingUpdate();
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onUpdate() {
        if (this.risingTicks > 0) {
            --this.risingTicks;
        }

        if (!this.world.isRemote && Modconfig.Suicidal_Minion && (this.getOwner() != null && (!(this.getOwner() instanceof EntityPlayer) && !this.getOwner().isEntityAlive()))) {
            this.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageIsAbsolute().setDamageBypassesArmor(), this.getMaxHealth());
        }

        if (this.isTamed() && this.limitedLifeTicks >= 0 && this.ticksExisted >= this.limitedLifeTicks || this.limitedLifeTicks >= 0 && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.isTamed() && !(this.getOwner() instanceof EntityPlayer) || this.isTamed() && this.getOwner() == null) {
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

        super.onUpdate();
    }

    public boolean shouldAttackEntity(EntityLivingBase target, EntityLivingBase owner) {
        if (!(target instanceof EntityCreeper) && !(target instanceof EntityGhast)) {
            if (target instanceof EntityPlayer && owner instanceof EntityPlayer && !((EntityPlayer) owner).canAttackPlayer((EntityPlayer) target)) {
                return false;
            } else {
                return !(target instanceof AbstractHorse) || !((AbstractHorse) target).isTame();
            }
        } else {
            return false;
        }
    }

    class EntityAIOwnerHurtByTarget extends EntityAITarget {
        EntityForsaken forsaken;
        EntityLivingBase attacker;
        private int timestamp;

        public EntityAIOwnerHurtByTarget(EntityForsaken forsaken) {
            super(forsaken, false);
            this.forsaken = forsaken;
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (!this.forsaken.isTamed()) {
                return false;
            } else {
                EntityLivingBase entitylivingbase = this.forsaken.getOwner();

                if (entitylivingbase == null) {
                    return false;
                } else {
                    this.attacker = entitylivingbase.getRevengeTarget();
                    int i = entitylivingbase.getRevengeTimer();
                    return i != this.timestamp && this.isSuitableTarget(this.attacker, false) && this.forsaken.shouldAttackEntity(this.attacker, entitylivingbase);
                }
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.taskOwner.setAttackTarget(this.attacker);
            EntityLivingBase entitylivingbase = this.forsaken.getOwner();

            if (entitylivingbase != null) {
                this.timestamp = entitylivingbase.getRevengeTimer();
            }

            super.startExecuting();
        }
    }

    class EntityAIOwnerHurtTarget extends EntityAITarget {
        EntityForsaken forsaken;
        EntityLivingBase attacker;
        private int timestamp;

        public EntityAIOwnerHurtTarget(EntityForsaken forsaken) {
            super(forsaken, false);
            this.forsaken = forsaken;
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            if (!this.forsaken.isTamed()) {
                return false;
            } else {
                EntityLivingBase entitylivingbase = this.forsaken.getOwner();

                if (entitylivingbase == null) {
                    return false;
                } else {
                    this.attacker = entitylivingbase.getLastAttackedEntity();
                    int i = entitylivingbase.getLastAttackedEntityTime();
                    return i != this.timestamp && this.isSuitableTarget(this.attacker, false) && this.forsaken.shouldAttackEntity(this.attacker, entitylivingbase);
                }
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.taskOwner.setAttackTarget(this.attacker);
            EntityLivingBase entitylivingbase = this.forsaken.getOwner();

            if (entitylivingbase != null) {
                this.timestamp = entitylivingbase.getLastAttackedEntityTime();
            }

            super.startExecuting();
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
            return EntityForsaken.this.getOwner() != null && EntityForsaken.this.getOwner() instanceof EntityLiving && ((EntityLiving) EntityForsaken.this.getOwner()).getAttackTarget() != null && this.isSuitableTarget(((EntityLiving) EntityForsaken.this.getOwner()).getAttackTarget(), false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            EntityForsaken.this.setAttackTarget(((EntityLiving) EntityForsaken.this.getOwner()).getAttackTarget());
            super.startExecuting();
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.FORSAKEN;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }

    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
        return !this.isTamed();
    }

    // Immune to Deathtouched and Wither
    @Override
    public boolean isPotionApplicable(PotionEffect effect) {
        return effect.getPotion() != ModMobEffects.FRAGILE && effect.getPotion() != ModMobEffects.FRAGILE_KING && effect.getPotion() != MobEffects.WITHER && super.isPotionApplicable(effect);
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);

        if (cause.getTrueSource() instanceof EntityCreeper) {
            EntityCreeper entitycreeper = (EntityCreeper) cause.getTrueSource();

            if (entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop()) {
                entitycreeper.incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 1), 0.0F);
            }
        }

        if (!this.world.isRemote && this.world.getGameRules().getBoolean("showDeathMessages") && this.getOwner() instanceof EntityPlayerMP) {
            this.getOwner().sendMessage(this.getCombatTracker().getDeathMessage());
        }
    }

    /**
     * Create a compound tag for the specified pattern and colour.
     *
     * @param pattern The pattern
     * @param color   The colour
     * @return The compound tag
     */
    protected NBTTagCompound createPatternTag(BannerPattern pattern, EnumDyeColor color) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("Pattern", pattern.getHashname());
        tag.setInteger("Color", color.getDyeDamage());
        return tag;
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
    }

    @Override
    public void setCombatTask() {
        if (this.world != null && !this.world.isRemote) {
            this.tasks.removeTask(this.entityAIStaff);
            ItemStack itemstack = this.getHeldItemMainhand();

            if (itemstack.getItem().equals(FishItems.FORSAKEN_STAFF)) {
                this.tasks.addTask(3, this.entityAIStaff);
            } else {
                super.setCombatTask();
                if (this.getHeldItemOffhand().getItem().equals(Items.SHIELD)) {
                    this.tasks.addTask(2, this.entityAICharge);
                }
            }
        }
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
        ItemStack shield = new ItemStack(Items.SHIELD);
        NBTTagList patternsList = new NBTTagList();
        shield.getOrCreateSubCompound("BlockEntityTag").setTag("Patterns", patternsList);
        patternsList.appendTag(createPatternTag(AddRecipes.PATTERN_SKELETONKING, EnumDyeColor.WHITE));
        shield.getOrCreateSubCompound("BlockEntityTag").setInteger("Base", EnumDyeColor.BLACK.getDyeDamage());

        switch (this.getSkin()) {
            case 0:
                this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, shield);
                this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(7.0D);
                this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4D);
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
            case 1:
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
                this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
                break;
            case 2:
            default:
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
                this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(5.0D);
                break;
            case 3:
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(FishItems.FORSAKEN_STAFF));
                this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
                break;
        }

        return ientitylivingdata;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        if (!super.attackEntityAsMob(entityIn)) {
            return false;
        } else {
            if (entityIn instanceof EntityLivingBase) {
                if (this.getOwner() instanceof EntitySkeletonKing) {
                    ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE_KING, 10 * 20, 2));
                } else {
                    ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE, 10 * 20, 2));
                }
            }

            return true;
        }
    }

    @Override
    public boolean isOnSameTeam(Entity entity) {
        if (this.isTamed()) {
            EntityLivingBase entitylivingbase = this.getOwner();

            if (entity == entitylivingbase) {
                return true;
            }

            if (entitylivingbase != null) {
                return entitylivingbase.isOnSameTeam(entity);
            }
        }

        if (entity == null) {
            return false;
        } else if (entity == this) {
            return true;
        } else if (super.isOnSameTeam(entity)) {
            return true;
        } else if (entity instanceof EntitySkeletonKing || entity instanceof EntityForsaken || entity instanceof EntityBoneWorm) {
            return this.getTeam() == null && entity.getTeam() == null;
        } else {
            return false;
        }
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        Entity entity = source.getTrueSource();

        if (entity != null && entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isOnSameTeam(this)) {
            return false;
        }

        return super.attackEntityFrom(source, amount);
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        switch (id) {
            case 32:
                this.risingTicks = 20;
                break;
            default:
                super.handleStatusUpdate(id);
                break;
        }
    }

    public Team getTeam() {
        if (this.isTamed()) {
            EntityLivingBase entitylivingbase = this.getOwner();

            if (entitylivingbase != null) {
                return entitylivingbase.getTeam();
            }
        }

        return super.getTeam();
    }

    public boolean isCharging() {
        return this.entityAICharge == null ? false : this.entityAICharge.isCharging();
    }

    protected EntityArrow getArrow(float p_190726_1_) {
        EntityArrow entityarrow = super.getArrow(p_190726_1_);

        if (entityarrow instanceof EntityTippedArrow) {
            if (this.getOwner() instanceof EntitySkeletonKing) {
                ((EntityTippedArrow) entityarrow).addEffect(new PotionEffect(ModMobEffects.FRAGILE_KING, 10 * 20, 2));
            } else {
                ((EntityTippedArrow) entityarrow).addEffect(new PotionEffect(ModMobEffects.FRAGILE, 10 * 20, 2));
            }
        }

        return entityarrow;
    }

    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }

    public boolean isTamed() {
        return (((Byte) this.dataManager.get(TAMED)).byteValue() & 4) != 0;
    }

    public void setTamed(boolean tamed) {
        byte b0 = ((Byte) this.dataManager.get(TAMED)).byteValue();

        if (tamed) {
            this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 | 4)));
        } else {
            this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 & -5)));
        }

        this.setupTamedAI();
    }

    protected void setupTamedAI() {
    }

    @Nullable
    public UUID getOwnerId() {
        return this.dataManager.get(OWNER_UNIQUE_ID).orNull();
    }

    public void setOwnerId(@Nullable UUID p_184754_1_) {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
    }

    @Nullable
    public EntityLivingBase getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : SpawnUtil.getEntityByUniqueId(uuid, this.getEntityWorld());
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public boolean isOwner(EntityLivingBase entityIn) {
        return entityIn == this.getOwner();
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setSkin(compound.getInteger("Variant"));
        String s;

        if (compound.hasKey("OwnerUUID", 8)) {
            s = compound.getString("OwnerUUID");
        } else {
            String s1 = compound.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
        }

        if (!s.isEmpty()) {
            try {
                this.setOwnerId(UUID.fromString(s));
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", getSkin());

        if (this.getOwnerId() == null) {
            compound.setString("OwnerUUID", "");
        } else {
            compound.setString("OwnerUUID", this.getOwnerId().toString());
        }
    }
}
