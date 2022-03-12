package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.entities.LavaCowEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LavaCowRenderer extends MobRenderer<LavaCowEntity, CowModel<LavaCowEntity>>  {
	private static ResourceLocation LAVACOW_TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/lavacow.png");
	static{
        System.out.println(LAVACOW_TEXTURES.getPath());
    }

    public LavaCowRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CowModel<LavaCowEntity>(), 0.7F);
    }
    
    protected int getBlockLightLevel(LavaCowEntity p_225624_1_, BlockPos p_225624_2_) {
        return 15;
    }
    
    @Override
	public ResourceLocation getTextureLocation(LavaCowEntity entity) {
        return LAVACOW_TEXTURES;
    }
}
