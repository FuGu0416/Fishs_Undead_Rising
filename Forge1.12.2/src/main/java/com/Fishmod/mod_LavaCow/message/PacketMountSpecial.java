package com.Fishmod.mod_LavaCow.message;

import java.util.Random;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.flying.EntityEnigmoth;
import com.Fishmod.mod_LavaCow.entities.flying.EntityVespa;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityMothScales;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;
import com.Fishmod.mod_LavaCow.init.FishItems;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMountSpecial implements IMessage, IMessageHandler<PacketMountSpecial, IMessage> {

    public int Id;
    private double posX;
    private double posY;
    private double posZ;
    
	public PacketMountSpecial() {
	}
	
	public PacketMountSpecial(int Id, double posX, double posY, double posZ) {		
		this.Id = Id;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}
	
	@Override
	public IMessage onMessage(PacketMountSpecial message, MessageContext ctx) {
		final EntityPlayerMP player = ctx.getServerHandler().player;
		Entity entity = player.world.getEntityByID(message.Id);
		Vec3d lookVec = entity.getLookVec();
		
		if (entity instanceof EntitySalamander) {
   	 	for(int i = 0; i < 8; i++) {
			EntitySmallFireball entityammo = new EntitySmallFireball(entity.world, (EntityLivingBase) entity, lookVec.x * (7.0D + new Random().nextGaussian() * 2.0D), lookVec.y * (-1.0D + new Random().nextGaussian() * 3.0D) - 0.25D, lookVec.z * (7.0D + new Random().nextGaussian() * 2.0D));
			entityammo.posX = message.posX + lookVec.x * 2.0D;
			entityammo.posY = message.posY + (double)(entity.height / 2.0F) + 1.5D;  
			entityammo.posZ = message.posZ + lookVec.z * 2.0D;
			entity.world.spawnEntity(entityammo);			
   	 	}
   	 	entity.world.playSound(null, message.posX, message.posY, message.posZ, FishItems.ENTITY_SALAMANDER_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F));
		} else if (entity instanceof EntityVespa) {
			((EntityVespa) entity).setAttackTimer(20);
			((EntityVespa) entity).abilityCooldown = ((EntityVespa) entity).abilityCooldown();
	   	 	entity.world.setEntityState(entity, (byte)4);
		} else if (entity instanceof EntityEnigmoth) {
	   	 	for (int i = 0; i < Modconfig.Enigmoth_Scale_Amount_Mount; i++) {
	   	 		
	   	 		EntityMothScales entityammo = new EntityMothScales(entity.world, (EntityLiving)entity, 0.0D, 0.0D, 0.0D);
	   	 		entityammo.setPosition(message.posX - entity.width + (entity.width * player.getRNG().nextDouble()), message.posY - (double)(entity.height / 2.0F), message.posZ - entity.width + (entity.width * player.getRNG().nextDouble()));		   	 			
	   	 		entity.world.spawnEntity(entityammo);	
	   	 		entityammo.setScaleType(((EntityEnigmoth) entity).getSkin());
	   	 	}
	   	 	entity.world.playSound(null, message.posX, message.posY, message.posZ, SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.PLAYERS, 1.0F, 1.0F);
	   	 	entity.world.playSound(null, message.posX, message.posY, message.posZ, FishItems.ENTITY_ENIGMOTH_SCALES, SoundCategory.PLAYERS, 1.0F, 1.0F);
	   	 	entity.world.setEntityState(entity, (byte)10);
	   	 	
       	 	if (((EntityEnigmoth) entity).getSkin() == 0) {
       	 		((EntityEnigmoth) entity).addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * 20, 0));
       	 		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 8 * 20, 0));
       	 	}
       	 	
       	 	if (((EntityEnigmoth) entity).getSkin() == 1) {
       	 		((EntityEnigmoth) entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 * 20, 0));
       	 		player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 8 * 20, 0));
       	 	}
	   	 	
       	 	if (((EntityEnigmoth) entity).getSkin() == 2) {
       	 		((EntityEnigmoth) entity).addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * 20, 0));
       	 		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 8 * 20, 0));
       	 	}
       	 	
	   	 	if (((EntityEnigmoth) entity).getSkinFixedTick() == 0) {
	   	 		((EntityEnigmoth) entity).setSkin(player.getRNG().nextInt(3));
	   	 	}
		}
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
        Id = buf.readInt();
        posX = buf.readDouble();
        posY = buf.readDouble();
        posZ = buf.readDouble();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
        buf.writeInt(Id);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
		
	}

}
