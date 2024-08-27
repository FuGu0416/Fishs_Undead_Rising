package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.config.FURConfig;
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
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/moogma/moogma_glow.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/moogma/moogma.png"),
			new ResourceLocation("mod_lavacow:textures/mobs/moogma/moogma1.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }

    public LavaCowRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CowModel<LavaCowEntity>(), 0.7F);
        
        if (!FURConfig.Lavacow_Texture.get()) {
        	this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
        }
    }
    
    @Override
	public ResourceLocation getTextureLocation(LavaCowEntity entity) {
        return TEXTURES[FURConfig.Lavacow_Texture.get() ? 0 : 1];
    }
    
    @Override
    protected int getBlockLightLevel(LavaCowEntity p_225624_1_, BlockPos p_225624_2_) {
        return FURConfig.Lavacow_Texture.get() ? 8 : super.getBlockLightLevel(p_225624_1_, p_225624_2_);
    }
}
