package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerMimicChest;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelMimic;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderMimic extends RenderLiving<EntityMimic> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic2.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic3.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimicvoid.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic4.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic5.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimicnether.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/mimic/mimic6.png")
    };

    public static int getVoidSkin() {
        return 3;
    }

    static {
        for (ResourceLocation texture : TEXTURES)
            System.out.println(texture.getResourcePath());
    }

    public RenderMimic(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelMimic(), 0.4F);
        this.addLayer(new LayerMimicChest(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMimic entity) {
        return TEXTURES[entity.getSkin()];
    }

    @Override
    protected void preRenderCallback(EntityMimic entity, float partialTickTime) {
        if (entity.isChild()) GlStateManager.scale(0.5F, 0.5F, 0.5F);
    }
}
