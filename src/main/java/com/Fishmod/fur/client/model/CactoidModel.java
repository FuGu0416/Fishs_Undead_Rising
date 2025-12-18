package com.Fishmod.fur.client.model;

import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.tameable.CactoidEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

/**
 * ModelZombie - Either Mojang or a mod author
 * Created using Tabula 7.0.1
 */
public class CactoidModel extends GeoModel<CactoidEntity> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactoid/cactoid.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactoid/cactoid1.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactoid/cactoid2.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactoid/cactoid3.png")
	};	
	
	private static final ResourceLocation[] TEXTURES_CAMO = new ResourceLocation[] {
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactoid/cactoid_camo.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactoid/cactoid1_camo.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactoid/cactoid2_camo.png"),
			new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/cactoid/cactoid3_camo.png")
	};
	
    private static final ResourceLocation ANIMATIONS = new ResourceLocation(mod_LavaCow.MODID, "animations/cactoid.animation.json");

    private static final ResourceLocation MODEL = new ResourceLocation(mod_LavaCow.MODID, "geo/cactoid.geo.json");
    
    @Override
    public ResourceLocation getTextureResource(CactoidEntity object) {
        return object.isSilent() ? TEXTURES_CAMO[object.getSkin()] : TEXTURES[object.getSkin()];
    }

    @Override
    public ResourceLocation getAnimationResource(CactoidEntity animatable) {
        return ANIMATIONS;
    }

	@Override
	public ResourceLocation getModelResource(CactoidEntity animatable) {
		return MODEL;
	}
	
    @Override
    public void setCustomAnimations(CactoidEntity animatable, long instanceId, AnimationState<CactoidEntity> animationState) {
        CoreGeoBone skin0 = getAnimationProcessor().getBone("skin0");
        CoreGeoBone skin1 = getAnimationProcessor().getBone("skin1");
        CoreGeoBone skin2 = getAnimationProcessor().getBone("skin2");
        CoreGeoBone skin3 = getAnimationProcessor().getBone("skin3");
     
        int variant = animatable.getSkin();
        
        switch (variant) {
        	case 0 :       	
        		skin0.setHidden(false);
        		skin1.setHidden(true);
        		skin2.setHidden(true);
        		skin3.setHidden(true);
    			break;
        	case 1 :
        		skin0.setHidden(true);
        		skin1.setHidden(false);
        		skin2.setHidden(true);
        		skin3.setHidden(true);
        		break;
        	case 2 :
        		skin0.setHidden(true);
        		skin1.setHidden(true);
        		skin2.setHidden(false);
        		skin3.setHidden(true);
         		break;  
        	case 3 :
        		skin0.setHidden(true);
        		skin1.setHidden(true);
        		skin2.setHidden(true);
        		skin3.setHidden(false);
        		break;
        	default :
        		break;
	    }
    }
    
    @Nullable
    @Override
	public RenderType getRenderType(CactoidEntity p_230496_1_, ResourceLocation texture) {
    	return RenderType.entityTranslucent(this.getTextureResource(p_230496_1_));
    }
}
