package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.entities.projectiles.EntityKingsWrath;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;

public class RenderKingsWrath extends RenderSnowball<EntityKingsWrath>{
	
    public RenderKingsWrath(RenderManager rendermanagerIn, Item itemIn) {
        super(rendermanagerIn, itemIn, Minecraft.getMinecraft().getRenderItem());
    }    
}
