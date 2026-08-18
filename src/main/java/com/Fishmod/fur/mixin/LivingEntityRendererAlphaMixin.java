package com.Fishmod.fur.mixin;

import com.Fishmod.fur.init.FUREffectRegistry;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Ghostly Armor spirit form: renders the wearer semi-transparent for the duration.
 *
 * <p>This is a fresh implementation, not a reuse of the old Nightmare MobEffect's render-layer overlay
 * ({@code NightmareLayer}/{@code NightmareGeoLayer}/{@code FURClientEvents#onAddLayers}) - that whole
 * subsystem was deleted (see PLACEHOLDERS.md, 2026-07-15) specifically because non-player mobs' MobEffects
 * don't sync to observing clients in this setup, so the client-side {@code hasEffect} check the layers
 * relied on was always false for anyone but the mob's own (nonexistent) client. That failure mode is
 * specific to non-player entities; a player's own effects sync to trackers normally (this codebase
 * already relies on that elsewhere, e.g. {@code MoltenArmorItem#tickSoulSpeed}), so the same category of
 * bug shouldn't apply here - but this hasn't been confirmed by an actual multi-client test.
 *
 * <p>Vanilla has no built-in per-entity alpha hook for its model rendering (unlike GeckoLib's
 * {@code getRenderColor}, used for {@code WraithRenderer}'s translucency) - {@link LivingEntityRenderer#render}
 * hardcodes full opacity (or {@code 0.15F} for the "invisible but see-own-body-in-spectator" case) in its
 * one call to {@code EntityModel#renderToBuffer} (inherited from {@code Model}, but called through the
 * {@code model} field which is declared as {@code EntityModel} - see the {@code @At} target below). This
 * mixin captures "is this render call for a spirit-form player" at the top of {@code render()} into an
 * instance field, then substitutes the alpha argument of that one {@code renderToBuffer} call using the
 * captured flag.
 *
 * <p>First attempt targeted {@code Model} (where {@code renderToBuffer} is actually declared) as the
 * {@code @ModifyArg}'s owner and crashed the client at launch (`InjectionError: ... failed injection
 * check, (0/1) succeeded`) - the {@code @Inject} above it applied fine, only the {@code @ModifyArg}
 * failed. Confirmed the real owner by running {@code javap} against the exact recomp jar named in the
 * crash report (`forge-1.20.1-47.3.7-recomp.jar`): the invokevirtual's owner is
 * {@code net/minecraft/client/model/EntityModel} (the field's declared type), not {@code Model}. Fixed
 * below - confirmed by a clean `runClient` launch and play session (no crash report, clean shutdown).
 *
 * <p><b>Follow-up (reported by user as "transparency barely visible"):</b> the alpha value alone does
 * nothing on the RenderType {@code render()} normally selects for a visible, non-invisible entity
 * ({@code this.model.renderType(texture)}, typically a cutout/alpha-test type) - cutout types run with
 * GL blending disabled ({@code NO_TRANSPARENCY} render state), so a reduced alpha is not blended against
 * the background at all; it only matters for the alpha-test discard threshold (~0.1), which
 * {@link #SPIRIT_FORM_ALPHA} (0.35F) is safely above, so visually nothing changed. Vanilla's own
 * "invisible entity visible to its own spectator" case works around exactly this by swapping in a
 * translucent-blend RenderType ({@code RenderType.itemEntityTranslucentCull}) instead of just lowering
 * alpha - confirmed via {@code getRenderType(T, boolean, boolean, boolean)}'s source, where the 3rd
 * ("translucent") boolean argument controls that swap. Added a second {@code @ModifyArg} below that
 * forces that argument true for spirit-form players, so the alpha value above now actually blends.
 * (Armor doesn't go through this class's RenderType at all - see the matching fix in
 * {@code HumanoidArmorLayerAlphaMixin}.)
 */
@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererAlphaMixin {

	private static final float SPIRIT_FORM_ALPHA = 0.35F;

	/** Set at the top of each render() call, read by {@link #fur_spiritFormAlpha}. Renderer instances
	 *  are shared but render() calls are sequential on the render thread, never reentrant into the same
	 *  instance, so a plain instance field is safe here. */
	@Unique
	private boolean fur_spiritForm = false;

	@Inject(
		method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
		at = @At("HEAD")
	)
	private void fur_captureSpiritForm(LivingEntity entity, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light, CallbackInfo ci) {
		this.fur_spiritForm = entity instanceof Player player && player.hasEffect(FUREffectRegistry.SPIRIT_FORM.get());
	}

	@ModifyArg(
		method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
		at = @At(
			value = "INVOKE",
			// Owner is EntityModel, not Model where renderToBuffer is actually declared - javac emits
			// the invokevirtual against the field's own declared type (LivingEntityRenderer#model is
			// typed EntityModel), not the method's declaring class. Confirmed by javap against the exact
			// recomp jar named in the crash report (forge-1.20.1-47.3.7): the original guess of `Model`
			// as owner scanned a target but failed the argument-injection check (0/1 succeeded).
			target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"
		),
		index = 7
	)
	private float fur_spiritFormAlpha(float alpha) {
		return this.fur_spiritForm ? SPIRIT_FORM_ALPHA : alpha;
	}

	/**
	 * Forces the "translucent" argument of {@code getRenderType} to {@code true} for spirit-form players,
	 * so {@code render()} selects {@code RenderType.itemEntityTranslucentCull} (a real alpha-blend type)
	 * instead of the normal opaque/cutout type - without this, {@link #fur_spiritFormAlpha} has nothing to
	 * visibly affect. Owner confirmed via javap: the call is {@code this.getRenderType(...)} from within
	 * {@code LivingEntityRenderer} itself, so the invokevirtual owner is this class, not a field type.
	 */
	@ModifyArg(
		method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/world/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;"
		),
		index = 2
	)
	private boolean fur_forceTranslucentRenderType(boolean translucent) {
		return translucent || this.fur_spiritForm;
	}
}
