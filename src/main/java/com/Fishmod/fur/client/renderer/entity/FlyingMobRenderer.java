package com.Fishmod.fur.client.renderer.entity;

import com.Fishmod.fur.entities.flying.FlyingMobEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/**
 * Shared base for every wild-flying-AI mob's renderer (Vespa, Ptera, Flarefly, Enigmoth, Beelzebub,
 * Void Glider). Adds a banked-turn visual roll on top of whatever the subclass already does, driven
 * by {@link FlyingMobEntity#getBankAngle()} (computed client-side from the entity's own recent yaw
 * change - see that method's javadoc). Purely cosmetic: doesn't touch the actual flight path, which
 * {@code FlyingMobEntity.FlyingMoveHelper} now turns gradually on its own.
 */
public abstract class FlyingMobRenderer<T extends FlyingMobEntity & GeoAnimatable> extends GeoEntityRenderer<T> {

	protected FlyingMobRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
		super(context, model);
	}

	@Override
	protected void applyRotations(T entity, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
		super.applyRotations(entity, poseStack, ageInTicks, rotationYaw, partialTicks);
		poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getBankAngle()));
	}
}
