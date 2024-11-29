package com.Fishmod.mod_LavaCow.entities.ai;

import java.util.EnumSet;
import java.util.UUID;

import com.Fishmod.mod_LavaCow.core.SpawnUtil;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.world.server.ServerWorld;

public class SkeletonOwnerHurtTargetGoal extends TargetGoal {
	private final CreatureEntity tameAnimal;
	private LivingEntity ownerLastHurt;
	private int timestamp;
	private UUID ownerID;
	
	public SkeletonOwnerHurtTargetGoal(CreatureEntity EntityIn, UUID uniqueIDIn) {
		super(EntityIn, false);
		this.tameAnimal = EntityIn;
		this.ownerID = uniqueIDIn;
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
	}

	public boolean canUse() {
		LivingEntity livingentity = SpawnUtil.getEntityByUniqueId(this.ownerID, (ServerWorld) this.tameAnimal.level);
		if (livingentity == null) {
			return false;
		} else {
            this.ownerLastHurt = livingentity.getLastHurtMob();
            int i = livingentity.getLastHurtMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurt, EntityPredicate.DEFAULT);
		}
	}

	public void start() {
		this.mob.setTarget(this.ownerLastHurt);
		LivingEntity livingentity = SpawnUtil.getEntityByUniqueId(this.ownerID, (ServerWorld) this.tameAnimal.level);
		if (livingentity != null) {
			this.timestamp = livingentity.getLastHurtMobTimestamp();
		}
	
		super.start();
	}
}