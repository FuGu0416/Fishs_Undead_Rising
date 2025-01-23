package com.Fishmod.mod_LavaCow.entities;

import java.util.Set;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.flying.EntityGhostRay;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import com.google.common.collect.Sets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLavaCow extends EntityCow {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityGhostRay.class, DataSerializers.VARINT);
    private static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(Items.BLAZE_POWDER);

    public EntityLavaCow(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 1.4F);
        isImmuneToFire = true;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, false, TEMPTATION_ITEMS));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Lavacow_Health);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return this.getSkin() == 0 ? 15728880 : super.getBrightnessForRender();
    }

    /**
     * Gets how bright this entity is.
     */
    @Override
    public float getBrightness() {
        return this.getSkin() == 0 ? 1.0F : super.getBrightness();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SKIN_TYPE, Integer.valueOf(0));
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        // Variants - Classic (0) and Modern (1)
        if (this.world.rand.nextDouble() <= Modconfig.Lavacow_Classic_Texture_Chance) {
            this.setSkin(0);
        } else {
            this.setSkin(1);
        }

        return super.onInitialSpawn(difficulty, livingdata);
    }

    public int getSkin() {
        return this.dataManager.get(SKIN_TYPE).intValue();
    }

    public void setSkin(int skinType) {
        this.dataManager.set(SKIN_TYPE, Integer.valueOf(skinType));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("Variant", this.getSkin());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setSkin(compound.getInteger("Variant"));
    }

    @Override
    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && this.world.getBlockState((new BlockPos(this)).down()).canEntitySpawn(this) && (this.world.checkNoEntityCollision(this.getEntityBoundingBox()) && !this.world.containsAnyLiquid(this.getEntityBoundingBox()));
    }

    private boolean isWalkingonLand() {
        return Math.abs(this.limbSwingAmount) > 0.01F && !this.inWater && this.onGround;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.isWalkingonLand()) {
            for (int j = 0; j < 2; ++j) {
                float f = this.rand.nextFloat() * ((float) Math.PI * 2F);
                float f1 = this.rand.nextFloat() * 0.5F;
                float f2 = MathHelper.sin(f) * f1;
                float f3 = MathHelper.cos(f) * f1;
                World world = this.world;
                EnumParticleTypes enumparticletypes = EnumParticleTypes.FLAME;
                double d0 = this.posX + (double) f2;
                double d1 = this.posZ + (double) f3;
                world.spawnParticle(enumparticletypes, d0, this.getEntityBoundingBox().minY + (double) f1, d1, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (itemstack.getItem() == Items.BUCKET && !player.capabilities.isCreativeMode && !this.isChild() && Modconfig.Lavacow_Bucket) {
            player.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0F, 1.0F);
            itemstack.shrink(1);
            itemstack.getMaxStackSize();

            if (itemstack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.LAVA_BUCKET));
            } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.LAVA_BUCKET))) {
                player.dropItem(new ItemStack(Items.LAVA_BUCKET), false);
            }

            return true;
        } else {
            return super.processInteract(player, hand);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) source.getImmediateSource();

            if (!source.isExplosion()) {
                entity.attackEntityFrom(DamageSource.causeIndirectDamage((Entity) this, (EntityLivingBase) this).setFireDamage(), 2.0F);
            }
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public EntityLavaCow createChild(EntityAgeable ageable) {
        return new EntityLavaCow(this.world);
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTableHandler.LAVACOW;
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.contains(stack.getItem());
    }
}
