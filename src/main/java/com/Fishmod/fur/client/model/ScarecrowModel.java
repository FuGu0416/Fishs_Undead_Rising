package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.ScarecrowEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class ScarecrowModel extends GeoModel<ScarecrowEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/scarecrow/scarecrow.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/scarecrow/scarecrow1.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/scarecrow/scarecrow2.png")
	};	
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/scarecrow.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/scarecrow.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(ScarecrowEntity object) {
        return TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(ScarecrowEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(ScarecrowEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(ScarecrowEntity animatable, long instanceId, AnimationState<ScarecrowEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("neck");
        CoreGeoBone head1 = getAnimationProcessor().getBone("head1");
        CoreGeoBone head2 = getAnimationProcessor().getBone("neck1");
        CoreGeoBone scythe = getAnimationProcessor().getBone("scepter_base");
     
        int variant = animatable.getSkin();
        
        switch (variant) {
        	case 0 :       	
        		head.setHidden(false);
        		head1.setHidden(true);
        		head2.setHidden(true);
    			break;
        	case 1 :
        		head.setHidden(true);
        		head1.setHidden(false);
        		head2.setHidden(true);
        		break;
        	case 2 :
        		head.setHidden(true);
        		head1.setHidden(true);
        		head2.setHidden(false);
         		break;   
        	default :
        		break;
	    }
	    	        
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (animationState.isCurrentAnimation(ScarecrowEntity.IDLE)) {
	        if (head != null) {
	            head.setRotX(-24.56F + (entityData.headPitch() * Mth.DEG_TO_RAD));
	            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
	        }
	        
	        if (head1 != null) {
	            head1.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
	            head1.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
	        }
	        
	        if (head2 != null) {
	            head2.setRotX(-62.0F + (entityData.headPitch() * Mth.DEG_TO_RAD));
	            head2.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
	        }
        }
        
        if ((animatable.hasItemInSlot(EquipmentSlot.MAINHAND) || animatable.hasItemInSlot(EquipmentSlot.OFFHAND))) {
        	if (!scythe.isHidden()) {
        		scythe.setHidden(true);
        	}
        } else if (scythe.isHidden()) {
    		scythe.setHidden(false);     	
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(ScarecrowEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
