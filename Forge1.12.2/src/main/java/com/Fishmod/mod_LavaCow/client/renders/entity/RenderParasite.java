package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelParasite;
import com.Fishmod.mod_LavaCow.entities.EntityParasite;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderParasite extends RenderLiving<EntityParasite>{
	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/parasite/parasite.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/parasite/parasite1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/parasite/parasite2.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }
	
    public RenderParasite(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelParasite(), 0.3F);
    }
    
    @Override
	protected float getDeathMaxRotation(EntityParasite entity) {
		return 180F;
	}
    
    @Override
    protected ResourceLocation getEntityTexture(EntityParasite entity) {
        return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityParasite entity, float partialTickTime) {  	
    	if (entity.getRidingEntity() != null) {
    		GlStateManager.scale(1.2F, 1.2F, 1.2F);
			GlStateManager.translate(0.0F, 0.0F, -0.4F);
			GlStateManager.rotate(180F, 0F, 1F, 0F);
			if((entity.getRidingEntity() instanceof EntityPlayer || entity.getRidingEntity() instanceof EntityZombie || entity.getRidingEntity() instanceof EntityVillager || entity.getRidingEntity() instanceof AbstractIllager || entity.getRidingEntity() instanceof EntitySkeleton) && !((EntityLivingBase)entity.getRidingEntity()).isChild()) {
				if(!(entity.getRidingEntity() instanceof EntityPlayer))GlStateManager.translate(0.0F, 0.3F, -0.3F);
				GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				
			}
			//GlStateManager.rotatef(180F, 0F, 1F, 0F);	
		}    	
    	/*else if (entity.isEvolving) {
    		float f = 0.012F * (float)MathHelper.abs(100 - entity.lifespawn);
    		GlStateManager.scale(1.0F + f, 1.0F + f, 1.0F + f);
    	}*/
	}
}
