package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIPickupMeat;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIAttackMelee;
import com.Fishmod.mod_LavaCow.entities.ai.EntityFishAIBreakDoor;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySummonedZombie;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGhoul extends EntityMob implements IAggressive {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityGhoul.class, DataSerializers.VARINT);
    public static final int ATTACK_TIMER = 12;
    private boolean isAggressive = false;
    private int attackTimer = 0;

    public EntityGhoul(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.2F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityFishAIBreakDoor(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        if (!Modconfig.SunScreen_Mode) this.tasks.addTask(5, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(4, new EntityGhoul.AIGhoulAttack(this, 1.3D, true));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 0, true, true, new Predicate<Entity>() {
            public boolean apply(@Nullable Entity input) {
                return (input instanceof EntityLivingBase && !(input instanceof EntityCreeper))
                        && ((EntityLivingBase) input).attackable()
                        && ((EntityLivingBase) input).getHealth() <= ((EntityLivingBase) input).getMaxHealth() * ((float) Modconfig.Ghoul_Target_Health_Threshold / 100.0F)
                        && !(((EntityLivingBase) input).getCreatureAttribute().equals(EnumCreatureAttribute.UNDEAD));
            }
        }));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
        this.targetTasks.addTask(4, new EntityAIPickupMeat<>(this, EntityItem.class, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Ghoul_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Ghoul_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
    }

    @Override
    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
    }

    @Override
    public double getMountedYOffset() {
        return -0.25D;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    @Override
    public void onUpdate() {
        if (this.getAttackTimer() > 0) {
            --this.attackTimer;
        }

        if (!Modconfig.SunScreen_Mode && this.world.isDaytime() && !this.world.isRemote) {
            float f = this.getBrightness();

            if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ))) {
                boolean flag = true;
                ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if (!itemstack.isEmpty()) {
                    if (itemstack.isItemStackDamageable()) {
                        itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));

                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
                            this.renderBrokenItemStack(itemstack);
                            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag) {
                    this.setFire(8);
                }
            }
        }

        super.onUpdate();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag) {
            float f = this.world.getDifficultyForLocation(this.getPosition()).getAdditionalDifficulty();

            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < f * 0.3F) {
                entityIn.setFire(2 * (int) f);
            }

            if (entityIn instanceof EntityLivingBase
                    && ((EntityLivingBase) entityIn).attackable()
                    && ((EntityLivingBase) entityIn).getHealth() <= ((EntityLivingBase) entityIn).getMaxHealth() * ((float) Modconfig.Ghoul_Target_Health_Threshold / 100.0F)) {
                this.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 4 * 20, 2));
            }

            if (entityIn instanceof EntityLivingBase && !(((EntityLivingBase) entityIn).deathTime > 0)) {
                this.heal(2.0F);
            }
        }

        return flag;
    }

    @Override
    public boolean isOnSameTeam(Entity entity) {
        if (entity == null) {
            return false;
        } else if (entity == this) {
            return true;
        } else if (super.isOnSameTeam(entity)) {
            return true;
        } else if (entity instanceof EntityGhoul || entity instanceof EntitySummonedZombie) {
            return this.getTeam() == null && entity.getTeam() == null;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Ghoul_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Ghoul_Attack);
        this.setHealth(this.getMaxHealth());

        if (BiomeDictionary.hasType(this.getEntityWorld().getBiome(this.getPosition()), Type.COLD))
            // Variant 2, 4
            this.setSkin(2 + this.getRNG().nextInt(1) * 2);
        else if (BiomeDictionary.hasType(this.getEntityWorld().getBiome(this.getPosition()), Type.DENSE))
            // Variant 1, 3
            this.setSkin(1 + this.getRNG().nextInt(1) * 2);
        else {
            // Variant 0, 1
            this.setSkin(this.getRNG().nextInt(2));
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
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        switch (id) {
            case 5:
                this.setAttackTimer(ATTACK_TIMER);
                break;
            default:
                super.handleStatusUpdate(id);
                break;
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

    @Override
    public boolean isAggressive() {
        return isAggressive;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.8F;
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
    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_GHOUL_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_GHOUL_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_GHOUL_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    @Override
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

    static class AIGhoulAttack extends EntityFishAIAttackMelee {
        public AIGhoulAttack(EntityCreature entity, double speedIn, boolean useLongMemory) {
            super(entity, speedIn, useLongMemory);
        }

        protected int atkTimerMax() {
            return ATTACK_TIMER;
        }

        protected int atkTimerHit() {
            return 5;
        }

        protected byte atkTimerEvent() {
            return (byte) 5;
        }
    }
}
