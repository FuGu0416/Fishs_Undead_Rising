package com.Fishmod.mod_LavaCow.entities;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.ai.EntityChargeAttackGoal;
import com.Fishmod.mod_LavaCow.entities.projectiles.DeathCoilEntity;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ForsakenEntity extends AbstractSkeletonEntity {
	private final EntityChargeAttackGoal entityAICharge = new EntityChargeAttackGoal(this);
	private final RangedAttackGoal staffGoal = new RangedAttackGoal(this, 1.25D, 40, 20.0F) {
		public void stop() {
			super.stop();
			ForsakenEntity.this.setAggressive(false);
		}

		public void start() {
			super.start();
			ForsakenEntity.this.setAggressive(true);
		}
	};
	
	public ForsakenEntity(EntityType<? extends ForsakenEntity> p_i48555_1_, World worldIn) {
		super(p_i48555_1_, worldIn);
	}

    public static boolean checkForsakenSpawnRules(EntityType<? extends ForsakenEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return MonsterEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
    }
    
    @Override
    public void push(Entity entityIn) {
    	super.push(entityIn);
		if(this.entityAICharge != null && this.isCharging()) {
			this.doHurtTarget(entityIn);
			entityIn.setDeltaMovement(entityIn.getDeltaMovement().add(this.getLookAngle().normalize().multiply(0.8D, 1.6D, 0.8D)));
		}
    }
    
    @Override
    protected boolean isSunBurnTick() {
    	return false;
    }
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return AbstractSkeletonEntity.createAttributes()
        		.add(Attributes.MAX_HEALTH, FURConfig.Forsaken_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Forsaken_Attack.get());
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.SKELETON_DEATH;
    }

    @Override
    protected SoundEvent getStepSound()
    {
        return SoundEvents.SKELETON_STEP;
    }
 
    @Override
    protected void dropCustomDeathLoot(DamageSource p_213333_1_, int p_213333_2_, boolean p_213333_3_) {
        super.dropCustomDeathLoot(p_213333_1_, p_213333_2_, p_213333_3_);
        Entity entity = p_213333_1_.getEntity();
        if (entity instanceof CreeperEntity) {
           CreeperEntity creeperentity = (CreeperEntity)entity;
           if (creeperentity.canDropMobsSkull()) {
              creeperentity.increaseDroppedSkulls();
              this.spawnAtLocation(Items.SKELETON_SKULL);
           }
        }
	}
    
	/**
	 * Create a compound tag for the specified pattern and colour.
	 *
	 * @param pattern The pattern
	 * @param color   The colour
	 * @return The compound tag
	 */
	protected CompoundNBT createPatternTag(BannerPattern pattern, DyeColor color) {
		CompoundNBT tag = new CompoundNBT();
		tag.putString("Pattern", pattern.getHashname());
		tag.putInt("Color", color.getId());
		return tag;
	}
    
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance p_180481_1_) {

    }
	
	@Override
	public void reassessWeaponGoal() {
		if (this.level != null && !this.level.isClientSide) {
			this.goalSelector.removeGoal(this.staffGoal);
			ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.MAINHAND);
			if (itemstack.getItem() == FURItemRegistry.FORSAKEN_STAFF) {
				this.goalSelector.addGoal(4, this.staffGoal);
			} else {
				super.reassessWeaponGoal();				
				if (this.getOffhandItem().getItem().equals(Items.SHIELD)) {
					this.goalSelector.addGoal(2, this.entityAICharge);
				}
			}
		}
	}
	
	@Override
	public void performRangedAttack(LivingEntity p_82196_1_, float p_82196_2_) {
		ItemStack itemstack = this.getItemBySlot(EquipmentSlotType.MAINHAND);
		if (itemstack.getItem() == FURItemRegistry.FORSAKEN_STAFF) {
			DeathCoilEntity entitysnowball = new DeathCoilEntity(this.level, this, 0.0D, 0.0D, 0.0D);
			entitysnowball.setPos(this.getX() - (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)MathHelper.sin(this.yBodyRot * ((float)Math.PI / 180F)), this.getEyeY() - (double)0.1F, this.getZ() + (double)(this.getBbWidth() + 1.0F) * 0.5D * (double)MathHelper.cos(this.yBodyRot * ((float)Math.PI / 180F)));
			double d0 = p_82196_1_.getX() - this.getX();
			double d1 = p_82196_1_.getY(0.3333333333333333D) - entitysnowball.getY();
			double d2 = p_82196_1_.getZ() - this.getZ();
			float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;		
			entitysnowball.shoot(d0, d1 + (double)f, d2, 1.5F, 10.0F);
			if (!this.isSilent()) {
				this.level.playSound((PlayerEntity)null, this.getX(), this.getY(), this.getZ(), SoundEvents.BLAZE_SHOOT, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
			}

			this.level.addFreshEntity(entitysnowball);
		} else {
			super.performRangedAttack(p_82196_1_, p_82196_2_);
		}
	}
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Override
    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
    	ILivingEntityData ilivingentitydata = super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
        ItemStack shield = new ItemStack(Items.SHIELD);
        ListNBT patternsList = new ListNBT();
        patternsList.add(createPatternTag(FURItemRegistry.PATTERN_SKELETONKING, DyeColor.WHITE));
		shield.getOrCreateTagElement("BlockEntityTag").put("Patterns", patternsList);		
        shield.getOrCreateTagElement("BlockEntityTag").putInt("Base", DyeColor.BLACK.getId());    
 
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Forsaken_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Forsaken_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
        switch(this.getRandom().nextInt(4)) {
        	case 0:     		
        		this.setItemSlot(EquipmentSlotType.OFFHAND, shield);
                this.getAttribute(Attributes.ARMOR).setBaseValue(4.0D);
                this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4D);
                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.22D);
                this.getOffhandItem().setDamageValue(this.getRandom().nextInt(this.getOffhandItem().getMaxDamage()));
        	case 1:
        		this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));      		
        		break;
        	case 2:
        	default:
        		this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.BOW));
        		break;     	
        	case 3:
        		this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(FURItemRegistry.FORSAKEN_STAFF));
        		break;
        }
        this.getMainHandItem().setDamageValue(this.getRandom().nextInt(this.getMainHandItem().getMaxDamage()));
        this.populateDefaultEquipmentEnchantments(difficulty);
        this.reassessWeaponGoal();
                
        return ilivingentitydata;
    }
    
    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if (!super.doHurtTarget(entityIn)) {
           return false;
        } else {
           if (entityIn instanceof LivingEntity) {
              ((LivingEntity)entityIn).addEffect(new EffectInstance(FUREffectRegistry.FRAGILE, 200, 2));
           }

           return true;
        }
	}
    
	/**
	* Called when the entity is attacked.
	*/
    @Override
	public boolean hurt(DamageSource source, float amount) {
    	return super.hurt(source, amount);
    }
    
    public boolean isCharging() {
    	return this.entityAICharge == null ? false : this.entityAICharge.isCharging();
    }
    
    @Override
    protected AbstractArrowEntity getArrow(ItemStack p_213624_1_, float p_213624_2_) {
        AbstractArrowEntity abstractarrowentity = super.getArrow(p_213624_1_, p_213624_2_);
        if (abstractarrowentity instanceof ArrowEntity) {
           ((ArrowEntity)abstractarrowentity).addEffect(new EffectInstance(FUREffectRegistry.FRAGILE, 200, 2));
        }

        return abstractarrowentity;
	}
}
