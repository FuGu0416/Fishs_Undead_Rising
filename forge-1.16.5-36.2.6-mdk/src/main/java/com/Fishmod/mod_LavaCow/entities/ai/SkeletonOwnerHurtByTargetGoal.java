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

public class SkeletonOwnerHurtByTargetGoal extends TargetGoal {
	private final CreatureEntity tameAnimal;
	private LivingEntity ownerLastHurtBy;
	private int timestamp;
	private UUID ownerID;
	   
	public SkeletonOwnerHurtByTargetGoal(CreatureEntity EntityIn, UUID uniqueIDIn) {
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
            this.ownerLastHurtBy = livingentity.getLastHurtByMob();
            int i = livingentity.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, EntityPredicate.DEFAULT);
		}
	}

	public void start() {
		this.mob.setTarget(this.ownerLastHurtBy);
		LivingEntity livingentity = SpawnUtil.getEntityByUniqueId(this.ownerID, (ServerWorld) this.tameAnimal.level);
		if (livingentity != null) {
			this.timestamp = livingentity.getLastHurtByMobTimestamp();
		}

		super.start();
	}
}