package com.Fishmod.mod_LavaCow.item;

import java.util.Random;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.EntityLavaCow;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityMimic;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityRaven;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;
import com.Fishmod.mod_LavaCow.entities.tameable.EntityScarecrow;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFissionPotion extends ItemFishCustom{
	
	private SoundEvent using_sound = null;
	private EnumParticleTypes using_particle = EnumParticleTypes.SMOKE_NORMAL;
	private EnumRarity Rarity;
	
	public ItemFissionPotion(String registryName, SoundEvent soundIn, EnumParticleTypes particleIn, EnumRarity rarity, boolean hasTooltip) {
    	super(registryName, Items.GLASS_BOTTLE, mod_LavaCow.TAB_ITEMS, true);
    	this.setMaxStackSize(1);
    	this.using_sound = soundIn;
		this.using_particle = particleIn;
		this.Rarity = rarity;
    }
	
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return stack.getItem() == FishItems.POTION_OF_MOOTEN_LAVA;
    }
	
    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack)
    {
        return this.Rarity;
    }
    
    private boolean isVanilla(Class<?> ClassIn) {
    	return ClassIn.equals(EntityVillager.class) || ClassIn.equals(EntityDonkey.class) 
    			|| ClassIn.equals(EntityLlama.class) || ClassIn.equals(EntityMule.class) 
    			|| ClassIn.equals(EntityHorse.class) || ClassIn.equals(EntityChicken.class) 
    			|| ClassIn.equals(EntityCow.class) || ClassIn.equals(EntityLavaCow.class) 
    			|| ClassIn.equals(EntityMooshroom.class) || ClassIn.equals(EntityPig.class) 
    			|| ClassIn.equals(EntityPolarBear.class) || ClassIn.equals(EntityRabbit.class) 
    			|| ClassIn.equals(EntitySheep.class) || ClassIn.equals(EntityMimic.class) 
    			|| ClassIn.equals(EntityOcelot.class) || ClassIn.equals(EntityParrot.class) 
    			|| ClassIn.equals(EntityWolf.class) || ClassIn.equals(EntityRaven.class)
    			|| ClassIn.equals(EntitySalamander.class) || ClassIn.equals(EntityScarecrow.class);
    }
	
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {	
    	if(((!Modconfig.Fission_ModEntity && isVanilla(target.getClass())) || (Modconfig.Fission_ModEntity && target instanceof EntityAgeable)) && !target.isChild()) {
    		
    		double dx = target.posX;
    		double dy = target.posY;
    		double dz = target.posZ;
    		boolean flag = false;
    		
    		if(!playerIn.world.isRemote) {
	    		if(stack.getItem().equals(FishItems.FISSIONPOTION)) {	    			
			    	EntityAgeable parent = (EntityAgeable)target;
			    	EntityAgeable entityageable = parent.createChild(parent) != null ? parent.createChild(parent) : (EntityAgeable)EntityRegistry.getEntry(target.getClass()).newInstance(parent.world);//ItemMonsterPlacer.spawnCreature(parent.world, eggInfo, parent.posX, parent.posY, parent.posZ);
			    	entityageable.setGrowingAge(-24000);
			        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
			        parent.world.spawnEntity(entityageable);
			        if(entityageable.getClass() == parent.getClass())parent.setGrowingAge(-24000);
			        if(parent instanceof EntityTameable 
			        		&& ((EntityTameable)parent).isTamed()
			        		&& entityageable instanceof EntityTameable 
			        		&& !((EntityTameable)entityageable).isTamed())
			        	((EntityTameable)entityageable).setTamedBy(playerIn);
			        entityageable.playLivingSound();
			        
			        flag = true;
	    		}
	    		else if(stack.getItem().equals(FishItems.POTION_OF_MOOTEN_LAVA) && target instanceof EntityCow && !(target instanceof EntityLavaCow)) {
	    			
	    			if(target.getActivePotionEffect(MobEffects.FIRE_RESISTANCE) == null)target.setFire(12);
	    			else {	    				
	    				for(int i = 0 ; i < 2 ; i++) {
		    				EntityAgeable entityageable = new EntityLavaCow(playerIn.world);
		    				entityageable.setGrowingAge(-24000);
		    				entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
		    				playerIn.world.spawnEntity(entityageable);
	    				}	    				
	    				target.setDead();
	    			}
	    			
			        flag = true;
	    		}
    		}
    		
    		if(!playerIn.isCreative() && flag) {
    			stack.shrink(1);
    			playerIn.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
    		}
	    	
    		playerIn.playSound(this.using_sound, 1.0F, 1.0F);
    		for (int i = 0; i < 5; ++i) {
    			double d0 = new Random().nextGaussian() * 0.02D;
    			double d1 = new Random().nextGaussian() * 0.02D;
    			double d2 = new Random().nextGaussian() * 0.02D;
    			playerIn.world.spawnParticle(this.using_particle, dx + (double)(new Random().nextFloat() * playerIn.width * 2.0F) - (double)playerIn.width, dy + 1.0D + (double)(new Random().nextFloat() * playerIn.height), dz + (double)(new Random().nextFloat() * playerIn.width * 2.0F) - (double)playerIn.width, d0, d1, d2);
    		}
        }  
    	
        return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

}
