package com.Fishmod.mod_LavaCow.entities.ai;

import java.util.EnumSet;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.entities.tameable.WispEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class WispSwellGoal extends Goal {
	   private final WispEntity wisp;
	   private LivingEntity target;

	   public WispSwellGoal(WispEntity p_i1655_1_) {
	      this.wisp = p_i1655_1_;
	      this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	   }

	   public boolean canUse() {
	      LivingEntity livingentity = this.wisp.getTarget();
	      return (this.wisp.getSwellDir() > 0 || livingentity != null && this.wisp.distanceToSqr(livingentity) < 4.0D) && (!this.wisp.isTame() || (this.wisp.isTame() && FURConfig.Wisp_Tamed_Explosion.get()));
	   }

	   public void start() {
	      this.wisp.getNavigation().stop();
	      this.target = this.wisp.getTarget();
	   }

	   public void stop() {
	      this.target = null;
	   }

	   public void tick() {
	      if (this.target == null) {
	         this.wisp.setSwellDir(-1);
	      } else if (this.wisp.distanceToSqr(this.target) > 49.0D) {
	         this.wisp.setSwellDir(-1);
	      } else if (!this.wisp.getSensing().canSee(this.target)) {
	         this.wisp.setSwellDir(-1);
	      } else {
	         this.wisp.setSwellDir(1);
	      }
	   }
	}
