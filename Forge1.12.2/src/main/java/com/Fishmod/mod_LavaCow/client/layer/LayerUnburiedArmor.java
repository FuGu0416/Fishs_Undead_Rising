package com.Fishmod.mod_LavaCow.client.layer;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelUnburied;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelUnburiedArmor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerUnburiedArmor extends LayerArmorBase<ModelBiped>{
	
	RenderLiving<?> renderer;
    private float alpha = 1.0F;
    private float colorR = 1.0F;
    private float colorG = 1.0F;
    private float colorB = 1.0F;
    private boolean skipRenderGlint;
	
    public LayerUnburiedArmor(RenderLiving<?> renderZombieMushroom)
    {
        super(renderZombieMushroom);
        this.renderer = renderZombieMushroom;
    }

    protected void initArmor()
    {
        this.modelLeggings = new ModelUnburiedArmor(0.5F);
        this.modelArmor = new ModelUnburiedArmor(1.0F);
    	//this.modelLeggings = new ModelBiped(0.5F);
    	//this.modelArmor = new ModelBiped(1.0F);
    }
    
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
    }
    
    private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn)
    {
        ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);
        GlStateManager.pushMatrix();
        if (itemstack.getItem() instanceof ItemArmor)
        {
            ItemArmor itemarmor = (ItemArmor)itemstack.getItem();

            if (itemarmor.getEquipmentSlot() == slotIn)
            {
            	ModelBiped t = this.getModelFromSlot(slotIn);
                t = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, t);
                t.setModelAttributes(this.renderer.getMainModel());
                t.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                if(itemarmor.getClass() != ItemArmor.class && slotIn == EntityEquipmentSlot.HEAD) {

                	((ModelUnburied)this.renderer.getMainModel()).Body_base.postRender(0.0625F);
                	((ModelUnburied)this.renderer.getMainModel()).Body_waist.postRender(0.0625F);
                	((ModelUnburied)this.renderer.getMainModel()).Body_chest.postRender(0.0625F);
                	((ModelUnburied)this.renderer.getMainModel()).Neck0.postRender(0.0625F);
                	((ModelUnburied)this.renderer.getMainModel()).Neck1.postRender(0.0625F);
                	((ModelUnburied)this.renderer.getMainModel()).Head.postRender(0.0625F);
                	
                	if(!entityLivingBaseIn.isChild()) {
                		GlStateManager.translate(0.0F, 0.25F, -0.20F);
                		GlStateManager.scale(1.05F, 1.05F, 1.05F);
                	}
                	else {
                		GlStateManager.translate(0.0F, -0.85F, -0.15F);
                		GlStateManager.scale(1.5F, 1.5F, 1.5F);
                	}
                    //GlStateManager.rotate(0, 1, 0, 0);
                    
                    
                    //t.bipedHead.rotateAngleX = 0.0F;
                    //t.bipedHead.rotateAngleY = 0.0F;
                    //t.bipedHead.rotateAngleZ = 0.0F;
                }             
                /*else if(slotIn == EntityEquipmentSlot.CHEST) {
                	((ModelUnburied)this.renderer.getMainModel()).Body_base.postRender(0.0625F);
                	((ModelUnburied)this.renderer.getMainModel()).Body_waist.postRender(0.0625F);
                	((ModelUnburied)this.renderer.getMainModel()).Body_chest.postRender(0.0625F);
                	
                	if(!entityLivingBaseIn.isChild()) {
                		GlStateManager.translate(0.0F, -0.25F, 0.0F);
                		GlStateManager.scale(1.05F, 1.0F, 1.05F);
                	}
                	else {
                		GlStateManager.translate(0, -1.6F, 0.0F);
                		GlStateManager.scale(2.1F, 1.8F, 2.1F);
                	}
                }
                else if(slotIn == EntityEquipmentSlot.LEGS) {
                	((ModelUnburied)this.renderer.getMainModel()).Body_base.postRender(0.0625F);
                	
                	GlStateManager.rotate(-15.0F, 1.0F, 0.0F, 0.0F);
                	
                	if(!entityLivingBaseIn.isChild()) {
                		GlStateManager.translate(0.0F, -0.80F, 0.0F);
                		GlStateManager.scale(1.0F, 0.9F, 1.0F);
                	}
                	else {
                		GlStateManager.translate(0, -1.6F, 0.0F);
                		GlStateManager.scale(2.1F, 1.8F, 2.1F);
                	}
                }
                else if(slotIn == EntityEquipmentSlot.FEET) {
                	((ModelUnburied)this.renderer.getMainModel()).Body_base.postRender(0.0625F);
                	
                	if(!entityLivingBaseIn.isChild()) {
                		GlStateManager.translate(0.0F, -0.75F, -0.25F);
                		GlStateManager.scale(0.8F, 0.8F, 0.8F);
                	}
                	else {
                		GlStateManager.translate(0, -1.6F, 0.0F);
                		GlStateManager.scale(2.1F, 1.8F, 2.1F);
                	}
                }*/

                this.setModelSlotVisible(t, slotIn);
                this.renderer.bindTexture(this.getArmorResource(entityLivingBaseIn, itemstack, slotIn, null));

                {
                    if (itemarmor.hasOverlay(itemstack)) // Allow this for anything, not only cloth
                    {
                        int i = itemarmor.getColor(itemstack);
                        float f = (float)(i >> 16 & 255) / 255.0F;
                        float f1 = (float)(i >> 8 & 255) / 255.0F;
                        float f2 = (float)(i & 255) / 255.0F;
                        GlStateManager.color(this.colorR * f, this.colorG * f1, this.colorB * f2, this.alpha);
                        if(itemarmor.getClass() != ItemArmor.class)
                        	t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, 0.0F, 0.0F, scale);
                        else
                        	t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                        this.renderer.bindTexture(this.getArmorResource(entityLivingBaseIn, itemstack, slotIn, "overlay"));
                    }
                    { // Non-colored
                        GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
                        if(itemarmor.getClass() != ItemArmor.class)
                        	t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, 0.0F, 0.0F, scale);
                        else
                        	t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    } // Default
                        if (!this.skipRenderGlint && itemstack.hasEffect())
                        {
                            renderEnchantedGlint(this.renderer, entityLivingBaseIn, t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                        }
                }
            }
        }
        GlStateManager.popMatrix();
    }
    
    @SuppressWarnings("incomplete-switch")
    protected void setModelSlotVisible(ModelBiped p_188359_1_, EntityEquipmentSlot slotIn)
    {
        this.setModelVisible(p_188359_1_);

        switch (slotIn)
        {
            case HEAD:
                p_188359_1_.bipedHead.showModel = true;
                p_188359_1_.bipedHeadwear.showModel = true;
                break;
            case CHEST:
                p_188359_1_.bipedBody.showModel = true;
                p_188359_1_.bipedRightArm.showModel = false;
                p_188359_1_.bipedLeftArm.showModel = false;
                break;
            case LEGS:
                p_188359_1_.bipedBody.showModel = true;
                p_188359_1_.bipedRightLeg.showModel = true;
                p_188359_1_.bipedLeftLeg.showModel = true;
                break;
            case FEET:
                p_188359_1_.bipedRightLeg.showModel = true;
                p_188359_1_.bipedLeftLeg.showModel = true;
        }
    }

    protected void setModelVisible(ModelBiped model)
    {
        model.setVisible(false);
    }

    @Override
    protected ModelBiped getArmorModelHook(net.minecraft.entity.EntityLivingBase entity, net.minecraft.item.ItemStack itemStack, EntityEquipmentSlot slot, ModelBiped model)
    {
        return net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
    }
}
