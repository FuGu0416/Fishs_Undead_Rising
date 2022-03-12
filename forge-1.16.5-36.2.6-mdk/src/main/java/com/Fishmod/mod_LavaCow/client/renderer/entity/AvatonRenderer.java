package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.AvatonModel;
import com.Fishmod.mod_LavaCow.entities.AvatonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AvatonRenderer extends MobRenderer<AvatonEntity, AvatonModel<AvatonEntity>> {
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/avaton.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public AvatonRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new AvatonModel<>(), 0.0F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(AvatonEntity entity) {
    	return TEXTURES[0];
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(AvatonEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
}
