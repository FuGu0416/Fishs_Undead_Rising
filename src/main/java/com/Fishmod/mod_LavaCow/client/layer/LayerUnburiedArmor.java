package com.Fishmod.mod_LavaCow.client.layer;

import java.util.Map;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.UnburiedModel;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LayerUnburiedArmor<T extends LivingEntity, M extends UnburiedModel<T>> extends LayerRenderer<T, M> {
	private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
	private final BipedModel<T> ArmorModel = new BipedModel<>(1.0F);
	
    public LayerUnburiedArmor(IEntityRenderer<T, M> p_i226039_1_) {
        super(p_i226039_1_);
    }
    
    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        this.renderArmorPiece(p_225628_1_, p_225628_2_, p_225628_4_, EquipmentSlotType.CHEST, p_225628_3_, ArmorModel);
        this.renderArmorPiece(p_225628_1_, p_225628_2_, p_225628_4_, EquipmentSlotType.LEGS, p_225628_3_, ArmorModel);
        this.renderArmorPiece(p_225628_1_, p_225628_2_, p_225628_4_, EquipmentSlotType.FEET, p_225628_3_, ArmorModel);
        this.renderArmorPiece(p_225628_1_, p_225628_2_, p_225628_4_, EquipmentSlotType.HEAD, p_225628_3_, ArmorModel);
	}
    
	private void renderArmorPiece(MatrixStack matrixStackIn, IRenderTypeBuffer p_241739_2_, T entityIn, EquipmentSlotType slotIn, int p_241739_5_, BipedModel<T> p_241739_6_) {
		matrixStackIn.pushPose();
		ItemStack itemstack = entityIn.getItemBySlot(slotIn);
        if (itemstack.getItem() instanceof ArmorItem) {        	
        	ArmorItem armoritem = (ArmorItem)itemstack.getItem();
        	if (armoritem.getSlot() == slotIn) {
        		p_241739_6_ = getArmorModelHook(entityIn, itemstack, slotIn, p_241739_6_);
        		this.translateToSlot(matrixStackIn, slotIn);
        		this.getParentModel().copyPropertiesTo(p_241739_6_);              
        		this.setPartVisibility(p_241739_6_, slotIn);           
        		boolean flag1 = itemstack.hasFoil();
        		if (armoritem instanceof net.minecraft.item.IDyeableArmorItem) {
        			int i = ((net.minecraft.item.IDyeableArmorItem)armoritem).getColor(itemstack);
        			float f = (float)(i >> 16 & 255) / 255.0F;
        			float f1 = (float)(i >> 8 & 255) / 255.0F;
        			float f2 = (float)(i & 255) / 255.0F;
        			this.renderModel(matrixStackIn, p_241739_2_, p_241739_5_, flag1, p_241739_6_, f, f1, f2, this.getArmorResource(entityIn, itemstack, slotIn, null));
        			this.renderModel(matrixStackIn, p_241739_2_, p_241739_5_, flag1, p_241739_6_, 1.0F, 1.0F, 1.0F, this.getArmorResource(entityIn, itemstack, slotIn, "overlay"));
        		} else {
        			this.renderModel(matrixStackIn, p_241739_2_, p_241739_5_, flag1, p_241739_6_, 1.0F, 1.0F, 1.0F, this.getArmorResource(entityIn, itemstack, slotIn, null));
        		}
        	}
    	}
    	matrixStackIn.popPose();
	}
	
	private void translateToSlot(MatrixStack matrixStackIn, EquipmentSlotType slotIn) {
        switch (slotIn) {
	        case HEAD:
	        	this.getParentModel().Body_base.translateAndRotate(matrixStackIn);
	        	this.getParentModel().Body_waist.translateAndRotate(matrixStackIn);
	        	this.getParentModel().Body_chest.translateAndRotate(matrixStackIn);
	        	this.getParentModel().Neck0.translateAndRotate(matrixStackIn);
	        	this.getParentModel().Neck1.translateAndRotate(matrixStackIn);
	        	this.getParentModel().Head.translateAndRotate(matrixStackIn);
	            break;
	        case CHEST:
	        	this.getParentModel().Body_base.translateAndRotate(matrixStackIn);
	        	this.getParentModel().Body_waist.translateAndRotate(matrixStackIn);
	        	matrixStackIn.scale(1.1F, 1.1F, 1.1F);
	        	matrixStackIn.translate(0.0F, -0.15F, -0.1F);
	            break;
	        case LEGS:
	        	this.getParentModel().Body_base.translateAndRotate(matrixStackIn);
	        	matrixStackIn.translate(0.0F, -0.1F, 0.0F);
	        	matrixStackIn.scale(0.8F, 0.8F, 0.8F);
	            break;
	        case FEET:
	        	this.getParentModel().Body_base.translateAndRotate(matrixStackIn);
	        	matrixStackIn.translate(0.0F, 0.2F, -0.2F);
	        	matrixStackIn.scale(1.1F, 1.1F, 1.1F);
	        	matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(30.0F));
	            break;
			default:
				break;
	    }		
	}
    
    protected void setPartVisibility(BipedModel<T> p_188359_1_, EquipmentSlotType slotIn) {
    	p_188359_1_.setAllVisible(false);
        switch (slotIn) {
            case HEAD:
                p_188359_1_.head.visible = true;
                p_188359_1_.hat.visible = true;
                break;
            case CHEST:
                p_188359_1_.body.visible = true;
                break;
            case LEGS:
                p_188359_1_.rightLeg.visible = true;
                p_188359_1_.leftLeg.visible = true;
                break;
            case FEET:
                p_188359_1_.rightLeg.visible = true;
                p_188359_1_.leftLeg.visible = true;
                break;
			default:
				break;
        }
    }
    
    private void renderModel(MatrixStack p_241738_1_, IRenderTypeBuffer p_241738_2_, int p_241738_3_, boolean p_241738_5_, BipedModel<?> p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource) {
       IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_241738_2_, RenderType.armorCutoutNoCull(armorResource), false, p_241738_5_);
       p_241738_6_.renderToBuffer(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
    }
    
	private boolean usesInnerModel(EquipmentSlotType p_188363_1_) {
		return p_188363_1_ == EquipmentSlotType.LEGS;
	}
	
	protected BipedModel<T> getArmorModelHook(T entity, ItemStack itemStack, EquipmentSlotType slot, BipedModel<T> model) {
        return net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
	}
    
    /**
     * More generic ForgeHook version of the above function, it allows for Items to have more control over what texture they provide.
     *
     * @param entity Entity wearing the armor
     * @param stack ItemStack for the armor
     * @param slot Slot ID that the item is in
     * @param type Subtype, can be null or "overlay"
     * @return ResourceLocation pointing at the armor's texture
     */
    public ResourceLocation getArmorResource(net.minecraft.entity.Entity entity, ItemStack stack, EquipmentSlotType slot, @Nullable String type) {
       ArmorItem item = (ArmorItem)stack.getItem();
       String texture = item.getMaterial().getName();
       String domain = "minecraft";
       int idx = texture.indexOf(':');
       if (idx != -1) {
          domain = texture.substring(0, idx);
          texture = texture.substring(idx + 1);
       }
       String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (usesInnerModel(slot) ? 2 : 1), type == null ? "" : String.format("_%s", type));

       s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
       ResourceLocation resourcelocation = ARMOR_LOCATION_CACHE.get(s1);

       if (resourcelocation == null) {
          resourcelocation = new ResourceLocation(s1);
          ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
       }

       return resourcelocation;
    }    
}
