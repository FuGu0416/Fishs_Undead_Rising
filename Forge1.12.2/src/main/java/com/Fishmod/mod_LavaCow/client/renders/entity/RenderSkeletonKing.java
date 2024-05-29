package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelSkeletonKing;
import com.Fishmod.mod_LavaCow.entities.EntitySkeletonKing;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSkeletonKing extends RenderLiving<EntitySkeletonKing> {
	private static ResourceLocation TEXTURES_GLOW = new ResourceLocation("mod_lavacow:textures/mobs/skeletonking/skeletonking_glow.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/skeletonking/skeletonking.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/skeletonking/skeletonking_angry.png"),
	};
	
	
	static {
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

	public RenderSkeletonKing(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelSkeletonKing(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_GLOW));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntitySkeletonKing entity) {
        return entity.isAngered() ? TEXTURES[1] : TEXTURES[0];
    }
    
    @Override
	protected void preRenderCallback(EntitySkeletonKing entity, float partialTickTime) {
	}
}
