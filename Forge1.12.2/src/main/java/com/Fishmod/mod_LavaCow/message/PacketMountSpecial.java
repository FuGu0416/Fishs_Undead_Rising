package com.Fishmod.mod_LavaCow.message;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
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
   	 	for(int i = 0 ; i < 8 ; i++) {
			EntitySmallFireball entityammo = new EntitySmallFireball(entity.world, (EntityLivingBase) entity, lookVec.x * (7.0D + new Random().nextGaussian() * 2.0D), lookVec.y * (-1.0D + new Random().nextGaussian() * 3.0D) - 0.25D, lookVec.z * (7.0D + new Random().nextGaussian() * 2.0D));
			entityammo.posX = message.posX + lookVec.x * 2.0D;
			entityammo.posY = message.posY + (double)(entity.height / 2.0F) + 1.5D;  
			entityammo.posZ = message.posZ + lookVec.z * 2.0D;
			entity.world.spawnEntity(entityammo);
			entity.world.playSound(null, message.posX, message.posY, message.posZ, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (new Random().nextFloat() * 0.4F + 1.2F));
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
