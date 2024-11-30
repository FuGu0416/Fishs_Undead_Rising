package com.Fishmod.mod_LavaCow.entities;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIChargeAttack;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUndeadSwine extends EntityMob {
    private int sheepTimer;
    private int attackTimer;
    private EntityAIEatGrass entityAIEatGrass;
    private AIChargeAttack entityAICharge;
    private final List<Block> DIGOUT_SHROOM = Lists.newArrayList(
            Modblocks.GLOWSHROOM,
            Modblocks.CORDY_SHROOM,
            Modblocks.VEIL_SHROOM,
            Blocks.BROWN_MUSHROOM,
            Blocks.RED_MUSHROOM
    );

    public EntityUndeadSwine(World worldIn) {
        super(worldIn);
        this.setSize(1.6F, 1.8F);
        this.experienceValue = 20;
    }

    @Override
    protected void initEntityAI() {
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.entityAICharge = new EntityUndeadSwine.AIChargeAttack(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, this.entityAICharge);
        this.tasks.addTask(4, this.entityAIEatGrass);
        if (!Modconfig.SunScreen_Mode) this.tasks.addTask(4, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityUndeadSwine.AISwineAttack(this));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }

    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.UndeadSwine_Health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.UndeadSwine_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }

    @Override
    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
        super.applyEntityCollision(entityIn);

        if (entityIn instanceof EntityLivingBase) {
            if (this.entityAICharge != null && this.entityAICharge.isCharging() && !((EntityLivingBase) entityIn).isOnSameTeam(this)) {
                this.attackEntityAsMob(entityIn);

                ((EntityLivingBase) entityIn).knockBack(entityIn, 2.0F * 0.5F, (double) MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180.0F)), (double) (-MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180.0F))));
            }
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onLivingUpdate() {
        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        if (this.attackTimer > 0) {
            --this.attackTimer;
        }

        if (!Modconfig.SunScreen_Mode && this.world.isDaytime() && !this.world.isRemote) {
            float f = this.getBrightness();
            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ)))
                this.setFire(40);
        }

        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);

        if (flag) {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
                entityIn.setFire(2 * (int) f);
            }

            if (entityIn instanceof EntityLivingBase) {
                if (this.entityAICharge != null && this.entityAICharge.isCharging() && !((EntityLivingBase) entityIn).isOnSameTeam(this)) {
                    this.attackEntityAsMob(entityIn);

                    ((EntityLivingBase) entityIn).knockBack(entityIn, 2.0F * 0.5F, (double) MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180.0F)), (double) (-MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180.0F))));
                }
            }
        }

        return flag;
    }

    @SideOnly(Side.CLIENT)
    public boolean isDigging() {
        return this.sheepTimer > 0;
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTimer = 10;
        } else if (id == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);

        // Rider gets damaged during charge attack
        /*if (this.world.rand.nextInt(100) == 0) {
            EntitySkeleton entityRider = new EntitySkeleton(this.world);
            entityRider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entityRider.onInitialSpawn(difficulty, (IEntityLivingData) null);
            this.world.spawnEntity(entityRider);
            entityRider.startRiding(this);
        } else if (this.world.rand.nextInt(100) == 1) {
            EntityZombie entityRider = new EntityZombie(this.world);
            entityRider.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entityRider.onInitialSpawn(difficulty, (IEntityLivingData) null);
            this.world.spawnEntity(entityRider);
            entityRider.startRiding(this, true);
        }*/

        return livingdata;
    }

    static class AIChargeAttack extends EntityAIChargeAttack {
        public AIChargeAttack(EntityUndeadSwine entity) {
            super(entity);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void startExecuting() {
            super.startExecuting();
            this.charger.playSound(FishItems.ENTITY_UNDEADSWINE_CHARGE, 1.0F, 1.0F);
        }

    }

    static class AISwineAttack extends EntityAIAttackMelee {
        public AISwineAttack(EntityUndeadSwine entity) {
            super(entity, 1.0D, true);
        }

        @Override
        protected double getAttackReachSqr(EntityLivingBase attackTarget) {
            return (double) (this.attacker.width * this.attacker.width + attackTarget.width);
        }
    }

    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This function
     * is used in the AIEatGrass)
     */
    @Override
    public void eatGrassBonus() {
        this.dropItem(new ItemStack(DIGOUT_SHROOM.get(this.rand.nextInt(DIGOUT_SHROOM.size()))).getItem(), 1);
    }

    @Override
    public boolean isOnSameTeam(Entity entity) {
        if (entity == null) {
            return false;
        } else if (entity == this) {
            return true;
        } else if (super.isOnSameTeam(entity)) {
            return true;
        } else if (entity instanceof EntityUndeadSwine) {
            return this.getTeam() == null && entity.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.6F;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_UNDEADSWINE_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_UNDEADSWINE_DEATH;
    }

    @Override
    @Nullable
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.UNDEADSWINE;
    }

    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean canDropLoot() {
        return !this.isBurning() || this.recentlyHit != 0;
    }
}
