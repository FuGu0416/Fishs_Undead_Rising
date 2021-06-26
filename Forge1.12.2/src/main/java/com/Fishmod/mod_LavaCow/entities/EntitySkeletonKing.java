package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntityDeathCoil;
import com.Fishmod.mod_LavaCow.entities.projectiles.EntitySandBurst;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySkeletonKing extends EntityMob implements IAggressive{
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
	private static final DataParameter<BlockPos> SPAWN_ORIGIN = EntityDataManager.<BlockPos>createKey(EntitySkeletonKing.class, DataSerializers.BLOCK_POS);
	private int attackTimer;
	protected int spellTicks[] = {0, 0};
	
	public EntitySkeletonKing(World worldIn) {
		super(worldIn);
		this.setSize(1.25F, 3.1F);
		this.isImmuneToFire = true;
		this.experienceValue = 50;
	}
	
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new AICastingApell());
        this.tasks.addTask(2, new EntitySkeletonKing.AITeleportSpell());
        this.tasks.addTask(3, new EntitySkeletonKing.AITossSpell());
        this.tasks.addTask(4, new EntitySkeletonKing.AISummoningSpell());
        this.tasks.addTask(5, new EntitySkeletonKing.AISkeletonKingAttackMelee(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }
    
    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    	this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.SkeletonKing_Health);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.SkeletonKing_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
    
	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(SPAWN_ORIGIN, new BlockPos(0, 0, 0));
	}
    
	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}
	
    public boolean isSpellcasting()
    {
    	for(int i = 0 ; i < 2; i++) {
    		if(this.spellTicks[i] > 0)
    			return true;
    	}

    	return false;
    }
	
    public boolean isSpellcasting(int i)
    {
    	return this.spellTicks[i] > 0;
    }
    
    public int getSpellTicks(int i)
    {
        return this.spellTicks[i];
    }
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
        if (this.attackTimer > 0) {
            --this.attackTimer;
            
            this.motionX = 0.0F;
            this.motionZ = 0.0F;
            this.rotationPitch = this.prevRotationPitch;
            this.rotationYaw = this.prevRotationYaw;
            
            if(this.attackTimer == 10)
            	this.playSound(FishItems.ENTITY_SKELETONKING_ATTACK, 1.0F, 1.0F);
        }
        
        for(int i = 0 ; i < 2; i++) {
	        if (this.spellTicks[i] > 0) {
	            --this.spellTicks[i];
	            
	            if (this.world.isRemote)
	            {
	                for (int j = 0; j < 4; ++j)
	                {
	                    this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, this.rand.nextDouble() * 0.5D, 0.0D);
	                }
	            }
	        }    
        }
        
        // Should always return EntityLivingBase (according to the documentation).
    	EntityLivingBase target = this.getAttackTarget();
        
        if (target != null && this.getDistanceSq(target) < 4.0D && this.getAttackTimer() == 5 && this.deathTime <= 0 && this.canEntityBeSeen(target)) {
    		IBlockState state = world.getBlockState(target.getPosition().down());
    		int blockId = Block.getStateId(state);

        	this.playSound(SoundEvents.BLOCK_SAND_BREAK, 1, 0.5F);
        	
	        if (state.isOpaqueCube()) {
	            if (world.isRemote) {
	            	for(int i = 0; i < 4; i++)
	            		this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, target.posX + (double) (this.rand.nextFloat() * target.width * 2.0F) - (double) this.width, target.posY + (double) (this.rand.nextFloat() * target.width * 2.0F) - (double) target.width, target.posZ + (double) (this.rand.nextFloat() * target.width * 2.0F) - (double) target.width, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, blockId);
	            }
	        }
	        
            for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(1.0D, 0.25D, 1.0D)))
            {
                if (!this.isEntityEqual(entitylivingbase) && !this.isOnSameTeam(entitylivingbase))
                {
                    entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                    entitylivingbase.knockBack(this, 0.4F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                }
            }
        }   
	}
	
	@Override
	public int getMaxFallHeight() {
		//Doesn't take fall damage
		return 128;
	}

    public boolean attackEntityAsMob(Entity entityIn)
    {
    	if (this.attackTimer == 0) {
	    	this.attackTimer = 20;
	        this.world.setEntityState(this, (byte)4);	        
    	}

		return false;
    }
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
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
	
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	if(source.getDamageType().equals(DamageSource.IN_WALL.damageType)) {
    		this.world.createExplosion(this, this.posX, this.posY, this.posZ, (float)(3.0D + this.rand.nextDouble() * 1.5D), true);
    	}
    		
    	return super.attackEntityFrom(source, amount);
    }
	
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
    	if (id == 4) 
    	{
            this.attackTimer = 20;
        }
    	else if (id == 10) {
    		
        	this.spellTicks[0] = 30;
        }
		else if (id == 11) {
    		
        	this.spellTicks[1] = 15;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }
    
    public class AICastingApell extends EntityAIBase
    {
        public AICastingApell()
        {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return EntitySkeletonKing.this.isSpellcasting();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            super.startExecuting();
            EntitySkeletonKing.this.navigator.clearPath();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            super.resetTask();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            if (EntitySkeletonKing.this.getAttackTarget() != null)
            {
                EntitySkeletonKing.this.getLookHelper().setLookPositionWithEntity(EntitySkeletonKing.this.getAttackTarget(), (float)EntitySkeletonKing.this.getHorizontalFaceSpeed(), (float)EntitySkeletonKing.this.getVerticalFaceSpeed());
            }
        }
    }
    
    public class AISummoningSpell extends EntityAIBase
    {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
        	if (EntitySkeletonKing.this.getAttackTarget() == null || EntitySkeletonKing.this.getAttackTimer() > 0)
                return false;
            else if ((EntitySkeletonKing.this.isSpellcasting() || !EntitySkeletonKing.this.canEntityBeSeen(EntitySkeletonKing.this.getAttackTarget())) && EntitySkeletonKing.this.getDistance(EntitySkeletonKing.this.getAttackTarget()) > 16.0D)
                return false;

        	return EntitySkeletonKing.this.ticksExisted >= this.spellCooldown;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return EntitySkeletonKing.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.spellWarmup = this.getCastWarmupTime();
            EntitySkeletonKing.this.spellTicks[0] = this.getCastingTime();
            this.spellCooldown = EntitySkeletonKing.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EntitySkeletonKing.this.world.setEntityState(EntitySkeletonKing.this, (byte)10);
            if (soundevent != null)
            {
                EntitySkeletonKing.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            --this.spellWarmup;

            if (this.spellWarmup == 0)
            {
                this.castSpell();
                //EntitySkeletonKing.this.playSound(EntitySkeletonKing.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell()
        {
            for (int i = 0; i < 11; ++i)
            {
                BlockPos blockpos = (new BlockPos(EntitySkeletonKing.this)).add(-12 + EntitySkeletonKing.this.rand.nextInt(24), 0, -12 + EntitySkeletonKing.this.rand.nextInt(24));

                EntitySandBurst entityevokerfangs = new EntitySandBurst(EntitySkeletonKing.this.world, (double)blockpos.getX(), (double)EntitySkeletonKing.this.world.getHeight(blockpos).getY(), (double)blockpos.getZ(), 0.0F, 20, EntitySkeletonKing.this);
                EntitySkeletonKing.this.world.spawnEntity(entityevokerfangs);
            }
            
            EntitySandBurst entityevokerfangs = new EntitySandBurst(EntitySkeletonKing.this.world, EntitySkeletonKing.this.getAttackTarget().posX, EntitySkeletonKing.this.getAttackTarget().posY, EntitySkeletonKing.this.getAttackTarget().posZ, 0.0F, 10, EntitySkeletonKing.this);
            EntitySkeletonKing.this.world.spawnEntity(entityevokerfangs);
        }

        protected int getCastWarmupTime()
        {
            return 30;
        }

        protected int getCastingTime()
        {
            return 30;
        }

        protected int getCastingInterval()
        {
            return 200;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound()
        {
        	return FishItems.ENTITY_SKELETONKING_SPELL_SUMMON;
        }
    }
    
    public class AITeleportSpell extends EntityAIBase
    {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
        	if (EntitySkeletonKing.this.getAttackTarget() == null || EntitySkeletonKing.this.getAttackTimer() > 0)
                return false;
            else if (EntitySkeletonKing.this.isSpellcasting() || EntitySkeletonKing.this.getDistance(EntitySkeletonKing.this.getAttackTarget()) > 16.0D || EntitySkeletonKing.this.getDistance(EntitySkeletonKing.this.getAttackTarget()) < 4.0D)
                return false;

        	return EntitySkeletonKing.this.ticksExisted >= this.spellCooldown;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return EntitySkeletonKing.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.spellWarmup = this.getCastWarmupTime();
            this.spellCooldown = EntitySkeletonKing.this.ticksExisted + this.getCastingInterval();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            --this.spellWarmup;

            if (this.spellWarmup == 0)
            {
                this.castSpell();
            }
        }

        protected void castSpell()
        {
        	EntitySkeletonKing.this.attemptTeleport(EntitySkeletonKing.this.getAttackTarget().posX, EntitySkeletonKing.this.getAttackTarget().posY, EntitySkeletonKing.this.getAttackTarget().posZ);
        	EntitySkeletonKing.this.playSound(this.getSpellPrepareSound(), 1.0F, 1.0F);
        }

        protected int getCastWarmupTime()
        {
            return 20;
        }

        protected int getCastingTime()
        {
            return 30;
        }

        protected int getCastingInterval()
        {
            return (EntitySkeletonKing.this.getHealth() >  EntitySkeletonKing.this.getMaxHealth() * 0.5F) ? 320 : 160;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound()
        {
        	return FishItems.ENTITY_SKELETONKING_SPELL_TELEPORT;
        }
    }
    
    public class AITossSpell extends EntityAIBase
    {
        protected int spellWarmup;
        protected int spellCooldown;

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
        	if (EntitySkeletonKing.this.getAttackTarget() == null || EntitySkeletonKing.this.getHealth() >  EntitySkeletonKing.this.getMaxHealth() * 0.5F || EntitySkeletonKing.this.getAttackTimer() > 0)
                return false;
            else if ((EntitySkeletonKing.this.isSpellcasting() || !EntitySkeletonKing.this.canEntityBeSeen(EntitySkeletonKing.this.getAttackTarget())) && EntitySkeletonKing.this.getDistance(EntitySkeletonKing.this.getAttackTarget()) > 16.0D)
                return false;

        	return EntitySkeletonKing.this.ticksExisted >= this.spellCooldown;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting()
        {
            return EntitySkeletonKing.this.getAttackTarget() != null && this.spellWarmup > 0;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.spellWarmup = this.getCastWarmupTime();
            EntitySkeletonKing.this.spellTicks[1] = this.getCastingTime();
            this.spellCooldown = EntitySkeletonKing.this.ticksExisted + this.getCastingInterval();
            SoundEvent soundevent = this.getSpellPrepareSound();
            EntitySkeletonKing.this.world.setEntityState(EntitySkeletonKing.this, (byte)11);
            if (soundevent != null)
            {
                EntitySkeletonKing.this.playSound(soundevent, 1.0F, 1.0F);
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            --this.spellWarmup;

            if (this.spellWarmup == 0)
            {
                this.castSpell();
                //EntitySkeletonKing.this.playSound(EntitySkeletonKing.this.getSpellSound(), 1.0F, 1.0F);
            }
        }

        protected void castSpell()
        {
        	if(EntitySkeletonKing.this.getAttackTarget() == null)
        		return;
        	
        	for(int i = -1; i < 2; i++) {
        		EntityDeathCoil entitysnowball = new EntityDeathCoil(EntitySkeletonKing.this.world, EntitySkeletonKing.this);
	        	
	            entitysnowball.shoot(EntitySkeletonKing.this, EntitySkeletonKing.this.rotationPitch, EntitySkeletonKing.this.rotationYaw + (i * 30.0F), 0.0F, 0.75F, 1.0F);
	            
	            EntitySkeletonKing.this.world.spawnEntity(entitysnowball);
        	}
        }

        protected int getCastWarmupTime()
        {
            return 15;
        }

        protected int getCastingTime()
        {
            return 15;
        }

        protected int getCastingInterval()
        {
            return 120;
        }

        @Nullable
        protected SoundEvent getSpellPrepareSound()
        {
            return FishItems.ENTITY_SKELETONKING_SPELL_TOSS;
        }
    }
    
    static class AISkeletonKingAttackMelee extends EntityAIAttackMelee {

		public AISkeletonKingAttackMelee(EntityCreature creature, double speedIn, boolean useLongMemory) {
			super(creature, speedIn, useLongMemory);
		}
		
	    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_)
	    {
	        double d0 = this.getAttackReachSqr(p_190102_1_);

	        if (p_190102_2_ <= d0 && this.attackTick <= 0)
	        {
	            this.attackTick = 60;
	            this.attacker.swingArm(EnumHand.MAIN_HAND);
	            this.attacker.attackEntityAsMob(p_190102_1_);
	        }
	    }

	    protected double getAttackReachSqr(EntityLivingBase attackTarget)
	    {
	        return (double)(this.attacker.width * 3.0F * this.attacker.width * 3.0F + attackTarget.width);
	    }
    	
    }
	
	@Override
	public void setCustomNameTag(String name) {
		super.setCustomNameTag(name);
		bossInfo.setName(this.getDisplayName());
	}
	
	@Override
    public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
    }

	@Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.spellTicks[0] = compound.getInteger("SpellTicks0");
        this.spellTicks[1] = compound.getInteger("SpellTicks1");
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("SpellTicks0", this.spellTicks[0]);
        compound.setInteger("SpellTicks1", this.spellTicks[1]);
    }
	
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return null;
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return FishItems.ENTITY_SKELETONKING_DEATH;
    }
    
    protected SoundEvent getSpellSound()
    {
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_SKELETON_STEP;
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
		BlockPos position = new BlockPos(this.getPosition());
		super.onDeath(cause);
		
		while(this.world.getBlockState(position.down()).getBlock() == Blocks.AIR)
			position = position.down();
		
		while(this.world.getBlockState(position).getBlock() != Blocks.AIR)
			position = position.up();
		
		this.world.setBlockState(position, Blocks.CHEST.getDefaultState(), 8 | 4 | 2 | 1);
        if (this.world.getBlockState(position).getBlock() instanceof BlockChest) {
            TileEntity tileentity = this.world.getTileEntity(position);
            if (tileentity instanceof TileEntityChest && !tileentity.isInvalid()) {
                ((TileEntityChest) tileentity).setLootTable(LootTableHandler.SKELETON_KING, rand.nextLong());
            }
        }
    }
}
