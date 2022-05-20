package com.Fishmod.mod_LavaCow.item;

import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.entities.tameable.SalamanderEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FURFishBucketItem extends BucketItem {
	private final EntityType<?> type;
	private final java.util.function.Supplier<? extends EntityType<?>> fishTypeSupplier;
	
	public FURFishBucketItem(EntityType<?> fishTypeIn, java.util.function.Supplier<? extends Fluid> fluid, Item.Properties builder) {
		super(fluid, builder);
		this.type = fishTypeIn;
		this.fishTypeSupplier = () -> fishTypeIn;
	}

	@Override
	public void checkExtraContent(World worldIn, ItemStack stack, BlockPos pos) {
		if (worldIn instanceof ServerWorld) {
			this.spawn((ServerWorld)worldIn, stack, pos);
		}
	}

	@Override
	protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
		worldIn.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
	}
	
	private void spawn(ServerWorld worldIn, ItemStack stack, BlockPos pos) {
		Entity entity = this.type.spawn(worldIn, stack, (PlayerEntity)null, pos, SpawnReason.BUCKET, true, false);
		if (entity != null && entity.getType() == FUREntityRegistry.SALAMANDER) {
			CompoundNBT compoundnbt = stack.getOrCreateTag();
			if(compoundnbt.contains("SalamanderData")){
                ((SalamanderEntity) entity).readAdditionalSaveData(compoundnbt.getCompound("SalamanderData"));
            }
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> list, ITooltipFlag flag) {
	}
  
	protected EntityType<?> getFishType() {
		return fishTypeSupplier.get();
	}
}