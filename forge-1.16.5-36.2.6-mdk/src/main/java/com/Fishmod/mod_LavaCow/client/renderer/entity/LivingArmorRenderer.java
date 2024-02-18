package com.Fishmod.mod_LavaCow.client.renderer.entity;

import com.Fishmod.mod_LavaCow.client.layer.LayerGenericGlowing;
import com.Fishmod.mod_LavaCow.client.layer.LayerGenericHeldItem;
import com.Fishmod.mod_LavaCow.client.model.entity.LivingArmorModel;
import com.Fishmod.mod_LavaCow.entities.LivingArmorEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LivingArmorRenderer extends MobRenderer<LivingArmorEntity, LivingArmorModel<LivingArmorEntity>> {
	private static final ResourceLocation TEXTURES_EYE = new ResourceLocation("mod_lavacow:textures/mobs/living_armor/living_armor_eyes.png");
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[] {
			new ResourceLocation("mod_lavacow:textures/mobs/living_armor/living_armor.png")
	};
	
	static{
		for(ResourceLocation texture: TEXTURES)
			System.out.println(texture.getPath());
    }
	
    public LivingArmorRenderer(EntityRendererManager rendermanagerIn) {
        super(rendermanagerIn, new LivingArmorModel<LivingArmorEntity>(), 0.5F);
        this.addLayer(new LayerGenericGlowing<>(this, TEXTURES_EYE));
        this.addLayer(new LayerGenericHeldItem<>(this, 0.1F, 0.15F, -0.4F, 1.2F));
    }

	@Override
	public ResourceLocation getTextureLocation(LivingArmorEntity entity) {
		return TEXTURES[entity.getSkin()];
	}
}
