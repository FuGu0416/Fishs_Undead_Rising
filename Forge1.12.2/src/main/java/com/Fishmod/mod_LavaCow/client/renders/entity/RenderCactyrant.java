package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.ModelCactyrant;
import com.Fishmod.mod_LavaCow.entities.EntityCactyrant;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCactyrant extends RenderLiving<EntityCactyrant> {
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/cactyrant/cactyrant_glow.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/cactyrant/cactyrant.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/cactyrant/cactyrant1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/cactyrant/cactyrant2.png")
	};
	private static final ResourceLocation[] TEXTURES_CAMO = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/cactyrant/cactyrant_camo.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/cactyrant/cactyrant1_camo.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/cactyrant/cactyrant2_camo.png")
	};

    public RenderCactyrant(RenderManager render)
    {
    	super(render, new ModelCactyrant(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityCactyrant entity)
    {
        return entity.isSilent() ? TEXTURES_CAMO[entity.getSkin()] : TEXTURES[entity.getSkin()];
    }
}