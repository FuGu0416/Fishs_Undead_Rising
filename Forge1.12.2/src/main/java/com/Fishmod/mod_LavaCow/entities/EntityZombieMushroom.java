package com.Fishmod.mod_LavaCow.entities;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.client.Modconfig;
import com.Fishmod.mod_LavaCow.core.SpawnUtil;
import com.Fishmod.mod_LavaCow.entities.tameable.EntitySummonedZombie;
import com.Fishmod.mod_LavaCow.init.FishItems;
import com.Fishmod.mod_LavaCow.init.ModEnchantments;
import com.Fishmod.mod_LavaCow.init.Modblocks;
import com.Fishmod.mod_LavaCow.util.LootTableHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityZombieMushroom extends EntitySummonedZombie implements IAggressive {
    private static final DataParameter<Integer> SKIN_TYPE = EntityDataManager.<Integer>createKey(EntityZombieMushroom.class, DataSerializers.VARINT);
    private Vec3d[] spore_color = {new Vec3d(0.83D, 0.73D, 0.5D), new Vec3d(0.0D, 0.98D, 0.93D)};

    public EntityZombieMushroom(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(SKIN_TYPE, Integer.valueOf(0));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.ZombieMushroom_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.ZombieMushroom_Attack);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    }

    @Override
    public boolean getCanSpawnHere() {
        return SpawnUtil.isAllowedDimension(this.dimension) && super.getCanSpawnHere();
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        super.onUpdate();
        
        if (!this.getEntityWorld().isRemote) {
            if (this.isEntityAlive()) {
                if (this.ticksExisted % 20 == 0) {
                    final List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D));
                    
                    if (!list.isEmpty()) {
                        for (Entity entity1 : list) {
                            if (entity1 instanceof EntityLivingBase) {
                                float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
                                ((EntityLivingBase) entity1).addPotionEffect(new PotionEffect(MobEffects.POISON, 2 * 20 * (int) local_difficulty, 0));
                            }
                        }
                    }
                }
            }
        }

        if (this.ticksExisted % 5 == 0 && this.getEntityWorld().isRemote)
            mod_LavaCow.PROXY.spawnCustomParticle("spore", world, this.posX + (double) (new Random().nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + (double) (new Random().nextFloat() * this.height), this.posZ + (double) (new Random().nextFloat() * this.width * 2.0F) - (double) this.width, 0.0D, 0.0D, 0.0D, (float) this.spore_color[this.getSkin()].x, (float) this.spore_color[this.getSkin()].y, (float) this.spore_color[this.getSkin()].z);
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
            this.attackTimer = 5;
            this.world.setEntityState(this, (byte) 4);

            return true;
        } else {
            return false;
        }
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.Unburied_Health);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Modconfig.Unburied_Attack);
        this.setHealth(this.getMaxHealth());

        boolean is_near_shroom = false;
        int dx = MathHelper.floor(this.posX);
        int dy = MathHelper.floor(this.getEntityBoundingBox().minY);
        int dz = MathHelper.floor(this.posZ);
        int r = 4;

        for (BlockPos C : BlockPos.getAllInBox(new BlockPos(dx - r, dy - r, dz - r), new BlockPos(dx + r, dy + r, dz + r)))
            if (this.getEntityWorld().getBlockState(C).getBlock() == Modblocks.GLOWSHROOM
                    || this.getEntityWorld().getBlockState(C).getBlock() == Modblocks.GLOWSHROOM_BLOCK_STEM
                    || this.getEntityWorld().getBlockState(C).getBlock() == Modblocks.GLOWSHROOM_BLOCK_CAP)
                is_near_shroom = true;


        if (is_near_shroom || (this.posY < 50.0D && !this.world.canSeeSky(new BlockPos(this.posX, (double) Math.round(this.posY), this.posZ))))
            this.setSkin(1);

        this.setEquipmentBasedOnDifficulty(difficulty);

        return super.onInitialSpawn(difficulty, entityLivingData);
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
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            } else {
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
            }
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        if (!world.isRemote) {
            if (new Random().nextFloat() < 0.1F) {
                int getVariant = this.getSkin();
                switch (getVariant) {
                    case 0:
                        this.entityDropItem(new ItemStack(Modblocks.CORDY_SHROOM, 1), 0.0f);
                        break;
                    case 1:
                        this.entityDropItem(new ItemStack(Modblocks.GLOWSHROOM, 1), 0.0f);
                        break;
                    default:
                        break;
                }
            }

            int i = net.minecraftforge.common.ForgeHooks.getLootingLevel(this, cause.getTrueSource(), cause);
            if (this.canDropLoot())
                LootTableHandler.dropRareLoot(this, FishItems.POISONSPORE, Modconfig.ZombieMushroom_DropSpore, ModEnchantments.POISONOUS, 3, i);

            if (this.world.getDifficulty() == EnumDifficulty.HARD && !this.isBurning()) {
                makeAreaOfEffectCloud(this);
            }
        }
    }

    private void makeAreaOfEffectCloud(EntityZombieMushroom EntityIn) {
        EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(EntityIn.world, EntityIn.posX, EntityIn.posY, EntityIn.posZ);
        float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
        entityareaeffectcloud.setOwner(EntityIn);
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setRadiusOnUse(-0.5F);
        entityareaeffectcloud.setWaitTime(10);
        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float) entityareaeffectcloud.getDuration());
        entityareaeffectcloud.setPotion(PotionTypes.POISON);
        entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.POISON, 2 * 20 * (int) local_difficulty, 0));
        entityareaeffectcloud.setColor(5149489);

        EntityIn.world.spawnEntity(entityareaeffectcloud);
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Modconfig.ZombieMushroom_Health + ((float) this.unbreaking * 2.0F));
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
        return LootTableHandler.ZOMBIEMUSHROOM;
    }
}
