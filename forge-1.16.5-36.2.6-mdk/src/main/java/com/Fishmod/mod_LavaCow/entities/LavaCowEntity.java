package com.Fishmod.mod_LavaCow.entities;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LavaCowEntity extends CowEntity
{	
	public LavaCowEntity(EntityType<? extends LavaCowEntity> p_i48567_1_, World worldIn)
    {
        super(p_i48567_1_, worldIn);
    }
	
	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
		this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(Items.BLAZE_POWDER), false));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
	}
	
	public float getBrightness() {
		return 1.0F;
	}
	
	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, FURConfig.Lavacow_Health.get()).add(Attributes.MOVEMENT_SPEED, (double)0.2F);
	}	
		
    public static boolean checkLavaCowSpawnRules(EntityType<? extends LavaCowEntity> p_223316_0_, IWorld p_223316_1_, SpawnReason p_223316_2_, BlockPos p_223316_3_, Random p_223316_4_) {
        return true;//SpawnUtil.isAllowedDimension(this.dimension);
    }
	
    private boolean isWalkingonLand()
    {
    	return this.distanceToSqr(this.xOld, this.yOld, this.zOld) > 0.0D && !this.isInWaterRainOrBubble() && this.onGround;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void aiStep()
    {
        super.aiStep();

        if (this.isWalkingonLand())
        {       	
        	for (int j = 0; j < 2; ++j)
            {
                float f = this.random.nextFloat() * ((float)Math.PI * 2F);
                float f1 = this.random.nextFloat() * 0.5F;
                float f2 = MathHelper.sin(f) * f1;
                float f3 = MathHelper.cos(f) * f1;
                World world = this.level;
                BasicParticleType enumparticletypes = ParticleTypes.FLAME;
                double d0 = this.getX() + (double)f2;
                double d1 = this.getZ() + (double)f3;
                world.addParticle(enumparticletypes, d0, this.getBoundingBox().minY + (double)f1, d1, 0.0D, 0.0D, 0.0D);
            }
        }
    }
	
	@Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
       ItemStack itemstack = player.getItemInHand(hand);
       if (itemstack.getItem() == Items.BUCKET && !this.isBaby()) {
          player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
          ItemStack itemstack1 = DrinkHelper.createFilledResult(itemstack, player, Items.LAVA_BUCKET.getDefaultInstance());
          player.setItemInHand(hand, itemstack1);
          return ActionResultType.sidedSuccess(this.level.isClientSide);
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
    public ILivingEntityData finalizeSpawn(IServerWorld p_213386_1_, DifficultyInstance difficulty, SpawnReason p_213386_3_, @Nullable ILivingEntityData livingdata, @Nullable CompoundNBT p_213386_5_) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(FURConfig.Lavacow_Health.get());
    	this.setHealth(this.getMaxHealth());
    	
    	return super.finalizeSpawn(p_213386_1_, difficulty, p_213386_3_, livingdata, p_213386_5_);
    }
		
	@Override
	public LavaCowEntity getBreedOffspring(ServerWorld worldIn, AgeableEntity ageable) {
	    return FUREntityRegistry.LAVACOW.create(worldIn);
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
