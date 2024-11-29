package com.Fishmod.mod_LavaCow.item;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.capability.SimpleCapProvider;
import com.Fishmod.mod_LavaCow.client.model.armor.ModelCrown;
import com.Fishmod.mod_LavaCow.entities.ai.EntityAIFollowEntity;
import com.Fishmod.mod_LavaCow.entities.ai.SkeletonOwnerHurtByTargetGoal;
import com.Fishmod.mod_LavaCow.entities.ai.SkeletonOwnerHurtTargetGoal;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class SkeletonKingCrownItem extends ArmorItem {
	private List<Goal> remove = new ArrayList<Goal>();
	
	public SkeletonKingCrownItem(Item.Properties p_i48534_3_) {
		super(ArmorMaterial.DIAMOND, EquipmentSlotType.HEAD, p_i48534_3_);
	}
	
    /**
     * Returns true if this item has an enchantment glint. By default, this returns
     * <code>stack.isItemEnchanted()</code>, but other items can override it (for instance, written books always return
     * true).
     *  
     * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
     * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
     */
	@Override
	public boolean isFoil(ItemStack stack) {
        return true;
    }
    
	@Override
	public boolean isValidRepairItem(ItemStack armour, ItemStack material) {
		return material.getItem() == FURItemRegistry.HATRED_SHARD;
	}
    
    /**
     * Called to tick armor in the armor slot. Override to do something
     */
	@Override
    public void onArmorTick(ItemStack itemStack, World world, PlayerEntity player) {	
    	if(player.tickCount % 20 == 0) {    			    	
	    	for (AbstractSkeletonEntity Skeleton : world.getEntitiesOfClass(AbstractSkeletonEntity.class, player.getBoundingBox().inflate(16.0D))) {   		
	    		this.remove.clear();
	    		
				Skeleton.targetSelector.getRunningGoals().forEach((k) -> {
					if(k.getGoal() instanceof NearestAttackableTargetGoal<?>) {
						this.remove.add(k.getGoal());			
					}
				});				
								
				for(Goal e : this.remove) {
					Skeleton.targetSelector.removeGoal(e);
				}
				
	    		if(!Skeleton.getTags().contains("FUR_tameSkeleton") && ((Skeleton.getNavigation() instanceof GroundPathNavigator) || (Skeleton.getNavigation() instanceof FlyingPathNavigator))) {
	    			Skeleton.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.0F, 1.0F);
		            for(int i = 0; i < 16; ++i) {
		                double d0 = Item.random.nextGaussian() * 0.02D;
		                double d1 = Item.random.nextGaussian() * 0.02D;
		                double d2 = Item.random.nextGaussian() * 0.02D;
		                player.level.addParticle(ParticleTypes.ENTITY_EFFECT, Skeleton.getRandomX(1.0D), Skeleton.getRandomY() + 1.0D, Skeleton.getRandomZ(1.0D), d0, d1, d2);
		            }

		    		Skeleton.goalSelector.addGoal(6, new EntityAIFollowEntity(Skeleton, player.getUUID(), 1.0D, 10.0F, 2.0F));
		    		Skeleton.targetSelector.addGoal(1, new SkeletonOwnerHurtByTargetGoal(Skeleton, player.getUUID()));
		    		Skeleton.targetSelector.addGoal(2, new SkeletonOwnerHurtTargetGoal(Skeleton, player.getUUID()));		
		    		Skeleton.addTag("FUR_tameSkeleton");
			    }		
	    	}
    	}
    }
    
	@Override
    @OnlyIn(Dist.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType armorSlot, String type) {
		return "mod_lavacow:textures/armors/kings_crown/kings_crown.png";
	}
	
	@SuppressWarnings("unchecked")
	@Override
    @OnlyIn(Dist.CLIENT)
	public <E extends BipedModel<?>> E getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, E _default) {
		return (E) new ModelCrown<>(1.0F);
	}
	
	@Override
    @OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tooltip.mod_lavacow:skeletonking_crown").withStyle(TextFormatting.YELLOW));
	}
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT unused) {
    	if (ModList.get().isLoaded("curios")) {
	    	return new SimpleCapProvider<>(CuriosCapability.ITEM, new ICurio() {
		        @Override
		        public void curioTick(String identifier, int index, LivingEntity livingEntity) {
		        	if (livingEntity instanceof PlayerEntity) {
		        		stack.onArmorTick(((PlayerEntity)livingEntity).level,(PlayerEntity)livingEntity);
		        	}
		        }
	    	});
    	}
    	
    	return super.initCapabilities(stack, unused);
    }
}
