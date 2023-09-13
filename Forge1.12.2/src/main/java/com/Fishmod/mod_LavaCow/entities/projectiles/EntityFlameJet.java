package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.mod_LavaCow;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityFlameJet extends EntityThrowable {
	
	private float damage = 6.0F;
	
	public EntityFlameJet(World worldIn) {
        super(worldIn);
    }

    public EntityFlameJet(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityFlameJet(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public static void registerFixesAcidJet(DataFixer fixer) {
        EntityThrowable.registerFixesThrowable(fixer, "FlameJet");
    }
    
    @Override
    public void onUpdate() {
       super.onUpdate();
       if(this.getEntityWorld().isRemote)
    	   for(int i = 0 ; i < 8 + this.rand.nextInt(8) ; i++) {
    		   mod_LavaCow.PROXY.spawnCustomParticle("ectoplasm", world, this.posX + this.rand.nextDouble() * 0.5D, this.posY + 0.5D + this.rand.nextDouble() * 0.5D, this.posZ + this.rand.nextDouble() * 0.5D, 0.0D, 0.0D, 0.0D, 1.0F, 1.0F, 1.0F);
    	   }
    }

	   protected void onImpact(RayTraceResult result) {
	        if (!this.world.isRemote)
	        {
	            if (result.entityHit != null)
	            {
	                if (!result.entityHit.isImmuneToFire())
	                {
	                    boolean flag = result.entityHit.attackEntityFrom(DamageSource.ON_FIRE, 5.0F);

	                    if (flag)
	                    {
	                        this.applyEnchantments(this.thrower, result.entityHit);
	                        result.entityHit.setFire(10);
	                    }
	                }
	            }
	            else
	            {
	                boolean flag1 = true;

	                if (this.thrower != null && this.thrower instanceof EntityLiving)
	                {
	                    ForgeEventFactory.getMobGriefingEvent(this.world, this.thrower);
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

	            this.setDead();
	        }
	   }

	   /**
	    * Returns true if other Entities should be prevented from moving through this Entity.
	    */
	   public boolean canBeCollidedWith() {
	      return false;
	   }
	   
	   public void setDamage(float damageIn) {
		   this.damage = damageIn;
	   }

	   public float getDamage() {
		   return this.damage;
	   }
	   
	   protected EnumParticleTypes getParticleType() {
	        return EnumParticleTypes.SMOKE_NORMAL;
	    }
	}

