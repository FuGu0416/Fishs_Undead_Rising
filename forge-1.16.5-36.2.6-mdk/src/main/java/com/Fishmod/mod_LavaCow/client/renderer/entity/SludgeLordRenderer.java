package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.SludgeLordModel;
import com.Fishmod.mod_LavaCow.entities.SludgeLordEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SludgeLordRenderer extends MobRenderer<SludgeLordEntity, SludgeLordModel<SludgeLordEntity>> {
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/sludgelord/sludgelord_glow.png");
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/sludgelord/sludgelord.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

    public SludgeLordRenderer(EntityRendererManager rendermanagerIn) {
    	super(rendermanagerIn, new SludgeLordModel<>(), 1.0F);
    	this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }
    
    @Override
	public ResourceLocation getTextureLocation(SludgeLordEntity entity) {
        return TEXTURES;
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(SludgeLordEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
}
