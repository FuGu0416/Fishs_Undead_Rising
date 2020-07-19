package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.layer.LayerUnburiedArmor;
import com.Fishmod.mod_LavaCow.client.layer.LayerZombieMushroom;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelUnburied;
import com.Fishmod.mod_LavaCow.entities.EntityZombieMushroom;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderZombieMushroom extends RenderLiving<EntityZombieMushroom> {
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied3_eyes.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied2.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/unburied/unburied3.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }
	
    public ModelUnburied getMainModel()
    {
        return (ModelUnburied)super.getMainModel();
    }

    public RenderZombieMushroom(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelUnburied(), 0.5F);
        this.addLayer(new LayerZombieMushroom(this));
        this.addLayer(new LayerGenericGlowing(this, TEXTURES_EYE));
        this.addLayer(new LayerGenericHeldItem(this, 0.0F, 0.15F, -0.6F, 1.0F));
        LayerUnburiedArmor layerbipedarmor = new LayerUnburiedArmor(this);
        /*{
            protected void initArmor()
            {
                this.modelLeggings = new ModelUnburiedArmor(0.5F);
                this.modelArmor = new ModelUnburiedArmor(1.0F);
            }
        };*/
        this.addLayer(layerbipedarmor);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(EntityZombieMushroom entity) {
    	return TEXTURES[entity.getSkin()];
    }
    
    @Override
	protected void preRenderCallback(EntityZombieMushroom entity, float partialTickTime) {
    	if(entity.isChild()) {
    		GlStateManager.scale(0.6F, 0.6F, 0.6F);
    	}
	}
}
