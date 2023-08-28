package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.layer.LayerScarecrowCollar;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelScarecrow;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarecrow;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderScarecrow extends RenderLiving<EntityScarecrow>{
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow_eyes.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow2.png")
	};
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

	public RenderScarecrow(RenderManager rendermanagerIn) {
    	super(rendermanagerIn, new ModelScarecrow(), 0.5F);
    	this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    	this.addLayer(new LayerScarecrowCollar(this));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityScarecrow entity) {
        return TEXTURES[entity.getSkin()];
    }
}
