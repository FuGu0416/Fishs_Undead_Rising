package com.Fishmod.mod_LavaCow.proxy;

import com.Fishmod.mod_LavaCow.client.particle.ParticalLocustSwarm;
import com.Fishmod.mod_LavaCow.client.renders.RenderFactories;
import com.Fishmod.mod_LavaCow.client.renders.item.RenderVespaShield;
import com.Fishmod.mod_LavaCow.client.renders.tileentity.TileEntityScarecrowHeadRenderer;
import com.Fishmod.mod_LavaCow.compat.TinkersCompatBridge;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.Fishmod.mod_LavaCow.init.Modkeys;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead_common;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead_plague;
import com.Fishmod.mod_LavaCow.tileentity.TileEntityScarecrowHead_straw;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleBreaking;
import net.minecraft.client.particle.ParticleRedstone;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy implements IProxy {
	
	@Override
    public void preInit(FMLPreInitializationEvent event) {
		RenderFactories.registerEntityRenderers();
    }
 
    public void init(FMLInitializationEvent event) {
    	Modkeys.init();
    }
 
    public void postInit(FMLPostInitializationEvent event) {
    	ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(Modblocks.SCARECROWHEAD_COMMON), 0, TileEntityScarecrowHead_common.class);
    	ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(Modblocks.SCARECROWHEAD_STRAW), 0, TileEntityScarecrowHead_straw.class);
    	ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(Modblocks.SCARECROWHEAD_PLAGUE), 0, TileEntityScarecrowHead_plague.class);
    }
    
    @Override
    public void spawnCustomParticle(String particleName, World world, double x, double y, double z, double vecX, double vecY, double vecZ, float r, float g, float b) {
		Particle fx = null;
		float f = (float)Math.random() * 0.4F + 0.6F;
		
		if (particleName.equals("spore")) {
    		fx = new ParticleRedstone.Factory().createParticle(EnumParticleTypes.REDSTONE.getParticleID(), world, x, y, z, 0.0D, 0.0D, 0.0D);
		}
		
		if (particleName.equals("sludgejet")) {
    		//fx = new ParticleDragonBreath.Factory().createParticle(EnumParticleTypes.DRAGON_BREATH.getParticleID(), world, x, y, z, 0.0D, 0.0D, 0.0D);
			fx = new ParticleBreaking.Factory().createParticle(EnumParticleTypes.SLIME.getParticleID(), world, x, y, z, vecX, vecY, vecZ, Item.getIdFromItem(FishItems.SILKY_SLUDGE), 0);
		}
		
        if (particleName.equals("locust_swarm")) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new ParticalLocustSwarm(world, x, y, z, vecX, vecY, vecZ));
        }
		
		if (fx != null) {
			fx.setRBGColorF(((float)(Math.random() * 0.20000000298023224D) + 0.8F) * r * f, ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * g * f, ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * b * f);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
	}
	
	public void spawnCustomParticle(String particleName, World world, double x, double y, double z, double vecX, double vecY, double vecZ) {
		spawnCustomParticle(particleName, world, x, y, z, vecX, vecY, vecZ, 1.0F, 1.0F, 1.0F);
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void preRender() {
        TinkersCompatBridge.loadTinkersClientCompat();
    }
    
    @Override
	public void registerItemAndBlockRenderers() {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityScarecrowHead_common.class, new TileEntityScarecrowHeadRenderer(0));
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityScarecrowHead_straw.class, new TileEntityScarecrowHeadRenderer(1));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityScarecrowHead_plague.class, new TileEntityScarecrowHeadRenderer(2));
	}
}
