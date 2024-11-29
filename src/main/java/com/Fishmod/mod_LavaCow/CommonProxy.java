package com.Fishmod.mod_LavaCow;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.Fishmod.mod_LavaCow.message.MessageMountSpecial;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {
	private int ID = 0;
	
    public void init() {
    }

    public void clientInit() {
    }
    
    public Item.Properties setupISTER(Item.Properties group) {
        return group;
    }

    public PlayerEntity getClientSidePlayer() {
        return null;
    }

    public void openBookGUI(ItemStack itemStackIn) {
    }

    public void openBookGUI(ItemStack itemStackIn, String page) {
    }

    public Object getArmorModel(int armorId, LivingEntity entity) {
        return null;
    }

    public void onEntityStatus(Entity entity, byte updateKind) {
    }

    public void updateBiomeVisuals(int x, int z) {
    }

    public void setupParticles() {
    }
    
    public void initNetwork() {
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
    }
}
