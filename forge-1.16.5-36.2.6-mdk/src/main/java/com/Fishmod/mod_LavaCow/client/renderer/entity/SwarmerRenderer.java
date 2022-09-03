package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.SwarmerModel;
import com.Fishmod.mod_LavaCow.entities.aquatic.PiranhaEntity;
import com.Fishmod.mod_LavaCow.entities.aquatic.SwarmerEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class SwarmerRenderer extends MobRenderer<SwarmerEntity, SwarmerModel<SwarmerEntity>> {	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/piranha/zombiepiranha.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/piranha/piranha.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/piranha/zombiepiranha1.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public SwarmerRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new SwarmerModel<SwarmerEntity>(), 0.3F);
    }
    
    @Override
	public ResourceLocation getTextureLocation(SwarmerEntity entity) {
        if(entity instanceof PiranhaEntity)return TEXTURES[1];
        else return TEXTURES[0];
    }
    
    @Override
    protected void setupRotations(SwarmerEntity entityLiving, MatrixStack p_225621_2_, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, p_225621_2_, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        p_225621_2_.mulPose(Vector3f.YP.rotationDegrees(f));
        
        if(entityLiving instanceof PiranhaEntity)
        	p_225621_2_.scale(0.8F, 0.8F, 0.8F);
        
        if (!entityLiving.isInWaterOrBubble() && !entityLiving.isAggressive()) {
        	p_225621_2_.translate(0.1F, -0.3F, -0.1F);
        	p_225621_2_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }        
     }
}
