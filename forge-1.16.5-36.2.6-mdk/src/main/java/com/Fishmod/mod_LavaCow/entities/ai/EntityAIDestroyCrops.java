package com.Fishmod.mod_LavaCow.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIDestroyCrops extends Goal {

	protected final CreatureEntity entity;
	public int destroyTicks;
	private boolean isHarvest;
    private final double movementSpeed;
    /** Controls task execution delay */
    protected int runDelay;
    private int timeoutCounter;
    private int maxStayTicks;
    /** Block to move to */
    protected BlockPos destinationBlock = BlockPos.ZERO;
    private boolean isAboveDestination;
    private final int searchLength;
	
	public EntityAIDestroyCrops(CreatureEntity creature, double speedIn, boolean isHarvestIn) {
		this.entity = creature;
		this.destroyTicks = 0;
		this.isHarvest = isHarvestIn;
        this.movementSpeed = speedIn;
        this.searchLength = 16;
	}
	    
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse()
    {
        if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.entity.level, this.entity))
        {
            return false;
        }
        else if (this.runDelay > 0)
        {
            --this.runDelay;
            return false;
        }
        else
        {
            this.runDelay = 40 + this.entity.getRandom().nextInt(40);
            return this.searchForDestination();
        }
    }
    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse()
    {
        return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.entity.level, this.destinationBlock);
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start()
    {
        this.entity.getNavigation().moveTo((double)((float)this.destinationBlock.getX()) + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
        this.timeoutCounter = 0;
        this.maxStayTicks = this.entity.getRandom().nextInt(this.entity.getRandom().nextInt(1200) + 1200) + 1200;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick()
    {
        if (this.entity.distanceToSqr(this.destinationBlock.above().getX(), this.destinationBlock.above().getY(), this.destinationBlock.above().getZ()) > 1.0D)
        {
            this.isAboveDestination = false;
            ++this.timeoutCounter;

            if (this.timeoutCounter % 40 == 0)
            {
                this.entity.getNavigation().moveTo((double)((float)this.destinationBlock.getX()) + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
            }
        }
        else
        {
            this.isAboveDestination = true;
            --this.timeoutCounter;
        }

        this.entity.getLookControl().setLookAt((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.entity.getMaxHeadXRot());

        if (this.getIsAboveDestination()) {
        	World world = this.entity.level;
            BlockPos blockpos = this.destinationBlock.above();
            
            this.destroyTicks++;
            
            if (this.destroyTicks > 30) {
            	Block block = world.getBlockState(blockpos).getBlock();
            	
            	world.destroyBlock(blockpos, true);
            	if(this.isHarvest) {
            		world.setBlock(blockpos, block.defaultBlockState(), 3);
            		world.destroyBlockProgress(entity.getId(), blockpos, 0);
            	}
            }
            else if (this.destroyTicks % 10 == 0)
            	world.destroyBlockProgress(entity.getId(), blockpos, this.destroyTicks / 10);	
        }
    }
    
    protected boolean getIsAboveDestination()
    {
        return this.isAboveDestination;
    }
    
    /**
     * Searches and sets new destination block and returns true if a suitable block (specified in {@link
     * net.minecraft.entity.ai.EntityAIMoveToBlock#shouldMoveTo(World, BlockPos) EntityAIMoveToBlock#shouldMoveTo(World,
     * BlockPos)}) can be found.
     */
    private boolean searchForDestination()
    {
        int i = this.searchLength;
        BlockPos blockpos = this.entity.blockPosition();

        for (int k = 0; k <= 1; k = k > 0 ? -k : 1 - k)
        {
            for (int l = 0; l < i; ++l)
            {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1)
                {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1)
                    {
                        BlockPos blockpos1 = blockpos.offset(i1, k - 1, j1);

                        if (this.entity.isWithinRestriction(blockpos1) && this.shouldMoveTo(this.entity.level, blockpos1))
                        {
                            this.destinationBlock = blockpos1;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Return true to set given position as destination
     */
	protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();

        if (block == Blocks.FARMLAND)
        {
            pos = pos.above();
            BlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();

            if (block instanceof CropsBlock && (!this.isHarvest || ((CropsBlock)block).isMaxAge(iblockstate)))
            {
                return true;
            }
        }

        pos = pos.above();
        BlockState iblockstate = worldIn.getBlockState(pos);
        block = iblockstate.getBlock();

        if (!this.isHarvest && block instanceof BushBlock)
        {
            return true;
        }

        return false;
	}

}
