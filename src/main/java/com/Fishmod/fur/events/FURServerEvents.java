package com.Fishmod.fur.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.data.providers.FURBiomeTagsProvider;
import com.Fishmod.fur.data.providers.FUREntityTypeTagsProvider;
import com.Fishmod.fur.entities.GhoulEntity;
import com.Fishmod.fur.entities.ParasiteEntity;
import com.Fishmod.fur.entities.flying.VespaEntity;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;
import com.Fishmod.fur.entities.tameable.MimicEntity;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;
import com.Fishmod.fur.item.ChitinArmorItem;
import com.Fishmod.fur.item.VespaShieldItem;
import com.Fishmod.fur.item.FamineArmorItem;
import com.Fishmod.fur.item.GhostlyArmorItem;
import com.Fishmod.fur.item.MoltenArmorItem;
import com.Fishmod.fur.worldgen.biome.FURBiomeSourceAccessor;
import com.Fishmod.fur.worldgen.biome.FURMultiNoiseBiomeSourceAccessor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
//@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class FURServerEvents {
	
    /**
     * Custom entity death event, using for manipulating vanilla entities loots or onDeath triggers.
     * Example: Spawn swarm of parasites when a zombie dies (10% chance)
     */
    @SubscribeEvent
    public void onEDeath(LivingDeathEvent event) {
    	LivingEntity entity = event.getEntity();
    	Entity killer = event.getSource().getDirectEntity();
	    Level world = entity.level();
		
    	/**
         * Give a chance to spawn horde of Parasites when a listed target dies.
         **/
    	if (world instanceof ServerLevel serverLevel && !entity.isInWaterOrBubble() &&
    			((entity.attackable() && entity.getType().is(FUREntityTypeTagsProvider.PARASITE_TARGETS))
    					&& (world.random.nextInt(100) < FURConfig.pSpawnRate_Parasite.get()) && (!(entity instanceof TamableAnimal tamed) || !tamed.isTame())
    			|| entity.hasEffect(FUREffectRegistry.INFESTED.get()))) {
    		MobEffectInstance infestedEffect = entity.getEffect(FUREffectRegistry.INFESTED.get());
    		int var6 = infestedEffect != null ? infestedEffect.getAmplifier() : 0;
    		int var2 = 3 + world.random.nextInt(3);
    		int spawnCount = var2 + (var6 * (1 + world.random.nextInt(3)));
    		float var4,var5;
    		ParasiteEntity passenger = (ParasiteEntity) SpawnUtil.gotRiderEntity(entity.getPassengers(), FUREntityRegistry.PARASITE.get());
    		var biome = world.getBiome(entity.blockPosition());
    		double ex = entity.getX(), ey = entity.getY(), ez = entity.getZ();

    		for (int var3 = 0; var3 < spawnCount; ++var3) {
    			var4 = ((float)(var3 % 2) - 0.5F) / 4.0F;
                var5 = ((float)(var3 / 2) - 0.5F) / 4.0F;

        		ParasiteEntity ParasiteEntity = SpawnUtil.trySpawnEntity(FUREntityRegistry.PARASITE.get(), serverLevel, new BlockPos((int)(ex + var4), (int)ey + 1, (int)(ez + var5)));

        		if (ParasiteEntity != null) {
	        		if (passenger != null) {
	        			ParasiteEntity.setSkin(passenger.getSkin());
	        		} else if (killer instanceof VespaEntity vespa) {
	        			ParasiteEntity.setSkin(2);
	        			if (vespa.isTame()) {
	        				ParasiteEntity.setSummoned(true);
	        			}	        			
	        		} else if (biome.containsTag(Tags.Biomes.IS_DESERT) || biome.containsTag(BiomeTags.IS_BADLANDS)) {
	        			ParasiteEntity.setSkin(1);
	        		} else if (biome.containsTag(BiomeTags.IS_JUNGLE)) {
	        			ParasiteEntity.setSkin(2);
	        		} else {
	        			ParasiteEntity.setSkin(0);
	        		}
        		}
    		}
    	}

    	/**
         * Give a chance to spawn horde of Lampreys when a listed target dies.
         **/    	   	
    	if (world instanceof ServerLevel serverLevel && entity.isInWaterOrBubble() &&
    			(entity.getType().is(FUREntityTypeTagsProvider.LAMPREY_TARGETS) && (world.random.nextInt(100) < FURConfig.pSpawnRate_Lamprey.get())
    			|| entity.hasEffect(FUREffectRegistry.INFESTED.get()))) {
    		MobEffectInstance infestedEffect = entity.getEffect(FUREffectRegistry.INFESTED.get());
    		int var6 = infestedEffect != null ? infestedEffect.getAmplifier() : 0;
    		int var2 = 3 + world.random.nextInt(3);
    		int spawnCount = var2 + (var6 * (1 + world.random.nextInt(3)));
    		float var4,var5;
    		double ex = entity.getX(), ey = entity.getY(), ez = entity.getZ();

    		for (int var3 = 0; var3 < spawnCount; ++var3) {
    			var4 = ((float)(var3 % 2) - 0.5F) / 4.0F;
                var5 = ((float)(var3 / 2) - 0.5F) / 4.0F;

        		SpawnUtil.trySpawnEntity(FUREntityRegistry.LAMPREY.get(), serverLevel, new BlockPos((int)(ex + var4), (int)ey, (int)(ez + var5)));
    		}
    	}
    	
    	if (entity instanceof MimicEntity mimic) {
    		int ItemPos = mimic.containsItem(Items.TOTEM_OF_UNDYING);
    		            
    		if (ItemPos != -1) {
    			mimic.setHealth(1.0F);
    			mimic.removeAllEffects();
    			mimic.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
    			mimic.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
    			mimic.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
    			mimic.level().broadcastEntityEvent(mimic, (byte)35);
    			mimic.inventory.getItem(ItemPos).shrink(1);
    			
    			event.setCanceled(true);
    		}
    	}   
    }
    
    @SubscribeEvent
    public void onEntityDrop (LivingDropsEvent event) {
    	/**
         * Add bonus loot (Intestine) to various entities.
         **/
    	/*    	
    	if (event.isRecentlyHit() && event.getEntity() instanceof AbstractIllagerEntity && event.getEntity().getRandom().nextFloat() < 0.01F * (float)FURConfig.General_IllagerNose.get()) {          
            event.getEntity().spawnAtLocation(FURItemRegistry.ILLAGER_NOSE, 1);
        }*/
    	
    	if (event.getEntity().getTags().contains("FUR_noLoot")) {
    		event.setCanceled(true);
    	}
    }
    
    /**
     * Custom anvil event, using for making items into enchantment for weapons and tools
     * Example: Make glow shroom and parasite a good lure
     */
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {        
    	/*ItemStack tool = event.getLeft();
    	ItemStack ench = event.getRight();
    	ItemStack outputStack = tool.copy();
    	Map<Enchantment, Integer> currentEnchantments = EnchantmentHelper.getEnchantments(tool);    	
    	int cost = 0, k = 0;
    	boolean flag = false;
    	
    	if (FURConfig.Enchantment_Enable.get()) {
	    	for (Tuple<Enchantment, Tuple<ItemStack, Integer>> recipe : LootTableHandler.ANVIL_RECIPE) {
	    		if (recipe.getA().canEnchant(tool) && ench.getItem().equals(recipe.getB().getA().getItem())) {
	    			cost = 0;
	    			flag = true;
	    			
                    for (Enchantment enchantment : currentEnchantments.keySet()) {
                    	if (enchantment != recipe.getA() && recipe.getA().isCompatibleWith(enchantment)) {
                    		cost++;
                    	} else {
                    		flag = false;
                    		break;
                    	}
                    }
                    
                    if (flag) {
                        switch(recipe.getA().getRarity()) {
                        	case COMMON:
                        		k = 1;
                        		break;
                        	case UNCOMMON:
                        		k = 2;
                        		break;
                        	case RARE:
                        		k = 4;
                        		break;
                        	case VERY_RARE:
                        		k = 8;
                        }
                        
                        k = Math.max(1, k / 2);
                        cost += k * recipe.getB().getB();
                        
		        		event.setOutput(outputStack);
		        		event.setCost(cost);
		    			event.setOutput(event.getLeft().copy());
		    			event.getOutput().enchant(recipe.getA(), recipe.getB().getB());
		        		event.setMaterialCost(1);
                    }
	        	}
	    	}
    	}*/
    }
    
    /**
     * Player on update event, repeat once per second
     * Add auto repairing effect for Golden Heart
     */
    @SubscribeEvent
    public void playerTick(final TickEvent.PlayerTickEvent event) {
    	int Armor_Famine_lvl = 0;
    	
    	if (event.phase == TickEvent.Phase.START) return;
    	
        final Player player = event.player;
        
        // Molten Armor full-set: lava walking.
        // Placed here (Phase.END) so it runs after Minecraft's fluid physics,
        // preventing our position lock from being overwritten in the same tick.
        if (!player.isShiftKeyDown() && MoltenArmorItem.countMoltenPieces(player) >= 4) {
        	MoltenArmorItem.applyLavaWalking(player);
        }

        // Soulforged Armor full-set: Soul Speed on soul sand / soul soil.
        if (MoltenArmorItem.isWearingFullSoulforged(player)) {
        	MoltenArmorItem.tickSoulSpeed(player);
        }
        
        if (player.level().isClientSide()) return;
        if ((player.level().getGameTime() & 0x1FL) > 0L) return;    
        
		if (player.level() instanceof ServerLevel serverLevel && serverLevel.getDifficulty() != Difficulty.PEACEFUL && serverLevel.random.nextFloat() < 0.1F) {
			for (ItemEntity itemEnt : serverLevel.getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(5.0F))) {
				Item foodItem = itemEnt.getItem().getItem();
		    	if (foodItem.isEdible() && foodItem.getFoodProperties().isMeat()) {
					BlockPos pos = itemEnt.blockPosition();

		            if (itemEnt.isInWater() && serverLevel.getBiome(pos).containsTag(FURBiomeTagsProvider.HAS_SWARMER)) {
						for (int i = 0; i < 2 + serverLevel.random.nextInt(3); i++) {
		    				double posX = pos.getX() + ((serverLevel.random.nextDouble() * 5.0D) - 2.5D);
		    				double posY = pos.getY();
		    				double posZ = pos.getZ() + ((serverLevel.random.nextDouble() * 5.0D) - 2.5D);
		    				BlockPos blockpos = new BlockPos((int)posX, (int)posY, (int)posZ);

		    				if (serverLevel.getBlockState(blockpos).getFluidState().is(FluidTags.WATER)) {
		    					if (SpawnUtil.isDay(serverLevel) && serverLevel.getBiome(blockpos).containsTag(FURBiomeTagsProvider.HAS_PIRANHA)) {
		    						SpawnUtil.trySpawnEntity(FUREntityRegistry.PIRANHA.get(), serverLevel, blockpos);
		    					} else {
		    						SpawnUtil.trySpawnEntity(FUREntityRegistry.SWARMER.get(), serverLevel, blockpos);
		    					}
		    					itemEnt.playSound(SoundEvents.GENERIC_EAT, 1, 1);
		    					itemEnt.discard();
		    				}
		    			}
		            }
		    	}
			}
		}
			  	    
		for (ItemStack S : player.getArmorSlots()) {
			if (S.getItem() instanceof FamineArmorItem) {
				Armor_Famine_lvl++;
			}
		}
		
		if (Armor_Famine_lvl >= 4) {
			player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 7 * 20, 9));
		}
    }    
    
    @SubscribeEvent
    public void onEDamage(LivingDamageEvent event) {
    	DamageSource source = event.getSource();
    	LivingEntity Attacked = event.getEntity();
    	Entity Attacker = source.getDirectEntity();
    	float effectlevel = 1.0F;
	    int Armor_Chitin_lvl = 0;
	    
	    if (event.getSource().is(DamageTypeTags.IS_FIRE) && event.getEntity().hasEffect(FUREffectRegistry.IMMOLATION.get())) {
	    	event.setCanceled(true);
	    	return;
	    }
	    		
		for (ItemStack S : Attacked.getArmorSlots()) {
			if (S.getItem() instanceof ChitinArmorItem) {
				Armor_Chitin_lvl++;
			}
		}		

		if ((Armor_Chitin_lvl >= 2) && source.is(DamageTypeTags.IS_FALL)) {
			event.setAmount(event.getAmount() * 0.5F);
		}
    	
    	// Molten Armor full-set bonus: 50% fire damage reduction
    	if (source.is(DamageTypeTags.IS_FIRE)) {
    		event.setAmount(MoltenArmorItem.applyFireReduction(Attacked, event.getAmount()));

    		if (Attacked instanceof Player player && !Attacked.fireImmune()) {
    			boolean have_Heart = false;

    			for (int i = 0; i < 9; i++) {
    				if (player.getInventory().getItem(i).getItem().equals(FURItemRegistry.MOOTEN_HEART.get())
    						|| player.getInventory().getItem(i).getItem().equals(FURItemRegistry.SOULFORGED_HEART.get())) {
    					have_Heart = true;
    				}
    			}

    			/*if (ModList.get().isLoaded("curios") && !have_Heart) {
    				have_Heart = (CurioIntegration.findItem(FURItemRegistry.MOOTENHEART, Attacked) != ItemStack.EMPTY);
    				have_Heart |= (CurioIntegration.findItem(FURItemRegistry.SOULFIREHEART, Attacked) != ItemStack.EMPTY);
    			}*/

    			if (have_Heart) {
    				effectlevel -= (float)FURConfig.MootenHeart_Damage.get() / 100.0F;
    			}
    		}
    	}

    	// Molten Armor 2-piece bonus: retaliation burn on attacker
    	// Only triggers on melee attacks (mob attack, mob attack no aggro, player attack)
    	if (source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO) || source.is(DamageTypes.PLAYER_ATTACK)) {
    		MoltenArmorItem.applyRetaliationBurn(Attacked, source.getDirectEntity(), event.getAmount());

    		if (Attacker instanceof LivingEntity attackerLiving && attackerLiving.hasEffect(FUREffectRegistry.SOUL_SIPHON.get())) {
    			int siphonLevel = attackerLiving.getEffect(FUREffectRegistry.SOUL_SIPHON.get()).getAmplifier() + 1;
    			attackerLiving.heal(event.getAmount() * 0.05F * siphonLevel);
    		}
    	}

    	if (source.is(DamageTypeTags.IS_EXPLOSION) && source.getEntity() instanceof Wolf wolf) {
    		if (Attacked.getMobType().equals(MobType.UNDEAD) && wolf.getName().equals(Component.translatable("entity.fur.holygrenade"))) {
    			event.setAmount(event.getAmount() * 0.45F);
    			Attacked.setSecondsOnFire(8);
    		} else if (wolf.getName().equals(Component.translatable("entity.fur.ghostbomb"))) {
    			Attacked.setDeltaMovement(0.0D, Attacked.getDeltaMovement().y, 0.0D);
    			Attacked.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 0));
    			event.setAmount(event.getAmount() * 0.20F);
    		} else if (wolf.getName().equals(Component.translatable("entity.fur.sonicbomb"))) {
    			Attacked.addEffect(new MobEffectInstance(FUREffectRegistry.FEAR.get(), 4 * 20, 2, false, false, true));
    			event.setAmount(event.getAmount() * 0.33F);
    		} else {
    			event.setAmount(event.getAmount() * 0.15F);
    		}
    	}
    	
    	if (Attacked.hasEffect(FUREffectRegistry.CORRODED.get()) && (source.is(DamageTypeTags.IS_PROJECTILE) || source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO) || source.is(DamageTypes.PLAYER_ATTACK))) {
    		event.setAmount(event.getAmount() * (1.0F + 0.1F * (1 + Attacked.getEffect(FUREffectRegistry.CORRODED.get()).getAmplifier())));
    	}
    	
    	if (Attacked.hasEffect(FUREffectRegistry.THORNED.get())) {
    		if (source.is(DamageTypes.CACTUS) || source.is(DamageTypes.SWEET_BERRY_BUSH) || source.is(DamageTypes.THORNS)) {
    			event.setCanceled(true);
    		} else if (Attacker != null && !source.is(DamageTypes.MAGIC) && !source.is(DamageTypeTags.IS_EXPLOSION) && Attacker instanceof LivingEntity) {
    			Attacker.hurt(Attacker.damageSources().thorns(Attacked), 1.0F + Attacked.getEffect(FUREffectRegistry.THORNED.get()).getAmplifier());
            }
    	}
    	
		int Armor_Ghostly_lvl = 0;
		
		for (ItemStack S : Attacked.getArmorSlots()) {
			if (S.getItem() instanceof GhostlyArmorItem) {
				Armor_Ghostly_lvl++;
			}
		}
		
		if (Armor_Ghostly_lvl >= 2) {
			Attacked.heal(event.getAmount() * 0.2F);
		}
		
		if (source.getEntity() instanceof LivingEntity attackerLiving && (attackerLiving.getHealth() < Attacked.getHealth())) {
			Armor_Ghostly_lvl = 0;

			for (ItemStack S : attackerLiving.getArmorSlots()) {
				if (S.getItem() instanceof GhostlyArmorItem) {
					Armor_Ghostly_lvl++;
				}
			}
			
			if (Armor_Ghostly_lvl >= 4) {
				event.setAmount(event.getAmount() * 1.2F);
			}
		}
    	
    	event.setAmount(event.getAmount() * effectlevel);
    }    
    
	@SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event) {
    	/*if (event.getEntity() != null && event.getEntity().getType().equals(EntityType.HOGLIN))
    		((HoglinEntity)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((HoglinEntity)event.getEntity()), WarpedFireflyEntity.class, 6.0F, 1.0D, 1.2D));*/
    	
    	if (event.getEntity() instanceof AbstractSkeleton skeleton && skeleton.getTags().contains("FUR_tameSkeleton")) {
    		skeleton.removeTag("FUR_tameSkeleton");
    	}
    	
    	/*if (event.getEntity() != null && event.getEntity() instanceof IronGolem golem) {
    		golem.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(golem, Player.class, 0, true, false, (p_210136_0_) -> {
				boolean noseInCurios = ModList.get().isLoaded("curios") && (CurioIntegration.findItem(FURItemRegistry.ILLAGER_NOSE, p_210136_0_) != ItemStack.EMPTY); 
    			return p_210136_0_.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(FURItemRegistry.ILLAGER_NOSE) || noseInCurios;
			}));	
    	}*/
    }
    
    @SubscribeEvent
    public void onEAttack(LivingAttackEvent event) {
    }

    /**
     * Vespa shield counter-attack: when blocking with the vespa shield, deal 2 thorns damage
     * and apply Poison I (6s) to the direct attacker.
     *
     * Uses ShieldBlockEvent (Forge 1.20.1), which fires only when a block actually succeeds —
     * replacing the old canBlockDamageSource + LivingAttackEvent pattern from 1.16.5.
     */
    @SubscribeEvent
    public void onShieldBlock(ShieldBlockEvent event) {
        LivingEntity blocker = event.getEntity();
        if (blocker.level().isClientSide()) return;
        if (!(blocker.getUseItem().getItem() instanceof VespaShieldItem)) return;

        DamageSource source = event.getDamageSource();
        if (source.getDirectEntity() instanceof LivingEntity attacker) {
            attacker.hurt(blocker.damageSources().thorns(blocker), 2.0F);
            attacker.addEffect(new MobEffectInstance(MobEffects.POISON, 6 * 20, 0));
        }
    }
        
    @SubscribeEvent
    public void onEWakeup(PlayerWakeUpEvent event) {
		/*ItemStack have_DreamCatcher = null;
		Player player = event.getPlayer();
		World world = event.getPlayer().level;
		
		if (!event.updateWorld() && player.level().getDifficulty() != Difficulty.PEACEFUL) {
			for(int i = 0; i < 9 ; i++) {
				if(player.inventory.getItem(i).getItem().equals(FURItemRegistry.DREAMCATCHER)) {
					have_DreamCatcher = player.inventory.getItem(i);
					break;
				}
			}
			
			if (ModList.get().isLoaded("curios") && have_DreamCatcher == null) {
				have_DreamCatcher = CurioIntegration.findItem(FURItemRegistry.DREAMCATCHER, player);
			}
		}		
		
		if (world instanceof ServerLevel && have_DreamCatcher != null && have_DreamCatcher != ItemStack.EMPTY && !event.updateWorld() && player.level().getDifficulty() != Difficulty.PEACEFUL) {
			MobSpawnInfo.Spawners Result = ((MobSpawnInfo.Spawners)WeightedRandom.getRandomItem(world.random, LootTableHandler.DREAMCATCHER_LIST));
			System.out.println(Result);
			Entity LivingEntity = Result.type.create(world);
			int min  = Result.minCount;
			int max  = Result.maxCount;
			boolean has_spawn = false;
			
			if (LivingEntity instanceof CreatureEntity) {
				for(int i = 0; i < new Random().nextInt(MathHelper.abs(max-min) + 1) + min; i++) {
					double k1 = player.getX() + (world.random.nextDouble() * 32.0D) - 16.0D;
					double l1 = player.getY() + (world.random.nextDouble() * 4.0D) - 2.0D;
					double i2 = player.getZ() + (world.random.nextDouble() * 32.0D) - 16.0D;
					BlockPos pos = SpawnUtil.getHeight(world, new BlockPos(k1, l1, i2));
					
					SpawnUtil.trySpawnEntity((EntityType<CreatureEntity>) Result.type, ((ServerLevel) world), pos);
					
					has_spawn = true;
				}
				
				if (has_spawn && FURConfig.DreamCatcher_dur.get() > 0) {
					world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_TRIGGER, SoundCategory.BLOCKS, 1.0F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
					
					if (!player.isCreative()) {
						have_DreamCatcher.hurtAndBreak(FURConfig.DreamCatcher_dur.get(), event.getEntity(), (p_220045_0_) -> {
			    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);			    			
			    		});
					}
				}
			}            	
		}*/
    }
    
    @SubscribeEvent
    public void onActiveItemUseStart(LivingEntityUseItemEvent.Start event) {
	    //int Armor_Swine_lvl = 0;
    	
    	if (event.getEntity().hasEffect(FUREffectRegistry.SOILED.get()) && 
    			!FUREffectRegistry.SOILED.get().getCurativeItems().contains(event.getItem()) &&
    			(event.getItem().isEdible() || event.getItem().getItem() instanceof PotionItem)) {
    		event.setCanceled(true);
    	}
    			
		/*for (ItemStack S : event.getEntity().getArmorSlots()) {
			if (S.getItem() instanceof SwineArmorItem) {
				Armor_Swine_lvl++;
			}
		}
    	
    	if ((Armor_Swine_lvl >= 2) && event.getItem().getItem().isEdible()) {
    		event.setDuration((int) (event.getDuration() * 0.75F));
    	}*/
    }
    
    @SubscribeEvent
    public void onActiveItemUseFinish(LivingEntityUseItemEvent.Finish event) {
	    /*int Armor_Swine_lvl = 0;
		
		for (ItemStack S : event.getEntity().getArmorSlots()) {
			if(S.getItem() instanceof SwineArmorItem) {
				Armor_Swine_lvl++;
			}
		}
    	
    	if (!event.getEntity().level().isClientSide() && (Armor_Swine_lvl >= 4) && event.getItem().isEdible()) {
    		event.getEntity().curePotionEffects(new ItemStack(Items.MILK_BUCKET));
    	}*/
    }

	@SubscribeEvent
	public void playerDeath(LivingDropsEvent event) {
		if (event.isCanceled()) {
			return;
		}

		if (!(event.getEntity() instanceof Player player) || player.level().isClientSide() || player.getRandom().nextInt(1000) > FURConfig.pSpawnRate_DeathMimic.get()) {
			return;
		}

		AABB boundingBox = new AABB(player.blockPosition()).inflate(16);
		List<MimicEntity> nearbyMimics = player.level().getEntitiesOfClass(MimicEntity.class, boundingBox);

		if (nearbyMimics.isEmpty()) {
			int spawnX = (int) Math.floor(player.getX() + player.getRandom().nextInt(8) - 4);
			int spawnZ = (int) Math.floor(player.getZ() + player.getRandom().nextInt(8) - 4);
			BlockPos spawn = new BlockPos(spawnX, player.level().getHeight(Heightmap.Types.WORLD_SURFACE, spawnX, spawnZ), spawnZ);

			// We show some pretty effects to indicate they magically spawned in or something like that.
			// Alternatively, we could make mimics look like they dug out of the ground?
			AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(player.level(), spawnX, spawn.getY(), spawnZ);
	        areaeffectcloudentity.setRadius(5.0F);
	        areaeffectcloudentity.setRadiusOnUse(-0.5F);
	        areaeffectcloudentity.setWaitTime(5);
	        areaeffectcloudentity.setDuration(20);
	        areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
	        areaeffectcloudentity.setParticle(ParticleTypes.ENTITY_EFFECT);
			// No matter the value, this still appears as white to me?
	        areaeffectcloudentity.setFixedColor(0x6F5B3C);
			player.level().addFreshEntity(areaeffectcloudentity);
			
			MimicEntity Mimic = FUREntityRegistry.MIMIC.get().create(player.level());
			Mimic.setPersistenceRequired();
			Mimic.moveTo(spawn.getX(), spawn.getY(), spawn.getZ(), 0.0F, 0.0F);							
			Mimic.playAmbientSound();
		}
	}
	
    @SubscribeEvent
    public void onTradeSetup(VillagerTradesEvent event) {
    	// 2 = Apprentice, 3 = Journeyman, 4 = Expert, 5 = Master, reference = VillagerTrades.class
    	if (FURConfig.BonusVillagerTrades.get()) {
	        if (event.getType() == VillagerProfession.FISHERMAN) {
	            event.getTrades().get(2).add((trader, rand) -> new MerchantOffer(
	            		new ItemStack(FURItemRegistry.PIRANHA_RAW.get(), 6),
	            		new ItemStack(Items.EMERALD, 1),
	                    new ItemStack(FURItemRegistry.PIRANHA_COOKED.get(), 6),
	                    16,		// max uses
	                    5,      // villager xp
	                    0.05f   // price multiplier
	                ));
	            event.getTrades().get(2).add((trader, rand) -> new MerchantOffer(
	            		new ItemStack(FURItemRegistry.SWARMER_RAW.get(), 6),
	            		new ItemStack(Items.EMERALD, 1),
	                    new ItemStack(FURItemRegistry.SWARMER_COOKED.get(), 6),
	                    16,
	                    5,
	                    0.05f
	                ));
	            event.getTrades().get(2).add((trader, rand) -> new MerchantOffer(
	            		new ItemStack(FURItemRegistry.LAMPREY_RAW.get(), 6),
	            		new ItemStack(Items.EMERALD, 1),
	                    new ItemStack(FURItemRegistry.LAMPREY_COOKED.get(), 6),
	                    16,
	                    5,
	                    0.05f
	                ));	            
	        }
	        
	        if (event.getType() == VillagerProfession.BUTCHER) {
	        	event.getTrades().get(2).add((trader, rand) -> new MerchantOffer(
	        		    new ItemStack(FURItemRegistry.BLOATED_INTESTINE.get(), 4),  // player to trade
	        		    new ItemStack(Items.EMERALD, 1),                            // player to get
	        		    12,                                                         // max uses
	        		    1,                                                          // xp
	        		    0.05F                                                       // price multiplier
	        		));
	            //list.add(new ItemsForEmeraldsTrade(FURItemRegistry.PLAGUED_PORKCHOP, 2, 1, 12, 1));
	        }
	        
	        if (event.getType() == VillagerProfession.CLERIC) {
	            event.getTrades().get(5).add((trader, rand) -> new MerchantOffer(
	            		new ItemStack(Items.EMERALD, 10),
	            		new ItemStack(FURItemRegistry.HOLY_WATER.get(), 1),
	                    4,
	                    20,
	                    0.05f
	                ));	        	
	        }
    	}
    }
    
    @SubscribeEvent
    public void onWanderingTradeSetup(WandererTradesEvent event) {
    	if (FURConfig.BonusWanderingTraderTrades.get()) {
    		var genericTrades = event.getGenericTrades();
    		var rareTrades = event.getRareTrades(); 
    		
    		genericTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 4),
    	            new ItemStack(FURItemRegistry.SCYTHE_CLAW.get(), 1),
    	            12, 
    	            1, 
    	            0.05f
    	        ));
    		genericTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 1),
    	            new ItemStack(FURItemRegistry.FOUL_BRISTLE.get(), 3),
    	            12, 
    	            1, 
    	            0.05f
    	        ));
    		genericTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 1),
    	            new ItemStack(FURItemRegistry.CACTUS_THORN.get(), 4),
    	            12, 
    	            1, 
    	            0.05f
    	        ));
    		genericTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 8),
    	            new ItemStack(FURItemRegistry.CACTUS_FRUIT.get(), 1),
    	            12, 
    	            1, 
    	            0.05f
    	        ));
    		genericTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 5),
    	            new ItemStack(FURItemRegistry.PIRANHA_BUCKET.get(), 1),
    	            12, 
    	            1, 
    	            0.05f
    	        ));
    		genericTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 5),
    	            new ItemStack(FURItemRegistry.SWARMER_BUCKET.get(), 1),
    	            12, 
    	            1, 
    	            0.05f
    	        ));
    		genericTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 5),
    	            new ItemStack(FURItemRegistry.LAMPREY_BUCKET.get(), 1),
    	            12, 
    	            1, 
    	            0.05f
    	        ));    		
    		genericTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 4),
    	            new ItemStack(FURItemRegistry.ANCIENT_AMBER.get(), 1),
    	            12, 
    	            1, 
    	            0.05f
    	        ));
	        //genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.SILKY_SLUDGE, 4, 1, 12, 1));
	        //genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.PIGBOARHIDE, 6, 1, 12, 1));

    		rareTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 24),
    	            new ItemStack(FURItemRegistry.POISON_SPORE.get(), 1),
    	            4, 
    	            15, 
    	            0.05f
    	        ));
    		rareTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 30),
    	            new ItemStack(FURItemRegistry.UNDYING_HEART.get(), 1),
    	            4, 
    	            20, 
    	            0.05f
    	        ));
    		rareTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 30),
    	            new ItemStack(FURItemRegistry.ACIDIC_HEART.get(), 1),
    	            4, 
    	            20, 
    	            0.05f
    	        ));
    		rareTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 3),
    	            new ItemStack(FURItemRegistry.WISP_IN_A_BOTTLE.get(), 1),
    	            12, 
    	            1, 
    	            0.05f
    	        ));
    		
	        //rareTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.STAINED_KINGS_CROWN, 80, 1, 2, 30));
	        //rareTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.PHEROMONE_GLAND, 18, 1, 4, 20));
    	}
    }
    
    @SubscribeEvent
    public void onEJump(LivingJumpEvent event) {
	    int Armor_Chitin_lvl = 0;
	    
		for (ItemStack S : event.getEntity().getArmorSlots()) {			
			if(S.getItem() instanceof ChitinArmorItem) {
				Armor_Chitin_lvl++;
			}
		}   
		
		if (Armor_Chitin_lvl >= 4) {
			event.getEntity().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3 * 20, 0));
		}
    }
    
    @SubscribeEvent
    public void onPCritical(CriticalHitEvent event) {
    	/*int CriticalBoostlvl = EnchantmentHelper.getItemEnchantmentLevel(FUREnchantmentRegistry.CRITICALBOOST, event.getPlayer().getMainHandItem());
    	if (CriticalBoostlvl != 0 && event.getDamageModifier() > 1.0F) {
    		event.setDamageModifier(event.getDamageModifier() + (CriticalBoostlvl * 0.15F));
    	}*/
    }
    
    @SubscribeEvent
    public void onEHeal(LivingHealEvent event) {
    	float effectlevel = 1.0F;
    	
    	if (event.getEntity() instanceof Player player) {
    		boolean have_Heart = false;

    		for (int i = 0; i < 9 ; i++) {
    			if (player.getInventory().getItem(i).getItem().equals(FURItemRegistry.SOULFORGED_HEART.get())) {
					have_Heart = true;
    			}
    		}
    		
    		/*if (ModList.get().isLoaded("curios") && !have_Heart) {
    			have_Heart = (CurioIntegration.findItem(FURItemRegistry.SOULFIREHEART, event.getEntity()) != ItemStack.EMPTY);
    		}*/
    		
    		if (have_Heart) {
    			effectlevel += 0.25F;
    		}
    			
    	}
    	
    	if (event.getEntity().hasEffect(FUREffectRegistry.SOILED.get())) {
    		effectlevel -= 0.25F * (1 + event.getEntity().getEffect(FUREffectRegistry.SOILED.get()).getAmplifier());
    	}  
    	
    	if (event.getEntity().hasEffect(FUREffectRegistry.FLOURISHED.get())) {
    		effectlevel += 0.25F * (1 + event.getEntity().getEffect(FUREffectRegistry.FLOURISHED.get()).getAmplifier());
    	} 
    	
    	event.setAmount(event.getAmount() * effectlevel);
    }
    
    @SubscribeEvent
    public void onInventoryOpen(final PlayerContainerEvent.Open event) {
    	/*if (!event.getPlayer().isCreative() && event.getContainer() instanceof ChestContainer) {
    		AxisAlignedBB axisalignedbb = AxisAlignedBB.unitCubeFromLowerCorner(event.getPlayer().position()).inflate(16.0D, 10.0D, 16.0D);
    		for (MobEntity mobs : event.getPlayer().level().getLoadedEntitiesOfClass(GraveRobberEntity.class, axisalignedbb)) {
    			mobs.setTarget(event.getPlayer());
    		}
    	}*/
    }
    
    @SubscribeEvent
    public void onESetTarget(LivingChangeTargetEvent event) {    
        LivingEntity entity = event.getEntity();
        LivingEntity newTarget = event.getNewTarget();
        
    	// Neutral
        if (newTarget != null && entity.getLastHurtByMob() != newTarget) {
        	/*Boolean hasNose = newTarget.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(FURItemRegistry.ILLAGER_NOSE);
        	
    		if (ModList.get().isLoaded("curios") && !hasNose) {
    			hasNose = (CurioIntegration.findItem(FURItemRegistry.ILLAGER_NOSE, newTarget) != ItemStack.EMPTY);
    		}
    		
        	if (entity.getMobType().equals(MobType.ILLAGER) && hasNose) {
        		((MobEntity) mob).setTarget(null);
        	}*/
        	
        	if (entity instanceof Mob mob && mob.getMobType().equals(MobType.ARTHROPOD) && newTarget.hasEffect(FUREffectRegistry.CHARMING_PHEROMONE.get())) {
        		mob.setTarget(null);
        		mob.getNavigation().moveTo(newTarget.getX(), newTarget.getY(), newTarget.getZ(), mob.getMoveControl().getSpeedModifier());
        	}
        }
        
        // Passive
        /*if (newTarget != null) {
        	Boolean hasCrown = newTarget.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(FURItemRegistry.SKELETONKING_CROWN);
        	
    		if (ModList.get().isLoaded("curios") && !hasCrown) {
    			hasCrown = (CurioIntegration.findItem(FURItemRegistry.SKELETONKING_CROWN, newTarget) != ItemStack.EMPTY);
    		}
    		
        	if (mob instanceof AbstractSkeletonEntity && hasCrown) {
        		((MobEntity) mob).setTarget(null);
        	}
        }*/
    } 
    
    @SubscribeEvent
    public void onELiving(LivingTickEvent event) { 
    	LivingEntity living = event.getEntity();

    	// Molten Armor lava walking is handled in playerTick (Phase.END).

    	if (living.hasEffect(FUREffectRegistry.FEAR.get()) && living.getRandom().nextFloat() < 0.3f && living.level() instanceof ServerLevel serverLevel) {
    		var rng = living.getRandom();
			double d0 = rng.nextGaussian() * 0.02D;
			double d1 = rng.nextGaussian() * 0.02D;
			double d2 = rng.nextGaussian() * 0.02D;
			serverLevel.sendParticles(FURParticleRegistry.FEAR.get(), living.getRandomX(1.0D), living.getRandomY() + living.getBbHeight() * 0.5D, living.getRandomZ(1.0D), 2, d0, d1, d2, 0.0D);
    	}

    	if (living.hasEffect(FUREffectRegistry.IMMOLATION.get()) && living.getRandom().nextFloat() < 0.3f && living.level() instanceof ServerLevel serverLevel) {
    		var rng = living.getRandom();
			double d0 = rng.nextGaussian() * 0.3D;
			double d1 = rng.nextGaussian() * 0.3D;
			double d2 = rng.nextGaussian() * 0.3D;
			serverLevel.sendParticles(ParticleTypes.FLAME, living.getRandomX(1.0D), living.getRandomY() + living.getBbHeight() * 0.5D, living.getRandomZ(1.0D), 2, d0, d1, d2, 0.0D);
    	}			
    }
    
    @SubscribeEvent
    public void onELightning(EntityStruckByLightningEvent event) { 
    	if (event.getEntity().level().getDifficulty() != Difficulty.PEACEFUL && event.getEntity() instanceof Bee bee && bee.getRandom().nextInt(100) < FURConfig.pBeeConvertRate_Vespa.get()) {
            VespaEntity entity = FUREntityRegistry.VESPA.get().create(event.getEntity().level());
            entity.finalizeSpawn((ServerLevel)event.getEntity().level(), event.getEntity().level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.CONVERSION, null, (CompoundTag)null);
            entity.moveTo(event.getEntity().blockPosition(), 0.0F, 0.0F);
            entity.setSkin(1);
            entity.setPersistenceRequired();
            event.getEntity().level().addFreshEntity(entity);
            event.getEntity().discard();
    	}
    }
    
    @SubscribeEvent
    public void onEHurt(LivingHurtEvent event) {
    	DamageSource source = event.getSource();
    	LivingEntity Attacked = event.getEntity();
    	Entity Attacker = source.getEntity();
    	Entity DirectAttacker = source.getDirectEntity();
    	
	    int Armor_Famine_lvl = 0;	    
	    
	    if (DirectAttacker != null) {
	    	CompoundTag data = DirectAttacker.getPersistentData();
	    	
			for (ItemStack S : DirectAttacker.getArmorSlots()) {
				if (S.getItem() instanceof FamineArmorItem) {
					Armor_Famine_lvl++;
				}
			}		
		
			if (Armor_Famine_lvl >= 4 && DirectAttacker instanceof LivingEntity) {
				event.setAmount(event.getAmount() + 2.0F);
			}
						
			if (DirectAttacker instanceof FURTameableEntity tamable && 
					data.contains("sharpness", Tag.TAG_INT) && 
					data.contains("bane_of_arthropods", Tag.TAG_INT) && 
					data.contains("smite", Tag.TAG_INT)) {
				event.setAmount(event.getAmount() + tamable.getBonusDamage(Attacked, data.getInt("sharpness"), data.getInt("bane_of_arthropods"), data.getInt("smite")));	
			}
			
	    	if (DirectAttacker instanceof LivingEntity living) {
	    		Item heldItem = living.getMainHandItem().getItem();
	    		if (heldItem.equals(FURItemRegistry.BONE_SWORD.get()))
	    			event.setAmount(event.getAmount() + Math.min((float)FURConfig.BoneSword_DamageCap.get(), Attacked.getMaxHealth() * ((float)FURConfig.BoneSword_Damage.get() * 0.01F)));
	    		/*else if (heldItem.equals(FURItemRegistry.SPECTRAL_DAGGER) && !Attacked.getMobType().equals(CreatureAttribute.UNDEAD))
	    			event.setAmount(event.getAmount() + 2.0F);*/
	    	}
	    	
	    	if (Attacker instanceof LivingEntity living && (Attacker.equals(DirectAttacker) || source.is(DamageTypeTags.IS_PROJECTILE)) && living.hasEffect(FUREffectRegistry.VENOMOUS.get())) {
		        Attacked.addEffect(new MobEffectInstance(MobEffects.POISON, 80, living.getEffect(FUREffectRegistry.VENOMOUS.get()).getAmplifier()));
	    	}
	    	
			if (DirectAttacker.getType().equals(FUREntityRegistry.GHOUL_ARROW.get()) && (Attacked.getHealth() <= Attacked.getMaxHealth() * ((float)FURConfig.Ghoul_targetHPThreshold.get() / 100.0F))) {
				if (DirectAttacker.getCommandSenderWorld() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(ParticleTypes.CRIT, Attacked.getX(), Attacked.getY(), Attacked.getZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
				}
				event.setAmount(event.getAmount() + 4.0F);
			}

			if (DirectAttacker.getType().equals(FUREntityRegistry.FANG_ARROW.get())) {
				if (DirectAttacker.getCommandSenderWorld() instanceof ServerLevel serverLevel) {
					serverLevel.sendParticles(ParticleTypes.CRIT, Attacked.getX(), Attacked.getY(), Attacked.getZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
				}
				event.setAmount(event.getAmount() + Math.min((float)FURConfig.BoneSword_DamageCap.get(), Attacked.getMaxHealth() * ((float)FURConfig.BoneSword_Damage.get() * 0.01F)));
			}
    	}
    } 
    
    @SubscribeEvent
    public void onELootingLevelEvent(LootingLevelEvent event) {
        DamageSource Attacker = event.getDamageSource();
        if (Attacker != null) {
            if (Attacker.getEntity() instanceof GhoulEntity) {
                event.setLootingLevel(event.getLootingLevel() + 3);
            }

            Entity directEntity = event.getDamageSource().getDirectEntity();
            if (directEntity != null && directEntity.getType().equals(FUREntityRegistry.GHOUL_ARROW.get())) {
                event.setLootingLevel(event.getLootingLevel() + 3);
            }
        }
    }
    
    @SubscribeEvent
    public void onLevelLoad(net.minecraftforge.event.level.LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (serverLevel.dimension() != Level.OVERWORLD) return;
        net.minecraft.world.level.biome.BiomeSource source = serverLevel.getChunkSource().getGenerator().getBiomeSource();
        if (!(source instanceof FURMultiNoiseBiomeSourceAccessor noiseAccessor)) return;
        noiseAccessor.fur_setWorldSeed(serverLevel.getSeed());
        noiseAccessor.fur_setDimension(serverLevel.dimension());
        serverLevel.registryAccess().registry(net.minecraft.core.registries.Registries.BIOME).ifPresent(reg ->
            reg.getHolder(com.Fishmod.fur.init.FURBiomesRegistry.LUMINOUS_UNDERGROVE).ifPresent(holder -> {
                noiseAccessor.fur_setLuminousHolder(holder);
                if (source instanceof FURBiomeSourceAccessor accessor) {
                    java.util.Map<net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.Biome>, net.minecraft.core.Holder<net.minecraft.world.level.biome.Biome>> map = new java.util.HashMap<>();
                    map.put(com.Fishmod.fur.init.FURBiomesRegistry.LUMINOUS_UNDERGROVE, holder);
                    accessor.fur_setResourceKeyMap(map);
                    accessor.fur_expandBiomesWith(java.util.Set.of(holder));
                }
            })
        );
    }

    @SubscribeEvent
    public void onPpickupXpEvent(PlayerXpEvent.PickupXp event) {
    	int Armor_Famine_lvl = 0;	    
	    
	    if (event.getEntity() != null) {
			for (ItemStack S : event.getEntity().getArmorSlots()) {
				if (S.getItem() instanceof FamineArmorItem) {
					Armor_Famine_lvl++;
				}
			}
	    }		
		
		if (event.getEntity() != null && Armor_Famine_lvl >= 2) {
			event.getEntity().heal(1.0F);
		}
    }

    /**
     * Dismiss player-summoned minions (Scarab, Shroomling, Unburied, ...) when their
     * summoner logs off, so temporary summons do not linger in the world. Permanent
     * tamed pets are left untouched (isSummonedMinion() == false).
     */
    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
    	MinecraftServer server = event.getEntity().getServer();
    	if (server == null) {
    		return;
    	}

    	UUID ownerId = event.getEntity().getUUID();
    	List<FURTameableEntity> toDiscard = new ArrayList<>();

    	for (ServerLevel level : server.getAllLevels()) {
    		for (Entity entity : level.getAllEntities()) {
    			if (entity instanceof FURTameableEntity minion
    					&& minion.isSummonedMinion()
    					&& ownerId.equals(minion.getOwnerUUID())) {
    				toDiscard.add(minion);
    			}
    		}
    	}

    	// Discard after iterating to avoid mutating the entity list mid-traversal.
    	for (FURTameableEntity minion : toDiscard) {
    		minion.level().broadcastEntityEvent(minion, (byte)11);
    		minion.discard();
    	}
    }
}