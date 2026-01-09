package com.Fishmod.fur.item;

import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DiseasedBreadItem extends FURItem {
	
	public DiseasedBreadItem(Properties properties, int tooltip) {
		super(properties, 32, net.minecraft.world.item.UseAnim.EAT, tooltip);
	}	
    
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity living, InteractionHand hand) {
    	if (player.level() instanceof ServerLevel && player.getItemInHand(hand).getItem().equals(FURItemRegistry.DISEASED_BREAD.get()) && living instanceof Villager Villager && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(living, EntityType.ZOMBIE_VILLAGER, (timer) -> {})) {
            ZombieVillager zombievillager = Villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
            zombievillager.finalizeSpawn((ServerLevel)player.level(), player.level().getCurrentDifficultyAt(zombievillager.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), (CompoundTag)null);
            zombievillager.setVillagerData(Villager.getVillagerData());
            zombievillager.setGossips(Villager.getGossips().store(NbtOps.INSTANCE));
            zombievillager.setTradeOffers(Villager.getOffers().createTag());
            zombievillager.setVillagerXp(Villager.getVillagerXp());
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(living, zombievillager);
            if (!player.isSilent()) {
            	player.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            	player.level().levelEvent((Player)null, 1026, player.blockPosition(), 0);
            }  
            
    		if(!player.isCreative()) {		
    			this.finishUsingItem(stack, player.level(), player);
    		}
    		
    		return InteractionResult.SUCCESS;
    	}
    	
        return InteractionResult.PASS;
	}
}
