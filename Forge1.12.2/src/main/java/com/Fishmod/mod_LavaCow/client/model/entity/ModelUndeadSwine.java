package com.Fishmod.mod_LavaCow.client.model.entity;


import com.Fishmod.mod_LavaCow.entities.EntityUndeadSwine;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelPolarBear - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ModelUndeadSwine extends ModelBase {
    public ModelRenderer Body2;
    public ModelRenderer Hair2;
    public ModelRenderer Tail;
    public ModelRenderer Leg_hind_l_1;
    public ModelRenderer Leg_hind_r_1;
    public ModelRenderer Body1;
    public ModelRenderer Leg_hind_l_2;
    public ModelRenderer Leg_hind_r_2;
    public ModelRenderer Hair1;
    public ModelRenderer Leg_front_l_1;
    public ModelRenderer Leg_front_r_1;
    public ModelRenderer Head;
    public ModelRenderer Leg_front_l_2;
    public ModelRenderer Leg_front_r_2;
    public ModelRenderer Snout;
    public ModelRenderer Jaw;
    public ModelRenderer Ear1;
    public ModelRenderer Ear2;
    public ModelRenderer Tusk1;
    public ModelRenderer Tusk2;

    public ModelUndeadSwine() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.Leg_hind_l_2 = new ModelRenderer(this, 56, 16);
        this.Leg_hind_l_2.mirror = true;
        this.Leg_hind_l_2.setRotationPoint(0.0F, 5.0F, -0.5F);
        this.Leg_hind_l_2.addBox(-1.0F, -0.5F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(Leg_hind_l_2, 0.35395277230445005F, 0.0F, 0.0F);
        this.Leg_front_r_1 = new ModelRenderer(this, 0, 16);
        this.Leg_front_r_1.setRotationPoint(-4.0F, 0.0F, -3.0F);
        this.Leg_front_r_1.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 3, 0.0F);
        this.setRotateAngle(Leg_front_r_1, 0.136659280431156F, 0.0F, 0.0F);
        this.Hair2 = new ModelRenderer(this, 42, 0);
        this.Hair2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Hair2.addBox(-1.0F, -6.0F, -6.0F, 2, 2, 7, 0.0F);
        this.setRotateAngle(Hair2, 0.0F, 0.0F, 0.0017453292519943296F);
        this.Hair1 = new ModelRenderer(this, 22, 8);
        this.Hair1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Hair1.addBox(-1.0F, -6.5F, -6.0F, 2, 2, 6, 0.0F);
        this.Body2 = new ModelRenderer(this, 32, 14);
        this.Body2.setRotationPoint(0.0F, 15.0F, 4.0F);
        this.Body2.addBox(-3.0F, -4.0F, -6.0F, 6, 8, 10, 0.0F);
        this.setRotateAngle(Body2, -0.091106186954104F, 0.0F, 0.0F);
        this.Leg_front_r_2 = new ModelRenderer(this, 56, 9);
        this.Leg_front_r_2.setRotationPoint(0.0F, 4.5F, 0.5F);
        this.Leg_front_r_2.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(Leg_front_r_2, -0.31869712141416456F, 0.0F, 0.0F);
        this.Tusk1 = new ModelRenderer(this, 0, 11);
        this.Tusk1.mirror = true;
        this.Tusk1.setRotationPoint(-1.5F, 0.0F, -1.0F);
        this.Tusk1.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(Tusk1, 0.5009094953223726F, 0.0F, -0.6373942428283291F);
        this.Body1 = new ModelRenderer(this, 4, 18);
        this.Body1.setRotationPoint(0.0F, 0.0F, -4.0F);
        this.Body1.addBox(-4.0F, -4.5F, -6.0F, 8, 8, 6, 0.0F);
        this.setRotateAngle(Body1, 0.27314402793711257F, 0.0F, 0.0F);
        this.Jaw = new ModelRenderer(this, 35, 0);
        this.Jaw.setRotationPoint(0.0F, 2.0F, -5.0F);
        this.Jaw.addBox(-2.0F, 0.0F, -3.0F, 4, 1, 3, 0.0F);
        this.Leg_hind_r_1 = new ModelRenderer(this, 0, 16);
        this.Leg_hind_r_1.setRotationPoint(-3.0F, 0.0F, 3.0F);
        this.Leg_hind_r_1.addBox(-1.0F, 0.0F, -2.0F, 2, 5, 3, 0.0F);
        this.setRotateAngle(Leg_hind_r_1, -0.27314402793711257F, 0.0F, 0.0F);
        this.Tail = new ModelRenderer(this, 12, 11);
        this.Tail.setRotationPoint(0.0F, 0.0F, 3.2F);
        this.Tail.addBox(-0.5F, -0.0F, 0.0F, 1, 5, 1, 0.0F);
        this.setRotateAngle(Tail, 0.6333799855487422F, 0.0F, 0.0F);
        this.Tusk2 = new ModelRenderer(this, 0, 11);
        this.Tusk2.setRotationPoint(1.5F, 0.0F, -1.0F);
        this.Tusk2.addBox(-0.5F, -3.0F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(Tusk2, 0.5009094953223726F, 0.0F, 0.6373942428283291F);
        this.Ear2 = new ModelRenderer(this, 4, 11);
        this.Ear2.mirror = true;
        this.Ear2.setRotationPoint(2.0F, -2.5F, -3.0F);
        this.Ear2.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Ear2, 0.136659280431156F, -0.36425021489121656F, 0.22759093446006054F);
        this.Leg_front_l_1 = new ModelRenderer(this, 0, 16);
        this.Leg_front_l_1.setRotationPoint(4.0F, 0.0F, -3.0F);
        this.Leg_front_l_1.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 3, 0.0F);
        this.setRotateAngle(Leg_front_l_1, 0.136659280431156F, 0.0F, 0.0F);
        this.Snout = new ModelRenderer(this, 22, 0);
        this.Snout.setRotationPoint(0.0F, 0.0F, -5.0F);
        this.Snout.addBox(-2.0F, -1.0F, -5.0F, 4, 3, 5, 0.0F);
        this.Ear1 = new ModelRenderer(this, 4, 11);
        this.Ear1.setRotationPoint(-2.0F, -2.5F, -3.0F);
        this.Ear1.addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
        this.setRotateAngle(Ear1, 0.136659280431156F, 0.36425021489121656F, -0.22759093446006054F);
        this.Leg_hind_r_2 = new ModelRenderer(this, 56, 16);
        this.Leg_hind_r_2.setRotationPoint(0.0F, 5.0F, -0.5F);
        this.Leg_hind_r_2.addBox(-1.0F, -0.5F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(Leg_hind_r_2, 0.35395277230445005F, 0.0F, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, -4.5F);
        this.Head.addBox(-3.0F, -3.0F, -5.5F, 6, 6, 5, 0.0F);
        this.setRotateAngle(Head, -0.136659280431156F, 0.0F, 0.0F);
        this.Leg_hind_l_1 = new ModelRenderer(this, 0, 16);
        this.Leg_hind_l_1.setRotationPoint(3.0F, 0.0F, 3.0F);
        this.Leg_hind_l_1.addBox(-1.0F, 0.0F, -2.0F, 2, 5, 3, 0.0F);
        this.setRotateAngle(Leg_hind_l_1, -0.27314402793711257F, 0.0F, 0.0F);
        this.Leg_front_l_2 = new ModelRenderer(this, 56, 9);
        this.Leg_front_l_2.mirror = true;
        this.Leg_front_l_2.setRotationPoint(0.0F, 4.5F, 0.5F);
        this.Leg_front_l_2.addBox(-1.0F, 0.0F, -1.0F, 2, 5, 2, 0.0F);
        this.setRotateAngle(Leg_front_l_2, -0.31869712141416456F, 0.0F, 0.0F);
        this.Leg_hind_l_1.addChild(this.Leg_hind_l_2);
        this.Body1.addChild(this.Leg_front_r_1);
        this.Body2.addChild(this.Hair2);
        this.Body1.addChild(this.Hair1);
        this.Leg_front_r_1.addChild(this.Leg_front_r_2);
        this.Jaw.addChild(this.Tusk1);
        this.Body2.addChild(this.Body1);
        this.Head.addChild(this.Jaw);
        this.Body2.addChild(this.Leg_hind_r_1);
        this.Body2.addChild(this.Tail);
        this.Jaw.addChild(this.Tusk2);
        this.Head.addChild(this.Ear2);
        this.Body1.addChild(this.Leg_front_l_1);
        this.Head.addChild(this.Snout);
        this.Head.addChild(this.Ear1);
        this.Leg_hind_r_1.addChild(this.Leg_hind_r_2);
        this.Body1.addChild(this.Head);
        this.Body2.addChild(this.Leg_hind_l_1);
        this.Leg_front_l_1.addChild(this.Leg_front_l_2);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.Body2.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
    
    private float GradientAnimation(float AnimStart, float AnimEnd, float TickIn) {
    	return AnimStart * MathHelper.sin((float)Math.PI * 0.5F * TickIn) + AnimEnd * MathHelper.sin((float)Math.PI * 0.5F * (1.0F - TickIn));
    }
    
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {	
        this.Jaw.rotateAngleX = 0.3662880558742251F + (-0.15F * MathHelper.sin(0.03F * ageInTicks + 0.2F * (float)Math.PI));
        
        this.Body1.rotateAngleY = netHeadYaw * 0.004F;
        
        this.Leg_front_r_1.rotateAngleX = 0.136659280431156F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        this.Leg_front_l_1.rotateAngleX = 0.136659280431156F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
        this.Leg_hind_r_1.rotateAngleX = -0.27314402793711257F + MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 0.7F * limbSwingAmount;
        this.Leg_hind_l_1.rotateAngleX = -0.27314402793711257F + MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        
        float i = (float)((EntityUndeadSwine) entityIn).getAttackTimer() / 10.0F;
        if(i > 0) {
        	this.Head.rotateAngleX = GradientAnimation(0.5462880558742251F, -0.4553564018453205F, i);
        	this.Snout.rotateAngleX = -0.22759093446006054F;
        }
        else {
        	
            if(((EntityUndeadSwine)entityIn).isDigging())
            	this.Head.rotateAngleX = 0.7F + 0.15F * MathHelper.sin(ageInTicks);
            else this.Head.rotateAngleX = -0.136659280431156F + headPitch * 0.017453292F;
        	
            this.Head.rotateAngleY = netHeadYaw * 0.008F; 
            this.Head.rotationPointY = -0.3F * MathHelper.sin(0.03F * ageInTicks);
            
            this.Snout.rotateAngleX = headPitch * 0.008F; 
        }
    }
}
