package com.Fishmod.mod_LavaCow.item;

import java.util.List;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.SkeletonKingEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURSoundRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class CrownItem extends Item {    
	private final int type;
	
	public CrownItem(Properties PropertiesIn, int typeIn) {
		super(PropertiesIn);
		this.type = typeIn;		
	}
	
    /**
     * Called when a Block is right-clicked with this Item
     */
	@Override
	public ActionResultType useOn(ItemUseContext ContextIn) {
		PlayerEntity player = ContextIn.getPlayer();
		Hand hand = ContextIn.getHand();
		BlockPos pos = ContextIn.getClickedPos();
		World worldIn = ContextIn.getLevel();
        if(this.type == 1 && FURConfig.pSpawnRate_SkeletonKing.get()
        		&& worldIn.getBlockState(pos).getBlock().equals(Blocks.SKELETON_SKULL)
        		&& BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(pos))).contains(Type.HOT)
        		&& BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(pos))).contains(Type.DRY)
        		&& BiomeDictionary.getTypes(SpawnUtil.getRegistryKey(worldIn.getBiome(pos))).contains(Type.SANDY)) {
        			if(!player.isCreative())
    					player.getItemInHand(hand).shrink(1);
        			
        			worldIn.destroyBlock(pos, false);
        			LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(worldIn);
                    lightningboltentity.moveTo(Vector3d.atBottomCenterOf(pos));
                    lightningboltentity.setCause(player instanceof ServerPlayerEntity ? (ServerPlayerEntity)player : null);
                    worldIn.addFreshEntity(lightningboltentity);
                    
    				if(!worldIn.isClientSide()) {
    		        	SkeletonKingEntity entityskeletonking = FUREntityRegistry.SKELETONKING.create(worldIn);  		        	
    		        	entityskeletonking.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
    		        	worldIn.addFreshEntity(entityskeletonking);
    		        	entityskeletonking.finalizeSpawn(entityskeletonking.getServer().overworld(), entityskeletonking.level.getCurrentDifficultyAt(pos), SpawnReason.MOB_SUMMONED, null, (CompoundNBT)null);
    		        	entityskeletonking.setInvulnerableTicks(150);
    		        	entityskeletonking.setHealth(entityskeletonking.getMaxHealth() * 0.10F);
    	        	}
    				
    				worldIn.playSound(player, pos, FURSoundRegistry.SKELETONKING_SPAWN, SoundCategory.HOSTILE, 1.0F, 1.0F);
    				
        			return ActionResultType.SUCCESS;
        }
        
        return super.useOn(ContextIn);
    }
    
	@Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:kings_crown." + this.type));
		tooltip.add(new TranslationTextComponent(""));
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:kings_crown." + this.type + ".desc0").withStyle(TextFormatting.ITALIC));
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:kings_crown." + this.type + ".desc1").withStyle(TextFormatting.ITALIC));
		tooltip.add(new TranslationTextComponent("tootip.mod_lavacow:kings_crown." + this.type + ".desc2").withStyle(TextFormatting.ITALIC));
	}
}
