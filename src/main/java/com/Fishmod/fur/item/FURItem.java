package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURItem extends Item {
	private UseAnim UseAnim;
	private int UseDuration;
	private int Tooltip = 0;
	
	public FURItem(Properties p_i48487_1_, int UseDurationIn, UseAnim UseAnimIn, int TooltipIn) {
		super(p_i48487_1_);
		this.UseDuration = UseDurationIn;
		this.UseAnim = UseAnimIn;
		this.Tooltip = TooltipIn;
	}
	
    /**
     * 0: no tooltips
     * 1: tooltips w/ white text 
     * 2: tooltips w/ yellow text 
     */	
	public FURItem(Properties p_i48487_1_, int TooltipIn) {
		this(p_i48487_1_, 32, net.minecraft.world.item.UseAnim.EAT, TooltipIn);
	}
	
	public FURItem(Properties p_i48487_1_) {
		this(p_i48487_1_, 0);
	}

	@Override
    public int getUseDuration(ItemStack p_77626_1_) {
        return this.UseDuration;
    }

	@Override
    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return this.UseAnim;
    }
	
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
    	
		/*if((stack.getItem().equals(FURItemRegistry.SHATTERED_ICE) || stack.getItem().equals(FURItemRegistry.BOABING)) && entityLiving.isOnFire()) {
			entityLiving.clearFire();
		}*/
		
        /*if (!worldIn.isClientSide && stack.getItem().equals(FURItemRegistry.LAMPREY_KABAYAKI) && entityLiving instanceof PlayerEntity && !((PlayerEntity)entityLiving).isCreative()) {
        	if (!((PlayerEntity)entityLiving).inventory.add(new ItemStack(Items.STICK, 2))) {
        		((PlayerEntity)entityLiving).spawnAtLocation(new ItemStack(Items.STICK, 2));
            }
        }*/
		
    	return super.finishUsingItem(stack, worldIn, entityLiving);
    }
    
    @Override
    public boolean isFoil(ItemStack stack) {
    	return super.isFoil(stack)/* || stack.getItem().equals(FURItemRegistry.HOLY_WATER)*/;
    }
    
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity entityIn, InteractionHand handIn) {
    	/*if (playerIn.level instanceof ServerWorld && playerIn.getItemInHand(handIn).getItem().equals(FURItemRegistry.DISEASED_BREAD) && entityIn instanceof VillagerEntity && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(entityIn, EntityType.ZOMBIE_VILLAGER, (timer) -> {})) {
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
    	}*/
    	
        return InteractionResult.PASS;
	}
    
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
    	/*if (itemStack.getItem().equals(FURItemRegistry.BURNTOVIPOSITOR)) {
    		return 6400;
    	} else */if (itemStack.getItem().equals(FURItemRegistry.IMP_HORN.get())) {
    		return 3200;
    	} else {
    		return super.getBurnTime(itemStack, recipeType);
    	}
    }
 
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		/*if(stack.getItem().equals(FURItemRegistry.SOULFIREHEART)) {
			tooltip.add(Component.translatable("tooltip." + this.getName(stack), FURConfig.MootenHeart_Damage.get()).withStyle(ChatFormatting.YELLOW));
			tooltip.add(Component.translatable("tooltip." + this.getName(stack) + ".l2", 25).withStyle(ChatFormatting.GREEN));
		} else if(stack.getItem().equals(FURItemRegistry.MOOTENHEART))
			tooltip.add(Component.translatable("tooltip." + this.getName(stack), FURConfig.MootenHeart_Damage.get()).withStyle(ChatFormatting.YELLOW).append(Component.translatable("item.mod_lavacow.potion_of_mooten_lava").withStyle(ChatFormatting.YELLOW)));
		else */if (this.Tooltip == 2)
			tooltip.add(Component.translatable("tooltip." + this.getName(stack)));			
		else if (this.Tooltip == 1)
			tooltip.add(Component.translatable("tooltip." + this.getName(stack)).withStyle(ChatFormatting.YELLOW));
	}	
}
