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
	public boolean postProcess(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random rand, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
		if (!this.updateAverageGroundHeight(p_230383_1_, p_230383_5_, -9)) {
			return false;
		} else {
			BlockPos pos;
	        int GenMimic = rand.nextInt(4);
	        int l = 1;		
			Boolean isBadland = false;
			
	        /*for(int i = -8; i < 8; i++) {
	        	for(int j = -8; j < 8; j++) {
	        		for(int k = -8; k < 8; k++) {
	        			if (p_230383_1_.getBlockState(p_230383_7_.offset(i, j, k)).getBlock().equals(Blocks.RED_SAND) || p_230383_1_.getBlockState(p_230383_7_.offset(i, j, k)).getBlock().equals(Blocks.RED_SANDSTONE)) {
	        				isBadland = true;
	        				break;
	        			}
	        		}
	        		
	        		if(isBadland) {
	        			break;
	        		}
	        	}
	        	
	    		if(isBadland) {
	    			break;
	    		}
	        }*/
	        
	        for(int k1 = 0; k1 < this.width; ++k1) {
	            for(int j = 0; j < this.depth; ++j) {
	            	if(isBadland) {
	            		this.fillColumnDown(p_230383_1_, Blocks.RED_SAND.defaultBlockState(), k1, -8, j, p_230383_5_);
	            	} else {
	            		this.fillColumnDown(p_230383_1_, Blocks.SAND.defaultBlockState(), k1, -8, j, p_230383_5_);
	            	}
	            }
	        }
	        
			for(int j = p_230383_5_.y1 ; j >= p_230383_5_.y0; j--)
				for(int i = p_230383_5_.x0 + 7 ; i <= p_230383_5_.x1 + 7; i++)			
					for(int k = p_230383_5_.z0 - 7 ; k <= p_230383_5_.z1 - 7; k++) {
						pos = new BlockPos(i, j, k);
						if(p_230383_1_.getBlockState(pos).getBlock() == Blocks.CHEST) {						
							if (GenMimic < l) {	
								Direction Chestfacing = p_230383_1_.getBlockState(pos).getValue(ChestBlock.FACING);
								p_230383_1_.removeBlock(pos, false);
								
								MimicEntity Mimic = FUREntityRegistry.MIMIC.create(p_230383_1_.getLevel());
								Mimic.setPersistenceRequired();
								Mimic.moveTo((double)i + 0.5D, (double)j, (double)k + 0.5D, 0.0F, 0.0F);							
								Mimic.finalizeSpawn(p_230383_1_, p_230383_1_.getCurrentDifficultyAt(new BlockPos(i, j, k)), SpawnReason.STRUCTURE, (ILivingEntityData)null, (CompoundNBT)null);
								Mimic.setSkin(7);					
								Mimic.inventory.setItem(0, new ItemStack(FURItemRegistry.STAINED_KINGS_CROWN, 1));
								Mimic.doMimicChest(Chestfacing);						
								p_230383_1_.addFreshEntityWithPassengers(Mimic);
								break;
							}
							l++;
						}
					}
			
			if(isBadland) {
	            for(int i = this.boundingBox.x0; i <= this.boundingBox.x1; ++i) {
	                for(int j = this.boundingBox.y0; j <= this.boundingBox.y1; ++j) {
	                	for(int k = this.boundingBox.z0 ; k <= this.boundingBox.z1; k++) {
	                		pos = new BlockPos(i, j, k);
	                		if (p_230383_5_.isInside(pos)) {
		            			if (p_230383_1_.getBlockState(pos).getBlock().equals(Blocks.SAND)) {
		            				p_230383_1_.setBlock(pos, Blocks.RED_SAND.defaultBlockState(), 2);
		            			}
		            			
		            			if (p_230383_1_.getBlockState(pos).getBlock().equals(Blocks.SANDSTONE)) {
		            				p_230383_1_.setBlock(pos, Blocks.RED_SANDSTONE.defaultBlockState(), 2);
		            			}
		            			
		            			if (p_230383_1_.getBlockState(pos).getBlock().equals(Blocks.CHISELED_SANDSTONE)) {
		            				p_230383_1_.setBlock(pos, Blocks.CHISELED_RED_SANDSTONE.defaultBlockState(), 2);
		            			}
		            			
		            			if (p_230383_1_.getBlockState(pos).getBlock().equals(Blocks.SMOOTH_SANDSTONE)) {
		            				p_230383_1_.setBlock(pos, Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState(), 2);
		            			}
		            			
		            			if (p_230383_1_.getBlockState(pos).getBlock().equals(Blocks.SANDSTONE_STAIRS)) {
		            				p_230383_1_.setBlock(pos, Blocks.RED_SANDSTONE_STAIRS.defaultBlockState(), 2);
		            			}         			
		            			
		            			if (p_230383_1_.getBlockState(pos).getBlock().equals(Blocks.SANDSTONE_SLAB)) {
		            				p_230383_1_.setBlock(pos, Blocks.RED_SANDSTONE_SLAB.defaultBlockState(), 2);
		            			}
		            			
		            			if (p_230383_1_.getBlockState(pos).getBlock().equals(Blocks.BROWN_TERRACOTTA)) {
		            				p_230383_1_.setBlock(pos, Blocks.YELLOW_TERRACOTTA.defaultBlockState(), 2);
		            			}
	                		}
	                	}              	
	                }
	            }
			}
	
			return true;
		}
	}

}
