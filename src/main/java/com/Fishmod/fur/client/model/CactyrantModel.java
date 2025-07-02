package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.CactyrantEntity;

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
public class CactyrantModel extends GeoModel<CactyrantEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactyrant/cactyrant.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactyrant/cactyrant1.png")
	};
	
	private static final ResourceLocation[] TEXTURES_CAMO = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactyrant/cactyrant_camo.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactyrant/cactyrant1_camo.png")
	};
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/cactyrant.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/cactyrant.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(CactyrantEntity object) {
        return object.isCamouflaging() ? TEXTURES_CAMO[object.getSkin()] : TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(CactyrantEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(CactyrantEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(CactyrantEntity animatable, long instanceId, AnimationState<CactyrantEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("Head");
        CoreGeoBone Head_Flower0 = getAnimationProcessor().getBone("Head_Flower0");
        CoreGeoBone Limb0_Fruit0 = getAnimationProcessor().getBone("Limb0_Fruit0");
        CoreGeoBone Limb0_Fruit1 = getAnimationProcessor().getBone("Limb0_Fruit1");
        CoreGeoBone Limb0_Fruit2 = getAnimationProcessor().getBone("Limb0_Fruit2");
        CoreGeoBone Limb1_Fruit0 = getAnimationProcessor().getBone("Limb1_Fruit0");
        CoreGeoBone Limb1_Fruit1 = getAnimationProcessor().getBone("Limb1_Fruit1");
        
    	switch(animatable.getGrowingStage()) {
	    	case 0:
		    	Head_Flower0.setHidden(true);
		    	Limb0_Fruit0.setHidden(true);
		    	Limb0_Fruit1.setHidden(true);
		    	Limb0_Fruit2.setHidden(true);
		    	Limb1_Fruit0.setHidden(true);
		    	Limb1_Fruit1.setHidden(true);		    	
	    		break;
	    	case 1:
		    	Head_Flower0.setHidden(false);
		    	Limb0_Fruit0.setHidden(true);
		    	Limb0_Fruit1.setHidden(true);
		    	Limb0_Fruit2.setHidden(true);
		    	Limb1_Fruit0.setHidden(true);
		    	Limb1_Fruit1.setHidden(true);	
	    		break;
	    	case 2:
			default:
		    	Head_Flower0.setHidden(false);
		    	Limb0_Fruit0.setHidden(false);
		    	Limb0_Fruit1.setHidden(false);
		    	Limb0_Fruit2.setHidden(false);
		    	Limb1_Fruit0.setHidden(false);
		    	Limb1_Fruit1.setHidden(false);
				break;
		}
    	
        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(CactyrantEntity p_230496_1_, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(p_230496_1_));
    }
}
