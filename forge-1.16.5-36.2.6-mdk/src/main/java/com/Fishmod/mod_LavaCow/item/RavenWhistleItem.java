package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.tameable.RavenEntity;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RavenWhistleItem extends FURItem {

	private UUID OrderEntityID;
	
	public RavenWhistleItem(Properties PropertiesIn) {
		super(PropertiesIn, 0, UseAction.NONE, 1);
	}
    
    /**
     * Called when the equipped item is right clicked.
     */
    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        Vector3d pos = playerIn.pick(30.0D, 1.0F, false).getLocation();
        
        if(this.OrderEntityID != null && !playerIn.level.isClientSide()) { 	
        	Entity entity = SpawnUtil.getEntityByUniqueId(this.OrderEntityID, worldIn.getServer().overworld());
        	if(playerIn.distanceTo(entity) < 16.0F) {
	        	((RavenEntity) entity).setTargetLocation((float)pos.x, (float)pos.y, (float)pos.z);
	        	worldIn.playSound(null, entity, FURSoundRegistry.RAVEN_CALL, SoundCategory.VOICE, 1.0F, 1.0F);
	        	playerIn.getCooldowns().addCooldown(this, 30);
				return new ActionResult<ItemStack>(ActionResultType.SUCCESS, playerIn.getItemInHand(handIn));
        	} else if (!playerIn.level.isClientSide()) {
        		playerIn.displayClientMessage(new TranslationTextComponent("command.mod_lavacow.whistle_err", entity.getName()), true);
        	}
        }
        
    	return super.use(worldIn, playerIn, handIn);
    }
	
    /**
     * Returns true if the item can be used on the given entity, e.g. shears on sheep.
     */
    @Override
    public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if(target instanceof RavenEntity && ((RavenEntity) target).getOwnerUUID() != null && ((RavenEntity) target).getOwnerUUID().equals(playerIn.getUUID())) {
        	this.OrderEntityID = target.getUUID();
        	playerIn.getItemInHand(hand).getOrCreateTagElement("OrderName").putString("OrderName", target.getName().getString());
        	playerIn.getItemInHand(hand).getOrCreateTagElement("OrderID").putUUID("OrderID", target.getUUID());

        	if(!playerIn.level.isClientSide()) {
        		playerIn.displayClientMessage(new TranslationTextComponent("command.mod_lavacow.whistle", target.getName()), true);
        	}
        	
        	return ActionResultType.SUCCESS;
        }
    	
    	return ActionResultType.FAIL;
    }
    
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
		
		if(stack.hasTag()) {
			tooltip.add(new TranslationTextComponent(stack.getTag().getString("OrderName")).withStyle(TextFormatting.DARK_GRAY));
		}			
	}
}
