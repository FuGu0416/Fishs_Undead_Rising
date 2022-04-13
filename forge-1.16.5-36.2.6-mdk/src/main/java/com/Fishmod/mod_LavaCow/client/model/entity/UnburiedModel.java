package com.Fishmod.mod_LavaCow.client.model.entity;

import com.Fishmod.mod_LavaCow.entities.IAggressive;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.util.math.MathHelper;

/**
 * ModelGhoul - Fish0016054
 * Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class UnburiedModel<T extends LivingEntity> extends FURBaseModel<T> implements IHasArm, IHasHead {
    public ModelRenderer Body_base;
    public ModelRenderer Body_waist;
    public ModelRenderer Leg_l_Seg0;
    public ModelRenderer Leg_r_Seg0;
    public ModelRenderer Body_chest;
    public ModelRenderer Arm_l_Seg0;
    public ModelRenderer Arm_r_Seg0;
    public ModelRenderer Shroom_base;
    public ModelRenderer Neck0;
    public ModelRenderer Arm_l_Seg1;
    public ModelRenderer Arm_r_Seg1;
    public ModelRenderer Neck1;
    public ModelRenderer Head;
    public ModelRenderer Jaw0;
    public ModelRenderer Head_teeth;
    public ModelRenderer Jaw1;
    public ModelRenderer Jaw1_teeth;
    public ModelRenderer Leg_l_Seg1;
    public ModelRenderer Leg_r_Seg1;
    public ModelRenderer Arm_l_bandage;
    public ModelRenderer Arm_r_bandage;

    public UnburiedModel() {
    	this(0.0F);
    }
    
    public UnburiedModel(float scale) {
        this.texWidth = 64;
        this.texHeight = 64;
        this.Neck0 = new ModelRenderer(this, 0, 26);
        this.Neck0.setPos(0.0F, -3.0F, 0.0F);
        this.Neck0.addBox(-2.0F, -5.0F, -2.0F, 4.0F, 5.0F, 4.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Neck0, 0.3642502295386026F, 0.0F, 0.0F);
        this.Arm_l_Seg1 = new ModelRenderer(this, 56, 20);
        this.Arm_l_Seg1.setPos(1.0F, 7.0F, 1.0F);
        this.Arm_l_Seg1.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 10.0F, 2.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Arm_l_Seg1, -0.7740534966278743F, 0.0F, 0.0F);
        this.Body_waist = new ModelRenderer(this, 0, 45);
        this.Body_waist.setPos(0.0F, -3.2F, 0.0F);
        this.Body_waist.addBox(-3.5F, -8.0F, -1.5F, 7.0F, 8.0F, 3.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Body_waist, 0.45535640450848164F, 0.0F, -0.04555309164612875F);
        this.Jaw1 = new ModelRenderer(this, 40, 2);
        this.Jaw1.setPos(0.0F, -1.0F, 0.0F);
        this.Jaw1.addBox(-3.0F, 0.0F, -7.8F, 6.0F, 2.0F, 6.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Jaw1, 0.0911061832922575F, 0.0F, -0.04555309164612875F);
        this.Arm_l_Seg0 = new ModelRenderer(this, 48, 22);
        this.Arm_l_Seg0.setPos(5.0F, -2.0F, 0.0F);
        this.Arm_l_Seg0.addBox(0.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Arm_l_Seg0, -0.7740534966278743F, 0.0F, -0.5462880425584197F);
        this.Body_chest = new ModelRenderer(this, 0, 35);
        this.Body_chest.setPos(0.0F, -8.0F, 0.0F);
        this.Body_chest.addBox(-5.0F, -4.0F, -2.5F, 10.0F, 5.0F, 5.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Body_chest, 0.500909508638178F, 0.0F, -0.0911061832922575F);
        this.Neck1 = new ModelRenderer(this, 0, 0);
        this.Neck1.setPos(0.0F, -4.0F, 0.3F);
        this.Neck1.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Neck1, -1.2747885016356248F, -0.2275909337942703F, -0.18203784630933073F);
        this.Jaw1_teeth = new ModelRenderer(this, 0, 21);
        this.Jaw1_teeth.setPos(0.0F, -1.0F, 0.0F);
        this.Jaw1_teeth.addBox(-2.5F, 0.0F, -7.5F, 5.0F, 1.0F, 4.0F, 0.0F, 0.0F, scale);
        this.Shroom_base = new ModelRenderer(this, 0, 0);
        this.Shroom_base.setPos(0.0F, -1.0F, 2.2F);
        this.Shroom_base.addBox(0.0F, 0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Shroom_base, -1.1383037594559906F, 0.0F, 0.0F);
        this.Arm_r_Seg0 = new ModelRenderer(this, 48, 36);
        this.Arm_r_Seg0.setPos(-5.0F, -2.0F, 0.0F);
        this.Arm_r_Seg0.addBox(-2.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Arm_r_Seg0, -0.7740534966278743F, 0.0F, 0.5462880425584197F);
        this.Leg_r_Seg0 = new ModelRenderer(this, 32, 36);
        this.Leg_r_Seg0.setPos(-1.9F, 0.0F, 0.1F);
        this.Leg_r_Seg0.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Leg_r_Seg0, -0.27314402127920984F, -0.500909508638178F, 0.0911061832922575F);
        this.Arm_r_bandage = new ModelRenderer(this, 52, 10);
        this.Arm_r_bandage.setPos(1.0F, 7.0F, 1.0F);
        this.Arm_r_bandage.addBox(-3.0F, 2.0F, 0.0F, 2.0F, 6.0F, 4.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Arm_r_bandage, -0.7740534966278743F, 0.0F, 0.0F);
        this.Jaw0 = new ModelRenderer(this, 24, 0);
        this.Jaw0.setPos(0.0F, 3.0F, 0.0F);
        this.Jaw0.addBox(-3.5F, -3.5F, -3.0F, 7.0F, 5.0F, 3.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Jaw0, 0.7285004590772052F, 0.0F, 0.0F);
        this.Body_base = new ModelRenderer(this, 0, 56);
        this.Body_base.setPos(0.0F, 10.0F, 0.0F);
        this.Body_base.addBox(-4.0F, -4.0F, -2.0F, 8.0F, 4.0F, 4.0F, 0.0F, 0.0F, scale);
        this.Leg_l_Seg1 = new ModelRenderer(this, 40, 22);
        this.Leg_l_Seg1.setPos(0.0F, 8.0F, -1.0F);
        this.Leg_l_Seg1.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Leg_l_Seg1, 0.591841146688116F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setPos(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -3.0F, -8.0F, 8.0F, 6.0F, 8.0F, 0.0F, 0.0F, scale);
        this.Arm_l_bandage = new ModelRenderer(this, 39, 10);
        this.Arm_l_bandage.setPos(1.0F, 7.0F, 1.0F);
        this.Arm_l_bandage.addBox(-1.0F, 3.0F, 0.0F, 2.0F, 6.0F, 4.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Arm_l_bandage, -0.7740534966278743F, 0.0F, 0.0F);
        this.Leg_l_Seg0 = new ModelRenderer(this, 32, 22);
        this.Leg_l_Seg0.setPos(1.9F, 0.0F, 0.1F);
        this.Leg_l_Seg0.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Leg_l_Seg0, -0.27314402127920984F, 0.0F, -0.0911061832922575F);
        this.Leg_r_Seg1 = new ModelRenderer(this, 40, 36);
        this.Leg_r_Seg1.setPos(0.0F, 8.0F, -1.0F);
        this.Leg_r_Seg1.addBox(-1.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Leg_r_Seg1, 0.591841146688116F, 0.0F, 0.0F);
        this.Head_teeth = new ModelRenderer(this, 0, 15);
        this.Head_teeth.setPos(0.0F, 0.0F, 0.0F);
        this.Head_teeth.addBox(-3.5F, 3.0F, -7.5F, 7.0F, 1.0F, 4.0F, 0.0F, 0.0F, scale);
        this.Arm_r_Seg1 = new ModelRenderer(this, 56, 34);
        this.Arm_r_Seg1.setPos(-1.0F, 7.0F, 1.0F);
        this.Arm_r_Seg1.addBox(-1.0F, 0.0F, -2.0F, 2.0F, 10.0F, 2.0F, 0.0F, 0.0F, scale);
        this.setRotateAngle(Arm_r_Seg1, -0.7740534966278743F, 0.0F, 0.0F);
        this.Body_chest.addChild(this.Neck0);
        this.Arm_l_Seg0.addChild(this.Arm_l_Seg1);
        this.Body_base.addChild(this.Body_waist);
        this.Jaw0.addChild(this.Jaw1);
        this.Body_chest.addChild(this.Arm_l_Seg0);
        this.Body_waist.addChild(this.Body_chest);
        this.Neck0.addChild(this.Neck1);
        this.Jaw1.addChild(this.Jaw1_teeth);
        this.Body_chest.addChild(this.Shroom_base);
        this.Body_chest.addChild(this.Arm_r_Seg0);
        this.Body_base.addChild(this.Leg_r_Seg0);
        this.Arm_r_Seg0.addChild(this.Arm_r_bandage);
        this.Head.addChild(this.Jaw0);
        this.Leg_l_Seg0.addChild(this.Leg_l_Seg1);
        this.Neck1.addChild(this.Head);
        this.Arm_l_Seg0.addChild(this.Arm_l_bandage);
        this.Body_base.addChild(this.Leg_l_Seg0);
        this.Leg_r_Seg0.addChild(this.Leg_r_Seg1);
        this.Head.addChild(this.Head_teeth);
        this.Arm_r_Seg0.addChild(this.Arm_r_Seg1);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) { 
        ImmutableList.of(this.Body_base).forEach((modelRenderer) -> { 
            modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        });
    }
    
    @Override
    public void translateToHand(HandSide side, MatrixStack p_225599_2_) {
    	this.Body_base.translateAndRotate(p_225599_2_);
    	this.Body_waist.translateAndRotate(p_225599_2_);
    	this.Body_chest.translateAndRotate(p_225599_2_);
    	this.Arm_l_Seg0.translateAndRotate(p_225599_2_);
    	this.Arm_l_Seg1.translateAndRotate(p_225599_2_);
    }

    @Override
    protected ModelRenderer getArm(HandSide side) {
    	return side == HandSide.LEFT ? this.Arm_l_Seg0 : this.Arm_r_Seg0;
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    	this.Head_Looking(this.Head, 0.0F, 0.0F, netHeadYaw, headPitch);
    	this.SwingX_Sin(this.Jaw0, 0.7285004297824331F, ageInTicks, -0.04F, 0.06F, false, 0.0F);
    	this.SwingX_Sin(this.Jaw1, 0.091106186954104F, ageInTicks, -0.03F, 0.06F, false, 0.0F);
    	
    	this.Head.y = 0.0F + (-0.55F  * MathHelper.sin(0.06F * ageInTicks + 0.2F * (float)Math.PI)); 
    	this.Body_chest.y = -8.0F + (-0.55F  * MathHelper.sin(0.06F * ageInTicks + 0.3F * (float)Math.PI)); 
    	this.SwingX_Sin(this.Arm_r_Seg1, -0.7740535232594852F, ageInTicks, -0.03F, 0.06F, true, 0.0F);
    	this.SwingX_Sin(this.Arm_l_Seg1, -0.7740535232594852F, ageInTicks, -0.03F, 0.06F, false, 0.0F);
    	
    	this.Body_base.z = 2.0F + (-0.5F  * MathHelper.sin(0.6F * limbSwing)); 
    	
    	if(this.riding) {
    		this.Body_base.y = 15.0F; 
    		this.Body_base.zRot = 0.0F;
    		
    		this.setRotateAngle(Leg_l_Seg0, -1.6845917940249266F, -0.36425021489121656F, -0.091106186954104F);
    		this.setRotateAngle(Leg_l_Seg1, 1.0927506446736497F, 0.0F, 0.0F);
    		this.setRotateAngle(Leg_r_Seg0, -1.730144887501979F, 0.18203784098300857F, 0.091106186954104F);
    		this.setRotateAngle(Leg_r_Seg1, 0.9105382707654417F, 0.0F, 0.0F);
    	} else {
    		this.Body_base.y = 10.0F + (-0.5F  * MathHelper.sin(0.6F * limbSwing)); 
    		this.SwingZ_Sin(this.Body_base, 0.0F, limbSwing, limbSwingAmount * 0.1F, 0.6F, false, 0.0F);
    		
        	this.SwingX_Sin(this.Leg_r_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.6F, true, 0.0F);
        	this.SwingX_Sin(this.Leg_l_Seg0, -0.27314402793711257F, limbSwing, limbSwingAmount * 0.7F, 0.6F, false, 0.0F);
        	this.SwingX_Sin(this.Leg_r_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.6F, false, 0.3F * (float)Math.PI);
        	this.SwingX_Sin(this.Leg_l_Seg1, 0.5918411493512771F, limbSwing, limbSwingAmount * 0.4F, 0.6F, true, 0.3F * (float)Math.PI);
  
            this.Leg_l_Seg0.yRot = 0.0F;
            this.Leg_r_Seg0.yRot = 0.0F;
    	}
    	
    	float i = (float)((IAggressive) entityIn).getAttackTimer() / 5.0F;
    	boolean aggressive = ((MobEntity) entityIn).isAggressive();
        ItemStack itemstack = ((LivingEntity) entityIn).getMainHandItem();
        ItemStack itemstack1 = ((LivingEntity) entityIn).getOffhandItem();
        
    	if (i > 0) {
    		if(itemstack != null)
    			this.Arm_l_Seg0.xRot = GradientAnimation(-3.141592653589793F, -1.3203415791337103F, i);
    		if(itemstack1 != null)
    			this.Arm_r_Seg0.xRot = GradientAnimation(-3.141592653589793F, -1.3203415791337103F, i);
    	} else if (aggressive) {
    		this.setRotateAngle(Arm_r_Seg0, -1.9577358219620393F, 0.0F, 0.091106186954104F);
    		this.setRotateAngle(Arm_l_Seg0, -1.8212510744560826F, 0.0F, 0.045553093477052F);
    	} else {
    		this.setRotateAngle(Arm_r_Seg0, -0.7740535232594852F, 0.0F, 0.5462880558742251F);
    		this.setRotateAngle(Arm_l_Seg0, -0.7740535232594852F, 0.0F, -0.5462880558742251F);
    		
        	this.Arm_r_Seg0.y = -2.0F + (-0.55F  * MathHelper.sin(0.06F * ageInTicks + 0.5F * (float)Math.PI)); 
        	this.Arm_l_Seg0.y = -2.0F + (-0.55F * MathHelper.sin(0.06F * ageInTicks + 0.5F * (float)Math.PI)); 
    	}
    }

	@Override
	public ModelRenderer getHead() {
		return this.Head;
	}
}
