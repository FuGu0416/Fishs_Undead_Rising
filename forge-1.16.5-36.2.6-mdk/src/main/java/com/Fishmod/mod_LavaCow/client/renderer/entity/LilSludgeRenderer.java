package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.LilSludgeModel;
import com.Fishmod.mod_LavaCow.entities.tameable.LilSludgeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LilSludgeRenderer extends MobRenderer<LilSludgeEntity, LilSludgeModel<LilSludgeEntity>> {
	public static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/lilsludge/lilsludge.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/lilsludge/lilsludge2.png")
	};

	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public LilSludgeRenderer(EntityRendererManager rendermanagerIn) {
    	super(rendermanagerIn, new LilSludgeModel<LilSludgeEntity>(), 0.5F);
    }
    
    @Override
	public ResourceLocation getTextureLocation(LilSludgeEntity entity) {
		return TEXTURES[entity.getSkin()];
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(LilSludgeEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
}
