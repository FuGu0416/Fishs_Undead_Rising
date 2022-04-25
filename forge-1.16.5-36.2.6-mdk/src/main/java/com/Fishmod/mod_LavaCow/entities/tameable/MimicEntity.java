package com.Fishmod.mod_LavaCow.entities.tameable;

import java.util.UUID;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAITargetItem;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class MimicEntity extends FURTameableEntity implements IAggressive {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.defineId(MimicEntity.class, DataSerializers.INT);
    private static final DataParameter<String> CHEST_TEXTURE = EntityDataManager.defineId(MimicEntity.class, DataSerializers.STRING);
    private static final ITextComponent CONTAINER_TITLE = new TranslationTextComponent("container.enderchest");
    public static ArrayList<String> TEXTURE_POOL = new ArrayList<String>(Arrays.asList(
            "textures/entity/chest/normal.png"
    ));

    private int AttackTimer, AggressiveTimer = 40;
    public float rotationAngle = 0.0F;
    public int IdleTimer, SitTimer;
	public Inventory inventory;
    private EntityAITargetItem<ItemEntity> AITargetItem;
	
	public MimicEntity(EntityType<? extends MimicEntity> p_i48549_1_, World worldIn) {
        super(p_i48549_1_, worldIn);
        this.inventory = new Inventory(27);       
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
        this.entityData.define(SKIN_TYPE, (4 + this.getRandom().nextInt(5)) % 6);
        this.entityData.define(CHEST_TEXTURE, MimicEntity.TEXTURE_POOL.get(this.getRandom().nextInt(MimicEntity.TEXTURE_POOL.size())));
	}
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
        		.add(Attributes.MOVEMENT_SPEED, 0.22D)
        		.add(Attributes.MAX_HEALTH, FURConfig.Mimic_Health.get())
        		.add(Attributes.ATTACK_DAMAGE, FURConfig.Mimic_Attack.get())
        		.add(Attributes.ARMOR, 20.0D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D);
    }

	@Override
	public boolean removeWhenFarAway(double p_213397_1_) {
        return this.inventory.isEmpty() && (!this.isPersistenceRequired() || super.removeWhenFarAway(p_213397_1_));
    }
	
    public static boolean checkMimicSpawnRules(EntityType<? extends MimicEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) { 	
    	return SpawnUtil.isNearBlock(p_223316_1_, Blocks.CHEST, p_223316_3_, 4) != null && FURTameableEntity.checkMonsterSpawnRules(p_223316_0_, (IServerWorld) p_223316_1_, p_223316_2_, p_223316_3_, p_223316_4_);//SpawnUtil.isAllowedDimension(this.dimension);
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
    public void setTame(boolean tamed) {
    	super.setTame(tamed);
        
        if (tamed) {
        	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Mimic_Health.get() * 3.0D);
        	this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        	this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Mimic_Attack.get() * 0.5D);
        	this.setSilent(false);
        } else {
        	this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Mimic_Health.get());
        	this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.22D);
        	this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Mimic_Attack.get());
        }
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
    	if (!this.level.isClientSide()) {
			for (int i = 0; i < this.inventory.getContainerSize();i++)
				if (this.inventory.getItem(i).isEmpty()) {
					this.inventory.setItem(i, itemstackIn.copy());
					itemstackIn.shrink(itemstackIn.getCount());
					return;
				}			
    	}
    }
    
    public int containsItem(Item itemIn) {
    	if (!this.level.isClientSide()) {
			for (int i = 0; i < this.inventory.getContainerSize();i++)
				if (this.inventory.getItem(i).getItem().equals(itemIn)) {
					return i;
				}			
    	}   	
    	return -1;
    }
    
    private void EmergencyFood() {
    	if (!this.level.isClientSide())
			for (int i = 0; i < this.inventory.getContainerSize();i++)
				if (this.isFood(this.inventory.getItem(i))) {
					Item item = this.inventory.getItem(i).getItem();
					this.playSound(SoundEvents.GENERIC_EAT, 0.4F, 1.0F);
					this.heal((float)item.getFoodProperties().getNutrition());
                    this.addEffect(new EffectInstance(Effects.REGENERATION, 8*20, 0));
                    this.inventory.setItem(i, new ItemStack(this.inventory.getItem(i).getItem(), this.inventory.getItem(i).getCount() - 1));
				}
    }
	
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
	@Override
    public void tick() {
		super.tick();
		
		if(this.AggressiveTimer > 0)
			this.AggressiveTimer--;
		
    	if(this.AttackTimer > 0)
    		this.AttackTimer--;
    	
		if(!this.level.isClientSide()) {
			if (!this.isAggressive() && !this.isTame()) {
				if(!this.isSilent()) {
					this.setInSittingPose(true);
					this.level.broadcastEntityEvent(this, (byte)41);
				}
			
				this.setPos(MathHelper.floor(this.getX()) + 0.5, MathHelper.floor(this.getY()), MathHelper.floor(this.getZ()) + 0.55);
				this.yRot = this.yRotO = this.rotationAngle;
				this.yBodyRot = this.yBodyRotO = 0F;		
				
				if (this.level.getBlockState(this.blockPosition().below()).getMaterial().equals(Material.AIR))
					this.setPos(this.getX(), this.getY() - 1, this.getZ());
	
				this.setSilent(true);
				this.setSpeed(0.0F);
			} else if (this.getTarget() != null) {
				this.AggressiveTimer = 200;
				this.setSilent(false);
				this.setSpeed(0.19F);
			}
		}
		
		if(!this.isTame() && this.getTarget() != null && this.distanceTo(this.getTarget()) > this.getAttribute(Attributes.FOLLOW_RANGE).getValue()) {
			this.setTarget((LivingEntity)null);
		}
		
		for(ItemStack S: this.getHandSlots())
			if(!S.isEmpty())this.hasSpace(S);
		
		if(this.isTame() && this.getHealth() <= this.getMaxHealth() * 0.5F)
			this.EmergencyFood();
		
		if(this.getSkin() == getVoidSkin() && this.tickCount % 100 == 0) {
            for (int i = 0; i < 8; ++i) {
                int j = this.getRandom().nextInt(2) * 2 - 1;
                int k = this.getRandom().nextInt(2) * 2 - 1;
                double d0 = (double)this.getX() + 0.5D + 0.25D * (double)j;
                double d1 = (double)((float)this.getY() + this.getRandom().nextFloat());
                double d2 = (double)this.getZ() + 0.5D + 0.25D * (double)k;
                double d3 = (double)(this.getRandom().nextFloat() * (float)j);
                double d4 = ((double)this.getRandom().nextFloat() - 0.5D) * 0.125D;
                double d5 = (double)(this.getRandom().nextFloat() * (float)k);
                this.level.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
            }
		}
		
    	if(this.level.isClientSide()) {
        	if(this.isInSittingPose() && this.SitTimer > 0) {
        		this.SitTimer--;
        	}
        	
        	if(!this.isInSittingPose() && this.SitTimer < 20) {
        		this.SitTimer++;
        	}
    	}
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
    	super.aiStep();
    	
    	if(this.IdleTimer > 0)
    		this.IdleTimer--;
    	   	
		if (!this.isAggressive() && !this.isTame() && this.tickCount % 100 == 0 && this.getRandom().nextInt(5) == 0)
			this.IdleTimer = 30 + this.getRandom().nextInt(30);
    }
	
    @Override
    public void travel(Vector3d p_213352_1_) {
		if((!this.isAggressive() && !this.isTame()) || (this.SitTimer > 0 && this.SitTimer < 20)) {
            this.setDeltaMovement(Vector3d.ZERO);
		}
		else
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
		
    	if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof ArrowEntity)) {
    		amount = (amount + 1.0F) / 2.0F;
    	}

    	return super.hurt(source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
    	boolean flag = super.doHurtTarget(entityIn);
        if (flag) {
        	this.playSound(FURSoundRegistry.SWARMER_ATTACK, 1.0F, 1.0F);
        	this.doEnchantDamageEffects(this, entityIn);
        	this.AttackTimer = 5;
        	this.level.broadcastEntityEvent(this, (byte)40);
        	
        	if(this.getSkin() == 6 && this.getRandom().nextInt(4) == 0) {
        		entityIn.setSecondsOnFire(4);
        	}
        }
        return flag;
    }
    
    @Override
    public void doSitCommand(PlayerEntity playerIn) {
    	super.doSitCommand(playerIn);
    	this.level.broadcastEntityEvent(this, (byte)(41 + this.getRandom().nextInt(4)));
    	this.setInSittingPose(true);
    }
    
    @Override
    public void doFollowCommand(PlayerEntity playerIn) {
    	ItemStack is;
    	super.doFollowCommand(playerIn);
    	
    	if(this.getSkin() == this.getVoidSkin()) {
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
    	this.level.broadcastEntityEvent(this, (byte)(41 + facing.get2DDataValue()));
    }
    
    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);   	
        Item item = itemstack.getItem();
        
        if (itemstack.getItem() instanceof SpawnEggItem) {
            return super.mobInteract(player, hand);
        } else if(this.isTame()) {
        	if (player.isCrouching()) {
        		if(!this.level.isClientSide()) {
        			if(this.getSkin() == getVoidSkin()) {	
    					EnderChestInventory enderchestinventory = player.getEnderChestInventory();
    					player.openMenu(new SimpleNamedContainerProvider((p_226928_1_, p_226928_2_, p_226928_3_) -> {
    						return ChestContainer.threeRows(p_226928_1_, p_226928_2_, enderchestinventory);
        	            }, CONTAINER_TITLE));
        				this.playSound(SoundEvents.ENDER_CHEST_OPEN, 1.0F, 1.0F);
        			} else {
        				this.openGUI(player, this.getName());
        				this.playSound(SoundEvents.CHEST_OPEN, 1.0F, 1.0F);
                	}
                    return ActionResultType.sidedSuccess(this.level.isClientSide);
                }
        	}

            if (!itemstack.isEmpty()) {
            	ActionResultType actionresulttype = itemstack.interactLivingEntity(player, this, hand);
            	if (actionresulttype.equals(ActionResultType.SUCCESS)) {
            		return actionresulttype;
            	}
            	
            	if (item.isEdible()) {
                    if (item.getFoodProperties().isMeat() && this.getHealth() < this.getMaxHealth()) {
                       if (!player.abilities.instabuild) {
                          itemstack.shrink(1);
                       }

                       this.playSound(SoundEvents.GENERIC_EAT, 0.4F, 1.0F);
                       this.heal((float)item.getFoodProperties().getNutrition());
                       return ActionResultType.SUCCESS;
                    }
            	} else if (this.isOwnedBy(player) && this.getSkin() != getVoidSkin() && item == Items.ENDER_EYE) {
             	   if (!player.abilities.instabuild) {
                        itemstack.shrink(1);
             	   }
             	   this.setSkin(getVoidSkin());
             	   this.setCanPickUpLoot(false);
             	   ItemStack is;
 
        	       for (int i = 0; i < this.inventory.getContainerSize();i++) {
        	    	   is = this.inventory.getItem(i);
        	    	   if (!is.isEmpty()) {
	    					this.spawnAtLocation(is.copy(), 0.2F);
	    					is.shrink(is.getCount());        	    		   
        	    	   }
        	       }
 	       			
        	       this.playSound(SoundEvents.AMBIENT_CAVE, 1.0F, 1.0F);
        	       for (int i = 0; i < 16; ++i) {
        	    	   double d0 = this.getRandom().nextGaussian() * 0.02D;
        	    	   double d1 = this.getRandom().nextGaussian() * 0.02D;
        	    	   double d2 = this.getRandom().nextGaussian() * 0.02D;
 		               this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(this.getRandom().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(this.getRandom().nextFloat() * this.getBbHeight()), this.getZ() + (double)(this.getRandom().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
        	       }

        	       return ActionResultType.SUCCESS;
                } else if (this.isOwnedBy(player) && this.getSkin() != getVoidSkin() && item == FURItemRegistry.MOOTENHEART) {
                	if (!player.abilities.instabuild) {
                		itemstack.shrink(1);
                	}
					this.setSkin(6);
					this.playSound(SoundEvents.AMBIENT_CAVE, 1.0F, 1.0F);
					for (int i = 0; i < 16; ++i) {
					    double d0 = this.getRandom().nextGaussian() * 0.02D;
					    double d1 = this.getRandom().nextGaussian() * 0.02D;
					    double d2 = this.getRandom().nextGaussian() * 0.02D;
					    this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double)(this.getRandom().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), this.getY() + (double)(this.getRandom().nextFloat() * this.getBbHeight()), this.getZ() + (double)(this.getRandom().nextFloat() * this.getBbWidth()) - (double)this.getBbWidth(), d0, d1, d2);
					}

  	       			return ActionResultType.SUCCESS;
                 }
            }

            return super.mobInteract(player, hand);
        }
        
        if(!this.isTame() && this.distanceToSqr(player) < 2.0D) {
	        this.playSound(SoundEvents.CHEST_OPEN, 1.0F, 1.0F);
	        this.playSound(FURSoundRegistry.MIMIC_AMBIENT, 0.4F, 1.0F);
	        this.setTarget(player);	        
	        this.setInSittingPose(false);
        }

        return super.mobInteract(player, hand);
     }
    
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData entityLivingData, @Nullable CompoundNBT p_213386_5_) {   	
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Mimic_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Mimic_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	if(BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(this.blockPosition()))).contains(Type.NETHER))
 		   this.setSkin(6); 	 
    	this.unpackLootTable(this.getSkin() == 6 ? LootTables.NETHER_BRIDGE : this.getSkin() == 7 ? LootTableHandler.DESERT_TOMB_CHEST : LootTables.SIMPLE_DUNGEON);
    	for (int i = 0; i < this.inventory.getContainerSize();i++) {
    		if (this.getRandom().nextFloat() >= 0.05F) {
    			this.inventory.removeItem(i, 3);
    		}
		}
 	   	return entityLivingData;
    }
    
    private void unpackLootTable(ResourceLocation lootTable) {
    	long lootTableSeed = this.getRandom().nextLong();
    	
        if (lootTable != null && this.level.getServer() != null) {
           LootTable loottable = this.level.getServer().getLootTables().get(lootTable);

           lootTable = null;
           LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)this.level)).withParameter(LootParameters.ORIGIN, Vector3d.atCenterOf(this.blockPosition())).withOptionalRandomSeed(lootTableSeed).withLuck(-5.0F);

           loottable.fill(this.inventory, lootcontext$builder.create(LootParameterSets.CHEST));
        }

     }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
    	return stack.getItem().equals(FURItemRegistry.PTERA_WING_JUNGLE) || stack.getItem().equals(FURItemRegistry.PTERA_WING_DESERT) || stack.getItem().equals(FURItemRegistry.PTERA_WING_COOKED);
    }
    
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.getTarget() != null || this.AggressiveTimer > 0 || this.lastHurtByPlayerTime > 58) {
            this.setAggressive(true);
            this.level.broadcastEntityEvent(this, (byte)11);
        } else if (this.AITargetItem.canUse() && this.canPickupItems()) {
            if (!this.isAggressive() && this.getRandom().nextInt(1000) < 10) {
            	this.setAggressive(true);
                this.level.broadcastEntityEvent(this, (byte)11);
            }
        } else if (this.getRandom().nextInt(1000) < 100) {
        	this.setAggressive(false);
            this.level.broadcastEntityEvent(this, (byte)34);
        }
    }

    public void openGUI(PlayerEntity playerIn, ITextComponent NameIn) {
        if (!this.level.isClientSide && (!this.hasPassenger(playerIn))) {
            NetworkHooks.openGui((ServerPlayerEntity) playerIn, new INamedContainerProvider() {
                @Override
                public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                    return ChestContainer.threeRows(p_createMenu_1_, p_createMenu_2_, inventory);
                }

                @Override
                public ITextComponent getDisplayName() {
                    return NameIn;
                }
            });
        }
    }
    
    @Override
    public boolean fireImmune() {
        return this.getSkin() == 6 || super.fireImmune();
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
	
	public int getVoidSkin() {
		return 3;
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
        if (id == 40)
        {
            this.AttackTimer = 5;
        }
        else if (id == 41)
        {
        	this.rotationAngle = 180.0F * ((float)Math.PI / 180.0F);
        }
        else if (id == 42)
        {
        	this.rotationAngle = 270.0F * ((float)Math.PI / 180.0F);
        }
        else if (id == 43)
        {
        	this.rotationAngle = 0.0F * ((float)Math.PI / 180.0F);
        }
        else if (id == 44)
        {
        	this.rotationAngle = 90.0F * ((float)Math.PI / 180.0F);
        }
        else
        {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
        return 0.7F;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
		this.inventory.fromTag(compound.getList("Items", 10));
		this.setSkin(compound.getInt("Variant"));
		this.setChestTexture(compound.getString("Chest"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
	@Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
		compound.put("Items", this.inventory.createTag());
        compound.putInt("Variant", getSkin());
        compound.putString("Chest", getChestTexture());
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return FURSoundRegistry.MIMIC_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return FURSoundRegistry.MIMIC_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return FURSoundRegistry.MIMIC_DEATH;
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
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
	}
    
    /**
     * Entity won't drop items or experience points if this returns false
     */
    @Override
    protected boolean shouldDropLoot() {
       return !this.isTame() && super.shouldDropLoot();
    }
	
    @Override
	public MimicEntity getBreedOffspring(ServerWorld worldIn, AgeableEntity ageable) {
		MimicEntity entity = FUREntityRegistry.MIMIC.create(worldIn);
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			entity.setOwnerUUID(uuid);
			entity.setTame(true);
			entity.setHealth(this.getMaxHealth());
			entity.setSkin(this.getRandom().nextBoolean() ? this.getSkin() : ((MimicEntity)ageable).getSkin());
			if(entity.getSkin() == 3)entity.setSkin(0);
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
		if (!this.level.isClientSide) {
			for (int i = 0; i < this.inventory.getContainerSize();i++) {
				is = this.inventory.getItem(i);
	            if (!is.isEmpty() && !EnchantmentHelper.hasVanishingCurse(is)) {
	                this.spawnAtLocation(is);
            	}
			}
		}

		this.inventory.clearContent();
	}
}
