package com.Fishmod.mod_LavaCow.client.renderer.entity;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.model.entity.AvatonModel;
import com.Fishmod.mod_LavaCow.entities.floating.WraithEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WraithRenderer extends MobRenderer<WraithEntity, AvatonModel<WraithEntity>> {
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/wraith/wraith_glow.png");
	private static final ResourceLocation TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/wraith/wraith.png");
	static{
        System.out.println(TEXTURES.getPath());
    }

    public WraithRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new AvatonModel<>(), 0.0F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }
    
    @Override
    public ResourceLocation getTextureLocation(WraithEntity entity) {
    	return TEXTURES;
    }
    
    @Nullable
    @Override
    protected RenderType getRenderType(WraithEntity p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
    	return RenderType.entityTranslucent(this.getTextureLocation(p_230496_1_));
    }
    
    @Override
    protected int getBlockLightLevel(WraithEntity p_225624_1_, BlockPos p_225624_2_) {
        return 8;
    }
}
