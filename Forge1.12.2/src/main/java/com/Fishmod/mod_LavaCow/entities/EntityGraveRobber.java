package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.floating.EntityGraveRobberGhost;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGraveRobber extends AbstractIllager {
    public EntityGraveRobber(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(8, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityGraveRobber.class}));
        if (this.getAttackTarget() == null)
            this.targetTasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 4.5F, 1.0D, 1.2D));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityVillager.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityIronGolem.class, true));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Grave_Robber_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Grave_Robber_Attack);
    }

    @SideOnly(Side.CLIENT)
    public boolean isAggressive() {
        return this.isAggressive(1);
    }

    public void setAggressive(boolean p_190636_1_) {
        this.setAggressive(1, p_190636_1_);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AbstractIllager.IllagerArmPose getArmPose() {
        return this.isAggressive() ? AbstractIllager.IllagerArmPose.ATTACKING : AbstractIllager.IllagerArmPose.CROSSED;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        return ientitylivingdata;
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        this.setAggressive(this.getAttackTarget() != null);
    }

    /**
     * Returns whether this Entity is on the same team as the given Entity.
     */
    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ILLUSION_ILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ILLUSION_ILLAGER_HURT;
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.GRAVE_ROBBER;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);

        if (this.world.rand.nextDouble() <= Modconfig.Grave_Robber_Ghost_Chance && !this.world.isRemote) {
            EntityGraveRobberGhost entity = new EntityGraveRobberGhost(this.world);

            entity.setAttackTarget((EntityLivingBase) cause.getTrueSource());
            entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.0F, 1.0F);

            this.world.spawnEntity(entity);

            if (entity != null && cause.getTrueSource() != null && cause.getTrueSource() instanceof EntityLivingBase) {
                if (!(cause.getTrueSource() instanceof EntityPlayer)) {
                    entity.setAttackTarget((EntityLivingBase) cause.getTrueSource());
                }
            }
        }
    }
}
