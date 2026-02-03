package com.Fishmod.fur.item;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.Fishmod.fur.entities.tameable.SalamanderEntity;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SalamanderBucketItem extends BucketItem {
	private final java.util.function.Supplier<? extends EntityType<?>> typeSupplier;
	
	public SalamanderBucketItem(Supplier<EntityType<?>> fishTypeIn, Supplier<? extends Fluid> fluid, Item.Properties builder) {
		super(fluid, builder);
		this.typeSupplier = fishTypeIn;
	}

	@Override
	public void checkExtraContent(@Nullable Player player, Level worldIn, ItemStack stack, BlockPos pos) {
		if (worldIn instanceof ServerLevel server) {
			this.spawn(server, stack, pos);
		}
	}

	@Override
	protected void playEmptySound(@Nullable Player player, LevelAccessor worldIn, BlockPos pos) {
		worldIn.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
	}
	
	private void spawn(ServerLevel worldIn, ItemStack stack, BlockPos pos) {
		Entity entity = this.typeSupplier.get().spawn(worldIn, stack, (Player)null, pos, MobSpawnType.BUCKET, true, false);
		if (entity != null && entity.getType() == FUREntityRegistry.SALAMANDER.get()) {
			CompoundTag compoundnbt = stack.getOrCreateTag();
			if(compoundnbt.contains("SalamanderData")){
                ((SalamanderEntity) entity).readAdditionalSaveData(compoundnbt.getCompound("SalamanderData"));
            } else {
            	((SalamanderEntity) entity).setBaby(true);
            }
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
	}
  
	protected EntityType<?> getFishType() {
		return typeSupplier.get();
	}
}