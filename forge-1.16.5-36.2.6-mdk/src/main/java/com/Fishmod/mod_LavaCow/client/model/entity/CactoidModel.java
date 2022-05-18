package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.CactoidEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports

@OnlyIn(Dist.CLIENT)
public class CactoidModel<T extends CactoidEntity> extends FURBaseModel<T> {
	private final ModelRenderer body_base;
	private final ModelRenderer root_r;
	private final ModelRenderer root_l;
	private final ModelRenderer crest;
	private final ModelRenderer flower0;
	private final ModelRenderer flower1;
	private final ModelRenderer fruit;
	
	public CactoidModel() {
		this.texWidth = 32;
		this.texHeight = 32;

		this.body_base = new ModelRenderer(this);
		this.body_base.setPos(0.0F, 22.0F, 0.0F);
		this.body_base.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		this.root_r = new ModelRenderer(this);
		this.root_r.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.root_r);
		this.root_r.texOffs(0, 0).addBox(-3.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, true);

		this.root_l = new ModelRenderer(this);
		this.root_l.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.root_l);
		this.root_l.texOffs(0, 0).addBox(1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		this.crest = new ModelRenderer(this);
		this.crest.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.crest);
		this.crest.texOffs(0, 16).addBox(-2.0F, -11.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
		
		this.flower0 = new ModelRenderer(this);
		this.flower0.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.flower0);
		this.setRotateAngle(this.flower0, 0.0F, -0.7854F, 0.0F);
		this.flower0.texOffs(20, 17).addBox(-3.0F, -14.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, false);

		this.flower1 = new ModelRenderer(this);
		this.flower1.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.flower1);
		this.setRotateAngle(this.flower1, 0.0F, 0.7854F, 0.0F);
		this.flower1.texOffs(20, 17).addBox(-3.0F, -14.0F, 0.0F, 6.0F, 6.0F, 0.0F, 0.0F, true);

		this.fruit = new ModelRenderer(this);
		this.fruit.setPos(0.0F, 0.0F, 0.0F);
		this.body_base.addChild(this.fruit);
		this.fruit.texOffs(24, 0).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
    	switch(entity.getGrowingStage()) {
	    	case 0:
		    	this.flower0.visible = false;
		    	this.flower1.visible = false;
		    	this.fruit.visible = false;
	    		break;
	    	case 1:
		    	this.flower0.visible = true;
		    	this.flower1.visible = true;
		    	this.fruit.visible = false;
	    		break;
	    	case 2:
			default:
		    	this.flower0.visible = false;
		    	this.flower1.visible = false;
		    	this.fruit.visible = true;
				break;
		}
    	
		if (entity.isSilent()) {  
			this.body_base.y = 24.0F;
			this.root_r.visible = false;
			this.root_l.visible = false;
		} else {
			this.body_base.y = 22.0F;
			this.root_r.visible = true;
			this.root_l.visible = true;
	        this.root_r.xRot = MathHelper.cos(limbSwing) * 0.7F * limbSwingAmount;
	        this.root_l.xRot = MathHelper.cos(limbSwing + (float)Math.PI) * 0.7F * limbSwingAmount;			
		}
	}

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.body_base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, 0.85F);
        });
    }
}