package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.projectiles.BasicBombEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURThrowableItem extends Item {

	public FURThrowableItem(String registryName) {
		super(new Item.Properties().tab(mod_LavaCow.TAB));
		this.setRegistryName(registryName);
	}
	
    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack itemstack = playerIn.getItemInHand(handIn);

        if (!playerIn.isCreative()) {
            itemstack.shrink(1);
        }

        worldIn.playSound((PlayerEntity)null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        
    	if (itemstack.getItem().equals(FURItemRegistry.HOLY_GRENADE)) {
    		BasicBombEntity entitysnowball = new BasicBombEntity(FUREntityRegistry.HOLY_GRENADE, playerIn, worldIn, SoundEvents.GENERIC_EXPLODE, 4.0F);
            entitysnowball.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, -20.0F, 0.75F, 1.0F);
            worldIn.addFreshEntity(entitysnowball);
    	} else if(itemstack.getItem().equals(FURItemRegistry.GHOSTBOMB)) {
    		BasicBombEntity entitysnowball = new BasicBombEntity(FUREntityRegistry.GHOSTBOMB, playerIn, worldIn, FURSoundRegistry.BANSHEE_HURT, 4.0F);
            entitysnowball.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, -20.0F, 0.75F, 1.0F);
            worldIn.addFreshEntity(entitysnowball);
    	} else if(itemstack.getItem().equals(FURItemRegistry.SONICBOMB)) {
    		BasicBombEntity entitysnowball = new BasicBombEntity(FUREntityRegistry.SONICBOMB, playerIn, worldIn, FURSoundRegistry.BANSHEE_ATTACK, 4.0F);
            entitysnowball.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, -20.0F, 0.75F, 1.0F);
            worldIn.addFreshEntity(entitysnowball);
    	} else if(itemstack.getItem().equals(FURItemRegistry.BASIC_BOMB)) {
    		BasicBombEntity entitysnowball = new BasicBombEntity(FUREntityRegistry.BASIC_BOMB, playerIn, worldIn, SoundEvents.GENERIC_EXPLODE, 2.0F);
            entitysnowball.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, -20.0F, 0.75F, 1.0F);
            worldIn.addFreshEntity(entitysnowball);
    	}

        playerIn.awardStat(Stats.ITEM_USED.get(this));
        
        return ActionResult.pass(itemstack);
    }
    
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tooltip." + this.getRegistryName()).withStyle(TextFormatting.YELLOW));
	}
}
