package com.Fishmod.fur.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.core.VespaInfestation;
import com.Fishmod.fur.data.providers.FURBiomeTagsProvider;
import com.Fishmod.fur.data.providers.FUREntityTypeTagsProvider;
import com.Fishmod.fur.data.providers.FURStructureTagsProvider;
import com.Fishmod.fur.entities.CactyrantEntity;
import com.Fishmod.fur.entities.GhoulEntity;
import com.Fishmod.fur.entities.GraveRobberEntity;
import com.Fishmod.fur.entities.ParasiteEntity;
import com.Fishmod.fur.entities.flying.FlareflyEntity;
import com.Fishmod.fur.entities.flying.VespaEntity;
import com.Fishmod.fur.entities.projectiles.BasicBombEntity;
import com.Fishmod.fur.entities.tameable.CactoidEntity;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;
import com.Fishmod.fur.entities.tameable.MimicEntity;
import com.Fishmod.fur.entities.tameable.ScarecrowEntity;
import com.Fishmod.fur.block.DreamcatcherBlock;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURParticleRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;
import com.Fishmod.fur.integration.curios.CurioIntegration;
import com.Fishmod.fur.item.ChitinArmorItem;
import com.Fishmod.fur.item.VespaShieldItem;
import com.Fishmod.fur.item.FamineArmorItem;
import com.Fishmod.fur.item.GhostlyArmorItem;
import com.Fishmod.fur.item.MoltenArmorItem;
import com.Fishmod.fur.item.SkeletonKingCrownItem;
import com.Fishmod.fur.worldgen.biome.FURBiomeSourceAccessor;
import com.Fishmod.fur.worldgen.biome.FURMultiNoiseBiomeSourceAccessor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.VanillaGameEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
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

	    if (event.getSource().is(DamageTypeTags.IS_FIRE) && event.getEntity().hasEffect(FUREffectRegistry.IMMOLATION.get())) {
	    	event.setCanceled(true);
	    	return;
	    }

    	// Molten Armor full-set bonus: 50% fire damage reduction
    	if (source.is(DamageTypeTags.IS_FIRE)) {
    		event.setAmount(MoltenArmorItem.applyFireReduction(Attacked, event.getAmount()));
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

    	if (source.is(DamageTypeTags.IS_EXPLOSION) && source.getDirectEntity() instanceof BasicBombEntity bomb) {
    		if (Attacked.getMobType().equals(MobType.UNDEAD) && bomb.getType().equals(FUREntityRegistry.HOLY_GRENADE.get())) {
    			event.setAmount(event.getAmount() * 0.45F);
    			Attacked.setSecondsOnFire(8);
    		} else if (bomb.getType().equals(FUREntityRegistry.GHOST_BOMB.get())) {
    			Attacked.setDeltaMovement(0.0D, Attacked.getDeltaMovement().y, 0.0D);
    			Attacked.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 20, 0));
    			event.setAmount(event.getAmount() * 0.20F);
    		} else if (bomb.getType().equals(FUREntityRegistry.SONIC_BOMB.get())) {
    			Attacked.addEffect(FUREffectRegistry.fear(4 * 20, 2));
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
    	
		if (Attacked instanceof Player ghostlyPlayer) {
			// Ghostly Armor full-set spirit-form invulnerability and the 2-piece dodge are both handled
			// in onEAttack (LivingAttackEvent), not here - see that method for why.

			// Ghostly Armor full-set bonus: a fatal hit is intercepted instead of killing the wearer -
			// health is set to 1 and the wearer becomes a brief invulnerable spirit. Gated by its own
			// cooldown so it can't trigger back-to-back.
			if (!event.isCanceled() && GhostlyArmorItem.hasGhostlyPieces(ghostlyPlayer, GhostlyArmorItem.FULLSET_THRESHOLD)
					&& event.getAmount() >= ghostlyPlayer.getHealth()
					&& isGhostlyDeathPreventionReady(ghostlyPlayer)) {
				event.setCanceled(true);
				ghostlyPlayer.setHealth(1.0F);
				triggerGhostlySpiritForm(ghostlyPlayer);
			}
		}

    	event.setAmount(event.getAmount() * effectlevel);
    }

	/** Per-player cooldown for the Ghostly Armor full-set death-prevention bonus, keyed by game time (ticks). */
	private static final Map<UUID, Long> GHOSTLY_DEATH_PREVENTION_READY_AT = new HashMap<>();

	private static boolean isGhostlyDeathPreventionReady(Player player) {
		Long readyAt = GHOSTLY_DEATH_PREVENTION_READY_AT.get(player.getUUID());
		return readyAt == null || player.level().getGameTime() >= readyAt;
	}

	/**
	 * Consumes the death-prevention cooldown and applies the "spirit form" state: a short window of
	 * full invulnerability (enforced in {@link #onEDamage}), no attacking ({@link #onAttackEntity}) and
	 * no item use ({@link #onActiveItemUseStart}/{@link #onRightClickItem}/{@link #onRightClickBlock}).
	 * The translucent render + soul particles are driven client-side off the same synced effect.
	 */
	private static void triggerGhostlySpiritForm(Player player) {
		GHOSTLY_DEATH_PREVENTION_READY_AT.put(player.getUUID(), player.level().getGameTime() + FURConfig.Ghostly_DeathPreventionCooldown.get() * 20L);
		int durationTicks = (int) Math.round(FURConfig.Ghostly_SpiritFormDuration.get() * 20.0D);
		player.addEffect(new MobEffectInstance(FUREffectRegistry.SPIRIT_FORM.get(), durationTicks, 0, false, false, false));
		// Speed III for the same window - spirit form already ignores entity collision, this makes the
		// "get clear of danger before it ends" escape window actually usable instead of just theoretical.
		player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, durationTicks, 2, false, true, true));
		// Regeneration IV, same duration as spirit form/Speed III.
		player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, durationTicks, 3, false, true, true));
		// Untarget immediately for anything already locked onto the player - onESetTarget only blocks
		// *new* targeting attempts made while spirit form is active, it can't retroactively undo a
		// setTarget() call from before this trigger fired (often the very attack that almost killed them).
		if (player.level() instanceof ServerLevel serverLevel) {
			for (Mob mob : serverLevel.getEntitiesOfClass(Mob.class, player.getBoundingBox().inflate(64.0D), m -> m.getTarget() == player)) {
				mob.setTarget(null);
			}
		}
		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), FURSoundRegistry.SPIRIT_FORM_TRIGGERED.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
	}
    
	@SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event) {
    	if (event.getEntity() != null && event.getEntity().getType().equals(EntityType.HOGLIN))
    		((Hoglin)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Hoglin)event.getEntity()), FlareflyEntity.class, 6.0F, 1.0D, 1.2D));
    	
    	if (event.getEntity() != null && event.getEntity() instanceof IronGolem golem) {
    		golem.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(golem, Player.class, 0, true, false, (living) -> {
				boolean noseInCurios = ModList.get().isLoaded("curios") && (CurioIntegration.findItem(FURItemRegistry.ILLAGER_NOSE.get(), living) != ItemStack.EMPTY);
    			return living.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(FURItemRegistry.ILLAGER_NOSE.get()) || noseInCurios;
			}));
    	}
    }
    
    @SubscribeEvent
    public void onEAttack(LivingAttackEvent event) {
    	// Ghostly Armor - both the full-set spirit-form invulnerability and the 2-piece dodge are
    	// deliberately hooked here rather than in onEDamage (LivingDamageEvent) - Forge gates the very
    	// first line of LivingEntity#hurt() on this event (ForgeHooks.onLivingAttack), so canceling it
    	// makes hurt() return false immediately, before any sound/knockback/hurt-animation runs.
    	// Canceling at LivingDamageEvent only zeroes the health change; the hurt sound and the attacker's
    	// hit-confirm sound/knockback are already decided from hurt()'s own local damage estimate by that
    	// point and play regardless. Cancelling here also flips the attacker's swing outcome to a miss
    	// (e.g. a player attacker gets SoundEvents.PLAYER_ATTACK_NODAMAGE instead of a hit sound).

    	// Full-set: spirit form is 100% invulnerability to any damage source (not scoped to melee/
    	// projectile like the dodge below - fall/fire/drowning/etc. should all be blocked too, matching
    	// the original "full invulnerability" spec). No sound/particle here - the trigger already
    	// announced itself, and this can fire every attack tick a mob still tries (even though
    	// onESetTarget/the untarget sweep in triggerGhostlySpiritForm mean that should be rare).
    	if (event.getEntity() instanceof Player spiritPlayer && spiritPlayer.hasEffect(FUREffectRegistry.SPIRIT_FORM.get())) {
    		event.setCanceled(true);
    		return;
    	}

    	// 2-piece bonus: chance to fully negate an incoming hit. Melee + projectile only (same tag/type
    	// check used elsewhere in this class) - fall damage, fire, drowning, magic, explosions etc.
    	// aren't "hits" a dodge should be able to shrug off.
    	DamageSource attackSource = event.getSource();
    	boolean isMeleeOrProjectile = attackSource.is(DamageTypeTags.IS_PROJECTILE) || attackSource.is(DamageTypes.MOB_ATTACK)
    			|| attackSource.is(DamageTypes.MOB_ATTACK_NO_AGGRO) || attackSource.is(DamageTypes.PLAYER_ATTACK);
    	if (isMeleeOrProjectile && event.getEntity() instanceof Player player
    			&& GhostlyArmorItem.hasGhostlyPieces(player, GhostlyArmorItem.TWO_PIECE_THRESHOLD)
    			&& player.getRandom().nextInt(100) < FURConfig.Ghostly_DodgeChance.get()) {
    		event.setCanceled(true);
    		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), FURSoundRegistry.BANSHEE_HURT.get(), SoundSource.PLAYERS, 0.5F, 1.6F);
    	}
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
        
    /**
     * Per-block dedup so a dreamcatcher gains at most +1 charge per night even when several players sleep
     * in range: maps block position to the game-day it was last charged. All sleepers wake on the same
     * dawn tick, so an entry matching the current day means "already charged tonight". Pruned each wake.
     */
    private static final Map<BlockPos, Long> DREAMCATCHER_CHARGED_DAY = new HashMap<>();

    /**
     * Charges nearby dreamcatchers when a player has actually slept through the night. Forge fires
     * {@code PlayerWakeUpEvent} with {@code updateLevel() == false} only for the server-initiated dawn
     * wake (i.e. the night was skipped); a manual early exit reports {@code true} and is ignored here.
     */
    @SubscribeEvent
    public void onEWakeup(PlayerWakeUpEvent event) {
        if (event.updateLevel()) {
            return; // Player left the bed early — the night was not passed.
        }
        if (!FURConfig.Dreamcatcher_Enabled.get()) {
            return;
        }

        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level)) {
            return;
        }

        int radius = FURConfig.Dreamcatcher_ChargeRadius.get();
        long day = level.getDayTime() / 24000L;
        DREAMCATCHER_CHARGED_DAY.values().removeIf(charged -> charged < day);

        BlockPos origin = player.blockPosition();
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    cursor.set(origin.getX() + dx, origin.getY() + dy, origin.getZ() + dz);
                    BlockState state = level.getBlockState(cursor);
                    if (!(state.getBlock() instanceof DreamcatcherBlock)) {
                        continue;
                    }

                    BlockPos pos = cursor.immutable();
                    Long lastCharged = DREAMCATCHER_CHARGED_DAY.get(pos);
                    if (lastCharged != null && lastCharged == day) {
                        continue; // Already charged tonight by another sleeper.
                    }
                    DREAMCATCHER_CHARGED_DAY.put(pos, day);

                    int charge = state.getValue(DreamcatcherBlock.CHARGE);
                    if (charge < 5) {
                        level.setBlock(pos, state.setValue(DreamcatcherBlock.CHARGE, charge + 1), 3);
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onActiveItemUseStart(LivingEntityUseItemEvent.Start event) {
	    //int Armor_Swine_lvl = 0;

    	if (event.getEntity().hasEffect(FUREffectRegistry.SOILED.get()) &&
    			!FUREffectRegistry.SOILED.get().getCurativeItems().contains(event.getItem()) &&
    			(event.getItem().isEdible() || event.getItem().getItem() instanceof PotionItem)) {
    		event.setCanceled(true);
    	}

    	// Ghostly Armor spirit form: no eating/drinking/charged item use (bows, crossbows, ...) while active.
    	if (event.getEntity().hasEffect(FUREffectRegistry.SPIRIT_FORM.get())) {
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
	        		    new ItemStack(FURItemRegistry.BLOATED_GUT.get(), 4),  // player to trade
	        		    new ItemStack(Items.EMERALD, 1),                            // player to get
	        		    12,                                                         // max uses
	        		    1,                                                          // xp
	        		    0.05F                                                       // price multiplier
	        		));
	        	event.getTrades().get(2).add((trader, rand) -> new MerchantOffer(
	        		    new ItemStack(FURItemRegistry.PLAGUED_PORKCHOP.get(), 1),
	        		    new ItemStack(Items.EMERALD, 1),
	        		    12,
	        		    1,
	        		    0.05F
	        		));	        	
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
    		genericTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 6),
    	            new ItemStack(FURItemRegistry.PIGBOARHIDE.get(), 1),
    	            12, 
    	            1, 
    	            0.05f
    	        ));    		

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
    		rareTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 18),
    	            new ItemStack(FURItemRegistry.PHEROMONE_GLAND.get(), 1),
    	            4, 
    	            20, 
    	            0.05f
    	        ));   		
    		// 1.16.5 charged 80 emeralds, which exceeds one stack and made the trade uncompletable;
    		// capped to 64 so it fits the trade slot.
    		rareTrades.add((trader, rand) -> new MerchantOffer(
    	            new ItemStack(Items.EMERALD, 64),
    	            new ItemStack(FURItemRegistry.STAINED_KINGS_CROWN.get(), 1),
    	            2,
    	            30,
    	            0.05f
    	        ));
    	}
    }
    
    @SubscribeEvent
    public void onEHeal(LivingHealEvent event) {
    	float effectlevel = 1.0F;

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
    	final Player player = event.getEntity();

    	// Opening a chest while not in creative draws nearby Grave Robbers onto the looter.
    	if (!player.level().isClientSide() && !player.isCreative() && event.getContainer() instanceof ChestMenu) {
    		AABB area = AABB.unitCubeFromLowerCorner(player.position()).inflate(16.0D, 10.0D, 16.0D);
    		for (GraveRobberEntity robber : player.level().getEntitiesOfClass(GraveRobberEntity.class, area)) {
    			robber.setTarget(player);
    		}
    	}
    }

    /**
     * Per-instance state for {@link #onTombAmbush}, keyed by structure bounding-box centre.
     * {@code TOMB_AMBUSH_ARMED_AT}/{@code TOMB_AMBUSH_ARMED_ENTRANCE} hold a pending ambush's fire
     * time and spawn position - captured the moment the player passes the entrance marker, so the
     * delayed spawn still lands at the entrance even if the player has since wandered off deeper
     * into the tomb. {@code TOMB_AMBUSH_COOLDOWN_UNTIL} is set the moment an ambush arms (not when
     * it fires), so a player camping the entrance can't re-arm a second batch while the first is
     * still walking in. None of this is persisted across restarts - acceptable since this is
     * ambience, not a one-shot reward.
     */
    private static final Map<BlockPos, Long> TOMB_AMBUSH_ARMED_AT = new HashMap<>();
    private static final Map<BlockPos, BlockPos> TOMB_AMBUSH_ARMED_ENTRANCE = new HashMap<>();
    private static final Map<BlockPos, Long> TOMB_AMBUSH_COOLDOWN_UNTIL = new HashMap<>();
    private static final int TOMB_AMBUSH_DELAY_MIN_TICKS = 100; // 5s
    private static final int TOMB_AMBUSH_DELAY_MAX_TICKS = 200; // 10s
    private static final double TOMB_AMBUSH_TRIGGER_RADIUS_SQR = 6.0D * 6.0D; // "passing by" the marker
    private static final long TOMB_AMBUSH_COOLDOWN_TICKS = 24000L; // 1 in-game day

    /**
     * Grave Robber reinforcements: once a player passes within a few blocks of a generated
     * {@code royal_tomb} instance's baked-in {@code fur:tomb_entrance} marker (a
     * {@code minecraft:marker} entity placed in the structure NBT at the entrance), arms a delayed
     * ambush - 2-3 Grave Robbers spawn at that marker 5-10s later, flavor for "word got out
     * someone's digging in the tomb" rather than an instant, obviously scripted pop-in.
     */
    @SubscribeEvent
    public void onTombAmbush(final TickEvent.PlayerTickEvent event) {
    	if (event.phase == TickEvent.Phase.START) {
    		return;
    	}

    	Player player = event.player;
    	if (!(player.level() instanceof ServerLevel serverLevel) || serverLevel.getDifficulty() == Difficulty.PEACEFUL) {
    		return;
    	}
    	if ((serverLevel.getGameTime() & 0x1FL) > 0L) {
    		return;
    	}

    	StructureStart structureStart = serverLevel.structureManager().getStructureWithPieceAt(player.blockPosition(), FURStructureTagsProvider.ROYAL_TOMB);
    	if (!structureStart.isValid()) {
    		return;
    	}

    	BoundingBox box = structureStart.getBoundingBox();
    	BlockPos key = box.getCenter();
    	long now = serverLevel.getGameTime();

    	Long armedAt = TOMB_AMBUSH_ARMED_AT.get(key);
    	if (armedAt != null) {
    		if (now >= armedAt) {
    			TOMB_AMBUSH_ARMED_AT.remove(key);
    			BlockPos entrancePos = TOMB_AMBUSH_ARMED_ENTRANCE.remove(key);
    			if (serverLevel.random.nextFloat() < 0.5F) {
    				int reinforcements = 2 + serverLevel.random.nextInt(4);
    				for (int i = 0; i < reinforcements; i++) {
    					SpawnUtil.trySpawnEntity(FUREntityRegistry.GRAVEROBBER.get(), serverLevel, entrancePos);
    				}
    			}
    		}
    		return; // already armed (or just fired) - don't re-arm this pass
    	}

    	Long cooldownUntil = TOMB_AMBUSH_COOLDOWN_UNTIL.get(key);
    	if (cooldownUntil != null && now < cooldownUntil) {
    		return;
    	}

    	AABB searchArea = new AABB(box.minX(), box.minY(), box.minZ(), box.maxX() + 1, box.maxY() + 1, box.maxZ() + 1);
    	List<Marker> entrances = serverLevel.getEntitiesOfClass(Marker.class, searchArea, marker -> marker.getTags().contains("fur:tomb_entrance"));
    	if (entrances.isEmpty()) {
    		return;
    	}

    	Marker entrance = entrances.get(0);
    	if (player.distanceToSqr(entrance) > TOMB_AMBUSH_TRIGGER_RADIUS_SQR) {
    		return; // in the tomb, but hasn't passed the entrance yet
    	}

    	int delay = TOMB_AMBUSH_DELAY_MIN_TICKS + serverLevel.random.nextInt(TOMB_AMBUSH_DELAY_MAX_TICKS - TOMB_AMBUSH_DELAY_MIN_TICKS + 1);
    	TOMB_AMBUSH_ARMED_AT.put(key, now + delay);
    	TOMB_AMBUSH_ARMED_ENTRANCE.put(key, entrance.blockPosition());
    	TOMB_AMBUSH_COOLDOWN_UNTIL.put(key, now + delay + TOMB_AMBUSH_COOLDOWN_TICKS);
    }

    @SubscribeEvent
    public void onESetTarget(LivingChangeTargetEvent event) {
        LivingEntity entity = event.getEntity();
        LivingEntity newTarget = event.getNewTarget();

        // Chitin Armor 2-piece bonus: Arthropod mobs' effective target-detection range against the
        // wearer is cut to 25% of normal, i.e. targeting is only allowed within a quarter of the
        // mob's usual follow range. Event-driven — runs once per target-acquisition attempt, no
        // per-tick polling.
        if (newTarget instanceof Player player && entity instanceof Mob mob && mob.getMobType().equals(MobType.ARTHROPOD)
        		&& ChitinArmorItem.hasChitinPieces(player, ChitinArmorItem.TWO_PIECE_THRESHOLD)) {
        	double allowedRange = mob.getAttributeValue(Attributes.FOLLOW_RANGE) * ChitinArmorItem.DETECTION_RANGE_FACTOR;
        	if (mob.distanceToSqr(player) > allowedRange * allowedRange) {
        		event.setCanceled(true);
        		return;
        	}
        }

        // Ghostly Armor full-set: spirit form makes the wearer untargetable, not just unhittable - refuse
        // any new targeting attempt outright. Mobs that already had them targeted before spirit form
        // started are cleared separately (triggerGhostlySpiritForm), since cancelling this event only
        // blocks future setTarget() calls, not one already in effect.
        if (newTarget instanceof Player spiritPlayer && spiritPlayer.hasEffect(FUREffectRegistry.SPIRIT_FORM.get())) {
        	event.setCanceled(true);
        	return;
        }

        // Scarecrow/Cactoid/Mimic/Cactyrant disguise: while camouflaged (sitting pose for the first
        // three, the separate isCamouflaging() flag for Cactyrant since it isn't a TamableAnimal), no
        // unit may acquire them as a target - they read as an inanimate scarecrow/cactus/chest to any
        // AI, not just to the player. Only blocks new targeting attempts, same scope as the Ghostly
        // Armor spirit-form check above; a mob that already had one of these targeted before it
        // disguised keeps that target (disguising only happens once nothing is currently after them).
        if ((newTarget instanceof ScarecrowEntity || newTarget instanceof CactoidEntity || newTarget instanceof MimicEntity)
        		&& ((TamableAnimal)newTarget).isInSittingPose()) {
        	event.setCanceled(true);
        	return;
        }
        if (newTarget instanceof CactyrantEntity cactyrant && cactyrant.isCamouflaging()) {
        	event.setCanceled(true);
        	return;
        }

    	// Neutral
        if (newTarget != null && entity.getLastHurtByMob() != newTarget) {
        	boolean hasNose = newTarget.getItemBySlot(EquipmentSlot.HEAD).getItem().equals(FURItemRegistry.ILLAGER_NOSE.get());

        	if (ModList.get().isLoaded("curios") && !hasNose) {
        		hasNose = CurioIntegration.findItem(FURItemRegistry.ILLAGER_NOSE.get(), newTarget) != ItemStack.EMPTY;
        	}

        	if (entity.getMobType().equals(MobType.ILLAGER) && hasNose) {
        		event.setCanceled(true);
        	}

        	if (entity instanceof Mob mob && mob.getMobType().equals(MobType.ARTHROPOD) && newTarget.hasEffect(FUREffectRegistry.CHARMING_PHEROMONE.get())) {
        		mob.setTarget(null);
        		mob.getNavigation().moveTo(newTarget.getX(), newTarget.getY(), newTarget.getZ(), mob.getMoveControl().getSpeedModifier());
        	}
        }
        
        // Passive
        if (newTarget != null && entity instanceof PathfinderMob mob && SkeletonKingCrownItem.isEligible(mob) && SkeletonKingCrownItem.isWearingCrown(newTarget)) {
        	mob.setTarget(null);
        }
    }

    /**
     * Chitin Armor 2-piece bonus: prevents Arthropod-type mobs (spiders, Parasites, ...) from mounting
     * the wearer. Covers the Parasite's host-attachment mechanic and any future Arthropod mob using the
     * same {@code startRiding()} approach. Event-driven — fires once per mount attempt, no continuous
     * checking.
     */
    @SubscribeEvent
    public void onEntityMount(EntityMountEvent event) {
    	if (event.isMounting() && event.getEntityMounting() instanceof Mob mob && mob.getMobType().equals(MobType.ARTHROPOD)
    			&& event.getEntityBeingMounted() instanceof Player player && ChitinArmorItem.hasChitinPieces(player, ChitinArmorItem.TWO_PIECE_THRESHOLD)) {
    		event.setCanceled(true);
    	}
    }

    /**
     * Ghostly Armor spirit form: blocks the wearer's own melee attacks while active. Combined with the
     * item-use blocks below (which cover bows/crossbows/tridents, since those are used via right-click),
     * this covers "no player-initiated attacking of any kind" without needing a separate ranged-attack hook.
     */
    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
    	if (event.getEntity().hasEffect(FUREffectRegistry.SPIRIT_FORM.get())) {
    		event.setCanceled(true);
    	}
    }

    /** Ghostly Armor spirit form: blocks right-click item use (eating, drinking, tool use, ...) while active. */
    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
    	if (event.getEntity().hasEffect(FUREffectRegistry.SPIRIT_FORM.get())) {
    		event.setCanceled(true);
    	}
    }

    /** Ghostly Armor spirit form: blocks right-click block interaction (chests, doors, ...) while active. */
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    	if (event.getEntity().hasEffect(FUREffectRegistry.SPIRIT_FORM.get())) {
    		event.setCanceled(true);
    	}
    }

    /**
     * Holy Water alone starts a Zombie Villager's conversion back into a Villager - no Weakness needed
     * first, unlike the Golden Apple method. The timer/particles/sound/delay themselves still exactly
     * match a Golden Apple cure; only the "must be weakened first" gate is skipped. Doesn't replace the
     * Golden Apple method, just gives Holy Water its own, simpler one.
     *
     * <p>{@code ZombieVillager#startConverting(UUID, int)} - the vanilla method that actually starts it -
     * is private, only ever called from vanilla's own {@code mobInteract} (Golden Apple) and from
     * {@code readAdditionalSaveData} when it finds a saved "ConversionTime" tag (loading a
     * mid-conversion zombie villager back in). The latter is public on {@code ZombieVillager} (widened
     * from the supertype's {@code protected abstract}), so calling it directly with a "ConversionTime"/
     * "ConversionPlayer" tag reaches the exact same private logic - reflection isn't needed at all. Round-
     * tripping through {@link net.minecraft.world.entity.Entity#saveWithoutId} first (rather than handing
     * it a bare tag with just those two keys) matters: unlike {@code Entity#load}, this method doesn't
     * touch position/rotation, but {@code LivingEntity#readAdditionalSaveData} does unconditionally reset
     * a few fields (AbsorptionAmount, HurtTime, DeathTime, HurtByTimestamp) to their tag value with no
     * "if contains" guard - reusing its own just-saved tag means those round-trip back to what they
     * already were instead of getting zeroed out.
     */
    @SubscribeEvent
    public void onEntityInteractHolyWater(PlayerInteractEvent.EntityInteract event) {
    	ItemStack stack = event.getItemStack();
    	if (!(event.getTarget() instanceof ZombieVillager zombieVillager) || !stack.is(FURItemRegistry.HOLY_WATER.get())) {
    		return;
    	}

    	if (zombieVillager.isConverting()) {
    		event.setCancellationResult(InteractionResult.CONSUME);
    		event.setCanceled(true);
    		return;
    	}

    	Player player = event.getEntity();
    	if (!player.getAbilities().instabuild) {
    		stack.shrink(1);
    	}

    	if (!event.getLevel().isClientSide()) {
    		CompoundTag tag = zombieVillager.saveWithoutId(new CompoundTag());
    		tag.putInt("ConversionTime", zombieVillager.getRandom().nextInt(2401) + 3600);
    		tag.putUUID("ConversionPlayer", player.getUUID());
    		zombieVillager.readAdditionalSaveData(tag);
    	}

    	event.setCancellationResult(InteractionResult.SUCCESS);
    	event.setCanceled(true);
    }

    /**
     * Chitin Armor full-set bonus: the wearer takes no fall damage for falls up to
     * {@link ChitinArmorItem#FALL_IMMUNITY_DISTANCE} blocks. Falls beyond that are calculated
     * normally against the full distance, not merely reduced by the immune portion.
     */
    @SubscribeEvent
    public void onEFall(LivingFallEvent event) {
    	if (event.getEntity() instanceof Player player && event.getDistance() <= ChitinArmorItem.FALL_IMMUNITY_DISTANCE
    			&& ChitinArmorItem.hasChitinPieces(player, ChitinArmorItem.FULLSET_THRESHOLD)) {
    		event.setCanceled(true);
    	}
    }

    /** Movement/locomotion GameEvents suppressed by the Chitin Armor full-set sneaking bonus. */
    private static final Set<GameEvent> CHITIN_SUPPRESSED_VIBRATIONS = Set.of(
    		GameEvent.STEP, GameEvent.SWIM, GameEvent.SPLASH, GameEvent.FLAP, GameEvent.ELYTRA_GLIDE, GameEvent.HIT_GROUND);

    /**
     * Chitin Armor full-set bonus: while sneaking, fully blocks the wearer's movement-related
     * GameEvents (steps, swims, landings, ...) from reaching vibration listeners (Sculk Sensors,
     * Wardens). This is separate from the footstep sound muting in {@link #onPlayLevelSound} —
     * Wardens/Sculk Sensors listen to GameEvent vibrations, not SoundEvents, so silencing the sound
     * alone would not hide the wearer from them. Vanilla already withholds most movement GameEvents
     * while sneaking-and-grounded; this closes the remaining cases (mid-air, swimming, ...) with a
     * full block rather than a partial reduction, unlike the detection-range dampening in
     * {@link #onESetTarget}.
     */
    @SubscribeEvent
    public void onVanillaGameEvent(VanillaGameEvent event) {
    	if (!CHITIN_SUPPRESSED_VIBRATIONS.contains(event.getVanillaEvent())) {
    		return;
    	}

    	if (event.getCause() instanceof Player player && player.isShiftKeyDown()
    			&& ChitinArmorItem.hasChitinPieces(player, ChitinArmorItem.FULLSET_THRESHOLD)) {
    		event.setCanceled(true);
    	}
    }

    /**
     * Chitin Armor full-set bonus: mutes the wearer's own footstep sounds while sneaking. Runs off
     * {@link PlayLevelSoundEvent.AtPosition}, the path {@code Entity#playStepSound} broadcasts
     * through, since footstep sounds carry no entity reference there — matched instead by exact
     * position against currently-sneaking full-set wearers, and by sound identity
     * ({@link ChitinArmorItem#isStepSound}) so unrelated self-sounds (hurt, eat, ...) broadcast
     * through the same path are left untouched. Vibration suppression for the same sneaking bonus is
     * handled separately in {@link #onVanillaGameEvent} — Sculk Sensors/Wardens don't listen to sound.
     */
    @SubscribeEvent
    public void onPlayLevelSound(PlayLevelSoundEvent.AtPosition event) {
    	if (event.getSound() == null || !ChitinArmorItem.isStepSound(event.getSound().value())) {
    		return;
    	}
    	if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
    		return;
    	}

    	Vec3 pos = event.getPosition();
    	for (Player player : serverLevel.players()) {
    		if (player.isShiftKeyDown() && ChitinArmorItem.hasChitinPieces(player, ChitinArmorItem.FULLSET_THRESHOLD)
    				&& player.position().distanceToSqr(pos) < 1.0E-6D) {
    			event.setCanceled(true);
    			return;
    		}
    	}
    }

    /**
     * Drives the Skeleton King's Crown's escort effect from the governed mob's own tick rather than
     * the wearer's armor tick, since armor ticking stops firing the moment the crown is removed and
     * a removal needs to be noticed too. Every 20 ticks: a governed mob whose owner no longer wears
     * the crown (offline, dead, unequipped — see {@link SkeletonKingCrownItem#getOwnerId}) is
     * released back to its original AI; an ungoverned, eligible mob next to a wearer is placed under
     * their command. No persisted flag is involved — {@link SkeletonKingCrownItem#getOwnerId} reads
     * the goal selector directly, so this is fully self-correcting after a chunk reload with nothing
     * left to desync. Eligibility ({@link SkeletonKingCrownItem#isEligible}) is data-driven off the
     * vanilla {@code minecraft:skeletons} entity type tag (which this mod already extends with its
     * own reskins), not a Java type check, so adding a future skeleton-family mob to that tag is
     * enough to bring it under the crown's effect — no code change needed here.
     */
    @SubscribeEvent
    public void onSkeletonGuardUpkeep(LivingTickEvent event) {
    	if (!(event.getEntity() instanceof PathfinderMob mob) || !SkeletonKingCrownItem.isEligible(mob) || mob.level().isClientSide()) {
    		return;
    	}
    	if (mob.tickCount % 20 != 0 || !(mob.level() instanceof ServerLevel serverLevel)) {
    		return;
    	}

    	UUID ownerId = SkeletonKingCrownItem.getOwnerId(mob);

    	if (ownerId != null) {
    		LivingEntity owner = SpawnUtil.getEntityByUniqueId(ownerId, serverLevel);
    		if (!(owner instanceof Player player) || !SkeletonKingCrownItem.isWearingCrown(player)) {
    			SkeletonKingCrownItem.release(mob);
    		}
    		return;
    	}

    	if (!((mob.getNavigation() instanceof GroundPathNavigation) || (mob.getNavigation() instanceof FlyingPathNavigation))) {
    		return;
    	}

    	Player nearestWearer = null;
    	double nearestDistSqr = Double.MAX_VALUE;

    	for (Player player : serverLevel.getEntitiesOfClass(Player.class, mob.getBoundingBox().inflate(16.0D))) {
    		if (!SkeletonKingCrownItem.isWearingCrown(player)) {
    			continue;
    		}

    		double distSqr = mob.distanceToSqr(player);
    		if (distSqr < nearestDistSqr) {
    			nearestDistSqr = distSqr;
    			nearestWearer = player;
    		}
    	}

    	if (nearestWearer != null) {
    		SkeletonKingCrownItem.govern(mob, nearestWearer);
    	}
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

    /**
     * Vespa Ovum incubation tick. This fires for every living entity every tick, so the early-outs
     * (client side, then the missing-tag check) are kept as cheap as possible.
     */
    @SubscribeEvent
    public void onInfestTick(LivingTickEvent event) {
    	if (event.getEntity().level().isClientSide) {
    		return;
    	}

    	LivingEntity host = event.getEntity();
    	CompoundTag data = host.getPersistentData();
    	if (!data.contains(VespaInfestation.TICKS_KEY)) {
    		return;
    	}

    	int ticks = data.getInt(VespaInfestation.TICKS_KEY) - 1;
    	int stage = VespaInfestation.stageForTicks(ticks);
    	data.putInt(VespaInfestation.TICKS_KEY, ticks);
    	data.putInt(VespaInfestation.STAGE_KEY, stage);

    	// Cosmetic twitch: nudge the host upward so the client sees it convulse. No damage dealt.
    	if (ticks % 40 == 0) {
    		host.setDeltaMovement(host.getDeltaMovement().add(0.0D, 0.12D, 0.0D));
    		host.hurtMarked = true;
    	}

    	// Shaking: drive vanilla's built-in "fully frozen" shudder rather than nudging physics. Holding the
    	// host at/above its freeze threshold makes LivingEntityRenderer#isShaking oscillate the whole model,
    	// and DATA_TICKS_FROZEN syncs to clients on its own — no packets or client code needed, and it works
    	// on vanilla hosts. baseTick() bleeds off 2/tick when the host is not in powder snow, so top it back
    	// up every tick with a margin. Vanilla deals freeze damage to any fully-frozen entity, so onInfest-
    	// FreezeImmunity cancels it while incubating to keep this cosmetic. Only from stage 1 (ticks < 800)
    	// onward, so early incubation stays calm and the trembling ramps in as emergence nears.
    	if (stage >= 1) {
    		host.setTicksFrozen(host.getTicksRequiredToFreeze() + 5);
    	}

    	if (ticks <= 0) {
    		this.vespaEmergence(host);
    	}
    }

    /**
     * Emergence: the incubation finished, so the host bursts. Order matters — the infestation NBT is
     * stripped BEFORE the host dies so the early-harvest handler (which keys off the tag) does not
     * misread this death as an early harvest.
     */
    private void vespaEmergence(LivingEntity host) {
    	Level level = host.level();

    	// 1. Read the injector before clearing, then strip infestation state first.
    	UUID ownerId = VespaInfestation.getOwner(host);
    	VespaInfestation.clear(host);

    	double ex = host.getX(), ey = host.getY(), ez = host.getZ();

    	// 2. Kill the host (plain generic source; no custom damage type).
    	host.hurt(host.damageSources().generic(), Float.MAX_VALUE);

    	if (level instanceof ServerLevel serverLevel) {
    		// The brood is tamed to the player who injected the ovum (if still online).
    		Player owner = ownerId != null ? serverLevel.getPlayerByUUID(ownerId) : null;

    		// 3. Spawn 1-3 skin-2 parasites with a small random outward nudge.
    		int spawnCount = 1 + host.getRandom().nextInt(3);
    		for (int i = 0; i < spawnCount; ++i) {
    			ParasiteEntity parasite = FUREntityRegistry.PARASITE.get().create(level);
    			if (parasite == null) {
    				continue;
    			}
    			parasite.setSkin(2);
    			parasite.moveTo(ex, ey, ez, level.random.nextFloat() * 360.0F, 0.0F);
    			double dx = (host.getRandom().nextDouble() - 0.5D) * 0.4D;
    			double dz = (host.getRandom().nextDouble() - 0.5D) * 0.4D;
    			parasite.setDeltaMovement(dx, 0.3D, dz);
    			if (owner != null) {
    				parasite.tame(owner);
    			}
    			level.addFreshEntity(parasite);
    		}

    		// 4. Burst particles + sound.
    		serverLevel.sendParticles(ParticleTypes.ITEM_SLIME, ex, ey + host.getBbHeight() * 0.5D, ez, 30, 0.2D, 0.2D, 0.2D, 0.05D);
    		level.playSound(null, ex, ey, ez, SoundEvents.SLIME_SQUISH, SoundSource.HOSTILE, 1.0F, 0.8F);
    	}
    }

    /**
     * The Vespa Ovum "shaking" is driven by holding the host fully frozen, but vanilla deals 1 freeze
     * damage every 40 ticks to ANY fully-frozen entity (LivingEntity.baseTick — not gated on powder snow).
     * Left unchecked that whittles the host down and kills it before emergence, which then trips the
     * early-harvest path instead of bursting. Suppress freeze damage while an infestation is incubating.
     */
    @SubscribeEvent
    public void onInfestFreezeImmunity(LivingAttackEvent event) {
    	if (event.getSource().is(DamageTypes.FREEZE)
    			&& event.getEntity().getPersistentData().contains(VespaInfestation.TICKS_KEY)) {
    		event.setCanceled(true);
    	}
    }

    /**
     * Early harvest: if the host dies while still carrying the infestation tag (i.e. before emergence
     * stripped it), the incubating brood can be salvaged as raw parasites. LivingDeathEvent has no
     * drops list, so this must be done here on LivingDropsEvent.
     */
    @SubscribeEvent
    public void onInfestHarvest(LivingDropsEvent event) {
    	LivingEntity entity = event.getEntity();
    	if (entity.level().isClientSide || !entity.getPersistentData().contains(VespaInfestation.TICKS_KEY)) {
    		return;
    	}

    	int dropCount = 1 + entity.getRandom().nextInt(3);
    	for (int i = 0; i < dropCount; ++i) {
    		ItemStack raw = new ItemStack(FURItemRegistry.PARASITE_RAW.get());
    		raw.getOrCreateTag().putInt("variant", 2);
    		event.getDrops().add(new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), raw));
    	}

    	VespaInfestation.clear(entity);
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
			for (ItemStack S : DirectAttacker.getArmorSlots()) {
				if (S.getItem() instanceof FamineArmorItem) {
					Armor_Famine_lvl++;
				}
			}

			if (Armor_Famine_lvl >= 4 && DirectAttacker instanceof LivingEntity) {
				event.setAmount(event.getAmount() + 2.0F);
			}

			if (DirectAttacker instanceof FURTameableEntity tamable) {
				event.setAmount(event.getAmount() + tamable.getWeaponEnchants().getBonusDamage(Attacked));
			}
			
	    	if (DirectAttacker instanceof LivingEntity living) {
	    		Item heldItem = living.getMainHandItem().getItem();
	    		if (heldItem.equals(FURItemRegistry.BONE_SWORD.get())) {
	    			event.setAmount(event.getAmount() + Math.min((float)FURConfig.BoneSword_DamageCap.get(), Attacked.getMaxHealth() * ((float)FURConfig.BoneSword_Damage.get() * 0.01F)));
	    		}
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