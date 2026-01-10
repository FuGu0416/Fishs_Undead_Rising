package com.Fishmod.fur.entities.ai;

import java.util.EnumSet;

import javax.annotation.Nullable;

import com.Fishmod.fur.core.SpawnUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;

public class FloatingMoveRandomGoal extends Goal {
	protected PathfinderMob mob;
	
	public FloatingMoveRandomGoal(PathfinderMob creature) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.mob = creature;
	}

	public boolean canUse() {
		return this.mob.getNavigation().isDone() && this.mob.getRandom().nextInt(7) == 0;
	}

	public boolean canContinueToUse() {
        return this.mob.getNavigation().isInProgress();
	}

	public void start() {
        BlockPos blockpos = this.mob.blockPosition();
        int groundHeight = SpawnUtil.getHeight(this.mob).getY();
        int y = this.mob.getRandom().nextInt(11) - 5;

        if (groundHeight > 0) {
        	y = Math.min(groundHeight + 4 - blockpos.getY(), y);
        }
        
        for(int i = 0; i < 3; ++i) {
        	Vec3 vector3d = this.findPos();
        	if (vector3d != null) {
        		this.mob.getMoveControl().setWantedPosition(vector3d.x + 0.5D, vector3d.y + 0.5D, vector3d.z + 0.5D, 0.25D);
        		if (this.mob.getTarget() == null) {
        			this.mob.getLookControl().setLookAt(vector3d.x + 0.5D, vector3d.y + 0.5D, vector3d.z + 0.5D, 180.0F, 20.0F);
        		}
        		break;
        	}
        }
	}

    @Nullable
    private Vec3 findPos() {
       Vec3 vector3d;
       vector3d = this.mob.getViewVector(0.0F);
       Vec3 vector3d2 = HoverRandomPos.getPos(this.mob, 8, 7, vector3d.x, vector3d.z, ((float)Math.PI / 2F), 2, 1);
       return vector3d2 != null ? vector3d2 : AirAndWaterRandomPos.getPos(this.mob, 8, 4, -2, vector3d.x, vector3d.y, vector3d.z);
    }
}