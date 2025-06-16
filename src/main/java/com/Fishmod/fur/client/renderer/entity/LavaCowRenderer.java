package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.client.layer.LayerGenericGlowing;
import com.Fishmod.fur.entities.LavaCowEntity;

import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LavaCowRenderer extends MobRenderer<LavaCowEntity, CowModel<LavaCowEntity>>  {
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("fur:textures/mobs/moogma/moogma_glowmask.png");
	private static final ResourceLocation TEXTURES = new ResourceLocation("fur:textures/mobs/moogma/moogma.png");
	
	static{
        System.out.println(TEXTURES.getPath());
    }

    public LavaCowRenderer(EntityRendererProvider.Context rendermanagerIn) {
        super(rendermanagerIn, new CowModel<LavaCowEntity>(rendermanagerIn.bakeLayer(ModelLayers.COW)), 0.7F);
    	this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }
    
    @Override
	public ResourceLocation getTextureLocation(LavaCowEntity entity) {
        return TEXTURES;
    }
}
