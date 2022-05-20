package com.Fishmod.mod_LavaCow.item;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.entities.tameable.CactoidEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CactoidPotItem extends Item {
	private final EntityType<?> type;
	public CactoidPotItem(EntityType<?> fishTypeIn, Item.Properties builder) {
		super(builder);
		this.type = fishTypeIn;
	}

	public void checkExtraContent(World worldIn, ItemStack stack, BlockPos pos) {
		if (worldIn instanceof ServerWorld) {
			this.spawn((ServerWorld)worldIn, stack, pos);
		}
	}

	protected void playEmptySound(@Nullable PlayerEntity player, IWorld worldIn, BlockPos pos) {
		worldIn.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
	}
	
	private void spawn(ServerWorld worldIn, ItemStack stack, BlockPos pos) {
		Entity entity = this.type.spawn(worldIn, stack, (PlayerEntity)null, pos, SpawnReason.BUCKET, true, false);
		if (entity != null && entity.getType() == FUREntityRegistry.CACTOID) {
			CompoundNBT compoundnbt = stack.getOrCreateTag();
			if(compoundnbt.contains("CactoidData")){
                ((CactoidEntity) entity).readAdditionalSaveData(compoundnbt.getCompound("CactoidData"));
            }
		}
	}
	
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
        BlockPos blockpos = blockraytraceresult.getBlockPos();
        
        if (!worldIn.isClientSide) {
            if(player == null || !player.isCreative()){
            	itemstack.shrink(1);
            }
            
            this.checkExtraContent((ServerWorld)worldIn, itemstack, blockpos);
            this.playEmptySound(player, worldIn, blockpos);                  
        }
        
        return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide);
	}
}