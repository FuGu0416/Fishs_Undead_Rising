package com.Fishmod.mod_LavaCow.proxy;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements IProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }
 
    public void init(FMLInitializationEvent event) {
 
    }
 
    public void postInit(FMLPostInitializationEvent event) {
 
    }

	@Override
	public void spawnCustomParticle(String particleName, World world, double x, double y, double z, double vecX,
			double vecY, double vecZ, float r, float g, float b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preRender() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerItemAndBlockRenderers() {
		// TODO Auto-generated method stub
		
	}
	
}
