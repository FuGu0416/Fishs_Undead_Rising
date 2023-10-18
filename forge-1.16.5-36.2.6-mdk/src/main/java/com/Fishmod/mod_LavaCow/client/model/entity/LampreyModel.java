// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.aquatic.LampreyEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LampreyModel<T extends LampreyEntity> extends FURBaseModel<T> implements IHasHead {
	private final ModelRenderer[] Parasite_Seg;
	
	private final ModelRenderer Mandible0;
	private final ModelRenderer Mandible0_2;
	private final ModelRenderer Mandible1;
	private final ModelRenderer Mandible1_2;
	private final ModelRenderer Mandible2;
	private final ModelRenderer Mandible2_2;
	private final ModelRenderer seg3_fin;
	private final ModelRenderer seg4_fin;
	private final ModelRenderer seg5_fin;
	private final ModelRenderer seg6_fin;
	private ParasiteModel.State state = ParasiteModel.State.NORMAL;
	
	public LampreyModel() {
		this.Parasite_Seg = new ModelRenderer[6];
		
		this.texWidth = 64;
		this.texHeight = 32;

		this.Parasite_Seg[0] = new ModelRenderer(this);
		this.Parasite_Seg[0].setPos(0.0F, 21.0F, -7.0F);
		this.Parasite_Seg[0].texOffs(0, 0).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);

		this.Mandible0 = new ModelRenderer(this);
		this.Mandible0.setPos(0.0F, 0.5F, -0.5F);
		this.Parasite_Seg[0].addChild(this.Mandible0);
		this.setRotationAngle(this.Mandible0, -0.7285F, 0.0F, 0.0F);
		this.Mandible0.texOffs(20, 0).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		this.Mandible0_2 = new ModelRenderer(this);
		this.Mandible0_2.setPos(0.0F, 0.0F, -1.0F);
		this.Mandible0.addChild(this.Mandible0_2);
		this.setRotationAngle(this.Mandible0_2, 0.5918F, 0.0F, 0.0F);
		this.Mandible0_2.texOffs(20, 2).addBox(-0.5F, -0.5F, -0.8F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		this.Mandible1 = new ModelRenderer(this);
		this.Mandible1.setPos(-1.0F, 1.0F, -1.0F);
		this.Parasite_Seg[0].addChild(this.Mandible1);
		this.setRotationAngle(this.Mandible1, 0.0F, 0.1367F, 0.0F);
		this.Mandible1.texOffs(20, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		this.Mandible1_2 = new ModelRenderer(this);
		this.Mandible1_2.setPos(0.0F, 0.5F, -1.0F);
		this.Mandible1.addChild(this.Mandible1_2);
		this.setRotationAngle(this.Mandible1_2, 0.0F, -0.5463F, 0.0F);
		this.Mandible1_2.texOffs(20, 2).addBox(-0.5F, -0.5F, -0.8F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		this.Mandible2 = new ModelRenderer(this);
		this.Mandible2.setPos(1.0F, 1.0F, -1.0F);
		this.Parasite_Seg[0].addChild(this.Mandible2);
		this.setRotationAngle(this.Mandible2, 0.0F, -0.1367F, 0.0F);
		this.Mandible2.texOffs(20, 0).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 1.0F, 1.0F, 0.0F, true);

		this.Mandible2_2 = new ModelRenderer(this);
		this.Mandible2_2.setPos(0.0F, 0.5F, -1.0F);
		this.Mandible2.addChild(this.Mandible2_2);
		this.setRotationAngle(this.Mandible2_2, 0.0F, 0.5463F, 0.0F);
		this.Mandible2_2.texOffs(20, 2).addBox(-0.5F, -0.5F, -0.8F, 1.0F, 1.0F, 1.0F, 0.0F, true);

		this.Parasite_Seg[1] = new ModelRenderer(this);
		this.Parasite_Seg[1].setPos(0.0F, 20.5F, -5.0F);
		this.Parasite_Seg[1].texOffs(0, 4).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 3.0F, 2.0F, 0.0F, false);

		this.Parasite_Seg[2] = new ModelRenderer(this);
		this.Parasite_Seg[2].setPos(0.0F, 20.0F, -1.5F);
		this.Parasite_Seg[2].texOffs(0, 9).addBox(-3.0F, 0.0F, -2.5F, 6.0F, 4.0F, 5.0F, 0.0F, false);

		this.seg3_fin = new ModelRenderer(this);
		this.seg3_fin.setPos(0.0F, 0.0F, 1.5F);
		this.Parasite_Seg[2].addChild(this.seg3_fin);
		this.seg3_fin.texOffs(24, -5).addBox(0.0F, -1.0F, -4.0F, 0.0F, 6.0F, 5.0F, 0.0F, false);

		this.Parasite_Seg[3] = new ModelRenderer(this);
		this.Parasite_Seg[3].setPos(0.0F, 20.75F, 2.5F);
		this.Parasite_Seg[3].texOffs(0, 18).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 3.0F, 3.0F, 0.0F, false);

		this.seg4_fin = new ModelRenderer(this);
		this.seg4_fin.setPos(0.0F, -0.75F, 2.5F);
		this.Parasite_Seg[3].addChild(this.seg4_fin);
		this.seg4_fin.texOffs(24, 3).addBox(0.0F, -1.0F, -4.0F, 0.0F, 6.0F, 3.0F, 0.0F, false);

		this.Parasite_Seg[4] = new ModelRenderer(this);
		this.Parasite_Seg[4].setPos(0.0F, 21.5F, 5.5F);
		this.Parasite_Seg[4].texOffs(0, 24).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 2.0F, 3.0F, 0.0F, false);

		this.seg5_fin = new ModelRenderer(this);
		this.seg5_fin.setPos(0.0F, -1.5F, 2.5F);
		this.Parasite_Seg[4].addChild(this.seg5_fin);
		this.seg5_fin.texOffs(24, 9).addBox(0.0F, -1.0F, -4.0F, 0.0F, 6.0F, 3.0F, 0.0F, false);

		this.Parasite_Seg[5] = new ModelRenderer(this);
		this.Parasite_Seg[5].setPos(0.0F, 22.25F, 8.0F);
		this.Parasite_Seg[5].texOffs(11, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

		this.seg6_fin = new ModelRenderer(this);
		this.seg6_fin.setPos(0.0F, -2.25F, 3.0F);
		this.Parasite_Seg[5].addChild(this.seg6_fin);
		this.seg6_fin.texOffs(24, 14).addBox(0.0F, -1.0F, -4.0F, 0.0F, 6.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		float f = 1.0F;
		if (!entity.isInWater()) {
			f = 1.5F;
		}
	      
		for (int i = 1; i < this.Parasite_Seg.length; i++) {	           
			if (this.state == ParasiteModel.State.RIDING) {
				this.Parasite_Seg[i].y = 20.0F + Math.abs((float)i - 2.0F) + f * MathHelper.sin(ageInTicks * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * (float)Math.abs(i - 2);
				this.Parasite_Seg[i].yRot = 0.0F;
			} else {
				this.Parasite_Seg[i].y = 20.0F + Math.abs((float)i - 2.0F);
				this.Parasite_Seg[i].yRot = f * MathHelper.cos(ageInTicks * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (float)(1 + Math.abs(i - 2));
				this.Parasite_Seg[i].x = f * MathHelper.sin(ageInTicks * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * (float)Math.abs(i - 2);
			}           
		}
	        
		if (this.state == ParasiteModel.State.RIDING) {
			this.Parasite_Seg[0].xRot = 1.0F;
		} else {
			this.Parasite_Seg[0].xRot = 0.0F;
		}
	        
		this.Mandible0.xRot = -0.7285F + 0.35F * MathHelper.sin(ageInTicks * 0.9F);
		this.Mandible1.yRot = 0.26F * MathHelper.sin(ageInTicks * 0.9F);
		this.Mandible2.yRot = -0.26F * MathHelper.sin(ageInTicks * 0.9F);
	        
		if (entity.getVehicle() != null) {
			this.state = ParasiteModel.State.RIDING;
		} else {
			this.state = ParasiteModel.State.NORMAL;
		}
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        for(int i = 0; i < 6; i++) {
	    	ImmutableList.of(this.Parasite_Seg[i]).forEach((modelRenderer) -> { 
	            modelRenderer.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	        });
        }
	}
	
	@Override
	public ModelRenderer getHead() {
		return this.Parasite_Seg[0];
	}
}