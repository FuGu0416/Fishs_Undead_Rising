package com.Fishmod.mod_LavaCow.client.renders.entity;

import com.Fishmod.mod_LavaCow.client.model.entity.ModelCactoid;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityCactoid;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCactoid extends RenderLiving<EntityCactoid>
{
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/cactoid/cactoid.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/cactoid/cactoid1.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/cactoid/cactoid2.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/cactoid/cactoid3.png")
	};
	private static final ResourceLocation[] TEXTURES_CAMO = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/cactoid/cactoid_camo.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/cactoid/cactoid1_camo.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/cactoid/cactoid2_camo.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/cactoid/cactoid3_camo.png")
	};

    public RenderCactoid(RenderManager render)
    {
    	super(render, new ModelCactoid(), 0.25F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityCactoid entity)
    {
        return entity.isSilent() ? TEXTURES_CAMO[entity.getSkin()] : TEXTURES[entity.getSkin()];
    }
}