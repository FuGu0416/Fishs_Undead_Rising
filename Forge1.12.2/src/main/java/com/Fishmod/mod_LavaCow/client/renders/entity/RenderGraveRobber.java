package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelGraveRobber;
import com.Fishmod.mod_LavaCow.entities.EntityGraveRobber;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;

public class RenderGraveRobber extends RenderLiving<EntityGraveRobber>{
	private static final ResourceLocation TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/graverobber/graverobber.png");

    public RenderGraveRobber(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelGraveRobber<EntityGraveRobber>(0.0F, 0.0F, 128, 64), 0.5F);
        this.addLayer(new LayerHeldItem(this)
        {
            public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                if (((EntityGraveRobber)entitylivingbaseIn).isAggressive()) {
                    super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                } else if (!((EntityGraveRobber)entitylivingbaseIn).getHeldItemOffhand().isEmpty()) {
                	ItemStack itemstack = entitylivingbaseIn.getHeldItemOffhand();
                	
                	GlStateManager.color(1.0F, 1.0F, 1.0F);
                    GlStateManager.pushMatrix();
                    Minecraft minecraft = Minecraft.getMinecraft();

                    GlStateManager.translate(0.0F, 0.0F, -0.375F);
                    GlStateManager.scale(0.875F, 0.875F, 0.875F);

                    minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
                    GlStateManager.popMatrix();
                }
            }
            
            protected void translateToHand(EnumHandSide p_191361_1_) {
                ((ModelIllager)this.livingEntityRenderer.getMainModel()).getArm(p_191361_1_).postRender(0.0625F);
            }
        });
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityGraveRobber entity) {
    	return TEXTURE;
    }
    
    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityMob entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
