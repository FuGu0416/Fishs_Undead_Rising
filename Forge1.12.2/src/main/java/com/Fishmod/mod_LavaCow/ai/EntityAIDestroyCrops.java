package com.Fishmod.mod_LavaCow.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIDestroyCrops extends EntityAIMoveToBlock {

	protected final EntityCreature entity;
	public int destroyTicks;
	private boolean isHarvest;
	
	public EntityAIDestroyCrops(EntityCreature creature, double speedIn, boolean isHarvestIn) {
		super(creature, speedIn, 16);
		this.entity = creature;
		this.destroyTicks = 0;
		this.isHarvest = isHarvestIn;
	}
	
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entity.world, this.entity))
        {
            return false;
        }

        return super.shouldExecute();
    }
    
    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask() {
        super.updateTask();
        this.entity.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.entity.getVerticalFaceSpeed());

        if (this.getIsAboveDestination()) {
        	World world = this.entity.world;
            BlockPos blockpos = this.destinationBlock.up();
            
            this.destroyTicks++;
            
            if (this.destroyTicks > 60) {
            	Block block = world.getBlockState(blockpos).getBlock();
            	
            	world.destroyBlock(blockpos, true);
            	if(this.isHarvest) {
            		world.setBlockState(blockpos, block.getDefaultState(), 3);
            		world.sendBlockBreakProgress(entity.getEntityId(), blockpos, 0);
            	}
            }
            else if (this.destroyTicks % 10 == 0)
            	world.sendBlockBreakProgress(entity.getEntityId(), blockpos, this.destroyTicks / 10);	
        }
    }

	@Override
	protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();

        if (block == Blocks.FARMLAND)
        {
            pos = pos.up();
            IBlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();

            if (block instanceof BlockCrops && (!this.isHarvest || ((BlockCrops)block).isMaxAge(iblockstate)))
            {
                return true;
            }
        }

        pos = pos.up();
        IBlockState iblockstate = worldIn.getBlockState(pos);
        block = iblockstate.getBlock();

        if (block instanceof BlockBush)
        {
            return true;
        }

        return false;
	}

}
