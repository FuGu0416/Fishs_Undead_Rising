package com.Fishmod.fur.mixin;

import com.Fishmod.fur.init.FUREffectRegistry;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Ghostly Armor spirit form: makes worn armor go semi-transparent alongside the body
 * ({@link LivingEntityRendererAlphaMixin}). Reported by the user as "only the body looks transparent, not
 * the armor" - correct, because armor is always drawn with {@code RenderType.armorCutoutNoCull}, a cutout
 * (alpha-test, no GL blend) type; a reduced alpha value has no visible effect on a non-blended draw, the
 * same root issue {@link LivingEntityRendererAlphaMixin} had to work around for the body. Fix here mirrors
 * that one: redirect the RenderType to a translucent-blend variant
 * ({@code RenderType.itemEntityTranslucentCull}, the same type vanilla itself uses for the "own invisible
 * body visible to self" case) and modify the alpha argument of the following {@code renderToBuffer} call,
 * both gated on the wearer having spirit form.
 *
 * <p>Confirmed against the compiled class (`javap -c -p` on the exact recomp jar matching the dev
 * environment's Forge version, {@code forge-1.20.1-47.3.7-recomp.jar}) rather than guessed from source:
 * both the {@code RenderType.armorCutoutNoCull} call and the {@code renderToBuffer} call it feeds live in
 * the private
 * {@code renderModel(PoseStack, MultiBufferSource, int, ArmorItem, Model, boolean, float, float, float,
 * ResourceLocation)} overload; the {@code renderToBuffer} receiver's declared type there is {@code Model}
 * directly (unlike {@link LivingEntityRendererAlphaMixin}'s case, where the field is declared
 * {@code EntityModel} - this parameter is literally typed {@code Model} in HumanoidArmorLayer's source).
 */
@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerAlphaMixin {

	private static final float SPIRIT_FORM_ALPHA = 0.35F;

	/** Set at the top of each render() call; same non-reentrancy reasoning as
	 *  {@link LivingEntityRendererAlphaMixin#fur_spiritForm}. */
	@Unique
	private boolean fur_spiritForm = false;

	@Inject(
		method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
		at = @At("HEAD")
	)
	private void fur_captureSpiritForm(PoseStack poseStack, MultiBufferSource buffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		this.fur_spiritForm = entity instanceof Player player && player.hasEffect(FUREffectRegistry.SPIRIT_FORM.get());
	}

	@Redirect(
		method = "renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/Model;ZFFFLnet/minecraft/resources/ResourceLocation;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/RenderType;armorCutoutNoCull(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"
		)
	)
	private RenderType fur_maybeTranslucentArmor(ResourceLocation texture) {
		return this.fur_spiritForm ? RenderType.itemEntityTranslucentCull(texture) : RenderType.armorCutoutNoCull(texture);
	}

	@ModifyArg(
		method = "renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/Model;ZFFFLnet/minecraft/resources/ResourceLocation;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/Model;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"
		),
		index = 7
	)
	private float fur_armorAlpha(float alpha) {
		return this.fur_spiritForm ? SPIRIT_FORM_ALPHA : alpha;
	}
}
