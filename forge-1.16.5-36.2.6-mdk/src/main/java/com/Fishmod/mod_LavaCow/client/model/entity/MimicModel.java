package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.tameable.MimicEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelMimic - Fish0016054
 * Created using Tabula 7.0.1
 */
@OnlyIn(Dist.CLIENT)
public class MimicModel<T extends MimicEntity> extends FURBaseModel<T> {
    public ModelRenderer Chest_Base;
    public ModelRenderer Chest_top;
    public ModelRenderer Eye_r;
    public ModelRenderer Eye_l;
    public ModelRenderer Leg0_r_Seg0;
    public ModelRenderer Leg1_r_Seg0;
    public ModelRenderer Leg0_l_Seg0;
    public ModelRenderer Leg1_l_Seg0;
    public ModelRenderer Pincer_r_Seg0;
    public ModelRenderer Pincer_l_Seg0;
    public ModelRenderer Chest_Base_Outer;
    public ModelRenderer Chest_lock;
    public ModelRenderer Chest_top_outer;
    public ModelRenderer Chest_lock_outer;
    public ModelRenderer Leg0_r_Seg1;
    public ModelRenderer Leg0_r_Seg2;
    public ModelRenderer Leg1_r_Seg1;
    public ModelRenderer Leg1_r_Seg2;
    public ModelRenderer Leg0_l_Seg1;
    public ModelRenderer Leg0_l_Seg2;
    public ModelRenderer Leg1_l_Seg1;
    public ModelRenderer Leg1_l_Seg2;
    public ModelRenderer Pincer_r_Seg1;
    public ModelRenderer Pincer_r_Seg2;
    public ModelRenderer Pincer_r_Seg3;
    public ModelRenderer Pincer_l_Seg1;
    public ModelRenderer Pincer_l_Seg2;
    public ModelRenderer Pincer_l_Seg3;

    public MimicModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.Chest_Base = new ModelRenderer(this, 0, 19);
        this.Chest_Base.setPos(0.0F, 18.0F, 1.0F);
        this.Chest_Base.addBox(-7.0F, -5.0F, -7.0F, 0, 0, 0, 0.0F);
        this.Eye_r = new ModelRenderer(this, 44, 0);
        this.Eye_r.setPos(-2.0F, -4.0F, -5.0F);
        this.Eye_r.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(Eye_r, 0.8155923594569501F, 0.36425021489121656F, 0.0F);
        this.Leg1_r_Seg1 = new ModelRenderer(this, 6, 44);
        this.Leg1_r_Seg1.setPos(0.0F, -8.0F, 0.0F);
        this.Leg1_r_Seg1.addBox(-1.0F, -2.0F, -1.5F, 2, 10, 3, 0.0F);
        this.setRotateAngle(Leg1_r_Seg1, 0.0F, 0.0F, 0.8651597102135892F);
        this.Leg0_l_Seg2 = new ModelRenderer(this, 1, 56);
        this.Leg0_l_Seg2.mirror = true;
        this.Leg0_l_Seg2.setPos(0.0F, 8.0F, 0.0F);
        this.Leg0_l_Seg2.addBox(-0.5F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(Leg0_l_Seg2, 0.0F, 0.0F, 0.5918411493512771F);
        this.Leg1_l_Seg1 = new ModelRenderer(this, 6, 44);
        this.Leg1_l_Seg1.mirror = true;
        this.Leg1_l_Seg1.setPos(0.0F, -8.0F, 0.0F);
        this.Leg1_l_Seg1.addBox(-1.0F, -2.0F, -1.5F, 2, 10, 3, 0.0F);
        this.setRotateAngle(Leg1_l_Seg1, 0.0F, 0.0F, -0.8651597102135892F);
        this.Pincer_l_Seg3 = new ModelRenderer(this, 17, 56);
        this.Pincer_l_Seg3.mirror = true;
        this.Pincer_l_Seg3.setPos(0.0F, 9.0F, 1.0F);
        this.Pincer_l_Seg3.addBox(-1.0F, -1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.Leg1_l_Seg2 = new ModelRenderer(this, 1, 56);
        this.Leg1_l_Seg2.mirror = true;
        this.Leg1_l_Seg2.setPos(0.0F, 8.0F, 0.0F);
        this.Leg1_l_Seg2.addBox(-0.5F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(Leg1_l_Seg2, 0.0F, 0.0F, 0.5918411493512771F);
        this.Pincer_r_Seg1 = new ModelRenderer(this, 26, 45);
        this.Pincer_r_Seg1.setPos(0.0F, -6.0F, 0.0F);
        this.Pincer_r_Seg1.addBox(-1.0F, -2.0F, -2.0F, 2, 10, 4, 0.0F);
        this.setRotateAngle(Pincer_r_Seg1, -0.36425021489121656F, 0.0F, 1.2292353921796064F);
        this.Leg1_r_Seg0 = new ModelRenderer(this, 1, 44);
        this.Leg1_r_Seg0.setPos(-6.0F, 4.0F, 5.0F);
        this.Leg1_r_Seg0.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Leg1_r_Seg0, 0.0F, 0.5462880558742251F, -0.5009094953223726F);
        this.Pincer_r_Seg0 = new ModelRenderer(this, 17, 45);
        this.Pincer_r_Seg0.setPos(-6.0F, 2.0F, -4.0F);
        this.Pincer_r_Seg0.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(Pincer_r_Seg0, 0.5918411493512771F, -1.0016444577195458F, -1.3658946726107624F);
        this.Chest_top = new ModelRenderer(this, 0, 0);
        this.Chest_top.setPos(0.0F, -4.0F, 7.0F);
        this.Chest_top.addBox(-7.0F, -5.0F, -14.0F, 0, 0, 0, 0.0F);
        this.setRotateAngle(Chest_top, -0.136659280431156F, 0.0F, 0.0F);
        this.Leg1_l_Seg0 = new ModelRenderer(this, 1, 44);
        this.Leg1_l_Seg0.mirror = true;
        this.Leg1_l_Seg0.setPos(6.0F, 4.0F, 5.0F);
        this.Leg1_l_Seg0.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Leg1_l_Seg0, 0.0F, -0.5462880558742251F, 0.5009094953223726F);
        this.Chest_lock = new ModelRenderer(this, 0, 0);
        this.Chest_lock.setPos(0.0F, 0.0F, -14.0F);
        this.Chest_lock.addBox(-1.0F, -2.0F, -1.0F, 0, 0, 0, 0.0F);
        this.Leg0_l_Seg0 = new ModelRenderer(this, 1, 44);
        this.Leg0_l_Seg0.mirror = true;
        this.Leg0_l_Seg0.setPos(6.0F, 4.0F, 0.0F);
        this.Leg0_l_Seg0.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Leg0_l_Seg0, 0.0F, 0.22759093446006054F, 0.5009094953223726F);
        this.Leg0_r_Seg2 = new ModelRenderer(this, 1, 56);
        this.Leg0_r_Seg2.setPos(0.0F, 8.0F, 0.0F);
        this.Leg0_r_Seg2.addBox(-0.5F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(Leg0_r_Seg2, 0.0F, 0.0F, -0.5918411493512771F);
        this.Pincer_l_Seg0 = new ModelRenderer(this, 17, 45);
        this.Pincer_l_Seg0.mirror = true;
        this.Pincer_l_Seg0.setPos(6.0F, 2.0F, -4.0F);
        this.Pincer_l_Seg0.addBox(-1.0F, -6.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(Pincer_l_Seg0, 0.5918411493512771F, 1.0016444577195458F, 1.3658946726107624F);
        this.Pincer_l_Seg2 = new ModelRenderer(this, 17, 56);
        this.Pincer_l_Seg2.mirror = true;
        this.Pincer_l_Seg2.setPos(0.0F, 8.0F, -1.0F);
        this.Pincer_l_Seg2.addBox(-1.0F, -1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(Pincer_l_Seg2, -0.3754203221039803F, 0.0F, 0.0F);
        this.Pincer_r_Seg3 = new ModelRenderer(this, 17, 56);
        this.Pincer_r_Seg3.setPos(0.0F, 9.0F, 1.0F);
        this.Pincer_r_Seg3.addBox(-1.0F, -1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.Leg0_l_Seg1 = new ModelRenderer(this, 6, 44);
        this.Leg0_l_Seg1.mirror = true;
        this.Leg0_l_Seg1.setPos(0.0F, -8.0F, 0.0F);
        this.Leg0_l_Seg1.addBox(-1.0F, -2.0F, -1.5F, 2, 10, 3, 0.0F);
        this.setRotateAngle(Leg0_l_Seg1, 0.0F, 0.0F, -0.8651597102135892F);
        this.Pincer_l_Seg1 = new ModelRenderer(this, 26, 45);
        this.Pincer_l_Seg1.mirror = true;
        this.Pincer_l_Seg1.setPos(0.0F, -6.0F, 0.0F);
        this.Pincer_l_Seg1.addBox(-1.0F, -2.0F, -2.0F, 2, 10, 4, 0.0F);
        this.setRotateAngle(Pincer_l_Seg1, -0.36425021489121656F, 0.0F, -1.2292353921796064F);
        this.Eye_l = new ModelRenderer(this, 44, 0);
        this.Eye_l.mirror = true;
        this.Eye_l.setPos(2.0F, -4.0F, -5.0F);
        this.Eye_l.addBox(-0.5F, -5.0F, -0.5F, 1, 5, 1, 0.0F);
        this.setRotateAngle(Eye_l, 0.8155923594569501F, -0.36425021489121656F, 0.0F);
        this.Leg0_r_Seg0 = new ModelRenderer(this, 1, 44);
        this.Leg0_r_Seg0.setPos(-6.0F, 4.0F, 0.0F);
        this.Leg0_r_Seg0.addBox(-0.5F, -8.0F, -0.5F, 1, 8, 1, 0.0F);
        this.setRotateAngle(Leg0_r_Seg0, 0.0F, -0.22759093446006054F, -0.5009094953223726F);
        this.Leg0_r_Seg1 = new ModelRenderer(this, 6, 44);
        this.Leg0_r_Seg1.setPos(0.0F, -8.0F, 0.0F);
        this.Leg0_r_Seg1.addBox(-1.0F, -2.0F, -1.5F, 2, 10, 3, 0.0F);
        this.setRotateAngle(Leg0_r_Seg1, 0.0F, 0.0F, 0.8651597102135892F);
        this.Leg1_r_Seg2 = new ModelRenderer(this, 1, 56);
        this.Leg1_r_Seg2.setPos(0.0F, 8.0F, 0.0F);
        this.Leg1_r_Seg2.addBox(-0.5F, -1.0F, -1.0F, 1, 3, 2, 0.0F);
        this.setRotateAngle(Leg1_r_Seg2, 0.0F, 0.0F, -0.5918411493512771F);
        this.Pincer_r_Seg2 = new ModelRenderer(this, 17, 56);
        this.Pincer_r_Seg2.setPos(0.0F, 8.0F, -1.0F);
        this.Pincer_r_Seg2.addBox(-1.0F, -1.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(Pincer_r_Seg2, -0.3754203221039803F, 0.0F, 0.0F);
        this.Chest_Base_Outer = new ModelRenderer(this, 0, 19);
        this.Chest_Base_Outer.setPos(0.0F, 0.0F, 0.0F);
        this.Chest_Base_Outer.addBox(-7.0F, -5.0F, -7.0F, 14.0F, 10.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Chest_Base_Outer, 0.0F, 3.141592653589793F, 3.141592653589793F);
        this.Chest_lock_outer = new ModelRenderer(this, 0, 0);
        this.Chest_lock_outer.setPos(0.0F, 0.0F, 14.0F);
        this.Chest_lock_outer.addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 1.0F, 0.0F, 0.0F, 0.0F);
        this.Chest_top_outer = new ModelRenderer(this, 0, 0);
        this.Chest_top_outer.setPos(0.0F, 0.0F, 0.0F);
        this.Chest_top_outer.addBox(-7.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.setRotateAngle(Chest_top_outer, 0.0F, 3.141592653589793F, 3.141592653589793F);
        this.Chest_Base.addChild(this.Eye_r);
        this.Leg1_r_Seg0.addChild(this.Leg1_r_Seg1);
        this.Leg0_l_Seg1.addChild(this.Leg0_l_Seg2);
        this.Leg1_l_Seg0.addChild(this.Leg1_l_Seg1);
        this.Pincer_l_Seg1.addChild(this.Pincer_l_Seg3);
        this.Leg1_l_Seg1.addChild(this.Leg1_l_Seg2);
        this.Pincer_r_Seg0.addChild(this.Pincer_r_Seg1);
        this.Chest_Base.addChild(this.Leg1_r_Seg0);
        this.Chest_Base.addChild(this.Pincer_r_Seg0);
        this.Chest_Base.addChild(this.Chest_top);
        this.Chest_Base.addChild(this.Leg1_l_Seg0);
        this.Chest_top.addChild(this.Chest_lock);
        this.Chest_Base.addChild(this.Leg0_l_Seg0);
        this.Leg0_r_Seg1.addChild(this.Leg0_r_Seg2);
        this.Chest_Base.addChild(this.Pincer_l_Seg0);
        this.Pincer_l_Seg1.addChild(this.Pincer_l_Seg2);
        this.Pincer_r_Seg1.addChild(this.Pincer_r_Seg3);
        this.Leg0_l_Seg0.addChild(this.Leg0_l_Seg1);
        this.Pincer_l_Seg0.addChild(this.Pincer_l_Seg1);
        this.Chest_Base.addChild(this.Eye_l);
        this.Chest_Base.addChild(this.Leg0_r_Seg0);
        this.Leg0_r_Seg0.addChild(this.Leg0_r_Seg1);
        this.Leg1_r_Seg1.addChild(this.Leg1_r_Seg2);
        this.Pincer_r_Seg1.addChild(this.Pincer_r_Seg2);
        this.Chest_Base.addChild(this.Chest_Base_Outer);
        this.Chest_top_outer.addChild(this.Chest_lock_outer);
        this.Chest_top.addChild(this.Chest_top_outer);
    }
    
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
    	ImmutableList.of(this.Chest_Base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }); 
    }
    
    private void showAllBodyPart(boolean showIn) 
    {
    	this.Leg0_r_Seg0.visible = showIn;
    	this.Leg0_l_Seg0.visible = showIn;
    	this.Leg1_r_Seg0.visible = showIn;
    	this.Leg1_l_Seg0.visible = showIn;
    	this.Pincer_r_Seg0.visible = showIn;
    	this.Pincer_l_Seg0.visible = showIn;
    }
    
    private void toCamouflagePose(float TickIn) {
		this.Leg0_r_Seg0.zRot = GradientAnimation(-0.5009094953223726F, 0.5918411493512771F, TickIn);
    	this.Leg0_r_Seg0.x = GradientAnimation(-6.0F, -5.0F, TickIn);
    	this.Leg0_r_Seg0.y = GradientAnimation(4.0F, 3.5F, TickIn);
    	this.Leg0_l_Seg0.zRot = GradientAnimation(0.5009094953223726F, -0.5918411493512771F, TickIn);
    	this.Leg0_l_Seg0.x = GradientAnimation(6.0F, 5.0F, TickIn);
    	this.Leg0_l_Seg0.y = GradientAnimation(4.0F, 3.5F, TickIn);
    	
    	this.Leg0_r_Seg1.zRot = GradientAnimation(0.8651597102135892F, 0.136659280431156F, TickIn);
    	this.Leg0_l_Seg1.zRot = GradientAnimation(-0.8651597102135892F, -0.136659280431156F, TickIn);
    	
    	this.Leg1_r_Seg0.xRot = GradientAnimation(0.0F, -0.045553093477052F, TickIn);
    	this.Leg1_r_Seg0.yRot = GradientAnimation(0.5462880558742251F, 0.0F, TickIn);
    	this.Leg1_r_Seg0.zRot = GradientAnimation(-0.5009094953223726F, 0.31869712141416456F, TickIn);
    	this.Leg1_r_Seg0.x = GradientAnimation(-6.0F, -5.0F, TickIn);
    	this.Leg1_r_Seg0.y = GradientAnimation(4.0F, 3.0F, TickIn);
    	this.Leg1_l_Seg0.xRot = GradientAnimation(0.0F, -0.045553093477052F, TickIn);
    	this.Leg1_l_Seg0.yRot = GradientAnimation(-0.5462880558742251F, 0.0F, TickIn);
    	this.Leg1_l_Seg0.zRot = GradientAnimation(0.5009094953223726F, -0.31869712141416456F, TickIn);
    	this.Leg1_l_Seg0.x = GradientAnimation(6.0F, 5.0F, TickIn);
    	this.Leg1_l_Seg0.y = GradientAnimation(4.0F, 3.0F, TickIn);
    	
    	this.Leg1_r_Seg1.zRot = GradientAnimation(0.8651597102135892F, 0.136659280431156F, TickIn);
    	this.Leg1_l_Seg1.zRot = GradientAnimation(-0.8651597102135892F, -0.136659280431156F, TickIn);
    	
    	this.Pincer_r_Seg0.x = GradientAnimation(-6.0F, 0.6F, TickIn);
    	this.Pincer_r_Seg0.z = GradientAnimation(-4.0F, 3.6F, TickIn);
    	this.Pincer_l_Seg0.x = GradientAnimation(6.0F, -0.6F, TickIn);
    	this.Pincer_l_Seg0.z = GradientAnimation(-4.0F, 3.6F, TickIn);
    	
    	this.Pincer_r_Seg1.xRot = GradientAnimation(-0.36425021489121656F, -0.5918411493512771F, TickIn);
    	this.Pincer_r_Seg1.yRot = GradientAnimation(0.0F, -0.6373942428283291F, TickIn);
    	this.Pincer_l_Seg1.xRot = GradientAnimation(-0.36425021489121656F, -0.5918411493512771F, TickIn);
    	this.Pincer_l_Seg1.yRot = GradientAnimation(0.0F, 0.6373942428283291F, TickIn);
    	
    	this.Pincer_r_Seg3.xRot = GradientAnimation(0.0F, -0.4553564018453205F, TickIn);
    	this.Pincer_l_Seg3.xRot = GradientAnimation(0.0F, -0.4553564018453205F, TickIn);
    }
    
    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	MimicEntity Entity = (MimicEntity)entityIn;
    	float i = ((float)Entity.getSitTimer()) / 20.0F;
    	float j = ((float)Entity.getAttackTimer()) / 5.0F;

    	if((Entity.isAggressive() || Entity.isTame()) && !Entity.isInSittingPose()) {   		
    		this.showAllBodyPart(true); 
    		if(i <= 1.0F)
    			this.toCamouflagePose(i);
    		
    		if(i == 1.0F) {
	    		this.Chest_top.xRot = -0.2F + (-0.02F * MathHelper.sin(0.12F * ageInTicks + 0.1F)); 
	    		this.Chest_Base.yRot = 0.12F * MathHelper.cos(limbSwing);
	    		this.Chest_Base.y = 18.0F + MathHelper.cos(limbSwing);
	
	        	this.Chest_Base.setPos(0.0F, 18.0F, 1.0F);
	        	this.Eye_r.setPos(-2.0F, -4.0F, -5.0F);
	        	this.Eye_l.setPos(2.0F, -4.0F, -5.0F);
	        	
	        	this.Leg0_r_Seg0.zRot = -0.5F + MathHelper.cos(limbSwing) * 0.7F * limbSwingAmount;
	        	this.Leg0_l_Seg0.zRot = 0.5F + MathHelper.cos(limbSwing + ((float)Math.PI * 0.5F)) * 0.7F * limbSwingAmount;
	        	this.Leg1_r_Seg0.zRot = -0.5F + MathHelper.cos(limbSwing + (float)Math.PI) * 0.7F * limbSwingAmount;
	        	this.Leg1_l_Seg0.zRot = 0.5F + MathHelper.cos(limbSwing + ((float)Math.PI * 1.5F)) * 0.7F * limbSwingAmount;
    		}
    		
    		if(j > 0.0F) {
    			this.Pincer_l_Seg0.y = GradientAnimation(-4.0F, 1.0F, j);   	
    			this.Pincer_r_Seg0.y = GradientAnimation(-4.0F, 1.0F, j);   			
    		} else{
    	    	this.Pincer_l_Seg0.y = 1.0F + (-0.55F * MathHelper.sin(0.12F * ageInTicks));
    	    	this.Pincer_r_Seg0.y = 1.0F + (-0.55F * MathHelper.sin(0.12F * ageInTicks)); 
    		}
        } else if (Entity.isInSittingPose()) {     	       	
        	//this.Chest_Base.yRot = Entity.rotationAngle;
        	if(i >= 0.0F) {
        		this.toCamouflagePose(i);
        		this.showAllBodyPart(true); 
        	}

        	if(i == 0.0F) {
        		this.showAllBodyPart(false); 
        		if(Entity.isTame() || Entity.IdleTimer > 0) {
	        		this.Chest_top.xRot = -0.2F + (-0.02F * MathHelper.sin(0.12F * ageInTicks + 0.1F)); 
	
	            	this.Chest_Base.setPos(0.0F, 18.0F, 1.0F);
	            	this.Eye_r.setPos(-2.0F, -4.0F, -5.0F);
	            	this.Eye_l.setPos(2.0F, -4.0F, -5.0F);
	        	} else {
	            	this.Chest_top.xRot = 0.0F;
	            	this.Chest_Base.setPos(0.0F, 19.0F, 1.0F);
	            	this.Eye_r.setPos(0.0F, 0.0F, 0.0F);
	            	this.Eye_l.setPos(0.0F, 0.0F, 0.0F);
	        	}
        	}      	
        }   	
    }
}
