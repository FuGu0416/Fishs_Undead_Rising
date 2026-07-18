package com.Fishmod.fur.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.entities.ai.EntityAIFollowEntity;
import com.Fishmod.fur.entities.ai.SkeletonOwnerHurtByTargetGoal;
import com.Fishmod.fur.entities.ai.SkeletonOwnerHurtTargetGoal;
import com.Fishmod.fur.init.FURItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;

import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class SkeletonKingCrownItem extends ArmorItem {
	private final List<Goal> remove = new ArrayList<Goal>();

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
	 * Called to tick armor in the armor slot. Forces nearby skeletons to become
	 * loyal servants of the wearer: strips their target goals and adds
	 * owner-follow / owner-hurt goals (ported with the SkeletonKing entity).
	 */
	@Override
	public void onArmorTick(ItemStack itemStack, Level level, Player player) {
		if (player.tickCount % 20 == 0) {
			for (AbstractSkeleton skeleton : level.getEntitiesOfClass(AbstractSkeleton.class, player.getBoundingBox().inflate(16.0D))) {
				this.remove.clear();

				skeleton.targetSelector.getRunningGoals().forEach((k) -> {
					if (k.getGoal() instanceof NearestAttackableTargetGoal<?>) {
						this.remove.add(k.getGoal());
					}
				});

				for (Goal e : this.remove) {
					skeleton.targetSelector.removeGoal(e);
				}

				if (!skeleton.getTags().contains("FUR_tameSkeleton") && ((skeleton.getNavigation() instanceof GroundPathNavigation) || (skeleton.getNavigation() instanceof FlyingPathNavigation))) {
					skeleton.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.0F, 1.0F);
					for (int i = 0; i < 16; ++i) {
						double d0 = skeleton.getRandom().nextGaussian() * 0.02D;
						double d1 = skeleton.getRandom().nextGaussian() * 0.02D;
						double d2 = skeleton.getRandom().nextGaussian() * 0.02D;
						player.level().addParticle(ParticleTypes.ENTITY_EFFECT, skeleton.getRandomX(1.0D), skeleton.getRandomY() + 1.0D, skeleton.getRandomZ(1.0D), d0, d1, d2);
					}

					skeleton.goalSelector.addGoal(6, new EntityAIFollowEntity(skeleton, player.getUUID(), 1.0D, 10.0F, 2.0F));
					skeleton.targetSelector.addGoal(1, new SkeletonOwnerHurtByTargetGoal(skeleton, player.getUUID()));
					skeleton.targetSelector.addGoal(2, new SkeletonOwnerHurtTargetGoal(skeleton, player.getUUID()));
					skeleton.addTag("FUR_tameSkeleton");
				}
			}
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot armorSlot, String type) {
		return mod_LavaCow.MODID + ":textures/armors/kings_crown/kings_crown.png";
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept((IClientItemExtensions) mod_LavaCow.PROXY.getArmorProperties());
	}

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
