package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SinisterWhetstoneItem extends Item {	
	public SinisterWhetstoneItem(Properties p_i48487_1_) {
		super(p_i48487_1_);
	}
 
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		String s = "I";
		CompoundNBT compoundnbt = stack.getTag();
		
		if(compoundnbt != null) {
	        switch(compoundnbt.getInt("level")) {
	        	case 0:
	        	case 1:
	        		break;
		    	case 2:		    	
		    		s = "II";    
		    		break;     	
		    	case 3:
		    		s = "III";
		    		break;
		    	case 4:
		    		s = "IV";
		    		break;	
		    	case 5:	    		
		    	default:
		    		s = "V";      		
		    		break;
		    }
		}
		
		tooltip.add(new TranslationTextComponent("enchantment.mod_lavacow.criticalboost").append(s).withStyle(TextFormatting.GRAY));
	}	
}
