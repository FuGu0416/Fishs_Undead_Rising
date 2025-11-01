package com.Fishmod.fur.item;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.Fishmod.fur.entities.tameable.WispEntity;
import com.Fishmod.fur.init.FUREntityRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class EntityBucketItem extends Item {
	private final Supplier<EntityType<?>> typeSupplier;
    private final Item returnItem;

    public EntityBucketItem(Supplier<EntityType<?>> typeSupplier, Item returnItem, Item.Properties properties) {
        super(properties);
        this.typeSupplier = typeSupplier;
        this.returnItem = returnItem;
    }

    public void checkExtraContent(@Nullable Player player, Level level, ItemStack stack, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            this.spawn(serverLevel, stack, pos);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, pos);
        }
    }

    protected void playEmptySound(@Nullable Player player, LevelAccessor level, BlockPos pos) {
        level.playSound(player, pos, SoundEvents.BUCKET_EMPTY_FISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    private void spawn(ServerLevel level, ItemStack stack, BlockPos pos) {
        Entity entity = this.typeSupplier.get().spawn(level, stack, null, pos, MobSpawnType.BUCKET, true, false);
        
		/*if (entity != null && entity.getType() == FUREntityRegistry.CACTOID) {
			CompoundNBT compoundnbt = stack.getOrCreateTag();
			if(compoundnbt.contains("CactoidData")){
                ((CactoidEntity) entity).readAdditionalSaveData(compoundnbt.getCompound("CactoidData"));
            }
		}*/
		
		if (entity != null && entity.getType() == FUREntityRegistry.WISP.get()) {
			CompoundTag compoundnbt = stack.getOrCreateTag();
			if(compoundnbt.contains("WispData")){
                ((WispEntity) entity).readAdditionalSaveData(compoundnbt.getCompound("WispData"));
            }
		}
    }

	public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		BlockHitResult raytraceresult = getPlayerPOVHitResult(worldIn, player, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos blockpos = raytraceresult.getBlockPos();
        
        if (!worldIn.isClientSide) {
            if(player == null || !player.isCreative()){
            	itemstack.shrink(1);
            }
            
            this.checkExtraContent(player, worldIn, itemstack, blockpos);
            this.playEmptySound(player, worldIn, blockpos);                  
        }
        
        return InteractionResultHolder.sidedSuccess(this.getEmptySuccessItem(itemstack, player), worldIn.isClientSide());
	}
	
	protected ItemStack getEmptySuccessItem(ItemStack stack, Player player) {
		return !player.getAbilities().instabuild ? new ItemStack(this.returnItem) : stack;
	}
}
