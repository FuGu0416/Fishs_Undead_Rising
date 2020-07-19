package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.entities.projectiles.EntityHolyGrenade;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;

public class RenderHolyGrenade extends RenderSnowball<EntityHolyGrenade>{
	
    public RenderHolyGrenade(RenderManager rendermanagerIn, Item itemIn) {
        super(rendermanagerIn, itemIn, Minecraft.getMinecraft().getRenderItem());
    }    
}
