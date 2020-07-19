package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.entities.tameable.EntitySalamander;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityWarSmallFireball extends EntityEnchantableFireBall {
	
	//private float damage = 5.0F;
	
	   public EntityWarSmallFireball(World worldIn) {
	      super(worldIn);
	   }

	   public EntityWarSmallFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
	      super(worldIn, shooter, accelX, accelY, accelZ);
	   }

	   public EntityWarSmallFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
	      super(worldIn, x, y, z, accelX, accelY, accelZ);
	   }
	   
	    public static void registerFixesSmallFireball(DataFixer fixer)
	    {
	        EntityFireball.registerFixesFireball(fixer, "WarSmallFireball");
	    }

	    /**
	     * Called when this EntityFireball hits a block or entity.
	     */
	    protected void onImpact(RayTraceResult result)
	    {
	        if (!this.world.isRemote)
	        {
	            if (result.entityHit != null)
	            {
	                if (!result.entityHit.isImmuneToFire())
	                {
	                    if(this.shootingEntity instanceof EntitySalamander)this.setDamage((float) this.shootingEntity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
	                	boolean flag = result.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), this.getDamage());
		            	
	                    if (this.knockbackStrength > 0) {
		                    float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		                    if (f1 > 0.0F) {
		                    	result.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * (double)0.6F / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * (double)0.6F / (double)f1);
		                    }
		                 }
	                    
	                    if (flag)
	                    {
	                        this.applyEnchantments(this.shootingEntity, result.entityHit);
	                        result.entityHit.setFire(5 + flame);
	                    }
	                }
	            }
	            else
	            {
	                boolean flag1 = true;

	                if (this.shootingEntity != null && this.shootingEntity instanceof EntityLiving)
	                {
	                    flag1 = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.shootingEntity);
	                }

	                if (flag1)
	                {
	                    BlockPos blockpos = result.getBlockPos().offset(result.sideHit);

	                    if (this.world.isAirBlock(blockpos))
	                    {
	                        this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
	                    }
	                }
	            }
	        }
	        
	        if (result.entityHit != null && result.entityHit == this.shootingEntity)
	        	return;
	        this.setDead();
	    }
	}

