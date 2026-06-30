package com.Fishmod.fur.item;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;

import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class SkeletonKingCrownItem extends ArmorItem {

	public SkeletonKingCrownItem(Item.Properties properties) {
		super(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET, properties);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.HATRED_SHARD.get();
	}

	/**
	 * Called to tick armor in the armor slot.
	 *
	 * The original 1.16.5 behaviour forces nearby skeletons to become loyal
	 * servants of the wearer (strips their target goals and adds owner-follow /
	 * owner-hurt goals). Those AI tasks (EntityAIFollowEntity,
	 * SkeletonOwnerHurtByTargetGoal, SkeletonOwnerHurtTargetGoal) are part of the
	 * SkeletonKing entity which has not been ported to 1.20.1 yet, so the taming
	 * logic is deferred until that port lands. See PLACEHOLDERS.md.
	 */
	@Override
	public void onArmorTick(ItemStack itemStack, Level level, Player player) {
		/* Deferred — pending SkeletonKing entity port (skeleton-taming AI goals).
		if (player.tickCount % 20 == 0) {
			for (AbstractSkeleton Skeleton : level.getEntitiesOfClass(AbstractSkeleton.class, player.getBoundingBox().inflate(16.0D))) {
				// ... strip NearestAttackableTargetGoal, add owner-follow + owner-hurt goals, tag "FUR_tameSkeleton"
			}
		}
		*/
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot armorSlot, String type) {
		return mod_LavaCow.MODID + ":textures/armors/kings_crown/kings_crown.png";
	}

	// NOTE: the bespoke ModelCrown worn-model from 1.16.5 is deferred; the crown
	// renders on the default helmet model for now. See PLACEHOLDERS.md.

	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.YELLOW));
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		if (ModList.get().isLoaded("curios")) {
			return new CuriosProvider(stack);
		}
		return super.initCapabilities(stack, nbt);
	}

	private class CuriosProvider implements ICapabilityProvider {
		private final LazyOptional<ICurio> curio;

		CuriosProvider(ItemStack stack) {
			this.curio = LazyOptional.of(() -> new ICurio() {
				@Override
				public ItemStack getStack() {
					return stack;
				}

				@Override
				public void curioTick(SlotContext slotContext) {
					if (slotContext.entity() instanceof Player player) {
						SkeletonKingCrownItem.this.onArmorTick(stack, player.level(), player);
					}
				}
			});
		}

		@Nonnull
		@Override
		public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
			return CuriosCapability.ITEM.orEmpty(cap, this.curio);
		}
	}
}
