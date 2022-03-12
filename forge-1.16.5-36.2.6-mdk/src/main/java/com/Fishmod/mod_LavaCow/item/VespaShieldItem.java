package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VespaShieldItem extends ShieldItem {
	
	public VespaShieldItem(Item.Properties p_i48470_1_)
	{
		super(p_i48470_1_);
	}
	
	@Override
	public String getDescriptionId(ItemStack stack)
    {
        return this.getDescriptionId();
    }
	
    /**
     * allows items to add custom lines of information to the mouseover description
     */
	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tootip." + this.getRegistryName()).withStyle(TextFormatting.YELLOW));
	}
		
	/**
	 * Called when an attack was successfully blocked
	 * @param stack
	 * @param attacked
	 * @param source
	 */
	public void onAttackBlocked(ItemStack stackIn, LivingEntity attackedIn, float damageIn, DamageSource sourceIn) {	
		if(!attackedIn.level.isClientSide) {			
			if(sourceIn.getDirectEntity() instanceof LivingEntity) {
				sourceIn.getDirectEntity().hurt(DamageSource.thorns(attackedIn) , 2.0F);
				((LivingEntity)sourceIn.getDirectEntity()).addEffect(new EffectInstance(Effects.POISON, 6 * 20, 0));
			}
		}
	}
	
    /**
     * Determines whether the entity can block the damage source based on the damage source's location, whether the
     * damage source is blockable, and whether the entity is blocking.
     */
    public boolean canBlockDamageSource(ItemStack stackIn, LivingEntity attackedIn, Hand handIn, DamageSource damageSourceIn)
    {
        if (attackedIn.isUsingItem() && attackedIn.getUsedItemHand() == handIn && attackedIn.isBlocking() && (!(damageSourceIn instanceof EntityDamageSource) || damageSourceIn.getDirectEntity() != null))
        {
            Vector3d vec3d = damageSourceIn.getSourcePosition();

            if (vec3d != null)
            {
                Vector3d vec3d1 = attackedIn.getLookAngle();
                Vector3d vec3d2 = new Vector3d(attackedIn.getX(), attackedIn.getY(), attackedIn.getZ()).subtract(vec3d).normalize();
                vec3d2 = new Vector3d(vec3d2.x, 0.0D, vec3d2.z);

                if (vec3d2.dot(vec3d1) < 0.0D)
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
	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
        return material.getItem() == FURItemRegistry.VESPA_CARAPACE ? true : super.isValidRepairItem(armour, material);
    }
}
