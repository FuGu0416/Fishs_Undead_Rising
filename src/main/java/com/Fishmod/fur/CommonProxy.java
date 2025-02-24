package com.Fishmod.fur;

import net.minecraft.world.entity.player.Player;

public class CommonProxy {
	
    public void commonInit() {
    }

    public void clientInit() {
    }
    
    /*public Item.Properties setupISTER(Item.Properties group) {
        return group;
    }*/

    public Player getClientSidePlayer() {
        return null;
    }

    /*public void openBookGUI(ItemStack itemStackIn) {
    }*/
    
    /*public void openBookGUI(ItemStack itemStackIn, String page) {
    }*/

    /*public Object getArmorModel(int armorId, LivingEntity entity) {
        return null;
    }*/

    /*public void onEntityStatus(Entity entity, byte updateKind) {
    }*/

    public void updateBiomeVisuals(int x, int z) {
    }

    public void setupParticles() {
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
