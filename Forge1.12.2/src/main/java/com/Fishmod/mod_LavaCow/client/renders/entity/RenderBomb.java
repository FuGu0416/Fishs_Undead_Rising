package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.entities.projectiles.EntityBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;

public class RenderBomb extends RenderSnowball<EntityBomb>{
	
    public RenderBomb(RenderManager rendermanagerIn, Item itemIn) {
        super(rendermanagerIn, itemIn, Minecraft.getMinecraft().getRenderItem());
    }    
}
