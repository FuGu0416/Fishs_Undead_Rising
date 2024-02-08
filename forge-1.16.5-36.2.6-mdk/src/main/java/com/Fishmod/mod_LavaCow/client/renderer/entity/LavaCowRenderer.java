package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.entities.LavaCowEntity;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LavaCowRenderer extends MobRenderer<LavaCowEntity, CowModel<LavaCowEntity>>  {
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/lavacow/lavacow_glow.png");
	private static final ResourceLocation LAVACOW_TEXTURES = new ResourceLocation("mod_lavacow:textures/mobs/lavacow/lavacow.png");
	
	static{
        System.out.println(LAVACOW_TEXTURES.getPath());
    }

    public LavaCowRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new CowModel<LavaCowEntity>(), 0.7F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
    }
    
    @Override
	public ResourceLocation getTextureLocation(LavaCowEntity entity) {
        return LAVACOW_TEXTURES;
    }
}
