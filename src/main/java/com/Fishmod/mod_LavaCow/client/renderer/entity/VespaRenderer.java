package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.VespaModel;
import com.Fishmod.mod_LavaCow.entities.flying.VespaEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.util.ResourceLocation;

public class VespaRenderer extends MobRenderer<VespaEntity, VespaModel<VespaEntity>> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/vespa/vespa.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/vespa/vespa1.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public VespaRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new VespaModel<VespaEntity>(), 0.5F);
        this.addLayer(new SaddleLayer<>(this, this.getModel(), new ResourceLocation("mod_lavacow:textures/mobs/vespa/vespa_saddle.png")));
    }
    
    @Override
    public ResourceLocation getTextureLocation(VespaEntity entity) {
    	return TEXTURES[entity.getSkin()];
    }
}
