package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntitySonicBomb extends EntityBomb {
    public EntitySonicBomb(World worldIn) {
        super(worldIn);
    }

	public EntitySonicBomb(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn, FishItems.ENTITY_BANSHEE_ATTACK, 4.0F, "Sonic Grenade");
	}
}
