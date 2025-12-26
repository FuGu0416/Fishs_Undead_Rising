package com.Fishmod.fur.client.model.layer;

import java.util.Calendar;

import com.Fishmod.fur.client.model.MimicModel;
import com.Fishmod.fur.entities.tameable.MimicEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class LayerMimicChest<T extends MimicEntity> extends GeoRenderLayer<T> {
    private static final ResourceLocation TEXTURE_CHEST_ENDER = new ResourceLocation("textures/entity/chest/ender.png");
    private static final ResourceLocation TEXTURE_CHEST_XMAS = new ResourceLocation("textures/entity/chest/christmas.png");
    private static final ResourceLocation TEXTURE_CHEST = new ResourceLocation("textures/entity/chest/normal.png");
    private ResourceLocation textureLocation;
    private boolean xmasTextures = false;
    
    public LayerMimicChest(GeoRenderer<T> renderer) {
        super(renderer);
        
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
        	this.xmasTextures = true;
        }
    }
    
    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if (animatable.getSkin() == MimicModel.getVoidSkin()) {
            this.textureLocation = TEXTURE_CHEST_ENDER;
        } else if (this.xmasTextures) {
        	this.textureLocation = TEXTURE_CHEST_XMAS;
        } else {
            String chestTexture = animatable.getChestTexture();

            // In the event that compatibility is no longer enabled (or the mod was removed), we reset the chest texture.
            if (!MimicEntity.TEXTURE_POOL.contains(chestTexture)) {
            	this.textureLocation = TEXTURE_CHEST;
            }

            this.textureLocation = new ResourceLocation(chestTexture);
        }

        RenderType RenderType = net.minecraft.client.renderer.RenderType.entityCutoutNoCull(this.textureLocation);        
		getRenderer().reRender(bakedModel, poseStack, bufferSource, animatable, RenderType,
				   bufferSource.getBuffer(RenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
				   1, 1, 1, 1);
	}
}
