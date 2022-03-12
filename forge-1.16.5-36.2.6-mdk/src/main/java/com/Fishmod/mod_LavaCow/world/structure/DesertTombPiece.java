package com.Fishmod.mod_LavaCow.world.structure;

import java.util.Random;

import com.Fishmod.mod_LavaCow.entities.tameable.MimicEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;
import com.Fishmod.mod_LavaCow.init.FURItemRegistry;

import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.ScatteredStructurePiece;
import net.minecraft.world.gen.feature.structure.StructureManager;

public class DesertTombPiece extends ScatteredStructurePiece {

	protected DesertTombPiece(Random p_i48652_1_, int p_i48652_2_, int p_i48652_3_) {
		super(IStructurePieceType.JIGSAW, p_i48652_1_, p_i48652_2_, 64, p_i48652_3_, 7, 7, 9);
	}

	@Override
	public boolean postProcess(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_,
			Random rand, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
		BlockPos pos;
        int GenMimic = rand.nextInt(2);
        int l = 0;		

		for(int j = p_230383_5_.y1 ; j >= p_230383_5_.y0; j--)
			for(int i = p_230383_5_.x0 + 7 ; i <= p_230383_5_.x1 + 7; i++)			
				for(int k = p_230383_5_.z0 - 7 ; k <= p_230383_5_.z1 - 7; k++) {
					pos = new BlockPos(i, j, k);
					if(p_230383_1_.getBlockState(pos).getBlock() == Blocks.CHEST) {
						if (GenMimic == l) {	
							Direction Chestfacing = p_230383_1_.getBlockState(pos).getValue(ChestBlock.FACING);
							p_230383_1_.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
							
							MimicEntity Mimic = FUREntityRegistry.MIMIC.create(p_230383_1_.getLevel());
							Mimic.setPersistenceRequired();
							Mimic.moveTo((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);							
							Mimic.finalizeSpawn(p_230383_1_, p_230383_1_.getCurrentDifficultyAt(new BlockPos(i, j, k)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
							Mimic.setSkin(7);					
							Mimic.inventory.setItem(0, new ItemStack(FURItemRegistry.GHOSTJELLY/* FishItems.KINGS_CROWN*/, 1));
							Mimic.doMimicChest(Chestfacing);						
							p_230383_1_.addFreshEntityWithPassengers(Mimic);
							break;
						}
						l++;
					}

				}

		return true;
	}

}
