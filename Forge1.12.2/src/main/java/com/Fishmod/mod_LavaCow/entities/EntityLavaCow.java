package com.Fishmod.mod_LavaCow.entities;

import java.util.Set;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.init.FishItems;
import com.google.common.collect.Sets;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLavaCow extends EntityCow
{
	private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Items.BLAZE_POWDER);
	
	public EntityLavaCow(World worldIn)
    {
        super(worldIn);
        this.setSize(0.9F, 1.4F);
        isImmuneToFire = true;
    }
	
	@Override
	protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, false, TEMPTATION_ITEMS));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this)); 
    }
	
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }
	
    /**
     * Gets how bright this entity is.
     */
	public float getBrightness(float par1)
    {
        return 1.0F;
    }
	
    @Override
	public boolean getCanSpawnHere() {
		if(this.dimension == DimensionType.OVERWORLD.getId())
			return super.getCanSpawnHere();
		else return false;
	}
	
    private boolean isWalkingonLand()
    {
    	return Math.abs(this.limbSwingAmount) > 0.01F && !this.inWater && this.onGround;
    }
	
    /**
     * Called to update the entity's position/logic.
     */
	@Override
    public void onUpdate()
    {
        super.onUpdate();

        if (this.isWalkingonLand())
        {       	
        	for (int j = 0; j < 2; ++j)
            {
                float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
                float f1 = this.rand.nextFloat() * 0.5F;
                float f2 = MathHelper.sin(f) * f1;
                float f3 = MathHelper.cos(f) * f1;
                World world = this.world;
                EnumParticleTypes enumparticletypes = EnumParticleTypes.FLAME;
                double d0 = this.posX + (double)f2;
                double d1 = this.posZ + (double)f3;
                world.spawnParticle(enumparticletypes, d0, this.getEntityBoundingBox().minY + (double)f1, d1, 0.0D, 0.0D, 0.0D);
            }
        }
    }
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (itemstack.getItem() == Items.BUCKET && !player.capabilities.isCreativeMode && !this.isChild())
        {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
            itemstack.shrink(1);
            itemstack.getMaxStackSize();

            if (itemstack.isEmpty())
            {
                player.setHeldItem(hand, new ItemStack(Items.LAVA_BUCKET));
            }
            else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.LAVA_BUCKET)))
            {
                player.dropItem(new ItemStack(Items.LAVA_BUCKET), false);
            }

            return true;
        }
        else
        {
            return super.processInteract(player, hand);
        }
    }
		
	@Override
    public EntityLavaCow createChild(EntityAgeable ageable)
    {
        return new EntityLavaCow(this.world);
    }
	
	@Override
	protected void dropFewItems(boolean recentlyHit, int looting) {
		if (recentlyHit) {
			int chance = rand.nextInt(3) + rand.nextInt(1 + looting);
			for (int amount = 0; amount < chance; ++amount)
				entityDropItem(new ItemStack(FishItems.MOLTENBEEF), 0.0F);
		}
	}
	
	@Nullable
	@Override
	protected ResourceLocation getLootTable() {
		return null;
	}
	
    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
	@Override
    public boolean isBreedingItem(ItemStack stack)
    {
        return TEMPTATION_ITEMS.contains(stack.getItem());
    }
}
