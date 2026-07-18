package com.Fishmod.fur.entities.ai;

import java.util.EnumSet;
import java.util.UUID;

import com.Fishmod.fur.core.SpawnUtil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

/**
 * "Fight for my master" — attacks whatever the UUID-bound owner (the Crown of Rule wearer) last
 * struck. Ported from 1.16.5.
 */
public class SkeletonOwnerHurtTargetGoal extends TargetGoal {
    private final PathfinderMob tameAnimal;
    private LivingEntity ownerLastHurt;
    private int timestamp;
    private UUID ownerID;

    public SkeletonOwnerHurtTargetGoal(PathfinderMob entityIn, UUID uniqueIDIn) {
        super(entityIn, false);
        this.tameAnimal = entityIn;
        this.ownerID = uniqueIDIn;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        if (!(this.tameAnimal.level() instanceof ServerLevel server)) {
            return false;
        }

        LivingEntity livingentity = SpawnUtil.getEntityByUniqueId(this.ownerID, server);
        if (livingentity == null) {
            return false;
        } else {
            this.ownerLastHurt = livingentity.getLastHurtMob();
            int i = livingentity.getLastHurtMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT);
        }
    }

    public void start() {
        this.mob.setTarget(this.ownerLastHurt);
        if (this.tameAnimal.level() instanceof ServerLevel server) {
            LivingEntity livingentity = SpawnUtil.getEntityByUniqueId(this.ownerID, server);
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtMobTimestamp();
            }
        }

        super.start();
    }
}
