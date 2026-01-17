package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.fur.entities.projectiles.BasicBombEntity;
import com.Fishmod.fur.init.FUREntityRegistry;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURSoundRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURThrowableItem extends Item {

	public FURThrowableItem(Properties properties) {
		super(properties);
	}
	
    /**
     * Called when the equipped item is right clicked.
     */
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack itemstack = player.getItemInHand(hand);

        if (!player.isCreative()) {
            itemstack.shrink(1);
        }

        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
        
    	if (itemstack.getItem().equals(FURItemRegistry.HOLY_GRENADE.get())) {
    		BasicBombEntity entitysnowball = new BasicBombEntity(FUREntityRegistry.HOLY_GRENADE.get(), player, level, SoundEvents.GENERIC_EXPLODE, 4.0F);
            entitysnowball.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.75F, 1.0F);
            level.addFreshEntity(entitysnowball);
    	} else if (itemstack.getItem().equals(FURItemRegistry.GHOST_BOMB.get())) {
    		BasicBombEntity entitysnowball = new BasicBombEntity(FUREntityRegistry.GHOST_BOMB.get(), player, level, FURSoundRegistry.BANSHEE_HURT.get(), 4.0F);
            entitysnowball.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.75F, 1.0F);
            level.addFreshEntity(entitysnowball);
    	} else if (itemstack.getItem().equals(FURItemRegistry.SONIC_BOMB.get())) {
    		BasicBombEntity entitysnowball = new BasicBombEntity(FUREntityRegistry.SONIC_BOMB.get(), player, level, FURSoundRegistry.BANSHEE_ATTACK.get(), 4.0F);
            entitysnowball.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.75F, 1.0F);
            level.addFreshEntity(entitysnowball);
    	} else if (itemstack.getItem().equals(FURItemRegistry.BASIC_BOMB.get())) {
    		BasicBombEntity entitysnowball = new BasicBombEntity(FUREntityRegistry.BASIC_BOMB.get(), player, level, SoundEvents.GENERIC_EXPLODE, 2.0F);
            entitysnowball.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.75F, 1.0F);
            level.addFreshEntity(entitysnowball);
    	}

        player.awardStat(Stats.ITEM_USED.get(this));
        
        return InteractionResultHolder.pass(itemstack);
    }
    
	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
    	tooltip.add(Component.translatable(this.getDescriptionId() +  ".desc").withStyle(ChatFormatting.YELLOW));
	}
}
