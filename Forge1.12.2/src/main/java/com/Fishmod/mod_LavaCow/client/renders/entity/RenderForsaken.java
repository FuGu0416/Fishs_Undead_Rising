package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerForsakenArmor;
import com.Fishmod.mod_LavaCow.entities.EntityForsaken;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderForsaken extends RenderSkeleton {
    private static final ResourceLocation TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");

    public RenderForsaken(RenderManager p_i47191_1_)
    {
        super(p_i47191_1_);
        this.addLayer(new LayerForsakenArmor(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(AbstractSkeleton entity)
    {
        return TEXTURES;
    }
    
    @Override
	protected void preRenderCallback(AbstractSkeleton entity, float partialTickTime) {
    	if(entity.isChild()) {
    		GlStateManager.scale(0.6F, 0.6F, 0.6F);
    	}
    	
    	GlStateManager.translate(0.0D, (double)((EntityForsaken)entity).getRisingTicks() / 20.0D, 0.0D);
	}
}
