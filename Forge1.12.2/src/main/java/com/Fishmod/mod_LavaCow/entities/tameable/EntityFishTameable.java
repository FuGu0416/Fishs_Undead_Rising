package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.item.ItemNetherStew;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityFishTameable extends EntityTameable {
    protected static final DataParameter<Float> DATA_HEALTH = EntityDataManager.createKey(EntityFishTameable.class, DataSerializers.FLOAT);
    protected EntityFishTameable.State state;
    protected EntityAIBase wander;
    protected EntityAIBase follow;

    public EntityFishTameable(World worldIn) {
        super(worldIn);
        this.setTamed(false);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(DATA_HEALTH, this.getHealth());
        this.state = EntityFishTameable.State.WANDERING;
    }

    protected void initEntityAI() {
        this.wander = this.wanderGoal();
        this.follow = this.followGoal();
        this.aiSit = new EntityAISit(this);

        this.tasks.addTask(7, this.wander);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel() {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.world.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32)) {
            return false;
        } else {
            int i = this.world.getLightFromNeighbors(blockpos);

            if (this.world.isThundering()) {
                int j = this.world.getSkylightSubtracted();
                this.world.setSkylightSubtracted(10);
                i = this.world.getLightFromNeighbors(blockpos);
                this.world.setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }

    public float getBlockPathWeight(BlockPos pos) {
        return 10.0F - this.world.getLightBrightness(pos);
    }

    public boolean getCanSpawnHere() {
        IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
        return this.isValidLightLevel()
                && this.world.getDifficulty() != EnumDifficulty.PEACEFUL
                && this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) >= 0.0F
                && iblockstate.canEntitySpawn(this);
    }

    /**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn() {
        return !(this.isTamed() && this.getOwner() instanceof EntityPlayer);
    }

    protected boolean isCommandable() {
        return true;
    }

    protected boolean canSitCondition() {
        return true;
    }

    protected void doSitCommand(EntityPlayer playerIn) {
        byte b0 = ((Byte) this.dataManager.get(TAMED)).byteValue();

        this.tasks.removeTask(this.wander);
        this.isJumping = false;
        this.navigator.clearPath();
        this.state = EntityFishTameable.State.SITTING;
        this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 | 1)));
        if (playerIn != null)
            playerIn.sendStatusMessage(new TextComponentTranslation(this.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.sitting")), true);
    }

    protected void doFollowCommand(EntityPlayer playerIn) {
        byte b0 = ((Byte) this.dataManager.get(TAMED)).byteValue();

        this.follow = new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F);
        this.tasks.addTask(6, this.follow);
        this.navigator.clearPath();
        this.state = EntityFishTameable.State.FOLLOWING;
        this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 & -2)));
        if (playerIn != null)
            playerIn.sendStatusMessage(new TextComponentTranslation(this.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.following")), true);
    }

    protected void doWanderCommand(EntityPlayer playerIn) {
        byte b0 = ((Byte) this.dataManager.get(TAMED)).byteValue();

        this.tasks.removeTask(this.follow);
        this.wander = new EntityAIWanderAvoidWater(this, 1.0D, 0.0F);
        this.tasks.addTask(7, this.wander);
        this.navigator.clearPath();
        this.state = EntityFishTameable.State.WANDERING;
        this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 & -2)));
        if (playerIn != null)
            playerIn.sendStatusMessage(new TextComponentTranslation(this.getName()).appendSibling(new TextComponentTranslation("command.mod_lavacow.wandering")), true);
    }

    protected EntityAIBase wanderGoal() {
        return new EntityAIWanderAvoidWater(this, 1.0D, 0.0F);
    }

    protected EntityAIBase followGoal() {
        return new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F);
    }

    protected int TameRate(ItemStack stack) {
        return 3;
    }

    public boolean isWandering() {
        return this.state.equals(EntityFishTameable.State.WANDERING);
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isServerWorld()) {
            if (this.isBreedingItem(itemstack) && canTameCondition()) {
                if (!player.capabilities.isCreativeMode) {
                    if (itemstack.getItem() instanceof ItemBucket) {
                        itemstack.shrink(1);
                        player.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    } else if (itemstack.getItem() instanceof ItemNetherStew) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.setHeldItem(hand, new ItemStack(Items.BOWL));
                        } else if (!player.inventory.add(1, new ItemStack(Items.BOWL)) && !player.world.isRemote) {
                            player.entityDropItem(new ItemStack(Items.BOWL), 0.0F);
                        }
                    } else {
                        itemstack.shrink(1);
                    }
                }

                if (this.rand.nextInt(this.TameRate(itemstack)) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((EntityLivingBase) null);
                    this.setHealth(this.getMaxHealth());
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.world.setEntityState(this, (byte) 6);
                }

                return true;
            } else if (this.isTamed() && this.isOwner(player) && this.isCommandable() && this.getActiveHand().equals(hand)) {
                if (!this.isBreedingItem(itemstack) && this.getPassengers().isEmpty()) {
                    if (this.state.equals(EntityFishTameable.State.WANDERING)) {
                        if (this.canSitCondition()) {
                            this.doSitCommand(player);
                        }
                    } else if (this.state.equals(EntityFishTameable.State.SITTING)) {
                        this.doFollowCommand(player);
                    } else if (this.state.equals(EntityFishTameable.State.FOLLOWING)) {
                        this.doWanderCommand(player);
                    }

                    return true;
                }
            }
        }

        return super.processInteract(player, hand);
    }

    protected boolean canTameCondition() {
        return !this.isTamed() || (this.isTamed() && this.getOwner() == null);
    }

    @Override
    public void setTamedBy(EntityPlayer player) {
        if (this.isCommandable()) {
            this.doFollowCommand(player);
        }

        super.setTamedBy(player);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote && !this.isTamed() && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
        
        /*if (!this.world.isRemote && Modconfig.Suicidal_Minion && (this.getOwner() != null && (!(this.getOwner() instanceof EntityPlayer) && !this.getOwner().isEntityAlive()))) {
        	this.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageIsAbsolute().setDamageBypassesArmor() , this.getMaxHealth());
        }*/
    }

    protected void updateAITasks() {
        super.updateAITasks();
        this.dataManager.set(DATA_HEALTH, this.getHealth());
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isEntityInvulnerable(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();

            if (this.aiSit != null) {
                this.aiSit.setSitting(false);
            }

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            // When summoned, don't damage summoner or other minions 
            if (entity != null && entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isOnSameTeam(this)) {
                return false;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    @Nullable
    public EntityLivingBase getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : SpawnUtil.getEntityByUniqueId(uuid, this.getEntityWorld());
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Override
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return null;
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

    /**
     * Writes the extra NBT data specific to this type of entity. Should <em>not</em> be called from outside this class;
     * use {@link #writeUnlessPassenger} or {@link #writeWithoutTypeId} instead.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if (this.state.equals(EntityFishTameable.State.WANDERING)) {
            compound.setByte("state", (byte) 0);
        } else if (this.state.equals(EntityFishTameable.State.SITTING)) {
            compound.setByte("state", (byte) 1);
        } else if (this.state.equals(EntityFishTameable.State.FOLLOWING)) {
            compound.setByte("state", (byte) 2);
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        switch (compound.getByte("state")) {
            case (byte) 0:
                this.state = EntityFishTameable.State.WANDERING;
                this.doWanderCommand(null);
                break;
            case (byte) 1:
                this.state = EntityFishTameable.State.SITTING;
                this.doSitCommand(null);
                break;
            case (byte) 2:
                this.state = EntityFishTameable.State.FOLLOWING;
                this.doFollowCommand(null);
                break;
            default:
                break;
        }
    }

    public boolean isPreventingPlayerRest(EntityPlayer playerIn) {
        return !this.isTamed();
    }

    static enum State {
        SITTING,
        WANDERING,
        FOLLOWING;
    }
}
