package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemNetherStew extends ItemFishCustomFood
{
	public static List<Triple<Potion, Integer, Integer>> Effect_nethersoup = Lists.newArrayList(
		    	new ImmutableTriple<Potion, Integer, Integer>(MobEffects.STRENGTH, 40*20, 1),
		    	new ImmutableTriple<Potion, Integer, Integer>(MobEffects.RESISTANCE, 40*20, 1),
		    	new ImmutableTriple<Potion, Integer, Integer>(MobEffects.SPEED, 40*20, 1),
		    	new ImmutableTriple<Potion, Integer, Integer>(MobEffects.ABSORPTION, 40*20, 1)
			);
	
	public static List<Triple<Potion, Integer, Integer>> Effect_bonestew = Lists.newArrayList(
	    	new ImmutableTriple<Potion, Integer, Integer>(MobEffects.ABSORPTION, 20*20, 1),
	    	new ImmutableTriple<Potion, Integer, Integer>(MobEffects.RESISTANCE, 20*20, 0)
		);

	public ItemNetherStew(String registryName) {
    	super(registryName, 6, 0.6F, false, 32, true);
        this.setAlwaysEdible();
        this.setMaxStackSize(Modconfig.Bowls_Stack ? 64 : 1);
    }
    
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {	
    	if (stack.getItem().equals(FishItems.NETHERSTEW) && target instanceof EntityPig && !target.isChild())
        {
    		if(!playerIn.isCreative()) 
    		{
    			stack.shrink(1);
    		}
    		
    		playerIn.playSound(SoundEvents.ENTITY_GENERIC_BURN, 1.0F, 1.0F);
        	for (int i = 0; i < 5; ++i)
            {
                double d0 = new Random().nextGaussian() * 0.02D;
                double d1 = new Random().nextGaussian() * 0.02D;
                double d2 = new Random().nextGaussian() * 0.02D;
                target.world.spawnParticle(EnumParticleTypes.HEART, target.posX + (double)(new Random().nextFloat() * target.width * 2.0F) - (double)target.width, target.posY + 1.0D + (double)(new Random().nextFloat() * target.height), target.posZ + (double)(new Random().nextFloat() * target.width * 2.0F) - (double)target.width, d0, d1, d2);
            }
        	
        	if (!playerIn.world.isRemote) 
        	{
	    		EntityPigZombie entitypigzombie = new EntityPigZombie(target.world);
	            entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
	            entitypigzombie.setLocationAndAngles(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
	            entitypigzombie.setChild(true);
	            target.world.spawnEntity(entitypigzombie);
        	}
            return true;
        }
        else
        {   	
        	return super.itemInteractionForEntity(stack, playerIn, target, hand);
        }
    }
    
    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
    	super.onItemUseFinish(stack, worldIn, entityLiving);
    	
    	if (this.equals(FishItems.GHOSTJELLY)) 
    	{
    		entityLiving.setVelocity(0.0D, 2.0D, 0.0D);
    		entityLiving.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 3 * 20, 2));
    		entityLiving.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6 * 20, 3));
    		entityLiving.playSound(SoundEvents.ENTITY_FIREWORK_LAUNCH, 1.0F, 1.0F);
    	}
    	
        if(!worldIn.isRemote && Modconfig.Bowls_Stack && entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).isCreative())
        	((EntityPlayer)entityLiving).inventory.addItemStackToInventory(new ItemStack(Items.BOWL));
    	
        return Modconfig.Bowls_Stack ? stack : new ItemStack(Items.BOWL);
    }

}
