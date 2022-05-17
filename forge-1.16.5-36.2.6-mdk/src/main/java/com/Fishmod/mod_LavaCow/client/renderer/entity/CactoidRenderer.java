package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.CactoidModel;
import com.Fishmod.mod_LavaCow.entities.tameable.CactoidEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CactoidRenderer extends MobRenderer<CactoidEntity, CactoidModel<CactoidEntity>> {	
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/cactoid/cactoid.png");
	private static final ResourceLocation TEXTURES_CAMO = new ResourceLocation("mod_lavacow:textures/mobs/cactoid/cactoid_camo.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

	public CactoidRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CactoidModel<>(), 0.25F);
    }
    
    @Override
    public ResourceLocation getTextureLocation(CactoidEntity entity) {
        return entity.isSilent() ? TEXTURES_CAMO : TEXTURES;
    }
}
