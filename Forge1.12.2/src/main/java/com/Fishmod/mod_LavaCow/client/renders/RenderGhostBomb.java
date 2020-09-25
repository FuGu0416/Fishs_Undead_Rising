package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.entities.projectiles.EntityGhostBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;

public class RenderGhostBomb extends RenderSnowball<EntityGhostBomb>{
	
    public RenderGhostBomb(RenderManager rendermanagerIn, Item itemIn) {
        super(rendermanagerIn, itemIn, Minecraft.getMinecraft().getRenderItem());
    }    
}
