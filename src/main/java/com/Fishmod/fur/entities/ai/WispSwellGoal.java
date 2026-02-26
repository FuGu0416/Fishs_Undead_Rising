package com.Fishmod.fur.entities.ai;

import java.util.EnumSet;

import com.Fishmod.fur.config.FURConfig;
import com.Fishmod.fur.entities.tameable.WispEntity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

/**

Updated for Minecraft Forge 1.20.1 (Mojang mappings).

Controls the swelling/explosion trigger behavior of WispEntity.
*/
public class WispSwellGoal extends Goal {
	private final WispEntity wisp;
	private LivingEntity target;

	public WispSwellGoal(WispEntity wisp) {
		this.wisp = wisp;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		LivingEntity livingTarget = this.wisp.getTarget();
		boolean tamedExplosionAllowed = !this.wisp.isTame() || (this.wisp.isTame() && FURConfig.Wisp_Tamed_Explosion.get());
		return (this.wisp.getSwellDir() > 0 || (livingTarget != null && this.wisp.distanceToSqr(livingTarget) < 4.0D))
				&& tamedExplosionAllowed;
	}

	@Override
	public void start() {
		this.wisp.getNavigation().stop();
		this.target = this.wisp.getTarget();
	}

	@Override
	public void stop() {
		this.target = null;
	}

	@Override
	public void tick() {
		if (this.target == null) {
			this.wisp.setSwellDir(-1);
		} else if (this.wisp.distanceToSqr(this.target) > 49.0D) {
			this.wisp.setSwellDir(-1);
		} else if (!this.wisp.getSensing().hasLineOfSight(this.target)) {
			this.wisp.setSwellDir(-1);
		} else {
			this.wisp.setSwellDir(1);
		}
	}
}