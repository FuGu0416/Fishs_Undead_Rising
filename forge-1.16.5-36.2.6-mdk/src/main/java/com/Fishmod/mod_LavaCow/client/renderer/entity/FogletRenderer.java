package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.FogletModel;
import com.Fishmod.mod_LavaCow.entities.FogletEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FogletRenderer extends MobRenderer<FogletEntity, FogletModel<FogletEntity>> {
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/foglet/foglet_eyes.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/foglet/foglet.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/foglet/foglet2.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/foglet/foglet3.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }
	
    public FogletRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new FogletModel<FogletEntity>(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }

	@Override
	public ResourceLocation getTextureLocation(FogletEntity entity) {
		return TEXTURES[entity.getSkin()];
	}
}
