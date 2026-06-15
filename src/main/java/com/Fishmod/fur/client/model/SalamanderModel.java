package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.SalamanderEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class SalamanderModel extends GeoModel<SalamanderEntity> {	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/salamander/salamander.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/salamander/salamander1.png")
	};
	
	private static final ResourceLocation[] TEXTURES_CHILD = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/salamander/salamander_nymph.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/salamander/salamander_nymph1.png")
	};
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/salamander.animation.json");
    private static final ResourceLocation ANIMATIONS_CHILD = new ResourceLocation(mod_LavaCow.MODID, "animations/salamander_nymph.animation.json");
    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/salamander.geo.json");
    private static final ResourceLocation MODEL_CHILD = new ResourceLocation(mod_LavaCow.MODID, "geo/salamander_nymph.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(SalamanderEntity object) {
        return object.isNymph() ? TEXTURES_CHILD[object.getSkin()] : TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(SalamanderEntity animatable) {
        return animatable.isNymph() ? ANIMATIONS_CHILD : ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(SalamanderEntity animatable) {
		return animatable.isNymph() ? MODEL_CHILD : MODEL;
	}
	
    @Override
    public void setCustomAnimations(SalamanderEntity animatable, long instanceId, AnimationState<SalamanderEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");
        CoreGeoBone tooth_l = getAnimationProcessor().getBone("Tooth_l");
        CoreGeoBone tooth_r = getAnimationProcessor().getBone("Tooth_r");
        CoreGeoBone cannon = getAnimationProcessor().getBone("CannonBase");
    	
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
        
        if (tooth_l != null && tooth_r != null && cannon != null) {
	        if (animatable.isBaby()) {
	    		if (animatable.getGrowingStage() == 1) {
	        		tooth_l.setHidden(true);
	        		tooth_r.setHidden(true);
	    		} else {
	        		tooth_l.setHidden(false);
	        		tooth_r.setHidden(false);
	    		}
	    		
	    		cannon.setHidden(true);
	    	} else {
	    		tooth_l.setHidden(false);
	    		tooth_r.setHidden(false);
	    		cannon.setHidden(false);
	    	}
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(SalamanderEntity entity, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(entity));
    }
}
