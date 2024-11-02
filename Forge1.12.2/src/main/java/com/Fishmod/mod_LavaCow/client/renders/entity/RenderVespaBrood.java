package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelParasite;
import com.Fishmod.mod_LavaCow.entities.misc.EntityVespaBrood;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderVespaBrood extends RenderLiving<EntityVespaBrood> {
    private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/parasite/parasite2.png");

    public RenderVespaBrood(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelParasite(), 0.3F);
    }

    @Override
    protected float getDeathMaxRotation(EntityVespaBrood entity) {
        return 180.0F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityVespaBrood entity) {
        return TEXTURES;
    }
}
