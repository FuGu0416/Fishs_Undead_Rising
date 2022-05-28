package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.WarpedFireflyModel;
import com.Fishmod.mod_LavaCow.entities.flying.WarpedFireflyEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WarpedFireflyRenderer extends MobRenderer<WarpedFireflyEntity, WarpedFireflyModel<WarpedFireflyEntity>> {		
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/warped_firefly/warped_firefly_glow.png");
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/warped_firefly/warped_firefly.png");	
	
	static{
        System.out.println(TEXTURES.getPath());
    }

    public WarpedFireflyRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new WarpedFireflyModel<>(), 0.4F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }
    
    @Override
	public ResourceLocation getTextureLocation(WarpedFireflyEntity entity) {
    	return TEXTURES;
    }
}
