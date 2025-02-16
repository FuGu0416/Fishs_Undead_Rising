package com.Fishmod.mod_LavaCow.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.floating.EntityGraveRobberGhost;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGraveRobber extends AbstractIllager {
    public int tradeTimer = 0;
    private EntityAIAvoidEntity<EntityPlayer> avoid;
    private boolean isAvoiding;
    
    public EntityGraveRobber(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.95F);
        this.setCanPickUpLoot(true);
        this.isAvoiding = false;
    }

    @Override
    protected void initEntityAI() {
    	this.avoid = new EntityAIAvoidEntity<>(this, EntityPlayer.class, new Predicate<Entity>() {
            // TODO: Baubles support
            //boolean noseInCurios = ModList.get().isLoaded("curios") && (CurioIntegration.findItem(FURItemRegistry.ILLAGER_NOSE, p_210136_0_) != ItemStack.EMPTY);

            @Override
            public boolean apply(Entity input) {
            	EntityPlayer target = (EntityPlayer) input;
                return !(target.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(FishItems.ILLAGER_NOSE));
            }
        }, 4.5F, 1.0D, 1.2D);
    	
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(2, new EntityAITrade(this));       
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.6D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[]{EntityGraveRobber.class}));
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

    @Override
    protected boolean canEquipItem(ItemStack stack) {
        return stack.getItem() == Items.EMERALD && !this.isAggressive() && this.getHeldItemOffhand().isEmpty();
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.tradeTimer = (compound.getInteger("TradeTimer"));
        this.setCanPickUpLoot(true);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("TradeTimer", this.tradeTimer);
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
        ((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
        this.setEquipmentBasedOnDifficulty(difficulty);
        this.setEnchantmentBasedOnDifficulty(difficulty);
        return ientitylivingdata;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (!Modconfig.Grave_Robber_Spawn_Underground) {
            return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere() && this.world.canSeeSky(new BlockPos(this));
        }

        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
    }
    
    /**
     * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
     * better.
     */
    protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
    	super.updateEquipmentIfNeeded(itemEntity);
    	
    	ItemStack itemstack = itemEntity.getItem();
    	
    	if (itemEntity.getThrower() != null) {
    		EntityPlayer owner = this.world.getPlayerEntityByName(itemEntity.getThrower());
	    	if (this.canEquipItem(itemstack) && owner != null && owner.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(FishItems.ILLAGER_NOSE)) {
	            this.onItemPickup(itemEntity, itemstack.getCount());
	            this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, itemstack);
	            itemEntity.setDead();
	        }
    	}
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        this.setAggressive(this.getAttackTarget() != null);     
        
        if (this.getAttackTarget() == null) {
        	if (!this.isAvoiding) {
	        	this.targetTasks.addTask(2, this.avoid);
	        	this.isAvoiding = true;
        	}
        } else if (this.isAvoiding) {
        	this.targetTasks.removeTask(this.avoid);
        	this.isAvoiding = false;
        }        
    }   

    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        switch (id) {
            case 4:
                this.tradeTimer = 60;
                break;
            default:
                super.handleStatusUpdate(id);
                break;
        }
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

    static class EntityAITrade extends EntityAIBase {
        private final EntityGraveRobber mob;

        public EntityAITrade(EntityGraveRobber entity) {
            this.mob = entity;
            this.setMutexBits(1);
        }

        private List<ItemStack> getItemStacks(EntityGraveRobber mob) {
        	ResourceLocation resourcelocation = LootTableHandler.TRADE_LOOT;
        	
        	if (resourcelocation != null) {
	            LootTable loottable = mob.world.getLootTableManager().getLootTableFromLocation(resourcelocation);
	            LootContext.Builder lootcontext$builder = (new LootContext.Builder((WorldServer) this.mob.world)).withLootedEntity(this.mob);	 	            
	            return loottable.generateLootForPools(new Random(), lootcontext$builder.build());
        	} else {
        		// Should not happened.
        		List<ItemStack> emtpy = new ArrayList<ItemStack>();
        		emtpy.add(new ItemStack(Items.EMERALD));       		
        		return emtpy;
        	}
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean shouldExecute() {
            return this.mob.tradeTimer <= 0 && !this.mob.getHeldItemOffhand().isEmpty() && !this.mob.isAggressive();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean shouldContinueExecuting() {
            return this.mob.tradeTimer > 0 && !this.mob.getHeldItemOffhand().isEmpty() && !this.mob.isAggressive();
        }

        @Override
        public void startExecuting() {
            this.mob.tradeTimer = 60;
            this.mob.world.setEntityState(this.mob, (byte) 4);
            this.mob.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15F);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void updateTask() {
            if (this.mob.tradeTimer > 0) {
                this.mob.tradeTimer--;
            }
        }

        @Override
        public void resetTask() {
            this.mob.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35F);
            this.mob.tradeTimer = 0;

            if (!this.mob.isAggressive()) {
                List<ItemStack> lootList = getItemStacks(this.mob);
                if (lootList.size() > 0) {
                    ItemStack loot = lootList.remove(0).copy();

                    if (!this.mob.world.isRemote) {
                        this.mob.entityDropItem(loot, 1.5F);
                    }
                }
            } else {
                if (!this.mob.world.isRemote) {
                    this.mob.entityDropItem(this.mob.getHeldItemOffhand().copy(), 1.5F);
                }
            }

            ItemStack stack = this.mob.getHeldItemOffhand().copy();
            stack.setCount(stack.getCount() - 1);
            this.mob.setHeldItem(EnumHand.OFF_HAND, stack);
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
