package com.Fishmod.mod_LavaCow.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIDestroyCrops extends EntityAIBase {

	protected final EntityCreature entity;
	public int destroyTicks;
	private boolean isHarvest;
    private final double movementSpeed;
    /** Controls task execution delay */
    protected int runDelay;
    private int timeoutCounter;
    private int maxStayTicks;
    /** Block to move to */
    protected BlockPos destinationBlock = BlockPos.ORIGIN;
    private boolean isAboveDestination;
    private final int searchLength;
	
	public EntityAIDestroyCrops(EntityCreature creature, double speedIn, boolean isHarvestIn) {
		this.entity = creature;
		this.destroyTicks = 0;
		this.isHarvest = isHarvestIn;
        this.movementSpeed = speedIn;
        this.searchLength = 16;
        this.setMutexBits(5);
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
        else if (this.runDelay > 0)
        {
            --this.runDelay;
            return false;
        }
        else
        {
            this.runDelay = 40 + this.entity.getRNG().nextInt(40);
            return this.searchForDestination();
        }
    }
    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.entity.world, this.destinationBlock);
    }
    
    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()) + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
        this.timeoutCounter = 0;
        this.maxStayTicks = this.entity.getRNG().nextInt(this.entity.getRNG().nextInt(1200) + 1200) + 1200;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if (this.entity.getDistanceSqToCenter(this.destinationBlock.up()) > 1.0D)
        {
            this.isAboveDestination = false;
            ++this.timeoutCounter;

            if (this.timeoutCounter % 40 == 0)
            {
                this.entity.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()) + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
            }
        }
        else
        {
            this.isAboveDestination = true;
            --this.timeoutCounter;
        }

        this.entity.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.entity.getVerticalFaceSpeed());

        if (this.getIsAboveDestination()) {
        	World world = this.entity.world;
            BlockPos blockpos = this.destinationBlock.up();
            
            this.destroyTicks++;
            
            if (this.destroyTicks > 30) {
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
        BlockPos blockpos = new BlockPos(this.entity);

        for (int k = 0; k <= 1; k = k > 0 ? -k : 1 - k)
        {
            for (int l = 0; l < i; ++l)
            {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1)
                {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1)
                    {
                        BlockPos blockpos1 = blockpos.add(i1, k - 1, j1);

                        if (this.entity.isWithinHomeDistanceFromPosition(blockpos1) && this.shouldMoveTo(this.entity.world, blockpos1))
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

        if (!this.isHarvest && block instanceof BlockBush)
        {
            return true;
        }

        return false;
	}

}
