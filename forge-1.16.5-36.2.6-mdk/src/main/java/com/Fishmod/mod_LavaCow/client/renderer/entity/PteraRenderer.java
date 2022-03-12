package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.PteraModel;
import com.Fishmod.mod_LavaCow.entities.flying.PteraEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PteraRenderer extends MobRenderer<PteraEntity, PteraModel<PteraEntity>> {
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/ptera/ptera.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/ptera/ptera1.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public PteraRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new PteraModel<PteraEntity>(), 0.5F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(PteraEntity entity) {
    	return TEXTURES[entity.getSkin()];
    }
}
