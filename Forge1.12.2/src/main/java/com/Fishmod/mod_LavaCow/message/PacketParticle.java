package com.Fishmod.mod_LavaCow.message;

import java.util.Random;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketParticle implements IMessage, IMessageHandler<PacketParticle, IMessage> {

	public byte particleType;
	public double posX;
	public double posY;
	public double posZ;

	public PacketParticle() {
	}
	
	public PacketParticle(EnumParticleTypes particleType, double posX, double posY, double posZ) {
		this.particleType = (byte) particleType.getParticleID();
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	public IMessage onMessage(PacketParticle message, MessageContext ctx) {
		WorldClient world = FMLClientHandler.instance().getWorldClient();
		
		if (world == null)
			return null;
		
		world.spawnParticle(EnumParticleTypes.getParticleFromId(message.particleType), false, message.posX, message.posY, message.posZ, new Random().nextGaussian() * 0.02D, new Random().nextGaussian() * 0.02D, new Random().nextGaussian() * 0.02D);
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		particleType = buf.readByte();
		posX = buf.readDouble();
		posY = buf.readDouble();
		posZ = buf.readDouble();		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(particleType);
		buf.writeDouble(posX).writeDouble(posY).writeDouble(posZ);
	}
}
