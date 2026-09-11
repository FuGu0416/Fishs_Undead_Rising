package com.Fishmod.fur.message;

import java.util.function.Supplier;

import com.Fishmod.fur.entities.flying.BeelzebubEntity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

/**
 * Sent when the rider left-clicks (Attack key) while riding a Beelzebub that has a prey grabbed in
 * its mouth -- bites the grabbed prey instead of the rider's normal attack. See
 * {@link com.Fishmod.fur.events.FURBeelzebubClientEvents} for the client-side key interception.
 */
public class MessageBeelzebubBite {
	public int mountId;

	public MessageBeelzebubBite() {
	}

	public MessageBeelzebubBite(int mountId) {
		this.mountId = mountId;
	}

	public static void serialize(final MessageBeelzebubBite message, final FriendlyByteBuf buf) {
		buf.writeInt(message.mountId);
	}

	public static MessageBeelzebubBite deserialize(final FriendlyByteBuf buf) {
		final MessageBeelzebubBite message = new MessageBeelzebubBite();
		message.mountId = buf.readInt();
		return message;
	}

	public static void handle(MessageBeelzebubBite message, Supplier<NetworkEvent.Context> context) {
		context.get().setPacketHandled(true);
		Player player = context.get().getSender();
		if (player == null) {
			return;
		}
		Entity entity = player.level().getEntity(message.mountId);
		// The entity id is client-supplied — only accept the mount the sender is actually riding.
		if (!(entity instanceof BeelzebubEntity beelzebub) || player.getVehicle() != beelzebub) {
			return;
		}

		LivingEntity grabbed = beelzebub.getGrabbedPrey();
		if (grabbed == null || !beelzebub.isBiteReady()) {
			return;
		}

		beelzebub.startBiteCooldown();
		float damage = (float) beelzebub.getAttributeValue(Attributes.ATTACK_DAMAGE);
		grabbed.hurt(beelzebub.damageSources().mobAttack(beelzebub), damage);
		beelzebub.heal(beelzebub.getMaxHealth() * 0.05F);
		beelzebub.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
		// attack_hold_blend: ridden left-click bite only -- the wild AI-driven bite has no one-shot pulse.
		beelzebub.level().broadcastEntityEvent(beelzebub, (byte) 13);
		// grabbed dying here is handled by vanilla's normal death flow (loot/xp, and it detaches
		// itself as a passenger during entity removal) -- nothing extra to do.
	}
}
