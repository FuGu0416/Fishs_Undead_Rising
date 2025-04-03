package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityFishCustomArrow;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFishCustomArrow extends RenderArrow<EntityFishCustomArrow> {
    public static final ResourceLocation TEXTURE_FANG = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/projectile/fang_arrow.png");
    public static final ResourceLocation TEXTURE_GHOULISH = new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/projectile/ghoul_arrow.png");

    public RenderFishCustomArrow(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFishCustomArrow entity) {
        switch (entity.getArrowType()) {
            case 1:
                return TEXTURE_FANG;
            case 0:
            default:
                return TEXTURE_GHOULISH;
        }
    }
}
