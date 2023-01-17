package com.Fishmod.mod_LavaCow.events;

import java.util.List;
import java.util.Map;
import java.util.Random;
import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.GraveRobberEntity;
import com.Fishmod.mod_LavaCow.entities.ParasiteEntity;
import com.Fishmod.mod_LavaCow.entities.WendigoEntity;
import com.Fishmod.mod_LavaCow.entities.aquatic.PiranhaEntity;
import com.Fishmod.mod_LavaCow.entities.aquatic.SwarmerEntity;
import com.Fishmod.mod_LavaCow.entities.flying.VespaEntity;
import com.Fishmod.mod_LavaCow.entities.flying.WarpedFireflyEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.LilSludgeEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.MimicEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.RavenEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.UnburiedEntity;
import com.Fishmod.mod_LavaCow.init.FURBlockRegistry;
import com.Fishmod.mod_LavaCow.init.FUREffectRegistry;
import com.Fishmod.mod_LavaCow.init.FUREnchantmentRegistry;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.item.ChitinArmorItem;
import com.Fishmod.mod_LavaCow.item.FamineArmorItem;
import com.Fishmod.mod_LavaCow.item.FelArmorItem;
import com.Fishmod.mod_LavaCow.item.GhostlyArmorItem;
import com.Fishmod.mod_LavaCow.item.SwineArmorItem;
import com.Fishmod.mod_LavaCow.item.VespaShieldItem;
import com.Fishmod.mod_LavaCow.misc.EmeraldForItemsTrade;
import com.Fishmod.mod_LavaCow.misc.ItemsForEmeraldsAndItemsTrade;
import com.Fishmod.mod_LavaCow.misc.ItemsForEmeraldsTrade;
import com.Fishmod.mod_LavaCow.misc.LootTableHandler;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
//@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class EventHandler {
	
    /**
     * Custom entity death event, using for manipulating vanilla entities loots or onDeath triggers.
     * Example: Spawn swarm of parasites when a zombie dies (10% chance)
     */
    @SubscribeEvent
    public void onEDeath(LivingDeathEvent event) {
    	Entity entity = event.getEntityLiving();
    	Entity killer = event.getSource().getDirectEntity();
	    World world = event.getEntityLiving().level;
	    int Armor_Famine_lvl = 0;
		
	    if(killer != null) {
			for(ItemStack S : killer.getArmorSlots()) {
				if(S.getItem() instanceof FamineArmorItem) {
					Armor_Famine_lvl++;
				}
			}
	    }
		
		if(killer != null && Armor_Famine_lvl >= 4 && killer instanceof PlayerEntity) {
			((PlayerEntity)killer).heal(1.0F);
			((PlayerEntity)killer).getFoodData().eat(4, 0.0F);
		}

    	/**
         * Give a chance to spawn horde of Parasites when a zombie dies.
         **/
    	if (!world.isClientSide() && 
    			(((LootTableHandler.PARASITE_HOSTLIST.contains(entity.getType().getRegistryName()) && (new Random().nextInt(100) < FURConfig.pSpawnRate_Parasite.get())) 
    			|| (ParasiteEntity.gotParasite(entity.getPassengers()) != null)
    			|| event.getEntityLiving().hasEffect(FUREffectRegistry.INFESTED)))) {
    		int var2 = 3 + new Random().nextInt(3), var6 = 0;
    		float var4,var5;
    		ParasiteEntity passenger = ParasiteEntity.gotParasite(entity.getPassengers());
    		if(event.getEntityLiving().hasEffect(FUREffectRegistry.INFESTED))
    			var6 = event.getEntityLiving().getEffect(FUREffectRegistry.INFESTED).getAmplifier();
    		
    		for(int var3 = 0; var3 < var2 + ((var6 - 1) * (1 + new Random().nextInt(3))); ++var3) {
    			var4 = ((float)(var3 % 2) - 0.5F) / 4.0F;
                var5 = ((float)(var3 / 2) - 0.5F) / 4.0F;
                
        		ParasiteEntity ParasiteEntity = FUREntityRegistry.PARASITE.create(world);
        		if(passenger != null)ParasiteEntity.setSkin(passenger.getSkin());
        		else if(BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(entity.level.getBiome(entity.blockPosition()))).contains(Type.DRY)) ParasiteEntity.setSkin(1);
        		else if(BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(entity.level.getBiome(entity.blockPosition()))).contains(Type.JUNGLE) || killer instanceof VespaEntity) ParasiteEntity.setSkin(2);
        		else ParasiteEntity.setSkin(0);
                ParasiteEntity.moveTo(entity.getX() + (double)var4, entity.getY() + 1.0D, entity.getZ() + (double)var5, entity.yRot, entity.xRot);
                world.addFreshEntity(ParasiteEntity);
    		}
    	}			
    	
    	if (entity instanceof MimicEntity) {
    		int ItemPos = ((MimicEntity)entity).containsItem(Items.TOTEM_OF_UNDYING);
    		            
    		if(ItemPos != -1) {
        		((MimicEntity)entity).setHealth(1.0F);
        		((MimicEntity)entity).removeAllEffects();
        		((MimicEntity)entity).addEffect(new EffectInstance(Effects.REGENERATION, 900, 1));
        		((MimicEntity)entity).addEffect(new EffectInstance(Effects.ABSORPTION, 100, 1));
        		((MimicEntity)entity).addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 800, 0));
        		((MimicEntity)entity).level.broadcastEntityEvent(((MimicEntity)entity), (byte)35);
    			
    			event.setCanceled(true);
    			
    			((MimicEntity)entity).inventory.getItem(ItemPos).shrink(1);
    		}
    	}
    }
    
    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
    	/**
         * Add bonus loot (Intestine) to various entities.
         **/
    	if (event.getEntityLiving() instanceof LivingEntity && event.getEntityLiving().getRandom().nextFloat() < 0.01F * (float)FURConfig.General_Intestine.get()) {          
	        if (!(FURConfig.Intestine_banlist.get().contains(event.getEntityLiving().getType().getRegistryName().toString()))) {
	            event.getEntityLiving().spawnAtLocation(FURItemRegistry.INTESTINE, 1);
	        }
        }
    	
    	if (event.isRecentlyHit() && event.getEntityLiving() instanceof AbstractIllagerEntity && event.getEntityLiving().getRandom().nextFloat() < 0.01F * (float)FURConfig.General_IllagerNose.get()) {          
            event.getEntityLiving().spawnAtLocation(FURItemRegistry.ILLAGER_NOSE, 1);
        }
    	
    	if (event.getEntityLiving().getTags().contains("FUR_noLoot")) {
    		event.setCanceled(true);
    	}
    }
    
    /**
     * Custom anvil event, using for making items into enchantment for weapons and tools
     * Example: Make glow shroom and parasite a good lure
     */
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {        
    	ItemStack tool = event.getLeft();
    	ItemStack ench = event.getRight();
    	ItemStack outputStack = tool.copy();
    	Map<Enchantment, Integer> currentEnchantments = EnchantmentHelper.getEnchantments(tool);
    	int ench_lvl = 1;
    	
    	if(Enchantments.FISHING_SPEED.canEnchant(tool) && ench.getItem() == FURItemRegistry.PARASITE_COMMON && !currentEnchantments.containsKey(Enchantments.FISHING_SPEED)) {
    		ench_lvl = 1;
    		event.setOutput(outputStack);
    		event.setCost(ench_lvl * 2);
			event.setOutput(event.getLeft().copy());
			event.getOutput().enchant(Enchantments.FISHING_SPEED, ench_lvl);
    		event.setMaterialCost(1);
    	} else if(Enchantments.FISHING_SPEED.canEnchant(tool) && ench.getItem() == FURBlockRegistry.GLOWSHROOM.asItem() && !currentEnchantments.containsKey(Enchantments.FISHING_SPEED)) {
    		ench_lvl = 3;
    		event.setOutput(outputStack);
    		event.setCost(ench_lvl * 2);
			event.setOutput(event.getLeft().copy());
			event.getOutput().enchant(Enchantments.FISHING_SPEED, ench_lvl);
    		event.setMaterialCost(1);
    	} else if(FUREnchantmentRegistry.POISONOUS.canEnchant(tool) && FURConfig.Enchantment_Enable.get() && ench.getItem() == FURItemRegistry.POISONSPORE && !currentEnchantments.containsKey(FUREnchantmentRegistry.POISONOUS)) {
    		ench_lvl = 3;
    		event.setOutput(outputStack);
    		event.setCost(13);
			event.setOutput(event.getLeft().copy());
			event.getOutput().enchant(FUREnchantmentRegistry.POISONOUS, ench_lvl);
    		event.setMaterialCost(1);
    	} else if(FUREnchantmentRegistry.LIFESTEAL.canEnchant(tool) && FURConfig.Enchantment_Enable.get() && ench.getItem() == FURItemRegistry.UNDYINGHEART && !currentEnchantments.containsKey(FUREnchantmentRegistry.LIFESTEAL)) {
    		ench_lvl = 3;
    		event.setOutput(outputStack);
    		event.setCost(13);
			event.setOutput(event.getLeft().copy());
			event.getOutput().enchant(FUREnchantmentRegistry.LIFESTEAL, ench_lvl);
    		event.setMaterialCost(1);
    	} else if(FUREnchantmentRegistry.CORROSIVE.canEnchant(tool) && FURConfig.Enchantment_Enable.get() && ench.getItem() == FURItemRegistry.ACIDICHEART && !currentEnchantments.containsKey(FUREnchantmentRegistry.CORROSIVE)) {
    		ench_lvl = 1;
    		event.setOutput(outputStack);
    		event.setCost(4);
			event.setOutput(event.getLeft().copy());
			event.getOutput().enchant(FUREnchantmentRegistry.CORROSIVE, ench_lvl);
    		event.setMaterialCost(1);
    	} else if(FUREnchantmentRegistry.CRITICALBOOST.canEnchant(tool) && FURConfig.Enchantment_Enable.get() && ench.getItem() == FURItemRegistry.SINISTER_WHETSTONE && !currentEnchantments.containsKey(FUREnchantmentRegistry.CRITICALBOOST)) {
    		CompoundNBT compoundnbt = ench.getTag();
    		ench_lvl = compoundnbt == null ? 1 : compoundnbt.getInt("level");
    		event.setOutput(outputStack);
    		event.setCost(4 * ench_lvl);
			event.setOutput(event.getLeft().copy());
			event.getOutput().enchant(FUREnchantmentRegistry.CRITICALBOOST, ench_lvl);		
    		event.setMaterialCost(1);
    	}
    }
    
    /**
     * Player on update event, repeat once per second
     * Add auto repairing effect for Golden Heart
     */
    @SubscribeEvent
    public void playerTick(final TickEvent.PlayerTickEvent event) {
    	if (event.phase == TickEvent.Phase.START) {
            return;
        }
        final PlayerEntity player = event.player;
        if (player.level.isClientSide()) {
            return;
        }

        if ((player.level.getGameTime() & 0x1FL) > 0L) {
            return;
        }     
		
		if(player.level.getDifficulty() != Difficulty.PEACEFUL && player.level.random.nextFloat() < 0.1F)
			for (ItemEntity ItemEntity : player.level.getEntitiesOfClass(ItemEntity.class, player.getBoundingBox().inflate(5.0F))) {
		    	if(((ItemEntity) ItemEntity).getItem().getItem().isEdible() && ((ItemEntity) ItemEntity).getItem().getItem().getFoodProperties().isMeat()) {	
					BlockPos pos = ItemEntity.blockPosition();
	
		            if(ItemEntity.isInWater() && BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(player.level.getBiome(pos))).contains(Type.WET)) {     		            			         	            	
						for(int i = 0; i < 2 + player.level.random.nextInt(3); i++) {	    				
		    				double posX = pos.getX() + ((player.level.random.nextDouble() * 5.0D) - 2.5D);
		    				double posY = pos.getY();
		    				double posZ = pos.getZ() + ((player.level.random.nextDouble() * 5.0D) - 2.5D);
		    				
		    				if(player.level.getBlockState(new BlockPos(posX, posY, posZ)).getMaterial() == Material.WATER) {
		    					if(SpawnUtil.isDay(player.level)) {
		    						PiranhaEntity fish = FUREntityRegistry.PIRANHA.create(player.level);
		    						
		    						fish.moveTo(posX, posY, posZ, player.level.random.nextFloat() * 360.0f, 0.0f);
				    				player.level.addFreshEntity(fish);
				    				
				    				if(ItemEntity != null) {
				    					ItemEntity.playSound(SoundEvents.GENERIC_EAT, 1, 1);
				    					ItemEntity.remove();
				    				}	
		    					} else {
		    						SwarmerEntity fish = FUREntityRegistry.SWARMER.create(player.level);
			    					
		    						fish.moveTo(posX, posY, posZ, player.level.random.nextFloat() * 360.0f, 0.0f);
				    				player.level.addFreshEntity(fish);
				    				
				    				if(ItemEntity != null) {
				    					ItemEntity.playSound(SoundEvents.GENERIC_EAT, 1, 1);
				    					ItemEntity.remove();
				    				}		    						
		    					}
		    				}
		    			}
		            }
		    	}
			}
    }
    
    @SubscribeEvent
    public static void onBlockDestroyed(BlockEvent.BreakEvent event) {   	
    	if(event.getState().getMaterial() == Material.SAND 
    		&& BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(event.getWorld().getBiome(event.getPos()))).contains(Type.HOT)
    		&& BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(event.getWorld().getBiome(event.getPos()))).contains(Type.DRY)
    		&& BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(event.getWorld().getBiome(event.getPos()))).contains(Type.SANDY)
    		&& BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(event.getWorld().getBiome(event.getPos()))).contains(Type.OVERWORLD)
    		&& new Random().nextInt(100) < FURConfig.Parasite_SandSpawn.get()
    		&& FURConfig.pSpawnRate_Parasite.get() > 0
    		) {          	 
    		ParasiteEntity ParasiteEntity = FUREntityRegistry.PARASITE.create((World) event.getWorld());
    		ParasiteEntity.setSkin(1);
            ParasiteEntity.moveTo(event.getPos().getX(), event.getPos().getY()+1.0D, event.getPos().getZ(), 0.0F, 0.0F);
            ParasiteEntity.setDeltaMovement(ParasiteEntity.getDeltaMovement().add(0.0D, 0.4D, 0.0D));
            event.getWorld().addFreshEntity(ParasiteEntity);
    	}
    }
    
    @SubscribeEvent
    public void onEDamage(LivingDamageEvent event) {
    	DamageSource source = event.getSource();
    	LivingEntity Attacked = event.getEntityLiving();
    	Entity Attacker = source.getDirectEntity();
    	float effectlevel = 1.0F;
	    int Armor_Famine_lvl = 0;
	    int Armor_Chitin_lvl = 0;
	    
	    if(event.getSource().isFire() && event.getEntityLiving().hasEffect(FUREffectRegistry.IMMOLATION)) {
	    	event.setCanceled(true);
	    	return;
	    }
	    
	    if(Attacker != null) {
			for(ItemStack S : Attacker.getArmorSlots()) {
				if(S.getItem() instanceof FamineArmorItem) {
					Armor_Famine_lvl++;
				}
			}
	    }
		
		for(ItemStack S : Attacked.getArmorSlots()) {
			if (S.getItem() instanceof ChitinArmorItem) {
				Armor_Chitin_lvl++;
			}
		}
		
		if(Attacker != null && Armor_Famine_lvl >= 2 && Attacker instanceof LivingEntity && ((LivingEntity) Attacker).hasEffect(Effects.HUNGER)) {
			event.setAmount(event.getAmount() + 2.0F);
		}

		if((Armor_Chitin_lvl >= 2) && source.equals(DamageSource.FALL)) {
			event.setAmount(event.getAmount() * 0.5F);
		}
		
		if(Attacker != null && Attacker instanceof LilSludgeEntity) {
			LivingEntity Owner = ((LilSludgeEntity)Attacker).getOwner();			
			event.setAmount(event.getAmount() + ((LilSludgeEntity)Attacker).getBonusDamage(Attacked));			
			if(Owner != null)
				Owner.heal(event.getAmount() * ((LilSludgeEntity)Attacker).getLifestealLevel() * 0.05f);
		} else if (Attacker != null && Attacker instanceof UnburiedEntity) {
			LivingEntity Owner = ((UnburiedEntity)Attacker).getOwner();		
			event.setAmount(event.getAmount() + ((UnburiedEntity)Attacker).getBonusDamage(Attacked));		
			if(Owner != null)
				Owner.heal(event.getAmount() * ((UnburiedEntity)Attacker).getLifestealLevel() * 0.05f);
		}
    	
    	if(Attacker != null && Attacked.isOnFire() && Attacker instanceof PlayerEntity) {   		
    		for(ItemStack S : Attacker.getArmorSlots()) {
    			if(S.getItem() instanceof FelArmorItem)effectlevel += ((FelArmorItem)S.getItem()).effectlevel;
    		}
    	}
    	
    	if(Attacked instanceof PlayerEntity && !Attacked.fireImmune() && source.isFire()) {    		
    		for(ItemStack S : Attacked.getArmorSlots()) {
    			if(S.getItem() instanceof FelArmorItem)effectlevel -= ((FelArmorItem)S.getItem()).fireprooflevel;
    		}
    		
    		boolean have_Heart = false;
    		/*if(Loader.isModLoaded("baubles")) {
    			if(baubles.api.BaublesApi.isBaubleEquipped((PlayerEntity) Attacked, FURItemRegistry.MOOTENHEART) != -1)
    				have_Heart = true;
    		}*/
    		
    		for(int i = 0; i < 9 ; i++)
    			if(((PlayerEntity)Attacked).inventory.getItem(i).getItem().equals(FURItemRegistry.MOOTENHEART) || ((PlayerEntity)Attacked).inventory.getItem(i).getItem().equals(FURItemRegistry.SOULFIREHEART))
					have_Heart = true;
    		
    		if(have_Heart)
    			effectlevel -= (float)FURConfig.MootenHeart_Damage.get() / 100.0F;
    	}
    	
    	if(source.isExplosion() && source.getEntity() instanceof WolfEntity) {
    		if(Attacked.getMobType().equals(CreatureAttribute.UNDEAD) && source.getEntity().getName().getString().equals("entity.mod_lavacow.holygrenade")) {
    			event.setAmount(event.getAmount() * 0.45F);
    			Attacked.setSecondsOnFire(8);
    		} else if (source.getEntity().getName().getString().equals("entity.mod_lavacow.ghostbomb")) {
    			Attacked.setDeltaMovement(0.0D, Attacked.getDeltaMovement().y, 0.0D);
    			Attacked.addEffect(new EffectInstance(Effects.LEVITATION, 20, 0));
    			event.setAmount(event.getAmount() * 0.20F);
    		} else if (source.getEntity().getName().getString().equals("entity.mod_lavacow.sonicbomb")) {
    			Attacked.addEffect(new EffectInstance(FUREffectRegistry.FEAR, 4 * 20, 2));
    			event.setAmount(event.getAmount() * 0.33F);
    		} else
    			event.setAmount(event.getAmount() * 0.15F);
    	}
    	
    	if(Attacker != null && Attacker instanceof LivingEntity) {
    		Item heldItem = ((LivingEntity)Attacker).getMainHandItem().getItem();
    		if(heldItem.equals(FURItemRegistry.BONESWORD))
    			event.setAmount(event.getAmount() + Math.min((float)FURConfig.BoneSword_DamageCap.get(), Attacked.getMaxHealth() * ((float)FURConfig.BoneSword_Damage.get() * 0.01F)));
    		else if(heldItem.equals(FURItemRegistry.SPECTRAL_DAGGER) && !Attacked.getMobType().equals(CreatureAttribute.UNDEAD))
    			event.setAmount(event.getAmount() + 2.0F);
    	}
    	
    	if(Attacked.hasEffect(FUREffectRegistry.CORRODED))
    		event.setAmount(event.getAmount() * (1.0F + 0.1F * (1 + Attacked.getEffect(FUREffectRegistry.CORRODED).getAmplifier())));
    	
    	if(Attacker != null && Attacked.hasEffect(FUREffectRegistry.THORNED)) {
    		if(source == DamageSource.CACTUS || source == DamageSource.SWEET_BERRY_BUSH || (source instanceof EntityDamageSource && ((EntityDamageSource) source).isThorns())) {
    			event.setCanceled(true);
    		} else if (!source.isMagic() && !source.isExplosion() && Attacker instanceof LivingEntity) {
    			Attacker.hurt(DamageSource.thorns(Attacked), 1.0F + Attacked.getEffect(FUREffectRegistry.THORNED).getAmplifier());
            }
    	}
    	
		int Armor_Ghostly_lvl = 0;
		
		for(ItemStack S : Attacked.getArmorSlots()) {
			if(S.getItem() instanceof GhostlyArmorItem) {
				Armor_Ghostly_lvl++;
			}
		}
		
		if(Armor_Ghostly_lvl >= 2) {
			Attacked.heal(event.getAmount() * 0.2F);
		}
		
		if (source.getEntity() != null && Attacked != null && source.getEntity() instanceof LivingEntity && (((LivingEntity) source.getEntity()).getHealth() < Attacked.getHealth())) {
			Armor_Ghostly_lvl = 0;
			
			for(ItemStack S : source.getEntity().getArmorSlots()) {
				if(S.getItem() instanceof GhostlyArmorItem) {
					Armor_Ghostly_lvl++;
				}
			}
			
			if(Armor_Ghostly_lvl >= 4) {
				event.setAmount(event.getAmount() * 1.2F);
			}
		}
    	
    	event.setAmount(event.getAmount() * effectlevel);
    }
    
    @SubscribeEvent
    public void onEFall(LivingFallEvent event) {
        if(FURConfig.Raven_Slowfall.get() && event.getEntityLiving().isVehicle()) {
        	for(Entity E : event.getEntityLiving().getPassengers()) {
        		if(E instanceof RavenEntity) {
        			event.setDistance(0.0F);
        			break;
        		}
        	}
    	} 
    }
    
	@SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
    	if(FURConfig.Wendigo_AnimalAttack.get() && event.getEntity() != null && event.getEntity() instanceof AgeableEntity && !(event.getEntity() instanceof TameableEntity)) {
    		((AgeableEntity)event.getEntity()).goalSelector.addGoal(1, new AvoidEntityGoal<>((AgeableEntity)event.getEntity(), WendigoEntity.class, 8.0F, 0.8D, 0.8D));
    	}
    	
    	if(event.getEntity() != null && event.getEntity() instanceof VillagerEntity)
    		((VillagerEntity)event.getEntity()).goalSelector.addGoal(1, new AvoidEntityGoal<>(((VillagerEntity)event.getEntity()), UnburiedEntity.class, 8.0F, 0.8D, 0.8D));

    	if(event.getEntity() != null && event.getEntity().getType().equals(EntityType.HOGLIN))
    		((HoglinEntity)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((HoglinEntity)event.getEntity()), WarpedFireflyEntity.class, 6.0F, 1.0D, 1.2D));
    	
    	if(event.getEntity() != null && event.getEntity() instanceof AbstractSkeletonEntity && event.getEntity().getTags().contains("FUR_tameSkeleton")) {
    		event.getEntity().removeTag("FUR_tameSkeleton");
    	}
    	
    	if(event.getEntity() != null && event.getEntity() instanceof IronGolemEntity) {
    		((IronGolemEntity)event.getEntity()).targetSelector.addGoal(5, new NearestAttackableTargetGoal<>((IronGolemEntity)event.getEntity(), PlayerEntity.class, 0, true, false, (p_210136_0_) -> {
				return p_210136_0_.getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(FURItemRegistry.ILLAGER_NOSE);
			}));	
    	}
    }
    
    @SubscribeEvent
    public void onEAttack(LivingAttackEvent event) {
    	LivingEntity attacked = event.getEntityLiving();
    	
    	for(Hand hand : Hand.values()) {
			ItemStack stack = attacked.getItemInHand(hand);
			if(!stack.isEmpty() && stack.getItem() instanceof VespaShieldItem) {
				VespaShieldItem shield = (VespaShieldItem)stack.getItem();
				
				if(shield.canBlockDamageSource(stack, attacked, hand, event.getSource())) {
					if(!attacked.level.isClientSide()) {
						shield.onAttackBlocked(stack, attacked, event.getAmount(), event.getSource());
					}
				}
			}
    	}
    }
        
    @SubscribeEvent
    public void onEWakeup(PlayerWakeUpEvent event) {
		ItemStack have_DreamCatcher = null;
		PlayerEntity player = event.getPlayer();
		World world = event.getPlayer().level;
		
		/*if(Loader.isModLoaded("baubles")) {
			int i = baubles.api.BaublesApi.isBaubleEquipped(player, FURItemRegistry.DREAMCATCHER);
			if(i != -1)
				have_DreamCatcher = baubles.api.BaublesApi.getBaublesHandler(player).getItem(i);
		}*/
		
		if (!event.updateWorld()) {
			for(int i = 0; i < 9 ; i++) {
				if(player.inventory.getItem(i).getItem().equals(FURItemRegistry.DREAMCATCHER)) {
					have_DreamCatcher = player.inventory.getItem(i);
					break;
				}
			}
		}
		
		if (!world.isClientSide() && have_DreamCatcher != null && !event.updateWorld()) {
			MobSpawnInfo.Spawners Result = ((MobSpawnInfo.Spawners)WeightedRandom.getRandomItem(world.random, LootTableHandler.DREAMCATCHER_LIST));

			Entity LivingEntity = Result.type.create(world);
			int min  = Result.minCount;
			int max  = Result.maxCount;
			boolean has_spawn = false;
			
			if (LivingEntity instanceof MobEntity) {
				for(int i = 0; i < new Random().nextInt(MathHelper.abs(max-min) + 1) + min; i++) {
					Entity LivingEntity_to_spawn = Result.type.create(world);
					double k1 = player.getX() + (world.random.nextDouble() * 32.0D) - 16.0D;
					double l1 = player.getY() + (world.random.nextDouble() * 4.0D) - 2.0D;
					double i2 = player.getZ() + (world.random.nextDouble() * 32.0D) - 16.0D;
					BlockPos pos = SpawnUtil.getHeight(world, new BlockPos(k1, l1, i2));
					
					LivingEntity_to_spawn.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
					((MobEntity) LivingEntity_to_spawn).finalizeSpawn(LivingEntity_to_spawn.getServer().overworld(), world.getCurrentDifficultyAt(pos), SpawnReason.MOB_SUMMONED, (ILivingEntityData)null, (CompoundNBT)null);
					world.addFreshEntity(LivingEntity_to_spawn);
					has_spawn = true;
				}
				
				if (has_spawn) {
					world.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PORTAL_TRIGGER, SoundCategory.BLOCKS, 1.0F, (1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.2F) * 0.7F);
					
					if (!player.isCreative())
						have_DreamCatcher.hurtAndBreak(20, event.getEntityLiving(), (p_220045_0_) -> {
			    			p_220045_0_.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
			    		});
				}
			}            	
		}
			
    }
    
    @SubscribeEvent
    public void onActiveItemUseStart(LivingEntityUseItemEvent.Start event) {
	    int Armor_Famine_lvl = 0;
	    int Armor_Swine_lvl = 0;
	    
		for (ItemStack S : event.getEntityLiving().getArmorSlots()) {
			if(S.getItem() instanceof FamineArmorItem) {
				Armor_Famine_lvl++;
			}
		}
		
    	if ((event.getEntityLiving().hasEffect(FUREffectRegistry.SOILED) || (Armor_Famine_lvl >= 4)) && event.getItem().isEdible()) {
    		event.setCanceled(true);
    	}
    	
    	if (event.getEntityLiving().hasEffect(FUREffectRegistry.SOILED)  && event.getItem().getItem() instanceof PotionItem) {
    		event.setCanceled(true);
    	}
    			
		for (ItemStack S : event.getEntityLiving().getArmorSlots()) {
			if (S.getItem() instanceof SwineArmorItem) {
				Armor_Swine_lvl++;
			}
		}
    	
    	if ((Armor_Swine_lvl >= 2) && event.getItem().getItem().isEdible()) {
    		event.setDuration((int) (event.getDuration() * 0.75F));
    	}
    }
    
    @SubscribeEvent
    public void onActiveItemUseFinish(LivingEntityUseItemEvent.Finish event) {
	    int Armor_Swine_lvl = 0;
		
		for (ItemStack S : event.getEntityLiving().getArmorSlots()) {
			if(S.getItem() instanceof SwineArmorItem) {
				Armor_Swine_lvl++;
			}
		}
    	
    	if (!event.getEntityLiving().level.isClientSide() && (Armor_Swine_lvl >= 4) && event.getItem().isEdible()) {
    		event.getEntityLiving().curePotionEffects(new ItemStack(Items.MILK_BUCKET));
    	}
    }
    
    /*private void MendingBaubles(Item item, EntityXPOrb xpOrb, PlayerEntity player, PlayerPickupXpEvent event) {
		int Heart_Slot = baubles.api.BaublesApi.isBaubleEquipped(player, item);
		
		if(Heart_Slot != -1 && EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, baubles.api.BaublesApi.getBaublesHandler(event.getPlayerEntity()).getItem(Heart_Slot)) > 0) {
			event.setCanceled(true);
			ItemStack itemStack = baubles.api.BaublesApi.getBaublesHandler(player).getItem(Heart_Slot);
			
            if (xpOrb.delayBeforeCanPickup == 0 && player.xpCooldown == 0) {

                player.xpCooldown = 2;
                player.onItemPickup(xpOrb, 1);
                int i = Math.min(xpOrb.xpValue * 2, itemStack.getItemDamage());
                xpOrb.xpValue -= i / 2;

                itemStack.setItemDamage(itemStack.getItemDamage() - i);

                if (xpOrb.xpValue > 0) {
                    player.addExperience(xpOrb.xpValue);
                }

                xpOrb.remove();
            }
		}
    }
    
    @SubscribeEvent
    public void onPlayerXPPickUp(PlayerPickupXpEvent event) {

        if (!event.getPlayerEntity().level.isClientSide()) {
    		if(Loader.isModLoaded("baubles")) {
    			MendingBaubles(FURItemRegistry.GOLDENHEART, event.getOrb(), event.getPlayerEntity(), event);
    			MendingBaubles(FURItemRegistry.DREAMCATCHER, event.getOrb(), event.getPlayerEntity(), event);
            }
        }
    }*/

	@SubscribeEvent
	public void playerDeath(LivingDropsEvent event) {
		if (event.isCanceled()) {
			return;
		}

		Entity entity = event.getEntityLiving();
		Random random = new Random();

		if (entity.level.isClientSide() || !(entity instanceof PlayerEntity) || random.nextInt(1000) > FURConfig.pSpawnRate_DeathMimic.get()) {
			return;
		}

		PlayerEntity player = (PlayerEntity) entity;
		AxisAlignedBB boundingBox = new AxisAlignedBB(player.blockPosition()).inflate(16);
		List<Entity> nearbyMimics = player.level.getEntitiesOfClass(MimicEntity.class, boundingBox);

		if (nearbyMimics.isEmpty()) {
			int spawnX = MathHelper.floor(player.getX() + random.nextInt(8) - 4);
			int spawnZ = MathHelper.floor(player.getZ() + random.nextInt(8) - 4);
			BlockPos spawn = new BlockPos(spawnX, player.level.getHeight(Heightmap.Type.WORLD_SURFACE, spawnX, spawnZ), spawnZ);

			// We show some pretty effects to indicate they magically spawned in or something like that.
			// Alternatively, we could make mimics look like they dug out of the ground?
			AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(player.level, spawnX, spawn.getY(), spawnZ);
	        areaeffectcloudentity.setRadius(5.0F);
	        areaeffectcloudentity.setRadiusOnUse(-0.5F);
	        areaeffectcloudentity.setWaitTime(5);
	        areaeffectcloudentity.setDuration(20);
	        areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
	        areaeffectcloudentity.setParticle(ParticleTypes.ENTITY_EFFECT);
			// No matter the value, this still appears as white to me?
	        areaeffectcloudentity.setFixedColor(0x6F5B3C);
			player.level.addFreshEntity(areaeffectcloudentity);
			
			MimicEntity Mimic = FUREntityRegistry.MIMIC.create(player.level);
			Mimic.setPersistenceRequired();
			Mimic.moveTo(spawn.getX(), spawn.getY(), spawn.getZ(), 0.0F, 0.0F);							
			Mimic.playAmbientSound();
		}
	}
	
    @SubscribeEvent
    public void onTradeSetup(VillagerTradesEvent event) {
    	if(FURConfig.BonusVillagerTrades.get()) {
	        if (event.getType() == VillagerProfession.FISHERMAN) {
	            List<ITrade> list = event.getTrades().get(2);
	            list.add(new ItemsForEmeraldsAndItemsTrade(FURItemRegistry.PIRANHA, 6, FURItemRegistry.PIRANHA_COOKED, 6, 16, 1));
	            list.add(new ItemsForEmeraldsAndItemsTrade(FURItemRegistry.SWARMER, 6, FURItemRegistry.SWARMER_COOKED, 6, 16, 1));
	            event.getTrades().put(2, list);
	        }
	        
	        if (event.getType() == VillagerProfession.BUTCHER) {
	            List<ITrade> list = event.getTrades().get(2);
	            list.add(new EmeraldForItemsTrade(FURItemRegistry.INTESTINE, 4, 12, 1));
	            list.add(new ItemsForEmeraldsTrade(FURItemRegistry.PLAGUED_PORKCHOP, 2, 1, 12, 1));
	            event.getTrades().put(2, list);
	        }
    	}
    }
    
    @SubscribeEvent
    public void onWanderingTradeSetup(WandererTradesEvent event) {
    	if(FURConfig.BonusWanderingTraderTrades.get()) {
	        List<VillagerTrades.ITrade> genericTrades = event.getGenericTrades();
	        List<VillagerTrades.ITrade> rareTrades = event.getRareTrades(); 
	        genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.SCYTHE_CLAW, 4, 1, 12, 1));
	        genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.SILKY_SLUDGE, 4, 1, 12, 1));
	        genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.FOUL_BRISTLE, 1, 3, 12, 1));
	        genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.PIGBOARHIDE, 6, 1, 12, 1));
	        genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.CACTUS_THORN, 1, 4, 12, 1));
	        if(FURConfig.pSpawnRate_Cactoid.get() > 0)
	        	genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.CACTUS_FRUIT, 8, 1, 12, 1));
	        if(FURConfig.pSpawnRate_Piranha.get() > 0)
	        	genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.PIRANHA_BUCKET, 5, 1, 12, 1));
	        if(FURConfig.pSpawnRate_Swarmer.get() > 0)
	        	genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.SWARMER_BUCKET, 5, 1, 12, 1));
	        rareTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.POISONSPORE, 24, 1, 4, 15));
	        rareTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.UNDYINGHEART, 30, 1, 4, 20));
	        rareTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.ACIDICHEART, 30, 1, 4, 20));
	        rareTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.STAINED_KINGS_CROWN, 80, 1, 2, 30));
	        if(FURConfig.pSpawnRate_Wisp.get() > 0)
	        	genericTrades.add(new ItemsForEmeraldsTrade(FURItemRegistry.WISP_IN_A_BOTTLE, 3, 1, 12, 1));
    	}
    }
    
    @SubscribeEvent
    public void onEJump(LivingJumpEvent event) {
	    int Armor_Chitin_lvl = 0;
	    
		for(ItemStack S : event.getEntityLiving().getArmorSlots()) {			
			if(S.getItem() instanceof ChitinArmorItem) {
				Armor_Chitin_lvl++;
			}
		}   
		
		if(Armor_Chitin_lvl >= 4 && event.getEntity() instanceof LivingEntity) {
			event.getEntityLiving().addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 3 * 20, 0));
		}
    }
    
    @SubscribeEvent
    public void onPCritical(CriticalHitEvent event) {
    	int CriticalBoostlvl = EnchantmentHelper.getItemEnchantmentLevel(FUREnchantmentRegistry.CRITICALBOOST, event.getPlayer().getMainHandItem());
    	if (CriticalBoostlvl != 0 && event.getDamageModifier() > 1.0F) {
    		event.setDamageModifier(event.getDamageModifier() + (CriticalBoostlvl * 0.15F));
    	}
    }
    
    @SubscribeEvent
    public void onEHeal(LivingHealEvent event) {
    	float effectlevel = 1.0F;
    	
    	if(event.getEntityLiving() instanceof PlayerEntity) {    		
    		boolean have_Heart = false;
  		
    		for(int i = 0; i < 9 ; i++)
    			if(((PlayerEntity)event.getEntityLiving()).inventory.getItem(i).getItem().equals(FURItemRegistry.SOULFIREHEART))
					have_Heart = true;
    		
    		if (have_Heart) {
    			effectlevel += 0.25F;
    		}
    			
    	}
    	
    	if (event.getEntityLiving().hasEffect(FUREffectRegistry.SOILED)) {
    		effectlevel -= 0.25F * (1 + event.getEntityLiving().getEffect(FUREffectRegistry.SOILED).getAmplifier());
    	}  
    	
    	if (event.getEntityLiving().hasEffect(FUREffectRegistry.FLOURISHED)) {
    		effectlevel += 0.25F * (1 + event.getEntityLiving().getEffect(FUREffectRegistry.FLOURISHED).getAmplifier());
    	} 
    	
    	event.setAmount(event.getAmount() * effectlevel);
    }
    
    @SubscribeEvent
    public void onInventoryOpen(final PlayerContainerEvent.Open event) {
    	if (!event.getPlayer().isCreative() && event.getContainer() instanceof ChestContainer) {
    		AxisAlignedBB axisalignedbb = AxisAlignedBB.unitCubeFromLowerCorner(event.getPlayer().position()).inflate(16.0D, 10.0D, 16.0D);
    		for (MobEntity mobs : event.getPlayer().level.getLoadedEntitiesOfClass(GraveRobberEntity.class, axisalignedbb)) {
    			mobs.setTarget(event.getPlayer());
    		}
    	}
    }
    
    @SubscribeEvent
    public void onESetTarget(LivingSetAttackTargetEvent event) {    	
    	// Neutral
        if (event.getTarget() != null && event.getEntityLiving().getLastHurtByMob() != event.getTarget()) {
        	if (event.getEntityLiving().getMobType().equals(CreatureAttribute.ILLAGER) && event.getTarget().getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(FURItemRegistry.ILLAGER_NOSE)) {
        		((MobEntity) event.getEntityLiving()).setTarget(null);
        	}
        	
        	if (event.getEntityLiving().getMobType().equals(CreatureAttribute.ARTHROPOD) && event.getTarget().hasEffect(FUREffectRegistry.CHARMING_PHEROMONE)) {
        		((MobEntity) event.getEntityLiving()).setTarget(null);
        		((MobEntity) event.getEntityLiving()).getNavigation().moveTo(event.getTarget().getX(), event.getTarget().getY(), event.getTarget().getZ(), ((MobEntity) event.getEntityLiving()).getMoveControl().getSpeedModifier());
        	}
        }
        
        // Passive
        if (event.getTarget() != null) {
        	if (event.getEntity() instanceof AbstractSkeletonEntity && event.getTarget().getItemBySlot(EquipmentSlotType.HEAD).getItem().equals(FURItemRegistry.SKELETONKING_CROWN)) {
        		((MobEntity) event.getEntityLiving()).setTarget(null);
        	}
        }
    }
}
