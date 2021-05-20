package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.model.armor.ModelCrown;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIFollowEntity;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIGuardingEntity;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSkeletonKingCrown extends ItemArmor {

	public ItemSkeletonKingCrown(String registryName, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(ArmorMaterial.DIAMOND, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
        setRegistryName(registryName);
	}
	
    /**
     * Returns true if this item has an enchantment glint. By default, this returns
     * <code>stack.isItemEnchanted()</code>, but other items can override it (for instance, written books always return
     * true).
     *  
     * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
     * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
     */
	@Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
    
    /**
     * Return an item rarity from EnumRarity
     */
    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return EnumRarity.EPIC;
    }
    
	@Override
	public boolean getIsRepairable(ItemStack armour, ItemStack material) {
		return material.getItem() == Items.GOLD_INGOT;
	}
    
    /**
     * Called to tick armor in the armor slot. Override to do something
     */
	@Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack){
    	if(player.ticksExisted % 20 == 0) {
    		boolean modified = false;
    		
	    	for (AbstractSkeleton Skeleton : world.getEntitiesWithinAABB(AbstractSkeleton.class, player.getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D)))
	        {
	    		for (EntityAITasks.EntityAITaskEntry Task : Skeleton.tasks.taskEntries) {
	    			if (Task.action instanceof EntityAIFollowEntity)
	    				modified = true;
	    		}
	    		
	    		if(!modified) {
	    			Skeleton.setAttackTarget(null);
	    			
	    			Skeleton.playSound(SoundEvents.AMBIENT_CAVE, 1.0F, 1.0F);
		        	for (int i = 0; i < 16; ++i)
		            {
		                double d0 = new Random().nextGaussian() * 0.02D;
		                double d1 = new Random().nextGaussian() * 0.02D;
		                double d2 = new Random().nextGaussian() * 0.02D;
		                Skeleton.world.spawnParticle(EnumParticleTypes.SPELL_MOB, Skeleton.posX + (double)(new Random().nextFloat() * Skeleton.width) - (double)Skeleton.width, Skeleton.posY + (double)(new Random().nextFloat() * Skeleton.height), Skeleton.posZ + (double)(new Random().nextFloat() * Skeleton.width) - (double)Skeleton.width, d0, d1, d2);
		            }
		        	
		    		Skeleton.tasks.taskEntries.clear();
		    		Skeleton.targetTasks.taskEntries.clear();

		    		Skeleton.tasks.addTask(1, new EntityAISwimming(Skeleton));
		    		Skeleton.tasks.addTask(2, new EntityAIRestrictSun(Skeleton));
		    		Skeleton.tasks.addTask(3, new EntityAIFleeSun(Skeleton, 1.0D));
		    		Skeleton.tasks.addTask(6, new EntityAIFollowEntity(Skeleton, player.getUniqueID(), 1.0D, 10.0F, 2.0F));
		    		Skeleton.tasks.addTask(6, new EntityAIWatchClosest(Skeleton, EntityPlayer.class, 8.0F));
		    		Skeleton.tasks.addTask(6, new EntityAILookIdle(Skeleton));
		    		Skeleton.targetTasks.addTask(1, new EntityAIGuardingEntity(Skeleton, player.getUniqueID()));
		    		Skeleton.targetTasks.addTask(1, new EntityAIHurtByTarget(Skeleton, false, new Class[0]));
		    		Skeleton.setCombatTask();
		    		
		    		break;
	    		}
	        }
    	}
    }
    
	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot armorSlot, String type) {
		return "mod_lavacow:textures/mobs/skeletonking.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase player, ItemStack stack, EntityEquipmentSlot armorSlot, ModelBiped modelBiped) {
		return new ModelCrown(1.0F);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
			list.add(TextFormatting.YELLOW + I18n.format("tootip.mod_lavacow.skeletonking_crown"));
	}

}
