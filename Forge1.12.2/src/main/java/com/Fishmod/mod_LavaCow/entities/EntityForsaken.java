package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.AddRecipes;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityForsaken extends AbstractSkeleton{

	public EntityForsaken(World worldIn) {
		super(worldIn);
	}
	
    @Override
    public boolean getCanSpawnHere() {
    	//BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
        	       
        return SpawnUtil.isAllowedDimension(this.dimension) 
        		//&& SpawnUtil.isInsideStructure(this.world, "Mineshaft", new BlockPos(this.posX, this.posY, this.posZ)) 
        		//&& !this.world.canSeeSky(pos) 
        		&& super.getCanSpawnHere();
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Forsaken_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Forsaken_Attack);
    }
	
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableHandler.FORSAKEN;
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
    
    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        
        /*if(cause.getTrueSource() instanceof EntityPlayer && this.rand.nextInt(10) == 0) {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(this.world, (double)((float)this.posX + 0.5F), (double)this.posY, (double)((float)this.posZ + 0.5F), this);
            this.world.spawnEntity(entitytntprimed);
            this.world.playSound((EntityPlayer)null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }*/

        if (cause.getTrueSource() instanceof EntityCreeper)
        {
            EntityCreeper entitycreeper = (EntityCreeper)cause.getTrueSource();

            if (entitycreeper.getPowered() && entitycreeper.ableToCauseSkullDrop())
            {
                entitycreeper.incrementDroppedSkulls();
                this.entityDropItem(new ItemStack(Items.SKULL, 1, 1), 0.0F);
            }
        }
    }
    
	/**
	 * Create a compound tag for the specified pattern and colour.
	 *
	 * @param pattern The pattern
	 * @param color   The colour
	 * @return The compound tag
	 */
	protected NBTTagCompound createPatternTag(BannerPattern pattern, EnumDyeColor color) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("Pattern", pattern.getHashname());
		tag.setInteger("Color", color.getDyeDamage());
		return tag;
	}
    
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {

    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
        ItemStack shield = new ItemStack(Items.SHIELD);
		NBTTagList patternsList = new NBTTagList();
		shield.getOrCreateSubCompound("BlockEntityTag").setTag("Patterns", patternsList);
		patternsList.appendTag(createPatternTag(AddRecipes.PATTERN_SKELETONKING, EnumDyeColor.WHITE));
        shield.getOrCreateSubCompound("BlockEntityTag").setInteger("Base", EnumDyeColor.BLACK.getDyeDamage());       
        
        switch(this.rand.nextInt(4)) {
        	case 0:
        		this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, shield);
                this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
                this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4D);
                this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
        	case 1:
        		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));      		
        		break;
        	case 2:
        		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        		break;
        	case 3:
        		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.27D);
        		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        		
                EntitySkeletonHorse entityskeletonhorse = new EntitySkeletonHorse(this.world);
                entityskeletonhorse.onInitialSpawn(difficulty, (IEntityLivingData)null);
                entityskeletonhorse.setPosition(this.posX, this.posY, this.posZ);
                entityskeletonhorse.hurtResistantTime = 60;
                entityskeletonhorse.setHorseTamed(true);
                entityskeletonhorse.setGrowingAge(0);
                entityskeletonhorse.world.spawnEntity(entityskeletonhorse);
                
                this.startRiding(entityskeletonhorse);           
        		break;
        	default:
        		break;
        	
        }
        this.setCombatTask();
                
        return ientitylivingdata;
    }
    
    public boolean attackEntityAsMob(Entity entityIn)
    {
        if (!super.attackEntityAsMob(entityIn))
        {
            return false;
        }
        else
        {
            if (entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(ModMobEffects.FRAGILE, 200, 2));
            }

            return true;
        }
    }
    
    protected EntityArrow getArrow(float p_190726_1_)
    {
        EntityArrow entityarrow = super.getArrow(p_190726_1_);

        if (entityarrow instanceof EntityTippedArrow)
        {
            ((EntityTippedArrow)entityarrow).addEffect(new PotionEffect(ModMobEffects.FRAGILE, 200, 2));
        }
        
        return entityarrow;
    }
}
