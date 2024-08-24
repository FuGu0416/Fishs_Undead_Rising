package com.Fishmod.mod_LavaCow.entities.projectiles;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.entities.EntityForsaken;
import com.Fishmod.mod_LavaCow.entities.EntitySkeletonKing;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDeathCoil extends EntityWitherSkull implements IEntityAdditionalSpawnData {
	private float damage = 4.0F;
	protected int knockbackStrength;
	protected boolean flame = false;
	
    public EntityDeathCoil(World worldIn) {
        super(worldIn);
    }

    public EntityDeathCoil(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
    }

    public static void registerFixesAcidJet(DataFixer fixer) {
        EntityThrowable.registerFixesThrowable(fixer, "DeathCoil");
    }
    
    @SideOnly(Side.CLIENT)
    public EntityDeathCoil(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
    }
    
    @Override
    public void writeSpawnData(ByteBuf data) {
        data.writeInt(this.shootingEntity != null ? this.shootingEntity.getEntityId() : -1);
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
       
       if (this.world.isRemote || (this.shootingEntity == null || !this.shootingEntity.isDead) && this.world.isBlockLoaded(new BlockPos(this))) {
           mod_LavaCow.PROXY.spawnCustomParticle("wither_flame", world, this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
       }
       
       if(this.ticksExisted >= 10 * 20) {
    	   this.setDead();
       }
    }
    
    protected float getMotionFactor() {
	      return 1.0F;
    }
    
    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void shoot(double x, double y, double z, float velocity, float inaccuracy)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double)inaccuracy;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }
    
    public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
    {
        float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
        float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        this.motionX += entityThrower.motionX;
        this.motionZ += entityThrower.motionZ;

        if (!entityThrower.onGround)
        {
            this.motionY += entityThrower.motionY;
        }
    }
    
	/**
	* Called when this EntityFireball hits a block or entity.
	*/
    @Override
    protected void onImpact(RayTraceResult result) {
    	Entity shooter = this.shootingEntity;
    	Entity target = result.entityHit;
    	
        if (!this.world.isRemote) {
        	if (target != null && shooter != null && target instanceof EntityLivingBase && target != shooter && !target.isOnSameTeam(shooter)) {
	    		if(!(shooter instanceof EntityPlayer)) {
		    		this.setDamage((float)((EntityLivingBase)shooter).getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
	    		}	
	    	
	    		if (target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, (EntityLivingBase)shooter).setProjectile(), this.getDamage())) {
	    			float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
		    		((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.WITHER, 10 * 20 * (int)local_difficulty, 1));
		    		
		    		if (shooter instanceof EntityForsaken && ((EntityForsaken) shooter).getOwner() instanceof EntitySkeletonKing) { 
		    			((EntityLivingBase)target).addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE_KING, 10 * 20 * (int)local_difficulty, 2));
		    		} else {
		    			((EntityLivingBase)target).addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE, 10 * 20 * (int)local_difficulty, 2));
		    		}
		    	
	    			if (this.isBurning()) {
	    				target.setFire(5);
	    			}
	    	
	    			if (this.knockbackStrength > 0) {
                		float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                		if (f1 > 0.0F) {
                			target.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6D / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6D / (double)f1);
                		}
            		}
            
            		if (shooter instanceof EntityLivingBase) {
            			this.applyEnchantments((EntityLivingBase) shooter, target);
            		}
            	}
	    	}
	    }
        
        if (result.entityHit != null && result.entityHit == shooter) return;
	    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
		this.playSound(FishItems.ENTITY_SALAMANDER_SHOOT, 1.0F, 3.0F / (this.world.rand.nextFloat() * 0.4F + 1.2F));
	    this.setDead();
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
    
	public void setKnockbackStrength(int knockbackStrengthIn) {
		this.knockbackStrength = knockbackStrengthIn;
	}
	
    public void setFlame(boolean flameIn) {
    	this.flame = flameIn ? true : false;
    }

	protected EnumParticleTypes getParticleType() {
	    return EnumParticleTypes.TOWN_AURA;
	}
	
	@Override
    public boolean isBurning() {
        boolean flag = this.world != null && this.world.isRemote;
        return !this.isImmuneToFire && (flame || flag && this.getFlag(0));
    }
}

