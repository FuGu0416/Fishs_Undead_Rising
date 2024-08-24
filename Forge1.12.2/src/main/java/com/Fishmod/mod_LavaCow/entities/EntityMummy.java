package com.Fishmod.mod_LavaCow.entities;

import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySummonedZombie;
import com.Fishmod.mod_LavaCow.init.ModMobEffects;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMummy extends EntitySummonedZombie implements IAggressive {
	private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityMummy.class, DataSerializers.VARINT);
	
    public EntityMummy(World worldIn) {
        super(worldIn);
        this.daytimeBurning = false;
    }
    
    @Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(SKIN_TYPE, Integer.valueOf(0));
	}
    
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Mummy_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Mummy_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
    	super.onUpdate();

        if(this.ticksExisted % 2 == 0 && this.getEntityWorld().isRemote)
        	mod_LavaCow.PROXY.spawnCustomParticle("locust_swarm", world, this.posX + (double)(new Random().nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(new Random().nextFloat() * this.height), this.posZ + (double)(new Random().nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
    }
    
    public boolean attackEntityAsMob(Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
        	this.attackTimer = 5;
	        this.world.setEntityState(this, (byte)4);
        	
        	if (par1Entity instanceof EntityLivingBase) {
            	float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

            	((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(ModMobEffects.CORRODED, 2 * 20 * (int)local_difficulty, 1));
            }

            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);

        if (this.rand.nextFloat() < (this.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
            int i = this.rand.nextInt(3);

            if (i == 0) {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            }
            else {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SHOVEL));
            }
        }
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Mummy_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Mummy_Attack);
    	this.setHealth(this.getMaxHealth());
    	
        this.setEquipmentBasedOnDifficulty(difficulty);
        
        return super.onInitialSpawn(difficulty, livingdata);
    }
    
    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }
	
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
		setSkin(compound.getInteger("Variant")); 
    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Mummy_Health + ((float)this.unbreaking * 2.0F));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", getSkin());    
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableHandler.MUMMY;
    }
}
