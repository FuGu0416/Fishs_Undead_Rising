package com.Fishmod.mod_LavaCow.entities.projectiles;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.entities.ForsakenEntity;
import com.Fishmod.mod_LavaCow.entities.SkeletonKingEntity;
import com.Fishmod.mod_LavaCow.init.FUREntityRegistry;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SandBurstEntity extends Entity
{
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks = 22;
    private boolean clientSideAttackStarted;
    private LivingEntity caster;
    private UUID ownerUUID;

    public SandBurstEntity(EntityType<? extends SandBurstEntity> p_i50170_1_, World worldIn) {
        super(p_i50170_1_, worldIn);
    }

    public SandBurstEntity(World worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, LivingEntity casterIn) {
        super(FUREntityRegistry.SANDBURST, worldIn);
        this.warmupDelayTicks = p_i47276_9_;
        this.setOwner(casterIn);
        this.yRot = p_i47276_8_ * (180F / (float)Math.PI);
        this.setPos(x, y, z);
    }

    protected void defineSynchedData() {
    }

    public void setOwner(@Nullable LivingEntity p_190549_1_) {
        this.caster = p_190549_1_;
        this.ownerUUID = p_190549_1_ == null ? null : p_190549_1_.getUUID();
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.caster == null && this.ownerUUID != null && this.level instanceof ServerWorld) {
        	Entity entity = ((ServerWorld)this.level).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity)
            {
                this.caster = (LivingEntity)entity;
            }
        }

        return this.caster;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditionalSaveData(CompoundNBT p_70037_1_) {
        this.warmupDelayTicks = p_70037_1_.getInt("Warmup");
        if (p_70037_1_.hasUUID("Owner")) {
           this.ownerUUID = p_70037_1_.getUUID("Owner");
        }
	}

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void addAdditionalSaveData(CompoundNBT p_213281_1_) {
        p_213281_1_.putInt("Warmup", this.warmupDelayTicks);
        if (this.ownerUUID != null) {
           p_213281_1_.putUUID("Owner", this.ownerUUID);
        }
	}

    /**
     * Called to update the entity's position/logic.
     */    
    public void tick() {
        super.tick();
        BlockState state = this.level.getBlockState(new BlockPos(this.getX(), this.getY(), this.getZ()).below());
        
        if (this.level.isClientSide) {
           if (this.clientSideAttackStarted) {
              --this.lifeTicks;
              if (this.lifeTicks == 14) {
                 for(int i = 0; i < 12; ++i) {
                    double d0 = this.getX() + (this.random.nextDouble() * 2.0D - 1.0D) * (double)this.getBbWidth() * 0.5D;
                    double d1 = this.getY() + 0.05D + this.random.nextDouble();
                    double d2 = this.getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * (double)this.getBbWidth() * 0.5D;
                    double d3 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
                    double d4 = 0.3D + this.random.nextDouble() * 0.3D;
                    double d5 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
                    this.level.addParticle(ParticleTypes.CRIT, d0, d1 + 1.0D, d2, d3, d4, d5);
                 }
              }
           }
        } else if (--this.warmupDelayTicks < 0) {
           if (this.warmupDelayTicks == -8) {
              for(LivingEntity livingentity : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2D, 0.0D, 0.2D))) {
            	  if(!(livingentity instanceof SkeletonKingEntity || livingentity instanceof ForsakenEntity))
            		  this.dealDamageTo(livingentity);
              }
           }

           if (!this.sentSpikeEvent) {
              this.level.broadcastEntityEvent(this, (byte)4);
              this.sentSpikeEvent = true;
           }

           if (--this.lifeTicks < 0) {
        	   if(this.random.nextFloat() < 0.3F) {
        		   ForsakenEntity entityvex = FUREntityRegistry.FORSAKEN.create(this.level);
        		   entityvex.moveTo(this.blockPosition(), 0.0F, 0.0F);
        		   entityvex.finalizeSpawn(entityvex.getServer().overworld(), this.level.getCurrentDifficultyAt(entityvex.blockPosition()), SpawnReason.REINFORCEMENT, null, (CompoundNBT)null);
                     
                   if(!this.level.isClientSide())
                	   this.level.addFreshEntity(entityvex); 
        	   }
        	   
        	   this.remove();
           }
        }       
        
        if (state.getMaterial().isSolid()) {        	
            if (this.level.isClientSide()) {
            	for(int i = 0; i < 4; i++)
            		this.level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state).setPos(new BlockPos(this.getX(), this.getY(), this.getZ()).below()), this.getX() + (this.random.nextDouble() * 4.0D - 2.0D), this.getY(), this.getZ() + (this.random.nextDouble() * 4.0D - 2.0D), this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D, this.random.nextGaussian() * 0.02D);
            }
        }
        
        if (this.tickCount % 10 == 0) {
            this.playSound(SoundEvents.SAND_BREAK, 1, 0.5F);
        }
	}
    
    private void dealDamageTo(LivingEntity p_190551_1_) {
        LivingEntity livingentity = this.getOwner();
        if (p_190551_1_.isAlive() && !p_190551_1_.isInvulnerable() && p_190551_1_ != livingentity) {
           if (livingentity == null) {
              p_190551_1_.hurt(DamageSource.MAGIC, 6.0F);
           } else {
              if (livingentity.isAlliedTo(p_190551_1_)) {
                 return;
              }

              p_190551_1_.hurt(DamageSource.indirectMagic(this, livingentity), 6.0F);
              p_190551_1_.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.8D, 0.0D));
           }

        }
     }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
       super.handleEntityEvent(p_70103_1_);
       if (p_70103_1_ == 4) {
          this.clientSideAttackStarted = true;
          if (!this.isSilent()) {
             this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.EVOKER_FANGS_ATTACK, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.2F + 0.85F, false);
          }
       }

    }

    @OnlyIn(Dist.CLIENT)
    public float getAnimationProgress(float p_190550_1_) {
       if (!this.clientSideAttackStarted) {
          return 0.0F;
       } else {
          int i = this.lifeTicks - 2;
          return i <= 0 ? 1.0F : 1.0F - ((float)i - p_190550_1_) / 20.0F;
       }
    }
    
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}