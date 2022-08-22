package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.AvatonModel;
import com.Fishmod.mod_LavaCow.entities.floating.SeaHagEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SeaHagRenderer extends MobRenderer<SeaHagEntity, AvatonModel<SeaHagEntity>> {
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/avaton.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/sea_hag.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public SeaHagRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new AvatonModel<>(), 0.0F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(SeaHagEntity entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(SeaHagEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
    
    protected int getBlockLightLevel(SeaHagEntity p_225624_1_, BlockPos p_225624_2_) {
        return 8;
    }
}
