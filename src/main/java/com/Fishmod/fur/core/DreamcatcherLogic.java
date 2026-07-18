package com.Fishmod.fur.core;

import java.util.ArrayList;
import java.util.List;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

/**
 * Shared, BlockEntity-free logic for the Dreamcatcher: resolving the summon pool from the
 * {@code fur:dreamcatcher_pool} entity-type tag, reading base health without spawning, and running the
 * budgeted spawn wave. Kept out of the block so both the right-click affordability pre-check and the
 * scheduled-tick summon can share it.
 */
public final class DreamcatcherLogic {

    public static final TagKey<EntityType<?>> POOL =
            TagKey.create(net.minecraft.core.registries.Registries.ENTITY_TYPE, new ResourceLocation(mod_LavaCow.MODID, "dreamcatcher_pool"));

    private DreamcatcherLogic() {}

    /** Total HP budget a wave of the given charge may spend. */
    public static double budgetFor(int charge) {
        return (double) charge * FURConfig.Dreamcatcher_HpBudgetPerStage.get();
    }

    /**
     * Reads an entity type's base MAX_HEALTH from its default attributes without instantiating it.
     * Returns {@link Double#MAX_VALUE} for anything that is not a living entity or has no health attribute,
     * so such entries can never be affordable.
     */
    public static double baseHealth(EntityType<?> type) {
        if (!DefaultAttributes.hasSupplier(type)) {
            return Double.MAX_VALUE;
        }
        @SuppressWarnings("unchecked")
        AttributeSupplier supplier = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) type);
        if (!supplier.hasAttribute(Attributes.MAX_HEALTH)) {
            return Double.MAX_VALUE;
        }
        return supplier.getValue(Attributes.MAX_HEALTH);
    }

    private static boolean isBlacklisted(EntityType<?> type) {
        ResourceLocation id = ForgeRegistries.ENTITY_TYPES.getKey(type);
        if (id == null) {
            return true;
        }
        String key = id.toString();
        for (String s : FURConfig.Dreamcatcher_Blacklist.get()) {
            if (key.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Builds the list of pool entries affordable within {@code budget}: in the pool tag, not a boss, not
     * blacklisted, and with base health at or below the budget.
     */
    public static List<EntityType<?>> affordableCandidates(double budget) {
        List<EntityType<?>> out = new ArrayList<>();
        ITag<EntityType<?>> pool = ForgeRegistries.ENTITY_TYPES.tags().getTag(POOL);
        ITag<EntityType<?>> bosses = ForgeRegistries.ENTITY_TYPES.tags().getTag(Tags.EntityTypes.BOSSES);

        for (EntityType<?> type : pool) {
            if (bosses.contains(type) || isBlacklisted(type)) {
                continue;
            }
            double hp = baseHealth(type);
            if (hp <= budget) {
                out.add(type);
            }
        }
        return out;
    }

    /** True when at least one pool entry is affordable at the given charge. */
    public static boolean hasAffordableCandidate(int charge) {
        return !affordableCandidates(budgetFor(charge)).isEmpty();
    }

    /**
     * Runs the budgeted spawn wave around {@code blockPos}. Repeatedly picks a random affordable candidate
     * (base HP ≤ remaining budget) and tries to place it on a ring around the block, until the budget or
     * the max-count is exhausted. Spawned mobs are plain pool mobs (no buff), targeted at the summoner and
     * despawnable. Returns the number of mobs actually spawned.
     */
    public static int summonWave(ServerLevel level, BlockPos blockPos, int charge, Player target) {
        double budget = budgetFor(charge);
        List<EntityType<?>> candidates = affordableCandidates(budget);
        if (candidates.isEmpty()) {
            return 0;
        }

        RandomSource random = level.random;
        int maxCount = FURConfig.Dreamcatcher_MaxMobsPerWave.get();
        int ringMin = FURConfig.Dreamcatcher_SpawnRingMin.get();
        int ringMax = Math.max(ringMin, FURConfig.Dreamcatcher_SpawnRingMax.get());

        double remaining = budget;
        int spawned = 0;
        // Hard cap on total placement attempts so a fully walled-in dreamcatcher can never spin forever.
        int attemptsLeft = maxCount * 6 + 12;

        while (spawned < maxCount && attemptsLeft > 0) {
            // Narrow to what the remaining budget can still afford this iteration.
            List<EntityType<?>> affordable = new ArrayList<>();
            for (EntityType<?> t : candidates) {
                if (baseHealth(t) <= remaining) {
                    affordable.add(t);
                }
            }
            if (affordable.isEmpty()) {
                break;
            }

            EntityType<?> chosen = affordable.get(random.nextInt(affordable.size()));
            LivingEntity mob = tryPlace(level, blockPos, chosen, ringMin, ringMax);
            attemptsLeft--;

            if (mob != null) {
                if (target != null && mob instanceof Mob asMob) {
                    asMob.setTarget(target);
                }
                level.sendParticles(ParticleTypes.SOUL, mob.getX(), mob.getY() + mob.getBbHeight() * 0.5D, mob.getZ(),
                        12, 0.3D, 0.4D, 0.3D, 0.02D);
                level.playSound(null, mob.blockPosition(), SoundEvents.SOUL_ESCAPE, SoundSource.HOSTILE,
                        0.7F, 0.6F + random.nextFloat() * 0.2F);

                remaining -= baseHealth(chosen);
                spawned++;
            }
        }

        return spawned;
    }

    /**
     * Tries a handful of ring positions and returns the spawned mob, or null if none of the sampled
     * positions had valid ground / clear space.
     */
    private static LivingEntity tryPlace(ServerLevel level, BlockPos blockPos, EntityType<?> type, int ringMin, int ringMax) {
        if (!DefaultAttributes.hasSupplier(type)) {
            return null;
        }
        @SuppressWarnings("unchecked")
        EntityType<? extends LivingEntity> livingType = (EntityType<? extends LivingEntity>) type;

        RandomSource random = level.random;
        for (int i = 0; i < 4; i++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double dist = ringMin + random.nextDouble() * (ringMax - ringMin);
            int x = blockPos.getX() + (int) Math.round(Math.cos(angle) * dist);
            int z = blockPos.getZ() + (int) Math.round(Math.sin(angle) * dist);
            // Stay at the dreamcatcher's own altitude — trySpawnEntity already scans +-6 blocks in
            // the column. The old SpawnUtil.getHeight jumped to the surface heightmap first, which
            // sent every wave to the surface when the dreamcatcher hung in an underground base.
            BlockPos ground = new BlockPos(x, blockPos.getY(), z);

            LivingEntity spawned = SpawnUtil.trySpawnEntity(livingType, level, ground);
            if (spawned != null) {
                return spawned;
            }
        }
        return null;
    }
}
