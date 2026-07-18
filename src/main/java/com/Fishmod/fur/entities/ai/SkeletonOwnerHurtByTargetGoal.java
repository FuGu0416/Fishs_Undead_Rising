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
 * "Defend my master" — retaliates against whatever last hurt the UUID-bound owner (the Crown of
 * Rule wearer). Ported from 1.16.5.
 */
public class SkeletonOwnerHurtByTargetGoal extends TargetGoal {
    private final PathfinderMob tameAnimal;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;
    private UUID ownerID;

    public SkeletonOwnerHurtByTargetGoal(PathfinderMob entityIn, UUID uniqueIDIn) {
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
            this.ownerLastHurtBy = livingentity.getLastHurtByMob();
            int i = livingentity.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
        }
    }

    public void start() {
        this.mob.setTarget(this.ownerLastHurtBy);
        if (this.tameAnimal.level() instanceof ServerLevel server) {
            LivingEntity livingentity = SpawnUtil.getEntityByUniqueId(this.ownerID, server);
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtByMobTimestamp();
            }
        }

        super.start();
    }
}
