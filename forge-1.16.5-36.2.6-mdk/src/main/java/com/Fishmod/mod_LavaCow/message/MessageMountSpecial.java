package com.Fishmod.mod_LavaCow.message;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.Fishmod.mod_LavaCow.entities.flying.BeelzebubEntity;
import com.Fishmod.mod_LavaCow.entities.flying.EnigmothEntity;
import com.Fishmod.mod_LavaCow.entities.flying.VespaEntity;
import com.Fishmod.mod_LavaCow.entities.projectiles.MothScalesEntity;
import com.Fishmod.mod_LavaCow.entities.tameable.SalamanderEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageMountSpecial {
    public int Id;
    private double posX;
    private double posY;
    private double posZ;
    
	public MessageMountSpecial() {
	}
	
	public MessageMountSpecial(int Id, double posX, double posY, double posZ) {		
		this.Id = Id;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}
	
	public static void serialize(final MessageMountSpecial message, final PacketBuffer buf) {
        buf.writeInt(message.Id);
        buf.writeDouble(message.posX);
        buf.writeDouble(message.posY);
        buf.writeDouble(message.posZ);		
	}
	
	public static MessageMountSpecial deserialize(final PacketBuffer buf) {
		final MessageMountSpecial message = new MessageMountSpecial();
		message.Id = buf.readInt();
		message.posX = buf.readDouble();
		message.posY = buf.readDouble();
		message.posZ = buf.readDouble();
		
        return message;
	}

	public static class Handler implements BiConsumer<MessageMountSpecial, Supplier<NetworkEvent.Context>> {
		@Override
		public void accept(MessageMountSpecial message, Supplier<Context> context) {
			context.get().setPacketHandled(true);
			PlayerEntity player = context.get().getSender();
			Entity entity = player.level.getEntity(message.Id);
			Vector3d lookVec = player.getLookAngle();
			
			if (entity instanceof SalamanderEntity) {
		   	 	for (int i = 0 ; i < 8 ; i++) {
		   	 		SmallFireballEntity entityammo = new SmallFireballEntity(entity.level, (LivingEntity) entity, lookVec.x * (7.0D + new Random().nextGaussian() * 2.0D), lookVec.y * (-1.0D + new Random().nextGaussian() * 3.0D) - 0.25D, lookVec.z * (7.0D + new Random().nextGaussian() * 2.0D));
		   	 		entityammo.setPos(message.posX + lookVec.x * 2.0D, message.posY + (double)(entity.getBbHeight() / 2.0F) + 1.5D, message.posZ + lookVec.z * 2.0D);
					entity.level.addFreshEntity(entityammo);	
		   	 	}	
		   	 	entity.level.playSound(null, message.posX, message.posY, message.posZ, SoundEvents.BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F));
			} else if (entity instanceof VespaEntity) {
				((VespaEntity) entity).setAttackTimer(20);
				((VespaEntity) entity).abilityCooldown = ((VespaEntity) entity).abilityCooldown();
				entity.level.broadcastEntityEvent(entity, (byte)4);					
			} else if (entity instanceof BeelzebubEntity) {
				((BeelzebubEntity) entity).abilityCooldown = ((BeelzebubEntity) entity).abilityCooldown();
				((BeelzebubEntity) entity).castSpell();
				entity.playSound(((BeelzebubEntity) entity).getSpellSound(), 0.175F, 1.0F);
				entity.level.broadcastEntityEvent(entity, (byte)10);					
			} else if (entity instanceof EnigmothEntity) {
		   	 	for (int i = 0 ; i < 5 ; i++) {
		   	 		MothScalesEntity entityammo = new MothScalesEntity(FUREntityRegistry.MOTH_SCALES, (LivingEntity)entity, 0.0D, 0.0D, 0.0D, entity.level);
		   	 		entityammo.setPos(message.posX - entity.getBbWidth() + (entity.getBbWidth() * player.getRandom().nextDouble()), message.posY - (double)(entity.getBbHeight() / 2.0F), message.posZ - entity.getBbWidth() + (entity.getBbWidth() * player.getRandom().nextDouble()));
		   	 		entityammo.scaleType = ((EnigmothEntity) entity).getSkin();
		   	 		entity.level.addFreshEntity(entityammo);	
		   	 	}	
		   	 	entity.level.playSound(null, message.posX, message.posY, message.posZ, SoundEvents.EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F));
		   	 	((EnigmothEntity) entity).setSkin(player.getRandom().nextInt(3));
			}
		}
	}


}
