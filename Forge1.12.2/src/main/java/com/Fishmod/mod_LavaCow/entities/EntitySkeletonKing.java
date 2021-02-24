package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySkeletonKing extends EntityMob implements IAggressive{
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
	private static final DataParameter<BlockPos> SPAWN_ORIGIN = EntityDataManager.<BlockPos>createKey(EntitySkeletonKing.class, DataSerializers.BLOCK_POS);
	private int attackTimer;
    private Vec3d mobVector;
	
	public EntitySkeletonKing(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.5F);
		this.isImmuneToFire = true;
	}
	
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }
    
    protected void applyEntityAI()
    {
    	this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    	this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    	this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityPig>(this, EntityPig.class, true));
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
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
        if (this.attackTimer > 0) {
            --this.attackTimer;
            
            this.motionX = 0.0F;
            this.motionY = 0.0F;
            this.motionZ = 0.0F;
            this.rotationPitch = this.prevRotationPitch;
            this.rotationYaw = this.prevRotationYaw;
        }
        
        this.mobVector = new Vec3d(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ);
        
        // Should always return EntityLivingBase (according to the documentation).
    	EntityLivingBase target = this.getAttackTarget();

        if (target != null && this.getDistanceSq(target) < 9.0D && this.getAttackTimer() == 5 && this.deathTime <= 0) {
        	Vec3d targetVector = new Vec3d(target.posX, target.posY + (double)target.getEyeHeight(), target.posZ);
    		RayTraceResult rayTrace = this.world.rayTraceBlocks(mobVector, targetVector, false, true, false);

    		// If there's something in the way, we retry from the mob's body.
    		if (rayTrace != null && rayTrace.typeOfHit == RayTraceResult.Type.BLOCK) {
    			rayTrace = this.world.rayTraceBlocks(mobVector.subtract(0.0D, (double)this.getEyeHeight(), 0.0D), targetVector, false, true, false);
    		}

    		if (rayTrace == null || rayTrace.typeOfHit != RayTraceResult.Type.BLOCK) {
	        	this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
	        	
                for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 0.25D, 2.0D)))
                {
                    if (!this.isEntityEqual(entitylivingbase) && !this.isOnSameTeam(entitylivingbase))
                    {
                        entitylivingbase.knockBack(this, 0.4F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                        entitylivingbase.motionY += 0.4000000059604645D;
                        entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
                    }
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
	    	this.attackTimer = 30;
	        this.world.setEntityState(this, (byte)4);
	        return true;
    	}
    	else return false;
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
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
    	if (id == 4) 
    	{
            this.attackTimer = 30;
        }
        else
        {
            super.handleStatusUpdate(id);
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
	
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_WITHER_SKELETON;
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
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    protected SoundEvent getStepSound()
    {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }
    
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}
}
