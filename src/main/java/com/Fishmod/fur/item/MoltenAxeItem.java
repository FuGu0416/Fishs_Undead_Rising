package com.Fishmod.fur.item;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoltenAxeItem extends AxeItem {
	private Item repair_material;
	private SimpleParticleType particles;
	
	public MoltenAxeItem(Properties p_i48487_1_, Tier materialIn, float damageIn, float attackspeedIn, Item repair, SimpleParticleType particlesIn) {
		super(materialIn, damageIn, attackspeedIn, p_i48487_1_);
		this.repair_material = repair;
		this.particles = particlesIn;
	}
		
	/**
	    * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
	    */
	@Override
	public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {	
		if(worldIn.getBlockState(pos).is(BlockTags.MINEABLE_WITH_AXE)) {
			worldIn.playSound((Player)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FIRE_AMBIENT, entityLiving.getSoundSource(), 1.0F, 1.0F);
			double j = 1.2D;
			for(int i = 0; i < 16; i++) {
				((ServerLevel) worldIn).sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5D + entityLiving.getRandom().nextDouble() * j - j/2, pos.getY() + 0.5D + entityLiving.getRandom().nextDouble() * j - j/2, pos.getZ() + 0.5D + entityLiving.getRandom().nextDouble() * j - j/2, 1, 0.0D, 0.0D, 0.0D, 0.0D);
				((ServerLevel) worldIn).sendParticles(this.particles, pos.getX() + 0.5D + entityLiving.getRandom().nextDouble() * j - j/2, pos.getY() + 0.5D + entityLiving.getRandom().nextDouble() * j - j/2, pos.getZ() + 0.5D + entityLiving.getRandom().nextDouble() * j - j/2, 1, 0.0D, 0.0D, 0.0D, 0.0D);	
			}
		}
				
		return super.mineBlock(stack, worldIn, state, pos, entityLiving);
	}
	
    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		int i = stack.getEnchantmentLevel(Enchantments.FIRE_ASPECT);			
		target.setSecondsOnFire((i + 2) * 4);
		
		return super.hurtEnemy(stack, target, attacker);
	}
	
	@Override
	public boolean isValidRepairItem(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.getItem().equals(this.repair_material);
	}
	
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    	tooltip.add(Component.translatable(stack.getDescriptionId() + ".desc").withStyle(ChatFormatting.YELLOW));
	}	
}
