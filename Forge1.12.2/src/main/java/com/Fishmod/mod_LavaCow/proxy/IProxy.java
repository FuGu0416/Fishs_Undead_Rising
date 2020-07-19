package com.Fishmod.mod_LavaCow.proxy;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {
	
    public void preInit(FMLPreInitializationEvent event);
    
    public void init(FMLInitializationEvent event);
 
    public void postInit(FMLPostInitializationEvent event);

	public void spawnCustomParticle(String particleName, World world, double x, double y, double z, double vecX, double vecY,
			double vecZ, float r, float g, float b);

	public void preRender();

	void registerItemAndBlockRenderers();
}
