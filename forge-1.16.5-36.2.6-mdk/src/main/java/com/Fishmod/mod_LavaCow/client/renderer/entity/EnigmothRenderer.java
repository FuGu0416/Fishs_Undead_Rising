package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.BeelzebubModel;
import com.Fishmod.mod_LavaCow.entities.flying.BeelzebubEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.util.ResourceLocation;

public class EnigmothRenderer extends MobRenderer<BeelzebubEntity, BeelzebubModel<BeelzebubEntity>> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public EnigmothRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new BeelzebubModel<BeelzebubEntity>(), 0.5F);
        this.addLayer(new SaddleLayer<>(this, this.getModel(), new ResourceLocation("mod_lavacow:textures/mobs/enigmoth/enigmoth_saddle.png")));
    }
    
    @Override
    public ResourceLocation getTextureLocation(BeelzebubEntity entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    @Override
    public void render(BeelzebubEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
    	super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    	this.model.isHarvestable = p_225623_1_.canHarvest();
    }   
}
