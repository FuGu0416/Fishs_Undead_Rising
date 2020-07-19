package com.Fishmod.mod_LavaCow.client.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.item.Item;

public class RenderAcidJet extends RenderSnowball<EntityFireball>{
	
    public RenderAcidJet(RenderManager rendermanagerIn, Item itemIn) {
        super(rendermanagerIn, itemIn, Minecraft.getMinecraft().getRenderItem());

    }    
}
