package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelZombiePiranha;
import com.Fishmod.mod_LavaCow.entities.floating.EntityGhostSwarmer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderGhostSwarmer extends RenderLiving<EntityGhostSwarmer> {
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/ghostswarmer/ghostswarmer_glow.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/ghostswarmer/ghostswarmer.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/ghostswarmer/ghostswarmer1.png"),
	};
	
	static {
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }

    public RenderGhostSwarmer(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelZombiePiranha(), 0.3F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityGhostSwarmer entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    protected void applyRotations(EntityGhostSwarmer entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        float f = 4.3F * MathHelper.sin(0.6F * ageInTicks);
        GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);   
     }
    
    @Override
    protected void preRenderCallback(EntityGhostSwarmer entity, float partialTickTime) {
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1F, 1F, 1F, 0.85F);
	}
}
