package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.layer.LayerUnburiedArmor;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelUnburied;
import com.Fishmod.mod_LavaCow.entities.EntityMummy;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMummy extends RenderLiving<EntityMummy> {
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied4.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }
	
    public ModelUnburied getMainModel()
    {
        return (ModelUnburied)super.getMainModel();
    }

    public RenderMummy(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelUnburied(), 0.5F);

        this.addLayer(new LayerGenericHeldItem<>(this, 0.0F, 0.15F, -0.6F, 1.0F));
        LayerUnburiedArmor layerbipedarmor = new LayerUnburiedArmor(this);

        this.addLayer(layerbipedarmor);
    }
    
	@Override
	protected ResourceLocation getEntityTexture(EntityMummy entity) {
		return TEXTURES[entity.getSkin()];
	}
   
    @Override
	protected void preRenderCallback(EntityMummy entity, float partialTickTime) {
    	if(entity.isChild()) {
    		GlStateManager.scale(0.6F, 0.6F, 0.6F);
    	}
	}


}
