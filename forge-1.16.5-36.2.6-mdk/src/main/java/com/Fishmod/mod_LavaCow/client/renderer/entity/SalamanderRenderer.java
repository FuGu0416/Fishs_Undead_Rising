package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerSalamander;
import com.Fishmod.mod_LavaCow.client.model.entity.SalamanderModel;
import com.Fishmod.mod_LavaCow.entities.tameable.SalamanderEntity;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SalamanderRenderer extends MobRenderer<SalamanderEntity, SalamanderModel<SalamanderEntity>> {		
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamander.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamander1.png")
	};
	
	private static final ResourceLocation[] TEXTURES_CHILD = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamanderlesser.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamanderlesser1.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public SalamanderRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new SalamanderModel<>(), 1.0F);
        this.addLayer(new SaddleLayer<>(this, this.getModel(), new ResourceLocation("mod_lavacow:textures/mobs/salamander/salamander_saddle.png")));
        this.addLayer(new LayerSalamander<>(this));
    }
    
    @Override
	public ResourceLocation getTextureLocation(SalamanderEntity entity) {
    	return entity.isNymph() ? TEXTURES_CHILD[entity.getSkin()] : TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void scale(SalamanderEntity entity, MatrixStack matrixStackIn, float partialTickTime) { 	
    	
    	switch (entity.getGrowingStage()) {
			case 0:
				matrixStackIn.scale(1.0F, 1.0F, 1.0F);
				break;
			case 1:
				matrixStackIn.scale(0.8F, 0.8F, 0.8F);
				break;
			case 2:
				matrixStackIn.scale(1.25F, 1.25F, 1.25F);
				break;
			default:
				matrixStackIn.scale(1.5F, 1.5F, 1.5F);
				break;   			
		}
	}
    
    @Override
    public void render(SalamanderEntity p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
    	this.model.nymph = p_225623_1_.isNymph();
    	super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }
    
    protected int getBlockLightLevel(SalamanderEntity p_225624_1_, BlockPos p_225624_2_) {
        return 5;
    }
}
