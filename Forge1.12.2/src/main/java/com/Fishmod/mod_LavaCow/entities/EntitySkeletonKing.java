package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityKingsWrath;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySandBurst;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.message.PacketParticle;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySkeletonKing extends EntityMob implements IAggressive {
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.NOTCHED_12)).setDarkenSky(true);
	private static final DataParameter<BlockPos> SPAWN_ORIGIN = EntityDataManager.<BlockPos>createKey(EntitySkeletonKing.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.<Integer>createKey(EntitySkeletonKing.class, DataSerializers.VARINT);
	private int attackTimer;
	private int blockBreakCounter;
	protected int spellTicks[] = {0, 0};
	
	public EntitySkeletonKing(World worldIn) {
		super(worldIn);
		this.setSize(1.25F, 3.1F);
		this.isImmuneToFire = true;
		this.experienceValue = 80;
	}
	
    protected void initEntityAI() {
    	this.tasks.addTask(0, new EntitySkeletonKing.AIDoNothing());
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(1, new AICastingApell());
        this.tasks.addTask(2, new EntitySkeletonKing.AITeleportSpell());
        this.tasks.addTask(3, new EntitySkeletonKing.AITossSpell());
        this.tasks.addTask(4, new EntitySkeletonKing.AISummoningSpell());
        this.tasks.addTask(5, new EntitySkeletonKing.AIUseSpell());
        this.tasks.addTask(6, new EntitySkeletonKing.AISkeletonKingAttackMelee(this, 1.0D, false));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }
    
    protected void applyEntityAI() {
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
    	this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.SkeletonKing_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.SkeletonKing_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(SPAWN_ORIGIN, new BlockPos(0, 0, 0));
		dataManager.register(INVULNERABILITY_TIME, 0);
	}
    
	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}
	
	/**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
	@Override
    public boolean canBePushed() {
        return this.isEntityAlive() && !this.isOnLadder() && this.getInvulTime() <= 0;
    }
	
	@Override
    public boolean canBeCollidedWith() {
        return !this.isDead && this.getInvulTime() <= 0;
    }
	
    public boolean isSpellcasting() {
    	for(int i = 0 ; i < 2; i++) {
    		if(this.spellTicks[i] > 0)
    			return true;
    	}

    	return false;
    }
	
    public boolean isSpellcasting(int i) {
    	return this.spellTicks[i] > 0;
    }
    
    public int getSpellTicks(int i) {
        return this.spellTicks[i];
    }
    
    // Once at half health, the king gets angered
    public boolean isAngered() {
        return EntitySkeletonKing.this.getHealth() < EntitySkeletonKing.this.getMaxHealth() * 0.5F && this.getInvulTime() <= 0;
    }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
        if (this.attackTimer > 0 && !(this.isSpellcasting())) {
            --this.attackTimer;
            
            if (this.attackTimer > 20 && getAttackTarget() != null && !(this.isSpellcasting()))
                getLookHelper().setLookPositionWithEntity((Entity)getAttackTarget(), 10.0F, 10.0F);
            
            this.motionX = 0.0F;
            this.motionY = 0.0F;
            this.motionZ = 0.0F;
            
            if(this.attackTimer == 20 && !(this.isSpellcasting()))
            	this.playSound(FishItems.ENTITY_SKELETONKING_ATTACK, 1.0F, 1.0F);
        }
        
        if (this.getInvulTime() > 0) {
            for(int i1 = 0; i1 < 3; ++i1) {
               this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + this.rand.nextGaussian(), this.posY + (double)(this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian(), 0.0D, this.rand.nextDouble() * 0.5D, 0.0D);
            }
        }
        
        if(this.getEntityWorld().isRemote && this.isAngered()) {
	    	   for(int i1 = 0; i1 < 2; ++i1) {
	    		   mod_LavaCow.PROXY.spawnCustomParticle("wither_flame", world, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + (double)(this.rand.nextFloat() * 3.3F), this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
	    	   }
		}
        
        for(int i = 0 ; i < 2; i++) {
	        if (this.spellTicks[i] > 0) {
	            --this.spellTicks[i];
	            
	            if (this.world.isRemote) {
	                for (int j = 0; j < 4; ++j) {
	                    this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, this.rand.nextDouble() * 0.5D, 0.0D);
	                }
	            }
	        }    
        }
       
        if (this.getAttackTimer() == 15 && this.deathTime <= 0 && !(this.isSpellcasting())) {
			double d0 = this.posX + 2.5D * this.getLookVec().normalize().x;
            double d1 = this.posY;
            double d2 = this.posZ + 2.5D * this.getLookVec().normalize().z;
    		IBlockState state = world.getBlockState(new BlockPos(d0, d1, d2).down());
    		int blockId = Block.getStateId(state);
    		       	
	        if (state.getMaterial().isSolid()) {
	        	this.playSound(state.getBlock().getSoundType(state, world, new BlockPos(d0, d1, d2), this).getBreakSound(), 1, 0.5F);
	        	
	            if (world.isRemote) {
	            	for(int i = 0; i < 64; i++)
	            		this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d0 + (this.rand.nextDouble() * 4.0D - 2.0D), d1, d2 + (this.rand.nextDouble() * 4.0D - 2.0D), this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, blockId);
	            }
	        }
	        
            this.playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 1.0F, 0.5F);
            this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, (1.5F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
	        
            for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(d0, d1, d2, d0, d1, d2).grow(1.5D))) {
                if (!this.isEntityEqual(entitylivingbase) && !this.isOnSameTeam(entitylivingbase)) {
                    entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                    entitylivingbase.addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE_KING, 20 * 20, 3));
                    if (entitylivingbase instanceof EntityPlayer) ((EntityPlayer)entitylivingbase).disableShield(true);
                    
                    if (this.isAngered()) {
                    	entitylivingbase.knockBack(entitylivingbase, 3.0F * 0.5F, (double)MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F))));
                    	this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 0.0D, 0.0D, 0.0D);
                    	this.playSound(FishItems.ENTITY_SKELETONKING_KNOCKBACK, 2.0F, 1.0F);
                    	
                        for (int k = 0; k < 10; ++k) {
                            double d3 = this.rand.nextGaussian() * 0.02D;
                            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, entitylivingbase.posX + (double)(this.rand.nextFloat() * entitylivingbase.width * 2.0F) - (double)entitylivingbase.width, entitylivingbase.posY + (double)(this.rand.nextFloat() * entitylivingbase.height), entitylivingbase.posZ + (double)(this.rand.nextFloat() * entitylivingbase.width * 2.0F) - (double)entitylivingbase.width, d3, 0.0D, d3);
                        }
                    } else {
                    	entitylivingbase.knockBack(entitylivingbase, 0.4F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                    }
                }
            }
        }   
	}

    public boolean attackEntityAsMob(Entity entityIn) {
    	if (this.attackTimer == 0) {
	    	this.attackTimer = 30;
	        this.world.setEntityState(this, (byte)4);	        
    	}

		return false;
    }
    
    public boolean canDestroyBlock(Block blockIn) {
        return blockIn != Blocks.BEDROCK &&
        		blockIn != Blocks.END_PORTAL &&
        		blockIn != Blocks.END_PORTAL_FRAME &&
        		blockIn != Blocks.COMMAND_BLOCK &&
        		blockIn != Blocks.REPEATING_COMMAND_BLOCK &&
        		blockIn != Blocks.CHAIN_COMMAND_BLOCK &&
        		blockIn != Blocks.BARRIER &&
        		blockIn != Blocks.STRUCTURE_BLOCK &&
        		blockIn != Blocks.STRUCTURE_VOID &&
        		blockIn != Blocks.PISTON_EXTENSION &&
        		blockIn != Blocks.END_GATEWAY;
    }
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		
        if (getInvulTime() > 0) {
            setInvulTime(getInvulTime() - 1);
            if (this.ticksExisted % 5 == 0)
              heal(getMaxHealth() * 0.03F); 
          }
        
        if (this.blockBreakCounter > 0) {
            --this.blockBreakCounter;

            if (this.blockBreakCounter == 0 && ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                int i1 = MathHelper.floor(this.posY);
                int l1 = MathHelper.floor(this.posX);
                int i2 = MathHelper.floor(this.posZ);
                boolean flag = false;

                for (int k2 = -1; k2 <= 1; ++k2) {
                    for (int l2 = -1; l2 <= 1; ++l2) {
                        for (int j = 0; j <= 3; ++j) {
                            int i3 = l1 + k2;
                            int k = i1 + j;
                            int l = i2 + l2;
                            BlockPos blockpos = new BlockPos(i3, k, l);
                            IBlockState iblockstate = this.world.getBlockState(blockpos);
                            Block block = iblockstate.getBlock();

                            if (!block.isAir(iblockstate, this.world, blockpos) && this.canDestroyBlock(block) && block.canEntityDestroy(iblockstate, world, blockpos, this) && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, iblockstate)) {
                                flag = this.world.destroyBlock(blockpos, true) || flag;
                            }
                        }
                    }
                }

                if (flag) {
                    this.world.playEvent((EntityPlayer)null, 1022, new BlockPos(this), 0);
                }
            }
        }
        
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}
	
	@Override
	public boolean isNonBoss() {
		return false;
	}
	
	@Override
	public boolean isAggressive() {
		return false;
	}

	@Override
    public int getAttackTimer() {
        return this.attackTimer;
    }

	@Override
	public void setAttackTimer(int i) {
		this.attackTimer = i;
	}
    
	@Override
	public boolean isOnSameTeam(Entity entity) {
        if (entity == null) {
            return false;
        }
        else if (entity == this) {
            return true;
        }
        else if (super.isOnSameTeam(entity)) {
            return true;
        }
        else if (entity instanceof EntitySkeletonKing || entity instanceof EntityForsaken) {
            return this.getTeam() == null && entity.getTeam() == null;
        }
        else {
            return false;
        }
    }
	
    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }
	
    /**
     * Called when the entity is attacked.
     */
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source != DamageSource.DROWN && !(source.getTrueSource() instanceof EntitySkeletonKing)) {
        	if (getInvulTime() > 0 && source != DamageSource.OUT_OF_WORLD || source == DamageSource.FALL || source == DamageSource.CACTUS
            		|| source == DamageSource.LIGHTNING_BOLT || source == DamageSource.DROWN) {
                return false;
            }
            else {
                Entity entity = source.getTrueSource();

                if (entity != null && !(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() == this.getCreatureAttribute() ||
                		entity != null && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isOnSameTeam(this)) {
                    return false;
                }
                else {
                    if (this.blockBreakCounter <= 0) {
                        this.blockBreakCounter = 20;
                    }

                    return super.attackEntityFrom(source, amount);
                }
            }
        }
    		
    	return super.attackEntityFrom(source, amount);
    }
	
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
		switch(id) {
		case 4:
			this.attackTimer = 30;
			break;
		case 10:
			this.spellTicks[0] = 30;
			break;
		case 11:
			this.spellTicks[1] = 15;
			break;
		default:
			super.handleStatusUpdate(id);
			break;
		}
    }
    
	@Override
	protected void onDeathUpdate() {
		this.world.addWeatherEffect(new EntityLightningBolt(world, posX, posY, posZ, true));
		
        if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot"))) {
           int i = this.getExperiencePoints(this.attackingPlayer);
           i = ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
           
           	while (i > 0) {
                int j = EntityXPOrb.getXPSplit(i);
                i -= j;
                this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
              }
          }

        this.setDead();
        
        for (int k = 0; k < 20; ++k) {
            double d2 = this.rand.nextGaussian() * 0.02D;
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
        }
    }
    
    public class AICastingApell extends EntityAIBase {
        public AICastingApell() {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return EntitySkeletonKing.this.isSpellcasting() && getInvulTime() <= 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            EntitySkeletonKing.this.navigator.clearPath();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            super.resetTask();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            if (EntitySkeletonKing.this.getAttackTarget() != null) {
                EntitySkeletonKing.this.getLookHelper().setLookPositionWithEntity(EntitySkeletonKing.this.getAttackTarget(), (float)EntitySkeletonKing.this.getHorizontalFaceSpeed(), (float)EntitySkeletonKing.this.getVerticalFaceSpeed());
            }
        }
    }
    
	// Immune to Deathtouched, Corroded, and Wither
    @Override
	public boolean isPotionApplicable(PotionEffect effect) {
		return effect.getPotion() != ModMobEffects.FRAGILE && effect.getPotion() != ModMobEffects.FRAGILE_KING && effect.getPotion() != ModMobEffects.CORRODED && effect.getPotion() != MobEffects.WITHER && super.isPotionApplicable(effect);
	}
    
    
    public class AISummoningSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;
        protected int fangCount;
        protected int fangTime;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
        	if (EntitySkeletonKing.this.getAttackTarget() == null || EntitySkeletonKing.this.getAttackTimer() > 0)
                return false;
            else if ((EntitySkeletonKing.this.isSpellcasting() || !EntitySkeletonKing.this.canEntityBeSeen(EntitySkeletonKing.this.getAttackTarget())) && EntitySkeletonKing.this.getDistance(EntitySkeletonKing.this.getAttackTarget()) > 16.0D)
                return false;

        	return EntitySkeletonKing.this.ticksExisted >= this.spellCooldown && getInvulTime() <= 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntitySkeletonKing.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntitySkeletonKing.this.spellTicks[0] = this.getCastingTime();
            this.spellCooldown = EntitySkeletonKing.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EntitySkeletonKing.this.world.setEntityState(EntitySkeletonKing.this, (byte)10);
            if (soundevent != null) {
                EntitySkeletonKing.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                EntitySkeletonKing.this.playSound(EntitySkeletonKing.this.getSpellSound(), 1.0F, 3.0F);
            }
        }

        protected void castSpell() {
        	this.fangCount = ((isAngered()) ? 24 : 16);
        	this.fangTime = ((isAngered()) ? 10 : 15);
        	
            for (int i = 0; i < fangCount; ++i) {
                BlockPos blockpos = (new BlockPos(EntitySkeletonKing.this)).add(-12 + EntitySkeletonKing.this.rand.nextInt(24), 0, -12 + EntitySkeletonKing.this.rand.nextInt(24));

                EntitySandBurst entityevokerfangs = new EntitySandBurst(EntitySkeletonKing.this.world, (double)blockpos.getX(), (double)EntitySkeletonKing.this.world.getHeight(blockpos).getY(), (double)blockpos.getZ(), 0.0F, fangTime, EntitySkeletonKing.this);
                EntitySkeletonKing.this.world.spawnEntity(entityevokerfangs);
            }
            
            EntitySandBurst entityevokerfangs = new EntitySandBurst(EntitySkeletonKing.this.world, EntitySkeletonKing.this.getAttackTarget().posX, EntitySkeletonKing.this.getAttackTarget().posY, EntitySkeletonKing.this.getAttackTarget().posZ, 0.0F, fangTime, EntitySkeletonKing.this);
            EntitySkeletonKing.this.world.spawnEntity(entityevokerfangs);
        }

        protected int getCastWarmupTime() {
            return 30;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return Modconfig.SkeletonKing_Ability_Sand_Tomb_Cooldown * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
        	return FishItems.ENTITY_SKELETONKING_SPELL_SUMMON;
        }
    }
    
    public class AIUseSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
        	if (EntitySkeletonKing.this.getAttackTarget() == null || EntitySkeletonKing.this.getAttackTimer() > 0)
                return false;
            else if ((EntitySkeletonKing.this.isSpellcasting() || !EntitySkeletonKing.this.canEntityBeSeen(EntitySkeletonKing.this.getAttackTarget())) && EntitySkeletonKing.this.getDistance(EntitySkeletonKing.this.getAttackTarget()) > 16.0D)
                return false;

        	int i = EntitySkeletonKing.this.world.getEntitiesWithinAABB(EntityForsaken.class, EntitySkeletonKing.this.getEntityBoundingBox().grow(16.0D)).size();
        	return EntitySkeletonKing.this.ticksExisted >= this.spellCooldown && getInvulTime() <= 0 && i < Modconfig.SkeletonKing_Ability_Summon_Max;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntitySkeletonKing.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntitySkeletonKing.this.spellTicks[1] = this.getCastingTime();
            this.spellCooldown = EntitySkeletonKing.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EntitySkeletonKing.this.world.setEntityState(EntitySkeletonKing.this, (byte)10);
            if (soundevent != null) {
            	EntitySkeletonKing.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
                EntitySkeletonKing.this.playSound(EntitySkeletonKing.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell() {
            for (int i = 0; i <  Modconfig.SkeletonKing_Ability_Summon_Num; ++i) {
                	BlockPos blockpos = (new BlockPos(EntitySkeletonKing.this)).add(-12 + EntitySkeletonKing.this.rand.nextInt(24), 0, -12 + EntitySkeletonKing.this.rand.nextInt(24));
                	EntityForsaken entity = new EntityForsaken(EntitySkeletonKing.this.world);
                	entity.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
                	entity.onInitialSpawn(EntitySkeletonKing.this.world.getDifficultyForLocation(blockpos), (IEntityLivingData)null);
                	entity.setLimitedLife(Modconfig.SkeletonKing_Minion_Lifespan * 20);
                	entity.setCanPickUpLoot(false);
                	entity.setTamed(true);
                	entity.setOwnerId(EntitySkeletonKing.this.getUniqueID());
                
                	if(!EntitySkeletonKing.this.world.isRemote)
                		EntitySkeletonKing.this.world.spawnEntity(entity);
                	
                	EntitySkeletonKing.this.world.setEntityState(entity, (byte)32);
                
                	if(EntitySkeletonKing.this.getAttackingEntity() != null)
                		entity.setAttackTarget(EntitySkeletonKing.this.getAttackingEntity());
                
                	if(EntitySkeletonKing.this.world instanceof World) {
                		for (int j = 0; j < 24; ++j) {
                		double d0 = entity.posX + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                		double d1 = entity.posY + (double)(entity.world.rand.nextFloat() * entity.height);
                		double d2 = entity.posZ + (double)(entity.world.rand.nextFloat() * entity.width * 2.0F) - (double)entity.width;
                		mod_LavaCow.NETWORK_WRAPPER.sendToAll(new PacketParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2));
                	}
                }                           
            }
        }

        protected int getCastWarmupTime() {
            return 30;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return Modconfig.SkeletonKing_Ability_Summon_Cooldown * 20;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return FishItems.ENTITY_SKELETONKING_SPELL_SKELETON_ARMY;
        }
    }
    
    public class AITeleportSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
        	if (EntitySkeletonKing.this.getAttackTarget() == null || EntitySkeletonKing.this.getAttackTimer() > 0)
                return false;
            else if ((EntitySkeletonKing.this.isSpellcasting() || !EntitySkeletonKing.this.canEntityBeSeen(EntitySkeletonKing.this.getAttackTarget())) && EntitySkeletonKing.this.getDistance(EntitySkeletonKing.this.getAttackTarget()) > 16.0D)
                return false;

        	return EntitySkeletonKing.this.ticksExisted >= this.spellCooldown && getInvulTime() <= 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntitySkeletonKing.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            this.spellCooldown = EntitySkeletonKing.this.ticksExisted + this.getCastingInterval();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
            }
        }

        protected void castSpell() {
        	EntitySkeletonKing.this.attemptTeleport(EntitySkeletonKing.this.getAttackTarget().posX, EntitySkeletonKing.this.getAttackTarget().posY, EntitySkeletonKing.this.getAttackTarget().posZ);
        	EntitySkeletonKing.this.playSound(this.getSpellPrepareSound(), 1.0F, 1.0F);
        }

        protected int getCastWarmupTime() {
            return 20;
        }

        protected int getCastingTime() {
            return 30;
        }

        protected int getCastingInterval() {
            return Modconfig.SkeletonKing_Ability_Sand_Wraith_Cooldown * ((isAngered()) ? 10 : 20);
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
        	return FishItems.ENTITY_SKELETONKING_SPELL_TELEPORT;
        }
    }
    
    public class AITossSpell extends EntityAIBase {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
        	if (EntitySkeletonKing.this.getAttackTarget() == null || !isAngered() || EntitySkeletonKing.this.getAttackTimer() > 0)
                return false;
            else if ((EntitySkeletonKing.this.isSpellcasting() || !EntitySkeletonKing.this.canEntityBeSeen(EntitySkeletonKing.this.getAttackTarget())) && EntitySkeletonKing.this.getDistance(EntitySkeletonKing.this.getAttackTarget()) > 16.0D)
                return false;

        	return EntitySkeletonKing.this.ticksExisted >= this.spellCooldown && getInvulTime() <= 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return EntitySkeletonKing.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.spellWarmup = this.getCastWarmupTime();
            EntitySkeletonKing.this.spellTicks[1] = this.getCastingTime();
            this.spellCooldown = EntitySkeletonKing.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EntitySkeletonKing.this.world.setEntityState(EntitySkeletonKing.this, (byte)11);
            if (soundevent != null) {
                EntitySkeletonKing.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.spellWarmup;

            if (this.spellWarmup == 0) {
                this.castSpell();
            }
        }

        protected void castSpell() {
        	if(EntitySkeletonKing.this.getAttackTarget() == null)
        		return;
        	
        	for(int i = -1; i < 2; i++) {
        		EntityKingsWrath entitysnowball = new EntityKingsWrath(EntitySkeletonKing.this.world, EntitySkeletonKing.this);
        		
	            entitysnowball.shoot(EntitySkeletonKing.this, EntitySkeletonKing.this.rotationPitch, EntitySkeletonKing.this.rotationYaw + (i * 30.0F), 0.0F, 0.75F, 1.0F);
	            
	            EntitySkeletonKing.this.world.spawnEntity(entitysnowball);
        	}
        }

        protected int getCastWarmupTime() {
            return 15;
        }

        protected int getCastingTime() {
            return 15;
        }

        protected int getCastingInterval() {
            return Modconfig.SkeletonKing_Ability_Projectile_Cooldown;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound() {
            return FishItems.ENTITY_SKELETONKING_SPELL_TOSS;
        }
    }
    
    static class AISkeletonKingAttackMelee extends EntityAIAttackMelee {
    	private int ticksUntilNextAttack;
    	
		public AISkeletonKingAttackMelee(EntityCreature creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}
		
		public void startExecuting() {
			super.startExecuting();
			this.ticksUntilNextAttack = 0;
		}
		
		public void updateTask() {
			super.updateTask();
			this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
		}
		
	    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
	        double d0 = this.getAttackReachSqr(p_190102_1_);

	        if (p_190102_2_ <= d0 && this.attackTick <= 0) {
	            this.attackTick = 60;
	            this.attacker.swingArm(EnumHand.MAIN_HAND);
	            this.attacker.attackEntityAsMob(p_190102_1_);
	        }
	    }
	    
	    protected void resetAttackCooldown() {
	    	this.ticksUntilNextAttack = 60;
	    }

	    protected boolean isTimeToAttack() {
	    	return this.ticksUntilNextAttack <= 0;
	    }

	    protected int getTicksUntilNextAttack() {
	    	return this.ticksUntilNextAttack;
	    }

	    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
	        return (double)(this.attacker.width * 3.0F * this.attacker.width * 3.0F + attackTarget.width);
	    }
    	
    }
    
    class AIDoNothing extends EntityAIBase {
        public AIDoNothing() {
        	this.setMutexBits(7);
        }

        public boolean shouldExecute() {
           return !(EntitySkeletonKing.this.isSpellcasting()) && EntitySkeletonKing.this.getInvulTime() > 0;
        }
        
        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
        	EntitySkeletonKing.this.setSilent(true);
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
        	EntitySkeletonKing.this.setSilent(false);
        }
	}
    
    public int getInvulTime() {
        return this.dataManager.get(INVULNERABILITY_TIME).intValue();
    }

    public void setInvulTime(int time) {
        this.dataManager.set(INVULNERABILITY_TIME, Integer.valueOf(time));
    }
	
	@Override
	public void setCustomNameTag(String name) {
		super.setCustomNameTag(name);
		this.bossInfo.setName(this.getDisplayName());
	}
	
	@Override
    public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

	@Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.spellTicks[0] = compound.getInteger("SpellTicks0");
        this.spellTicks[1] = compound.getInteger("SpellTicks1");
        this.setInvulTime(compound.getInteger("Invul"));
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("SpellTicks0", this.spellTicks[0]);
        compound.setInteger("SpellTicks1", this.spellTicks[1]);
        compound.setInteger("Invul", this.getInvulTime());
    }
	
    @Nullable
    protected ResourceLocation getLootTable() {
        return Modconfig.SkeletonKing_Loot_Option ? null : LootTableHandler.SKELETON_KING;
    }

    protected SoundEvent getAmbientSound() {
        return FishItems.ENTITY_SKELETONKING_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return FishItems.ENTITY_SKELETONKING_HURT;
    }

    protected SoundEvent getDeathSound() {
        return FishItems.ENTITY_SKELETONKING_DEATH;
    }
    
    protected SoundEvent getSpellSound() {
        return FishItems.ENTITY_SKELETONKING_SPELL_SUMMON;
    }

    protected SoundEvent getStepSound() {
        return FishItems.ENTITY_SKELETONKING_STEP;
    }
    
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }
    
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}
	
    /**
     * Called when the mob's health reaches 0.
     */
	@Override
    public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		
		if (Modconfig.SkeletonKing_Loot_Option && !this.world.isRemote) {
			BlockPos position = SpawnUtil.getHeight(this);
			
			this.world.setBlockState(position, Blocks.CHEST.getDefaultState(), 8 | 4 | 2 | 1);
	        if (this.world.getBlockState(position).getBlock() instanceof BlockChest) {
	            TileEntity tileentity = this.world.getTileEntity(position);
	            if (tileentity instanceof TileEntityChest && !tileentity.isInvalid()) {
	                ((TileEntityChest) tileentity).setLootTable(LootTableHandler.SKELETON_KING, rand.nextLong());
	            }
	        }
		}
    }
}
