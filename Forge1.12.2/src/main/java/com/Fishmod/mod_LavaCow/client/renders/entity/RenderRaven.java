package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerRaven;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelRaven;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityRaven;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderRaven extends RenderLiving<EntityRaven>{
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/raven/raven.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/raven/raven1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/raven/raven2.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/raven/raven3.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderRaven(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelRaven(), 0.3F);
        this.addLayer(new LayerRaven<>(this));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityRaven entity) {
        return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityRaven entity, float partialTickTime) {
    	if (entity.getSkin() == 1) {
    		GlStateManager.scale(1.2F, 1.2F, 1.2F);
    	}
	}
    
    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    public float handleRotationFloat(EntityParrot livingBase, float partialTicks)
    {
        return this.getCustomBob(livingBase, partialTicks);
    }

    private float getCustomBob(EntityParrot parrot, float p_192861_2_)
    {
        float f = parrot.oFlap + (parrot.flap - parrot.oFlap) * p_192861_2_;
        float f1 = parrot.oFlapSpeed + (parrot.flapSpeed - parrot.oFlapSpeed) * p_192861_2_;
        return (MathHelper.sin(f) + 1.0F) * f1;
    }
}
