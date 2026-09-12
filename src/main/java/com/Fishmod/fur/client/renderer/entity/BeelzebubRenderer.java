package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.client.layer.LayerBeelzebubGland;
import com.Fishmod.fur.client.layer.LayerSaddle;
import com.Fishmod.fur.client.model.BeelzebubModel;
import com.Fishmod.fur.entities.flying.BeelzebubEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeelzebubRenderer extends FlyingMobRenderer<BeelzebubEntity> {

    public BeelzebubRenderer(EntityRendererProvider.Context rendermanagerIn) {
        super(rendermanagerIn, new BeelzebubModel());
        this.shadowRadius = 0.5F;
        this.addRenderLayer(new LayerSaddle<>(this, new ResourceLocation(mod_LavaCow.MODID, "textures/mobs/beelzebub/beelzebub_saddle.png")));
        this.addRenderLayer(new LayerBeelzebubGland<>(this));
    }
}
