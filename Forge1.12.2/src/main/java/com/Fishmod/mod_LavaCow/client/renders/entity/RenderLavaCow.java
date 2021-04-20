package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.entities.EntityLavaCow;

import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLavaCow extends RenderLiving<EntityLavaCow>  {
	private static ResourceLocation LAVACOW_TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/lavacow.png");
	static{
        System.out.println(LAVACOW_TEXTURES.getResourcePath());
    }
    //public static final Factory FACTORY = new Factory();

    public RenderLavaCow(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelCow(), 0.5F);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityLavaCow entity) {
        return LAVACOW_TEXTURES;
    }
    
    /*public static class Factory implements IRenderFactory<EntityLavaCow> {
    	
        @Override
        public Render<? super EntityLavaCow> createRenderFor(RenderManager manager) {
        	System.out.println("OAOAOAOAOAOAOAOAOAOAOAO123");
        	return new RenderLavaCow(manager);
        }

    }*/
}
