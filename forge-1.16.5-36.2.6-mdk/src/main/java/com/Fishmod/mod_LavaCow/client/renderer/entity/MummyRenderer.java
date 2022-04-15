package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.layer.LayerUnburiedArmor;
import com.Fishmod.mod_LavaCow.client.model.entity.UnburiedModel;
import com.Fishmod.mod_LavaCow.entities.MummyEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MummyRenderer extends MobRenderer<MummyEntity, UnburiedModel<MummyEntity>> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied4.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }
	
    public MummyRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new UnburiedModel<MummyEntity>(), 0.5F);
        this.addLayer(new LayerGenericHeldItem<>(this, 0.0F, 0.15F, -0.6F, 1.0F));
        this.addLayer(new LayerUnburiedArmor<>(this));
    }
    
	@Override
	public ResourceLocation getTextureLocation(MummyEntity entity) {
		return TEXTURES[entity.getSkin()];
	}
   
    @Override
	protected void scale(MummyEntity entity, MatrixStack matrixStackIn, float partialTickTime) {
    	if(entity.isBaby()) {
    		matrixStackIn.scale(0.6F, 0.6F, 0.6F);
    	}
	}


}
