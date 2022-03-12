package com.Fishmod.mod_LavaCow.entities.ai;

import com.Fishmod.mod_LavaCow.entities.ISemiAquatic;

import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.pathfinding.WalkAndSwimNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SemiAquaticNavigator extends SwimmerPathNavigator {
	public SemiAquaticNavigator(MobEntity entitylivingIn, World worldIn) {
		super(entitylivingIn, worldIn);
	}

	protected boolean canUpdatePath() {
		return true;
	}

	protected PathFinder createPathFinder(int p_179679_1_) {
		this.nodeEvaluator = new WalkAndSwimNodeProcessor();
        return new PathFinder(this.nodeEvaluator, p_179679_1_);
	}

	public boolean isStableDestination(BlockPos pos) {
		if (this.mob instanceof ISemiAquatic) {
			ISemiAquatic entity = (ISemiAquatic)this.mob;
			if (entity.ShouldSwin()) {
				return this.level.getBlockState(pos).is(Blocks.WATER);
			} else if (entity.ShouldLand()) {
				return !this.level.getBlockState(pos).is(Blocks.WATER);
			}
		}

        return this.level.getBlockState(pos.below()).getMaterial() != Material.AIR;
	}
}
