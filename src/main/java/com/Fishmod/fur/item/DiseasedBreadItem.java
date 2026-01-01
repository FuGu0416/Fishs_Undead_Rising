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
	
	public DiseasedBreadItem(Properties p_i48487_1_, int TooltipIn) {
		super(p_i48487_1_, 32, net.minecraft.world.item.UseAnim.EAT, TooltipIn);
	}	
    
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity entityIn, InteractionHand handIn) {
    	if (playerIn.level() instanceof ServerLevel && playerIn.getItemInHand(handIn).getItem().equals(FURItemRegistry.DISEASED_BREAD.get()) && entityIn instanceof Villager Villager && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(entityIn, EntityType.ZOMBIE_VILLAGER, (timer) -> {})) {
            ZombieVillager zombievillager = Villager.convertTo(EntityType.ZOMBIE_VILLAGER, false);
            zombievillager.finalizeSpawn((ServerLevel)playerIn.level(), playerIn.level().getCurrentDifficultyAt(zombievillager.blockPosition()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), (CompoundTag)null);
            zombievillager.setVillagerData(Villager.getVillagerData());
            zombievillager.setGossips(Villager.getGossips().store(NbtOps.INSTANCE));
            zombievillager.setTradeOffers(Villager.getOffers().createTag());
            zombievillager.setVillagerXp(Villager.getVillagerXp());
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(entityIn, zombievillager);
            if (!playerIn.isSilent()) {
            	playerIn.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            	playerIn.level().levelEvent((Player)null, 1026, playerIn.blockPosition(), 0);
            }  
            
    		if(!playerIn.isCreative()) {		
    			this.finishUsingItem(stack, playerIn.level(), playerIn);
    		}
    		
    		return InteractionResult.SUCCESS;
    	}
    	
        return InteractionResult.PASS;
	}
}
