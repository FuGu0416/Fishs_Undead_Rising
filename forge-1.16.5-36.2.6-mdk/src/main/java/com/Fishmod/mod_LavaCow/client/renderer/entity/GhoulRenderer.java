package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.GhoulModel;
import com.Fishmod.mod_LavaCow.entities.GhoulEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GhoulRenderer extends MobRenderer<GhoulEntity, GhoulModel<GhoulEntity>> {
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul_eyes.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul2.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul3.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/ghoul/ghoul4.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }
	
    public GhoulRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new GhoulModel<GhoulEntity>(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }

	@Override
	public ResourceLocation getTextureLocation(GhoulEntity entity) {
		return TEXTURES[entity.getSkin()];
	}
}
