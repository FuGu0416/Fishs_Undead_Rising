package com.Fishmod.fur.entities.tameable;

import java.util.UUID;
import javax.annotation.Nullable;

import com.Fishmod.fur.client.model.MimicModel;
import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.data.providers.FURStructureTagsProvider;
import com.Fishmod.fur.entities.ai.EntityAITargetItem;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.item.BeastcallHornItem;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.BlockPos;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
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

public class MimicEntity extends FURTameableEntity implements GeoEntity {
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
    /** Same filler pool used by the Royal Tomb's other (non-treasure) chests. */
    private static final ResourceLocation ROYAL_TOMB_CHEST_COMMON = new ResourceLocation(mod_LavaCow.MODID, "chests/royal_tomb_chest_common");
    public static ArrayList<String> TEXTURE_POOL = new ArrayList<String>(Arrays.asList(
            "textures/entity/chest/normal.png"
    ));
    
    private MimicState state = MimicState.HOSTILE_ACTIVE;
    private int stateTimer = 0;
    private static final double RESET_DISTANCE_SQR = 16 * 16;
    public static final int MIMIC_EGG_HATCH_TIME = 600;
    private int distanceCheckCooldown = 0;
    public SimpleContainer inventory;
    
    public enum MimicState {
        DORMANT,		// wild only
        AWAKENING,		// wild only
        HOSTILE_ACTIVE,	// wild only
        TAME_IDLE,		// tamed only
        TAME_ACTIVE		// tamed only
    }

	public MimicEntity(EntityType<? extends MimicEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        this.inventory = new SimpleContainer(27);
    }
	
    @Override
    protected void registerGoals() {
    	super.registerGoals();
    	// aiSit is now registered by the base class's own registerGoals(); this used to
    	// duplicate that registration (same Goal instance at the same priority).
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.applyEntityAI();
    }

    protected void applyEntityAI() {
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new MimicEntity.TargetItemGoal<>(this, ItemEntity.class, true));
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
        		.add(Attributes.MAX_HEALTH, 10.0D)
        		.add(Attributes.ATTACK_DAMAGE, 8.0D)
        		.add(Attributes.ARMOR, 20.0D)
        		.add(Attributes.FOLLOW_RANGE, 16.0D);
    }
    
    @Override
    protected Goal followGoal() {
    	return new FollowOwnerGoal(this, 1.5D, 10.0F, 2.0F, false);
    }

    public static boolean checkMimicSpawnRules(EntityType<? extends MimicEntity> entityTypeIn, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) { 	
    	return FURTameableEntity.checkMonsterSpawnRules(entityTypeIn, level, spawnType, pos, randomSource, level.getLevel().dimension() == Level.NETHER)
    			&& SpawnUtil.isNearBlock(level, Blocks.CHEST, pos, 4) != null
    			&& level.getLevel().getEntitiesOfClass(MimicEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).inflate(FURConfig.Mimic_SpawnRadius.get().doubleValue())).size() < FURConfig.Mimic_SpawnCap.get();
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
    public boolean checkSpawnObstruction(LevelReader levelReader) {
        return levelReader.isUnobstructed(this);
    }
    
    @Override
    public void setTame(boolean tamed) {
    	super.setTame(tamed);
        float maxHealthO = this.getMaxHealth();
        
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
        
        this.setHealth(this.getHealth() * (this.getMaxHealth() / maxHealthO));
 
        if (tamed) {
	        if (this.state == MimicState.DORMANT || this.state == MimicState.AWAKENING) {
	        	this.state = MimicState.TAME_IDLE;
	            this.setNoAi(true);
	            this.navigation.stop();
	        } else if (this.state == MimicState.HOSTILE_ACTIVE) {
	        	this.state = MimicState.TAME_ACTIVE;
	        	this.setNoAi(false);
	        }
        } else {
	        if (this.state == MimicState.TAME_IDLE) {
	        	this.state = MimicState.DORMANT;
	        	this.setNoAi(true);
	        	this.navigation.stop();
	        } else if (this.state == MimicState.TAME_ACTIVE) {
	        	this.state = MimicState.HOSTILE_ACTIVE;
	        	this.setNoAi(false);
	        }       	
        }
	}

    public int containsItem(Item itemIn) {
    	if (!this.level().isClientSide()) {
			for (int i = 0; i < this.inventory.getContainerSize();i++) {
				if (this.inventory.getItem(i).getItem().equals(itemIn)) {
					return i;
				}			
			}
    	}  
    	
    	return -1;
    }
    
	@Override
	public void setInSittingPose(boolean sitting) {
		// Capture the pose before super changes it — comparing afterwards made both
		// conditions unsatisfiable, so the hide_in/hide_out animations never fired.
		boolean wasSitting = this.isInSittingPose();
		super.setInSittingPose(sitting);

		if (!wasSitting && sitting) {
			this.level().broadcastEntityEvent(this, (byte)9);
		} else if (wasSitting && !sitting) {
			this.level().broadcastEntityEvent(this, (byte)10);
		}
	}
	
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
	@Override
    public void tick() {
	    super.tick();

	    if (this.level().isClientSide) return;

	    // These must run in every state — DORMANT included (they used to sit behind the
	    // dormant early-return, so hidden mimics never peeked and paused egg incubation).
	    if (this.tickCount % 20 == 0) {
	        this.tickEggIncubation();
	    }

	    // Baby form: growth is gated on chest contact, not the normal AgeableMob timer - see
	    // setAge(int) and checkGrowthContact(). MimicState doesn't apply to babies (they don't
	    // disguise as furniture), so this runs independently of the state machine below.
	    if (this.isBaby() && this.tickCount % 5 == 0) {
	        this.checkGrowthContact();
	    }

		if ((state == MimicState.DORMANT || state == MimicState.TAME_IDLE) && !this.isAggressive() && this.tickCount % 100 == 0 && this.getRandom().nextInt(5) == 0) {
			this.level().broadcastEntityEvent(this, (byte)11);
		}

	    if (state == MimicState.DORMANT) {
	    	return;
	    }

	    if (state == MimicState.AWAKENING) {
	        if (--stateTimer <= 0) {
	            this.enterAmbush();
	        }
	    }

	    int skin = this.getSkin();
	    if (--distanceCheckCooldown <= 0) {
	        this.distanceCheckCooldown = 20;

	        LivingEntity target = this.getTarget();
	        if (!this.isTame() && (target == null || this.distanceToSqr(target) > RESET_DISTANCE_SQR)) {
	            this.returnToDormant();
	        }

		    if (skin != MimicModel.getVoidSkin() && !this.getMainHandItem().isEmpty()) {
		    	// addItem copies the stack, so always clear the hand afterwards — a partial fit
		    	// used to leave the hand loaded and re-drop the leftover every cycle (item dup).
		    	ItemStack leftover = this.inventory.addItem(this.getMainHandItem());
		    	this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);

		    	if (!leftover.isEmpty()) {
		    		this.spawnAtLocation(leftover, 0.2F);
		    	}
		    }
	    }

		if (skin == MimicModel.getVoidSkin() && this.tickCount % 100 == 0) {
            for (int i = 0; i < 8; ++i) {
                int j = this.random.nextInt(2) * 2 - 1;
                int k = this.random.nextInt(2) * 2 - 1;
                double d0 = (double)this.getX() + 0.5D + 0.25D * (double)j;
                double d1 = (double)((float)this.getY() + this.random.nextFloat());
                double d2 = (double)this.getZ() + 0.5D + 0.25D * (double)k;
                double d3 = (double)(this.random.nextFloat() * (float)j);
                double d4 = ((double)this.random.nextFloat() - 0.5D) * 0.125D;
                double d5 = (double)(this.random.nextFloat() * (float)k);
                this.level().addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
            }
		}
    }
	
	private void enterAmbush() {
	    this.state = MimicState.HOSTILE_ACTIVE;
	    this.setNoAi(false);
	    this.setSilent(false);
	    this.distanceCheckCooldown = 80;
	    
        if (this.isInSittingPose()) {
        	this.setInSittingPose(false);
        }
	}
	
	private void returnToDormant() {
		this.setInSittingPose(true);
	    this.setTarget(null);
	    this.setSilent(true);
		
	    this.snapRotationToCardinal();
		
		if (this.level().getBlockState(this.blockPosition().below()).isAir()) {
			this.setPos(this.getX(), this.getY() - 1, this.getZ());
		}
		
		this.setNoAi(true);
	    this.navigation.stop();
		
	    this.state = isTame() ? MimicState.TAME_IDLE : MimicState.DORMANT;
	}	
	
	private void tickEggIncubation() {
	    for (int i = 0; i < inventory.getContainerSize(); i++) {
	        ItemStack stack = inventory.getItem(i);

	        if (!stack.is(FURItemRegistry.MIMIC_EGG.get())) continue;

	        CompoundTag tag = stack.getOrCreateTag();
	        int time = tag.getInt("HatchTime");
	        time++;

	        if (time >= MIMIC_EGG_HATCH_TIME) {
	        	// Hatching needs one flower_pot per egg - the pot is the egg's "nest", handed back
	        	// later when the resulting baby grows up (see growToAdult()). Fully incubated but no
	        	// spare pot yet: fall through to save the timer as-is and keep re-checking every
	        	// pass instead of resetting, so topping up the inventory hatches it right away.
	        	int potSlot = this.containsItem(Items.FLOWER_POT);
	        	if (potSlot >= 0) {
		        	this.inventory.removeItem(potSlot, 1);
		        	this.inventory.removeItem(i, 1);
		        	// Stacked eggs share this tag — restart incubation for the ones left behind,
		        	// otherwise they cascade-hatch one per second off the inherited full timer.
		        	ItemStack rest = this.inventory.getItem(i);
		        	if (rest.is(FURItemRegistry.MIMIC_EGG.get())) {
		        		rest.getOrCreateTag().putInt("HatchTime", 0);
		        	}
		        	if (this.level() instanceof ServerLevel server) {
		        		super.spawnChildFromBreeding(server, this);
		        	}
		            return;
	        	}
	        }

	        tag.putInt("HatchTime", time);
	    }
	}
	
	/**
	 * Natural time-based aging (and vanilla's feed-to-speed-up-growth, e.g. {@code Animal}'s default
	 * baby-feeding path) is disabled here - Mimic babies only grow up via the flower-pot/empty-chest
	 * contact mechanic ({@link #checkGrowthContact()} / {@link #growToAdult()}), which calls
	 * {@code setAge(0)} directly. Any other attempt to drift the age toward 0 over time is clamped
	 * straight back to the baby sentinel instead of being allowed through.
	 */
	@Override
	public void setAge(int age) {
		super.setAge(age < 0 ? AgeableMob.BABY_START_AGE : age);
	}

	/** Baby-only: checks for contact with an empty chest (dropped item or placed block) and, if
	 *  found, consumes it and grows up. Called periodically from {@link #tick()}. */
	private void checkGrowthContact() {
		AABB box = this.getBoundingBox().inflate(0.1D);

		for (ItemEntity itemEntity : this.level().getEntitiesOfClass(ItemEntity.class, box)) {
			ItemStack stack = itemEntity.getItem();
			if (stack.is(Items.CHEST) && isChestStackEmpty(stack)) {
				itemEntity.discard();
				this.growToAdult();
				return;
			}
		}

		BlockPos origin = this.blockPosition();
		for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-1, -1, -1), origin.offset(1, 1, 1))) {
			if (this.level().getBlockState(pos).is(Blocks.CHEST)
					&& box.intersects(new AABB(pos))
					&& this.level().getBlockEntity(pos) instanceof ChestBlockEntity chest
					&& chest.isEmpty()) {
				this.level().removeBlock(pos, false);
				this.growToAdult();
				return;
			}
		}
	}

	/** True if a dropped {@code minecraft:chest} item stack has no items saved in its BlockEntityTag. */
	private static boolean isChestStackEmpty(ItemStack stack) {
		CompoundTag blockEntityTag = stack.getTagElement("BlockEntityTag");
		return blockEntityTag == null || blockEntityTag.getList("Items", 10).isEmpty();
	}

	private void growToAdult() {
		this.setAge(0);
		this.spawnAtLocation(new ItemStack(Items.FLOWER_POT), 0.2F);
	}

	@Override
	public boolean canPickUpLoot() {
		return !this.isBaby() && this.getSkin() != MimicModel.getVoidSkin();
	}
	
	@Override
	protected float getWaterSlowDown() {
		return 0.9F;
	}
	
    @Override
    public void travel(Vec3 travelVector) {
		if (this.isInSittingPose()) {
			// No walk input while planted, but keep gravity/friction so the mimic
			// falls when its support block is removed instead of hovering.
			super.travel(Vec3.ZERO);
	    } else {
			super.travel(travelVector);
		}
	}
    
    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
    	Entity entity = source.getDirectEntity();
    	
    	if (this.state == MimicState.DORMANT) {
    		this.triggerAmbush(null);
    	}
		
    	// Damage buffer for tamed mimics — skipped whenever a player caused the damage,
    	// directly or through any projectile (the old direct-entity Arrow check missed
    	// spectral arrows, tridents and fireballs).
    	if (this.isTame() && entity != null && !(source.getEntity() instanceof Player)) {
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
        	
        	if (this.getSkin() == MimicModel.getNetherSkin() && this.getRandom().nextInt(4) == 0) {
        		entityIn.setSecondsOnFire(4);
        	}
        }
        
        return flag;
    }
    
    @Override
    public void doSitCommand(Player playerIn) {
    	super.doSitCommand(playerIn);
    	this.returnToDormant();
    }
    
    @Override
    public void doFollowCommand(Player playerIn) {
    	super.doFollowCommand(playerIn);
        this.state = MimicState.TAME_ACTIVE;
        this.setNoAi(false);
        this.setSilent(false);

    	if (this.getSkin() == MimicModel.getVoidSkin()) {
    		this.dumpInventory();
    	}

    	this.setInSittingPose(false);
    }

    /** Drop the chest inventory's contents on the ground and empty it. */
    private void dumpInventory() {
    	for (int i = 0; i < this.inventory.getContainerSize(); i++) {
    		ItemStack is = this.inventory.getItem(i);

    		if (!is.isEmpty()) {
    			this.spawnAtLocation(is.copy(), 0.2F);
    			is.shrink(is.getCount());
    		}
    	}
    }

    private void playSkinConversionEffects() {
    	this.playSound(SoundEvents.AMBIENT_CAVE.get(), 1.0F, 1.0F);
    	for (int i = 0; i < 16; ++i) {
    		double d0 = this.getRandom().nextGaussian() * 0.02D;
    		double d1 = this.getRandom().nextGaussian() * 0.02D;
    		double d2 = this.getRandom().nextGaussian() * 0.02D;
    		this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (2.0D * this.getRandom().nextFloat() - 1.0D) * this.getBbWidth(), this.getY() + (this.getRandom().nextFloat() * this.getBbHeight()), this.getZ() + (2.0D * this.getRandom().nextFloat() - 1.0D) * this.getBbWidth(), d0, d1, d2);
    	}
    }
    
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);   	
        Item item = itemstack.getItem();
        
        if (itemstack.getItem() instanceof SpawnEggItem) {
            return super.mobInteract(player, hand);
        // isOwnedBy compares UUIDs — getOwner().equals(player) NPE'd when the owner
        // was offline or in another dimension and someone else interacted.
        } else if (this.isTame() && this.isOwnedBy(player)) {
        	// Babies can't be opened - they have no inventory GUI of their own yet (see
        	// canPickUpLoot() and TargetItemGoal, which also lock them out of picking anything up).
        	if (player.isCrouching() && !(item instanceof BeastcallHornItem) && !this.isBaby()) {
        		if (this.getSkin() == MimicModel.getVoidSkin()) {	
        			PlayerEnderChestContainer enderchestinventory = player.getEnderChestInventory();
					player.openMenu(new SimpleMenuProvider((containerId, playerInventory, menuPlayer) -> {
						return ChestMenu.threeRows(containerId, playerInventory, enderchestinventory);
    	            }, CONTAINER_TITLE));
    				this.playSound(SoundEvents.ENDER_CHEST_OPEN, 1.0F, 1.0F);
    			} else {
    				this.openGUI(player, this.getName());
    				this.playSound(SoundEvents.CHEST_OPEN, 1.0F, 1.0F);
            	}
        		
                return InteractionResult.sidedSuccess(this.level().isClientSide);
        	}

            // Babies can't be converted to the nether/void skins either - those items should just
            // pass through to the fallback interaction below (e.g. feeding) until it grows up.
            if (!itemstack.isEmpty() && !this.isBaby()) {
            	if (this.getSkin() != MimicModel.getVoidSkin() && item == Items.ENDER_EYE) {
            		if (!player.getAbilities().instabuild) {
            			itemstack.shrink(1);
            		}
            		this.setSkin(MimicModel.getVoidSkin());
            		// Void mimics expose the owner's ender chest instead of their own
            		// inventory — empty it first so nothing becomes unreachable.
            		this.dumpInventory();
            		this.playSkinConversionEffects();

            		return InteractionResult.sidedSuccess(this.level().isClientSide);
                } else if (this.getSkin() != MimicModel.getNetherSkin() && this.getSkin() != MimicModel.getVoidSkin() && item == FURItemRegistry.MOOTEN_HEART.get()) {
                	if (!player.getAbilities().instabuild) {
                		itemstack.shrink(1);
                	}
                	// getNetherSkin(), not a hardcoded index — the old literal 6 pointed at the
                	// void skin after the texture pool grew, clashing with the ender-eye path.
                	this.setSkin(MimicModel.getNetherSkin());
                	this.playSkinConversionEffects();

                	return InteractionResult.sidedSuccess(this.level().isClientSide);
                 }
            }

            return super.mobInteract(player, hand);
        }
        
        InteractionResult result = super.mobInteract(player, hand);
        if (result == InteractionResult.PASS && state == MimicState.DORMANT && this.distanceToSqr(player) < 2.0D && !player.getAbilities().instabuild) {
            this.triggerAmbush(player);
            return InteractionResult.CONSUME;
        }
        return result;
	}
    
    private void triggerAmbush(Player player) {
        this.state = MimicState.AWAKENING;
        this.stateTimer = 20;

        this.setNoAi(true);
        
        if (player != null) {
	        this.setTarget(player);
	
	        this.level().playSound(null, this.blockPosition(), SoundEvents.CHEST_OPEN, SoundSource.HOSTILE, 1.0F, 0.6F);
	        this.level().playSound(null, this.blockPosition(), FURSoundRegistry.MIMIC_AMBIENT.get(), SoundSource.HOSTILE, 0.4F, 1.0F);
        }
    }
    
    private static final float[] CARDINAL_YAWS = {
            0.0F,    // South
            90.0F,   // West
            180.0F,  // North
            -90.0F   // East
    };

    private void snapRotationToCardinal() {
        float currentYaw = Mth.wrapDegrees(this.getYRot());

        float closestYaw = CARDINAL_YAWS[0];
        float smallestDiff = Float.MAX_VALUE;

        for (float yaw : CARDINAL_YAWS) {
            float diff = Math.abs(Mth.wrapDegrees(currentYaw - yaw));
            if (diff < smallestDiff) {
                smallestDiff = diff;
                closestYaw = yaw;
            }
        }

        this.moveTo(Math.floor(this.getX()) + 0.5D, Math.floor(this.getY()), Math.floor(this.getZ()) + 0.55D, closestYaw, 0.0F);
        this.setYHeadRot(closestYaw);
        this.setYBodyRot(closestYaw);
    }
    
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficulty, MobSpawnType spawnTypeIn, @Nullable SpawnGroupData entityLivingData, @Nullable CompoundTag tag) {
        entityLivingData = super.finalizeSpawn(worldIn, difficulty, spawnTypeIn, entityLivingData, tag);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Mimic_Health.get());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Mimic_Attack.get());
    	this.setHealth(this.getMaxHealth());
    	
    	boolean royalTomb = worldIn.getLevel().structureManager().getStructureWithPieceAt(this.blockPosition(), FURStructureTagsProvider.ROYAL_TOMB).isValid();

    	if (worldIn.getBiome(this.blockPosition()).containsTag(BiomeTags.IS_NETHER)) {
    		this.setSkin(MimicModel.getNetherSkin());
    	} else if (royalTomb) {
    		this.setSkin(MimicModel.getTombSkin());
    	} else {
    		this.setSkin(this.getRandom().nextInt(MimicModel.getTombSkin()));
    	}

    	ResourceLocation lootTable;
    	if (this.getSkin() == MimicModel.getNetherSkin()) {
    		lootTable = BuiltInLootTables.NETHER_BRIDGE;
    	} else if (royalTomb) {
    		lootTable = ROYAL_TOMB_CHEST_COMMON;
    	} else {
    		lootTable = BuiltInLootTables.SIMPLE_DUNGEON;
    	}
    	this.unpackLootTable(lootTable);
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

           LootParams.Builder lootcontext$builder =(new LootParams.Builder((ServerLevel)this.level())).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(this.blockPosition())).withLuck(-5.0F);

           loottable.fill(this.inventory, lootcontext$builder.create(LootContextParamSets.CHEST), lootTableSeed);
        }
     }
    
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isFood(ItemStack stack) {
        if (this.getSkin() == MimicModel.getVoidSkin()) {
            return false;
        }
        
    	return stack.getItem().equals(FURItemRegistry.PTERA_WING_RAW.get()) || stack.getItem().equals(FURItemRegistry.PTERA_WING_COOKED.get());
    }    

    public void openGUI(Player playerIn, Component NameIn) {
        if (!this.level().isClientSide && (!this.hasPassenger(playerIn))) {
            NetworkHooks.openScreen((ServerPlayer) playerIn, new MenuProvider() {
				@Override
				public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
					return ChestMenu.threeRows(containerId, playerInventory, inventory);
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
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte id) {
    	if (id == 9) {
    		this.triggerAnim("trigger_controller", "hide_in");
    	} else if (id == 10) {
    		this.triggerAnim("trigger_controller", "hide_out");
    	} else if (id == 11) {
    		this.triggerAnim("trigger_controller", "hide_peek");
    	} else if (id == 40) {
    		this.triggerAnim("trigger_controller", "attack");
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
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

		// Restore the state machine — without this a reloaded mimic reverts to the
		// HOSTILE_ACTIVE field default while its NoAI/sitting flags say otherwise.
		if (compound.contains("MimicState")) {
			try {
				this.state = MimicState.valueOf(compound.getString("MimicState"));
			} catch (IllegalArgumentException e) {
				this.state = this.isTame() ? MimicState.TAME_ACTIVE : MimicState.HOSTILE_ACTIVE;
			}
		}
		this.stateTimer = compound.getInt("StateTimer");
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
        compound.putString("MimicState", this.state.name());
        compound.putInt("StateTimer", this.stateTimer);
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
    
	@Override
	public boolean isPushable() {
	    return state != MimicState.DORMANT && state != MimicState.TAME_IDLE;
	}
	
	@Override
	protected void pushEntities() {
	    if (state != MimicState.DORMANT && state != MimicState.TAME_IDLE) {
	        super.pushEntities();
	    }
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
    public boolean canFallInLove() {
        if (this.getSkin() == MimicModel.getVoidSkin()) {
            return false;
        }

        return super.canFallInLove();
    }
    
    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal partner) {
        this.setAge(6000);
        partner.setAge(6000);
        // Vanilla resets love here too — without it the pair keeps re-breeding for the
        // whole 600-tick love window and one feeding yields a stack of eggs.
        this.resetLove();
        partner.resetLove();

        ItemStack egg = new ItemStack(FURItemRegistry.MIMIC_EGG.get());

        if (this.inventory.canAddItem(egg)) {
        	this.inventory.addItem(egg);
            level.playSound(null, this.blockPosition(), SoundEvents.SNIFFER_EGG_PLOP, SoundSource.NEUTRAL, 1.0F, 1.0F);
        } else if (partner instanceof MimicEntity mimic && mimic.inventory.canAddItem(egg)) {
        	mimic.inventory.addItem(egg);
        	level.playSound(null, this.blockPosition(), SoundEvents.SNIFFER_EGG_PLOP, SoundSource.NEUTRAL, 1.0F, 1.0F);
        }

        level.broadcastEntityEvent(this, (byte) 18);
    }
	
    @Override
	public MimicEntity getBreedOffspring(ServerLevel worldIn, AgeableMob ageable) {
		MimicEntity entity = FUREntityRegistry.MIMIC.get().create(worldIn);
		// Bred babies skip finalizeSpawn, so apply the config attributes here
		// (setTame(true) below swaps in the tamed set for owned offspring).
		entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Mimic_Health.get());
		entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(FURConfig.Mimic_Attack.get());
		UUID uuid = this.getOwnerUUID();
		if (uuid != null) {
			entity.setOwnerUUID(uuid);
			entity.setTame(true);
			entity.setSkin(this.getRandom().nextBoolean() ? this.getSkin() : ((MimicEntity)ageable).getSkin());
			if (entity.getSkin() == MimicModel.getVoidSkin()) entity.setSkin(0);
		}
		entity.setHealth(entity.getMaxHealth());

		return entity;
	}
	
	/**
	* Called when the mob's health reaches 0.
	*/
    @Override
    protected void dropEquipment() {
    	super.dropEquipment();
		if (!this.level().isClientSide) {
			for (int i = 0; i < this.inventory.getContainerSize(); i++) {
				ItemStack is = this.inventory.getItem(i);
	            if (!is.isEmpty() && !EnchantmentHelper.hasVanishingCurse(is)) {
	                this.spawnAtLocation(is);
            	}
			}
		}

		this.inventory.clearContent();
	}
    
    static class TargetItemGoal<T extends ItemEntity> extends EntityAITargetItem<T> {
    	MimicEntity mimic;
    	
		public TargetItemGoal(Mob creature, Class<T> classTarget, boolean checkSight) {
			super(creature, classTarget, checkSight);
			this.mimic = (MimicEntity) this.mob;
		}
    	
		@Override
		public boolean canUse() {
			if (this.mimic.isBaby() || this.mimic.state == MimicState.DORMANT || this.mimic.state == MimicState.TAME_IDLE || this.mimic.isInSittingPose() || this.mimic.getSkin() == MimicModel.getVoidSkin()) return false;

			return super.canUse();
		}

		@Override
		public void stop() {
			if (this.targetEntity != null && this.targetEntity.isAlive()) {
				this.mimic.level().broadcastEntityEvent(this.mimic, (byte)40);
			}
			super.stop();
		}
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> animState) {
    	if (this.state == MimicState.DORMANT || this.state == MimicState.TAME_IDLE || this.isInSittingPose()) {
			animState.getController().setAnimation(IDLE_HIDE);
    	} else if (animState.isMoving() || !this.getNavigation().isDone()) {
            animState.getController().setAnimation(WALK);
        } else {
			animState.getController().setAnimation(IDLE);
        }

        return PlayState.CONTINUE;
    }
    
	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, "controller", 5, this::predicate));
		controllers.add(new AnimationController<>(this, "trigger_controller", 5, state -> PlayState.STOP)
				.triggerableAnim("attack", ATTACK)
				.triggerableAnim("hide_in", HIDE_IN)
				.triggerableAnim("hide_out", HIDE_OUT)
				.triggerableAnim("hide_peek", HIDE_PEEK));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}
