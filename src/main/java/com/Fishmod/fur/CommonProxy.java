package com.Fishmod.fur;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;

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
    
    /*public void initNetwork() {
        final String version = "1";
        mod_LavaCow.NETWORK = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(mod_LavaCow.MODID, "net"))
                .networkProtocolVersion(() -> version)
                .clientAcceptedVersions(version::equals)
                .serverAcceptedVersions(version::equals)
                .simpleChannel();
        this.registerMessage(MessageMountSpecial.class, MessageMountSpecial::serialize, MessageMountSpecial::deserialize, new MessageMountSpecial.Handler());
    }
    
    private <MSG> void registerMessage(final Class<MSG> clazz, final BiConsumer<MSG, PacketBuffer> encoder, final Function<PacketBuffer, MSG> decoder, final BiConsumer<MSG, Supplier<NetworkEvent.Context>> consumer) {
    	mod_LavaCow.NETWORK.messageBuilder(clazz, this.ID++)
                .encoder(encoder).decoder(decoder)
                .consumer(consumer)
                .add();
    }*/
}
