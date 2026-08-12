package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityAcidJet;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBoneWorm extends EntityMob implements IRangedAttackMob {
    private static final DataParameter<Boolean> UNDERGROUND = EntityDataManager.createKey(EntityBoneWorm.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityBoneWorm.class, DataSerializers.VARINT);
    /** 0.0-3.5ish: how deep the worm has burrowed. Server-authoritative only (only ever written
     *  from {@link #onUpdate()}'s {@code isServerWorld()} branch); synced so a freshly (re)tracked
     *  client starts from the real current value instead of 0 - see the 2026-08-12 fix note above
     *  {@link #attackEntityFrom}. */
    private static final DataParameter<Float> LOCATION_FIX = EntityDataManager.<Float>createKey(EntityBoneWorm.class, DataSerializers.FLOAT);
    private boolean isAggressive = false;
    public int attackTimer[] = {0, 0};
    public int diggingTimer[] = {0, 0};
    protected EntityAIAttackRanged range_atk;
    protected EntityAIAvoidEntity<EntityPlayer> avoid_player;
    private int avoid_cooldown;

    public EntityBoneWorm(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 2.0F);
    }

    protected void initEntityAI() {
        this.range_atk = new BoneWormRangedAttackGoal(1.0D, 40, 60, 12.0F);
        this.avoid_player = new EntityAIAvoidEntity<EntityPlayer>(this, EntityPlayer.class, 10.0F, 1.0D, 1.2D);

        this.tasks.addTask(0, this.range_atk);
        if (!Modconfig.SunScreen_Mode) this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityBoneWorm.AIBoneWormTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.BoneWorm_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.BoneWorm_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(UNDERGROUND, false);
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
        this.getDataManager().register(LOCATION_FIX, Float.valueOf(0.0F));
    }

    @Override
    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    private boolean isWalking() {
        return Math.abs(this.posX - this.prevPosX) > 0.05D || Math.abs(this.posY - this.prevPosY) > 0.05D || Math.abs(this.posZ - this.prevPosZ) > 0.05D;
    }

    public boolean isDigging() {
        return this.diggingTimer[0] != 0 || this.diggingTimer[1] != 0;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        BlockPos belowPos = new BlockPos(this.posX, this.posY, this.posZ).down();
        IBlockState state = world.getBlockState(belowPos);
        int blockId = Block.getStateId(state);
        boolean walkingOnSolid = this.isWalking() && state.isOpaqueCube();

        if (this.isWalking()) {
            if (state.isOpaqueCube() && world.isRemote) {
                this.spawnDigParticles(blockId, 0.02D);
            }

            // Server-only: playSound() on the server already broadcasts to every tracking client,
            // so calling it here too (unguarded, as before) doubled the crunch sound for anyone in
            // range of their own worm.
            if (!this.world.isRemote && this.ticksExisted % 10 == 0) {
                this.playSound(FishItems.ENTITY_BONEWORM_BURROW, 0.25F, 0.5F);
            }
        }

        if (this.isServerWorld()) {
            float locationFix = this.getRawLocationFix();

            if (locationFix > 0 && !this.isUnderground() && !this.isDigging()) {
                this.extinguish();
                this.diggingTimer[0] = 30;
                this.setUnderground(true);
                this.world.setEntityState(this, (byte) 6);
                this.playSound(FishItems.ENTITY_BONEWORM_BURROW, 1.0F, 1.0F);
            } else if (locationFix <= 1.5D && this.isUnderground() && !this.isDigging()) {
                this.diggingTimer[1] = 20;
                this.setUnderground(false);
                this.world.setEntityState(this, (byte) 7);
                this.playSound(FishItems.ENTITY_BONEWORM_BURROW, 1.0F, 1.0F);
            }

            if (walkingOnSolid) {
                if (locationFix <= 3.5F)
                    this.setLocationFix(locationFix + 0.125F);
            } else if (locationFix > 0.0F && state.isOpaqueCube()) {
                this.setLocationFix(locationFix - 0.125F);
            }

            if (this.avoid_cooldown == 0) {
                this.tasks.addTask(0, this.range_atk);
                this.tasks.removeTask(this.avoid_player);
                this.avoid_cooldown = -1;
            }

            if (this.avoid_cooldown > 0)
                this.avoid_cooldown--;
        }

        // Both sides: keep the collision box in step with the (now-synced) dig depth. Unlike
        // 1.16.5/1.20.1's equivalent call (whose return value was discarded there, a no-op),
        // Entity#setSize in this version really does mutate width/height and recompute the AABB -
        // it's a functioning mechanic here, not dead code, so it's kept (just moved out of the
        // increment/decrement branches; setSize() already no-ops internally when the size hasn't
        // actually changed, so calling it unconditionally each tick doesn't change behaviour).
        this.setSize(this.width, Math.max(2.0F - (float) this.getLocationFix(), 0.5F));

        // Client-only: same particle burst as before, fired identically from both the increment
        // and decrement cases (this version duplicated it verbatim in both) - mirrored here off
        // the now-synced locationFix rather than a locally-computed one, so it can't disagree with
        // what the server is doing.
        if (world.isRemote && state.isOpaqueCube() && (walkingOnSolid || this.getLocationFix() > 0.0D)) {
            this.spawnDigParticles(blockId, 0.1D);
        }
    }

    private void spawnDigParticles(int blockId, double yJitter) {
        for (int i = 0; i < 4; i++)
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK,
                    this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width,
                    this.posY + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width,
                    this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width,
                    this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * yJitter, this.rand.nextGaussian() * 0.02D, blockId);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        for (int i = 0; i < 2; i++) {
            if (this.attackTimer[i] > 0) {
                --this.attackTimer[i];
            }

            if (this.diggingTimer[i] > 0) {
                --this.diggingTimer[i];
            }
        }

        // Server-only: spit() spawns a real projectile via spawnEntity and plays its launch sound -
        // both already reach every tracking client on their own (the spawn packet, and the
        // server's own playSound broadcast). Running this unguarded (as before) meant every shot's
        // launch also ran client-side, producing a second, purely-local, server-unauthoritative
        // "ghost" projectile alongside the real one, plus a doubled launch sound.
        if (!this.world.isRemote && this.getAttackTarget() != null && this.getEntitySenses().canSee(this.getAttackTarget())
                && this.getAttackTimer(0) == 7 && this.deathTime <= 0 && this.getLocationFix() == 0) {
            this.spit(this.getAttackTarget());
        }

        // Digging puts out fire
        if (this.isWalking()) this.extinguish();

        super.onLivingUpdate();
    }

    @Override
    public boolean isOnSameTeam(Entity entity) {
        if (entity == null) {
            return false;
        } else if (entity == this) {
            return true;
        } else if (super.isOnSameTeam(entity)) {
            return true;
        } else if (entity instanceof EntityBoneWorm || entity instanceof EntityForsaken || entity instanceof EntitySkeletonKing) {
            return this.getTeam() == null && entity.getTeam() == null;
        } else {
            return false;
        }
    }

    public double getLocationFix() {
        return this.getRawLocationFix();
    }

    private float getRawLocationFix() {
        return this.dataManager.get(LOCATION_FIX).floatValue();
    }

    private void setLocationFix(float value) {
        this.dataManager.set(LOCATION_FIX, Float.valueOf(value));
    }

    /**
     * True once the worm is far enough into its dig that the renderer stops drawing it at all -
     * the single source of truth for both {@link #attackEntityFrom} and {@code RenderBoneWorm}, so
     * the two can't drift apart the way they used to (the renderer never actually hid the model at
     * all, only zeroed the shadow past 3.0, while this used a completely separate 3.0 threshold of
     * its own for invulnerability - two independent, non-communicating checks).
     */
    public boolean isHidden() {
        return this.getRawLocationFix() >= 1.5F;
    }

    /**
     * Called when the entity is attacked. Untouchable while hidden, except for damage meant to
     * bypass invulnerability (/kill, void) - this version has no BYPASSES_INVULNERABILITY tag
     * system; OUT_OF_WORLD (which /kill also routes through here) is the direct equivalent, same
     * fix as the newer versions' SkeletonKingEntity got for the same bug class.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isHidden() && source != DamageSource.OUT_OF_WORLD) {
            return false;
        } else {
            return super.attackEntityFrom(source, amount);
        }
    }

    public void spit(EntityLivingBase target) {
        EntityThrowable throwable = new EntityAcidJet(this.world, this);
        SoundEvent sound = FishItems.ENTITY_BONEWORM_ATTACK;

        double d0 = target.posY + (double) target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX - this.posX;
        double d2 = d0 - throwable.posY;
        double d3 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        throwable.shoot(d1, d2 + (double) f, d3, 1.6F, 1.0F);
        this.playSound((SoundEvent) sound, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(throwable);

        if (target instanceof EntityPlayer)
            this.setRunning(100);
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        this.attackTimer[0] = 15;
        this.world.setEntityState(this, (byte) 4);
    }

    public void setRunning(int CD) {
        this.tasks.removeTask(this.range_atk);
        this.tasks.addTask(1, this.avoid_player);
        this.avoid_cooldown = CD;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag) {
            this.attackTimer[1] = 16;
            this.world.setEntityState(this, (byte) 5);
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.BoneWorm_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.BoneWorm_Attack);
        this.setHealth(this.getMaxHealth());

        return super.onInitialSpawn(difficulty, livingdata);
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
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);

        if (!world.isRemote) {
            if (cause.getTrueSource() instanceof EntityCreeper) {
                EntityCreeper entitycreeper = (EntityCreeper) cause.getTrueSource();

                // Skull
                if (!(this instanceof EntitySoulWorm) && entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop()) {
                    entitycreeper.incrementDroppedSkulls();
                    this.entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0.0F);
                } else if ((this instanceof EntitySoulWorm) && entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop()) {
                    entitycreeper.incrementDroppedSkulls();
                    this.entityDropItem(new ItemStack(Items.SKULL, 1, 1), 0.0F);
                }
            }
        }
    }

    @Override
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

    public boolean isAggressive() {
        return isAggressive;
    }

    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }

    public int getAttackTimer(int i) {
        return this.attackTimer[i];
    }

    public boolean isUnderground() {
        return this.dataManager.get(UNDERGROUND);
    }

    public void setUnderground(boolean i) {
        this.dataManager.set(UNDERGROUND, i);
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTimer[0] = 15;
        } else if (id == 5) {
            this.attackTimer[1] = 16;
        } else if (id == 6) {
            this.diggingTimer[0] = 30;
        } else if (id == 7) {
            this.diggingTimer[1] = 20;
        } else if (id == 11) {
            this.isAggressive = true;
        } else if (id == 34) {
            this.isAggressive = false;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    static class AIBoneWormTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
        public AIBoneWormTarget(EntityBoneWorm entity, Class<T> classTarget, boolean checkSight) {
            super(entity, classTarget, checkSight);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.8F;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_BONEWORM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_BONEWORM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_BONEWORM_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SPIDER_STEP;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
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
        return LootTableHandler.BONEWORM;
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
    }

    /** Vanilla EntityAIAttackRanged has no idea this entity can go {@link #isHidden()} - without
     *  this override it kept telegraphing (and wasting) attacks on its normal cooldown while fully
     *  submerged. See the {@link #attackEntityFrom}/{@link #isHidden} fix note. Package-visible
     *  (not private) so {@link EntitySoulWorm}, which builds its own goal list rather than calling
     *  {@code super.initEntityAI()}, can still use it. */
    class BoneWormRangedAttackGoal extends EntityAIAttackRanged {
        BoneWormRangedAttackGoal(double movespeed, int attackIntervalMin, int maxAttackTime, float maxAttackDistance) {
            super(EntityBoneWorm.this, movespeed, attackIntervalMin, maxAttackTime, maxAttackDistance);
        }

        @Override
        public boolean shouldExecute() {
            return !EntityBoneWorm.this.isHidden() && super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return !EntityBoneWorm.this.isHidden() && super.shouldContinueExecuting();
        }
    }
}
