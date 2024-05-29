package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelZombiePiranha;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityPiranhaLauncher;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPiranhaLauncher extends Render<EntityPiranhaLauncher>{
	
	private final ModelZombiePiranha skeletonHeadModel = new ModelZombiePiranha();
	private static ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/swarmer/swarmer.png");
	static{
        System.out.println(TEXTURES.getResourcePath());
    }

    public RenderPiranhaLauncher(RenderManager rendermanagerIn) {
        super(rendermanagerIn);
    }
    
    private float getRenderYaw(float p_82400_1_, float p_82400_2_, float p_82400_3_)
    {
        float f;

        for (f = p_82400_2_ - p_82400_1_; f < -180.0F; f += 360.0F)
        {
            ;
        }

        while (f >= 180.0F)
        {
            f -= 360.0F;
        }

        return p_82400_1_ + p_82400_3_ * f;
    }
    
    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityPiranhaLauncher entity, double x, double y, double z, float entityYaw, float partialTicks)
    {	
    	GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        float f = this.getRenderYaw(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
        float f1 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
        //System.out.println("=3= " + entity.getSkin());
        GlStateManager.translate((float)x, (float)y + 0.8F, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.05F, 0.05F, 0.05F);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entity);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }
        
        this.skeletonHeadModel.render(entity, 0.0F, 0.0F, 0.0F, f, f1, 0.5F);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityPiranhaLauncher entity) {
		return TEXTURES;
	}
}
