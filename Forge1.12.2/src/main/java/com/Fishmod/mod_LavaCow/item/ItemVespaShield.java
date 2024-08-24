package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.init.FishItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemVespaShield extends ItemShield {
	
	public ItemVespaShield(String registryName)
	{
		super();
		setCreativeTab(mod_LavaCow.TAB_ITEMS);
		setUnlocalizedName(mod_LavaCow.MODID + "." + registryName);
		this.setMaxDamage(504);
        setRegistryName(registryName);
	}
	
	public String getItemStackDisplayName(ItemStack stack)
    {
        return new TextComponentTranslation("item.mod_lavacow.vespa_shield.name").getFormattedText();
    }
	
    /**
     * allows items to add custom lines of information to the mouseover description
     */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flag) {
		list.add(TextFormatting.YELLOW + I18n.format("tootip." + mod_LavaCow.MODID + ".vespa_shield"));
	}

		
	/**
	 * Called when an attack was successfully blocked
	 * @param stack
	 * @param attacked
	 * @param source
	 */
	public void onAttackBlocked(ItemStack stackIn, EntityLivingBase attackedIn, float damageIn, DamageSource sourceIn) {
		int i = 1 + MathHelper.floor(damageIn);
		
		if(!attackedIn.world.isRemote) {			
			if(sourceIn.getTrueSource() instanceof EntityLivingBase) {
				sourceIn.getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(attackedIn) , 2.0F);
				((EntityLivingBase)sourceIn.getTrueSource()).addPotionEffect(new PotionEffect(MobEffects.POISON, 6 * 20, 0));
				stackIn.damageItem(i, attackedIn);
			}
		}
	}
	
	/**
	 * Called when an attack was successfully blocked
	 * @param stack
	 * @param attacked
	 * @param source
	 */
	/*public void onAttackBlocked(ItemStack stackIn, EntityLivingBase attackedIn, float damageIn, DamageSource sourceIn) {
		int poisonous = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.POISONOUS, ((EntityLivingBase)sourceIn.getTrueSource()).getHeldItem(((EntityLivingBase)sourceIn.getTrueSource()).getActiveHand()));
		int i = 1 + MathHelper.floor(damageIn);
		int calc = 6 * poisonous;
		
		if(!attackedIn.world.isRemote) {			
			if(sourceIn.getTrueSource() instanceof EntityLivingBase) {
				sourceIn.getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(attackedIn) , 2.0F);
				((EntityLivingBase)sourceIn.getTrueSource()).addPotionEffect(new PotionEffect(MobEffects.POISON, calc + 6 * 20, 0));
				stackIn.damageItem(i, attackedIn);
			}
		}
	}
	
    /**
     * Determines whether the entity can block the damage source based on the damage source's location, whether the
     * damage source is blockable, and whether the entity is blocking.
     */
    public boolean canBlockDamageSource(ItemStack stackIn, EntityLivingBase attackedIn, EnumHand handIn, DamageSource damageSourceIn)
    {
        if (attackedIn.isHandActive() && attackedIn.getActiveHand() == handIn && !damageSourceIn.isUnblockable() && attackedIn.isActiveItemStackBlocking() && (!(damageSourceIn instanceof EntityDamageSource) || damageSourceIn.getTrueSource() != null))
        {
            Vec3d vec3d = damageSourceIn.getDamageLocation();

            if (vec3d != null)
            {
                Vec3d vec3d1 = attackedIn.getLook(1.0F);
                Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(attackedIn.posX, attackedIn.posY, attackedIn.posZ)).normalize();
                vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

                if (vec3d2.dotProduct(vec3d1) < 0.0D)
                {
                    return true;
                }
            }
        }

        return false;
    }
    
    /**
     * Return whether this item is repairable in an anvil.
     *  
     * @param toRepair the {@code ItemStack} being repaired
     * @param repair the {@code ItemStack} being used to perform the repair
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == FishItems.VESPA_CARAPACE ? true : super.getIsRepairable(toRepair, repair);
    }
}
