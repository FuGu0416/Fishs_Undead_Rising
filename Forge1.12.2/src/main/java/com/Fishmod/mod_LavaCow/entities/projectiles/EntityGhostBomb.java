package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityGhostBomb extends EntityBomb {
    public EntityGhostBomb(World worldIn) {
        super(worldIn);
    }

	public EntityGhostBomb(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn, FishItems.ENTITY_BANSHEE_HURT, 4.0F, "Ghost Grenade");
	}
}
