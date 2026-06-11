package com.Fishmod.fur.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.core.SpawnUtil;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;
import com.Fishmod.fur.entities.tameable.unburied.UnburiedEntity;
import com.Fishmod.fur.init.FUREffectRegistry;
import com.Fishmod.fur.init.FUREnchantmentRegistry;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
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
import net.minecraft.world.phys.Vec3;
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
		if (!level.isClientSide() && stack.getItem() == FURItemRegistry.REAPERS_SCYTHE.get() && entityIn instanceof LivingEntity living) {
			long now = level.getGameTime();
			CompoundTag tag = stack.getOrCreateTag();
			long cdEnd = tag.getLong("soul_siphon_cd");
			if (now >= cdEnd && living.getHealth() <= living.getMaxHealth() * 0.3F) {
				living.addEffect(new MobEffectInstance(FUREffectRegistry.SOUL_SIPHON.get(), 200, 2));
				tag.putLong("soul_siphon_cd", now + 2400L);
			}
		}
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
		} else if (stack.getItem() == FURItemRegistry.SKELETONKING_MACE.get()) {
        	target.addEffect(new MobEffectInstance(FUREffectRegistry.FRAGILE.get(), 200, 4));
		} else if (stack.getItem() == FURItemRegistry.VESPA_DAGGER.get()) {
			target.addEffect(new MobEffectInstance(MobEffects.POISON, 8 * 20, 1));
		}
				
        return super.hurtEnemy(stack, target, attacker);
    }
	
	public static <T extends FURTameableEntity> FURTameableEntity SummonMinion(Player player, ItemStack stack, Level level, BlockPos blockpos, EntityType<T> entityIn, int limitLife, int skin) {
		if (level instanceof ServerLevel) {
			FURTameableEntity entity = (FURTameableEntity)SpawnUtil.trySpawnEntity(entityIn, ((ServerLevel) level), blockpos);

			if (entity != null) {
				CompoundTag data = entity.getPersistentData();

				data.putInt("fire_aspect",        stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT));
				data.putInt("sharpness",          stack.getEnchantmentLevel(Enchantments.SHARPNESS));
				data.putInt("knockback",          stack.getEnchantmentLevel(Enchantments.KNOCKBACK));
				data.putInt("bane_of_arthropods", stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS));
				data.putInt("smite",              stack.getEnchantmentLevel(Enchantments.SMITE));
				data.putInt("unbreaking",         stack.getEnchantmentLevel(Enchantments.UNBREAKING));
				data.putInt("corrosive",          0);

				int dominion = stack.getEnchantmentLevel(FUREnchantmentRegistry.DOMINION.get());

				entity.tame(player);
				entity.setLimitedLife(limitLife);
				entity.setSkin(skin);
				if (dominion > 0 && entity.getAttribute(Attributes.MAX_HEALTH) != null) {
					entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getMaxHealth() * ((10.0D - (double)dominion) / 10.0D));
				}
				entity.setHealth(entity.getMaxHealth());

				if (entity instanceof UnburiedEntity) {
					((UnburiedEntity)entity).setSpellcasting();
				}
			}

			return entity;
		}

		return null;
	}
	
    /**
     * Called when the equipped item is right clicked.
     */
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);	
               
        if (stack.getItem() == FURItemRegistry.BEAST_CLAW.get() && player.onGround()) {
        	Vec3 lookVec = player.getLookAngle();

        	if (player.getOffhandItem().getItem() == FURItemRegistry.BEAST_CLAW.get() && player.getMainHandItem().getItem() == FURItemRegistry.BEAST_CLAW.get()) {
        		player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3 * 20, 0));
        		player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3 * 20, 0));
        	}

        	player.setDeltaMovement(player.getDeltaMovement().add(lookVec.x * 1.5D, lookVec.y * 0.15D + 0.4D, lookVec.z * 1.5D));
            stack.hurtAndBreak(8, player, (p_220045_0_) -> {
    			p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    		});
			player.getCooldowns().addCooldown(this, 120);

			return InteractionResultHolder.pass(stack);
		}

        if (stack.getItem() == FURItemRegistry.UNDERTAKER_SHOVEL.get() && level instanceof ServerLevel) {
            int dominion = stack.getEnchantmentLevel(FUREnchantmentRegistry.DOMINION.get());
            for (int i = 0; i < 4 + dominion; ++i) {
                BlockPos blockpos = player.blockPosition().offset(-6 + player.getRandom().nextInt(12), 0, -6 + player.getRandom().nextInt(12));
                FURWeaponItem.SummonMinion(player, stack, level, blockpos, FUREntityRegistry.UNBURIED.get(), FURConfig.Unburied_Lifespan.get() * 20, 0);
            }

            stack.hurtAndBreak(63, player, (p_220045_0_) -> {
                p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            player.getCooldowns().addCooldown(FURItemRegistry.UNDERTAKER_SHOVEL.get(), FURConfig.Undertaker_Shovel_Cooldown.get() * 20);

            return InteractionResultHolder.pass(stack);
        }

        if (stack.getItem() == FURItemRegistry.SCARAB_SCEPTER.get() && level instanceof ServerLevel) {
            int dominion = stack.getEnchantmentLevel(FUREnchantmentRegistry.DOMINION.get());
            Vec3 lookVec = player.getLookAngle();
            for (int i = 0; i < 4 + dominion; ++i) {
                BlockPos blockpos = player.blockPosition().offset((int)(lookVec.x * 3.0D + (player.getRandom().nextDouble() * 4.0D - 2.0D)), 0, (int)(lookVec.z * 3.0D + (player.getRandom().nextDouble() * 4.0D - 2.0D)));
                FURWeaponItem.SummonMinion(player, stack, level, blockpos, FUREntityRegistry.SCARAB.get(), FURConfig.Scarab_Lifespan.get() * 20, 0);
            }

            stack.hurtAndBreak(8, player, (p_220045_0_) -> {
                p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            player.getCooldowns().addCooldown(FURItemRegistry.SCARAB_SCEPTER.get(), FURConfig.ScarabScepter_Cooldown.get() * 20);

            return InteractionResultHolder.pass(stack);
        }

        if (stack.getItem() == FURItemRegistry.ANKH_SCEPTER.get() && level instanceof ServerLevel) {
            int dominion = stack.getEnchantmentLevel(FUREnchantmentRegistry.DOMINION.get());
            for (int i = 0; i < 4 + dominion; ++i) {
                BlockPos blockpos = player.blockPosition().offset(-6 + player.getRandom().nextInt(12), 0, -6 + player.getRandom().nextInt(12));
                FURWeaponItem.SummonMinion(player, stack, level, blockpos, FUREntityRegistry.MUMMY.get(), FURConfig.Mummy_Lifespan.get() * 20, 0);
            }

            stack.hurtAndBreak(63, player, (p_220045_0_) -> {
                p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            player.getCooldowns().addCooldown(FURItemRegistry.ANKH_SCEPTER.get(), FURConfig.Ankh_Scepter_Cooldown.get() * 20);

            return InteractionResultHolder.pass(stack);
        }

        if (stack.getItem() == FURItemRegistry.FUNGAL_STAFF.get() && level instanceof ServerLevel) {
            int dominion = stack.getEnchantmentLevel(FUREnchantmentRegistry.DOMINION.get());
            for (int i = 0; i < 4 + dominion; ++i) {
                BlockPos blockpos = player.blockPosition().offset(-6 + player.getRandom().nextInt(12), 0, -6 + player.getRandom().nextInt(12));
                FURWeaponItem.SummonMinion(player, stack, level, blockpos, FUREntityRegistry.MYCOSIS.get(), FURConfig.Mycosis_Lifespan.get() * 20, 0);
            }

            stack.hurtAndBreak(63, player, (p_220045_0_) -> {
                p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            player.getCooldowns().addCooldown(FURItemRegistry.FUNGAL_STAFF.get(), FURConfig.Fungal_Staff_Cooldown.get() * 20);

            return InteractionResultHolder.pass(stack);
        }

        if (stack.getItem() == FURItemRegistry.FROZEN_GRIP.get() && level instanceof ServerLevel) {
            int dominion = stack.getEnchantmentLevel(FUREnchantmentRegistry.DOMINION.get());
            for (int i = 0; i < 4 + dominion; ++i) {
                BlockPos blockpos = player.blockPosition().offset(-6 + player.getRandom().nextInt(12), 0, -6 + player.getRandom().nextInt(12));
                FURWeaponItem.SummonMinion(player, stack, level, blockpos, FUREntityRegistry.FRIGID.get(), FURConfig.Frigid_Lifespan.get() * 20, 0);
            }

            stack.hurtAndBreak(63, player, (p_220045_0_) -> {
                p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            player.getCooldowns().addCooldown(FURItemRegistry.FROZEN_GRIP.get(), FURConfig.Frozen_Grip_Cooldown.get() * 20);

            return InteractionResultHolder.pass(stack);
        }

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
			tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc", FURConfig.BoneSword_Damage.get(), FURConfig.BoneSword_DamageCap.get()).withStyle(ChatFormatting.YELLOW));
		} else if (stack.getItem().equals(FURItemRegistry.BEAST_CLAW.get())) {
			tooltip.add(Component.translatable(this.getDescriptionId() + ".desc0").withStyle(ChatFormatting.YELLOW));
			tooltip.add(Component.translatable(this.getDescriptionId() + ".desc1").withStyle(ChatFormatting.YELLOW));
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
