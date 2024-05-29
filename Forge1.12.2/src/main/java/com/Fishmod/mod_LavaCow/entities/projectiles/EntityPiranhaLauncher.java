package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.entities.aquatic.EntityZombiePiranha;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityPiranhaLauncher extends EntityEnchantableFireBall implements IEntityAdditionalSpawnData {
	
	   public EntityPiranhaLauncher(World worldIn) {
		   super(worldIn);
		   this.setDamage(this.getDamage() + 3.0F);
	   }

	   public EntityPiranhaLauncher(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
		   super(worldIn, shooter, accelX, accelY, accelZ);
		   this.setDamage(this.getDamage() + 3.0F);
	   }

	   public EntityPiranhaLauncher(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		   super(worldIn, x, y, z, accelX, accelY, accelZ);
		   this.setDamage(this.getDamage() + 3.0F);
	   }
	   
	    public static void registerFixesSmallFireball(DataFixer fixer)
	    {
	        EntityFireball.registerFixesFireball(fixer, "PiranhaLauncher");
	    }
	    
	    @Override
	    public void writeSpawnData(ByteBuf data) {
	        data.writeInt(shootingEntity != null ? shootingEntity.getEntityId() : -1);
	    }

	    @Override
	    public void readSpawnData(ByteBuf data) {
	        final Entity shooter = world.getEntityByID(data.readInt());

	        if (shooter instanceof EntityLivingBase) {
	            this.shootingEntity = (EntityLivingBase)shooter;
	        }
	    }
	   
	    @Override
	    public void onUpdate() {
	       super.onUpdate();
	       if(!this.collided)this.accelerationY -= 0.002f;
	    }

	   /**
	    * Called when this EntityFireball hits a block or entity.
	    */
	   protected void onImpact(RayTraceResult result) {
	    Entity shooter = this.shootingEntity;
	    Entity target = result.entityHit;
	    
	      if (!this.world.isRemote) {
	    	  EntityZombiePiranha entityzombie = new EntityZombiePiranha(this.world);
	    	  entityzombie.setIsAmmo(true);
	    	  
	        if (target != null && shooter != null && target instanceof EntityLivingBase && target != shooter && !target.isOnSameTeam(shooter)) {
	    		  entityzombie.setPosition(target.posX, target.posY + target.height, target.posZ);	    		  
	    		  entityzombie.startRiding(target);
	    		  this.world.spawnEntity(entityzombie);
	    		  entityzombie.setAttackTarget((EntityLivingBase) target);
	    		  
	            if (shooter != null) {
	            	if (target.attackEntityFrom(DamageSource.causeIndirectDamage(this, (EntityLivingBase)shooter).setProjectile(), this.getDamage())) {        		            		            	            	
		            	if (this.knockbackStrength > 0) {
		                    float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		                    
		                    if (f1 > 0.0F) {
		                    	target.addVelocity(this.motionX * (double)this.knockbackStrength * (double)0.6F / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * (double)0.6F / (double)f1);
		                    }
		                 }
		            	
		            	if(this.isBurning())
		            		target.setFire(5);
	            	}
	            }
	            
	    	  }
	    	  else {
	    		  entityzombie.setPosition(result.hitVec.x, result.hitVec.y + 1.5D, result.hitVec.z);
	    		  this.world.spawnEntity(entityzombie);	    		  
	    	  }
	      }
	      
	      if (target != null && target == this.shootingEntity) return;
	      this.setDead();
	   }
	   
	    protected EnumParticleTypes getParticleType()
	    {
	        return EnumParticleTypes.WATER_SPLASH;
	    }
	}

