package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.BansheeModel;
import com.Fishmod.mod_LavaCow.entities.BansheeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BansheeRenderer extends MobRenderer<BansheeEntity, BansheeModel<BansheeEntity>> {
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/banshee.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public BansheeRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new BansheeModel<>(), 0.0F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(BansheeEntity entity) {
    	return TEXTURES[0];
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(BansheeEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
}
