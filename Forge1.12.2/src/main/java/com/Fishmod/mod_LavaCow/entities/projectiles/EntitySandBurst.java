package com.Fishmod.mod_LavaCow.entities.projectiles;

import java.util.UUID;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.entities.EntityForsaken;
import com.Fishmod.mod_LavaCow.entities.EntitySkeletonKing;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySandBurst extends Entity {
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks;
    private boolean clientSideAttackStarted;
    private EntityLivingBase caster;
    private UUID casterUuid;

    public EntitySandBurst(World worldIn) {
        super(worldIn);
        this.lifeTicks = 15;
        this.setSize(0.5F, 0.8F);
    }

    public EntitySandBurst(World worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, EntityLivingBase casterIn) {
        this(worldIn);
        this.warmupDelayTicks = p_i47276_9_;
        this.setCaster(casterIn);
        this.rotationYaw = p_i47276_8_ * (180F / (float)Math.PI);
        this.setPosition(x, y, z);
    }

    protected void entityInit() {
    }

    public void setCaster(@Nullable EntityLivingBase p_190549_1_) {
        this.caster = p_190549_1_;
        this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.getUniqueID();
    }

    @Nullable
    public EntityLivingBase getCaster() {
        if (this.caster == null && this.casterUuid != null && this.world instanceof WorldServer) {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.casterUuid);

            if (entity instanceof EntityLivingBase) {
                this.caster = (EntityLivingBase)entity;
            }
        }

        return this.caster;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.warmupDelayTicks = compound.getInteger("Warmup");
        
        if (compound.hasKey("Owner")) {
        	this.casterUuid = compound.getUniqueId("OwnerUUID");
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("Warmup", this.warmupDelayTicks);

        if (this.casterUuid != null) {
            compound.setUniqueId("OwnerUUID", this.casterUuid);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        super.onUpdate();
        IBlockState state = world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ).down());
        int blockId = Block.getStateId(state);
        
        if (this.world.isRemote) {
            if (this.clientSideAttackStarted) {
                --this.lifeTicks;

                if (this.lifeTicks == 9) {
                    for (int i = 0; i < 12; ++i) {
                        double d0 = this.posX + (this.rand.nextDouble() * 2.0D - 1.0D) * (double)this.width * 0.5D;
                        double d1 = this.posY + 0.05D + this.rand.nextDouble();
                        double d2 = this.posZ + (this.rand.nextDouble() * 2.0D - 1.0D) * (double)this.width * 0.5D;
                        double d3 = (this.rand.nextDouble() * 2.0D - 1.0D) * 0.3D;
                        double d4 = 0.5D + this.rand.nextDouble() * 0.3D;
                        double d5 = (this.rand.nextDouble() * 2.0D - 1.0D) * 0.3D;
                        this.world.spawnParticle(EnumParticleTypes.CRIT, d0, d1 + 1.0D, d2, d3, d4, d5);
                    }
                }
            }
        }
        else if (--this.warmupDelayTicks < 0) {
            if (this.warmupDelayTicks == -5) {
                for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(0.2D, 0.0D, 0.2D))) {
                    if(!(entitylivingbase instanceof EntitySkeletonKing || entitylivingbase instanceof EntityForsaken))
                    	this.damage(entitylivingbase);
                }
            }

            if (!this.sentSpikeEvent) {
                this.world.setEntityState(this, (byte)4);
                this.sentSpikeEvent = true;
            }

            if (--this.lifeTicks < 0) {
                /*if(this.rand.nextFloat() < 0.3F) {
                	EntityForsaken entityvex = new EntityForsaken(this.world);
                    entityvex.moveToBlockPosAndAngles(this.getPosition(), 0.0F, 0.0F);
                    entityvex.onInitialSpawn(this.world.getDifficultyForLocation(this.getPosition()), (IEntityLivingData)null);
                    
                    if(!this.world.isRemote)
                    	this.world.spawnEntity(entityvex);  
                }*/
                this.setDead();
            }
        }

	        if (state.isOpaqueCube()) {
	            if (world.isRemote) {
	            	for(int i = 0; i < 4; i++)
	            		this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, blockId);
	            }
	        }
	        
	        if (this.ticksExisted % 10 == 0) {
	            this.playSound(SoundEvents.BLOCK_SAND_BREAK, 1, 0.5F);
	        }

    }

    private void damage(EntityLivingBase p_190551_1_) {
        EntityLivingBase entitylivingbase = this.getCaster();

        if (p_190551_1_.isEntityAlive() && !p_190551_1_.getIsInvulnerable() && p_190551_1_ != entitylivingbase) {
            if (entitylivingbase == null) {
                p_190551_1_.attackEntityFrom(DamageSource.MAGIC, 6.0F);
            }
            else {
                if (entitylivingbase.isOnSameTeam(p_190551_1_)) {
                    return;
                }
                
                p_190551_1_.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, entitylivingbase), 6.0F);  
                p_190551_1_.addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE_KING, 10 * 20, 2));
                p_190551_1_.motionY += 0.8D;
            }
        }
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        super.handleStatusUpdate(id);

        if (id == 4) {
            this.clientSideAttackStarted = true;

            if (!this.isSilent()) {
                this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.EVOCATION_FANGS_ATTACK, this.getSoundCategory(), 1.33F, this.rand.nextFloat() * 0.2F + 0.85F, false);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public float getAnimationProgress(float partialTicks) {
        if (!this.clientSideAttackStarted) {
            return 0.0F;
        }
        else {
            int i = this.lifeTicks - 2;
            return i <= 0 ? 1.0F : 1.0F - ((float)i - partialTicks) / 20.0F;
        }
    }
}