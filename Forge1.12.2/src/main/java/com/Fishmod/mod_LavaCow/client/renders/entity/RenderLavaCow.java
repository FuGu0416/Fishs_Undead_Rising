package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.entities.EntityLavaCow;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLavaCow extends RenderLiving<EntityLavaCow> {
    private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/moogma/moogma_glow.png");
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            new ResourceLocation("mod_lavacow:textures/mobs/moogma/moogma.png"),
            new ResourceLocation("mod_lavacow:textures/mobs/moogma/moogma1.png")
    };

    static {
        for (ResourceLocation texture : TEXTURES)
            System.out.println(texture.getResourcePath());
    }

    public RenderLavaCow(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCow(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLavaCow entity) {
        return TEXTURES[entity.getSkin()];
    }
}
