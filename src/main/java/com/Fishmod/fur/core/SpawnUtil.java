package com.Fishmod.fur.core;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.config.FURConfig;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class SpawnUtil {	
	public static boolean isDay(Level level) {
		return level.getDayTime() <= 12000;
	}
	
	public static boolean isAllowedDimension(String dimensionIn) {
		for(String i : FURConfig.Spawn_AllowList.get()) {
			if(i.equalsIgnoreCase(dimensionIn))
				return true;
		}
		
		return false;
	}
	
	public static boolean isAllowedDimensionCemetery(String dimensionIn) {
		for(String i : FURConfig.Spawn_Cemetery_AllowList.get()) {
			if(i.equalsIgnoreCase(dimensionIn))
				return true;
		}
		
		return false;
	}
	
	/* Used to determine the relative height */
    public static BlockPos getHeight(Entity entityIn) {
    	BlockPos pos = entityIn.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, entityIn.blockPosition());
    	
    	if (entityIn.level().dimensionType().hasCeiling()) {
    		do {
    			pos = pos.below();
    		} while (!entityIn.level().getBlockState(pos).isAir());

    		do {
    			pos = pos.below();
            } while (entityIn.level().getBlockState(pos).isAir() && pos.getY() > 0);
    	}

    	return pos;
    }

    public static BlockPos getHeight(Level worldIn, BlockPos posIn) {
    	BlockPos pos = worldIn.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, posIn);

    	if (worldIn.dimensionType().hasCeiling()) {
    		do {
    			pos = pos.below();
    		} while (!worldIn.getBlockState(pos).isAir());

    		do {
    			pos = pos.below();
            } while (worldIn.getBlockState(pos).isAir() && pos.getY() > 0);
    	}

    	return pos;
    }

    /**
     * Local ground near a position: climbs out of solid blocks (up to 8), then descends (up to 16)
     * to the first spot resting on solid ground. Unlike {@link #getHeight}, which jumps to the
     * surface heightmap, this stays at the caller's altitude — use it for effects/spawns that must
     * land near an entity that may be underground or indoors. Returns the climbed position (or the
     * input) when no ground is found within range.
     */
    public static BlockPos getLocalGround(Level worldIn, BlockPos from) {
    	BlockPos pos = from;

    	int steps = 0;
    	while (steps++ < 8 && !worldIn.getBlockState(pos).canBeReplaced()) {
    		pos = pos.above();
    	}
    	if (!worldIn.getBlockState(pos).canBeReplaced()) {
    		return from;
    	}

    	steps = 0;
    	while (steps++ < 16 && pos.getY() > worldIn.getMinBuildHeight() + 1 && worldIn.getBlockState(pos.below()).canBeReplaced()) {
    		pos = pos.below();
    	}

    	return pos;
    }
    
    public static LivingEntity getEntityByUniqueId(UUID uniqueId, ServerLevel worldIn){
        Entity entity = worldIn.getEntity(uniqueId);

        if (entity instanceof LivingEntity le)
        	return le;

        return null;
    }
    
    public static BlockPos isNearBlock(ServerLevelAccessor level, Block BlockIn, BlockPos pos, int r) {
        int dx = pos.getX();
        int dy = pos.getY();
        int dz = pos.getZ();
        BlockPos.MutableBlockPos mp = new BlockPos.MutableBlockPos();

        for (int i = dx - r; i < dx + r; i++)
        	for (int j = dy - r; j < dy + r; j++)
        		for (int k = dz - r; k < dz + r; k++)
        			if (level.getBlockState(mp.set(i, j, k)).getBlock().equals(BlockIn))
                		return mp.immutable();

    	return null;
    }
    
	public static Entity gotRiderEntity(List<Entity> listIn, EntityType<? extends Entity> typeIn) {
		for(Entity C : listIn) {
			if (C.getType().equals(typeIn)) {
				return C;	
			}
		}
		
		return null;
	}
    
    public static Component TimeupDeathMessage(Entity entityIn) {
    	return Component.translatable("death." + mod_LavaCow.MODID + ".timeup", entityIn.getDisplayName());
    }
	
    @Nullable
    public static <T extends LivingEntity> T trySpawnEntity(EntityType<T> entityIn, ServerLevel worldIn, BlockPos blockpos) {
    	for (int i = 0; i < 10; ++i) {
    		int d0 = (i == 0) ? 0 : (worldIn.random.nextInt(4) - 2);
    		int d1 = (i == 0) ? 0 : (worldIn.random.nextInt(4) - 2);
    		BlockPos blockpos1 = findSpawnPositionInColumn(worldIn, blockpos, d0, d1);
    		if (blockpos1 != null) {
    			return entityIn.spawn(worldIn, blockpos1.below(), MobSpawnType.MOB_SUMMONED);
    		}
    	}
    	
    	return null;
	}

	@Nullable
	private static BlockPos findSpawnPositionInColumn(ServerLevel worldIn, BlockPos pos, int xOffset, int zOffset) {
		BlockPos blockpos = pos.offset(xOffset, 6, zOffset);
		BlockState blockstate = worldIn.getBlockState(blockpos);
		
		if (blockstate.liquid()) {
			return blockpos;
		}

		for (int j = 6; j >= -6; --j) {
			BlockPos blockpos1 = blockpos;
			BlockState blockstate1 = blockstate;
			blockpos = blockpos.below();
			blockstate = worldIn.getBlockState(blockpos);
			if (blockstate1.isAir() && blockstate.isSolid()) {
				return blockpos1.above();
			}
		}

		return null;
	}
	
	public static double clamp(double input, double min, double max) {
		if (input < min) {
			return min;
		} else {
			return input > max ? max : input;
		}
	}
	
	public static void addFoodEffectTooltip(ItemStack stack, List<Component> tooltip, float durationFactor) {
		FoodProperties foodStats = stack.getItem().getFoodProperties(stack, null);
		if (foodStats == null) {
			return;
		}
		List<Pair<MobEffectInstance, Float>> effectList = foodStats.getEffects();
		List<Pair<Attribute, AttributeModifier>> attributeList = Lists.newArrayList();
		
		if (!effectList.isEmpty()) {
			for (Pair<MobEffectInstance, Float> effectPair : effectList) {
				MobEffectInstance instance = effectPair.getFirst();
				MutableComponent iformattabletextcomponent = Component.translatable(instance.getDescriptionId());
				MobEffect effect = instance.getEffect();
				Map<Attribute, AttributeModifier> attributeMap = effect.getAttributeModifiers();
				if (!attributeMap.isEmpty()) {
					for (Map.Entry<Attribute, AttributeModifier> entry : attributeMap.entrySet()) {
						AttributeModifier rawModifier = entry.getValue();
						AttributeModifier modifier = new AttributeModifier(rawModifier.getName(), effect.getAttributeModifierValue(instance.getAmplifier(), rawModifier), rawModifier.getOperation());
						attributeList.add(new Pair<>(entry.getKey(), modifier));
					}
				}

				if (instance.getAmplifier() > 0) {
					iformattabletextcomponent = Component.translatable("potion.withAmplifier", iformattabletextcomponent, Component.translatable("potion.potency." + instance.getAmplifier()));
				}

				if (instance.getDuration() > 20) {
					iformattabletextcomponent = Component.translatable("potion.withDuration", iformattabletextcomponent, MobEffectUtil.formatDuration(instance, durationFactor));
				}

				tooltip.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
			}
			
			if (!attributeList.isEmpty()) {
				tooltip.add(CommonComponents.EMPTY);
				tooltip.add((Component.translatable("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

				for (Pair<Attribute, AttributeModifier> pair : attributeList) {
					AttributeModifier modifier = pair.getSecond();
					double amount = modifier.getAmount();
					AttributeModifier.Operation op = modifier.getOperation();
					double formattedAmount;
					if (op != AttributeModifier.Operation.MULTIPLY_BASE && op != AttributeModifier.Operation.MULTIPLY_TOTAL) {
						formattedAmount = amount;
					} else {
						formattedAmount = amount * 100.0D;
					}

					if (amount > 0.0D) {
						tooltip.add((Component.translatable("attribute.modifier.plus." + op.toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
					} else if (amount < 0.0D) {
						formattedAmount = formattedAmount * -1.0D;
						tooltip.add((Component.translatable("attribute.modifier.take." + op.toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.RED));
					}
				}
			}
		}
	}
	
	public static void LavaBurst(Level worldIn, double x, double y, double z, double radius, SimpleParticleType particleIn) {
		double NumberofParticles = radius * 8.0D;
		double speedRadius = radius * 0.06D;

		for (double i = 0.0D; i < NumberofParticles; i++) {
			float angle = (float)(i / NumberofParticles * 360.0f);
			double vx = speedRadius * Math.sin(angle);
            double vz = speedRadius * Math.cos(angle);

            worldIn.addParticle(particleIn, x, y, z, vx, 0.0D, vz);
		}
	}
	
	public static boolean isCursorInsideBounds(int iconX, int iconY, int iconWidth, int iconHeight, double cursorX, double cursorY) {
		return iconX <= cursorX && cursorX < iconX + iconWidth && iconY <= cursorY && cursorY < iconY + iconHeight;
	}
}
