package com.Fishmod.fur.mixin;

import com.Fishmod.fur.init.FUREffectRegistry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Ghostly Armor spirit form: lets the wearer pass through other entities (no push-jostle in either
 * direction) while leaving ordinary block collision completely untouched.
 *
 * <p>{@code Entity#noPhysics} was not usable for this — it also short-circuits {@code Entity#move}
 * into a full noclip against blocks, which the spec explicitly rules out. Ordinary mobs never hard-block
 * movement to begin with ({@code Entity#canBeCollidedWith} defaults to {@code false}; only a few special
 * entities like boats override it) — the only thing actually stopping the wearer from walking straight
 * through a mob is the small mutual nudge in {@code Entity#push(Entity)}. Cancelling that call from
 * either side (the wearer pushing a mob, or a mob pushing the wearer) is therefore a complete fix,
 * scoped to exactly the "entity collision" the spec asks for.
 */
@Mixin(Entity.class)
public class EntityPushMixin {

	@Inject(method = "push(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
	private void fur_skipPushDuringSpiritForm(Entity other, CallbackInfo ci) {
		Entity self = (Entity) (Object) this;
		if (isSpiritForm(self) || isSpiritForm(other)) {
			ci.cancel();
		}
	}

	private static boolean isSpiritForm(Entity entity) {
		return entity instanceof Player player && player.hasEffect(FUREffectRegistry.SPIRIT_FORM.get());
	}
}
