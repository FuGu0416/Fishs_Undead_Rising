package com.Fishmod.fur.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;
import com.Fishmod.fur.entities.tameable.unburied.UnburiedEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;

public class FURWeaponItem extends SwordItem {
    private static final UUID REACH_UUID = UUID.fromString("d4b8d5f2-9d6b-4a36-8c70-9f1db2b7e801");
	private Item repair_material;
	private float Damage;
	protected float efficiency;
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;
	boolean hasDesc;
	
	public FURWeaponItem(Properties properties, Tier material, int damage, float attackspeed, double reach, Item repair, Boolean hasDesc) {
		super(material, damage, attackspeed, properties);
        this.Damage = (float)damage + 3.0F;
        this.repair_material = repair;
        this.efficiency = material.getSpeed();
        this.hasDesc = hasDesc;
        Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (reach != 0.0D) builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(REACH_UUID, "Weapon modifier", reach, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.Damage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)attackspeed, AttributeModifier.Operation.ADDITION));        
        this.defaultModifiers = builder.build();  
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entityIn, int itemSlot, boolean isSelected) {				
		/*if (entityIn instanceof LivingEntity && stack.getItem() == FURItemRegistry.FROZEN_DAGGER && entityIn.isInWaterRainOrBubble() && level.random.nextInt(50) < 2) {
			stack.setDamageValue(java.lang.Math.max(stack.getDamageValue() - 1, 0));
		}*/
	}
	
	/**
     * Returns the amount of damage this item will deal. One heart of damage is equal to 2 damage points.
     */
	@Override
	public float getDamage() {
        return this.Damage;
    }
	   
	@Override
	public boolean isEnchantable(ItemStack stack) {
		/*if (stack.getItem() == FURItemRegistry.SPECTRAL_DAGGER) {
			return this.getItemStackLimit(stack) == 1;
		}*/
		
		return super.isEnchantable(stack);
	}
	
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
    	/*if (stack.getItem() == FURItemRegistry.SPECTRAL_DAGGER && enchantment.category.equals(EnchantmentType.BREAKABLE)) {
    		return false;
    	}*/
    	
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
	
	/**
	* Called when this item is used when targeting a Block
	*/
	@Override
	public InteractionResult useOn(UseOnContext p_195939_1_) {
		if (!p_195939_1_.getLevel().isClientSide()) {
			Holder<Biome> biome = p_195939_1_.getLevel().getBiome(p_195939_1_.getClickedPos());
			for (SpawnerData E: biome.get().getMobSettings().getMobs(MobCategory.MONSTER).unwrap()) {
				System.out.println(biome.get().toString() + ": " + E.type.toString() + " " + E.getWeight());
			}
			for (SpawnerData E: biome.get().getMobSettings().getMobs(MobCategory.CREATURE).unwrap()) {
				System.out.println(biome.get().toString() + ": " + E.type.toString() + " " + E.getWeight());
			}
			for (SpawnerData E: biome.get().getMobSettings().getMobs(MobCategory.AMBIENT).unwrap()) {
				System.out.println(biome.get().toString() + ": " + E.type.toString() + " " + E.getWeight());
			}
			for (SpawnerData E: biome.get().getMobSettings().getMobs(MobCategory.WATER_AMBIENT).unwrap()) {
				System.out.println(biome.get().toString() + ": " + E.type.toString() + " " + E.getWeight());
			}
			for (SpawnerData E: biome.get().getMobSettings().getMobs(MobCategory.WATER_CREATURE).unwrap()) {
				System.out.println(biome.get().toString() + ": " + E.type.toString() + " " + E.getWeight());
			}
		}
		
		return super.useOn(p_195939_1_);
	}
	
    @NotNull
    @Override
    public AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
    	if (stack.getItem() == FURItemRegistry.REAPERS_SCYTHE.get()) {
    		return target.getBoundingBox().inflate(2.0D, 0.25D, 2.0D);
    	} else {
    		return super.getSweepHitBox(stack, player, target);
    	}
    }
	
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {	
		if (attacker instanceof Player && stack.getItem() == FURItemRegistry.FAMINE.get()) {
			((Player)attacker).getFoodData().eat(attacker.hasEffect(MobEffects.HUNGER) ? 2 : 1, 0.0F);
		}/* else if (stack.getItem() == FURItemRegistry.SKELETONKING_MACE) {
        	target.addEffect(new EffectInstance(FUREffectRegistry.FRAGILE, 200, 4));
		}*/ else if (stack.getItem() == FURItemRegistry.MOLTEN_HAMMER.get()/* stack.getItem() == FURItemRegistry.SOULFIREHAMMER*/) {
			int i = attacker.getMainHandItem().getEnchantmentLevel(Enchantments.FIRE_ASPECT);			
			target.setSecondsOnFire((i + 2) * 4);
			target.playSound(SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.85F);
		}/* else if (stack.getItem() == FURItemRegistry.VESPA_DAGGER) {
			int i = player.getItemInHand(hand).getEnchantmentLevel(FUREnchantmentRegistry.POISONOUS, stack);			
			target.addEffect(new EffectInstance(Effects.POISON, 8 * 20, i + 1));
		}*/
				
        return super.hurtEnemy(stack, target, attacker);
    }
	
	public static <T extends FURTameableEntity> void SummonMinion(Player player, int[] enchantmentIn, Level level, BlockPos blockpos, EntityType<T> entityIn, int limitLife, int skin) {
		if (level instanceof ServerLevel) {
			FURTameableEntity entity = (FURTameableEntity)SpawnUtil.trySpawnEntity(entityIn, ((ServerLevel) level), blockpos);  
			
			if (entity != null) {
				CompoundTag CompoundNBT = new CompoundTag();
		             	              
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
		    	entity.tame(player);
		        entity.setLimitedLife(limitLife);
		        entity.setSkin(skin);		       		        
		        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getMaxHealth() * ((10.0D - (double)enchantmentIn[9]) / 10.0D));
		        entity.setHealth(entity.getMaxHealth());
		        		        
		        if (entity instanceof UnburiedEntity) {
		        	((UnburiedEntity)entity).setSpellcasting();
		        }
			}
		}
	}
	
    /**
     * Called when the equipped item is right clicked.
     */
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		int[] enchantment_list = new int[10];		
		enchantment_list[0] = player.getItemInHand(hand).getEnchantmentLevel(Enchantments.FIRE_ASPECT);
		enchantment_list[1] = player.getItemInHand(hand).getEnchantmentLevel(Enchantments.SHARPNESS);
		enchantment_list[2] = player.getItemInHand(hand).getEnchantmentLevel(Enchantments.KNOCKBACK);
		enchantment_list[3] = player.getItemInHand(hand).getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS);
		enchantment_list[4] = player.getItemInHand(hand).getEnchantmentLevel(Enchantments.SMITE);
		//enchantment_list[5] = player.getItemInHand(hand).getEnchantmentLevel(FUREnchantmentRegistry.LIFESTEAL);
		//enchantment_list[6] = player.getItemInHand(hand).getEnchantmentLevel(FUREnchantmentRegistry.POISONOUS);
		//enchantment_list[7] = player.getItemInHand(hand).getEnchantmentLevel(FUREnchantmentRegistry.CORROSIVE);
		enchantment_list[8] = player.getItemInHand(hand).getEnchantmentLevel(Enchantments.UNBREAKING);
		//enchantment_list[9] = player.getItemInHand(hand).getEnchantmentLevel(FUREnchantmentRegistry.DOMINION);
		
    	/*if (player.getItemInHand(hand).getItem() == FURItemRegistry.SLUDGE_WAND && level instanceof ServerWorld) { 
    		BlockPos blockpos = new BlockPos(player.getX() + player.getLookAngle().x, player.getY() + 0.2F, player.getZ() + player.getLookAngle().z);
    		for (int i = 0; i < 1 + enchantment_list[9]; i++) {
    			FURWeaponItem.SummonMinion(player, enchantment_list, level, blockpos, FUREntityRegistry.LILSLUDGE, FURConfig.LilSludge_Lifespan.get() * 20, (enchantment_list[0] > 0) ? 1 : 0);
    		}
    		
            for (int j = 0; j < 4; ++j) {
            	double d0 = blockpos.getX() + (player.getRandom().nextDouble() * 2.0D) - 1.0D;
            	double d1 = blockpos.getY() + (player.getRandom().nextDouble() * 2.0D);
            	double d2 = blockpos.getZ() + (player.getRandom().nextDouble() * 2.0D) - 1.0D;
            	((ServerWorld) level).sendParticles(enchantment_list[0] > 0 ? ParticleTypes.FLAME : ParticleTypes.SPLASH, d0, d1, d2, 15, 0.0D, 0.0D, 0.0D, 0.0D);            	
            }
            
            player.getItemInHand(hand).hurtAndBreak(8, player, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    		});
            player.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
            
			return InteractionResultHolder.pass(player.getItemInHand(hand));
        }*/
        
       /* if (player.getItemInHand(hand).getItem() == FURItemRegistry.SOULFIREHAMMER) {
			double radius = 4.0D;

			List<Entity> list = level.getEntities(player, player.getBoundingBox().inflate(radius));
			for(Entity entity1 : list) {
				if ((entity1 instanceof LivingEntity && !(entity1 instanceof TamableAnimal)) || (entity1 instanceof TamableAnimal && !((TamableAnimal)entity1).isOwnedBy(player)) || (entity1 instanceof PlayerEntity && FURConfig.MoltenHammer_PVP.get())) {
					entity1.setSecondsOnFire(2 * enchantment_list[0]);
					entity1.hurt(DamageSource.mobAttack(player) , 10.0F + (float)enchantment_list[1]
							+ (((LivingEntity) entity1).getMobType().equals(CreatureAttribute.ARTHROPOD) ? (float)enchantment_list[3] : 0)
							+ (((LivingEntity) entity1).getMobType().equals(CreatureAttribute.UNDEAD) ? (float)enchantment_list[4] : 0));
					
					if (enchantment_list[2] > 0)
						((LivingEntity)entity1).setDeltaMovement(((LivingEntity)entity1).getDeltaMovement().add((float)enchantment_list[2] * 0.5F, (player.getX() - entity1.getX())/player.distanceTo(entity1), (player.getZ() - entity1.getZ())/player.distanceTo(entity1)));
					
		            if (enchantment_list[3] > 0 && (((LivingEntity) entity1).getMobType().equals(CreatureAttribute.ARTHROPOD))) {
		                int i = 20 + level.random.nextInt(10 * enchantment_list[3]);
		                ((LivingEntity)entity1).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, i, 3));
		            }
		            
		            if (enchantment_list[6] > 0)
		    			((LivingEntity)entity1).addEffect(new EffectInstance(Effects.POISON, 8*20, enchantment_list[6] - 1));
		            
		            if (enchantment_list[7] > 0)
		            	((LivingEntity)entity1).addEffect(new EffectInstance(FUREffectRegistry.CORRODED, 4*20, enchantment_list[7] - 1));
				}
			}
			LavaBurst(level, player.getX(), player.getY(), player.getZ(), radius, ParticleTypes.SOUL_FIRE_FLAME);
            player.getItemInHand(hand).hurtAndBreak(16, player, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    		});
			player.playSound(SoundEvents.GENERIC_EXPLODE, 1.0F, 1.0F);
			player.getCooldowns().addCooldown(this, 80);
			
			return InteractionResultHolder.pass(player.getItemInHand(hand));
		}
        
        if (player.getItemInHand(hand).getItem() == FURItemRegistry.BEAST_CLAW && player.isOnGround()) {
        	Vector3d lookVec = player.getLookAngle();
        	
        	if(player.getOffhandItem().getItem() == FURItemRegistry.BEAST_CLAW && player.getMainHandItem().getItem() == FURItemRegistry.BEAST_CLAW) {
        		player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 3 * 20, 0));
        		player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 3 * 20, 0));
        	}
        	
        	player.setDeltaMovement(player.getDeltaMovement().add(lookVec.x * 1.5D, lookVec.y * 0.15D + 0.4D, lookVec.z * 1.5D));
            player.getItemInHand(hand).hurtAndBreak(8, player, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    		});
			player.getCooldowns().addCooldown(this, 120);
			
			return InteractionResultHolder.pass(player.getItemInHand(hand));
		}*/
        
        if (player.getItemInHand(hand).getItem() == FURItemRegistry.UNDERTAKER_SHOVEL.get() && level instanceof ServerLevel) {
            for (int i = 0; i < 4 + enchantment_list[9]; ++i) {
                BlockPos blockpos = player.blockPosition().offset(-6 + player.getRandom().nextInt(12), 0, -6 + player.getRandom().nextInt(12));
                FURWeaponItem.SummonMinion(player, enchantment_list, level, blockpos, FUREntityRegistry.UNBURIED.get(), 20/*FURConfig.Unburied_Lifespan.get()*/ * 20, 0);
            }
            
            player.getItemInHand(hand).hurtAndBreak(63, player, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    		});
            //player.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            //player.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL.get(), 60/*FURConfig.Undertaker_Shovel_Cooldown.get()*/ * 20);
            //player.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            //player.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            //player.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
			
        	return InteractionResultHolder.pass(player.getItemInHand(hand));
		}
        
        /*if (player.getItemInHand(hand).getItem() == FURItemRegistry.SCARAB_SCEPTER && level instanceof ServerWorld) {       
        	Vector3d lookVec = player.getLookAngle();
        	
            for (int i = 0; i < 4 + enchantment_list[9]; ++i) {
                BlockPos blockpos = player.blockPosition().offset(lookVec.x * 3.0D + (Item.random.nextDouble() * 4.0D - 2.0D), 0, lookVec.z * 3.0D + (Item.random.nextDouble() * 4.0D - 2.0D));
                FURWeaponItem.SummonMinion(player, enchantment_list, level, blockpos, FUREntityRegistry.SCARAB, FURConfig.Scarab_Lifespan.get() * 20, 0);
            }
			
            player.getItemInHand(hand).hurtAndBreak(8, player, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    		});
            player.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
            
			return InteractionResultHolder.pass(player.getItemInHand(hand));
        }
        
        if (player.getItemInHand(hand).getItem() == FURItemRegistry.ANKH_SCEPTER && level instanceof ServerWorld) {
            for (int i = 0; i < 4 + enchantment_list[9]; ++i) {
                BlockPos blockpos = player.blockPosition().offset(-6 + Item.random.nextInt(12), 0, -6 + Item.random.nextInt(12));
                FURWeaponItem.SummonMinion(player, enchantment_list, level, blockpos, FUREntityRegistry.MUMMY, FURConfig.Mummy_Lifespan.get() * 20, 0);
            }
            
            player.getItemInHand(hand).hurtAndBreak(63, player, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    		});
            player.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
			
        	return InteractionResultHolder.pass(player.getItemInHand(hand));
		}
        
        if (player.getItemInHand(hand).getItem() == FURItemRegistry.FUNGAL_STAFF && level instanceof ServerWorld) {
            for (int i = 0; i < 4 + enchantment_list[9]; ++i) {
                BlockPos blockpos = player.blockPosition().offset(-6 + Item.random.nextInt(12), 0, -6 + Item.random.nextInt(12));
                FURWeaponItem.SummonMinion(player, enchantment_list, level, blockpos, FUREntityRegistry.MYCOSIS, FURConfig.ZombieMushroom_Lifespan.get() * 20, 0);
            }
            
            player.getItemInHand(hand).hurtAndBreak(63, player, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    		});
            player.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
			
        	return InteractionResultHolder.pass(player.getItemInHand(hand));
		}
        
        if (player.getItemInHand(hand).getItem() == FURItemRegistry.FROZEN_GRIP && level instanceof ServerWorld) {
            for (int i = 0; i < 4 + enchantment_list[9]; ++i) {
                BlockPos blockpos = player.blockPosition().offset(-6 + Item.random.nextInt(12), 0, -6 + Item.random.nextInt(12));
                FURWeaponItem.SummonMinion(player, enchantment_list, level, blockpos, FUREntityRegistry.FRIGID, FURConfig.ZombieFrozen_Lifespan.get() * 20, 0);
            }
            
            player.getItemInHand(hand).hurtAndBreak(63, player, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    		});
            player.getCooldowns().addCooldown(FURItemRegistry.SLUDGE_WAND, FURConfig.SludgeWand_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER, FURConfig.ScarabScepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL, FURConfig.Undertaker_Shovel_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER, FURConfig.Ankh_Scepter_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF, FURConfig.Fungal_Staff_Cooldown.get() * 20);
            player.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP, FURConfig.Frozen_Grip_Cooldown.get() * 20);
			
        	return InteractionResultHolder.pass(player.getItemInHand(hand));
		}*/        

    	return super.use(level, player, hand);
    }
	
	@Override
	public boolean isValidRepairItem(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.getItem().equals(this.repair_material);
	}
	
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		if (stack.getItem().equals(FURItemRegistry.BONE_SWORD.get())) {
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc", 5/*FURConfig.BoneSword_Damage.get()*/, 2/*FURConfig.BoneSword_DamageCap.get()*/).withStyle(ChatFormatting.YELLOW));
		/*} else if (stack.getItem().equals(FURItemRegistry.BEAST_CLAW)) {
			tooltip.add(new TranslationTextComponent(this.Tooltip + ".desc0").withStyle(TextFormatting.YELLOW));
			tooltip.add(new TranslationTextComponent(this.Tooltip + ".desc1").withStyle(TextFormatting.YELLOW));*/
		} else if (this.hasDesc)
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc").withStyle(ChatFormatting.YELLOW));
	}
	
    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
	@Override 
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }
}
