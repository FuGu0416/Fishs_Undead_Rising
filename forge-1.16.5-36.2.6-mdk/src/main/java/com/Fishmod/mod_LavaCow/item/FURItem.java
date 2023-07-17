package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURItem extends Item {
	private UseAction UseAction;
	private int UseDuration;
	private int Tooltip = 0;
	
	public FURItem(Properties p_i48487_1_, int UseDurationIn, UseAction UseActionIn, int TooltipIn) {
		super(p_i48487_1_);
		this.UseDuration = UseDurationIn;
		this.UseAction = UseActionIn;
		this.Tooltip = TooltipIn;
	}
	
	public FURItem(Properties p_i48487_1_, int TooltipIn) {
		this(p_i48487_1_, 32, net.minecraft.item.UseAction.EAT, TooltipIn);
	}
	
	public FURItem(Properties p_i48487_1_) {
		this(p_i48487_1_, 0);
	}

	@Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return this.UseDuration;
    }

	@Override
    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return this.UseAction;
    }
	
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
    	
		if((stack.getItem().equals(FURItemRegistry.SHATTERED_ICE) || stack.getItem().equals(FURItemRegistry.BOABING)) && entityLiving.isOnFire())
			entityLiving.clearFire();
		
    	return super.finishUsingItem(stack, worldIn, entityLiving);
    }
    
    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entityIn, Hand handIn) {
    	if (playerIn.level instanceof ServerWorld && playerIn.getItemInHand(handIn).getItem().equals(FURItemRegistry.DISEASED_BREAD) && entityIn instanceof VillagerEntity && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(entityIn, EntityType.ZOMBIE_VILLAGER, (timer) -> {})) {
    		VillagerEntity villagerentity = (VillagerEntity)entityIn;
            ZombieVillagerEntity zombievillagerentity = villagerentity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
            zombievillagerentity.finalizeSpawn((ServerWorld)playerIn.level, playerIn.level.getCurrentDifficultyAt(zombievillagerentity.blockPosition()), SpawnReason.CONVERSION, new ZombieEntity.GroupData(false, true), (CompoundNBT)null);
            zombievillagerentity.setVillagerData(villagerentity.getVillagerData());
            zombievillagerentity.setGossips(villagerentity.getGossips().store(NBTDynamicOps.INSTANCE).getValue());
            zombievillagerentity.setTradeOffers(villagerentity.getOffers().createTag());
            zombievillagerentity.setVillagerXp(villagerentity.getVillagerXp());
            net.minecraftforge.event.ForgeEventFactory.onLivingConvert(entityIn, zombievillagerentity);
            if (!playerIn.isSilent()) {
            	playerIn.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
            	playerIn.level.levelEvent((PlayerEntity)null, 1026, playerIn.blockPosition(), 0);
            }  
            
    		if(!playerIn.isCreative()) {		
    			this.finishUsingItem(stack, playerIn.level, playerIn);
    		}
    		
    		return ActionResultType.SUCCESS;
    	}
    	
        return ActionResultType.FAIL;
	}
    
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable IRecipeType<?> recipeType) {
    	if(itemStack.getItem().equals(FURItemRegistry.BURNTOVIPOSITOR)) {
    		return 6400;
    	} else if(itemStack.getItem().equals(FURItemRegistry.IMP_HORN)) {
    		return 3200;
    	} else {
    		return super.getBurnTime(itemStack, recipeType);
    	}
    }
 
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if(stack.getItem().equals(FURItemRegistry.SOULFIREHEART)) {
			tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName(), FURConfig.MootenHeart_Damage.get()).withStyle(TextFormatting.YELLOW));
			tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName() + ".l2", 25).withStyle(TextFormatting.GREEN));
		} else if(stack.getItem().equals(FURItemRegistry.MOOTENHEART))
			tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName(), FURConfig.MootenHeart_Damage.get()).withStyle(TextFormatting.YELLOW).append(new TranslationTextComponent("item.mod_lavacow.potion_of_mooten_lava").withStyle(TextFormatting.YELLOW)));
		else if(this.Tooltip == 2)
			tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName()));			
		else if(this.Tooltip == 1)
			tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName()).withStyle(TextFormatting.YELLOW));
	}	
}
