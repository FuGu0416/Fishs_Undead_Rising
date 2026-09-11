package com.Fishmod.fur.message;

import java.util.Random;
import java.util.function.Supplier;

import com.Fishmod.fur.entities.flying.BeelzebubEntity;
import com.Fishmod.fur.entities.flying.EnigmothEntity;
import com.Fishmod.fur.entities.flying.VespaEntity;
import com.Fishmod.fur.entities.projectiles.MoltenGlobEntity;
import com.Fishmod.fur.entities.projectiles.MothScalesEntity;
import com.Fishmod.fur.entities.tameable.SalamanderEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class MessageMountSpecial {
    public int Id;
    private double posX;
    private double posY;
    private double posZ;
    private double motionX;
    private double motionY;
    private double motionZ;

	public MessageMountSpecial() {
	}

	public MessageMountSpecial(int Id, double posX, double posY, double posZ) {
		this(Id, posX, posY, posZ, 0.0D, 0.0D, 0.0D);
	}

	public MessageMountSpecial(int Id, double posX, double posY, double posZ, double motionX, double motionY, double motionZ) {
		this.Id = Id;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
	}
	
	public static void serialize(final MessageMountSpecial message, final FriendlyByteBuf buf) {
        buf.writeInt(message.Id);
        buf.writeDouble(message.posX);
        buf.writeDouble(message.posY);
        buf.writeDouble(message.posZ);
        buf.writeDouble(message.motionX);
        buf.writeDouble(message.motionY);
        buf.writeDouble(message.motionZ);
	}
	
	public static MessageMountSpecial deserialize(final FriendlyByteBuf buf) {
		final MessageMountSpecial message = new MessageMountSpecial();
		message.Id = buf.readInt();
		message.posX = buf.readDouble();
		message.posY = buf.readDouble();
		message.posZ = buf.readDouble();
		message.motionX = buf.readDouble();
		message.motionY = buf.readDouble();
		message.motionZ = buf.readDouble();

        return message;
	}
       
	public static void handle(MessageMountSpecial message, Supplier<NetworkEvent.Context> context) {
		context.get().setPacketHandled(true);
		Player player = context.get().getSender();
		if (player == null) {
			return;
		}
		Entity entity = player.level().getEntity(message.Id);
		// The entity id is client-supplied — only accept the mount the sender is actually riding.
		if (entity == null || player.getVehicle() != entity) {
			return;
		}
		Vec3 lookVec = player.getLookAngle();

		if (entity instanceof SalamanderEntity salamander) {
			// Cooldown is authoritative here; the client-side check is just prediction.
			if (!salamander.isBarrageReady()) {
				return;
			}
			salamander.startBarrageCooldown();
			// Ridden special: lob a single Molten Glob along the rider's aim. The glob carries the
			// lizard's own gravity (yPower set in its EntityType ctor), so add a slight upward bias to
			// the launch velocity to counter the early drop and keep it tracking the crosshair.
			MoltenGlobEntity glob = FUREntityRegistry.MOLTEN_GLOB.get().create(entity.level());
			glob.setOwner(entity);
			glob.moveTo(salamander.getX() + lookVec.x * 2.0D, salamander.getY() + (double)(entity.getBbHeight() / 2.0F) + 1.5D, salamander.getZ() + lookVec.z * 2.0D, entity.getYRot(), entity.getXRot());
			double speed = 1.5D;
			glob.setDeltaMovement(lookVec.x * speed, lookVec.y * speed + 0.15D, lookVec.z * speed);
			glob.setFlame(true);
			entity.level().addFreshEntity(glob);
	   	 	entity.level().broadcastEntityEvent(entity, (byte)72);
	   	 	entity.level().playSound(null, salamander.getX(), salamander.getY(), salamander.getZ(), FURSoundRegistry.SALAMANDER_ATTACK_RANGE.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F));
		} else if (entity instanceof VespaEntity vespa) {
			vespa.abilityCooldown = vespa.abilityCooldown();
			entity.level().broadcastEntityEvent(entity, (byte)4);					
		} else if (entity instanceof BeelzebubEntity beelzebub) {
			// MOUNT_SPECIAL toggles the grab: release whatever is currently held, or otherwise try to
			// grab whatever is directly in front of Beelzebub. The client-side debounce is just
			// abilityCooldown() (see BeelzebubEntity) -- there's no separate server-side cooldown here.
			LivingEntity grabbed = beelzebub.getGrabbedPrey();
			if (grabbed != null) {
				grabbed.stopRiding();
			} else {
				// grab_blend plays on every grab attempt, whether or not anything ends up caught -- per
				// maintainer request, an empty snap still needs the same visual feedback a successful one
				// gets. Same clip the wild AIWildDevourGoal latch-on uses either way. Whether anything was
				// actually caught isn't decided here: the snap in the clip doesn't close until partway
				// through, so BeelzebubEntity#startGrabCheck() re-checks findGrabTarget() at that later
				// moment instead (see resolveGrabAttempt()) rather than whatever was in range right now.
				beelzebub.level().broadcastEntityEvent(beelzebub, (byte) 12);
				beelzebub.startGrabCheck();
			}
		} else if (entity instanceof EnigmothEntity) {
	   	 	// The ridden mount's server-side velocity is forced to zero (RidableFlyingMobEntity.travel zeroes
	   	 	// it off the controlling client), so use the real flight velocity captured client-side in the packet.
	   	 	Vec3 mountMotion = new Vec3(message.motionX, message.motionY, message.motionZ);
	   	 	double launchSpeed = 1.25D;
	   	 	for (int i = 0 ; i < 5 ; i++) {
	   	 		MothScalesEntity entityammo = new MothScalesEntity(FUREntityRegistry.MOTH_SCALES.get(), (LivingEntity)entity, lookVec.x, lookVec.y, lookVec.z, entity.level());
	   	 		// Spawn ~1 block ahead of the mount along the rider's aim (with a little scatter) so the
	   	 		// volley always clears the Enigmoth instead of dropping onto it when hovering/slow.
	   	 		entityammo.setPos(message.posX + lookVec.x + (player.getRandom().nextDouble() - 0.5D) * entity.getBbWidth(), message.posY + (double)(entity.getBbHeight() * 0.5F) + lookVec.y, message.posZ + lookVec.z + (player.getRandom().nextDouble() - 0.5D) * entity.getBbWidth());
	   	 		entity.level().addFreshEntity(entityammo);
	   	 		entityammo.setScaleType(((EnigmothEntity) entity).getSkin());
	   	 		// Launch along the rider's aim (so it leaves the mount) plus the mount's own forward momentum for inertia.
	   	 		entityammo.setDeltaMovement(lookVec.scale(launchSpeed).add(mountMotion));
	   	 	}
	   	 	entity.level().playSound(null, message.posX, message.posY, message.posZ, SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F));
	   	 	entity.level().broadcastEntityEvent(entity, (byte)10);
	   	 	
       	 	if (((EnigmothEntity) entity).getSkin() == 2) {
       	 		((EnigmothEntity) entity).addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 20, 0));
       	 		player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 20, 0));
       	 	}
       	 	
	   	 	if (((EnigmothEntity) entity).getSkinFixedTick() == 0) {
	   	 		((EnigmothEntity) entity).setSkin(player.getRandom().nextInt(3));
	   	 	}
		}
	}



}
