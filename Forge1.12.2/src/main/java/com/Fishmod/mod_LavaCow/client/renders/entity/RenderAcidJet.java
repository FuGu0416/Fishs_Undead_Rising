package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.entities.projectiles.EntityAcidJet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;

public class RenderAcidJet extends RenderSnowball<EntityAcidJet>{
	
    public RenderAcidJet(RenderManager rendermanagerIn, Item itemIn) {
        super(rendermanagerIn, itemIn, Minecraft.getMinecraft().getRenderItem());

    }    
}
