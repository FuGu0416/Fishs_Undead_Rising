package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.layer.ScarecrowCollarLayer;
import com.Fishmod.mod_LavaCow.client.model.entity.ScarecrowModel;
import com.Fishmod.mod_LavaCow.entities.tameable.ScarecrowEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScarecrowRenderer extends MobRenderer<ScarecrowEntity, ScarecrowModel<ScarecrowEntity>> {
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow_eyes.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/scarecrow/scarecrow2.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

	public ScarecrowRenderer(EntityRendererManager rendermanagerIn) {
    	super(rendermanagerIn, new ScarecrowModel<>(), 0.5F);
    	this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    	this.addLayer(new ScarecrowCollarLayer(this));   	
    	this.addLayer(new LayerGenericHeldItem<>(this, 0.0F, 0.15F, -0.4F, 1.33F));
    }
    
    @Override
	public ResourceLocation getTextureLocation(ScarecrowEntity entity) {
        return TEXTURES[entity.getSkin()];
    }
}
