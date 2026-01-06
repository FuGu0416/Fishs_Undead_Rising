package com.Fishmod.fur.entities.tameable;

import java.util.UUID;
import javax.annotation.Nullable;

import com.Fishmod.fur.client.model.MimicModel;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.IAggressive;
import com.Fishmod.fur.entities.ai.EntityAITargetItem;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Arrays;
import java.util.ArrayList;

public class MimicEntity extends FURTameableEntity implements IAggressive, GeoEntity {
	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("mimic.model.idle");
    private static final RawAnimation IDLE_HIDE = RawAnimation.begin().thenPlay("mimic.model.idle_hide");
    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("mimic.model.walking");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("mimic.model.attack");
    private static final RawAnimation HIDE_IN = RawAnimation.begin().thenPlay("mimic.model.hide_in");
    private static final RawAnimation HIDE_OUT = RawAnimation.begin().thenPlay("mimic.model.hide_out");
    private static final RawAnimation HIDE_PEEK = RawAnimation.begin().thenPlay("mimic.model.hide_peek");
    
	private static final EntityDataAccessor<Integer> SKIN_TYPE = SynchedEntityData.defineId(MimicEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> CHEST_TEXTURE = SynchedEntityData.defineId(MimicEntity.class, EntityDataSerializers.STRING);
    private static final MutableComponent CONTAINER_TITLE = Component.translatable("container.enderchest");
    public static ArrayList<String> TEXTURE_POOL = new ArrayList<String>(Arrays.asList(
            "textures/entity/chest/normal.png"
    ));

    public static final int ATTACK_TIMER = 15;
    public static final int IDLE_TIMER = 40;
    public static final int HIDE_IN_TIMER = 15;
    public static final int HIDE_OUT_TIMER = 21;
    private int AttackTimer, AggressiveTimer = 40;
    public float rotationAngle = 0.0F;
    private int IdleTimer, SitTimer;
	public SimpleContainer inventory;
    private EntityAITargetItem<ItemEntity> AITargetItem;
	
	public MimicEntity(EntityType<? extends MimicEntity> p_i48549_1_, Level worldIn) {
        super(p_i48549_1_, worldIn);
        this.inventory = new SimpleContainer(27);
        this.setCanPickUpLoot(true);
    }
	
    @Override
    protected void registerGoals() {   	
    	super.registerGoals();
    	this.goalSelector.addGoal(1, this.aiSit);
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)));
        this.AITargetItem = new EntityAITargetItem<>(this, ItemEntity.class, true);
        this.targetSelector.addGoal(4, this.AITargetItem);
    }
    
    @Override
	protected void defineSynchedData() {
		super.defineSynchedData();
        this.entityData.define(SKIN_TYPE, Integer.valueOf(0));
        this.entityData.define(CHEST_TEXTURE, MimicEntity.TEXTURE_POOL.get(this.getRandom().nextInt(MimicEntity.TEXTURE_POOL.size())));
	}
    
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.22D)
        		.add(Attributes.MAX_HEALTH, 10.0D/*FURConfig.Mimic_Health.get()*/)
        		.add(Attributes.ATTACK_DAMAGE, 8.0D/*FURConfig.Mimic_Attack.get()*/)
        		.add(Attributes.ARMOR, 20.0D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D);
    }
    
    @Override
    protected Goal followGoal() {
    	return new FollowOwnerGoal(this, 1.5D, 10.0F, 2.0F, false);
    }

	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
        return this.inventory.isEmpty() && super.removeWhenFarAway(p_213397_1_);
    }

    public static boolean checkMimicSpawnRules(EntityType<? extends MimicEntity> p_223316_0_, ServerLevelAccessor p_223316_1_, MobSpawnType p_223316_2_, BlockPos p_223316_3_, RandomSource p_223316_4_) { 	
    	return SpawnUtil.isNearBlock(p_223316_1_, Blocks.CHEST, p_223316_3_, 4) != null && FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);
    }
    
    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    @Override
    public int getMaxSpawnClusterSize() {
       return 1;
    }
    
    @Override
    public boolean canBreatheUnderwater() {
        return true;
	}
    
    @Override
    public boolean checkSpawnObstruction(LevelReader p_205019_1_) {
        return p_205019_1_.isUnobstructed(this);
    }
    
    @Override
    public void setTame(boolean tamed) {
    	super.setTame(tamed);
        float maxHealthO = this.getMaxHealth();
        
        if (tamed) {
        	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0D/*FURConfig.Mimic_Health.get()*/ * 3.0D);
        	this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        	this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D/*FURConfig.Mimic_Attack.get()*/ * 0.5D);
        	this.setSilent(false);
        } else {
        	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10.0D/*FURConfig.Mimic_Health.get()*/);
        	this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.22D);
        	this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0D/*FURConfig.Mimic_Attack.get()*/);
        }
        
        this.setHealth(this.getHealth() * (this.getMaxHealth() / maxHealthO));
	}

    private boolean canPickupItems() {
    	for (int i = 0; i < this.inventory.getContainerSize();i++) {
    		if (this.inventory.getItem(i).isEmpty()) {
    			return true;
    		}
    	}
        return false;
    }

    private void hasSpace(ItemStack itemstackIn) {
    	if (!this.level().isClientSide()) {
			for (int i = 0; i < this.inventory.getContainerSize();i++)
				if (this.inventory.getItem(i).isEmpty()) {
					this.inventory.setItem(i, itemstackIn.copy());
					itemstackIn.shrink(itemstackIn.getCount());
					return;
				}			
    	}
    }
    
    public int containsItem(Item itemIn) {
    	if (!this.level().isClientSide()) {
			for (int i = 0; i < this.inventory.getContainerSize();i++)
				if (this.inventory.getItem(i).getItem().equals(itemIn)) {
					return i;
				}			
    	}  
    	
    	return -1;
    }
    
    private void EmergencyFood() {
    	if (!this.level().isClientSide()) {
			for (int i = 0; i < this.inventory.getContainerSize();i++) {
				if (this.isFood(this.inventory.getItem(i))) {
					Item item = this.inventory.getItem(i).getItem();
					this.playSound(SoundEvents.GENERIC_EAT, 0.4F, 1.0F);
					this.heal((float)item.getFoodProperties().getNutrition());
                    this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 8*20, 0));
                    this.inventory.setItem(i, new ItemStack(this.inventory.getItem(i).getItem(), this.inventory.getItem(i).getCount() - 1));
				}
    		}
		}
    }
    
	public void setInSittingPose(boolean p_21838_) {
		super.setInSittingPose(p_21838_);

		if (p_21838_) {
			this.SitTimer = HIDE_IN_TIMER;
			this.level().broadcastEntityEvent(this, (byte)5);
		} else {
			this.SitTimer = HIDE_OUT_TIMER;
			this.level().broadcastEntityEvent(this, (byte)6);
		}
	}
	
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
	@Override
    public void tick() {
		super.tick();
		
		if (this.AggressiveTimer > 0) {
			this.AggressiveTimer--;
		}
		
    	if (this.AttackTimer > 0) {
    		this.AttackTimer--;
    	}

    	if (this.SitTimer > 0) {
    		this.SitTimer--;
    	}
    	
    	if (this.IdleTimer > 0) {
    		this.IdleTimer--;
    	}
    	
		if (!this.level().isClientSide()) {
			if (!this.isAggressive() && !this.isTame()) {
				if (!this.isSilent() || !this.isInSittingPose()) {
					this.setInSittingPose(true);
					this.level().broadcastEntityEvent(this, (byte)(41 + this.getRandom().nextInt(4)));
				}
			
				this.setPos(Math.floor(this.getX()) + 0.5D, Math.floor(this.getY()), Math.floor(this.getZ()) + 0.55D);
				this.setYRot(this.rotationAngle);
				this.yRotO = this.rotationAngle;
				this.yBodyRot = this.yBodyRotO = 0F;	
				
				if (this.level().getBlockState(this.blockPosition().below()).isAir()) {
					this.setPos(this.getX(), this.getY() - 1, this.getZ());
				}
	
				this.setSilent(true);
				this.setSpeed(0.0F);
			} else if (this.getTarget() != null) {
				this.AggressiveTimer = 200;
				this.setSilent(false);
				this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue());
			}
		}
		
		if (!this.isTame() && this.getTarget() != null && this.distanceTo(this.getTarget()) > this.getAttribute(Attributes.FOLLOW_RANGE).getValue()) {
			this.setTarget((LivingEntity)null);
		}
		
		for(ItemStack S: this.getHandSlots())
			if (!S.isEmpty())this.hasSpace(S);
		
		if (this.isTame() && this.getHealth() <= this.getMaxHealth() * 0.5F)
			this.EmergencyFood();
		
		if (this.getSkin() == MimicModel.getVoidSkin() && this.tickCount % 100 == 0) {
            for (int i = 0; i < 8; ++i) {
                int j = this.getRandom().nextInt(2) * 2 - 1;
                int k = this.getRandom().nextInt(2) * 2 - 1;
                double d0 = (double)this.getX() + 0.5D + 0.25D * (double)j;
                double d1 = (double)((float)this.getY() + this.getRandom().nextFloat());
                double d2 = (double)this.getZ() + 0.5D + 0.25D * (double)k;
                double d3 = (double)(this.getRandom().nextFloat() * (float)j);
                double d4 = ((double)this.getRandom().nextFloat() - 0.5D) * 0.125D;
                double d5 = (double)(this.getRandom().nextFloat() * (float)k);
                this.level().addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
            }
		}
		
		if (!this.isAggressive() && this.tickCount % 100 == 0 && this.getRandom().nextInt(5) == 0) {
			this.IdleTimer = IDLE_TIMER;
			this.level().broadcastEntityEvent(this, (byte)7);
		}
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
    	super.aiStep();
    }
	
    @Override
    public void travel(Vec3 p_213352_1_) {
		if (this.SitTimer > 0 || this.isInSittingPose()) {
            this.setDeltaMovement(Vec3.ZERO);
		} else
			super.travel(p_213352_1_);
	}
    
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
    	Entity entity = source.getDirectEntity();
    	this.setInSittingPose(false);
		this.AggressiveTimer = 200;
		this.setSilent(false);
		this.setSpeed(0.19F);
		
    	if (entity != null && !(entity instanceof Player) && !(entity instanceof Arrow)) {
    		amount = (amount + 1.0F) / 2.0F;
    	}

    	return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
    	boolean flag = super.doHurtTarget(entityIn);
    	
        if (flag) {
        	this.playSound(FURSoundRegistry.SWARMER_ATTACK.get(), 1.0F, 1.0F);
        	this.doEnchantDamageEffects(this, entityIn);
        	this.AttackTimer = ATTACK_TIMER;
        	this.level().broadcastEntityEvent(this, (byte)40);
        	
        	if (this.getSkin() == MimicModel.getNetherSkin() && this.getRandom().nextInt(4) == 0) {
        		entityIn.setSecondsOnFire(4);
        	}
        }
        
        return flag;
    }
    
    @Override
    public void doSitCommand(Player playerIn) {
    	super.doSitCommand(playerIn);
    	this.level().broadcastEntityEvent(this, (byte)(41 + this.getRandom().nextInt(4)));
    	this.setInSittingPose(true);
    	
    }
    
    @Override
    public void doFollowCommand(Player playerIn) {
    	ItemStack is;
    	super.doFollowCommand(playerIn);
    	
    	if (this.getSkin() == MimicModel.getVoidSkin()) {
      	   this.setCanPickUpLoot(false);
     	   
	       for (int i = 0; i < this.inventory.getContainerSize();i++) {
	    	   is = this.inventory.getItem(i);

	    	   if (!is.isEmpty()) {
	    		   this.spawnAtLocation(is.copy(), 0.2F);
	    		   is.shrink(is.getCount());
	    	   }
	       }
    	}
    	
    	this.setInSittingPose(false);
    }
    
    public void doMimicChest(Direction facing) {
    	this.level().broadcastEntityEvent(this, (byte)(41 + facing.get2DDataValue()));
    }
    
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);   	
        Item item = itemstack.getItem();
        
        if (itemstack.getItem() instanceof SpawnEggItem) {
            return super.mobInteract(player, hand);
        } else if (this.isTame() && this.getOwner().equals(player)) {
        	if (player.isCrouching()) {
        		if (this.getSkin() == MimicModel.getVoidSkin()) {	
        			PlayerEnderChestContainer enderchestinventory = player.getEnderChestInventory();
					player.openMenu(new SimpleMenuProvider((p_226928_1_, p_226928_2_, p_226928_3_) -> {
						return ChestMenu.threeRows(p_226928_1_, p_226928_2_, enderchestinventory);
    	            }, CONTAINER_TITLE));
    				this.playSound(SoundEvents.ENDER_CHEST_OPEN, 1.0F, 1.0F);
    			} else {
    				this.openGUI(player, this.getName());
    				this.playSound(SoundEvents.CHEST_OPEN, 1.0F, 1.0F);
            	}
        		
                return InteractionResult.sidedSuccess(this.level().isClientSide);
        	}

            if (!itemstack.isEmpty()) {            	
            	if (item.isEdible()) {
                    if (item.getFoodProperties().isMeat() && this.getHealth() < this.getMaxHealth()) {
                       if (!player.getAbilities().instabuild) {
                          itemstack.shrink(1);
                       }

                       this.playSound(SoundEvents.GENERIC_EAT, 0.4F, 1.0F);
                       this.heal((float)item.getFoodProperties().getNutrition());
                       return InteractionResult.SUCCESS;
                    }
            	} else if (this.isOwnedBy(player) && this.getSkin() != MimicModel.getVoidSkin() && item == Items.ENDER_EYE) {
             	   if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
             	   }
             	   this.setSkin(MimicModel.getVoidSkin());
             	   this.setCanPickUpLoot(false);
             	   ItemStack is;
 
        	       for (int i = 0; i < this.inventory.getContainerSize();i++) {
        	    	   is = this.inventory.getItem(i);
        	    	   if (!is.isEmpty()) {
	    					this.spawnAtLocation(is.copy(), 0.2F);
	    					is.shrink(is.getCount());        	    		   
        	    	   }
        	       }
 	       			
        	       this.playSound(SoundEvents.AMBIENT_CAVE.get(), 1.0F, 1.0F);
        	       for (int i = 0; i < 16; ++i) {
        	    	   double d0 = this.getRandom().nextGaussian() * 0.02D;
        	    	   double d1 = this.getRandom().nextGaussian() * 0.02D;
        	    	   double d2 = this.getRandom().nextGaussian() * 0.02D;
 		               this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(this.getRandom().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(this.getRandom().nextFloat() * this.getBbHeight()), this.getZ() + (double)(this.getRandom().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
        	       }

        	       return InteractionResult.SUCCESS;
                } else if (this.isOwnedBy(player) && this.getSkin() != MimicModel.getVoidSkin() && item == FURItemRegistry.MOOTENHEART.get()) {
                	if (!player.getAbilities().instabuild) {
                		itemstack.shrink(1);
                	}
					this.setSkin(6);
					this.playSound(SoundEvents.AMBIENT_CAVE.get(), 1.0F, 1.0F);
					for (int i = 0; i < 16; ++i) {
					    double d0 = this.getRandom().nextGaussian() * 0.02D;
					    double d1 = this.getRandom().nextGaussian() * 0.02D;
					    double d2 = this.getRandom().nextGaussian() * 0.02D;
					    this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(this.getRandom().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(this.getRandom().nextFloat() * this.getBbHeight()), this.getZ() + (double)(this.getRandom().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
					}

  	       			return InteractionResult.SUCCESS;
                 }
            }

            return super.mobInteract(player, hand);
        }
        
        if (!this.isTame() && this.distanceToSqr(player) < 2.0D) {
	        this.playSound(SoundEvents.CHEST_OPEN, 1.0F, 1.0F);
	        this.playSound(FURSoundRegistry.MIMIC_AMBIENT.get(), 0.4F, 1.0F);
	        this.setTarget(player);	        
	        this.setInSittingPose(false);
        }

        return super.mobInteract(player, hand);
     }
    
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType p_213386_3_, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag p_213386_5_) {   	
        /*this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Mimic_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Mimic_Attack.get());
    	this.setHealth(this.getMaxHealth());*/

    	if (worldIn.getBiome(this.blockPosition()).containsTag(BiomeTags.IS_NETHER)) {
    		this.setSkin(MimicModel.getNetherSkin()); 	 
    	} else {
    		this.setSkin(this.getRandom().nextInt(MimicModel.getTombSkin()));
    	}
    	
    	this.unpackLootTable(this.getSkin() == MimicModel.getNetherSkin() ? BuiltInLootTables.NETHER_BRIDGE : /*this.getSkin() == MimicModel.getTombSkin() ? LootTableHandler.DESERT_TOMB_CHEST : */BuiltInLootTables.SIMPLE_DUNGEON);
    	for (int i = 0; i < this.inventory.getContainerSize();i++) {
    		if (this.getRandom().nextFloat() >= 0.05F) {
    			this.inventory.removeItem(i, 3);
    		}
		}
    	
 	   	return entityLivingData;
    }
    
    private void unpackLootTable(ResourceLocation lootTable) {
    	long lootTableSeed = this.getRandom().nextLong();
    	
        if (lootTable != null && this.level().getServer() != null) {
           LootTable loottable = this.level().getServer().getLootData().getLootTable(lootTable);

           lootTable = null;
           LootParams.Builder lootcontext$builder = (new LootParams.Builder((ServerLevel)this.level())).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.blockPosition())).withLuck(-5.0F);

           loottable.fill(this.inventory, lootcontext$builder.create(LootContextParamSets.CHEST), lootTableSeed);
        }

     }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
    	return stack.getItem().equals(FURItemRegistry.PTERA_WING.get()) || stack.getItem().equals(FURItemRegistry.PTERA_WING_COOKED.get());
    }
    
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        
        if (this.getTarget() != null || this.AggressiveTimer > 0 || this.lastHurtByPlayerTime > 58) {
            this.setAggressive(true);
            this.level().broadcastEntityEvent(this, (byte)11);
        } else if (this.AITargetItem.canUse() && this.canPickupItems()) {
            if (!this.isAggressive() && this.getRandom().nextInt(1000) < 10) {
            	this.setAggressive(true);
                this.level().broadcastEntityEvent(this, (byte)11);
            }
        } else if (this.getRandom().nextInt(1000) < 100) {
        	this.setAggressive(false);
            this.level().broadcastEntityEvent(this, (byte)34);
        }
    }

    public void openGUI(Player playerIn, Component NameIn) {
        if (!this.level().isClientSide && (!this.hasPassenger(playerIn))) {
            NetworkHooks.openScreen((ServerPlayer) playerIn, new MenuProvider() {
				@Override
				public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
					return ChestMenu.threeRows(p_createMenu_1_, p_createMenu_2_, inventory);
				}

                @Override
                public Component getDisplayName() {
                    return NameIn;
                }
            });
        }
    }
    
    @Override
    public boolean fireImmune() {
        return this.getSkin() == MimicModel.getNetherSkin() || super.fireImmune();
	}

    public String getChestTexture() {
        return this.getEntityData().get(CHEST_TEXTURE);
    }

    public void setChestTexture(String chestTexture) {
        this.getEntityData().set(CHEST_TEXTURE, chestTexture);
    }

    public int getSkin() {
        return this.getEntityData().get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.getEntityData().set(SKIN_TYPE, skinType);
    }
	
    public int getSitTimer() {
       return this.SitTimer;
	}
    
	@Override
	public int getAttackTimer() {
		return this.AttackTimer;
	}

	@Override
	public void setAttackTimer(int i) {		
		this.AttackTimer = i;
	}
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 5) {
    		this.SitTimer = HIDE_IN_TIMER;
    	} else if (id == 6) {
    		this.SitTimer = HIDE_OUT_TIMER;
    	} else if (id == 7) {
    		this.IdleTimer = IDLE_TIMER;
    	} else if (id == 40) {
            this.AttackTimer = ATTACK_TIMER;
        } else if (id == 41) {
        	this.rotationAngle = 180.0F * ((float)Math.PI / 180.0F);
        } else if (id == 42) {
        	this.rotationAngle = 270.0F * ((float)Math.PI / 180.0F);
        } else if (id == 43) {
        	this.rotationAngle = 0.0F * ((float)Math.PI / 180.0F);
        } else if (id == 44) {
        	this.rotationAngle = 90.0F * ((float)Math.PI / 180.0F);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntityDimensions p_213348_2_) {
        return 0.7F;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
		this.inventory.fromTag(compound.getList("Items", 10));
		this.setSkin(compound.getInt("Variant"));
		this.setChestTexture(compound.getString("Chest"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
		compound.put("Items", this.inventory.createTag());
        compound.putInt("Variant", getSkin());
        compound.putString("Chest", getChestTexture());
    }
    
    @Override
    protected SoundEvent getAmbientSound() {
        return FURSoundRegistry.MIMIC_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FURSoundRegistry.MIMIC_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FURSoundRegistry.MIMIC_DEATH.get();
    }

	@Override
    protected void playStepSound(BlockPos pos, BlockState state) {
	    this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
	}
    
    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    @Override
    public void push(Entity entityIn) {
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
	}
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isTame() && super.shouldDropLoot();
    }
	
    @Override
	public MimicEntity getBreedOffspring(ServerLevel worldIn, AgeableMob ageable) {
		MimicEntity entity = FUREntityRegistry.MIMIC.get().create(worldIn);
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			entity.setOwnerUUID(uuid);
			entity.setTame(true);
			entity.setHealth(this.getMaxHealth());
			entity.setSkin(this.getRandom().nextBoolean() ? this.getSkin() : ((MimicEntity)ageable).getSkin());
			if(entity.getSkin() == MimicModel.getVoidSkin())entity.setSkin(0);
		}

		return entity;
	}
	
	/**
	* Called when the mob's health reaches 0.
	*/
    @Override
    protected void dropEquipment() {
    	ItemStack is;
    	super.dropEquipment();	
		if (!this.level().isClientSide) {
			for (int i = 0; i < this.inventory.getContainerSize();i++) {
				is = this.inventory.getItem(i);
	            if (!is.isEmpty() && !EnchantmentHelper.hasVanishingCurse(is)) {
	                this.spawnAtLocation(is);
            	}
			}
		}

		this.inventory.clearContent();
	}

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
    	if (this.isInSittingPose()) {
    		if (this.getSitTimer() == (HIDE_IN_TIMER - 1)) {
    			state.getController().setAnimation(HIDE_IN);
    		} if (this.IdleTimer == (IDLE_TIMER - 1)) {
    			state.getController().setAnimation(HIDE_PEEK);
    		} else {
    			state.getController().setAnimation(IDLE_HIDE);
    		}
    	} else if (this.getAttackTimer() == (ATTACK_TIMER - 1)) {
    		state.getController().setAnimation(ATTACK);
    	} else if (this.getAttackTimer() > 0 || this.getSitTimer() > 0) {
    		return PlayState.CONTINUE;
    	} else if (state.isMoving()) {
            state.getController().setAnimation(WALK);
        } else {
        	if (this.getSitTimer() == (HIDE_IN_TIMER - 1)) {
    			state.getController().setAnimation(HIDE_OUT);
    		} else {
    			state.getController().setAnimation(IDLE);
    		}
        }
        
        return PlayState.CONTINUE;
    }
    
	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));		
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
