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
	public AvoidOrFrightEntityGoal(PathfinderMob p_25027_, Class<T> p_25028_, float p_25029_, double p_25030_, double p_25031_) {
		this(p_25027_, p_25028_, (p_25052_) -> {
			return true;
		}, p_25029_, p_25030_, p_25031_, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
	}

	public AvoidOrFrightEntityGoal(PathfinderMob p_25040_, Class<T> p_25041_, Predicate<LivingEntity> p_25042_, float p_25043_, double p_25044_, double p_25045_, Predicate<LivingEntity> p_25046_) {
		super(p_25040_, p_25041_, p_25042_, p_25043_, p_25044_, p_25045_, p_25046_);
	}

	public AvoidOrFrightEntityGoal(PathfinderMob p_25033_, Class<T> p_25034_, float p_25035_, double p_25036_, double p_25037_, Predicate<LivingEntity> p_25038_) {
		this(p_25033_, p_25034_, (p_25049_) -> {
			return true;
		}, p_25035_, p_25036_, p_25037_, p_25038_);
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
