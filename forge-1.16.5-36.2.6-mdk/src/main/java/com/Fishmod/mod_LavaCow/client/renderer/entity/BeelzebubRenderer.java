package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.BeelzebubModel;
import com.Fishmod.mod_LavaCow.entities.flying.BeelzebubEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.util.ResourceLocation;

public class BeelzebubRenderer extends MobRenderer<BeelzebubEntity, BeelzebubModel<BeelzebubEntity>> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/beelzebub/beelzebub.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public BeelzebubRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new BeelzebubModel<BeelzebubEntity>(), 0.5F);
        this.addLayer(new SaddleLayer<>(this, this.getModel(), new ResourceLocation("mod_lavacow:textures/mobs/beelzebub/beelzebub_saddle.png")));
    }
    
    @Override
    public ResourceLocation getTextureLocation(BeelzebubEntity entity) {
    	return TEXTURES[entity.getSkin()];
    }
}
