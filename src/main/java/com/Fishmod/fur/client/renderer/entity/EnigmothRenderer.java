package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.client.layer.LayerEnigmoth;
import com.Fishmod.fur.client.layer.LayerSaddle;
import com.Fishmod.fur.client.model.EnigmothModel;
import com.Fishmod.fur.entities.flying.EnigmothEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class EnigmothRenderer extends GeoEntityRenderer<EnigmothEntity> {		
    public EnigmothRenderer(EntityRendererProvider.Context rendermanagerIn) {
    	super(rendermanagerIn, new EnigmothModel());
        this.shadowRadius = 1.0F;
        
        this.addRenderLayer(new LayerEnigmoth(this));
    	this.addRenderLayer(new LayerSaddle<>(this, new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/enigmoth/enigmoth_saddle.png")));
    }    
}
