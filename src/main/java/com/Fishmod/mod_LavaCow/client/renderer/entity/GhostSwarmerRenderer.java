package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.SwarmerModel;
import com.Fishmod.mod_LavaCow.entities.tameable.GhostSwarmerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class GhostSwarmerRenderer extends MobRenderer<GhostSwarmerEntity, SwarmerModel<GhostSwarmerEntity>> {	
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/piranha/zombiepiranha1.png"),
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public GhostSwarmerRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new SwarmerModel<GhostSwarmerEntity>(), 0.0F);
    }
    
    @Override
	public ResourceLocation getTextureLocation(GhostSwarmerEntity entity) {
        return TEXTURES[0];
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(GhostSwarmerEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
    
    @Override
    protected int getBlockLightLevel(GhostSwarmerEntity p_225624_1_, BlockPos p_225624_2_) {
        return 15;
    }
}
