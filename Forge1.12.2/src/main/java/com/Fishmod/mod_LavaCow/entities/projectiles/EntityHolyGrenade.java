package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityHolyGrenade extends EntityBomb {
    public EntityHolyGrenade(World worldIn) {
        super(worldIn);
    }

	public EntityHolyGrenade(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn, FishItems.RANDOM_HOLY_GRENADE, 4.0F, "Holy Grenade");
	}
}
