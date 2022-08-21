package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.model.entity.GraveRobberGhostModel;
import com.Fishmod.mod_LavaCow.entities.floating.GraveRobberGhostEntity;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GraveRobberGhostRenderer extends MobRenderer<GraveRobberGhostEntity, GraveRobberGhostModel<GraveRobberGhostEntity>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation("mod_lavacow:textures/mobs/graverobber/graverobber1.png");
	static{
        System.out.println(TEXTURE.getPath());
    }

    public GraveRobberGhostRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new GraveRobberGhostModel<GraveRobberGhostEntity>(), 0.0F);
    }
    
    @Override
    protected int getBlockLightLevel(GraveRobberGhostEntity p_225624_1_, BlockPos p_225624_2_) {
        return 15;
    }
    
    @Override
	public ResourceLocation getTextureLocation(GraveRobberGhostEntity entity) {
        return TEXTURE;
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(GraveRobberGhostEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
}