package com.Fishmod.fur.entities.ai;

import java.util.function.Predicate;

import com.Fishmod.fur.entities.CactyrantEntity;
import com.Fishmod.fur.entities.tameable.CactoidEntity;
import com.Fishmod.fur.entities.tameable.FURTameableEntity;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

public class AvoidOrFrightEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
	public AvoidOrFrightEntityGoal(PathfinderMob mob, Class<T> entityClassToAvoid, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier) {
		this(mob, entityClassToAvoid, (livingEntity) -> {
			return true;
		}, maxDistance, walkSpeedModifier, sprintSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
	}

	public AvoidOrFrightEntityGoal(PathfinderMob mob, Class<T> entityClassToAvoid, Predicate<LivingEntity> avoidPredicate, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier, Predicate<LivingEntity> predicateOnAvoidEntity) {
		super(mob, entityClassToAvoid, avoidPredicate, maxDistance, walkSpeedModifier, sprintSpeedModifier, predicateOnAvoidEntity);
	}

	public AvoidOrFrightEntityGoal(PathfinderMob mob, Class<T> entityClassToAvoid, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier, Predicate<LivingEntity> predicateOnAvoidEntity) {
		this(mob, entityClassToAvoid, (livingEntity) -> {
			return true;
		}, maxDistance, walkSpeedModifier, sprintSpeedModifier, predicateOnAvoidEntity);
	}
	
	public boolean canUse() {
		if (this.mob instanceof FURTameableEntity && ((FURTameableEntity)this.mob).isTame()) {
			return false;
		}
		
		return super.canUse();
	}
	
	public boolean canContinueToUse() {
		return super.canContinueToUse();
	}

	public void start() {
		if (this.mob.isSilent()) {
			if (this.mob instanceof CactyrantEntity) {
				 ((CactyrantEntity)this.mob).setShaking(true);
			} else if (this.mob instanceof CactoidEntity) {
				((CactoidEntity)this.mob).setShaking(true);
			} else {
				super.start();
			}
		} else {
			super.start();
		}
	}

	public void stop() {
		if (this.mob.isSilent()) {
			if (this.mob instanceof CactyrantEntity) {
				 ((CactyrantEntity)this.mob).setShaking(false);
			} else if (this.mob instanceof CactoidEntity) {
				((CactoidEntity)this.mob).setShaking(false);
			} else {
				super.stop();
			}
		} else {
			super.stop();
		}
	}

	public void tick() {
		super.tick();
	}
}
