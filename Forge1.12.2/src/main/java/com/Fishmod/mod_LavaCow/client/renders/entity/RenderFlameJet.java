package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.entities.projectiles.EntityFlameJet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;

public class RenderFlameJet extends RenderSnowball<EntityFlameJet>{
	
    public RenderFlameJet(RenderManager rendermanagerIn, Item itemIn) {
        super(rendermanagerIn, itemIn, Minecraft.getMinecraft().getRenderItem());
    }    
}
