package com.Fishmod.mod_LavaCow.client.renders;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelFoglet;
import com.Fishmod.mod_LavaCow.entities.EntityFoglet;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFoglet extends RenderLiving<EntityFoglet> {
	private static ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/foglet_eyes.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/foglet/foglet.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/foglet/foglet2.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getResourcePath());
    }
	
    public RenderFoglet(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelFoglet(), 0.5F);
        this.addLayer(new LayerGenericGlowing(this, TEXTURES_EYE));
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityFoglet entity) {
		return TEXTURES[entity.getSkin()];
	}
}
