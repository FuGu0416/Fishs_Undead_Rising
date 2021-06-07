package com.Fishmod.mod_LavaCow.entities;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.init.AddRecipes;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityForsaken extends AbstractSkeleton{

	public EntityForsaken(World worldIn) {
		super(worldIn);
	}
	
    @Override
    public boolean getCanSpawnHere() {
    	BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
        	       
        return SpawnUtil.isAllowedDimension(this.dimension) 
        		//&& SpawnUtil.isInsideStructure(this.world, "Mineshaft", new BlockPos(this.posX, this.posY, this.posZ)) 
        		&& !this.world.canSeeSky(pos) && super.getCanSpawnHere();
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Forsaken_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Forsaken_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4D);
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
    
    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);
        
        if(cause.getTrueSource() instanceof EntityPlayer && this.rand.nextInt(10) == 0) {
            EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(this.world, (double)((float)this.posX + 0.5F), (double)this.posY, (double)((float)this.posZ + 0.5F), this);
            this.world.spawnEntity(entitytntprimed);
            this.world.playSound((EntityPlayer)null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

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
        ItemStack shield = new ItemStack(Items.SHIELD);
		NBTTagList patternsList = new NBTTagList();
		shield.getOrCreateSubCompound("BlockEntityTag").setTag("Patterns", patternsList);
		patternsList.appendTag(createPatternTag(AddRecipes.PATTERN_SKELETONKING, EnumDyeColor.WHITE));
        shield.getOrCreateSubCompound("BlockEntityTag").setInteger("Base", EnumDyeColor.BLACK.getDyeDamage());
             
    	this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, shield);
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
        this.setCombatTask();
                
        return ientitylivingdata;
    }
}
