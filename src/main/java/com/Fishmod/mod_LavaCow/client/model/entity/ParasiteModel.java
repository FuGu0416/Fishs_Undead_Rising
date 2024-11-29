package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.ParasiteEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelParasite - Fish0016054
 * Created using Tabula 7.0.1
 */
@OnlyIn(Dist.CLIENT)
public class ParasiteModel<T extends ParasiteEntity> extends FURBaseModel<T> implements IHasHead {
	private final ModelRenderer[] Parasite_Seg;
	
    public ModelRenderer Mandible0;
    public ModelRenderer Mandible1;
    public ModelRenderer Mandible2;
    public ModelRenderer Mandible0_2;
    public ModelRenderer Mandible1_2;
    public ModelRenderer Mandible2_2;
    private ParasiteModel.State state = ParasiteModel.State.NORMAL;

    public ParasiteModel() {
    	this.Parasite_Seg = new ModelRenderer[6];
        this.texWidth = 64;
        this.texHeight = 32;
        this.Parasite_Seg[4] = new ModelRenderer(this, 0, 24);
        this.Parasite_Seg[4].setPos(0.0F, 22.0F, 5.5F);
        this.Parasite_Seg[4].addBox(-1.5F, 0.0F, -1.5F, 3, 2, 3, 0.0F);
        this.Mandible0 = new ModelRenderer(this, 20, 0);
        this.Mandible0.setPos(0.0F, 0.5F, -0.5F);
        this.Mandible0.addBox(-0.5F, -0.5F, -1.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Mandible0, -0.7285004297824331F, 0.0F, 0.0F);
        this.Mandible2 = new ModelRenderer(this, 20, 0);
        this.Mandible2.mirror = true;
        this.Mandible2.setPos(-1.0F, 1.0F, -1.0F);
        this.Mandible2.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Mandible2, 0.0F, 0.136659280431156F, 0.0F);
        this.Parasite_Seg[0] = new ModelRenderer(this, 0, 0);
        this.Parasite_Seg[0].setPos(0.0F, 22.0F, -7.0F);
        this.Parasite_Seg[0].addBox(-1.5F, 0.0F, -1.0F, 3, 2, 2, 0.0F);
        this.Mandible2_2 = new ModelRenderer(this, 20, 2);
        this.Mandible2_2.mirror = true;
        this.Mandible2_2.setPos(0.0F, 0.5F, -1.0F);
        this.Mandible2_2.addBox(-0.5F, -0.5F, -0.8F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Mandible2_2, 0.0F, -0.5462880558742251F, 0.0F);
        this.Parasite_Seg[3] = new ModelRenderer(this, 0, 18);
        this.Parasite_Seg[3].setPos(0.0F, 21.0F, 2.5F);
        this.Parasite_Seg[3].addBox(-2.0F, 0.0F, -1.5F, 4, 3, 3, 0.0F);
        this.Mandible0_2 = new ModelRenderer(this, 20, 2);
        this.Mandible0_2.setPos(0.0F, 0.0F, -1.0F);
        this.Mandible0_2.addBox(-0.5F, -0.5F, -0.8F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Mandible0_2, 0.5918411493512771F, 0.0F, 0.0F);
        this.Mandible1 = new ModelRenderer(this, 20, 0);
        this.Mandible1.setPos(1.0F, 1.0F, -1.0F);
        this.Mandible1.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Mandible1, 0.0F, -0.136659280431156F, 0.0F);
        this.Parasite_Seg[5] = new ModelRenderer(this, 11, 0);
        this.Parasite_Seg[5].setPos(0.0F, 23.0F, 8.0F);
        this.Parasite_Seg[5].addBox(-1.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F);
        this.Parasite_Seg[1] = new ModelRenderer(this, 0, 4);
        this.Parasite_Seg[1].setPos(0.0F, 21.0F, -5.0F);
        this.Parasite_Seg[1].addBox(-2.0F, 0.0F, -1.0F, 4, 3, 2, 0.0F);
        this.Parasite_Seg[2] = new ModelRenderer(this, 0, 9);
        this.Parasite_Seg[2].setPos(0.0F, 20.0F, -1.5F);
        this.Parasite_Seg[2].addBox(-3.0F, 0.0F, -2.5F, 6, 4, 5, 0.0F);
        this.Mandible1_2 = new ModelRenderer(this, 20, 2);
        this.Mandible1_2.setPos(0.0F, 0.5F, -1.0F);
        this.Mandible1_2.addBox(-0.5F, -0.5F, -0.8F, 1, 1, 1, 0.0F);
        this.setRotateAngle(Mandible1_2, 0.0F, 0.5462880558742251F, 0.0F);
        this.Parasite_Seg[0].addChild(this.Mandible0);
        this.Parasite_Seg[0].addChild(this.Mandible2);
        this.Mandible2.addChild(this.Mandible2_2);
        this.Mandible0.addChild(this.Mandible0_2);
        this.Parasite_Seg[0].addChild(this.Mandible1);
        this.Mandible1.addChild(this.Mandible1_2);
    }
   
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        for(int i = 0; i < 6; i++) {
	    	ImmutableList.of(this.Parasite_Seg[i]).forEach((modelRenderer) -> { 
	            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	        });
        }
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how "far"
     * arms and legs can swing at most.
     */
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	for (int i = 1; i < this.Parasite_Seg.length; i++) {      
    		if (this.state == ParasiteModel.State.RIDING) {
    			this.Parasite_Seg[i].y = 20.0F + Math.abs((float)i - 2.0F) + MathHelper.sin(ageInTicks * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * (float)Math.abs(i - 2);
    			this.Parasite_Seg[i].yRot = 0.0F;
    		} else {
    			this.Parasite_Seg[i].y = 20.0F + Math.abs((float)i - 2.0F);
    			this.Parasite_Seg[i].yRot = MathHelper.cos(ageInTicks * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.05F * (float)(1 + Math.abs(i - 2));
    			this.Parasite_Seg[i].x = MathHelper.sin(ageInTicks * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.2F * (float)Math.abs(i - 2);
    		}        
    	}
       
    	if (this.state == ParasiteModel.State.RIDING) {
    		this.Parasite_Seg[0].xRot = 1.0F;
    	} else {
    	   this.Parasite_Seg[0].xRot = 0.0F;
    	}
       
    	this.Mandible0.xRot = -0.7285004297824331F + 0.35F * MathHelper.sin(ageInTicks * 0.9F);
    	this.Mandible1.yRot = 0.26F * MathHelper.sin(ageInTicks * 0.9F);
    	this.Mandible2.yRot = -0.26F * MathHelper.sin(ageInTicks * 0.9F);
       
    	if (entityIn.getVehicle() != null) {
		   this.state = ParasiteModel.State.RIDING;
    	} else {
		   this.state = ParasiteModel.State.NORMAL;
    	}
    }
       
    @OnlyIn(Dist.CLIENT)
    static enum State {
        RIDING,
        NORMAL;
    }

	@Override
	public ModelRenderer getHead() {
		return this.Parasite_Seg[0];
	}
}
