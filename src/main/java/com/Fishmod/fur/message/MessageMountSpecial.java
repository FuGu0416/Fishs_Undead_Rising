package com.Fishmod.fur.message;

import java.util.Random;
import java.util.function.Supplier;

import com.Fishmod.fur.entities.tameable.SalamanderEntity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

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
	
	public static void serialize(final MessageMountSpecial message, final FriendlyByteBuf buf) {
        buf.writeInt(message.Id);
        buf.writeDouble(message.posX);
        buf.writeDouble(message.posY);
        buf.writeDouble(message.posZ);		
	}
	
	public static MessageMountSpecial deserialize(final FriendlyByteBuf buf) {
		final MessageMountSpecial message = new MessageMountSpecial();
		message.Id = buf.readInt();
		message.posX = buf.readDouble();
		message.posY = buf.readDouble();
		message.posZ = buf.readDouble();
		
        return message;
	}

	public static class Handler {
        public Handler() {
        }
        
		public static void handle(MessageMountSpecial message, Supplier<NetworkEvent.Context> context) {
			context.get().setPacketHandled(true);
			Player player = context.get().getSender();
			Entity entity = player.level().getEntity(message.Id);
			Vec3 lookVec = player.getLookAngle();
			
			if (entity instanceof SalamanderEntity) {
		   	 	for (int i = 0 ; i < 8 ; i++) {
		   	 		SmallFireball entityammo = new SmallFireball(entity.level(), (LivingEntity) entity, lookVec.x * (7.0D + new Random().nextGaussian() * 2.0D), lookVec.y * (-1.0D + new Random().nextGaussian() * 3.0D) - 0.25D, lookVec.z * (7.0D + new Random().nextGaussian() * 2.0D));
		   	 		entityammo.setPos(message.posX + lookVec.x * 2.0D, message.posY + (double)(entity.getBbHeight() / 2.0F) + 1.5D, message.posZ + lookVec.z * 2.0D);
					entity.level().addFreshEntity(entityammo);	
		   	 	}	
		   	 	entity.level().playSound(null, message.posX, message.posY, message.posZ, SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F));
			}/* else if (entity instanceof VespaEntity) {
				((VespaEntity) entity).setAttackTimer(20);
				((VespaEntity) entity).abilityCooldown = ((VespaEntity) entity).abilityCooldown();
				entity.level.broadcastEntityEvent(entity, (byte)4);					
			} else if (entity instanceof BeelzebubEntity) {
				((BeelzebubEntity) entity).abilityCooldown = ((BeelzebubEntity) entity).abilityCooldown();
				((BeelzebubEntity) entity).castSpell(FURConfig.Beelzebub_Ability_Num.get());
				entity.playSound(((BeelzebubEntity) entity).getSpellSound(), 0.175F, 1.0F);
				entity.level.broadcastEntityEvent(entity, (byte)10);					
			} else if (entity instanceof EnigmothEntity) {
		   	 	for (int i = 0 ; i < 5 ; i++) {
		   	 		MothScalesEntity entityammo = new MothScalesEntity(FUREntityRegistry.MOTH_SCALES, (LivingEntity)entity, 0.0D, 0.0D, 0.0D, entity.level);
		   	 		entityammo.setPos(message.posX - entity.getBbWidth() + (entity.getBbWidth() * player.getRandom().nextDouble()), message.posY - (double)(entity.getBbHeight() / 2.0F), message.posZ - entity.getBbWidth() + (entity.getBbWidth() * player.getRandom().nextDouble()));		   	 			
		   	 		entity.level.addFreshEntity(entityammo);	
		   	 		entityammo.setScaleType(((EnigmothEntity) entity).getSkin());
		   	 	}	
		   	 	entity.level.playSound(null, message.posX, message.posY, message.posZ, SoundEvents.EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F));
		   	 	entity.level.broadcastEntityEvent(entity, (byte)10);
		   	 	
	       	 	if (((EnigmothEntity) entity).getSkin() == 2) {
	       	 		((EnigmothEntity) entity).addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 20 * 20, 0));
	       	 		player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 20 * 20, 0));
	       	 	}
	       	 	
		   	 	if (((EnigmothEntity) entity).getSkinFixedTick() == 0) {
		   	 		((EnigmothEntity) entity).setSkin(player.getRandom().nextInt(3));
		   	 	}
			}*/
		}
	}


}
