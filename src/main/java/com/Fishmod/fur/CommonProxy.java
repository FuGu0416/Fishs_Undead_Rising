package com.Fishmod.fur;

import com.Fishmod.fur.message.MessageBeelzebubBite;
import com.Fishmod.fur.message.MessageMountSpecial;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.network.NetworkDirection;

public class CommonProxy {
	
    public void commonInit() {
    }

    public void clientInit() {
    }
    
    public Player getClientSidePlayer() {
        return null;
    }

    /*public void openBookGUI(ItemStack itemStackIn) {
    }*/
    
    /*public void openBookGUI(ItemStack itemStackIn, String page) {
    }*/

    public Object getArmorProperties() {
        return null;
    }
    
    public Object getISTERProperties() {
        return null;
    }

    /*public void onEntityStatus(Entity entity, byte updateKind) {
    }*/

    public void updateBiomeVisuals(int x, int z) {
    }

    public static void setupParticles(RegisterParticleProvidersEvent registry) {
    }
    
    public void initNetwork() {
		int packetId = 0;
    	mod_LavaCow.NETWORK.messageBuilder(MessageMountSpecial.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(MessageMountSpecial::serialize)
        .decoder(MessageMountSpecial::deserialize)
        .consumerMainThread(MessageMountSpecial::handle)
        .add();
    	mod_LavaCow.NETWORK.messageBuilder(MessageBeelzebubBite.class, packetId++, NetworkDirection.PLAY_TO_SERVER)
        .encoder(MessageBeelzebubBite::serialize)
        .decoder(MessageBeelzebubBite::deserialize)
        .consumerMainThread(MessageBeelzebubBite::handle)
        .add();
    }
}
