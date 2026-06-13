package com.Fishmod.fur.entities;

import javax.annotation.Nullable;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class LavaCowEntity extends Cow {	
	public LavaCowEntity(EntityType<? extends LavaCowEntity> entityType, Level worldIn)
    {
        super(entityType, worldIn);
    }
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.BLAZE_POWDER), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
	}
	
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, (double)0.2F);
	}	
		
    public static boolean checkLavaCowSpawnRules(EntityType<? extends LavaCowEntity> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return true;
    }
	
    private boolean isWalkingonLand() {
    	return this.distanceToSqr(this.xOld, this.yOld, this.zOld) > 0.0D && !this.isInWaterRainOrBubble() && this.onGround();
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep() {
        super.aiStep();

        if (this.isWalkingonLand()) {       	
        	for (int j = 0; j < 2; ++j) {
                float f = this.random.nextFloat() * ((float)Math.PI * 2F);
                float f1 = this.random.nextFloat() * 0.5F;
                double f2 = Math.sin(f) * f1;
                double f3 = Math.cos(f) * f1;
                Level world = this.level();
                SimpleParticleType enumparticletypes = ParticleTypes.FLAME;
                double d0 = this.getX() + f2;
                double d1 = this.getZ() + f3;
                world.addParticle(enumparticletypes, d0, this.getBoundingBox().minY + (double)f1, d1, 0.0D, 0.0D, 0.0D);
            }
        }
    }
	
	@Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
       ItemStack itemstack = player.getItemInHand(hand);
       if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
          player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
          ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, Items.LAVA_BUCKET.getDefaultInstance());
          player.setItemInHand(hand, itemstack1);
          return InteractionResult.sidedSuccess(this.level().isClientSide());
       } else {
          return super.mobInteract(player, hand);
       }
    }
	
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData livingdata, @Nullable CompoundTag tag) {    	
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Lavacow_Health.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(level, difficulty, spawnType, livingdata, tag);
    }
		
	@Override
	public LavaCowEntity getBreedOffspring(ServerLevel worldIn, AgeableMob ageable) {
	    return FUREntityRegistry.LAVACOW.get().create(worldIn);
	}
	
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
	@Override
    public boolean isFood(ItemStack stack) {
		return stack.getItem() == Items.BLAZE_POWDER;
    }
}
