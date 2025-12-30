package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelGhoul;
import com.Fishmod.mod_LavaCow.entities.EntityGhoul;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGhoul extends RenderLiving<EntityGhoul> {
    private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul_eyes.png");
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul1.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul2.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul3.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul4.png")
    };

    public RenderGhoul(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelGhoul(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityGhoul entity) {
        return TEXTURES[entity.getSkin()];
    }
}
