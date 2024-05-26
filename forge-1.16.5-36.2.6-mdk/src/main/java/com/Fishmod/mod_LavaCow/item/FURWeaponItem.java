package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.tameable.FURTameableEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.unburied.UnburiedEntity;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREnchantmentRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMultimap.Builder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURWeaponItem extends SwordItem {

	private Item repair_material;
	private float Damage;
	protected float efficiency;
	private String Tooltip = null;
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL, Blocks.FARMLAND, Blocks.GRASS_BLOCK, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.RED_SAND, Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.SOUL_SOIL);
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	
	public FURWeaponItem(Properties PropertiesIn, String registryName, IItemTier materialIn, int damageIn, float attackspeedIn, Item repair) {
		super(materialIn, damageIn, attackspeedIn, PropertiesIn);
        this.setRegistryName(registryName);
        this.Damage = (float)damageIn + 3.0F;
        this.repair_material = repair;
        this.efficiency = materialIn.getSpeed();
        this.Tooltip = "tootip." + registryName;
        Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.Damage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackspeedIn, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();   
	}
	
	@Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (getToolTypes(stack).stream().anyMatch(e -> state.isToolEffective(e))) return this.efficiency;
        return stack.getItem().equals(FURItemRegistry.UNDERTAKER_SHOVEL) && EFFECTIVE_ON.contains(state.getBlock()) ? this.efficiency : 1.0F;
    }
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {				
		if (entityIn instanceof LivingEntity && stack.getItem() == FURItemRegistry.FROZEN_DAGGER && entityIn.isInWaterRainOrBubble() && worldIn.random.nextInt(50) < 2)
			stack.setDamageValue(java.lang.Math.max(stack.getDamageValue() - 1, 0));
	}
	
	/**
     * Returns the amount of damage this item will deal. One heart of damage is equal to 2 damage points.
     */
	@Override
	public float getDamage() {
        return this.Damage;
    }
	   
	@Override
    public boolean hasContainerItem(ItemStack stack) {
        return (stack.getItem() == FURItemRegistry.MOLTENPAN || stack.getItem() == FURItemRegistry.SOULFIREPAN) && stack.getDamageValue() < stack.getMaxDamage();
    }
    
	@Override
    public ItemStack getContainerItem(ItemStack itemStack) {
		if (this.hasContainerItem(itemStack)) {
			ItemStack result = itemStack.copy();
			result.setDamageValue(itemStack.getDamageValue() + 8);
			return result;
		} 
		
		return ItemStack.EMPTY;
    }
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		if (stack.getItem() == FURItemRegistry.SPECTRAL_DAGGER) {
			return this.getItemStackLimit(stack) == 1;
		}
		
		return super.isEnchantable(stack);
	}
	
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
    	if (stack.getItem() == FURItemRegistry.SPECTRAL_DAGGER && enchantment.category.equals(EnchantmentType.BREAKABLE)) {
    		return false;
    	}
    	
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
	
	/**
	* Called when this item is used when targetting a Block
	*/
	@Override
	public ActionResultType useOn(ItemUseContext p_195939_1_) {
		/*if (!p_195939_1_.getLevel().isClientSide()) {
			Biome biome = p_195939_1_.getLevel().getBiome(p_195939_1_.getClickedPos());
			for (Spawners E: biome.getMobSettings().getMobs(EntityClassification.MONSTER)) {
				System.out.println(biome.getRegistryName() + ": " + E.type.getRegistryName() + " " + E.weight);
			}
			for (Spawners E: biome.getMobSettings().getMobs(EntityClassification.CREATURE)) {
				System.out.println(biome.getRegistryName() + ": " + E.type.getRegistryName() + " " + E.weight);
			}
			for (Spawners E: biome.getMobSettings().getMobs(EntityClassification.AMBIENT)) {
				System.out.println(biome.getRegistryName() + ": " + E.type.getRegistryName() + " " + E.weight);
			}
			for (Spawners E: biome.getMobSettings().getMobs(EntityClassification.WATER_AMBIENT)) {
				System.out.println(biome.getRegistryName() + ": " + E.type.getRegistryName() + " " + E.weight);
			}
			for (Spawners E: biome.getMobSettings().getMobs(EntityClassification.WATER_CREATURE)) {
				System.out.println(biome.getRegistryName() + ": " + E.type.getRegistryName() + " " + E.weight);
			}
		}*/
		
		return super.useOn(p_195939_1_);
	}
	
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		float f = (float) attacker.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
		
		if (attacker instanceof PlayerEntity && stack.getItem() == FURItemRegistry.REAPERS_SCYTHE) {
            float f3 = 1.0F + EnchantmentHelper.getSweepingDamageRatio(attacker) * f;

            for (LivingEntity LivingEntity : attacker.level.getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(2.0D, 0.25D, 2.0D))) {
                if (LivingEntity != attacker && LivingEntity != target && !attacker.isAlliedTo(LivingEntity) && attacker.distanceToSqr(LivingEntity) < 16.0D) {
                    LivingEntity.knockback(0.4F, (double)MathHelper.sin(attacker.yRot * 0.017453292F), (double)(-MathHelper.cos(attacker.yRot * 0.017453292F)));
                    LivingEntity.hurt(DamageSource.playerAttack((PlayerEntity) attacker), f3);
                }
            }

            attacker.level.playSound((PlayerEntity)null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, attacker.getSoundSource(), 1.0F, 1.0F);
            ((PlayerEntity) attacker).sweepAttack();
		} else if (attacker instanceof PlayerEntity && stack.getItem() == FURItemRegistry.FAMINE) {
			((PlayerEntity)attacker).getFoodData().eat(attacker.hasEffect(Effects.HUNGER) ? 2 : 1, 0.0F);
		} else if (stack.getItem() == FURItemRegistry.MOLTENPAN || stack.getItem() == FURItemRegistry.SOULFIREPAN) {
			target.playSound(SoundEvents.ANVIL_PLACE, 1.0F, 1.0F);
		} else if (stack.getItem() == FURItemRegistry.SKELETONKING_MACE) {
        	target.addEffect(new EffectInstance(FUREffectRegistry.FRAGILE, 200, 4));
		} else if (stack.getItem() == FURItemRegistry.MOLTENHAMMER || stack.getItem() == FURItemRegistry.MOLTENPAN || stack.getItem() == FURItemRegistry.SOULFIREHAMMER || stack.getItem() == FURItemRegistry.SOULFIREPAN) {
			int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);			
			target.setSecondsOnFire((i + 2) * 4);
		} else if (stack.getItem() == FURItemRegistry.VESPA_DAGGER) {
			int i = EnchantmentHelper.getItemEnchantmentLevel(FUREnchantmentRegistry.POISONOUS, stack);			
			target.addEffect(new EffectInstance(Effects.POISON, 8 * 20, i + 1));
		}
				
        return super.hurtEnemy(stack, target, attacker);
    }
	
	public static void LavaBurst(World worldIn, double x, double y, double z, double radius, BasicParticleType particleIn) {		
		double NumberofParticles = radius * 8.0D;
		
		for(double i = 0.0D; i < NumberofParticles; i++) {
			double d0 = x + radius * MathHelper.sin((float) (i / NumberofParticles * 360.0f));
            double d1 = (double)(y + 1);
            double d2 = z + radius * MathHelper.cos((float) (i / NumberofParticles * 360.0f));
            
            worldIn.addParticle(particleIn, d0, d1, d2, 0.0D, 0.0D, 0.0D); 
		}
	}
	
	public static <T extends FURTameableEntity> void SummonMinion(PlayerEntity playerIn, int[] enchantmentIn, World worldIn, BlockPos blockpos, EntityType<T> entityIn, int limitLife, int skin) {
		if (worldIn instanceof ServerWorld) {
			FURTameableEntity entity = (FURTameableEntity)SpawnUtil.trySpawnEntity(entityIn, ((ServerWorld) worldIn), blockpos);  
			
			if (entity != null) {
		        CompoundNBT CompoundNBT = new CompoundNBT();
		             	              
		    	CompoundNBT.putInt("fire_aspect", enchantmentIn[0]);
		    	CompoundNBT.putInt("sharpness", enchantmentIn[1]);
		    	CompoundNBT.putInt("knockback", enchantmentIn[2]);
		    	CompoundNBT.putInt("bane_of_arthropods", enchantmentIn[3]);
		    	CompoundNBT.putInt("smite", enchantmentIn[4]);
		    	CompoundNBT.putInt("unbreaking", enchantmentIn[8]);
		    	CompoundNBT.putInt("lifesteal", enchantmentIn[5]);
		    	CompoundNBT.putInt("poisonous", enchantmentIn[6]);
		    	CompoundNBT.putInt("corrosive", enchantmentIn[7]);
		    	
		    	entity.readAdditionalSaveData(CompoundNBT);  	
		    	entity.tame(playerIn);
		        entity.setLimitedLife(limitLife);
		        entity.setSkin(skin);		       		        
		        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getMaxHealth() * ((10.0D - (double)enchantmentIn[9]) / 10.0D));
		        entity.setHealth(entity.getMaxHealth());
		        		        
		        if (entity instanceof UnburiedEntity) {
		        	entity.level.broadcastEntityEvent(entity, (byte)32);
		        }
			}
		}
	}
	
    /**
     * Called when the equipped item is right clicked.
     */
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		int[] enchantment_list = new int[10];		
		enchantment_list[0] = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, playerIn.getItemInHand(handIn));
		enchantment_list[1] = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, playerIn.getItemInHand(handIn));
		enchantment_list[2] = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, playerIn.getItemInHand(handIn));
		enchantment_list[3] = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, playerIn.getItemInHand(handIn));
		enchantment_list[4] = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SMITE, playerIn.getItemInHand(handIn));
		enchantment_list[5] = EnchantmentHelper.getItemEnchantmentLevel(FUREnchantmentRegistry.LIFESTEAL, playerIn.getItemInHand(handIn));
		enchantment_list[6] = EnchantmentHelper.getItemEnchantmentLevel(FUREnchantmentRegistry.POISONOUS, playerIn.getItemInHand(handIn));
		enchantment_list[7] = EnchantmentHelper.getItemEnchantmentLevel(FUREnchantmentRegistry.CORROSIVE, playerIn.getItemInHand(handIn));
		enchantment_list[8] = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, playerIn.getItemInHand(handIn));
		enchantment_list[9] = EnchantmentHelper.getItemEnchantmentLevel(FUREnchantmentRegistry.DOMINION, playerIn.getItemInHand(handIn));
		
    	if (playerIn.getItemInHand(handIn).getItem() == FURItemRegistry.SLUDGE_WAND && worldIn instanceof ServerWorld) { 
    		BlockPos blockpos = new BlockPos(playerIn.getX() + playerIn.getLookAngle().x, playerIn.getY() + 0.2F, playerIn.getZ() + playerIn.getLookAngle().z);
    		for (int i = 0; i < 1 + enchantment_list[9]; i++) {
    			FURWeaponItem.SummonMinion(playerIn, enchantment_list, worldIn, blockpos, FUREntityRegistry.LILSLUDGE, FURConfig.LilSludge_Lifespan.get() * 20, (enchantment_list[0] > 0) ? 1 : 0);
    		}
    		
            for (int j = 0; j < 4; ++j) {
            	double d0 = blockpos.getX() + (playerIn.getRandom().nextDouble() * 2.0D) - 1.0D;
            	double d1 = blockpos.getY() + (playerIn.getRandom().nextDouble() * 2.0D);
            	double d2 = blockpos.getZ() + (playerIn.getRandom().nextDouble() * 2.0D) - 1.0D;
            	((ServerWorld) worldIn).sendParticles(enchantment_list[0] > 0 ? ParticleTypes.FLAME : ParticleTypes.SPLASH, d0, d1, d2, 15, 0.0D, 0.0D, 0.0D, 0.0D);            	
            }
            
            playerIn.getItemInHand(handIn).hurtAndBreak(8, playerIn, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
    		});
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
            
			return ActionResult.pass(playerIn.getItemInHand(handIn));
        }
        
        if (playerIn.getItemInHand(handIn).getItem() == FURItemRegistry.MOLTENHAMMER) {
			double radius = 4.0D;

			List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().inflate(radius));
			for (Entity entity1 : list) {
				if ((entity1 instanceof LivingEntity && !(entity1 instanceof TameableEntity)) || (entity1 instanceof TameableEntity && !((TameableEntity)entity1).isOwnedBy(playerIn)) || (entity1 instanceof PlayerEntity && FURConfig.MoltenHammer_PVP.get())) {
					entity1.setSecondsOnFire(2 * enchantment_list[0]);
					entity1.hurt(DamageSource.mobAttack(playerIn) , 8.0F + (float)enchantment_list[1]
							+ (((LivingEntity) entity1).getMobType().equals(CreatureAttribute.ARTHROPOD) ? (float)enchantment_list[3] : 0)
							+ (((LivingEntity) entity1).getMobType().equals(CreatureAttribute.UNDEAD) ? (float)enchantment_list[4] : 0));
					
					if (enchantment_list[2] > 0)
						((LivingEntity)entity1).setDeltaMovement(((LivingEntity)entity1).getDeltaMovement().add((float)enchantment_list[2] * 0.5F, (playerIn.getX() - entity1.getX())/playerIn.distanceTo(entity1), (playerIn.getZ() - entity1.getZ())/playerIn.distanceTo(entity1)));
					
		            if (enchantment_list[3] > 0 && (((LivingEntity) entity1).getMobType().equals(CreatureAttribute.ARTHROPOD))) {
		                int i = 20 + worldIn.random.nextInt(10 * enchantment_list[3]);
		                ((LivingEntity)entity1).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, i, 3));
		            }
		            
		            if (enchantment_list[6] > 0)
		    			((LivingEntity)entity1).addEffect(new EffectInstance(Effects.POISON, 8*20, enchantment_list[6] - 1));
		            
		            if (enchantment_list[7] > 0)
		            	((LivingEntity)entity1).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 4*20, enchantment_list[7] - 1));
				}
			}
			LavaBurst(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), radius, ParticleTypes.FLAME);
            playerIn.getItemInHand(handIn).hurtAndBreak(16, playerIn, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
    		});
			playerIn.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);
			playerIn.getCooldowns().addCooldown(this, 80);
			
			return ActionResult.pass(playerIn.getItemInHand(handIn));
		}
        
        if (playerIn.getItemInHand(handIn).getItem() == FURItemRegistry.SOULFIREHAMMER) {
			double radius = 4.0D;

			List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().inflate(radius));
			for(Entity entity1 : list) {
				if ((entity1 instanceof LivingEntity && !(entity1 instanceof TameableEntity)) || (entity1 instanceof TameableEntity && !((TameableEntity)entity1).isOwnedBy(playerIn)) || (entity1 instanceof PlayerEntity && FURConfig.MoltenHammer_PVP.get())) {
					entity1.setSecondsOnFire(2 * enchantment_list[0]);
					entity1.hurt(DamageSource.mobAttack(playerIn) , 10.0F + (float)enchantment_list[1]
							+ (((LivingEntity) entity1).getMobType().equals(CreatureAttribute.ARTHROPOD) ? (float)enchantment_list[3] : 0)
							+ (((LivingEntity) entity1).getMobType().equals(CreatureAttribute.UNDEAD) ? (float)enchantment_list[4] : 0));
					
					if (enchantment_list[2] > 0)
						((LivingEntity)entity1).setDeltaMovement(((LivingEntity)entity1).getDeltaMovement().add((float)enchantment_list[2] * 0.5F, (playerIn.getX() - entity1.getX())/playerIn.distanceTo(entity1), (playerIn.getZ() - entity1.getZ())/playerIn.distanceTo(entity1)));
					
		            if (enchantment_list[3] > 0 && (((LivingEntity) entity1).getMobType().equals(CreatureAttribute.ARTHROPOD))) {
		                int i = 20 + worldIn.random.nextInt(10 * enchantment_list[3]);
		                ((LivingEntity)entity1).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, i, 3));
		            }
		            
		            if (enchantment_list[6] > 0)
		    			((LivingEntity)entity1).addEffect(new EffectInstance(Effects.POISON, 8*20, enchantment_list[6] - 1));
		            
		            if (enchantment_list[7] > 0)
		            	((LivingEntity)entity1).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 4*20, enchantment_list[7] - 1));
				}
			}
			LavaBurst(worldIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), radius, ParticleTypes.SOUL_FIRE_FLAME);
            playerIn.getItemInHand(handIn).hurtAndBreak(16, playerIn, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
    		});
			playerIn.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);
			playerIn.getCooldowns().addCooldown(this, 80);
			
			return ActionResult.pass(playerIn.getItemInHand(handIn));
		}
        
        if (playerIn.getItemInHand(handIn).getItem() == FURItemRegistry.BEAST_CLAW && playerIn.isOnGround()) {
        	Vector3d lookVec = playerIn.getLookAngle();
        	
        	if(playerIn.getOffhandItem().getItem() == FURItemRegistry.BEAST_CLAW && playerIn.getMainHandItem().getItem() == FURItemRegistry.BEAST_CLAW) {
        		playerIn.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 3 * 20, 0));
        		playerIn.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 3 * 20, 0));
        	}
        	
        	playerIn.setDeltaMovement(playerIn.getDeltaMovement().add(lookVec.x * 1.5D, lookVec.y * 0.15D + 0.4D, lookVec.z * 1.5D));
            playerIn.getItemInHand(handIn).hurtAndBreak(8, playerIn, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
    		});
			playerIn.getCooldowns().addCooldown(this, 120);
			
			return ActionResult.pass(playerIn.getItemInHand(handIn));
		}
        
        if (playerIn.getItemInHand(handIn).getItem() == FURItemRegistry.UNDERTAKER_SHOVEL && worldIn instanceof ServerWorld) {
            for (int i = 0; i < 4 + enchantment_list[9]; ++i) {
                BlockPos blockpos = playerIn.blockPosition().offset(-6 + Item.random.nextInt(12), 0, -6 + Item.random.nextInt(12));
                FURWeaponItem.SummonMinion(playerIn, enchantment_list, worldIn, blockpos, FUREntityRegistry.UNBURIED, FURConfig.Unburied_Lifespan.get() * 20, 0);
            }
            
            playerIn.getItemInHand(handIn).hurtAndBreak(63, playerIn, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
    		});
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
			
        	return ActionResult.pass(playerIn.getItemInHand(handIn));
		}
        
        if (playerIn.getItemInHand(handIn).getItem() == FURItemRegistry.SCARAB_SCEPTER && worldIn instanceof ServerWorld) {       
        	Vector3d lookVec = playerIn.getLookAngle();
        	
            for (int i = 0; i < 4 + enchantment_list[9]; ++i) {
                BlockPos blockpos = playerIn.blockPosition().offset(lookVec.x * 3.0D + (Item.random.nextDouble() * 4.0D - 2.0D), 0, lookVec.z * 3.0D + (Item.random.nextDouble() * 4.0D - 2.0D));
                FURWeaponItem.SummonMinion(playerIn, enchantment_list, worldIn, blockpos, FUREntityRegistry.SCARAB, FURConfig.Scarab_Lifespan.get() * 20, 0);
            }
			
            playerIn.getItemInHand(handIn).hurtAndBreak(8, playerIn, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
    		});
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
            
			return ActionResult.pass(playerIn.getItemInHand(handIn));
        }
        
        if (playerIn.getItemInHand(handIn).getItem() == FURItemRegistry.ANKH_SCEPTER && worldIn instanceof ServerWorld) {
            for (int i = 0; i < 4 + enchantment_list[9]; ++i) {
                BlockPos blockpos = playerIn.blockPosition().offset(-6 + Item.random.nextInt(12), 0, -6 + Item.random.nextInt(12));
                FURWeaponItem.SummonMinion(playerIn, enchantment_list, worldIn, blockpos, FUREntityRegistry.MUMMY, FURConfig.Mummy_Lifespan.get() * 20, 0);
            }
            
            playerIn.getItemInHand(handIn).hurtAndBreak(63, playerIn, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
    		});
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
			
        	return ActionResult.pass(playerIn.getItemInHand(handIn));
		}
        
        if (playerIn.getItemInHand(handIn).getItem() == FURItemRegistry.FUNGAL_STAFF && worldIn instanceof ServerWorld) {
            for (int i = 0; i < 4 + enchantment_list[9]; ++i) {
                BlockPos blockpos = playerIn.blockPosition().offset(-6 + Item.random.nextInt(12), 0, -6 + Item.random.nextInt(12));
                FURWeaponItem.SummonMinion(playerIn, enchantment_list, worldIn, blockpos, FUREntityRegistry.MYCOSIS, FURConfig.ZombieMushroom_Lifespan.get() * 20, 0);
            }
            
            playerIn.getItemInHand(handIn).hurtAndBreak(63, playerIn, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
    		});
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
			
        	return ActionResult.pass(playerIn.getItemInHand(handIn));
		}
        
        if (playerIn.getItemInHand(handIn).getItem() == FURItemRegistry.FROZEN_GRIP && worldIn instanceof ServerWorld) {
            for (int i = 0; i < 4 + enchantment_list[9]; ++i) {
                BlockPos blockpos = playerIn.blockPosition().offset(-6 + Item.random.nextInt(12), 0, -6 + Item.random.nextInt(12));
                FURWeaponItem.SummonMinion(playerIn, enchantment_list, worldIn, blockpos, FUREntityRegistry.FRIGID, FURConfig.ZombieFrozen_Lifespan.get() * 20, 0);
            }
            
            playerIn.getItemInHand(handIn).hurtAndBreak(63, playerIn, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
    		});
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            playerIn.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
			
        	return ActionResult.pass(playerIn.getItemInHand(handIn));
		}        

    	return super.use(worldIn, playerIn, handIn);
    }
	
	@Override
	public boolean isValidRepairItem(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.getItem().equals(this.repair_material);
	}
	
	public FURWeaponItem setNoDescription() {
		this.Tooltip = null;
		return this;
	}
	
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (stack.getItem().equals(FURItemRegistry.BONESWORD)) {
			tooltip.add(new TranslationTextComponent(this.Tooltip, FURConfig.BoneSword_Damage.get(), FURConfig.BoneSword_DamageCap.get()).withStyle(TextFormatting.YELLOW));
		} else if (stack.getItem().equals(FURItemRegistry.BEAST_CLAW)) {
			tooltip.add(new TranslationTextComponent(this.Tooltip + ".desc0").withStyle(TextFormatting.YELLOW));
			tooltip.add(new TranslationTextComponent(this.Tooltip + ".desc1").withStyle(TextFormatting.YELLOW));
		} else if (this.Tooltip != null)
			tooltip.add(new TranslationTextComponent(this.Tooltip).withStyle(TextFormatting.YELLOW));
	}
	
    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
	@Override 
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return equipmentSlot == EquipmentSlotType.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }
}
